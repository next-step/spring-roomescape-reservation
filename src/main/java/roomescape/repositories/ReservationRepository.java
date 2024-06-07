package roomescape.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.entities.Reservation;

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
        String sql = "INSERT INTO RESERVATION(id, date, time, name) VALUES(?, ?, ?, ?)";
        jdbcTemplate.update(sql, reservation.getId(), reservation.getDate(), reservation.getTime(), reservation.getName());
        return reservation;
    }

    public List<Reservation> findAll(){
        String sql = "SELECT * FROM RESERVATION";
        List<Reservation> reservations = jdbcTemplate.query(sql, new RowMapper<Reservation>() {
            @Override
            public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
                Reservation reservation = new Reservation(
                    rs.getString("date"),
                    rs.getString("name"),
                    rs.getString("time")
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
