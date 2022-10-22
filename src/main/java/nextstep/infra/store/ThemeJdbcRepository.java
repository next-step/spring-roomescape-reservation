package nextstep.infra.store;

import nextstep.domain.theme.model.Theme;
import nextstep.domain.theme.model.ThemeRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ThemeJdbcRepository implements ThemeRepository {
    private final JdbcTemplate jdbcTemplate;

    public ThemeJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Theme> rowMapper = (resultSet, rowNum) -> {
        Theme theme = new Theme(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("description"),
            resultSet.getLong("price")
        );
        return theme;
    };

    @Override
    public Long create(Theme theme) {
        final String query = "INSERT INTO themes(name, description, price) VALUES (?, ?, ?)";

        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDescription());
            ps.setLong(3, theme.getPrice());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public List<Theme> findAll() {
       final String query = "SELECT * FROM themes";
       return jdbcTemplate.query(query, rowMapper);
    }

    @Override
    public Optional<Theme> findById(Long id) {
        final String query = "SELECT * FROM themes WHERE id = ?";

        try {
            var theme = jdbcTemplate.queryForObject(query, rowMapper, id);
            return Optional.ofNullable(theme);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void remove(Long id) {
        final String query = "DELETE FROM themes WHERE id = ?";
        jdbcTemplate.update(query, id);
    }
}
