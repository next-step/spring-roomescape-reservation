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
    private final static String TABLE_NAME = "reservation";
    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationEntity addReservation(ReservationEntity reservationEntity) {
        String sql = String.format("insert into %s (name, date, time) values(?,?,?)", TABLE_NAME);

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
        String sql = String.format("delete from %s where id = ?",TABLE_NAME);
        jdbcTemplate.update(sql, id);
    }

    public List<ReservationEntity> reservations() {
        String sql = String.format("select id, name, date, time from %s", TABLE_NAME);
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
