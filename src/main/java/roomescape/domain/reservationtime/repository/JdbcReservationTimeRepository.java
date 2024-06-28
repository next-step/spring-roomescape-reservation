package roomescape.domain.reservationtime.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservationtime.exception.ReservationTimeException;
import roomescape.domain.reservationtime.exception.ReservationTimeNotFoundException;
import roomescape.domain.reservationtime.model.ReservationTime;
import roomescape.domain.reservationtime.model.ReservationTimeId;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static roomescape.global.utils.DateTimeFormatUtils.toIsoLocal;

@Repository
@RequiredArgsConstructor
public class JdbcReservationTimeRepository implements ReservationTimeRepository {

    public static final String SELECT_RESERVATION_TIME_SQL = """
            select
                time_id,
                start_at,
                created_at
            from reservation_times""";

    public static final RowMapper<ReservationTime> RESERVATION_TIME_ROW_MAPPER =
            (rs, rowNum) -> ReservationTime.builder()
                    .id(new ReservationTimeId(rs.getLong("time_id")))
                    .startAt(LocalTime.parse(rs.getString("start_at")))
                    .createdAt(LocalDateTime.parse(rs.getString("created_at")))
                    .build();

    private final JdbcTemplate jdbcTemplate;

    @Override
    public ReservationTime save(final ReservationTime time) {
        if (Objects.isNull(time.getId())) {
            return insertWithKeyHolder(time);
        }

        updateAll(time);
        return getById(time.getId());
    }

    private void updateAll(final ReservationTime time) {
        final String updateSql = """
                update reservation_times set
                    start_at = ?,
                    created_at = ?
                where time_id = ?""";

        final int updateCount = jdbcTemplate.update(
                updateSql,
                toIsoLocal(time.getStartAt()),
                toIsoLocal(time.getCreatedAt()),
                time.getIdValue()
        );

        if (updateCount != 1) {
            throw new ReservationTimeException(
                    "Error occurred while updating ReservationTime where time_id=%d. Affected row is not 1 but %d."
                            .formatted(time.getIdValue(), updateCount)
            );
        }
    }

    private ReservationTime insertWithKeyHolder(final ReservationTime time) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String insertSql = "insert into reservation_times (start_at, created_at) values (?, ?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertSql, new String[]{"time_id"});
            ps.setString(1, toIsoLocal(time.getStartAt()));
            ps.setString(2, toIsoLocal(time.getCreatedAt()));
            return ps;
        }, keyHolder);

        final long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return ReservationTime.builder()
                .id(new ReservationTimeId(generatedId))
                .startAt(time.getStartAt())
                .createdAt(time.getCreatedAt())
                .build();
    }

    @Override
    public ReservationTime getById(final ReservationTimeId timeId) {
        return findById(timeId).orElseThrow(() -> ReservationTimeNotFoundException.fromId(timeId));
    }

    @Override
    public Optional<ReservationTime> findById(final ReservationTimeId timeId) {
        return queryForReservationTime(
                generateSelectSqlWithWhereClause("where time_id = ?"),
                timeId.getValue()
        );
    }

    @Override
    public List<ReservationTime> findAll() {
        return jdbcTemplate.query(SELECT_RESERVATION_TIME_SQL, RESERVATION_TIME_ROW_MAPPER);
    }

    @Override
    public Optional<ReservationTime> findByStartAt(final LocalTime startAt) {
        return queryForReservationTime(
                generateSelectSqlWithWhereClause("where start_at = ?"),
                toIsoLocal(startAt)
        );
    }

    @Override
    public ReservationTime getByStartAt(final LocalTime startAt) {
        return findByStartAt(startAt).orElseThrow(() -> ReservationTimeNotFoundException.fromStartAt(startAt));
    }

    @Override
    public void delete(final ReservationTimeId timeId) {
        final String deleteSql = "delete from reservation_times t where t.time_id = ?";
        jdbcTemplate.update(deleteSql, timeId.getValue());
    }

    private String generateSelectSqlWithWhereClause(String whereClause) {
        return SELECT_RESERVATION_TIME_SQL + " " + whereClause;
    }

    private Optional<ReservationTime> queryForReservationTime(final String selectSql, Object... objects) {
        try {
            final ReservationTime time = jdbcTemplate.queryForObject(
                    selectSql,
                    RESERVATION_TIME_ROW_MAPPER,
                    objects
            );
            return Optional.ofNullable(time);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteAllInBatch() {
        jdbcTemplate.execute("delete from reservation_times");
    }
}
