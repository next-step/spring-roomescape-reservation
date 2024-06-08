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

    public ReservationTime save(ReservationTime time){
        String sql = "INSERT INTO RESERVATION_TIME(start_at) VALUES(?)";

        jdbcTemplate.update(sql, time.getStartAt());
        return time;
    }

    public List<ReservationTime> findAll(){
        String sql = "SELECT * FROM RESERVATION_TIME";
        List<ReservationTime> times = jdbcTemplate.query(sql, new RowMapper<ReservationTime>() {
            @Override
            public ReservationTime mapRow(ResultSet rs, int rowNum) throws SQLException {
                ReservationTime time = new ReservationTime(
                        rs.getString("start_at")
                );
                time.setId(rs.getLong("id"));
                return time;
            }
        });
        return times;
    }

    public void deleteById(Long id){
        String sql = "DELETE FROM RESERVATION_TIME WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
