package roomescape.admin.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.Rollback;
import roomescape.admin.dto.SaveReservationRequest;
import roomescape.admin.dto.SaveReservationTimeRequest;
import roomescape.admin.dto.SaveThemeRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ReservationControllerTest {

    @Nested
    @DisplayName("방탈출 예약은")
    class Describe_Reservation{

        @Nested
        @DisplayName("데이터 유효성 검증을 진행할 때")
        class Context_with_validate_data{
            @Test
            @DisplayName("date에 숫자와 '-' 이외의 값을 입력하면, 에러를 리턴합니다.")
            void it_return_error_for_non_numeric_or_non_hyphen_in_date(){

            }

            @Test
            @DisplayName("date에 null, (공백) 값이 입력되면, 에러를 리턴합니다.")
            void it_return_error_for_null_or_empty_date(){

            }

            @Test
            @DisplayName("name에 문자이외의 값이 입력되면, 에러를 리턴합니다.")
            void it_return_error_for_non_string_in_name(){

            }

            @Test
            @DisplayName("name에 null, (공백) 값이 입력되면, 에러를 리턴합니다.")
            void it_return_error_for_null_or_empty_name(){

            }

        }

        @Nested
        @DisplayName("예약 시간과 테마를 저장하고")
        class Context_with_saving_reservation_time_and_theme{

            long given_예약시간_id;
            long given_테마_id;

            void given_예약_시간_10_20(){
                SaveReservationTimeRequest 예약시간_10_20 = new SaveReservationTimeRequest("10:20");

                var response = RestAssured.given().log().all()
                        .body(예약시간_10_20)
                        .contentType(ContentType.JSON)
                        .when().post("/times")
                        .then().log().all().extract();

                given_예약시간_id = response.jsonPath().getLong("id");
            }

            void given_테마(){
                SaveThemeRequest 테마1 = new SaveThemeRequest("테마1", "테마에 대한 설명", "good/wow.png");

                var response = RestAssured.given().log().all()
                        .body(테마1)
                        .contentType(ContentType.JSON)
                        .when().post("/themes")
                        .then().log().all().extract();

                given_테마_id = response.jsonPath().getLong("id");
            }

            @Test
            @DisplayName("예약한 뒤, 저장한 Dto를 리턴합니다.")
            @Rollback
            void it_return_saved_dto_after_saving_reservation(){
                given_예약_시간_10_20();
                given_테마();
                SaveReservationRequest 예약_10_20분 = new SaveReservationRequest("김민기","2024-01-10", given_예약시간_id, given_테마_id);

                var response = RestAssured
                        .given().log().all()
                        .body(예약_10_20분)
                        .contentType(ContentType.JSON)
                        .when().post("/reservations")
                        .then().log().all().extract();

                assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
                assertThat(response.jsonPath().getString("name")).isEqualTo("김민기");
            }
        }

        @Test
        @DisplayName("목록 List를 리턴합니다.")
        @Rollback
        void it_return_reservations(){
            Context_with_saving_reservation_time_and_theme reservationTime = new Context_with_saving_reservation_time_and_theme();
            reservationTime.it_return_saved_dto_after_saving_reservation();

            var response = RestAssured
                    .given().log().all()
                    .contentType(ContentType.JSON)
                    .when().get("/reservations")
                    .then().log().all().extract();

            var reservations = response.as(List.class);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(reservations).hasSize(1);
        }


        @Test
        @DisplayName("삭제하고, void를 리턴합니다.")
        @Rollback
        void it_return_void_and_delete_reservation() {
            Context_with_saving_reservation_time_and_theme reservationTime = new Context_with_saving_reservation_time_and_theme();
            reservationTime.it_return_saved_dto_after_saving_reservation();

            var response = RestAssured
                    .given().log().all()
                    .contentType(ContentType.JSON)
                    .when().delete("/reservations/1")
                    .then().log().all().extract();

            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        }
    }

}