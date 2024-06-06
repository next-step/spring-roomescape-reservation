package roomescape.apply.time.domain.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.apply.time.domain.ReservationTime;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Repository
public class ReservationTimeJDBCRepository implements ReservationTimeRepository {

    private static final String INSERT_SQL = """
        INSERT INTO reservation_time(start_at)
         VALUES (?)
    """;

    private static final String SELECT_ALL_SQL = """
        SELECT
            id,
            start_at
        FROM
            reservation_time
    """;

    private static final String CHECK_ID_EXISTS_SQL = """
        SELECT
            id
        FROM
            reservation_time
        WHERE
            id = ?
    """;

    private static final String SELECT_ONE_SQL = """
        SELECT
            id,
            start_at
        FROM
            reservation_time
        WHERE
            id = ?
    """;

    private static final String DELETE_SQL = """
        DELETE FROM reservation_time
        WHERE id = ?
    """;

    private final JdbcTemplate template;

    public ReservationTimeJDBCRepository(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public ReservationTime save(ReservationTime reservationTime) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
            ps.setString(1, reservationTime.getStartAt());
            return ps;
        }, keyHolder);

        long key = Objects.requireNonNull(keyHolder.getKey()).longValue();
        reservationTime.changeId(key);

        return reservationTime;
    }

    @Override
    public List<ReservationTime> findAll() {
        return template.query(SELECT_ALL_SQL, reservationTimeRowMapper());
    }

    @Override
    public void delete(Long id) {
        template.update(DELETE_SQL, id);
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
    public Optional<ReservationTime> findById(long timeId) {
        try {
            ReservationTime reservationTime = template.queryForObject(SELECT_ONE_SQL, reservationTimeRowMapper(), timeId);
            return Optional.ofNullable(reservationTime);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    private RowMapper<ReservationTime> reservationTimeRowMapper() {
        return (rs, rowNum) -> new ReservationTime(rs.getLong("id"), rs.getString("start_at"));
    }
}