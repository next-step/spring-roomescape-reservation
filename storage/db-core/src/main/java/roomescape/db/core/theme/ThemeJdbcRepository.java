package roomescape.db.core.theme;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.core.domain.theme.exception.ThemeException;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class ThemeJdbcRepository {

    public static final String SELECT_ALL_THEME_SQL = """
            select
                theme_id,
                name,
                description,
                thumbnail
            from themes""";

    public static final RowMapper<ThemeEntity> THEME_ENTITY_ROW_MAPPER =
            (rs, rowNum) -> ThemeEntity.builder()
                    .themeId(rs.getLong("theme_id"))
                    .name(rs.getString("name"))
                    .description(rs.getString("description"))
                    .thumbnail(rs.getString("thumbnail"))
                    .build();

    private final JdbcTemplate jdbcTemplate;

    public List<ThemeEntity> findAll() {
        return jdbcTemplate.query(SELECT_ALL_THEME_SQL, THEME_ENTITY_ROW_MAPPER);
    }

    public ThemeEntity save(final ThemeEntity themeEntity) {
        if (Objects.isNull(themeEntity.getThemeId())) {
            return insertWithKeyHolder(themeEntity);
        }

        updateAll(themeEntity);

        return themeEntity;
    }

    private ThemeEntity insertWithKeyHolder(final ThemeEntity themeEntity) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        final String insertSql = """
                insert into themes (
                    name,
                    description,
                    thumbnail
                ) values (?, ?, ?)""";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertSql, new String[]{"theme_id"});
            ps.setString(1, themeEntity.getName());
            ps.setString(2, themeEntity.getDescription());
            ps.setString(3, themeEntity.getThumbnail());
            return ps;
        }, keyHolder);

        final long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return ThemeEntity.builder()
                .themeId(generatedId)
                .name(themeEntity.getName())
                .description(themeEntity.getDescription())
                .thumbnail(themeEntity.getThumbnail())
                .build();
    }

    private void updateAll(final ThemeEntity themeEntity) {
        final String updateSql = """
                update themes set
                    name = ?,
                    description = ?, 
                    thumbnail = ? 
                where theme_id = ?""";

        final int updatedRowCount = jdbcTemplate.update(
                updateSql,
                themeEntity.getName(),
                themeEntity.getDescription(),
                themeEntity.getThumbnail(),
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
