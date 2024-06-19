package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.reservation.ui.dto.ReservationRequest;
import roomescape.reservationtime.ui.dto.ReservationTimeRequest;
import roomescape.reservation.application.ReservationService;
import roomescape.reservationtime.application.ReservationTimeService;
import roomescape.theme.application.ThemeService;
import roomescape.theme.ui.dto.ThemeRequest;
import roomescape.theme.ui.dto.ThemeResponse;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = {"server.port=8888"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ThemeTest {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ReservationTimeService reservationTimeService;
    @Autowired
    private ThemeService themeService;
    private final String URL = "http://localhost:8888";

    private long makeDummyReservation() {
        long timeId = reservationTimeService.add(ReservationTimeRequest.create("13:00"));
        long themeId = themeService.add(ThemeRequest.create("a", "b", "c"));
        String date = LocalDate.now().plusWeeks(1).toString();
        return reservationService.make(ReservationRequest.create("yeeun", date, timeId, themeId));
    }

    @Test
    @DisplayName("ThemeController - create()")
    void 테마_생성() {
        String name = "수키도키";
        String description = "흐르는 대로 살자 해파리처럼🪼";
        String thumbnail = "https://pbs.twimg.com/media/GApx6fjagAAkFsX.jpg";

        var response = RestAssured
                .given().log().all()
                .body(ThemeRequest.create(name, description, thumbnail))
                .contentType(ContentType.JSON)
                .when().post(URL + "/themes")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        ThemeResponse body = response.body().as(ThemeResponse.class);
        assertThat(body.getName()).isEqualTo(name);
        assertThat(body.getDescription()).isEqualTo(description);
        assertThat(body.getThumbnail()).isEqualTo(thumbnail);
    }

    @Test
    @DisplayName("ThemeController - create() : duplicated name")
    void 중복_이름_테마_생성() {
        테마_생성();
        String name = "수키도키";
        String description = "흐르는 대로 살자 해파리처럼🪼";
        String thumbnail = "https://pbs.twimg.com/media/GApx6fjagAAkFsX.jpg";

        var response = RestAssured
                .given().log().all()
                .body(ThemeRequest.create(name, description, thumbnail))
                .contentType(ContentType.JSON)
                .when().post(URL + "/themes")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("ThemeController - read() : all")
    void 전체_테마_조회() {
        테마_생성();

        var response = RestAssured
                .given().log().all()
                .when().get(URL + "/themes")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList("", ThemeResponse.class)).hasSize(1);
    }

    @Test
    @DisplayName("ThemeController - read() : no created theme")
    void 등록된_예약_시간_없는_경우__예약_시간_조회() {
        var response = RestAssured
                .given().log().all()
                .when().get(URL + "/themes")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList("", ThemeResponse.class)).hasSize(0);
    }

    @Test
    @DisplayName("ThemeController - delete()")
    void 테마_삭제() {
        테마_생성();

        var response = RestAssured
                .given().log().all()
                .when().delete(URL + "/themes/1")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("ThemeController - delete() : non existent theme")
    void 존재하지_않는_테마_삭제() {
        var response = RestAssured
                .given().log().all()
                .when().delete(URL + "/themes/1")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("ThemeController - delete() : theme with reservation existing")
    void 예약_존재하는_테마_삭제() {
        long reservationId = makeDummyReservation();
        var response = RestAssured
                .given().log().all()
                .when().delete(URL + "/themes/" + reservationId)
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
