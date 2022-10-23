package nextstep;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import nextstep.application.reservation.ReservationCreateExistValidation;
import nextstep.application.reservation.ReservationCreatePolicy;
import nextstep.application.reservation.ReservationDeleteNotExistValidation;
import nextstep.application.reservation.ReservationDeletePolicy;
import nextstep.application.reservation.ReservationService;
import nextstep.application.reservation.dto.Reservation;
import nextstep.domain.reservation.repository.MemoryReservationRepository;

public class Main {

  private static final String INPUT_1 = "1";
  private static final String INPUT_2 = "2";
  private static final String INPUT_3 = "3";
  private static final String INPUT_4 = "4";

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    ReservationService service = new ReservationService(new MemoryReservationRepository(),
        new ReservationCreatePolicy(List.of(new ReservationCreateExistValidation(new MemoryReservationRepository()))),
        new ReservationDeletePolicy(List.of(new ReservationDeleteNotExistValidation(new MemoryReservationRepository())))
    );

    while (true) {
      System.out.println("메뉴를 선택하세요.");
      System.out.println("1: 예약");
      System.out.println("2: 예약 취소");
      System.out.println("3: 예약 조회");
      System.out.println("4: 종료");

      String menuInput = scanner.nextLine();
      if (INPUT_1.equals(menuInput)) {

        System.out.println("예약 정보를 입력하세요.");
        System.out.println();

        System.out.println("날짜 (ex.2022-08-11)");
        String date = scanner.nextLine();

        System.out.println("시간 (ex.13:00)");
        String time = scanner.nextLine();

        System.out.println("예약자 이름");
        String name = scanner.nextLine();

        Reservation reservation = Reservation.builder()
            .date(LocalDate.parse(date))
            .time(time)
            .name(name)
            .build();

        try {
          service.create(reservation);
        } catch (RuntimeException e) {
          System.out.println("이미 해당 날짜, 시간에 예약이 존재합니다.");
          continue;
        }
        System.out.println("예약이 등록되었습니다.");
      }

      if (INPUT_2.equals(menuInput)) {

        System.out.println("취소할 예약 정보를 입력하세요.");
        System.out.println();

        System.out.println("날짜 (ex.2022-08-11)");
        String date = scanner.nextLine();

        System.out.println("시간 (ex.13:00)");
        String time = scanner.nextLine();

        service.removeReservation(LocalDate.parse(date), time);
        System.out.println("예약이 취소되었습니다.");
      }

      if (INPUT_3.equals(menuInput)) {

        System.out.println("예약 조회 할 날짜를 입력하세요.");
        System.out.println();

        System.out.println("날짜 (ex.2022-08-11)");
        String date = scanner.nextLine();

        service.findReservations(LocalDate.parse(date));
      }

      if (INPUT_4.equals(menuInput)) {

        break;
      }
    }
  }
}
