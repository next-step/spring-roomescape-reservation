package roomescape.domain.theme.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.common.exception.DataAccessException;
import roomescape.domain.theme.domain.Theme;
import roomescape.domain.theme.domain.ThemeId;

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
                deleted
            from themes""";

    public static final RowMapper<Theme> THEME_ENTITY_ROW_MAPPER =
            (rs, rowNum) -> Theme.builder()
                    .id(new ThemeId(rs.getLong("theme_id")))
                    .name(rs.getString("name"))
                    .description(rs.getString("description"))
                    .thumbnail(rs.getString("thumbnail"))
                    .deleted(rs.getBoolean("deleted"))
                    .build();

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Theme> findAll() {
        return jdbcTemplate.query(SELECT_ALL_THEME_SQL, THEME_ENTITY_ROW_MAPPER);
    }

    public Optional<Theme> findById(final Long themeId) {
        if (Objects.isNull(themeId)) {
            return Optional.empty();
        }
        return queryForTheme(SELECT_ALL_THEME_SQL + " where theme_id = ? ", themeId);
    }

    public List<Theme> findAllByDeleted(final boolean isDeleted) {
        return queryForThemeEntities(SELECT_ALL_THEME_SQL + " where deleted = ? ", isDeleted);
    }

    public List<Theme> findAllByIds(final List<Long> themeIds) {
        final String sql = SELECT_ALL_THEME_SQL + " where theme_id in (:ids)";

        final MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ids", themeIds);

        return namedParameterJdbcTemplate.query(sql, parameters, THEME_ENTITY_ROW_MAPPER);
    }

    public Theme save(final Theme theme) {
        if (Objects.isNull(theme.getId())) {
            return insertWithKeyHolder(theme);
        }

        updateAll(theme);

        return theme;
    }

    private Optional<Theme> queryForTheme(final String selectSql, Object... objects) {
        try {
            final Theme Theme = jdbcTemplate.queryForObject(
                    selectSql,
                    THEME_ENTITY_ROW_MAPPER,
                    objects
            );
            return Optional.ofNullable(Theme);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private List<Theme> queryForThemeEntities(final String selectSql, Object... objects) {
        return jdbcTemplate.query(selectSql, THEME_ENTITY_ROW_MAPPER, objects);
    }


    private Theme insertWithKeyHolder(final Theme theme) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        final String insertSql = """
                insert into themes (
                    name,
                    description,
                    thumbnail,
                    deleted
                ) values (?, ?, ?, ?)""";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertSql, new String[]{"theme_id"});
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDescription());
            ps.setString(3, theme.getThumbnail());
            ps.setBoolean(4, theme.isDeleted());
            return ps;
        }, keyHolder);

        final long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return Theme.builder()
                .id(new ThemeId(generatedId))
                .name(theme.getName())
                .description(theme.getDescription())
                .thumbnail(theme.getThumbnail())
                .deleted(theme.isDeleted())
                .build();
    }

    private void updateAll(final Theme theme) {
        final String updateSql = """
                update themes set
                    name = ?,
                    description = ?, 
                    thumbnail = ?,
                    deleted = ? 
                where theme_id = ?""";

        final int updatedRowCount = jdbcTemplate.update(
                updateSql,
                theme.getName(),
                theme.getDescription(),
                theme.getThumbnail(),
                theme.isDeleted(),
                theme.getId().value()
        );

        if (updatedRowCount != 1) {
            throw new DataAccessException(
                    "Error occurred while updating Theme where theme_id=%d. Affected row is not 1 but %d."
                            .formatted(theme.getId().value(), updatedRowCount)
            );
        }
    }

    public void deleteAllInBatch() {
        jdbcTemplate.execute("delete from themes");
    }
}
