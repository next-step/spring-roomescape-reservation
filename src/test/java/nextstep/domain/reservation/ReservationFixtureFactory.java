package nextstep.domain.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.domain.reservation.Reservation.Name;
import nextstep.domain.reservation.dto.ReservationCommandDto;


public class ReservationFixtureFactory {

  private final Reservation fixture;
  private final ReservationCommandDto.Create create;

  private ReservationFixtureFactory() {
    final Name name = Name.of("성시형");
    final LocalDate date = LocalDate.now().plusDays(1);
    final LocalTime time = LocalTime.now();

    this.fixture = Reservation.builder()
        .name(name)
        .date(date)
        .time(time)
        .build();

    this.create = ReservationCommandDto.Create
        .builder()
        .name(name.name())
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
   * 다음날 이 시간의 예약 생성 요청 객체. fixture와 값이 같음
   * <pre>
   *   ReservationCommandDto.Create(date= now + 1day, time = now, name = 성시형)
   * </pre>
   * @see ReservationFixtureFactory#getFixture() getFixture
   */
  public ReservationCommandDto.Create getFixtureCreateReq() {
    return create;
  }
}
