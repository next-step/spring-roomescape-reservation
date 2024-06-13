package roomescape.repository.mysql;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.repository.ReservationRepository;
import roomescape.repository.entity.ReservationEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class MySQLJdbcReservationRepository implements ReservationRepository {

    private static final String TABLE_COLUMN_ID = "id";
    private static final String TABLE_COLUMN_NAME = "name";
    private static final String TABLE_COLUMN_DATE = "date";
    private static final String TABLE_COLUMN_TIME_ID = "time_id";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public MySQLJdbcReservationRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public ReservationEntity save(ReservationEntity reservationEntity) {
        String sql = "INSERT INTO reservation (id, name, date, time_id) VALUES (:id, :name, :date, :time_id) " +
                "ON DUPLICATE KEY UPDATE name = VALUES(name), date = VALUES(date), time_id = VALUES(time_id)";

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(TABLE_COLUMN_ID, reservationEntity.getId())
                .addValue(TABLE_COLUMN_NAME, reservationEntity.getReservationName())
                .addValue(TABLE_COLUMN_DATE, reservationEntity.getReservationDate())
                .addValue(TABLE_COLUMN_TIME_ID, reservationEntity.getReservationTimeId());

        namedParameterJdbcTemplate.update(sql, sqlParameterSource, generatedKeyHolder);

        long id = Optional.ofNullable(generatedKeyHolder.getKey())
                .orElse(reservationEntity.getId())
                .longValue();

        return reservationEntity.changeId(id);
    }

    @Override
    public List<ReservationEntity> findAll() {
        String sql = "SELECT id, name, date, time_id FROM reservation";

        return namedParameterJdbcTemplate.query(sql, resultSet -> {
            List<ReservationEntity> reservationEntities = new ArrayList<>();
            while (resultSet.next()) {
                reservationEntities.add(
                        new ReservationEntity(
                                resultSet.getLong(TABLE_COLUMN_ID),
                                resultSet.getString(TABLE_COLUMN_NAME),
                                resultSet.getDate(TABLE_COLUMN_DATE).toLocalDate(),
                                resultSet.getLong(TABLE_COLUMN_TIME_ID)
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
                .addValue("id", reservationId);

        namedParameterJdbcTemplate.update(sql, sqlParameterSource);
    }

    @Override
    public Optional<ReservationEntity> findById(Long reservationId) {
        String sql = "SELECT * FROM reservation WHERE id = :id";

        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(TABLE_COLUMN_ID, reservationId);

        return Optional.ofNullable(
                namedParameterJdbcTemplate.queryForObject(
                        sql,
                        sqlParameterSource,
                        (resultSet, rowNum) -> new ReservationEntity(
                                resultSet.getLong(TABLE_COLUMN_ID),
                                resultSet.getString(TABLE_COLUMN_NAME),
                                resultSet.getDate(TABLE_COLUMN_DATE).toLocalDate(),
                                resultSet.getLong(TABLE_COLUMN_TIME_ID)
                        )
                )
        );
    }
}
