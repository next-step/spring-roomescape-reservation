package roomescape.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.DTO.ThemeRequest;
import roomescape.Entity.Theme;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class JdbcTemplateThemeRepository implements ThemeRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateThemeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Theme> themeRowMapper = (resultSet, rowNum) -> {
        return new Theme(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getString("thumbnail")
        );
    };

    @Override
    public List<Theme> findAll() {
        String sql = "select * from theme";
        return jdbcTemplate.query(sql, themeRowMapper);
    }

    @Override
    public Theme findById(Long id) {
        String sql = "select * from theme where id = ?";
        return jdbcTemplate.queryForObject(sql, themeRowMapper, id);
    }

    @Override
    public long save(ThemeRequest request) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            String sql = "insert into theme (name, description, thumbnail) values (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
            ps.setString(1, request.getName());
            ps.setString(2, request.getDescription());
            ps.setString(3, request.getThumbnail());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public long deleteById(Long id) {
        String sql = "delete from theme where id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
