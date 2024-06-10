package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.reservationTime.ReservationTimeRequest;

import java.text.ParseException;

import static org.hamcrest.Matchers.is;

@DisplayName("예약 시간 관련 api 호출 테스트")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationTimeAcceptanceTest {
	@BeforeEach
	void 예약_시간_등록() throws ParseException {
		ReservationTimeRequest request = new ReservationTimeRequest("12:00");

		RestAssured.given().log().all()
				.contentType(ContentType.JSON)
				.body(request)
				.when().post("/times")
				.then().log().all()
				.statusCode(200)
				.body("id", is(1));
	}

	@Test
	void 예약_시간_조회_성공() {
		RestAssured.given().log().all()
				.when().get("/times")
				.then().log().all()
				.statusCode(200)
				.body("size()", is(1));
	}

	@Test
	void 예약_시간_삭제_성공() {
		RestAssured.given().log().all()
				.when().delete("/times/1")
				.then().log().all()
				.statusCode(200);

		RestAssured.given().log().all()
				.when().get("/times")
				.then().log().all()
				.statusCode(200)
				.body("size()", is(0));
	}
}
