package roomescape.reservation.domain;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository
public class ReservationRepository {

    private static final String FIND_BY_ID_SQL = "select * from reservation where id = ?";
    private static final String SAVE_SQL = "insert into reservation (name, date, time) values (?, ?, ?)";
    private static final String FIND_ALL_SQL = "select * from reservation";
    private static final String DELETE_SQL = "delete from reservation where id = ?";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIME = "time";
    private static final int INDEX_ONE = 1;
    private static final int INDEX_TWO = 2;
    private static final int INDEX_THREE = 3;

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Reservation findById(long reservationId) {
        return jdbcTemplate.queryForObject(
                FIND_BY_ID_SQL,
                (rs, rowNum) -> new Reservation(
                        rs.getLong(COLUMN_ID),
                        rs.getString(COLUMN_NAME),
                        rs.getString(COLUMN_DATE),
                        rs.getString(COLUMN_TIME)
                ), reservationId);
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
            preparedStatement.setString(INDEX_THREE, reservation.getTime());
            return preparedStatement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, (rs, rowNum) -> new Reservation(rs.getLong(COLUMN_ID), rs.getString(COLUMN_NAME), rs.getString(COLUMN_DATE), rs.getString(COLUMN_TIME)));
    }

    public void delete(Long id) {
        jdbcTemplate.update(DELETE_SQL, id);
    }
}
