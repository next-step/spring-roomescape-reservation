package roomescape.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.entities.Reservation;
import roomescape.entities.ReservationTime;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Reservation save(Reservation reservation){
        String sql = "INSERT INTO RESERVATION(date, time_id, name) VALUES(?, ?, ?)";
        jdbcTemplate.update(sql, reservation.getDate(), reservation.getTime().getId(), reservation.getName());
        return reservation;
    }

    public List<Reservation> findAll(){
        String sql = "SELECT \n" +
                "    r.id as reservation_id, \n" +
                "    r.name as reservation_name, \n" +
                "    r.date as reservation_date, \n" +
                "    t.id as time_id, \n" +
                "    t.start_at as time_start_at \n" +
                "FROM reservation as r \n" +
                "inner join reservation_time as t \n" +
                "on r.time_id = t.id\n";
        List<Reservation> reservations = jdbcTemplate.query(sql, new RowMapper<Reservation>() {
            @Override
            public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
                Reservation reservation = new Reservation(
                    rs.getLong("reservation_id"),
                    rs.getString("date"),
                    rs.getString("name"),
                    new ReservationTime(rs.getString("time_start_at"))
                );
                reservation.setId(rs.getLong("id"));
                return reservation;
            }
        });
        return reservations;
    }

    public void deleteById(Long id){
        String sql = "DELETE FROM RESERVATION WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
