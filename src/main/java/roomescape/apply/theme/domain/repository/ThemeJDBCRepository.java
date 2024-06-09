package roomescape.apply.theme.domain.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.apply.theme.domain.Theme;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Repository
public class ThemeJDBCRepository implements ThemeRepository {

    private static final String INSERT_SQL = """
                INSERT INTO theme(name, description, thumbnail)
                 VALUES (?, ?, ?)
            """;

    private static final String SELECT_ALL_SQL = """
                SELECT
                    id,
                    name,
                    description,
                    thumbnail
                FROM
                    theme
            """;

    private static final String CHECK_ID_EXISTS_SQL = """
                SELECT
                    id
                FROM
                    theme
                WHERE
                    id = ?
            """;

    private static final String SELECT_ONE_SQL = """
                SELECT
                    id,
                    name,
                    description,
                    thumbnail
                FROM
                    theme
                WHERE
                    id = ?
            """;

    private static final String DELETE_SQL = """
                DELETE FROM theme
                WHERE id = ?
            """;

    private final JdbcTemplate template;

    public ThemeJDBCRepository(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Theme save(Theme theme) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDescription());
            ps.setString(3, theme.getThumbnail());
            return ps;
        }, keyHolder);

        long key = Objects.requireNonNull(keyHolder.getKey()).longValue();
        theme.changeId(key);

        return theme;
    }

    @Override
    public List<Theme> findAll() {
        return template.query(SELECT_ALL_SQL, themeRowMapper());
    }

    @Override
    public void deleteById(Long id) {
        template.update(DELETE_SQL, id);
    }

    @Override
    public Optional<Long> checkIdExists(long id) {
        try {
            Long themeId = template.queryForObject(CHECK_ID_EXISTS_SQL, Long.class, id);
            return Optional.ofNullable(themeId);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Theme> findById(long themeId) {
        try {
            Theme theme = template.queryForObject(SELECT_ONE_SQL, themeRowMapper(), themeId);
            return Optional.ofNullable(theme);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    private RowMapper<Theme> themeRowMapper() {
        return (rs, rowNum) -> new Theme(rs.getLong("id"), rs.getString("name"),
                                         rs.getString("description"), rs.getString("thumbnail"));
    }
}