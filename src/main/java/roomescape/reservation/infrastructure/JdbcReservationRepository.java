package roomescape.reservation.infrastructure;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.repository.ReservationRepository;
import roomescape.theme.domain.Theme;
import roomescape.time.domain.ReservationTime;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Reservation save(Reservation reservation) {
        SqlParameterSource paramsReservation = new MapSqlParameterSource()
                .addValue("name", reservation.getName())
                .addValue("date", reservation.getDate())
                .addValue("time_id", reservation.getTime().getId())
                .addValue("theme_id", reservation.getTheme().getId());
        Long reservationId = simpleJdbcInsert.executeAndReturnKey(paramsReservation).longValue();
        return new Reservation(reservationId, reservation.getName(), reservation.getDate(), reservation.getTime(), reservation.getTheme());
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT r.id, r.name, r.date, t.id as time_id, t.start_at,"
                + " th.id as theme_id, th.name as theme_name, th.description, th.thumbnail"
                + " FROM reservation as r"
                + " INNER JOIN reservation_time as t"
                + " ON r.time_id = t.id"
                + " INNER JOIN theme as th"
                + " ON r.theme_id = th.id";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                new ReservationTime(
                        resultSet.getLong("time_id"),
                        resultSet.getString("start_at")
                ),
                new Theme(
                        resultSet.getLong("theme_id"),
                        resultSet.getString("theme_name"),
                        resultSet.getString("description"),
                        resultSet.getString("thumbnail")
                )
        ));
    }

    @Override
    public void deleteById(Long reservationId) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, reservationId);
    }

    @Override
    public boolean existsById(Long reservationId) {
        String sql = "SELECT COUNT(*) FROM reservation WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, reservationId);
        return count != null && count.equals(1);
    }

    @Override
    public boolean existsByReservationTimeId(Long reservationTimeId) {
        String sql = "SELECT COUNT(*) FROM reservation WHERE time_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, reservationTimeId);
        System.out.println("count = " + count);
        return count != null && count.equals(1);
    }
}
