package nextstep.themes.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import nextstep.themes.Themes;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class ThemesDao {

    private final JdbcTemplate jdbcTemplate;

    public ThemesDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long insert(String name, String desc, Long price) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO themes(name, desc, price) VALUES(?, ?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[] {"id"});
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, desc);
            preparedStatement.setLong(3, price);
            return preparedStatement;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<Themes> findAll() {
        String sql = "SELECT * FROM themes";
        return jdbcTemplate.query(sql,
                                  (rs, rowNum) -> new Themes(
                                          rs.getLong("id"),
                                          rs.getString("name"),
                                          rs.getString("desc"),
                                          rs.getLong("price")
                                  ));
    }

    public void removeById(Long id) {
        String sql = "DELETE FROM themes WHERE id=?";
        jdbcTemplate.update(sql, id);
    }

    public Optional<Themes> findById(Long id) {
        String sql = "SELECT * FROM themes WHERE id=?";
        return jdbcTemplate.query(sql,
                                  (rs, rowNum) -> new Themes(
                                          rs.getLong("id"),
                                          rs.getString("name"),
                                          rs.getString("desc"),
                                          rs.getLong("price")
                                  ), id).stream().findAny();
    }
}
