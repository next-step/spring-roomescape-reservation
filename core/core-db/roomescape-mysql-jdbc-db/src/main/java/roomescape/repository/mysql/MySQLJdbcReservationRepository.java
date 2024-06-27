package roomescape.repository.mysql;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import roomescape.repository.ReservationJdbcRepository;
import roomescape.repository.entity.ReservationEntity;
import roomescape.repository.projection.ReservationViewProjection;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class MySQLJdbcReservationRepository implements ReservationJdbcRepository {

    private static final String TABLE_COLUMN_ID = "id";
    private static final String TABLE_COLUMN_NAME = "name";
    private static final String TABLE_COLUMN_DATE = "date";
    private static final String TABLE_COLUMN_TIME_ID = "time_id";
    private static final String TABLE_COLUMN_THEME_ID = "theme_id";

    private static final String TABLE_COLUMN_RESERVATION_ID = "reservation_id";
    private static final String TABLE_COLUMN_RESERVATION_NAME = "reservation_name";
    private static final String TABLE_COLUMN_RESERVATION_DATE = "reservation_date";
    private static final String TABLE_COLUMN_RESERVATION_TIME_ID = "time_id";
    private static final String TABLE_COLUMN_RESERVATION_TIME_START_AT = "time_start_at";
    private static final String TABLE_COLUMN_THEME_NAME = "theme_name";


    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public MySQLJdbcReservationRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public ReservationEntity save(ReservationEntity reservationEntity) {
        String sql = "INSERT INTO reservation (id, name, date, time_id, theme_id) VALUES (:id, :name, :date, :time_id, :theme_id) " +
                "ON DUPLICATE KEY UPDATE name = VALUES(name), date = VALUES(date), time_id = VALUES(time_id), theme_id = VALUES(theme_id)";

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(TABLE_COLUMN_ID, reservationEntity.getId())
                .addValue(TABLE_COLUMN_NAME, reservationEntity.getReservationName())
                .addValue(TABLE_COLUMN_DATE, reservationEntity.getReservationDate())
                .addValue(TABLE_COLUMN_TIME_ID, reservationEntity.getReservationTimeId())
                .addValue(TABLE_COLUMN_THEME_ID, reservationEntity.getThemeId());

        namedParameterJdbcTemplate.update(sql, sqlParameterSource, generatedKeyHolder);

        if (Objects.isNull(generatedKeyHolder.getKey())) {
            return reservationEntity;
        }

        return reservationEntity.withId(generatedKeyHolder.getKey().longValue());
    }

    @Override
    public List<ReservationEntity> findAll() {
        String sql = "SELECT id, name, date, time_id, theme_id FROM reservation";

        return namedParameterJdbcTemplate.query(sql, resultSet -> {
            List<ReservationEntity> reservationEntities = new ArrayList<>();
            while (resultSet.next()) {
                reservationEntities.add(
                        new ReservationEntity(
                                resultSet.getLong(TABLE_COLUMN_ID),
                                resultSet.getString(TABLE_COLUMN_NAME),
                                resultSet.getDate(TABLE_COLUMN_DATE).toLocalDate(),
                                resultSet.getLong(TABLE_COLUMN_TIME_ID),
                                resultSet.getLong(TABLE_COLUMN_THEME_ID)
                        )
                );
            }
            return reservationEntities;
        });
    }

    @Override
    public void delete(Long reservationId) {
        String sql = "DELETE FROM reservation WHERE id = :id";

        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(TABLE_COLUMN_ID, reservationId);

        namedParameterJdbcTemplate.update(sql, sqlParameterSource);
    }

    @Override
    public Optional<ReservationEntity> findById(Long reservationId) {
        String sql = "SELECT id, name, date, time_id, theme_id FROM reservation WHERE id = :id";

        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(TABLE_COLUMN_ID, reservationId);

        try {
            return Optional.ofNullable(
                    namedParameterJdbcTemplate.queryForObject(
                            sql,
                            sqlParameterSource,
                            (resultSet, rowNum) -> new ReservationEntity(
                                    resultSet.getLong(TABLE_COLUMN_ID),
                                    resultSet.getString(TABLE_COLUMN_NAME),
                                    resultSet.getDate(TABLE_COLUMN_DATE).toLocalDate(),
                                    resultSet.getLong(TABLE_COLUMN_TIME_ID),
                                    resultSet.getLong(TABLE_COLUMN_THEME_ID)
                            )
                    )
            );
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<ReservationViewProjection> findAllReservationViewProjection() {
        String sql = "SELECT " +
                "    r.id as reservation_id, " +
                "    r.name as reservation_name, " +
                "    r.date as reservation_date, " +
                "    t.id as time_id, " +
                "    t.start_at as time_start_at, " +
                "    m.id as theme_id, " +
                "    m.name as theme_name " +
                "FROM reservation as r " +
                "inner join reservation_time as t " +
                "on r.time_id = t.id " +
                "inner join theme as m " +
                "on r.theme_id = m.id";


        return namedParameterJdbcTemplate.query(sql, resultSet -> {
            List<ReservationViewProjection> reservationViewProjections = new ArrayList<>();
            while (resultSet.next()) {
                reservationViewProjections.add(
                        new ReservationViewProjection(
                                resultSet.getLong(TABLE_COLUMN_RESERVATION_ID),
                                resultSet.getString(TABLE_COLUMN_RESERVATION_NAME),
                                resultSet.getDate(TABLE_COLUMN_RESERVATION_DATE).toLocalDate(),
                                resultSet.getLong(TABLE_COLUMN_RESERVATION_TIME_ID),
                                resultSet.getTime(TABLE_COLUMN_RESERVATION_TIME_START_AT).toLocalTime(),
                                resultSet.getLong(TABLE_COLUMN_THEME_ID),
                                resultSet.getString(TABLE_COLUMN_THEME_NAME)
                        )
                );
            }

            return reservationViewProjections;
        });
    }

    @Override
    public Optional<ReservationEntity> findByTimeId(Long timeId) {
        String sql = "SELECT * FROM reservation WHERE time_id = :time_id";
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(TABLE_COLUMN_TIME_ID, timeId);

        try {
            return Optional.ofNullable(
                    namedParameterJdbcTemplate.queryForObject(
                            sql,
                            sqlParameterSource,
                            (resultSet, rowNum) -> new ReservationEntity(
                                    resultSet.getLong(TABLE_COLUMN_ID),
                                    resultSet.getString(TABLE_COLUMN_NAME),
                                    resultSet.getDate(TABLE_COLUMN_DATE).toLocalDate(),
                                    resultSet.getLong(TABLE_COLUMN_TIME_ID),
                                    resultSet.getLong(TABLE_COLUMN_THEME_ID)
                            )
                    )
            );
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<ReservationEntity> findByDateAndTimeId(LocalDate date, Long timeId) {
        String sql = "SELECT id, name, date, time_id, theme_id FROM reservation WHERE date = :date and time_id = :time_id";
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(TABLE_COLUMN_DATE, date)
                .addValue(TABLE_COLUMN_TIME_ID, timeId);

        try {
            return Optional.ofNullable(
                    namedParameterJdbcTemplate.queryForObject(
                            sql,
                            sqlParameterSource,
                            (resultSet, rowNum) -> new ReservationEntity(
                                    resultSet.getLong(TABLE_COLUMN_ID),
                                    resultSet.getString(TABLE_COLUMN_NAME),
                                    resultSet.getDate(TABLE_COLUMN_DATE).toLocalDate(),
                                    resultSet.getLong(TABLE_COLUMN_TIME_ID),
                                    resultSet.getLong(TABLE_COLUMN_THEME_ID)
                            )
                    )
            );
        } catch (Exception ex) {
            return Optional.empty();
        }
    }
}
