package roomescape.db.core.reservation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.core.domain.common.ActiveStatus;
import roomescape.core.domain.reservation.ReservationDate;
import roomescape.core.domain.reservation.ReservationGuestName;
import roomescape.core.domain.reservationtime.ReservationTimeId;
import roomescape.db.core.reservatiotime.ReservationTimeJdbcRepository;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static roomescape.db.core.common.utils.DateTimeFormatUtils.toIsoLocal;


@Slf4j
@Repository
public class ReservationJdbcRepository {

    private static final String SELECT_RESERVATION_SQL = """
            select
                r.reservation_id,
                r.name,
                r.date,
                r.active_status,
                r.deleted_at,
                r.created_at,
                t.time_id,
                t.start_at
            from reservations r
            inner join reservation_times t on r.time_id = t.time_id""";

    private static final RowMapper<ReservationEntity> RESERVATION_ROW_MAPPER = (rs, rowNum) ->
            ReservationEntity.builder()
                    .id(rs.getLong("reservation_id"))
                    .name(rs.getString("name"))
                    .date(LocalDate.parse(rs.getString("date")))
                    .time(ReservationTimeJdbcRepository.RESERVATION_TIME_ROW_MAPPER.mapRow(rs, rowNum))
                    .activeStatus(ActiveStatus.valueOf(rs.getString("active_status")))
                    .deletedAt(Objects.isNull(rs.getString("deleted_at")) ? null : LocalDateTime.parse(rs.getString("deleted_at")))
                    .createdAt(LocalDateTime.parse(rs.getString("created_at")))
                    .build();

    private final JdbcTemplate jdbcTemplate;

    public ReservationJdbcRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationEntity save(final ReservationEntity reservation) {
        if (Objects.nonNull(reservation.getId())) {
            return updateAll(reservation);
        }

        return insertWithKeyHolder(reservation);
    }

    private ReservationEntity updateAll(final ReservationEntity reservation) {
        String updateSql = """
                update reservations set
                    name = ?,
                    date = ?,
                    time_id = ?,
                    active_status = ?,
                    deleted_at = ?,
                    created_at = ?
                where reservation_id = ?""";

        jdbcTemplate.update(updateSql,
                reservation.getName(),
                toIsoLocal(reservation.getDate()),
                reservation.getTime().getId(),
                reservation.getActiveStatus().name(),
                Objects.isNull(reservation.getDeletedAt()) ? null : toIsoLocal(reservation.getDeletedAt()),
                toIsoLocal(reservation.getCreatedAt()),
                reservation.getId()
        );

        return reservation;
    }

    private ReservationEntity insertWithKeyHolder(final ReservationEntity reservation) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        final String insertSql = """
                insert into reservations (
                    name,
                    date,
                    time_id,
                    active_status,
                    deleted_at,
                    created_at
                ) values (?, ?, ?, ?, ?, ?)""";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertSql, new String[]{"reservation_id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, toIsoLocal(reservation.getDate()));
            ps.setLong(3, reservation.getTime().getId());
            ps.setString(4, reservation.getActiveStatus().name());
            ps.setString(5, Objects.isNull(reservation.getDeletedAt()) ? null : toIsoLocal(reservation.getDeletedAt()));
            ps.setString(6, toIsoLocal(reservation.getCreatedAt()));
            return ps;
        }, keyHolder);

        final long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return ReservationEntity.builder()
                .id(generatedId)
                .name(reservation.getName())
                .date(reservation.getDate())
                .time(reservation.getTime())
                .activeStatus(reservation.getActiveStatus())
                .deletedAt(reservation.getDeletedAt())
                .createdAt(reservation.getCreatedAt())
                .build();
    }

    public List<ReservationEntity> findAll() {
        return jdbcTemplate.query(SELECT_RESERVATION_SQL, RESERVATION_ROW_MAPPER);
    }

    public List<ReservationEntity> findAllByTimeId(final ReservationTimeId timeId) {
        final String selectSql = generateSelectSqlWithWhereCondition("where t.time_id = ?");
        return jdbcTemplate.query(selectSql, RESERVATION_ROW_MAPPER, timeId.getValue());
    }

    public Optional<ReservationEntity> findById(final Long reservationId) {
        final String selectSql = generateSelectSqlWithWhereCondition("where reservation_id = ?");
        return queryForReservation(selectSql, reservationId);
    }

    public Optional<ReservationEntity> findBy(
            final ReservationGuestName name,
            final ReservationDate date,
            final ReservationTimeId timeId
    ) {
        return queryForReservation(
                generateSelectSqlWithWhereCondition("where r.name = ? and r.date = ? and r.time_id = ?"),
                name.getValue(),
                toIsoLocal(date.getValue()),
                timeId.getValue()
        );
    }

    public void deleteAllInBatch() {
        jdbcTemplate.execute("delete from reservations");
    }

    private String generateSelectSqlWithWhereCondition(final String whereConditionSql) {
        return SELECT_RESERVATION_SQL + " " + whereConditionSql;
    }

    private Optional<ReservationEntity> queryForReservation(final String selectSql, Object... objects) {
        try {
            final ReservationEntity reservation = jdbcTemplate.queryForObject(
                    selectSql,
                    RESERVATION_ROW_MAPPER,
                    objects
            );
            return Optional.ofNullable(reservation);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
