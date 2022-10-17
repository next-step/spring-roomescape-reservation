package nextstep.data.jdbc.reservation;

import nextstep.domain.reservation.domain.model.Reservation;
import nextstep.domain.reservation.domain.model.ReservationRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ReservationJdbcRepository implements ReservationRepository {
    private static final RowMapper<Reservation> RESERVATION_ROW_MAPPER = (rs, rowNum) -> new Reservation(
            rs.getLong("id"),
            rs.getObject("date", LocalDate.class),
            rs.getObject("time", LocalTime.class),
            rs.getString("name")
    );

    private final JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert simpleJdbcInsert;


    public ReservationJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void init() {
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("reservation").usingGeneratedKeyColumns("id");
    }

    @Override
    public Reservation save(Reservation reservation) {
        long id = simpleJdbcInsert.executeAndReturnKey(
                Map.of(
                        "date", reservation.date(),
                        "time", reservation.time(),
                        "name", reservation.name()
                )
        ).longValue();

        return reservation.withId(id);
    }

    @Override
    public List<Reservation> findAllByDate(LocalDate date) {
        return jdbcTemplate.query(
                "SELECT * FROM reservation WHERE date=?",
                RESERVATION_ROW_MAPPER,
                date
        );
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("TRUNCATE TABLE reservation");
    }

    @Override
    public void deleteByDateAndTime(LocalDate date, LocalTime time) {
        jdbcTemplate.update("DELETE FROM reservation WHERE date=? AND time=?", date, time);
    }

    @Override
    public Optional<Reservation> findByDateTime(LocalDateTime dateTime) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "SELECT * FROM reservation WHERE date=? AND time=?",
                            RESERVATION_ROW_MAPPER,
                            dateTime.toLocalDate(), dateTime.toLocalTime()
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
