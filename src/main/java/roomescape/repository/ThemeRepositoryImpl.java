package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Theme;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ThemeRepositoryImpl implements ThemeRepository{

    private final JdbcTemplate jdbcTemplate;

    public ThemeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Theme> findThemes() {
        String sql = "select id, name, description, thumbnail from theme";

        return jdbcTemplate.query(
                sql, (rs, rownum) -> {
                    Theme theme = new Theme(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getString("thumbnail")
                    );
                    return theme;
                });
    }

    @Override
    public Theme createTheme(Theme entity) {
        String sql = "insert into theme(name, description, thumbnail) values(?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    sql, new String[]{"id"});
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getDescription());
            ps.setString(3, entity.getThumbnail());
          return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();
        return new Theme(id, entity.getName(), entity.getDescription(), entity.getThumbnail());
    }

    @Override
    public void deleteTheme(Long id) {
        String sql = "delete from theme where id = ?";
        int deletedId = jdbcTemplate.update(sql, id);

        if (deletedId < 1) {
            throw new RuntimeException("존재하지 않는 아이디 : " + deletedId);
        }
    }
}
