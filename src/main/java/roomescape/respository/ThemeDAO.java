package roomescape.respository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.model.Theme;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ThemeDAO {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Theme> rowMapper = (resultSet, rowNumber) -> {
        Theme theme = new Theme(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getString("thumbnail")
        );
        return theme;
    };

    public ThemeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Theme insertTheme(Theme theme) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "insert into theme (name, description, thumbnail) values (?,?,?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    new String[]{"id"});
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDescription());
            ps.setString(3, theme.getThumbnail());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        return findThemeById(id);
    }

    public Theme findThemeById(Long id) {
        String sql = "select id, name, description, thumbnail from theme where id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public List<Theme> readThemes() {
        String sql = "select id, name, description, thumbnail from theme";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void deleteTheme(Long id) {
        String sql = "delete from theme where id = ?";
        jdbcTemplate.update(sql, Long.valueOf(id));
    }
}
