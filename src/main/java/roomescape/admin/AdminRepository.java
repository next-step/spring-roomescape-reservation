package roomescape.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AdminRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<ReservationTime> readReservationTime() {
        return this.jdbcTemplate.query("select id as time_id, start_at as time_start_at from reservation_time", readReservationTimeRowMapper());
    }

    public ReservationTime readReservationTimeById(Long id) {
        return this.jdbcTemplate.queryForObject("select id as time_id, start_at as time_start_at from reservation_time where id = ?", readReservationTimeRowMapper(),id);
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
        PreparedStatement ps = con.prepareStatement("insert into reservation_time(start_at) values(?)", new String[]{"id"});
        ps.setString(1, startAt);

        return ps;
    }

    public void deleteReservationTime(Long id) {
        this.jdbcTemplate.update("delete from reservation_time where id = ?", id);
    }

    public List<Reservation> readReservation() {
        String readQuery = "select id, name, date, time from reservation";
        List<Reservation> data = this.jdbcTemplate.query(readQuery, readRowMapper());
        return data;
    }

    private RowMapper<Reservation> readRowMapper() {
        return (resultSet, rowNum) -> Reservation.read(resultSet);
    }

    public Long saveReservation(SaveReservationRequest saveReservationRequest) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(con -> saveStatement(con, saveReservationRequest), keyHolder);
        long id = keyHolder.getKey().longValue();
        return id;
    }

    private PreparedStatement saveStatement(Connection con, SaveReservationRequest saveReservationRequest) throws SQLException {
        String saveQuery = "insert into reservation(name, date, time) values(?,?,?)";
        String name = saveReservationRequest.name();
        String date = saveReservationRequest.date();
        String time = saveReservationRequest.time();

        PreparedStatement ps = con.prepareStatement(saveQuery, new String[]{"id"});
        ps.setString(1, name);
        ps.setString(2, date);
        ps.setString(3, time);

        return ps;
    }

    public void deleteReservation(Long id) {
        String deleteQuery = "delete from reservation where id = ?";

        this.jdbcTemplate.update(deleteQuery, id);
    }
}
