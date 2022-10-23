package nextstep.console;

import nextstep.domain.reservation.model.Reservation;
import nextstep.domain.reservation.model.ReservationMemoryRepository;
import nextstep.domain.reservation.service.ReservationResponse;
import nextstep.domain.reservation.service.ReservationService;
import nextstep.domain.schedule.model.Schedule;
import nextstep.domain.schedule.model.ScheduleMemoryRepository;
import nextstep.domain.schedule.service.ScheduleResponse;
import nextstep.domain.schedule.service.ScheduleService;
import nextstep.domain.theme.model.Theme;
import nextstep.domain.theme.model.ThemeMemoryRepository;
import nextstep.domain.theme.service.ThemeService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String INPUT_1 = "1";
    private static final String INPUT_2 = "2";
    private static final String INPUT_3 = "3";
    private static final String INPUT_4 = "4";

    public static void main(String[] args) {
        final ReservationService reservationService = new ReservationService(new ReservationMemoryRepository(), new ScheduleMemoryRepository());
        final ScheduleService scheduleService = new ScheduleService(new ScheduleMemoryRepository(), new ThemeMemoryRepository());
        final ThemeService themeService = new ThemeService(new ThemeMemoryRepository());

        createFixtures(scheduleService, themeService);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("메뉴를 선택하세요.");
            System.out.println("1: 예약");
            System.out.println("2: 예약 취소");
            System.out.println("3: 예약 조회");
            System.out.println("4: 종료");

            String menuInput = scanner.nextLine();
            if (INPUT_1.equals(menuInput)) {

                System.out.println("예약하고 싶은 날짜를 입력하세요.");
                System.out.println();

                System.out.println("날짜 (ex.2022-08-11)");
                String date = scanner.nextLine();

                List<ScheduleResponse> schedules = scheduleService.findAllByDate(date);

                System.out.println("해당 날짜의 예약을 원하는 방탈출 일정 번호를 입력하세요 (ex.1)");

                for (int i = 0; i < schedules.size(); i++) {
                    ScheduleResponse schedule = schedules.get(i);
                    System.out.printf("%d - 날짜: %s, 시간: %s, 이름: %s, 설명: %s, 금액: %d%n", i + 1, schedule.getDate(), schedule.getDate(), schedule.getTheme().getName(), schedule.getTheme().getDesc(), schedule.getTheme().getPrice());
                }

                String selectedSchedule = scanner.nextLine();

                System.out.println("예약자 이름");
                String name = scanner.nextLine();

                Reservation reservation = new Reservation(
                    null,
                    name,
                    schedules.get(Integer.parseInt(selectedSchedule) - 1).getId()
                );

                reservationService.create(reservation);
                System.out.println("예약이 등록되었습니다.");
            }

            if (INPUT_2.equals(menuInput)) {

                System.out.println("취소할 예약 정보를 입력하세요.");
                System.out.println();

                System.out.println("날짜 (ex.2022-08-11)");
                String date = scanner.nextLine();

                List<ScheduleResponse> schedules = scheduleService.findAllByDate(date);

                System.out.println("해당 날짜의 예약 취소를 원하는 방탈출 일정 번호를 입력하세요 (ex.1)");

                for (int i = 0; i < schedules.size(); i++) {
                    ScheduleResponse schedule = schedules.get(i);
                    System.out.printf("%d - 날짜: %s, 시간: %s, 이름: %s, 설명: %s, 금액: %d%n", i + 1, schedule.getDate(), schedule.getDate(), schedule.getTheme().getName(), schedule.getTheme().getDesc(), schedule.getTheme().getPrice());
                }

                String selectedSchedule = scanner.nextLine();

                reservationService.removeByScheduleId(schedules.get(Integer.parseInt(selectedSchedule) - 1).getId());

                System.out.println("예약이 취소되었습니다.");
            }

            if (INPUT_3.equals(menuInput)) {

                System.out.println("예약 조회 할 날짜를 입력하세요.");
                System.out.println();

                System.out.println("날짜 (ex.2022-08-11)");
                String date = scanner.nextLine();

                for (ReservationResponse response : reservationService.findAllByDate(date)) {
                    System.out.printf("날짜: %s,시간: %s, 이름: %s", response.getDate(), response.getDate(), response.getName());
                }
            }

            if (INPUT_4.equals(menuInput)) {

                break;
            }
        }
    }

    private static void createFixtures(ScheduleService scheduleService, ThemeService themeService) {
        Long themeId = themeService.create(new Theme(null, "예시", "예시 테마 입니다.", 20_000L));
        scheduleService.create(new Schedule(null, themeId, LocalDate.of(2022, 10, 24), LocalTime.of(10, 10)));
    }
}
