package roomescape.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

import java.sql.*;
import java.util.List;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    private Reservation mapReservation(ResultSet rs, int rowNum) throws SQLException {
        Long reservationId = rs.getLong("reservation_id");
        String reservationName = rs.getString("reservation_name");
        String reservationDate = rs.getString("reservation_date");
        Long timeId = rs.getLong("time_id");
        String startAt = rs.getString("start_at");

        ReservationTime time = new ReservationTime(timeId, startAt);
        return new Reservation(reservationId, reservationName, reservationDate, time);
    }

    @Autowired
    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String sql = "SELECT r.id as reservation_id, r.name as reservation_name, r.date as reservation_date, " +
                "t.id as time_id, t.start_at as start_at " +
                "FROM reservation as r " +
                "INNER JOIN reservation_time as t " +
                "ON r.time_id = t.id";
        return jdbcTemplate.query(sql, this::mapReservation);
    }

    public Long save(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, reservation.getName());
                ps.setString(2, reservation.getDate());
                ps.setLong(3, reservation.getTime().getId());
                return ps;
            }
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

}
