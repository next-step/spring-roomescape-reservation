package nextstep.theme.persistence;

import java.util.List;
import java.util.Map;
import nextstep.theme.domain.Theme;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ThemeDao {

    private static final RowMapper<Theme> themeMapper =
        (resultSet, rowNum) -> new Theme(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("desc"),
            resultSet.getLong("price")
        );

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ThemeDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long insert(Theme theme) {
        String query = "INSERT INTO theme (name, desc, price) VALUES (:name, :desc, :price)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(theme);
        jdbcTemplate.update(query, sqlParameterSource, keyHolder);
        return keyHolder.getKeyAs(Long.class);
    }

    public List<Theme> findAll() {
        String query = "SELECT * FROM theme";
        return jdbcTemplate.query(query, themeMapper);
    }

    public void deleteById(Long id) {
        String query = "DELETE FROM theme WHERE (id = :id)";
        jdbcTemplate.update(query, Map.of("id", id));
    }
}
