package nextstep.persistence.reservation;

import nextstep.domain.reservation.model.Reservation;
import nextstep.domain.reservation.model.ReservationRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationJdbcRepository implements ReservationRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertActor;
    private final RowMapper<Reservation> rowMapper;

    public ReservationJdbcRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
        this.rowMapper = (resultSet, rowNumber) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getLong("schedule_id"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime(),
                resultSet.getString("name"));
    }

    @Override
    public Reservation save(Reservation reservation) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("schedule_id", reservation.getScheduleId())
                .addValue("date", reservation.getDate())
                .addValue("time", reservation.getTime())
                .addValue("name", reservation.getName());
        Long id = insertActor.executeAndReturnKey(params).longValue();

        return new Reservation(id, reservation.getScheduleId(), reservation.getDate(), reservation.getTime(), reservation.getName());
    }

    @Override
    public Boolean existByDateTime(LocalDate date, LocalTime time) {
        String sql = "SELECT EXISTS(SELECT * FROM reservation WHERE date = ? AND time = ?)";
        Boolean exists = jdbcTemplate.queryForObject(sql, Boolean.class, date, time);

        return exists;
    }

    @Override
    public List<Reservation> findAllByDate(LocalDate date) {
        String sql = "SELECT * FROM reservation WHERE date = ?";

        return jdbcTemplate.query(sql, rowMapper, date);
    }

    @Override
    public void deleteByDateTime(LocalDate date, LocalTime time) {
        String sql = "DELETE FROM reservation WHERE date = ? AND time = ?";

        jdbcTemplate.update(sql, date, time);
    }

    @Override
    public Boolean existByScheduleId(Long scheduleId) {
        String sql = "SELECT EXISTS(SELECT * FROM reservation WHERE schedule_id = ?)";
        Boolean exists = jdbcTemplate.queryForObject(sql, Boolean.class, scheduleId);

        return exists;
    }
}
