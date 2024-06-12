package roomescape.repository.mysql;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.repository.entity.ReservationTimeEntity;

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

        long id = Optional.ofNullable(generatedKeyHolder.getKey())
                .orElse(reservationTimeEntity.getId())
                .longValue();

        return reservationTimeEntity.changeId(id);
    }
}
