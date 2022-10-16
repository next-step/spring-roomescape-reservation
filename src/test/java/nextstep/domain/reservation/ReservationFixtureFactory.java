package nextstep.domain.reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import nextstep.domain.reservation.Reservation.Name;
import nextstep.domain.reservation.dto.ReservationCommandDto;
import nextstep.domain.reservation.dto.ReservationFindCondition;


public class ReservationFixtureFactory {

  private static final Name NAME = Name.of("성시형");
  private static final LocalDate LOCAL_DATE = LocalDate.now().plusDays(1);

  private final Reservation fixture;
  private final ReservationCommandDto.Create create;
  private final ReservationFindCondition findCondition;

  private ReservationFixtureFactory() {
    final Name name = NAME;
    final LocalDate date = LOCAL_DATE;
    final LocalTime time = LocalTime.now();

    this.fixture = createReservation(name, date, time);

    this.create = ReservationCommandDto.Create
        .builder()
        .name(name.name())
        .date(date)
        .time(time)
        .build();

    this.findCondition = ReservationFindCondition.builder()
        .date(date)
        .time(time)
        .build();
  }

  private Reservation createReservation(Name name, LocalDate date, LocalTime time) {
    return Reservation.builder()
        .name(name)
        .date(date)
        .time(time)
        .build();
  }

  public static ReservationFixtureFactory newInstance() {
    return new ReservationFixtureFactory();
  }

  /**
   * 다음날 이 시간의 예약.
   * <pre>
   *   Reservation(date= now + 1day, time = now, name = 성시형)
   * </pre>
   */
  public Reservation getFixture() {
    return fixture;
  }

  /**
   * rageFrom으로 부터 rangeTo 까지의 예약을 분단위로 생성해 반환한다.
   * (rance closed)
   * <pre>
   *   var factory = ReservationFixtureFactory.newInstance();
   *   List<Reservation> reservationCustoms = factory.getCustomFixtures(LocalTime.now().plusMinutes(1), LocalTime.now().plusMinutes(10));
   *   assertThat(reservationCustoms).hasSize(10); // true
   * </pre>
   */
  public List<Reservation> getCustomFixtures(LocalTime rangeFrom, LocalTime rangeTo) {
    List<Reservation> reservations = new ArrayList<>();

    while (!rangeFrom.equals(rangeTo) && rangeTo.isAfter(rangeFrom)) {
      reservations.add(createReservation(NAME, LOCAL_DATE, rangeFrom));
      rangeFrom = rangeFrom.plusMinutes(1);
    }

    return reservations;
  }

  /**
   * 다음날 이 시간의 예약 생성 요청 객체. fixture와 값이 같음
   * <pre>
   *   ReservationCommandDto.Create(date= now + 1day, time = now, name = 성시형)
   * </pre>
   *
   * @see ReservationFixtureFactory#getFixture() getFixture
   */
  public ReservationCommandDto.Create getFixtureCreateReq() {
    return create;
  }

  /**
   * 다음날의 예약을 찾을 수 있는 조회조건 요청 객체. fixture와 값이 같음
   * <pre>
   *   ReservationFindCondition(date= now + 1day)
   * </pre>
   *
   * @see ReservationFixtureFactory#getFixture() getFixture
   */
  public ReservationFindCondition getFixtureFindCondition() {
    return findCondition;
  }
}
