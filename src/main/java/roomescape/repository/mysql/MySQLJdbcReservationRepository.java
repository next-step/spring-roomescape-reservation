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
import java.util.Objects;

@Primary
@Repository
public class MySQLJdbcReservationRepository implements ReservationRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public MySQLJdbcReservationRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public ReservationEntity save(ReservationEntity reservationEntity) {
        String sql = "INSERT INTO reservation (id, name, date_time) VALUES (:id, :name, :date_time) " +
                "ON DUPLICATE KEY UPDATE name = VALUES(name), date_time = VALUES(date_time)";

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("id", reservationEntity.getId())
                .addValue("name", reservationEntity.getReservationName())
                .addValue("date_time", reservationEntity.getReservationDateTime());

        namedParameterJdbcTemplate.update(sql, sqlParameterSource, generatedKeyHolder);

        long id = Objects.requireNonNull(generatedKeyHolder.getKey()).longValue();

        return reservationEntity.changeId(id);
    }

    @Override
    public List<ReservationEntity> findAll() {
        String sql = "SELECT id, name, date_time FROM reservation";

        return namedParameterJdbcTemplate.query(sql, resultSet -> {
            List<ReservationEntity> reservationEntities = new ArrayList<>();
            while (resultSet.next()) {
                reservationEntities.add(
                        new ReservationEntity(
                                resultSet.getLong("id"),
                                resultSet.getString("name"),
                                resultSet.getTimestamp("date_time").toLocalDateTime()
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
    public ReservationEntity findById(Long reservationId) {
        String sql = "SELECT * FROM reservation WHERE id = :id";

        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("id", reservationId);

        return namedParameterJdbcTemplate.queryForObject(
                sql,
                sqlParameterSource,
                (resultSet, rowNum) -> new ReservationEntity(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getTimestamp("date_time").toLocalDateTime()
                )
        );
    }
}
