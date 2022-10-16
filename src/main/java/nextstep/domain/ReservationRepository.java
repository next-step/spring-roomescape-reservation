package nextstep.domain;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public Reservation save(Reservation reservation) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(reservation);
        Long id = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        return new Reservation(id, reservation);
    }

    public List<Reservation> findAllByDate(LocalDate date) {
        String sql = "select * from reservation where date = ?";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getLong("schedule_id"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getTime("time").toLocalTime(),
                        resultSet.getString("name")
                ),
                date);
    }

    public void deleteByDateAndTime(LocalDate date, LocalTime time) {
        String sql = "delete from reservation where date = ? and time = ?";
        jdbcTemplate.update(sql, date, time);
    }

    public boolean existsByScheduleId(Long scheduleId) {
        return countByScheduleId(scheduleId) > 0;
    }

    private int countByScheduleId(Long scheduleId) {
        String sql = "select count(*) from reservation where schedule_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, scheduleId);
    }
}
