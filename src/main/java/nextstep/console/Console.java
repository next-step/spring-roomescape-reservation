package nextstep.console;

import nextstep.domain.Reservation;
import nextstep.service.ReservationService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Console {

    private static final String INPUT_1 = "1";
    private static final String INPUT_2 = "2";
    private static final String INPUT_3 = "3";
    private static final String INPUT_4 = "4";

    private final ReservationService reservationService;

    public Console(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public void execute() {
        Scanner scanner = new Scanner(System.in);

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

                // 예약 생성
                reservationService.save(LocalDate.parse(date), LocalTime.parse(time + ":00"), name);
                System.out.println("예약이 등록되었습니다.");
            }

            if (INPUT_2.equals(menuInput)) {

                System.out.println("취소할 예약 정보를 입력하세요.");
                System.out.println();

                System.out.println("날짜 (ex.2022-08-11)");
                String date = scanner.nextLine();

                System.out.println("시간 (ex.13:00)");
                String time = scanner.nextLine();

                // 예약 정보 삭제
                reservationService.deleteByLocalDateAndLocalTime(LocalDate.parse(date), LocalTime.parse(time));
                System.out.println("예약이 취소되었습니다.");
            }

            if (INPUT_3.equals(menuInput)) {

                System.out.println("예약 조회 할 날짜를 입력하세요.");
                System.out.println();

                System.out.println("날짜 (ex.2022-08-11)");
                String date = scanner.nextLine();

                // 예약 정보 조회
                for (Reservation reservation : reservationService.findReservationsByDate(LocalDate.parse(date))) {
                    System.out.println(reservation);
                }
            }

            if (INPUT_4.equals(menuInput)) {

                break;
            }
        }
    }
}
