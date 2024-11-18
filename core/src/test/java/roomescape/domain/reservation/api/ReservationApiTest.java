package roomescape.domain.reservation.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.domain.reservationtime.domain.ReservationTime;
import roomescape.domain.reservationtime.domain.ReservationTimeRepository;
import roomescape.domain.theme.domain.Theme;
import roomescape.domain.theme.domain.ThemeRepository;
import roomescape.support.RestAssuredTestSupport;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;

class ReservationApiTest extends RestAssuredTestSupport {

    @Autowired
    ReservationTimeRepository timeRepository;

    @Autowired
    ThemeRepository themeRepository;

    @DisplayName("예약을 생성한다")
    @Test
    void create_reservation() {
        final ReservationTime time = ReservationTime.builder()
                .startAt(LocalTime.of(12, 0))
                .createdAt(LocalDateTime.of(2024, 6, 23, 7, 0))
                .build();
        final ReservationTime savedTime = timeRepository.save(time);
        final Theme themeSaved = saveTheme("theme-name", "theme-description", "theme-thumbnail");

        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");

        // fixme service 통합 테스트에서는 fake clock holder를 썼지만 실제 통합 테스트에서는 어떻게 fakeClockHolder를 넣어줘야할까?
        params.put("date", "2099-08-05");
        params.put("timeId", String.valueOf(savedTime.getIdValue()));
        params.put("themeId", String.valueOf(themeSaved.getId().value()));

        final ValidatableResponse response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);

        // 조회
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("data.size()", is(1));

        final Long reservationId = ((Integer) response.extract().path("data.reservationId")).longValue();

        // 취소
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().pathParam("reservationId", reservationId).post("/reservations/{reservationId}/cancel")
                .then().log().all()
                .statusCode(200);

        // 조회
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("data.size()", is(1))
                .body("data[0].status", is("CANCELED"));
    }

    private Theme saveTheme(String name, String description, String thumbnail) {
        final Theme theme = Theme.builder()
                .name(name)
                .description(description)
                .thumbnail(thumbnail)
                .deleted(false)
                .build();
        return themeRepository.save(theme);
    }
}
