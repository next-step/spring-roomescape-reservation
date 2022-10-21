package nextstep.persistence.theme;

import nextstep.domain.theme.model.Theme;
import nextstep.domain.theme.model.ThemeRepository;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class ThemeJdbcRepository implements ThemeRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertActor;
    private final RowMapper<Theme> rowMapper;

    public ThemeJdbcRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName("theme")
                .usingGeneratedKeyColumns("id");
        this.rowMapper = (resultSet, rowNumber) -> new Theme(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("desc"),
                resultSet.getLong("price"));
    }

    @Override
    public Theme save(Theme theme) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", theme.getName())
                .addValue("desc", theme.getDesc())
                .addValue("price", theme.getPrice());
        Long id = insertActor.executeAndReturnKey(params).longValue();

        return new Theme(id, theme.getName(), theme.getDesc(), theme.getPrice());
    }

    @Override
    public List<Theme> findAll() {
        String sql = "SELECT * FROM theme";

        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Optional<Theme> findById(Long id) {
        String sql = "SELECT * FROM theme WHERE id = ?";
        List<Theme> themes = jdbcTemplate.query(sql, rowMapper, id);

        return Optional.ofNullable(DataAccessUtils.singleResult(themes));
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM theme WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }
}
