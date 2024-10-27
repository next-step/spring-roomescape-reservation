package roomescape.repository.reservation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.common.ActiveStatus;
import roomescape.domain.reservation.ReservationDate;
import roomescape.domain.reservation.ReservationGuestName;
import roomescape.domain.reservationtime.ReservationTimeId;
import roomescape.domain.theme.ThemeId;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static roomescape.repository.utils.DateTimeFormatUtils.toIsoLocal;


@Slf4j
@Repository
public class ReservationJdbcRepository {

    private static final String SELECT_RESERVATION_SQL = """
            select
                r.reservation_id,
                r.theme_id,
                r.time_id,
                r.name,
                r.date,
                r.active_status,
                r.deleted_at,
                r.created_at,
            from reservations r""";

    private static final RowMapper<ReservationEntity> RESERVATION_ROW_MAPPER = (rs, rowNum) ->
            ReservationEntity.builder()
                    .id(rs.getLong("reservation_id"))
                    .themeId(rs.getLong("theme_id"))
                    .timeId(rs.getLong("time_id"))
                    .name(rs.getString("name"))
                    .date(LocalDate.parse(rs.getString("date")))
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
                    theme_id = ?,
                    time_id = ?,
                    name = ?,
                    date = ?,
                    active_status = ?,
                    deleted_at = ?,
                    created_at = ?
                where reservation_id = ?""";

        jdbcTemplate.update(updateSql,
                reservation.getThemeId(),
                reservation.getTimeId(),
                reservation.getName(),
                toIsoLocal(reservation.getDate()),
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
                    theme_id,
                    name,
                    date,
                    time_id,
                    active_status,
                    deleted_at,
                    created_at
                ) values (?, ?, ?, ?, ?, ?, ?)""";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertSql, new String[]{"reservation_id"});
            ps.setLong(1, reservation.getThemeId());
            ps.setString(2, reservation.getName());
            ps.setString(3, toIsoLocal(reservation.getDate()));
            ps.setLong(4, reservation.getTimeId());
            ps.setString(5, reservation.getActiveStatus().name());
            ps.setString(6, Objects.isNull(reservation.getDeletedAt()) ? null : toIsoLocal(reservation.getDeletedAt()));
            ps.setString(7, toIsoLocal(reservation.getCreatedAt()));
            return ps;
        }, keyHolder);

        final long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return ReservationEntity.builder()
                .id(generatedId)
                .themeId(reservation.getThemeId())
                .name(reservation.getName())
                .date(reservation.getDate())
                .timeId(reservation.getTimeId())
                .activeStatus(reservation.getActiveStatus())
                .deletedAt(reservation.getDeletedAt())
                .createdAt(reservation.getCreatedAt())
                .build();
    }

    public List<ReservationEntity> findAll() {
        return jdbcTemplate.query(SELECT_RESERVATION_SQL, RESERVATION_ROW_MAPPER);
    }

    public List<ReservationEntity> findAllByTimeId(final ReservationTimeId timeId) {
        final String selectSql = generateSelectSqlWithWhereCondition("where r.time_id = ?");
        return jdbcTemplate.query(selectSql, RESERVATION_ROW_MAPPER, timeId.value());
    }

    public List<ReservationEntity> findAllByThemeId(final ThemeId themeId) {
        final String selectSql = generateSelectSqlWithWhereCondition("where r.theme_id = ?");
        return jdbcTemplate.query(selectSql, RESERVATION_ROW_MAPPER, themeId.value());
    }

    public Optional<ReservationEntity> findById(final Long reservationId) {
        final String selectSql = generateSelectSqlWithWhereCondition("where reservation_id = ?");
        return queryForReservation(selectSql, reservationId);
    }

    public List<ReservationEntity> findAllByActiveStatus(final ActiveStatus activeStatus) {
        final String selectSql = generateSelectSqlWithWhereCondition("where active_status = ?");
        return queryForReservations(selectSql, activeStatus.name());
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
                timeId.value()
        );
    }

    public void deleteAllInBatch() {
        jdbcTemplate.execute("delete from reservations");
    }

    private String generateSelectSqlWithWhereCondition(final String whereConditionSql) {
        return SELECT_RESERVATION_SQL + " " + whereConditionSql;
    }

    private List<ReservationEntity> queryForReservations(final String selectSql, final Object... objects) {
        return jdbcTemplate.query(selectSql, RESERVATION_ROW_MAPPER, objects);
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
