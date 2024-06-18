package roomescape.theme.domain;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.theme.domain.entity.Theme;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ThemeJdbcTemplateRepository implements ThemeRepository {
    private final JdbcTemplate jdbcTemplate;

    public ThemeJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Theme> rowMapper = (resultSet, rowNum) -> {
        return Theme.of(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getString("thumbnail")
        );
    };

    @Override
    public List<Theme> findAll() {
        String sql = """
                    select *
                    from theme
                    """;
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Theme findById(Long id) {
        String sql = """
                    select *
                    from theme
                    where id = ?""";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    @Override
    public Theme findByName(String name) {
        String sql = """
                    select *
                    from theme
                    where name = ?
                    """;
        return jdbcTemplate.queryForObject(sql, rowMapper, name);
    }

    @Override
    public Long countReservationMatchWith(Long id) {
        String sql = """
                    select count(*)
                    from reservation as r
                    inner join theme as t
                    on r.theme_id = t.id
                    where t.id = ?
                    """;
        return jdbcTemplate.queryForObject(sql, Long.class, id);
    }

    @Override
    public long save(String name, String description, String thumbnail) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            String sql = """
                        insert into theme
                        (name, description, thumbnail)
                        values (?, ?, ?)
                        """;
            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setString(3, thumbnail);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public long deleteById(Long id) {
        String sql = """
                    delete from theme
                    where id = ?
                    """;
        return jdbcTemplate.update(sql, id);
    }
}
