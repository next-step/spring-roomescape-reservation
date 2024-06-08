package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.util.List;

@Controller
public class ReservationController {
    private final JdbcTemplate jdbcTemplate;
    private final String SELECT_RESERVATION_SQL = "SELECT " +
            "r.id as reservation_id, " +
            "r.name as reservation_name, " +
            "r.date as reservation_date, " +
            "t.id as time_id, " +
            "t.start_at as time_start_at, " +
            "FROM reservation as r " +
            "INNER JOIN reservation_time as t " +
            "ON r.time_id = t.id";

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
                resultSet.getNString("reservation_name"),
                resultSet.getString("reservation_date"),
                reservationTime
        );
    };

    @GetMapping("/admin/reservation")
    public String showAdminPage() {
        return "admin/reservation";
    }

    @GetMapping("reservations")
    public ResponseEntity<List<Reservation>> read() {
        List<Reservation> reservations = jdbcTemplate.query(SELECT_RESERVATION_SQL, reservationRowMapper);
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("reservations")
    public ResponseEntity<Reservation> create(@RequestBody Reservation newReservation) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                String sql = "insert into reservation (name, date, time_id) values (?, ?, ?)";
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                ps.setString(1, newReservation.getName());
                ps.setString(2, newReservation.getDate());
                ps.setLong(3, newReservation.getTime().getId());
                return ps;
            }, keyHolder);

            long createId = keyHolder.getKey().longValue();
            String sql = SELECT_RESERVATION_SQL + " where r.id = ?";
            Reservation reservation = jdbcTemplate.queryForObject(sql, reservationRowMapper, createId);
            return ResponseEntity.ok().body(reservation);
        }
        catch (Exception e) {
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
