package roomescape.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.entities.ReservationTime;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ReservationTimeRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeRepository(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public ReservationTime save(ReservationTime reservationTime){
        String sql = "INSERT INTO RESERVATION_TIME(start_at) VALUES(?)";

        jdbcTemplate.update(sql, reservationTime.getStartAt());
        return reservationTime;
    }

    public List<ReservationTime> findAll(){
        String sql = "SELECT * FROM RESERVATION_TIME";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new ReservationTime(
                rs.getLong("id"),
                rs.getString("start_at")
        ));
    }

    public void deleteById(Long id){
        String sql = "DELETE FROM RESERVATION_TIME WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public ReservationTime findByStartAt(String startAt){
        String sql = "SELECT * FROM RESERVATION_TIME WHERE start_at = ?";
        return jdbcTemplate.queryForObject(
          sql, (rs, rowNum) -> new ReservationTime(
                rs.getLong("id"),
                rs.getString("start_at")
          ), startAt);
    }
}
