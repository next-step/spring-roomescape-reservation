package nextstep.infra.h2;

import nextstep.core.theme.Theme;
import nextstep.core.theme.ThemeRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class ThemeH2Repository implements ThemeRepository {
    private static final RowMapper<Theme> ROW_MAPPER = (resultSet, rowNum) -> new Theme(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("desc"),
            resultSet.getLong("price")
    );
    private final JdbcTemplate template;

    public ThemeH2Repository(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Theme save(Theme theme) {
        Objects.requireNonNull(theme);

        String query = "INSERT INTO themes(name, desc, price) VALUES(?, ?, ?)";
        template.update(query, theme.getName(), theme.getDesc(), theme.getPrice());

        Long id = template.queryForObject("SELECT last_insert_id()", Long.class);
        return new Theme(id, theme.getName(), theme.getDesc(), theme.getPrice());
    }

    @Override
    public List<Theme> findAll() {
        String query = "SELECT * FROM themes";
        return template.query(query, ROW_MAPPER);
    }
}
