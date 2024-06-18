package roomescape.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.entities.Reservation;
import roomescape.entities.ReservationTime;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
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
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    new String[]{"id"});
            ps.setString(1, reservation.getDate());
            ps.setLong(2, reservation.getReservationTime().getId());
            ps.setString(3, reservation.getName());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        return new Reservation(keyHolder.getKey().longValue(), reservation.getDate(), reservation.getName(), reservation.getReservationTime());
    }

    public List<Reservation> findAll(){
        String sql = """
                      select r.id as reservation_id,
                      r.name as reservation_name,
                      r.date as reservation_date,
                      t.time as time
               from reservation as r
               inner join reservation_time as t
               on r.time_id = t.id
            """;

        List<Reservation> reservations = jdbcTemplate.query(sql, new RowMapper<Reservation>() {
            @Override
            public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
                System.out.println(rs);
                return new Reservation( //TODO
                    rs.getLong("id"),
                    rs.getString("date"),
                    rs.getString("name"),
                    new ReservationTime(rs.getLong("id"), rs.getString("time"))
                );
            }
        });
        return reservations;
    }

    public void deleteById(Long id){
        String sql = "DELETE FROM RESERVATION WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
