package controller;

import domain.Reserve;
import domain.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/admin/reservation")
    public String adminReservation() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    public List<Reserve> readReserve() {
        String sql = "select r.id as reservation_id," +
                "r.name as reservation_name," +
                "r.date as reservation_date," +
                "t.id as time_id," +
                "t.start_as time_start_at" +
                " from reservation as r " +
                "inner join reservation_time as t" +
                "on r.time_id = t.id";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> {
                    Reserve reserve = new Reserve(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("date"),
                            rs.getString("time")
                    );
                    return reserve;
                });
    }

    @GetMapping("/reservations/{id}")
    public List<Reserve> findReserve(@PathVariable Long id) {
        String sql = "select id, name, date, time from reservation where id = ?";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> {
                    Reserve reserve = new Reserve(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("date"),
                            rs.getString("time")
                    );
                    return reserve;
                }, id);
    }
    @PostMapping("/reservations")
    public void addReserve(@RequestBody Reserve reserve) {
        String sql = "insert into reservation(name, date, time) values (?, ?, ?)";
        jdbcTemplate.update(sql, reserve.getName(), reserve.getDate(), reserve.getTime());
    }

    @DeleteMapping("/reservations/{id}")
    public int deleteReserve(@PathVariable Long id) {
        String sql = "delete from reservation where id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @PostMapping("/times")
    public ResponseEntity<String> startTime(@RequestBody Time time) {
        String sql = "insert into reservation_time(start_at) values ?";
        jdbcTemplate.update(sql, time.getStartAt());
        return ResponseEntity.ok().body("success");
    }

    @GetMapping("/times")
    public List<Time> checkTime() {
        String sql = "select id, start_at from reservation_time";

        return jdbcTemplate.query(sql,
                (rs, rowNum) -> {
            Time time = new Time(
                    rs.getString("start_at"));
            return time;});
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> timeDelte(@PathVariable Long id) {
        String sql = "delete from reservation_time where id = ?";
        jdbcTemplate.update(sql, id);
        return ResponseEntity.ok().build();
    }
}
