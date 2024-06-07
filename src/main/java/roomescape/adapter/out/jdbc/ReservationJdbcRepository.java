package roomescape.adapter.out.jdbc;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.adapter.mapper.ReservationMapper;
import roomescape.adapter.out.ReservationEntity;
import roomescape.adapter.out.ReservationTimeEntity;
import roomescape.application.port.out.ReservationPort;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Primary
@Repository
public class ReservationJdbcRepository implements ReservationPort {

  private final JdbcTemplate jdbcTemplate;
  private final ReservationTimeJdbcRepository reservationTimeJdbcRepository;

  public ReservationJdbcRepository(JdbcTemplate jdbcTemplate,
    ReservationTimeJdbcRepository reservationTimeJdbcRepository) {
    this.jdbcTemplate = jdbcTemplate;
    this.reservationTimeJdbcRepository = reservationTimeJdbcRepository;
  }

  @Override
  public List<Reservation> findReservations() {
    String sql =
      "select reservation.id, reservation.name, reservation.date, reservation.time_id, reservation_time.start_at from reservation INNER JOIN reservation_time on reservation.time_id = reservation_time.id";

    List<ReservationEntity> reservationEntities = jdbcTemplate.query(
      sql, (resultSet, rowNum) -> {
        Long id = resultSet.getLong("time_id");
        String time = resultSet.getString("start_at");

        ReservationTimeEntity reservationTimeEntity = ReservationTimeEntity.of(id, time);

        ReservationEntity reservationEntity = new ReservationEntity(
          resultSet.getLong("id"),
          resultSet.getString("name"),
          resultSet.getString("date"),
          reservationTimeEntity
        );

        return reservationEntity;
      }
    );

    return reservationEntities.stream()
                              .map(ReservationMapper::mapToDomain)
                              .toList();

  }

  @Override
  public Reservation saveReservation(Reservation reservation) {

    ReservationTime reservationTime = reservationTimeJdbcRepository.saveReservationTime(ReservationTime.of(
      reservation.getTime()
                 .getStartAt()));

    String sql = "INSERT INTO reservation(name, date, time_id) VALUES(?, ?, ?)";
    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, reservation.getName());
      ps.setString(2, reservation.getDate());
      ps.setLong(3, reservationTime.getId());
      return ps;
    }, keyHolder);

    return reservation.addId(Objects.requireNonNull(keyHolder.getKey())
                                    .longValue());
  }

  @Override
  public void deleteReservation(Long id) {
    String sql = "DELETE FROM reservation WHERE id = ?";

    jdbcTemplate.update(sql, id);
  }
}
