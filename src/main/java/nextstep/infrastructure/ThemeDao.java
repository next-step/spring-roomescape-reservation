package nextstep.infrastructure;

import java.util.List;
import java.util.Optional;
import nextstep.domain.Theme;
import nextstep.domain.repository.ThemeRepository;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ThemeDao implements ThemeRepository {

    private final JdbcTemplate jdbcTemplate;

    public ThemeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Theme theme) {
        jdbcTemplate.update(
            "insert into theme (name, desc, price) values (?, ?, ?)",
            theme.getName(),
            theme.getDesc(),
            theme.getPrice()
        );
    }

    @Override
    public Optional<Theme> findBy(String name) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                "select id, name, desc, price from theme where name = ?",
                (rs, rowNum) -> new Theme(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("desc"),
                    rs.getInt("price")
                ),
                name
            ));
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Theme> findBy(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                "select id, name, desc, price from theme where id = ?",
                (rs, rowNum) -> new Theme(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("desc"),
                    rs.getInt("price")
                ),
                id
            ));
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Theme> findAll() {
        return jdbcTemplate.query(
            "select id, name, desc, price from theme",
            (rs, rowNum) -> new Theme(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("desc"),
                rs.getInt("price")
            )
        );
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("delete from theme where id = ?", id);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from theme");
    }
}
