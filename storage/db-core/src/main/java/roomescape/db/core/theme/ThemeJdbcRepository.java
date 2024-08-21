package roomescape.db.core.theme;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.core.domain.common.ActiveStatus;
import roomescape.core.domain.theme.exception.ThemeException;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ThemeJdbcRepository {

    public static final String SELECT_ALL_THEME_SQL = """
            select
                theme_id,
                name,
                description,
                thumbnail,
                active_status
            from themes""";

    public static final RowMapper<ThemeEntity> THEME_ENTITY_ROW_MAPPER =
            (rs, rowNum) -> ThemeEntity.builder()
                    .themeId(rs.getLong("theme_id"))
                    .name(rs.getString("name"))
                    .description(rs.getString("description"))
                    .thumbnail(rs.getString("thumbnail"))
                    .activeStatus(ActiveStatus.valueOf(rs.getString("active_status")))
                    .build();

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<ThemeEntity> findAll() {
        return jdbcTemplate.query(SELECT_ALL_THEME_SQL, THEME_ENTITY_ROW_MAPPER);
    }

    public Optional<ThemeEntity> findById(final Long themeId) {
        if (Objects.isNull(themeId)) {
            return Optional.empty();
        }
        return queryForThemeEntity(SELECT_ALL_THEME_SQL + " where theme_id = ? ", themeId);
    }

    public List<ThemeEntity> findAllByActiveStatus(final ActiveStatus activeStatus) {
        return queryForThemeEntities(SELECT_ALL_THEME_SQL + " where active_status = ? ", activeStatus.name());
    }

    public List<ThemeEntity> findAllByIds(final List<Long> themeIds) {
        final String sql = SELECT_ALL_THEME_SQL + " where theme_id in (:ids)";

        final MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ids", themeIds);

        return namedParameterJdbcTemplate.query(sql, parameters, THEME_ENTITY_ROW_MAPPER);
    }

    public ThemeEntity save(final ThemeEntity themeEntity) {
        if (Objects.isNull(themeEntity.getThemeId())) {
            return insertWithKeyHolder(themeEntity);
        }

        updateAll(themeEntity);

        return themeEntity;
    }

    private Optional<ThemeEntity> queryForThemeEntity(final String selectSql, Object... objects) {
        try {
            final ThemeEntity themeEntity = jdbcTemplate.queryForObject(
                    selectSql,
                    THEME_ENTITY_ROW_MAPPER,
                    objects
            );
            return Optional.ofNullable(themeEntity);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private List<ThemeEntity> queryForThemeEntities(final String selectSql, Object... objects) {
        return jdbcTemplate.query(selectSql, THEME_ENTITY_ROW_MAPPER, objects);
    }


    private ThemeEntity insertWithKeyHolder(final ThemeEntity themeEntity) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        final String insertSql = """
                insert into themes (
                    name,
                    description,
                    thumbnail,
                    active_status
                ) values (?, ?, ?, ?)""";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertSql, new String[]{"theme_id"});
            ps.setString(1, themeEntity.getName());
            ps.setString(2, themeEntity.getDescription());
            ps.setString(3, themeEntity.getThumbnail());
            ps.setString(4, themeEntity.getActiveStatus().name());
            return ps;
        }, keyHolder);

        final long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return ThemeEntity.builder()
                .themeId(generatedId)
                .name(themeEntity.getName())
                .description(themeEntity.getDescription())
                .thumbnail(themeEntity.getThumbnail())
                .activeStatus(themeEntity.getActiveStatus())
                .build();
    }

    private void updateAll(final ThemeEntity themeEntity) {
        final String updateSql = """
                update themes set
                    name = ?,
                    description = ?, 
                    thumbnail = ?,
                    active_status = ? 
                where theme_id = ?""";

        final int updatedRowCount = jdbcTemplate.update(
                updateSql,
                themeEntity.getName(),
                themeEntity.getDescription(),
                themeEntity.getThumbnail(),
                themeEntity.getActiveStatus().name(),

                themeEntity.getThemeId()
        );

        if (updatedRowCount != 1) {
            throw new ThemeException(
                    "Error occurred while updating ThemeEntity where theme_id=%d. Affected row is not 1 but %d."
                            .formatted(themeEntity.getThemeId(), updatedRowCount)
            );
        }
    }

    public void deleteAllInBatch() {
        jdbcTemplate.execute("delete from themes");
    }
}
