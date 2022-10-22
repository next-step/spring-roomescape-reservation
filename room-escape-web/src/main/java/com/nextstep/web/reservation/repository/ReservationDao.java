package com.nextstep.web.reservation.repository;

import com.nextstep.web.reservation.repository.entity.ReservationEntity;
import nextsetp.domain.reservation.Reservation;
import org.springframework.cglib.core.Local;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ReservationDao {
    private static final String TABLE_NAME = "Reservation";
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final ReservationRowMapper rowMapper;

    public ReservationDao(NamedParameterJdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = new ReservationRowMapper();
        this.jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME);

    }

    public Long save(Reservation reservation) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("scheduleId", reservation.getScheduleId());
        parameters.put("reservationTime", reservation.getReservationTime());
        parameters.put("NAME", reservation.getName());

        return (long) jdbcInsert.execute(parameters);
    }

    public Optional<ReservationEntity> findBy(String date, String time) {
        String query = "SELECT * FROM RESERVATION WHERE date = :date AND time = :time";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("date", LocalDate.parse(date))
                .addValue("time", LocalTime.parse(time));
        return jdbcTemplate.query(query, namedParameters, rowMapper).stream().findFirst();
    }

    public List<ReservationEntity> findAllBy(List<Long> scheduleIds) {
        String query = "SELECT * FROM RESERVATION WHERE scheduleId IN :scheduleIds";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("scheduleId", scheduleIds);

        return jdbcTemplate.query(query, namedParameters, rowMapper);
    }

    public void delete(Long id) {
        String query = "DELETE FROM RESERVATION WHERE id = :id AND time = :time";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id);
        jdbcTemplate.update(query, namedParameters);
    }

    public class ReservationRowMapper implements RowMapper<ReservationEntity> {
        @Override
        public ReservationEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            ReservationEntity reservationEntity = new ReservationEntity(
                    rs.getLong("ID"),
                    rs.getLong("themeId"),
                    rs.getObject("reservationTime", LocalDateTime.class),
                    rs.getString("name")
            );
            return reservationEntity;
        }
    }
}
