package controller;

import domain.Reserve;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {
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
        String sql = "select id, name, date, time from reservation";
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
}
