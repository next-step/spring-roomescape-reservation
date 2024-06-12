package roomescape.reservationTime.service;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import roomescape.reservationTime.ReservationTime;

@Repository
public class ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public ReservationTime save(ReservationTime reservationTime) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO reservation_time(start_at) VALUES (?)", new String[]{"id"});
            ps.setString(1, reservationTime.getStartAt().toString());

            return ps;
        }, keyHolder);

        return new ReservationTime(keyHolder.getKey().longValue(),
            reservationTime.getStartAt());
    }

    public ReservationTime findById(Long id) {
        RowMapper<ReservationTime> rowMapper = (rs, rowNum) -> new ReservationTime(
            rs.getLong(1),
            rs.getTime(2).toLocalTime());

        return DataAccessUtils.singleResult(
            jdbcTemplate.query("SELECT id, start_at FROM reservation_time where id = ?",
                rowMapper, id));
    }

    public List<ReservationTime> find() {
        return jdbcTemplate.query("SELECT id, start_at FROM reservation_time",
            (rs, rowNum) ->
                new ReservationTime(rs.getLong("id"),
                    rs.getTime("start_at").toLocalTime()));
    }

    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM reservation_time WHERE id = ?", id);
    }
}
