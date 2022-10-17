package nextstep.data.jdbc.repository;

import nextstep.data.jdbc.repository.support.AbstractJdbcRepository;
import nextstep.domain.theme.domain.model.Theme;
import nextstep.domain.theme.domain.model.ThemeRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ThemeJdbcRepository extends AbstractJdbcRepository<Theme> implements ThemeRepository {
    protected ThemeJdbcRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected String tableName() {
        return "theme";
    }

    @Override
    protected RowMapper<Theme> rowMapper() {
        return (rs, rowNum) -> new Theme(
                rs.getLong("id"),
                rs.getObject("name", String.class),
                rs.getObject("description", String.class),
                rs.getLong("price")
        );
    }

    @Override
    public Theme save(Theme theme) {
        long id = simpleJdbcInsert.executeAndReturnKey(
                Map.of(
                        "name", theme.name(),
                        "description", theme.description(),
                        "price", theme.price()
                )
        ).longValue();

        return theme.withId(id);
    }

    @Override
    public List<Theme> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM theme",
                rowMapper()
        );
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM theme WHERE id=?", id);
    }

    @Override
    public void deleteAll() {
        super.deleteAll();
    }

    @Override
    public Optional<Theme> findByName(String name) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "SELECT * FROM theme WHERE name=?",
                            rowMapper(),
                            name
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
