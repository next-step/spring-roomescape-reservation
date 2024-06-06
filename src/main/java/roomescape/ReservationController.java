package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {
    private final JdbcTemplate jdbcTemplate;

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        return new Reservation(
                resultSet.getLong("id"),
                resultSet.getNString("name"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime()
        );
    };

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    @GetMapping("reservations")
    public ResponseEntity<List<Reservation>> read() {
        String sql = "select * from reservation";
        List<Reservation> reservations = jdbcTemplate.query(sql, reservationRowMapper);

        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("reservations")
    public ResponseEntity<Reservation> create(@RequestBody Reservation newReservation) {
        Reservation reservation = Reservation.toEntity(newReservation, index.getAndIncrement());
        reservations.add(reservation);
        return ResponseEntity.ok().body(reservation);
    }

    @DeleteMapping("reservations/{id}")
    public ResponseEntity<Void> remove(@PathVariable("id") Long id) {
        Reservation removedReservation;

        try {
             removedReservation = reservations.stream()
                    .filter(reservation -> Objects.equals(reservation.getId(), id))
                    .findFirst()
                    .orElseThrow(RuntimeException::new);
        }
        catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
        reservations.remove(removedReservation);
        return ResponseEntity.ok().build();
    }
}
