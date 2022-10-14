package nextstep.console;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import nextstep.core.Reservations;

public class RoomEscapeConsoleApplication {

    private static final String INPUT_1 = "1";
    private static final String INPUT_2 = "2";
    private static final String INPUT_3 = "3";
    private static final String INPUT_4 = "4";

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        final Reservations reservations = new Reservations();

        while (true) {
            printMenu();

            final String menuInput = scanner.nextLine();
            if (INPUT_1.equals(menuInput)) {
                reserve(reservations);
            }

            if (INPUT_2.equals(menuInput)) {
                cancel(reservations);
            }

            if (INPUT_3.equals(menuInput)) {
                lookUp(reservations);
            }

            if (INPUT_4.equals(menuInput)) {
                break;
            }
        }
    }

    private static void printMenu() {
        System.out.println("메뉴를 선택하세요.");
        System.out.println("1: 예약");
        System.out.println("2: 예약 취소");
        System.out.println("3: 예약 조회");
        System.out.println("4: 종료");
    }

    private static void reserve(Reservations reservations) {
        System.out.println("예약 정보를 입력하세요. \n");
        reservations.register(inputDate(), inputTime(), inputName());
        System.out.println("예약이 등록되었습니다.");
    }

    private static void cancel(Reservations reservations) {
        System.out.println("취소할 예약 정보를 입력하세요. \n");
        reservations.cancelByDateTime(inputDate(), inputTime());
        System.out.println("예약이 취소되었습니다.\n");
    }

    private static void lookUp(Reservations reservations) {
        System.out.println("예약 조회 할 날짜를 입력하세요.\n");
        reservations.findAllByDate(inputDate()).forEach(it -> System.out.println("{\n"
            + " 날짜 : " + it.getDate() + ", \n"
            + " 시간 : " + it.getTime() + ", \n"
            + " 예약자 : " + it.getName() + "\n"
            + "}")
        );
    }

    private static LocalTime inputTime() {
        System.out.println("시간 (ex.13:00)");
        return LocalTime.parse(scanner.nextLine());
    }

    private static LocalDate inputDate() {
        System.out.println("날짜 (ex.2022-08-11)");
        return LocalDate.parse(scanner.nextLine());
    }

    private static String inputName() {
        System.out.println("예약자 이름");
        return scanner.nextLine();
    }
}