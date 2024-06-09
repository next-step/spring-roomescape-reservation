package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.reservation.ReservationRequest;
import roomescape.reservationTime.ReservationTimeRequest;
import roomescape.theme.ThemeRequest;

import static org.hamcrest.Matchers.is;

@DisplayName("예약 관련 api 호출 테스트")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationAcceptanceTest {
	@BeforeEach
	void 예약_등록() {
		ReservationTimeRequest reservationTimeRequest = new ReservationTimeRequest("12:00");
		ThemeRequest themeRequest = new ThemeRequest("탈출 미션", "탈출하는 내용입니다.", "thumbnail.jpg");
		ReservationRequest params = new ReservationRequest("브라운", "2023-08-05", 1L, 1L);

		RestAssured.given().log().all()
				.contentType(ContentType.JSON)
				.body(reservationTimeRequest)
				.when().post("/times")
				.then().log().all()
				.statusCode(200);

		RestAssured.given().log().all()
				.contentType(ContentType.JSON)
				.body(themeRequest)
				.when().post("/themes")
				.then().log().all()
				.statusCode(201)
				.body("id", is(1));

		RestAssured.given().log().all()
				.contentType(ContentType.JSON)
				.body(params)
				.when().post("/reservations")
				.then().log().all()
				.statusCode(200)
				.body("id", is(1));
	}

	@Test
	void 예약_조회_성공() {
		RestAssured.given().log().all()
				.when().get("/reservations")
				.then().log().all()
				.statusCode(200)
				.body("size()", is(1));
	}

	@Test
	void 예약_삭제_성공() {
		RestAssured.given().log().all()
				.when().delete("/reservations/1")
				.then().log().all()
				.statusCode(200);

		RestAssured.given().log().all()
				.when().get("/reservations")
				.then().log().all()
				.statusCode(200)
				.body("size()", is(0));
	}
}
