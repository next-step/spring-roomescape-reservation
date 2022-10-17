package nextstep.domain;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class Reservations {
    private static final RowMapper<Reservation> RESERVATION_ROW_MAPPER = (resultSet, rowNum) -> new Reservation(
        resultSet.getDate("reservation_date").toLocalDate(),
        resultSet.getTime("reservation_time").toLocalTime(),
        resultSet.getString("name")
    );

    private final List<Reservation> reservations;
    private final JdbcTemplate jdbcTemplate;

    public Reservations(JdbcTemplate jdbcTemplate) {
        this.reservations = new ArrayList<>();
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(Reservation reservation) {
        try {
            jdbcTemplate.update("INSERT INTO reservation (name, reservation_date, reservation_time) VALUES (?, ?, ?)", reservation.getName(), reservation.getDate(), reservation.getTime());
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("예약할 수 없습니다.");
        }
    }

    public void remove(ReservationTime reservationTime) {
        jdbcTemplate.update("DELETE reservation WHERE reservation_date = ? AND reservation_time = ?", reservationTime.getDate(), reservationTime.getTime());
    }

    public List<Reservation> findBy(LocalDate targetDate) {
        return jdbcTemplate.query("SELECT * FROM reservation WHERE reservation_date = ?", RESERVATION_ROW_MAPPER, targetDate);
    }
}
