package nextstep.app.console;

import nextstep.core.reservation.ReservationRepository;
import nextstep.infra.h2.ReservationH2Repository;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Scanner;

public class RoomEscapeConsoleApplication {
    public static void main(String[] args) {
        DataSource dataSource = DataSourceBuilder.create()
                .driverClassName("org.h2.Driver")
                .url("jdbc:h2:mem:room-escape;MODE=MySQL")
                .username("sa")
                .build();
        JdbcTemplate template = new JdbcTemplate(dataSource);
        ReservationRepository repository = new ReservationH2Repository(template);

        template.execute("DROP TABLE IF EXISTS reservation");
        template.execute("""
                create table reservation
                (
                    id   bigint      not null primary key auto_increment comment '예약 아이디',
                    date date        not null default now() comment '예약 날짜',
                    time time        not null default now() comment '예약 시간',
                    name varchar(32) not null comment '예약자 이름'
                );
                                """);

        Scanner scanner = new Scanner(System.in);
        new ReservationController(scanner, repository).run();
    }
}