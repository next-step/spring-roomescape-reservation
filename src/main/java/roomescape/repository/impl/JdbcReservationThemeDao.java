package roomescape.repository.impl;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTheme;
import roomescape.repository.ReservationThemeDao;

@Repository
public class JdbcReservationThemeDao implements ReservationThemeDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationThemeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ReservationTheme save(ReservationTheme reservationTheme) {
        final String sql = "INSERT INTO theme (name, description, thumbnail) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservationTheme.getName());
            ps.setString(2, reservationTheme.getDescription());
            ps.setString(3, reservationTheme.getThumbnail());

            return ps;
        }, keyHolder);

        return reservationTheme.toEntity(reservationTheme, keyHolder.getKey().longValue());
    }

    @Override
    public List<ReservationTheme> findAll() {
        final String sql = "SELECT id, name, description, thumbnail FROM theme";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new ReservationTheme(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getString("thumbnail")
        ));
    }

    @Override
    public void delete(Long id) {
        final String sql = "DELETE FROM theme WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<ReservationTheme> findById(Long id) {
        final String sql = "SELECT id, name, description, thumbnail FROM theme WHERE id = ?";
        ReservationTheme reservationTheme = jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> new ReservationTheme(
                        rs.getLong("id")
                        , rs.getString("name")
                        , rs.getString("description")
                        , rs.getString("thumbnail")
                ), id);

        return Optional.ofNullable(reservationTheme);
    }


}

