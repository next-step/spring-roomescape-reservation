package roomescape.reservation;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;

@Component
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationEntity addReservation(ReservationEntity reservationEntity) {
        String sql = "insert into reservation (name, date, time) values(?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    sql,
                    new String[]{"id"});

            ps.setString(1, reservationEntity.getName());
            ps.setString(2, reservationEntity.getDate());
            ps.setString(3, reservationEntity.getTime());
            return ps;
        }, keyHolder);

        long generatedId = keyHolder.getKey().longValue();
        reservationEntity.setId(generatedId);

        return reservationEntity;
    }

    public void deleteReservation(Long id) {
        String sql = "delete from reservations where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<ReservationEntity> reservations() {
        String sql = "select id, name, date, time from reservation";
        return jdbcTemplate.query(sql, rowMapper);
    }

    private final RowMapper<ReservationEntity> rowMapper = (resultSet, rowNum) ->
        new ReservationEntity(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                resultSet.getString("time")
        );
}
