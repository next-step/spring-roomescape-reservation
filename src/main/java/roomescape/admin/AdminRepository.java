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
