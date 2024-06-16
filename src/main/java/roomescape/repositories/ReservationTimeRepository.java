package roomescape.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.entities.ReservationTime;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ReservationTimeRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeRepository(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public ReservationTime save(ReservationTime reservationTime){
        String sql = "INSERT INTO RESERVATION_TIME(time) VALUES(?)";

        jdbcTemplate.update(sql, reservationTime.getTime());
        return reservationTime;
    }

    public List<ReservationTime> findAll(){
        String sql = "SELECT * FROM RESERVATION_TIME";
        List<ReservationTime> times = jdbcTemplate.query(sql, new RowMapper<ReservationTime>() {
            @Override
            public ReservationTime mapRow(ResultSet rs, int rowNum) throws SQLException {
                ReservationTime time = new ReservationTime(
                        rs.getLong("id"),
                        rs.getString("time")
                );
                return time;
            }
        });
        return times;
    }

    public void deleteById(Long id){
        String sql = "DELETE FROM RESERVATION_TIME WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public ReservationTime findByTime(String time){
        String sql = "SELECT * FROM RESERVATION_TIME WHERE time = ?";
        return jdbcTemplate.queryForObject(sql, new RowMapper<ReservationTime>() {
            @Override
            public ReservationTime mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ReservationTime(
                        rs.getLong("id"),
                        rs.getString("time")
                );
            }
        }, time);
    }
}
