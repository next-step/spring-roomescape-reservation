package roomescape.admin.service;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import roomescape.admin.dto.SaveReservationRequest;
import roomescape.admin.dto.SaveReservationTimeRequest;
import roomescape.admin.dto.SaveThemeRequest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ReservationTimeServiceTest {

    @Nested
    @DisplayName("예약 시간은")
    class Describe_reservation_time{
        @Nested
        @DisplayName("예약된 목록에서 사용중이라면")
        class Context_with_in_use_reservations{
            long given_삭제_예약시간_id;
            long given_테마_id;

            void given_예약시간_10_20(){
                SaveReservationTimeRequest 예약시간_10_20 = new SaveReservationTimeRequest("10:20:00");

                var response = RestAssured.given().log().all()
                        .body(예약시간_10_20)
                        .contentType(ContentType.JSON)
                        .when().post("/times")
                        .then().log().all().extract();

                given_삭제_예약시간_id = response.jsonPath().getLong("id");
            }

            void given_테마(){
                SaveThemeRequest 테마 = new SaveThemeRequest("테마","테마설명","썸네일 URL");

                var response = RestAssured.given().log().all()
                        .body(테마)
                        .contentType(ContentType.JSON)
                        .when().post("/themes")
                        .then().log().all().extract();

                given_테마_id = response.jsonPath().getLong("id");
            }

            void given_예약_2024_12_12(){
                given_테마();
                given_예약시간_10_20();
                SaveReservationRequest given_예약_2024_12_12 = new SaveReservationRequest("김민기","2024-12-12", given_삭제_예약시간_id, given_테마_id);

                RestAssured.given().log().all()
                        .body(given_예약_2024_12_12)
                        .contentType(ContentType.JSON)
                        .when().post("/reservations")
                        .then().log().all().extract();
            }

            @Test
            @DisplayName("삭제할 수 없고, 에러가 발생합니다.")
            void it_return_error_and_not_delete_reservation(){
                given_예약_2024_12_12();

                var response = RestAssured
                        .given().log().all()
                        .contentType(ContentType.JSON)
                        .when().delete("/times/"+given_삭제_예약시간_id)
                        .then().log().all().extract();

                assertThat(response.statusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
        }

        @Nested
        @DisplayName("예약된 목록에서 사용하지 않는다면")
        class Context_with_in_not_use_reservations{
            void given_예약시간_10_30(){
                SaveReservationTimeRequest 예약시간_10_30 = new SaveReservationTimeRequest("10:30:00");

                RestAssured.given().log().all()
                        .body(예약시간_10_30)
                        .contentType(ContentType.JSON)
                        .when().post("/times")
                        .then().log().all().extract();
            }

            @Test
            @DisplayName("삭제하고, void를 리턴합니다.")
            void it_return_void_and_delete_reservation_time(){
                given_예약시간_10_30();

                var response = RestAssured
                        .given().log().all()
                        .contentType(ContentType.JSON)
                        .when().delete("/times/1")
                        .then().log().all().extract();

                assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            }
        }
    }

}
