package roomescape.repository.mysql;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.repository.entity.ReservationTimeEntity;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class MySQLJdbcReservationTimeRepository implements ReservationTimeRepository {

    private static final String TABLE_COLUMN_ID = "id";
    private static final String TABLE_COLUMN_START_AT = "start_at";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public MySQLJdbcReservationTimeRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public ReservationTimeEntity save(ReservationTimeEntity reservationTimeEntity) {
        String sql = "INSERT INTO reservation_time (id, start_at) VALUES (:id, :start_at) " +
                "ON DUPLICATE KEY UPDATE start_at = VALUES(start_at)";

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(TABLE_COLUMN_ID, reservationTimeEntity.getId())
                .addValue(TABLE_COLUMN_START_AT, reservationTimeEntity.getStartAt());

        namedParameterJdbcTemplate.update(sql, sqlParameterSource, generatedKeyHolder);

        if (Objects.isNull(generatedKeyHolder.getKey())) {
            return reservationTimeEntity;
        }

        return reservationTimeEntity.withId(generatedKeyHolder.getKey().longValue());
    }

    @Override
    public Optional<ReservationTimeEntity> findById(Long reservationTimeId) {
        String sql = "SELECT id, start_at FROM reservation_time WHERE id = :id";

        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(TABLE_COLUMN_ID, reservationTimeId);

        try {
            return Optional.ofNullable(
                    namedParameterJdbcTemplate.queryForObject(
                            sql,
                            sqlParameterSource,
                            (resultSet, rowNum) -> new ReservationTimeEntity(
                                    resultSet.getLong(TABLE_COLUMN_ID),
                                    resultSet.getTime(TABLE_COLUMN_START_AT).toLocalTime()
                            )
                    )
            );
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<ReservationTimeEntity> findByStartAt(LocalTime startAt) {
        String sql = "SELECT id, start_at FROM reservation_time WHERE start_at = :start_at";

        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(TABLE_COLUMN_START_AT, startAt);

        try {
            return Optional.ofNullable(
                    namedParameterJdbcTemplate.queryForObject(
                            sql,
                            sqlParameterSource,
                            (resultSet, rowNum) -> new ReservationTimeEntity(
                                    resultSet.getLong(TABLE_COLUMN_ID),
                                    resultSet.getTime(TABLE_COLUMN_START_AT).toLocalTime()
                            )
                    )
            );
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<ReservationTimeEntity> findAll() {
        String sql = "SELECT id, start_at FROM reservation_time";

        return namedParameterJdbcTemplate.query(sql, resultSet -> {
            List<ReservationTimeEntity> reservationTimeEntities = new ArrayList<>();
            while (resultSet.next()) {
                reservationTimeEntities.add(
                        new ReservationTimeEntity(
                                resultSet.getLong(TABLE_COLUMN_ID),
                                resultSet.getTime(TABLE_COLUMN_START_AT).toLocalTime()
                        )
                );
            }
            return reservationTimeEntities;
        });
    }

    @Override
    public void delete(Long reservationTimeId) {
        String sql = "DELETE FROM reservation_time WHERE id = :id";

        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(TABLE_COLUMN_ID, reservationTimeId);

        namedParameterJdbcTemplate.update(sql, sqlParameterSource);
    }
}
