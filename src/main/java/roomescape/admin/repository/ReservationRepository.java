package roomescape.admin.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.admin.dto.SaveReservationRequest;
import roomescape.admin.entity.Reservation;
import roomescape.admin.entity.ReservationTime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        return this.jdbcTemplate.query(ReservationQuery.FIND_ALL, readReservationRowMapper());
    }

    public Reservation findById(Long id) {
        return this.jdbcTemplate.queryForObject(ReservationQuery.FIND_BY_ID, readReservationRowMapper(),id);
    }

    private RowMapper<Reservation> readReservationRowMapper() {
        return (resultSet, rowNum) -> mapToReservation(resultSet);
    }

    public Reservation mapToReservation(ResultSet rs) throws SQLException {
        ReservationTime reservationTime = mapToReservationTime(rs);
        long id = rs.getLong("reservation_id");
        String name = rs.getString("reservation_name");
        String date = rs.getString("reservation_date");

        return Reservation.of(id, name, date, reservationTime);
    }

    private ReservationTime mapToReservationTime(ResultSet rs) throws SQLException {
        long id = rs.getLong("time_id");
        String startAt = rs.getString("time_start_at");

        return ReservationTime.of(id, startAt);
    }

    public Long save(SaveReservationRequest saveReservationRequest) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(con -> saveReserveStatement(con, saveReservationRequest), keyHolder);
        return keyHolder.getKey().longValue();
    }

    private PreparedStatement saveReserveStatement(Connection con, SaveReservationRequest saveReservationRequest) throws SQLException {
        String name = saveReservationRequest.name();
        String date = saveReservationRequest.date();
        Long timeId = saveReservationRequest.timeId();

        PreparedStatement ps = con.prepareStatement(ReservationQuery.SAVE, new String[]{"id"});
        ps.setString(1, name);
        ps.setString(2, date);
        ps.setLong(3,timeId);

        return ps;
    }

    public void delete(Long id) {
        this.jdbcTemplate.update(ReservationQuery.DELETE, id);
    }
}