package roomescape.apply.reservation.domain.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.apply.reservation.domain.Reservation;
import roomescape.apply.reservationtime.domain.ReservationTime;
import roomescape.apply.theme.domain.Theme;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ReservationJDBCRepository implements ReservationRepository {

    private static final String INSERT_SQL = """
        INSERT INTO reservation (name, date, time_id, theme_id)
        VALUES (?, ?, ?, ?)
    """;

    private static final String SELECT_ALL_SQL = """
        SELECT
            r.id as reservation_id,
            r.name as reservation_name,
            r.date as reservation_date,
            rt.id as time_id,
            rt.start_at as time_start_at,
            th.id as theme_id,
            th.name as theme_name,
            th.description as theme_description,
            th.thumbnail as theme_thumbnail
        FROM reservation as r
        inner join reservation_time as rt
            on r.time_id = rt.id
        inner join theme as th
            on r.theme_id = th.id
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

    private static final String SELECT_ID_WHERE_TIME_ID_AND_THEME_ID_SQL = """
        SELECT
            id
        FROM
            reservation
        WHERE
            time_id = ?
            AND theme_id = ?
    """;

    private static final String SELECT_ANY_BY_TIME_ID_SQL = """
        SELECT
            id
        FROM
            reservation
        WHERE
            time_id = ?
        LIMIT 1
    """;

    private static final String SELECT_ANY_BY_THEME_ID_SQL = """
        SELECT
            id
        FROM
            reservation
        WHERE
            theme_id = ?
        LIMIT 1
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
            ps.setLong(4, reservation.getTheme().getId());
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
    public void deleteById(long id) {
        template.update(DELETE_SQL, id);
    }

    @Override
    public Optional<Long> findIdByTimeIdAndThemeId(long timeId, long themeId) {
        try {
            Long reservationId = template.queryForObject(SELECT_ID_WHERE_TIME_ID_AND_THEME_ID_SQL, Long.class, timeId, themeId);
            return Optional.ofNullable(reservationId);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Long> findAnyByTimeId(long timeId) {
        try {
            Long foundReservationTimeId = template.queryForObject(SELECT_ANY_BY_TIME_ID_SQL, Long.class, timeId);
            return Optional.ofNullable(foundReservationTimeId);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Long> findAnyByThemeId(long themeId) {
        try {
            Long foundThemeId = template.queryForObject(SELECT_ANY_BY_THEME_ID_SQL, Long.class, themeId);
            return Optional.ofNullable(foundThemeId);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<Reservation> reservationRowMapper() {
        return (rs, rowNum) -> {
            long timeId = rs.getLong("time_id");
            String timeStartAt = rs.getString("time_start_at");
            ReservationTime reservationTime = new ReservationTime(timeId, timeStartAt);
            long themeId = rs.getLong("theme_id");
            String themeName = rs.getString("theme_name");
            String themeDescription = rs.getString("theme_description");
            String themeThumbnail = rs.getString("theme_thumbnail");
            Theme theme = new Theme(themeId, themeName, themeDescription, themeThumbnail);

            return new Reservation(
                    rs.getLong("reservation_id"),
                    rs.getString("reservation_name"),
                    rs.getString("reservation_date"),
                    reservationTime,
                    theme
            );
        };
    }
}