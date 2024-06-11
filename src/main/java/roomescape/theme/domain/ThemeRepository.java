package roomescape.theme.domain;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository
public class ThemeRepository {

    private static final String FIND_BY_ID_SQL = "SELECT * FROM theme WHERE id = ?";
    private static final String FIND_ALL_SQL = "SELECT * FROM theme";
    private static final String SAVE_SQL = "INSERT INTO theme (name, description, thumbnail) VALUES (?, ?, ?)";
    private static final String DELETE_SQL = "DELETE FROM theme WHERE id = ?";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_THUMBNAIL = "thumbnail";
    private static final int INDEX_ONE = 1;
    private static final int INDEX_TWO = 2;
    private static final int INDEX_THREE = 3;
    private static final int ZERO = 0;

    private final JdbcTemplate jdbcTemplate;

    public ThemeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(Theme theme) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    SAVE_SQL,
                    new String[]{COLUMN_ID}
            );
            preparedStatement.setString(INDEX_ONE, theme.getName());
            preparedStatement.setString(INDEX_TWO, theme.getDescription());
            preparedStatement.setString(INDEX_THREE, theme.getThumbnail());
            return preparedStatement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Theme findById(Long id) {
        return jdbcTemplate.query(FIND_BY_ID_SQL, (rs, rowNum) -> new Theme(rs.getLong(COLUMN_ID), rs.getString(COLUMN_NAME), rs.getString(COLUMN_DESCRIPTION), rs.getString(COLUMN_THUMBNAIL)), id).get(ZERO);

    }

    public List<Theme> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, (rs, rowNum) -> new Theme(rs.getLong(COLUMN_ID), rs.getString(COLUMN_NAME), rs.getString(COLUMN_DESCRIPTION), rs.getString(COLUMN_THUMBNAIL)));
    }

    public void delete(Long id) {
        jdbcTemplate.update(DELETE_SQL, id);
    }
}
