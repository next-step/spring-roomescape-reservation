package roomescape.repository.mysql;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import roomescape.repository.ThemeJdbcRepository;
import roomescape.repository.entity.ThemeEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class MySQLJdbcThemeRepository implements ThemeJdbcRepository {

    private static final String TABLE_COLUMN_ID = "id";
    private static final String TABLE_COLUMN_NAME = "name";
    private static final String TABLE_COLUMN_DESCRIPTION = "description";
    private static final String TABLE_COLUMN_THUMBNAIL = "thumbnail";


    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public MySQLJdbcThemeRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public ThemeEntity save(ThemeEntity themeEntity) {
        String sql = "INSERT INTO theme (id, name, description, thumbnail) VALUES (:id, :name, :description, :thumbnail) " +
                "ON DUPLICATE KEY UPDATE name = VALUES(name), description = VALUES(description), thumbnail = VALUES(thumbnail)";

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(TABLE_COLUMN_ID, themeEntity.getId())
                .addValue(TABLE_COLUMN_NAME, themeEntity.getName())
                .addValue(TABLE_COLUMN_DESCRIPTION, themeEntity.getDescription())
                .addValue(TABLE_COLUMN_THUMBNAIL, themeEntity.getThumbnail());

        namedParameterJdbcTemplate.update(sql, sqlParameterSource, generatedKeyHolder);

        if (Objects.isNull(generatedKeyHolder.getKey())) {
            return themeEntity;
        }

        return themeEntity.withId(generatedKeyHolder.getKey().longValue());
    }

    @Override
    public Optional<ThemeEntity> findById(Long themeId) {
        String sql = "SELECT id, name, description, thumbnail FROM theme WHERE id = :id";

        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(TABLE_COLUMN_ID, themeId);

        try {
            return Optional.ofNullable(
                    namedParameterJdbcTemplate.queryForObject(
                            sql,
                            sqlParameterSource,
                            (resultSet, rowNum) -> new ThemeEntity(
                                    resultSet.getLong(TABLE_COLUMN_ID),
                                    resultSet.getString(TABLE_COLUMN_NAME),
                                    resultSet.getString(TABLE_COLUMN_DESCRIPTION),
                                    resultSet.getString(TABLE_COLUMN_THUMBNAIL)
                            )
                    )
            );
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<ThemeEntity> findAll() {
        String sql = "SELECT id, name, description, thumbnail FROM theme";

        return namedParameterJdbcTemplate.query(sql, resultSet -> {
            List<ThemeEntity> themeEntities = new ArrayList<>();
            while (resultSet.next()) {
                themeEntities.add(
                        new ThemeEntity(
                                resultSet.getLong(TABLE_COLUMN_ID),
                                resultSet.getString(TABLE_COLUMN_NAME),
                                resultSet.getString(TABLE_COLUMN_DESCRIPTION),
                                resultSet.getString(TABLE_COLUMN_THUMBNAIL)
                        )
                );
            }
            return themeEntities;
        });
    }

    @Override
    public void delete(Long themeId) {
        String sql = "DELETE FROM theme WHERE id = :id";

        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(TABLE_COLUMN_ID, themeId);

        namedParameterJdbcTemplate.update(sql, sqlParameterSource);
    }
}
