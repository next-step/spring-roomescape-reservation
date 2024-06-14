package roomescape.admin.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.admin.dto.SaveThemeRequest;
import roomescape.admin.entity.Theme;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class ThemeRepository {

    private final JdbcTemplate jdbcTemplate;

    public ThemeRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<List<Theme>> findAll() {
        return Optional.of(this.jdbcTemplate.query(ThemeQuery.FIND_ALL, readThemeRowMapper()));
    }

    public Optional<Theme> findById(Long id) {
        return Optional.of(this.jdbcTemplate.queryForObject(ThemeQuery.FIND_BY_ID, readThemeRowMapper(),id));
    }

    public Optional<Integer> countById(Long id){
        return Optional.of(this.jdbcTemplate.queryForObject(ThemeQuery.COUNT_BY_ID, Integer.class, id));
    }

    private RowMapper<Theme> readThemeRowMapper() {
        return (resultSet, rowNum) -> mapToTheme(resultSet);
    }

    private Theme mapToTheme(ResultSet rs) throws SQLException {
        long id = rs.getLong("theme_id");
        String name = rs.getString("theme_name");
        String description = rs.getString("theme_description");
        String thumbnail = rs.getString("theme_thumbnail");

        return Theme.of(id, name, description, thumbnail);
    }

    public Long save(SaveThemeRequest saveThemeRequest) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(con -> saveThemeStatement(con, saveThemeRequest), keyHolder);
        long id = keyHolder.getKey().longValue();

        return id;
    }

    private PreparedStatement saveThemeStatement(Connection con, SaveThemeRequest saveThemeRequest) throws SQLException {
        String name = saveThemeRequest.name();
        String description = saveThemeRequest.description();
        String thumbNail = saveThemeRequest.thumbnail();
        PreparedStatement ps = con.prepareStatement(ThemeQuery.SAVE, new String[]{"id"});
        ps.setString(1, name);
        ps.setString(2, description);
        ps.setString(3, thumbNail);

        return ps;
    }

    public void delete(Long id) {
        this.jdbcTemplate.update(ThemeQuery.DELETE, id);
    }
}