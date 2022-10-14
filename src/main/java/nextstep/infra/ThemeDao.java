package nextstep.infra;

import java.util.List;
import javax.sql.DataSource;
import nextstep.domain.Theme;
import nextstep.domain.ThemeRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ThemeDao implements ThemeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertTheme;
    private final RowMapper<Theme> mapper;

    public ThemeDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertTheme = new SimpleJdbcInsert(dataSource)
            .withTableName("theme")
            .usingGeneratedKeyColumns("id");
        this.mapper = (resultSet, rowNum) -> new Theme(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("description"),
            resultSet.getBigDecimal("price")
        );
    }

    @Override
    public Theme save(Theme theme) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(theme);
        Long id = insertTheme.executeAndReturnKey(parameters).longValue();
        return new Theme(id, theme);
    }

    @Override
    public List<Theme> findAll() {
        String sql = "SELECT * FROM theme";
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public int deleteById(Long id) {
        String sql = "DELETE FROM theme WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
