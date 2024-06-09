package roomescape.theme.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import roomescape.theme.domain.Theme;
import roomescape.theme.domain.repository.ThemeRepository;

@Repository
public class JdbcThemeRepository implements ThemeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcThemeRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("theme")
                .usingGeneratedKeyColumns("id");;
    }

    @Override
    public Theme save(Theme theme) {
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(theme);
        long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return new Theme(id, theme.getName(), theme.getDescription(), theme.getThumbnail());
    }

    @Override
    public Optional<Theme> findById(Long themeId) {
        String sql = "SELECT * FROM theme WHERE id = ?";
        try {
            Theme findTheme = jdbcTemplate.queryForObject(sql, (resultSet, rowNum) -> new Theme(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getString("thumbnail")
            ), themeId);
            return Optional.ofNullable(findTheme);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Theme> findAll() {
        String sql = "SELECT * FROM theme";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> new Theme(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getString("thumbnail")
        ));
    }

    @Override
    public boolean existsById(Long themeId) {
        String sql = "SELECT COUNT(*) FROM theme WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, themeId);
        return count != null && count.equals(1);
    }

    @Override
    public void deleteById(Long themeId) {
        String sql = "DELETE FROM theme WHERE id = ?";
        jdbcTemplate.update(sql, themeId);
    }
}
