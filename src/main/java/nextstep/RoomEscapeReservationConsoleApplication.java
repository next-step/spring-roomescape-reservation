package nextstep;

import java.util.Scanner;
import nextstep.domain.Reservation;
import nextstep.domain.repository.ReservationRepository;
import nextstep.infrastructure.ReservationDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class RoomEscapeReservationConsoleApplication {

    private static final String INPUT_1 = "1";
    private static final String INPUT_2 = "2";
    private static final String INPUT_3 = "3";
    private static final String INPUT_4 = "4";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ReservationRepository reservationRepository = new ReservationDao(jdbcTemplate());

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

                reservationRepository.save(new Reservation(date, time, name));

                System.out.println("예약이 등록되었습니다.");
            }

            if (INPUT_2.equals(menuInput)) {

                System.out.println("취소할 예약 정보를 입력하세요.");
                System.out.println();

                System.out.println("날짜 (ex.2022-08-11)");
                String date = scanner.nextLine();

                System.out.println("시간 (ex.13:00)");
                String time = scanner.nextLine();

                reservationRepository.delete(date, time);

                System.out.println("예약이 취소되었습니다.");
            }

            if (INPUT_3.equals(menuInput)) {

                System.out.println("예약 조회 할 날짜를 입력하세요.");
                System.out.println();

                System.out.println("날짜 (ex.2022-08-11)");
                String date = scanner.nextLine();

                reservationRepository.findAllBy(date).forEach(System.out::println);
            }

            if (INPUT_4.equals(menuInput)) {

                break;
            }
        }
    }

    private static JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    private static DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setUrl("jdbc:h2:tcp://localhost/~/test");
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUsername("sa");

        return dataSource;
    }
}
