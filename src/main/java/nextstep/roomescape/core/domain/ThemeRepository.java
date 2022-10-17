package nextstep.roomescape.core.domain;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ThemeRepository {

    private static final RowMapper<Theme> ROW_MAPPER = (rs, rowNum) -> new Theme(
        rs.getInt("id"),
        rs.getString("name"),
        rs.getString("desc"),
        rs.getInt("price")
    );

    private final JdbcTemplate jdbcTemplate;

    public ThemeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Theme> findById(Integer id) {
        try {
            final String sql = "SELECT * FROM themes WHERE id = ? AND deleted = false";
            return Optional.of(jdbcTemplate.queryForObject(sql, ROW_MAPPER, id));
        } catch (IncorrectResultSizeDataAccessException IRSDAE) {
            return Optional.empty();
        }
    }

    public List<Theme> findAll() {
        final String sql = "SELECT * FROM themes WHERE deleted = false";
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    public void deleteById(Integer id) {
        final String sql = "UPDATE themes SET deleted = true WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public int create(Theme theme) {
        final String sql = "INSERT INTO themes(name, desc, price) VALUES (?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, theme.name());
            ps.setString(2, theme.desc());
            ps.setInt(3, theme.price());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    public Boolean existsById(Integer id) {
        final String sql = "SELECT EXISTS(SELECT 1 FROM themes WHERE id = ? AND deleted = false)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, id);
    }
}
