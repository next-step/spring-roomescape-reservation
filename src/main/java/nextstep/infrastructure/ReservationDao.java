package nextstep.infrastructure;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.domain.Reservation;
import nextstep.domain.repository.ReservationRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer save(Reservation reservation) {
        return jdbcTemplate.update(
            "insert into reservation (date, time, name) values (?, ?, ?)",
            reservation.getDate(),
            reservation.getTime(),
            reservation.getName()
        );
    }

    @Override
    public List<Reservation> findAllBy(LocalDate date) {
        return jdbcTemplate.query(
            "select id, date, time, name from reservation where date = ?",
            (rs, rowNum) -> new Reservation(
                rs.getLong("id"),
                rs.getString("date"),
                rs.getString("time"),
                rs.getString("name")
            ),
            date
        );
    }

    @Override
    public void delete(LocalDate date, LocalTime time) {
        jdbcTemplate.update("delete from reservation where date = ? and time = ?", date, time);
    }
}
