package roomescape;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

  @BeforeEach
  void initTimes() {
    postReservationTime(LocalTime.now().plusMinutes(20).toString());
    postReservationTime(LocalTime.now().minusMinutes(10).toString());
    postTheme("레벨2 탈출", "우테코 레벨2를 탈출하는 내용입니다.",
        "https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg");
    postTheme("레벨3 탈출", "우테코 레벨2를 탈출하는 내용입니다.",
        "https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg");
  }

  private void postReservationTime(String time) {
    Map<String, Object> params = new HashMap<>();
    params.put("startAt", time);
    RestAssured.given().log().all().contentType(ContentType.JSON).body(params).when().post("/times")
        .then().log().all().statusCode(201);
  }

  private void postTheme(String name, String description, String thumbnail) {
    Map<String, Object> params = new HashMap<>();
    params.put("name", name);
    params.put("description", description);
    params.put("thumbnail", thumbnail);
    RestAssured.given().log().all().contentType(ContentType.JSON).body(params).when()
        .post("/themes").then().log().all().statusCode(201);
  }

  @Test
  void page() {
    RestAssured.given().log().all().when().get("/").then().log().all().statusCode(200);
  }

  @Test
  @DisplayName("생성 및 조회, 삭제 후 조회 테스트")
  void reservation() {
    Map<String, Object> params = new HashMap<>();
    params.put("name", "브라운");
    params.put("date", "2024-08-05");
    params.put("timeId", 1L);
    params.put("themeId", 1L);

    RestAssured.given().log().all().contentType(ContentType.JSON).accept(ContentType.ANY)
        .body(params).when().post("/reservations").then().log().all().statusCode(201)
        .body("id", is(1));

    RestAssured.given().log().all().when().get("/reservations").then().log().all().statusCode(200)
        .body("size()", is(1));

    RestAssured.given().log().all().when().delete("/reservations/1").then().log().all()
        .statusCode(204);

    RestAssured.given().log().all().when().get("/reservations").then().log().all().statusCode(200)
        .body("size()", is(0));
  }

  @Test
  @DisplayName("이전 날짜 예약  예외")
  void reservationNotValid() {
    Map<String, Object> params = new HashMap<>();
    params.put("name", "브라운");
    params.put("date", "2023-08-05");
    params.put("timeId", 1L);
    params.put("themeId", 1L);

    RestAssured.given().log().all().contentType(ContentType.JSON).accept(ContentType.ANY)
        .body(params).when().post("/reservations").then().log().all().statusCode(400);
    RestAssured.given().log().all().when().get("/reservations").then().log().all().statusCode(200)
        .body("size()", is(0));

    RestAssured.given().log().all().when().delete("/reservations/1").then().log().all()
        .statusCode(404);

    RestAssured.given().log().all().when().get("/reservations").then().log().all().statusCode(200)
        .body("size()", is(0));
  }

  @Test
  @DisplayName("당일 이전 시간 예약  예외")
  void reservationTimeNotValid() {
    Map<String, Object> params = new HashMap<>();
    params.put("name", "브라운");
    params.put("date", LocalDate.now().toString());
    params.put("timeId", 2L);
    params.put("themeId", 1L);

    RestAssured.given().log().all().contentType(ContentType.JSON).accept(ContentType.ANY)
        .body(params).when().post("/reservations").then().log().all().statusCode(400);
    RestAssured.given().log().all().when().get("/reservations").then().log().all().statusCode(200)
        .body("size()", is(0));

    RestAssured.given().log().all().when().delete("/reservations/1").then().log().all()
        .statusCode(404);

    RestAssured.given().log().all().when().get("/reservations").then().log().all().statusCode(200)
        .body("size()", is(0));
  }

  @Test
  @DisplayName("예약중인 시간대, 테마 삭제 시도 예외")
  void themeTimeAreUsed() {
    Map<String, Object> params = new HashMap<>();
    params.put("name", "브라운");
    params.put("date", LocalDate.now().toString());
    params.put("timeId", 1L);
    params.put("themeId", 1L);

    RestAssured.given().log().all().contentType(ContentType.JSON).accept(ContentType.ANY)
        .body(params).when().post("/reservations").then().log().all().statusCode(201);
    RestAssured.given().log().all().contentType(ContentType.JSON).accept(ContentType.ANY)
        .body(params).when().post("/times/1").then().log().all().statusCode(405);
    RestAssured.given().log().all().contentType(ContentType.JSON).accept(ContentType.ANY)
        .body(params).when().post("/themes/1").then().log().all().statusCode(405);
  }

  @Test
  @DisplayName("not found 예외 테스트")
  void notFound() {
    Map<String, Object> params = new HashMap<>();
    params.put("name", "브라운");
    params.put("date", LocalDate.now().toString());
    params.put("timeId", 3L);
    params.put("themeId", 1L);

    RestAssured.given().log().all().contentType(ContentType.JSON).accept(ContentType.ANY)
        .body(params).when().post("/reservations").then().log().all().statusCode(404);
    params.put("timeId", 1L);
    params.put("themeId", 3L);
    RestAssured.given().log().all().contentType(ContentType.JSON).accept(ContentType.ANY)
        .body(params).when().post("/reservations").then().log().all().statusCode(404);

    RestAssured.given().log().all().contentType(ContentType.JSON).accept(ContentType.ANY)
        .when().delete("/reservations/2").then().log().all().statusCode(404);
    RestAssured.given().log().all().contentType(ContentType.JSON).accept(ContentType.ANY)
        .when().delete("/themes/3").then().log().all().statusCode(404);
    RestAssured.given().log().all().contentType(ContentType.JSON).accept(ContentType.ANY)
        .when().delete("/times/3").then().log().all().statusCode(404);
  }
}
