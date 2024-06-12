package roomescape.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;
import roomescape.DTO.ReservationRequest;
import roomescape.DTO.ReservationResponse;
import roomescape.Entity.Reservation;
import roomescape.Entity.ReservationTime;

import java.sql.PreparedStatement;
import java.util.List;

@RestController
public class ReservationController {
    private final JdbcTemplate jdbcTemplate;

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        ReservationTime reservationTime = new ReservationTime(
                resultSet.getLong("time_id"),
                resultSet.getString("time_start_at")
        );
        return new Reservation(
                resultSet.getLong("reservation_id"),
                resultSet.getString("reservation_name"),
                resultSet.getString("reservation_date"),
                reservationTime
        );
    };

    @GetMapping("reservations")
    public ResponseEntity<List<ReservationResponse>> read() {
        String sql = "SELECT " +
                "r.id as reservation_id, " +
                "r.name as reservation_name, " +
                "r.date as reservation_date, " +
                "t.id as time_id, " +
                "t.start_at as time_start_at, " +
                "FROM reservation as r " +
                "INNER JOIN reservation_time as t " +
                "ON r.time_id = t.id";
        List<Reservation> reservations = jdbcTemplate.query(sql, reservationRowMapper);
        return ResponseEntity.ok().body(ReservationResponse.toDTOList(reservations));
    }

    @PostMapping("reservations")
    public ResponseEntity<ReservationResponse> create(@RequestBody ReservationRequest request) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                String sql = "insert into reservation (name, date, time_id) values (?, ?, ?)";
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                ps.setString(1, request.getName());
                ps.setString(2, request.getDate());
                ps.setLong(3, request.getTimeId());
                return ps;
            }, keyHolder);
            long createId = keyHolder.getKey().longValue();

            String sql = "SELECT " +
                    "r.id as reservation_id, " +
                    "r.name as reservation_name, " +
                    "r.date as reservation_date, " +
                    "t.id as time_id, " +
                    "t.start_at as time_start_at, " +
                    "FROM reservation as r " +
                    "INNER JOIN reservation_time as t " +
                    "ON r.time_id = t.id " +
                    "WHERE r.id = ?";
            Reservation reservation = jdbcTemplate.queryForObject(sql, reservationRowMapper, createId);
            return ResponseEntity.ok().body(new ReservationResponse(reservation));
        }
        catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("reservations/{id}")
    public ResponseEntity<Void> remove(@PathVariable("id") Long id) {
        String sql = "delete from reservation where id = ?";
        long deleteCount = jdbcTemplate.update(sql, id);

        if (deleteCount == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
