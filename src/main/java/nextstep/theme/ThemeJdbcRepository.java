package nextstep.theme;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ThemeJdbcRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private final RowMapper<Theme> themeRowMapper = (rs, count) -> new Theme(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("desc"),
            rs.getInt("price")
    );

    public ThemeJdbcRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("theme")
                .usingGeneratedKeyColumns("id");
    }

    public void clear() {
        jdbcTemplate.update("DELETE FROM THEME");
    }

    public Theme create(Theme theme) {
        SqlParameterSource source = new MapSqlParameterSource()
                .addValue("name", theme.getName())
                .addValue("desc", theme.getDesc())
                .addValue("price", theme.getPrice());
        Long id = (Long) simpleJdbcInsert.executeAndReturnKey(source);
        theme.setId(id);
        return theme;
    }

    public boolean existByName(String name) {
        String sql = "SELECT * FROM theme WHERE name = ? LIMIT 1";
        List<Theme> themes = jdbcTemplate.query(sql, themeRowMapper, name);
        return !(themes.isEmpty());
    }

    public boolean existById(Long id) {
        String sql = "SELECT * FROM theme WHERE id = ? LIMIT 1";
        List<Theme> themes = jdbcTemplate.query(sql, themeRowMapper, id);
        return !(themes.isEmpty());
    }

    public List<Theme> findAll() {
        String sql = "SELECT * FROM theme";
        return jdbcTemplate.query(sql, themeRowMapper);
    }

    public boolean deleteThemeById(Long themeId) {
        String sql = "DELETE FROM theme WHERE id = ?";
        int count = jdbcTemplate.update(sql, themeId);
        return count > 0;
    }
}
