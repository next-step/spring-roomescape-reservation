package roomescape.service.impl;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final JdbcTemplate jdbcTemplate;

    public ReservationServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation create(Reservation reservation) {
        final String sql = "INSERT INTO reservation (name, \"date\", \"time\") values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate());
            ps.setString(3, reservation.getTime());

            return ps;
        }, keyHolder);

        return reservation.toEntity(reservation, keyHolder.getKey().longValue());
    }

    @Override
    public List<Reservation> read() {
        final String sql = "SELECT id, name, \"date\", \"time\" FROM reservation";
        List<Reservation> reservations = jdbcTemplate.query(sql, (resultSet, rowNum) ->
                new Reservation(resultSet.getLong("id")
                        , resultSet.getString("name")
                        , resultSet.getString("date")
                        , resultSet.getString("time"))
        );

        return reservations;
    }

    @Override
    public void delete(Long id) {
        final String sql = "DELETE FROM reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
