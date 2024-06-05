package roomescape.apply.reservation.domain.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.apply.reservation.domain.Reservation;
import roomescape.apply.time.domain.ReservationTime;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ReservationJDBCRepository implements ReservationRepository {

    private static final String INSERT_SQL = """
        INSERT INTO reservation (name, date, time_id)
        VALUES (?, ?, ?)
    """;

    private static final String SELECT_ALL_SQL = """
        SELECT
            r.id as reservation_id,
            r.name as reservation_name,
            r.date as reservation_date,
            t.id as time_id,
            t.start_at as time_start_at
        FROM reservation as r
        inner join reservation_time as t
            on r.time_id = t.id
    """;

    private static final String CHECK_ID_EXISTS_SQL = """
        SELECT
            id
        FROM
            reservation
        WHERE
            id = ?
    """;

    private static final String DELETE_SQL = """
        DELETE FROM reservation
        WHERE id = ?
    """;

    private final JdbcTemplate template;


    public ReservationJDBCRepository(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Reservation save(Reservation reservation) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate());
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);

        long key = Objects.requireNonNull(keyHolder.getKey()).longValue();
        reservation.changeId(key);

        return reservation;
    }

    @Override
    public List<Reservation> findAll() {
        return template.query(SELECT_ALL_SQL, reservationRowMapper());
    }

    @Override
    public Optional<Long> checkIdExists(long id) {
        try {
            Long reservation = template.queryForObject(CHECK_ID_EXISTS_SQL, Long.class, id);
            return Optional.ofNullable(reservation);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public void delete(long id) {
        template.update(DELETE_SQL, id);
    }

    private RowMapper<Reservation> reservationRowMapper() {
        return (rs, rowNum) -> {
            long timeId = rs.getLong("time_id");
            String timeStartAt = rs.getString("time_start_at");
            ReservationTime reservationTime = new ReservationTime(timeId, timeStartAt);

            return new Reservation(
                    rs.getLong("reservation_id"),
                    rs.getString("reservation_name"),
                    rs.getString("reservation_date"),
                    reservationTime
            );
        };
    }
}