package nextstep.reservation;

import java.net.URI;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final JdbcTemplate jdbcTemplate;

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping
    public ResponseEntity reserve(@RequestBody Reservation reservation) {
        if (existsReservation(reservation.getDate(), reservation.getTime())) {
            return ResponseEntity.badRequest().body("이미 예약되어 있는 시간대입니다.");
        }
        long id = insertReservation(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    private boolean existsReservation(LocalDate date, LocalTime time) {
        String sql = "SELECT count(*) FROM reservations WHERE date=? and time=?";
        return 0 < jdbcTemplate.queryForObject(sql, Integer.class, date, time);
    }

    private long insertReservation(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO reservations(date, time, name) VALUES(?, ?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setObject(1, reservation.getDate());
            preparedStatement.setObject(2, reservation.getTime());
            preparedStatement.setString(3, reservation.getName());
            return preparedStatement;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getReservations(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {

        String sql = "SELECT * FROM reservations WHERE date=?";
        List<Reservation> customers = jdbcTemplate.query(sql,
                (rs, rowNum) -> new Reservation(
                        rs.getLong("id"),
                        rs.getObject("date", LocalDate.class),
                        rs.getObject("time", LocalTime.class),
                        rs.getString("name")
                ), date);

        return ResponseEntity.ok(customers);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteReservation(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date, @RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime time) {
        String sql = "DELETE FROM reservations WHERE date=? and time=?";
        jdbcTemplate.update(sql, date, time);
        return ResponseEntity.noContent().build();
    }
}
