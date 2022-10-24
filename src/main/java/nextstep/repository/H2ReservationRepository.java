package nextstep.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import nextstep.domain.Reservation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class H2ReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Reservation> mapper =
        (resultSet, rowNum) -> new Reservation(
            resultSet.getLong("id"),
            resultSet.getDate("date").toLocalDate(),
            resultSet.getTime("time").toLocalTime(),
            resultSet.getString("name")
        );

    public H2ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long save(LocalDate date, LocalTime time, String name) {
        final String sql = "insert into reservation (date, time, name) values (?, ?, ?)";

        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update((Connection con) -> {
            PreparedStatement pstmt = con.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
            );
            pstmt.setObject(1, date);
            pstmt.setObject(2, time);
            pstmt.setString(3, name);
            return pstmt;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public List<Reservation> findReservationsByDate(LocalDate date) {
        final String sql = "select * from reservation where date = ?";

        return jdbcTemplate.query(sql, mapper, date);
    }

    @Override
    public void deleteByLocalDateAndLocalTime(LocalDate date, LocalTime time) {
        final String sql = "delete from reservation where date = ? and time = ?";
        jdbcTemplate.update(sql, date, time);
    }

    @Override
    public boolean existReservationByDateAndTime(LocalDate date, LocalTime time) {
        final String sql = "select exists (select id from reservation where date = ? and time = ?)";
        return Objects.requireNonNull(jdbcTemplate.queryForObject(sql, Boolean.class, date, time));
    }
}
