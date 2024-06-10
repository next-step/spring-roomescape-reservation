package roomescape.admin.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import roomescape.admin.dto.SaveReservationRequest;
import roomescape.admin.dto.SaveReservationTimeRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ReservationTimeControllerTest {

    @Nested
    @DisplayName("예약 시간은")
    class Describe_reservation_time{

        @Test
        @DisplayName("저장한 뒤, 저장한 Dto를 리턴합니다.")
        void it_save_reservation_time_and_return_saved_dto(){
            SaveReservationTimeRequest 예약시간_10_10 = new SaveReservationTimeRequest("10:10");

            var response = RestAssured.given().log().all()
                    .body(예약시간_10_10)
                    .contentType(ContentType.JSON)
                    .when().post("/times")
                    .then().log().all().extract();

            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.jsonPath().getLong("id")).isPositive();
        }

        @Test
        @DisplayName("목록들을 조회할 수 있습니다.")
        void it_return_reservation_times()  {
            it_save_reservation_time_and_return_saved_dto();

            var response = RestAssured
                    .given().log().all()
                    .contentType(ContentType.JSON)
                    .when().get("/times")
                    .then().log().all().extract();

            var reservationTimes = response.as(List.class);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(reservationTimes).hasSize(1);
        }

        @Nested
        @DisplayName("삭제하는 예약 시간이 이미 예약에서 사용된다면")
        class Context_use_reservation_time_you_are_deleting_for_reservation{
            long given_삭제_예약시간_id;

            void given_예약시간_10_20(){
                SaveReservationTimeRequest 예약시간_10_20 = new SaveReservationTimeRequest("10:20");


                var response = RestAssured.given().log().all()
                        .body(예약시간_10_20)
                        .contentType(ContentType.JSON)
                        .when().post("/times")
                        .then().log().all().extract();

                given_삭제_예약시간_id = response.jsonPath().getLong("id");
            }

            void given_예약_10_20(){
                given_예약시간_10_20();
                SaveReservationRequest given_10_20분_예약 = new SaveReservationRequest("김민기","2024-01-10", given_삭제_예약시간_id);

                RestAssured.given().log().all()
                        .body(given_10_20분_예약)
                        .contentType(ContentType.JSON)
                        .when().post("/reservations")
                        .then().log().all().extract();
            }

            @Test
            @DisplayName("취소할 수 없고, 에러가 발생합니다.")
            void it_not_delete_reservation_time_and_return_error(){
                given_예약_10_20();

                var response = RestAssured
                        .given().log().all()
                        .contentType(ContentType.JSON)
                        .when().delete("/times/"+given_삭제_예약시간_id)
                        .then().log().all().extract();

                assertThat(response.statusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
        }

        @Nested
        @DisplayName("삭제하는 예약 시간이 예약에서 사용되지않는다면")
        class Context_not_use_reservation_time_you_are_deleting_for_reservation{
            void given_예약시간_10_30(){
                SaveReservationTimeRequest 예약시간_10_30 = new SaveReservationTimeRequest("10:30");

                RestAssured.given().log().all()
                        .body(예약시간_10_30)
                        .contentType(ContentType.JSON)
                        .when().post("/times")
                        .then().log().all().extract();
            }

            @Test
            @DisplayName("취소하고, void를 리턴합니다.")
            void it_delete_reservation_time_and_return_void(){
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