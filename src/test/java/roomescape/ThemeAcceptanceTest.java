package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.theme.ThemeRequest;

import static org.hamcrest.Matchers.is;

@DisplayName("테마 관련 api 호출 테스트")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ThemeAcceptanceTest {
	@BeforeEach
	void 테마_등록() {
		ThemeRequest request = new ThemeRequest("테스트 테마", "이것은 테스트용 테마 입니다.", "thumbailName");

		RestAssured.given().log().all()
				.contentType(ContentType.JSON)
				.body(request)
				.when().post("/themes")
				.then().log().all()
				.statusCode(201)
				.body("id", is(1));
	}

	@Test
	void 테마_조회_성공() {
		RestAssured.given().log().all()
				.when().get("/themes")
				.then().log().all()
				.statusCode(200)
				.body("size()", is(1));
	}

	@Test
	void 테마_삭제_성공() {
		RestAssured.given().log().all()
				.when().delete("/themes/1")
				.then().log().all()
				.statusCode(204);

		RestAssured.given().log().all()
				.when().get("/themes")
				.then().log().all()
				.statusCode(204)
				.body("size()", is(0));
	}
}
