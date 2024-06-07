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
import roomescape.application.port.out.ReservationPort;
import roomescape.domain.Reservation;

@Primary
@Repository
public class RservationJdbcRepository implements ReservationPort {

  private final JdbcTemplate jdbcTemplate;

  public RservationJdbcRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public List<Reservation> findReservations() {
    String sql = "select id, name, date, time from reservation";

    List<ReservationEntity> reservationEntityList = jdbcTemplate.query(
      sql, (resultSet, rowNum) -> {
        ReservationEntity reservationEntity = new ReservationEntity(
          resultSet.getLong("id"),
          resultSet.getString("name"),
          resultSet.getString("date"),
          resultSet.getString("time")
        );

        return reservationEntity;
      }
    );

    return reservationEntityList.stream()
                                .map(ReservationMapper::mapToDomain)
                                .toList();

  }

  @Override
  public Reservation saveReservations(Reservation reservation) {
    String sql = "INSERT INTO reservation(name, date, time) VALUES(?, ?, ?)";
    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, reservation.getName());
      ps.setString(2, reservation.getDate());
      ps.setString(3, reservation.getTime());
      return ps;
    }, keyHolder);

    return reservation.addId(Objects.requireNonNull(keyHolder.getKey())
                                    .longValue());
  }

  @Override
  public void deleteReservation(Long id) {
    String sql = "DELETE FROM reservations WHERE id = ?";

    jdbcTemplate.update(sql, id);
  }
}
