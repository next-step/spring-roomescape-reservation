package nextstep;

import nextstep.domain.Reservation;
import nextstep.domain.ReservationTime;
import nextstep.domain.Reservations;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class RoomEscapeConsoleApplication {
    private static final String INPUT_1 = "1";
    private static final String INPUT_2 = "2";
    private static final String INPUT_3 = "3";
    private static final String INPUT_4 = "4";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DataSource dataSource = DataSourceBuilder.create()
            .driverClassName("org.h2.Driver")
            .url("jdbc:h2:mem:test")
            .username("sa")
            .build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("DROP TABLE reservation IF EXISTS");
        jdbcTemplate.execute("""
            CREATE TABLE reservation (
                name VARCHAR(255),
                reservation_date DATE,
                reservation_time TIME,
                CONSTRAINT reservation_date_time_unique UNIQUE (reservation_date, reservation_time)
            )"""
        );

        Reservations reservations = new Reservations(jdbcTemplate);

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
                    LocalDate.parse(date),
                    LocalTime.parse(time + ":00"),
                    name
                );

                reservations.add(reservation);
                System.out.println("예약이 등록되었습니다.");
            }

            if (INPUT_2.equals(menuInput)) {

                System.out.println("취소할 예약 정보를 입력하세요.");
                System.out.println();

                System.out.println("날짜 (ex.2022-08-11)");
                LocalDate date = LocalDate.parse(scanner.nextLine());

                System.out.println("시간 (ex.13:00)");
                LocalTime time = LocalTime.parse(scanner.nextLine());

                ReservationTime reservationTime = new ReservationTime(date, time);
                reservations.remove(reservationTime);

                System.out.println("예약이 취소되었습니다.");
            }

            if (INPUT_3.equals(menuInput)) {

                System.out.println("예약 조회 할 날짜를 입력하세요.");
                System.out.println();

                System.out.println("날짜 (ex.2022-08-11)");
                LocalDate date = LocalDate.parse(scanner.nextLine());

                reservations.findBy(date)
                    .forEach(System.out::println);
            }

            if (INPUT_4.equals(menuInput)) {

                break;
            }
        }
    }
}
