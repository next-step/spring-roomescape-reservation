package nextstep.ui;

import static org.springframework.http.HttpStatus.CREATED;

import io.restassured.RestAssured;
import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.ui.request.ReservationCreateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("예약 생성")
    @Test
    void create() {
        ReservationCreateRequest request = new ReservationCreateRequest(
            LocalDate.of(2022, 10, 11),
            LocalTime.of(13, 0),
            "최현구"
        );

        RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/reservations")
            .then().log().all()
            .statusCode(CREATED.value())
            .header("Location", "/reservations/1");
    }
}
