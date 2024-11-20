package roomescape.domain.reservationtime.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.common.exception.DataAccessException;
import roomescape.domain.reservationtime.domain.ReservationTime;
import roomescape.domain.reservationtime.domain.ReservationTimeId;
import roomescape.global.utils.DateTimeFormatUtils;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static roomescape.global.utils.DateTimeFormatUtils.toIsoLocal;


@Repository
@RequiredArgsConstructor
public class ReservationTimeJdbcRepository {

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
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ReservationTime save(final ReservationTime time) {
        if (Objects.isNull(time.getId())) {
            return insertWithKeyHolder(time);
        }

        updateAll(time);

        return time;
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
                time.getId().value()
        );

        if (updateCount != 1) {
            throw new DataAccessException(
                    "Error occurred while updating ReservationTime where time_id=%d. Affected row is not 1 but %d."
                            .formatted(time.getId(), updateCount)
            );
        }
    }

    public Optional<ReservationTime> findById(final Long timeId) {
        return queryForReservationTime(
                generateSelectSqlWithWhereClause("where time_id = ?"),
                timeId
        );
    }

    public List<ReservationTime> findAll() {
        return jdbcTemplate.query(SELECT_RESERVATION_TIME_SQL, RESERVATION_TIME_ROW_MAPPER);
    }

    public List<ReservationTime> findAllByIds(final List<Long> timeIds) {
        final String sql = SELECT_RESERVATION_TIME_SQL + " where time_id in (:ids)";

        final MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ids", timeIds);

        return namedParameterJdbcTemplate.query(sql, parameters, RESERVATION_TIME_ROW_MAPPER);
    }

    public Optional<ReservationTime> findByStartAt(final LocalTime startAt) {
        return queryForReservationTime(
                generateSelectSqlWithWhereClause("where start_at = ?"),
                DateTimeFormatUtils.toIsoLocal(startAt)
        );
    }

    public void delete(final Long timeId) {
        final String deleteSql = "delete from reservation_times t where t.time_id = ?";
        jdbcTemplate.update(deleteSql, timeId);
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

    public void deleteAllInBatch() {
        jdbcTemplate.execute("delete from reservation_times");
    }
}
