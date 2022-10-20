package nextstep.infra.h2;

import nextstep.core.Reservation;
import nextstep.core.ReservationRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Primary
@Repository
public class ReservationH2Repository implements ReservationRepository {
    private static final RowMapper<Reservation> ROW_MAPPER = (resultSet, rowNum) -> new Reservation(
            resultSet.getLong("id"),
            resultSet.getDate("date").toLocalDate(),
            resultSet.getTime("time").toLocalTime(),
            resultSet.getString("name")
    );
    private final JdbcTemplate template;

    public ReservationH2Repository(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Reservation save(Reservation reservation) {
        Objects.requireNonNull(reservation);

        String query = "INSERT INTO reservation(date, time, name) VALUES (?, ?, ?)";
        template.update(query, reservation.getDate(), reservation.getTime(), reservation.getName());

        Long id = template.queryForObject("SELECT last_insert_id()", Long.class);
        return new Reservation(id, reservation.getDate(), reservation.getTime(), reservation.getName());
    }

    @Override
    public List<Reservation> findAllByDate(LocalDate date) {
        Objects.requireNonNull(date);

        String query = "SELECT * FROM reservation WHERE date = ?";
        return template.query(query, ROW_MAPPER, date);
    }

    @Override
    public void deleteByDateAndTime(LocalDate date, LocalTime time) {
        Objects.requireNonNull(date);
        Objects.requireNonNull(time);

        String query = "DELETE FROM reservation WHERE date = ? AND time = ?";
        template.update(query, date, time);
    }

    @Override
    public List<Reservation> findAll() {
        String query = "SELECT * FROM reservation";
        return template.query(query, ROW_MAPPER);
    }
}
