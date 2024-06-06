package roomescape.service.impl;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final JdbcTemplate jdbcTemplate;

    public ReservationServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ReservationResponse create(ReservationRequest request) {
        final String sql = "INSERT INTO reservation (name, \"date\", time_id) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, request.getName());
            ps.setString(2, request.getDate());
            ps.setString(3, request.getTimeId());

            return ps;
        }, keyHolder);

        return new ReservationResponse(keyHolder.getKey().longValue(), request.getName(), request.getDate(),
                request.getTimeId());
    }

    @Override
    public List<Reservation> read() {
        final String sql = "SELECT "
                + "r.id as reservation_id"
                + ", r.name as reservation_name"
                + ", r.\"date\" as reservation_date"
                + ", t.id as time_id"
                + ", t.start_at as time_start_at"
                + " FROM reservation as r"
                + " inner join reservation_time as t"
                + " on r.time_id = t.id";

        List<Reservation> reservations = jdbcTemplate.query(sql, (resultSet, rowNum) -> {
                    ReservationTime reservationTime = new ReservationTime(
                            resultSet.getLong("time_id"),
                            resultSet.getString("time_start_at")
                    );

                    return new Reservation(resultSet.getLong("id")
                            , resultSet.getString("name")
                            , resultSet.getString("date")
                            , reservationTime);
                }
        );

        return reservations;
    }

    @Override
    public void delete(Long id) {
        final String sql = "DELETE FROM reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }
}