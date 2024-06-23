package roomescape.domain.reservation.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.model.Reservation;
import roomescape.domain.reservation.model.ReservationDateTime;
import roomescape.domain.reservation.model.ReservationGuestName;
import roomescape.domain.reservation.model.ReservationStatus;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private static final String SELECT_RESERVATION_SQL = """
            select
                id,
                name,
                datetime,
                status,
                canceled_at,
                created_at
            from reservations""";

    private static final RowMapper<Reservation> RESERVATION_ROW_MAPPER = (rs, rowNum) -> Reservation.builder()
            .id(rs.getLong("id"))
            .name(new ReservationGuestName(rs.getString("name")))
            .dateTime(new ReservationDateTime(rs.getTimestamp("datetime").toLocalDateTime()))
            .status(ReservationStatus.valueOf(rs.getString("status")))
            .canceledAt(Objects.isNull(rs.getTimestamp("canceled_at")) ? null : rs.getTimestamp("canceled_at").toLocalDateTime())
            .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
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
                    datetime = ?,
                    status = ?,
                    canceled_at = ?,
                    created_at = ?
                where id = ?""";

        jdbcTemplate.update(updateSql,
                reservation.getName().getValue(),
                Timestamp.valueOf(reservation.getDateTime().getValue()),
                reservation.getStatus().name(),
                reservation.getCanceledAt() == null ? null : Timestamp.valueOf(reservation.getCanceledAt()),
                Timestamp.valueOf(reservation.getCreatedAt()),
                reservation.getId()
        );

        return reservation;
    }

    private Reservation insertWithKeyHolder(final Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String insertSql = """
                insert into reservations (
                    name,
                    datetime,
                    status,
                    canceled_at,
                    created_at
                ) values (?, ?, ?, ?, ?)""";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertSql, new String[]{"id"});
            ps.setString(1, reservation.getName().getValue());
            ps.setTimestamp(2, Timestamp.valueOf(reservation.getDateTime().getValue()));
            ps.setString(3, reservation.getStatus().name());

            if (Objects.isNull(reservation.getCanceledAt())) {
                ps.setNull(4, Types.TIMESTAMP);
            } else {
                ps.setTimestamp(4, Timestamp.valueOf(reservation.getCanceledAt()));
            }

            ps.setTimestamp(5, Timestamp.valueOf(reservation.getCreatedAt()));
            return ps;
        }, keyHolder);

        final long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return Reservation.builder()
                .id(generatedId)
                .name(reservation.getName())
                .dateTime(reservation.getDateTime())
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
        final String selectSql = generateSelectSqlWithWhereCondition("where id = ?");
        return queryForReservation(selectSql, reservationId);
    }

    @Override
    public Optional<Reservation> findByNameAndDateTime(
            final ReservationGuestName name,
            final ReservationDateTime dateTime
    ) {
        final String selectSql = generateSelectSqlWithWhereCondition("where name = ? and dateTime = ?");
        return queryForReservation(selectSql, name.getValue(), dateTime.getValue());
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
