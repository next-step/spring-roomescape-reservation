package nextstep;

import nextsetp.domain.reservation.Reservation;
import nextsetp.domain.schedule.Schedule;
import nextstep.app.reservation.ReservationService;
import nextstep.app.theme.ThemeService;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String INPUT_1 = "1";
    private static final String INPUT_2 = "2";
    private static final String INPUT_3 = "3";
    private static final String INPUT_4 = "4";
    private static final String INPUT_5 = "5";
    private static final String INPUT_6 = "6";
    private static final String INPUT_7 = "7";
    private static final String INPUT_8 = "8";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ReservationService reservationService = Configuration.getReservationService();
        ThemeService themeService = Configuration.getThemeService();

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

                System.out.println("스케쥴 아이디를 입력해주세요");
                Long scheduleId = scanner.nextLong();

                System.out.println("예약자 이름");
                String name = scanner.nextLine();

                reservationService.save(scheduleId, name);
                System.out.println("예약이 등록되었습니다.");
            }

            if (INPUT_2.equals(menuInput)) {

                System.out.println("취소할 예약 정보를 입력하세요.");
                System.out.println();

                System.out.println("예약 아이디를 입력해주세요");
                Long reservationId = scanner.nextLong();

                reservationService.delete(reservationId);
                System.out.println("예약이 취소되었습니다.");
            }

            if (INPUT_3.equals(menuInput)) {

                System.out.println("예약 조회 할 날짜를 입력하세요.");
                System.out.println();

                System.out.println("날짜 (ex.2022-08-11)");
                String date = scanner.nextLine();

                List<Reservation> reservations = reservationService.findAllBy(date);

                reservations.forEach(reservation -> {
                    System.out.println("스케쥴: " + reservation.getScheduleId() + "\n"
                    + "이름: " + reservation.getName() + "\n");
                });
            }

            if (INPUT_4.equals(menuInput)) {
                System.out.println("테마 정보를 입력하세요.");
                System.out.println();

                System.out.println("테마 이름을 설정해주세요.");
                String name = scanner.nextLine();

                System.out.println("테마 설명을 설정해주세요.");
                String desc = scanner.nextLine();

                System.out.println("테마 가격을 설정해주세요.");
                Long price = scanner.nextLong();

                themeService.save(name, desc, price);
                System.out.println("테마가 등록되었습니다.");
            }

            if (INPUT_7.equals(menuInput)) {
                break;
            }
        }
    }
}