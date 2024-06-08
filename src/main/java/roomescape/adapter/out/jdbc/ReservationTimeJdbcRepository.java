package roomescape.adapter.out.jdbc;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.adapter.mapper.ReservationTimeMapper;
import roomescape.adapter.out.ReservationTimeEntity;
import roomescape.application.port.out.ReservationTimePort;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationTimeJdbcRepository implements ReservationTimePort {

  private final JdbcTemplate jdbcTemplate;

  public ReservationTimeJdbcRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public ReservationTime saveReservationTime(ReservationTime reservationTime) {
    String sql = "INSERT INTO reservation_time(start_at) VALUES(?)";
    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, reservationTime.getStartAt());
      return ps;
    }, keyHolder);

    return reservationTime.addId(Objects.requireNonNull(keyHolder.getKey())
                                        .longValue());
  }

  @Override
  public List<ReservationTime> findReservationTimes() {
    String sql = "SELECT id, start_at FROM reservation_time";

    List<ReservationTimeEntity> reservationTimeEntities = jdbcTemplate.query(
      sql, (resultSet, rowNum) -> {
        ReservationTimeEntity reservationTimeEntity = new ReservationTimeEntity(
          resultSet.getLong("id"),
          resultSet.getString("start_at")
        );

        return reservationTimeEntity;
      }
    );

    return reservationTimeEntities.stream()
                                  .map(ReservationTimeMapper::mapToDomain)
                                  .toList();
  }

  @Override
  public void deleteReservationTime(Long id) {
    String sql = "DELETE FROM reservation_time WHERE id = ?";

    jdbcTemplate.update(sql, id);
  }

  @Override
  public Optional<ReservationTime> findReservationTimeById(Long id) {
    String sql = "SELECT id, start_at FROM reservation_time WHERE id = ?";

    List<ReservationTimeEntity> reservationTimeEntities = jdbcTemplate.query(
      sql, (resultSet, rowNum) ->
        new ReservationTimeEntity(
          resultSet.getLong("id"),
          resultSet.getString("start_at")
        ),
      id
    );

    ReservationTime reservationTime = null;
    if (!reservationTimeEntities.isEmpty()) {
      reservationTime = ReservationTimeMapper.mapToDomain(reservationTimeEntities.get(0));
    }

    return Optional.ofNullable(reservationTime);
  }

  @Override
  public Optional<ReservationTime> findReservationTimeByStartAt(String startAt) {
    String sql = "SELECT id, start_at FROM reservation_time WHERE start_at = ?";

    List<ReservationTimeEntity> reservationTimeEntities = jdbcTemplate.query(
      sql, (resultSet, rowNum) ->
        new ReservationTimeEntity(
          resultSet.getLong("id"),
          resultSet.getString("start_at")
        ),
      startAt
    );

    ReservationTime reservationTime = null;
    if (!reservationTimeEntities.isEmpty()) {
      reservationTime = ReservationTimeMapper.mapToDomain(reservationTimeEntities.get(0));
    }

    return Optional.ofNullable(reservationTime);
  }
}
