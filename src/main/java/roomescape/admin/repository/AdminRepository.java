package roomescape.admin.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.admin.dto.SaveReservationRequest;
import roomescape.admin.dto.SaveReservationTimeRequest;
import roomescape.admin.entity.Reservation;
import roomescape.admin.entity.ReservationTime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AdminRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<ReservationTime> readReservationTime() {
        return this.jdbcTemplate.query(ReservationTimeQuery.READ_ALL, readReservationTimeRowMapper());
    }

    public ReservationTime readReservationTimeById(Long id) {
        return this.jdbcTemplate.queryForObject(ReservationTimeQuery.READ_BY_ID, readReservationTimeRowMapper(),id);
    }

    private RowMapper<ReservationTime> readReservationTimeRowMapper() {
        return (resultSet, rowNum) -> ReservationTime.read(resultSet);
    }

    public Long saveReservationTime(SaveReservationTimeRequest saveReservationTimeRequest) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(con -> saveReserveTimeStatement(con, saveReservationTimeRequest), keyHolder);
        long id = keyHolder.getKey().longValue();

        return id;
    }

    private PreparedStatement saveReserveTimeStatement(Connection con, SaveReservationTimeRequest saveReservationTimeRequest) throws SQLException {
        String startAt = saveReservationTimeRequest.startAt();
        PreparedStatement ps = con.prepareStatement(ReservationTimeQuery.SAVE, new String[]{"id"});
        ps.setString(1, startAt);

        return ps;
    }

    public void deleteReservation(Long id) {
        this.jdbcTemplate.update(ReservationQuery.DELETE, id);
    }

    public List<Reservation> readReservation() {
        return this.jdbcTemplate.query(ReservationQuery.READ_ALL, readReservationRowMapper());
    }

    public Reservation readReservationById(Long id) {
        return this.jdbcTemplate.queryForObject(ReservationQuery.READ_BY_ID, readReservationRowMapper(),id);
    }

    private RowMapper<Reservation> readReservationRowMapper() {
        return (resultSet, rowNum) -> Reservation.read(resultSet);
    }

    public Long saveReservation(SaveReservationRequest saveReservationRequest) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(con -> saveReserveStatement(con, saveReservationRequest), keyHolder);
        long id = keyHolder.getKey().longValue();

        return id;
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

    public void deleteReservationTime(Long id) {
        this.jdbcTemplate.update(ReservationTimeQuery.DELETE, id);
    }
}