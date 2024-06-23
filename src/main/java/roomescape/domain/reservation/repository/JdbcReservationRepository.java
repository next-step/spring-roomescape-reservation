package roomescape.domain.reservation.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.model.Reservation;
import roomescape.domain.reservation.model.ReservationDate;
import roomescape.domain.reservation.model.ReservationGuestName;
import roomescape.domain.reservation.model.ReservationStatus;
import roomescape.domain.reservationtime.model.ReservationTimeId;
import roomescape.domain.reservationtime.repository.JdbcReservationTimeRepository;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static roomescape.global.utils.DateTimeFormatUtils.toIsoLocal;

@Slf4j
@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private static final String SELECT_RESERVATION_SQL = """
            select
                r.reservation_id,
                r.name,
                r.date,
                r.status,
                r.canceled_at,
                r.created_at,
                t.time_id,
                t.start_at
            from reservations r
            inner join reservation_times t on r.time_id = t.time_id""";

    private static final RowMapper<Reservation> RESERVATION_ROW_MAPPER = (rs, rowNum) ->
            Reservation.builder()
                    .id(rs.getLong("reservation_id"))
                    .name(new ReservationGuestName(rs.getString("name")))
                    .date(new ReservationDate(LocalDate.parse(rs.getString("date"))))
                    .time(JdbcReservationTimeRepository.RESERVATION_TIME_ROW_MAPPER.mapRow(rs, rowNum))
                    .status(ReservationStatus.valueOf(rs.getString("status")))
                    .canceledAt(Objects.isNull(rs.getString("canceled_at")) ? null : LocalDateTime.parse(rs.getString("canceled_at")))
                    .createdAt(LocalDateTime.parse(rs.getString("created_at")))
                    .build();

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation save(final Reservation reservation) {
        if (Objects.nonNull(reservation.getId())) {
            return updateAll(reservation);
        }

        return insertWithKeyHolder(reservation);
    }

    private Reservation updateAll(final Reservation reservation) {
        String updateSql = """
                update reservations set
                    name = ?,
                    date = ?,
                    time_id = ?,
                    status = ?,
                    canceled_at = ?,
                    created_at = ?
                where reservation_id = ?""";

        jdbcTemplate.update(updateSql,
                reservation.getName().getValue(),
                toIsoLocal(reservation.getDate().getValue()),
                reservation.getTime().getIdValue(),
                reservation.getStatus().name(),
                Objects.isNull(reservation.getCanceledAt()) ? null : toIsoLocal(reservation.getCanceledAt()),
                toIsoLocal(reservation.getCreatedAt()),
                reservation.getId()
        );

        return reservation;
    }

    private Reservation insertWithKeyHolder(final Reservation reservation) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        final String insertSql = """
                insert into reservations (
                    name,
                    date,
                    time_id,
                    status,
                    canceled_at,
                    created_at
                ) values (?, ?, ?, ?, ?, ?)""";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertSql, new String[]{"reservation_id"});
            ps.setString(1, reservation.getName().getValue());
            ps.setString(2, toIsoLocal(reservation.getDate().getValue()));
            ps.setLong(3, reservation.getTime().getIdValue());
            ps.setString(4, reservation.getStatus().name());
            ps.setString(5, Objects.isNull(reservation.getCanceledAt()) ? null : toIsoLocal(reservation.getCanceledAt()));
            ps.setString(6, toIsoLocal(reservation.getCreatedAt()));
            return ps;
        }, keyHolder);

        final long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return Reservation.builder()
                .id(generatedId)
                .name(reservation.getName())
                .date(reservation.getDate())
                .time(reservation.getTime())
                .status(reservation.getStatus())
                .canceledAt(reservation.getCanceledAt())
                .createdAt(reservation.getCreatedAt())
                .build();
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query(SELECT_RESERVATION_SQL, RESERVATION_ROW_MAPPER);
    }

    @Override
    public Optional<Reservation> findById(final Long reservationId) {
        final String selectSql = generateSelectSqlWithWhereCondition("where reservation_id = ?");
        return queryForReservation(selectSql, reservationId);
    }

    @Override
    public Optional<Reservation> findBy(
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

    @Override
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
