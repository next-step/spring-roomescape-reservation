package roomescape.reservationTheme;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReservationThemeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<ReservationTheme> reservationThemeRowMapper;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationThemeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.reservationThemeRowMapper = (resultSet, rowNum) -> new ReservationTheme.Builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .thumbnail(resultSet.getString("thumbnail"))
                .build();
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("theme")
                .usingGeneratedKeyColumns("id");
    }

    public List<ReservationTheme> findAll(){

        final String sql = """
                            SELECT id, name, description, thumbnail
                            FROM theme
                           """;

        return jdbcTemplate.query(sql, reservationThemeRowMapper);
    }

    public Long save(ReservationTheme theme) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", theme.getName());
        parameters.put("description", theme.getDescription());
        parameters.put("thumbnail", theme.getThumbnail());

        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();

    }

    public ReservationTheme findById(Long id) {
        final String sql = """
                SELECT id, name, description, thumbnail
                FROM theme
                WHERE id = ?
                """;

        return jdbcTemplate.queryForObject(sql, reservationThemeRowMapper, id);
    }

    public Boolean existById(Long id) {
        final String sql = """
                SELECT EXISTS(
                    SELECT 1
                    FROM theme
                    WHERE id = ?
                )
                """;

        return jdbcTemplate.queryForObject(sql, Boolean.class, id);
    }

    public void deleteById(Long id) {
        final String sql = """
                DELETE
                FROM theme
                WHERE id = ?
                """;

        jdbcTemplate.update(sql, id);
    }
}
