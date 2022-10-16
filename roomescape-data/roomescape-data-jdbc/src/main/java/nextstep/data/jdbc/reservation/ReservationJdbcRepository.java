package nextstep.data.jdbc.reservation;

import nextstep.domain.reservation.domain.model.Reservation;
import nextstep.domain.reservation.domain.model.ReservationRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationJdbcRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation save(Reservation reservation) {
        return null;
    }

    @Override
    public List<Reservation> findAllByDate(LocalDate date) {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void deleteByDateAndTime(LocalDate date, LocalTime time) {

    }

    @Override
    public Optional<Reservation> findByDateTime(LocalDateTime dateTime) {
        return Optional.empty();
    }
}
