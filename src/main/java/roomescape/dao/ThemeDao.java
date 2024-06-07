package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Theme;

import java.util.List;
import java.util.Optional;

@Repository
public class ThemeDao {
    private JdbcTemplate jdbcTemplate;

    public ThemeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Theme> findAll() {
        return jdbcTemplate.query("SELECT * FROM theme", (rs, rowNum) -> new Theme(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getString("thumbnail")
        ));
    }

    public Theme save(Theme theme) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            var ps = connection.prepareStatement("INSERT INTO theme(name, description, thumbnail) VALUES (?, ?, ?)", new String[]{"id"});
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDescription());
            ps.setString(3, theme.getThumbnail());
            return ps;
        }, keyHolder);

        return new Theme(keyHolder.getKey().longValue(), theme.getName(), theme.getDescription(), theme.getThumbnail());
    }

    public void deleteById(Long id) {
        String sql = "delete from theme where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Optional<Theme> findById(Long themeId) {
        String sql = "select * from theme where id = ?";
        return jdbcTemplate.query(sql, rs -> {
            if (rs.next()) {
                return Optional.of(new Theme(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("thumbnail")
                ));
            } else {
                return Optional.empty();
            }
        }, themeId);
    }
}
