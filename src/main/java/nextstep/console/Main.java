package nextstep.console;

import nextstep.domain.reservation.model.Reservation;
import nextstep.domain.reservation.model.ReservationMemoryRepository;
import nextstep.domain.reservation.service.ReservationService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Main {
    private static final String INPUT_1 = "1";
    private static final String INPUT_2 = "2";
    private static final String INPUT_3 = "3";
    private static final String INPUT_4 = "4";

    public static void main(String[] args) {
        final ReservationService service = new ReservationService(new ReservationMemoryRepository());

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

                Reservation reservation = new Reservation(
                    null,
                    LocalDate.parse(date),
                    LocalTime.parse(time + ":00"),
                    name
                );

                service.create(reservation);
                System.out.println("예약이 등록되었습니다.");
            }

            if (INPUT_2.equals(menuInput)) {

                System.out.println("취소할 예약 정보를 입력하세요.");
                System.out.println();

                System.out.println("날짜 (ex.2022-08-11)");
                String date = scanner.nextLine();

                System.out.println("시간 (ex.13:00)");
                String time = scanner.nextLine();

                service.removeByDateAndTime(date, time);

                System.out.println("예약이 취소되었습니다.");
            }

            if (INPUT_3.equals(menuInput)) {

                System.out.println("예약 조회 할 날짜를 입력하세요.");
                System.out.println();

                System.out.println("날짜 (ex.2022-08-11)");
                String date = scanner.nextLine();

                for (Reservation reservation : service.findAllByDate(date)) {
                    System.out.println(reservation);
                }
            }

            if (INPUT_4.equals(menuInput)) {

                break;
            }
        }
    }
}
