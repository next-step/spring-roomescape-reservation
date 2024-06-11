package roomescape.reservation.domain;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.theme.domain.Theme;
import roomescape.time.domain.Time;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository
public class ReservationRepository {

    private static final String FIND_BY_ID_SQL =
            "SELECT " +
                "r.id AS reservation_id, " +
                "r.name AS reservation_name, " +
                "r.date AS reservation_date, " +
                "rt.id AS reservation_time_id, " +
                "rt.start_at AS reservation_time_start_at, " +
                "rt.start_at AS reservation_time, " +
                "t.id AS theme_id, " +
                "t.name AS theme_name, " +
                "t.description AS theme_description, " +
                "t.thumbnail AS theme_thumbnail " +
            "FROM reservation r " +
            "INNER JOIN reservation_time rt " +
                "ON r.time_id = rt.id " +
            "INNER JOIN theme t " +
                "ON r.theme_id = t.id " +
            "WHERE r.id = ?;";
    private static final String SAVE_SQL = "INSERT INTO reservation (name, date, time_id, theme_id) VALUES (?, ?, ?, ?)";
    private static final String FIND_ALL_SQL =
            "SELECT " +
                "r.id AS reservation_id, " +
                "r.name AS reservation_name, " +
                "r.date AS reservation_date, " +
                "rt.id AS reservation_time_id, " +
                "rt.start_at AS reservation_time_start_at, " +
                "rt.start_at AS reservation_time, " +
                "t.id AS theme_id, " +
                "t.name AS theme_name, " +
                "t.description AS theme_description, " +
                "t.thumbnail AS theme_thumbnail " +
            "FROM reservation r " +
            "INNER JOIN reservation_time rt " +
                "ON r.time_id = rt.id " +
            "INNER JOIN theme t " +
                "ON r.theme_id = t.id;";
    private static final String DELETE_SQL = "DELETE FROM reservation WHERE id = ?";
    private static final String COLUMN_ID = "id";
    private static final int INDEX_ONE = 1;
    private static final int INDEX_TWO = 2;
    private static final int INDEX_THREE = 3;
    private static final int INDEX_FOUR = 4;
    private static final int ZERO = 0;
    private static final String RESERVATION_ID = "reservation_id";
    private static final String RESERVATION_NAME = "reservation_name";
    private static final String RESERVATION_DATE = "reservation_date";
    private static final String TIME_ID = "reservation_time_id";
    private static final String TIME_START_AT = "reservation_time_start_at";
    private static final String THEME_ID = "theme_id";
    private static final String THEME_NAME = "theme_name";
    private static final String THEME_DESCRIPTION = "theme_description";
    private static final String THEME_THUMBNAIL = "theme_thumbnail";

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Reservation findById(long reservationId) {
        return jdbcTemplate.query(FIND_BY_ID_SQL, (rs, rowNum) ->
                new Reservation(
                        rs.getLong(RESERVATION_ID),
                        rs.getString(RESERVATION_NAME),
                        rs.getString(RESERVATION_DATE),
                        new Time(rs.getLong(TIME_ID), rs.getString(TIME_START_AT)),
                        new Theme(rs.getLong(THEME_ID), rs.getString(THEME_NAME), rs.getString(THEME_DESCRIPTION), rs.getString(THEME_THUMBNAIL))
                ), reservationId).get(ZERO);
    }

    public long save(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    SAVE_SQL,
                    new String[]{COLUMN_ID}
            );
            preparedStatement.setString(INDEX_ONE, reservation.getName());
            preparedStatement.setString(INDEX_TWO, reservation.getDate());
            preparedStatement.setLong(INDEX_THREE, reservation.getTime().getId());
            preparedStatement.setLong(INDEX_FOUR, reservation.getTheme().getId());
            return preparedStatement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, (rs, rowNum) ->
                new Reservation(
                        rs.getLong(COLUMN_ID),
                        rs.getString(RESERVATION_NAME),
                        rs.getString(RESERVATION_DATE),
                        new Time(rs.getLong(TIME_ID), rs.getString(TIME_START_AT)),
                        new Theme(rs.getLong(THEME_ID), rs.getString(THEME_NAME), rs.getString(THEME_DESCRIPTION), rs.getString(THEME_THUMBNAIL))
                )
        );
    }

    public void delete(Long id) {
        jdbcTemplate.update(DELETE_SQL, id);
    }
}
