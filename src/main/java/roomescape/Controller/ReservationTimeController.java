package roomescape.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.DTO.ReservationTimeRequest;
import roomescape.DTO.ReservationTimeResponse;
import roomescape.Entity.ReservationTime;

import java.sql.PreparedStatement;
import java.util.List;

@Controller
public class ReservationTimeController {
    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<ReservationTime> reservationTimeRowMapper = (resultSet, rowNum) -> {
        return new ReservationTime(
          resultSet.getLong("id"),
          resultSet.getString("start_at")
        );
    };

    @GetMapping("/admin/time")
    public String showAdminPage() {
        return "admin/time";
    }

    @GetMapping("times")
    public ResponseEntity<List<ReservationTimeResponse>> read() {
        String sql = "select * from reservation_time";
        List<ReservationTime> reservationTimes = jdbcTemplate.query(sql, reservationTimeRowMapper);
        return ResponseEntity.ok().body(ReservationTimeResponse.toDTOList(reservationTimes));
    }

    @PostMapping("times")
    public ResponseEntity<ReservationTimeResponse> create(@RequestBody ReservationTimeRequest request) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connect -> {
                String sql = "insert into reservation_time (start_at) values (?)";
                PreparedStatement ps = connect.prepareStatement(sql, new String[]{"id"});
                ps.setString(1, request.getStartAt());
                return ps;
            }, keyHolder);
            Long createId = keyHolder.getKey().longValue();

            String sql = "select * from reservation_time where id = ?";
            ReservationTime reservationTime = jdbcTemplate.queryForObject(sql, reservationTimeRowMapper, createId);
            return ResponseEntity.ok().body(new ReservationTimeResponse(reservationTime));
        }
        catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("times/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        String sql = "delete from reservation_time where id = ?";
        long deleteCount = jdbcTemplate.update(sql, id);

        if (deleteCount == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
