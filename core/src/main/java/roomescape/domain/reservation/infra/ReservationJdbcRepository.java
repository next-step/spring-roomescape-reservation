package roomescape.domain.reservation.infra;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.domain.Reservation;
import roomescape.domain.reservation.domain.ReservationDate;
import roomescape.domain.reservation.domain.ReservationGuestName;
import roomescape.domain.reservation.domain.ReservationStatus;
import roomescape.domain.reservationtime.domain.ReservationTimeId;
import roomescape.domain.theme.domain.ThemeId;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static roomescape.global.utils.DateTimeFormatUtils.toIsoLocal;


@Slf4j
@Repository
public class ReservationJdbcRepository {

    private static final String SELECT_RESERVATION_SQL = """
            select
                r.reservation_id,
                r.theme_id,
                r.date,
                r.time_id,
                r.name,
                r.status,
                r.reserved_at,
                r.canceled_at,
            from reservations r""";

    private static final RowMapper<Reservation> RESERVATION_ROW_MAPPER = (rs, rowNum) ->
            Reservation.builder()
                    .id(rs.getLong("reservation_id"))
                    .themeId(new ThemeId(rs.getLong("theme_id")))
                    .date(new ReservationDate(LocalDate.parse(rs.getString("date"))))
                    .timeId(new ReservationTimeId(rs.getLong("time_id")))
                    .name(new ReservationGuestName(rs.getString("name")))
                    .status(ReservationStatus.valueOf(rs.getString("status")))
                    .reservedAt(LocalDateTime.parse(rs.getString("reserved_at")))
                    .canceledAt(Objects.isNull(rs.getString("canceled_at")) ? null : LocalDateTime.parse(rs.getString("canceled_at")))
                    .build();

    private final JdbcTemplate jdbcTemplate;

    public ReservationJdbcRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Reservation save(final Reservation reservation) {
        if (Objects.nonNull(reservation.getId())) {
            return updateAll(reservation);
        }

        return insertWithKeyHolder(reservation);
    }

    private Reservation updateAll(final Reservation reservation) {
        String updateSql = """
                update reservations set
                    theme_id = ?,
                    date = ?,
                    time_id = ?,
                    name = ?,
                    status = ?,
                    reserved_at = ?,
                    canceled_at = ?
                where reservation_id = ?""";

        jdbcTemplate.update(updateSql,
                reservation.getThemeId().value(),
                toIsoLocal(reservation.getDate().getValue()),
                reservation.getTimeId().value(),
                reservation.getName().getValue(),
                reservation.getStatus().name(),
                toIsoLocal(reservation.getReservedAt()),
                Objects.isNull(reservation.getCanceledAt()) ? null : toIsoLocal(reservation.getCanceledAt()),
                reservation.getId()
        );

        return reservation;
    }

    private Reservation insertWithKeyHolder(final Reservation reservation) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        final String insertSql = """
                insert into reservations (
                    theme_id,
                    date,
                    time_id,
                    name,
                    status,
                    reserved_at,
                    canceled_at
                ) values (?, ?, ?, ?, ?, ?, ?)""";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertSql, new String[]{"reservation_id"});
            ps.setLong(1, reservation.getThemeId().value());
            ps.setString(2, toIsoLocal(reservation.getDate().getValue()));
            ps.setLong(3, reservation.getTimeId().value());
            ps.setString(4, reservation.getName().getValue());
            ps.setString(5, reservation.getStatus().name());
            ps.setString(6, toIsoLocal(reservation.getReservedAt()));
            ps.setString(7, Objects.isNull(reservation.getCanceledAt()) ? null : toIsoLocal(reservation.getCanceledAt()));
            return ps;
        }, keyHolder);

        final long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return Reservation.builder()
                .id(generatedId)
                .themeId(reservation.getThemeId())
                .date(reservation.getDate())
                .timeId(reservation.getTimeId())
                .name(reservation.getName())
                .status(reservation.getStatus())
                .reservedAt(reservation.getReservedAt())
                .canceledAt(reservation.getCanceledAt())
                .build();
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query(SELECT_RESERVATION_SQL, RESERVATION_ROW_MAPPER);
    }

    public List<Reservation> findAllByTimeId(final ReservationTimeId timeId) {
        final String selectSql = generateSelectSqlWithWhereCondition("where r.time_id = ?");
        return jdbcTemplate.query(selectSql, RESERVATION_ROW_MAPPER, timeId.value());
    }

    public List<Reservation> findAllByThemeId(final ThemeId themeId) {
        final String selectSql = generateSelectSqlWithWhereCondition("where r.theme_id = ?");
        return jdbcTemplate.query(selectSql, RESERVATION_ROW_MAPPER, themeId.value());
    }

    public Optional<Reservation> findById(final Long reservationId) {
        final String selectSql = generateSelectSqlWithWhereCondition("where reservation_id = ?");
        return queryForReservation(selectSql, reservationId);
    }

    public Optional<Reservation> findBy(
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

    private Optional<Reservation> queryForReservation(final String selectSql, Object... objects) {
        try {
            final Reservation reservation = jdbcTemplate.queryForObject(
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
