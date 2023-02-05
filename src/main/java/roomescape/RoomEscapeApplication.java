package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class RoomEscapeApplication {
    private static final String RESERVATION = "reservation";
    private static final String THEME = "theme";

    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String DELETE = "delete";
    private static final String QUIT = "quit";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Reservation> reservations = new ArrayList<>();
        Long reservationIdIndex = 0L;

        List<Theme> themes = new ArrayList<>();
        themes.add(new Theme("워너고홈", "병맛 어드벤처 회사 코믹물", 29_000));

        while (true) {
            System.out.println();
            System.out.println("### 명령어를 입력하세요. ###");
            System.out.println("- 예약하기: add reservation {date},{time},{name} ex) add reservation 2022-08-11,13:00,류성현");
            System.out.println("- 예약조회: find reservation {id} ex) find reservation 1");
            System.out.println("- 예약취소: delete reservation {id} ex) delete reservation 1");
            System.out.println("- 테마목록조회: find theme");
            System.out.println("- 종료: quit");

            String input = scanner.nextLine();
            if (input.startsWith(ADD + " " + RESERVATION)) {
                String params = input.split(" ")[2];

                String date = params.split(",")[0];
                String time = params.split(",")[1];
                String name = params.split(",")[2];


                Theme theme = themes.stream()
                        .filter(it -> Objects.equals(it.getName(), "워너고홈"))
                        .findFirst()
                        .orElseThrow(RuntimeException::new);

                Reservation reservation = new Reservation(
                        ++reservationIdIndex,
                        LocalDate.parse(date),
                        LocalTime.parse(time + ":00"),
                        name,
                        theme
                );

                reservations.add(reservation);

                System.out.println("예약이 등록되었습니다.");
                System.out.println("예약 번호: " + reservation.getId());
                System.out.println("예약 날짜: " + reservation.getDate());
                System.out.println("예약 시간: " + reservation.getTime());
                System.out.println("예약자 이름: " + reservation.getName());
            }

            if (input.startsWith(FIND + " " + RESERVATION)) {
                String params = input.split(" ")[2];

                Long id = Long.parseLong(params.split(",")[0]);

                Reservation reservation = reservations.stream()
                        .filter(it -> Objects.equals(it.getId(), id))
                        .findFirst()
                        .orElseThrow(RuntimeException::new);

                System.out.println("예약 번호: " + reservation.getId());
                System.out.println("예약 날짜: " + reservation.getDate());
                System.out.println("예약 시간: " + reservation.getTime());
                System.out.println("예약자 이름: " + reservation.getName());
                System.out.println("예약 테마 이름: " + reservation.getTheme().getName());
                System.out.println("예약 테마 설명: " + reservation.getTheme().getDesc());
                System.out.println("예약 테마 가격: " + reservation.getTheme().getPrice());
            }

            if (input.startsWith(DELETE + " " + RESERVATION)) {
                String params = input.split(" ")[2];

                Long id = Long.parseLong(params.split(",")[0]);

                if (reservations.removeIf(it -> Objects.equals(it.getId(), id))) {
                    System.out.println("예약이 취소되었습니다.");
                }
            }

            if (input.startsWith(FIND + " " + THEME)) {
                themes.forEach(it -> {
                    System.out.println("테마 이름: " + it.getName());
                    System.out.println("테마 설명: " + it.getDesc());
                    System.out.println("테마 가격: " + it.getPrice());
                    System.out.println();
                });
            }

            if (input.equals(QUIT)) {
                break;
            }
        }
    }
}
