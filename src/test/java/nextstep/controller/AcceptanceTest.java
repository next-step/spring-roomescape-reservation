package nextstep.controller;

import io.restassured.RestAssured;
import nextstep.dto.schedule.ScheduleCreateRequest;
import nextstep.dto.reservation.ReservationCreateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;

import static nextstep.Constants.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AcceptanceTest {
    @LocalServerPort
    int port;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    protected void initReservationTable() {
        String sql = "create table if not exists reservation (" +
                "id bigint not null auto_increment primary key," +
                "schedule_id bigint not null," +
                "date date not null," +
                "time time not null," +
                "name varchar(255) not null)";

        jdbcTemplate.execute("drop table reservation if exists");
        jdbcTemplate.execute(sql);
    }

    protected void initThemeTable() {
        String sql = "create table if not exists theme (" +
                "id bigint not null auto_increment primary key," +
                "name varchar(255) not null," +
                "desc varchar(255) not null," +
                "price int not null)";

        jdbcTemplate.execute("drop table theme if exists");
        jdbcTemplate.execute(sql);
    }

    protected void initScheduleTable() {
        String sql = "create table if not exists schedule (" +
                "id bigint not null auto_increment primary key," +
                "theme_id bigint not null," +
                "date date not null," +
                "time time not null)";

        jdbcTemplate.execute("drop table schedule if exists");
        jdbcTemplate.execute(sql);
    }

    protected void 스케줄생성() {
        ScheduleCreateRequest request = new ScheduleCreateRequest(THEME_ID, DATE_STRING, TIME_STRING);

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/schedules")
                .then().log().all().extract();
    }

    protected void 예약생성() {
        ReservationCreateRequest request = new ReservationCreateRequest(SCHEDULE_ID, NAME);

        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/reservations")
                .then().log().all().extract();
    }
}
