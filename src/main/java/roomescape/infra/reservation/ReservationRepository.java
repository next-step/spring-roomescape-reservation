package roomescape.infra.reservation;

import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.CreateReservation;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.theme.ThemeSummary;
import roomescape.domain.time.ReservationTime;
import roomescape.infra.theme.ThemeRepository;
import roomescape.infra.time.ReservationTimeRepository;

@Repository
public class ReservationRepository {

  private final ReservationJdbcRepository jdbcRepository;
  private final ReservationTimeRepository timeRepository;
  private final ThemeRepository themeRepository;

  public ReservationRepository(ReservationJdbcRepository jdbcRepository,
      ReservationTimeRepository timeRepository, ThemeRepository themeRepository) {
    this.jdbcRepository = jdbcRepository;
    this.timeRepository = timeRepository;
    this.themeRepository = themeRepository;
  }

  public List<Reservation> findAll() {
    List<ReservationEntity> reservations = jdbcRepository.findAll();
    List<Long> timeIds = reservations.stream().map(ReservationEntity::getTimeId).distinct()
        .toList();
    Map<Long, ReservationTime> timeMap = timeRepository.findByIds(timeIds).stream()
        .collect(toMap(ReservationTime::getId, time -> time));
    List<Long> themeIds = reservations.stream().map(ReservationEntity::getThemeId).distinct()
        .toList();
    Map<Long, ThemeSummary> themeMap = themeRepository.findSummaryByIds(themeIds).stream()
        .collect(toMap(ThemeSummary::id, t -> t));
    return reservations.stream().map(reservationEntity -> reservationEntity.toDomain(
            themeMap.get(reservationEntity.getThemeId()), timeMap.get(reservationEntity.getTimeId())))
        .toList();

  }

  public boolean isExists(CreateReservation reservation) {
    return jdbcRepository.isExists(reservation);
  }

  public Reservation getById(Long id) {
    ReservationEntity entity = jdbcRepository.getById(id);
    if (entity == null) {
      return null;
    }
    ReservationTime time = timeRepository.findById(entity.getTimeId());
    ThemeSummary theme = themeRepository.findSummaryOne(entity.getThemeId());
    return entity.toDomain(theme, time);
  }

  public long save(CreateReservation newReservation) {
    return jdbcRepository.save(newReservation);
  }

  public void delete(long id) {
    jdbcRepository.delete(id);
  }
}
