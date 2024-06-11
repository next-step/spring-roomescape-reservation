package roomescape.admin.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import roomescape.admin.dto.SaveReservationTimeRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ReservationTimeControllerTest {

    @Nested
    @DisplayName("예약 시간은")
    class Describe_reservation_time{

        @Nested
        @DisplayName("데이터 유효성 검증을 진행할 때")
        class Context_with_validate_data{
            @Test
            @DisplayName("startAt에 숫자와 ':' 이외의 값을 넣으면, 에러를 리턴합니다.")
            void it_return_error_for_non_numeric_or_non_colon_in_startAt(){

            }

            @Test
            @DisplayName("startAt에 null, (공백) 값이 입력되면, 에러를 리턴합니다.")
            void it_return_error_for_null_or_empty_startAt(){

            }

        }

        @Test
        @DisplayName("입력한 뒤, 저장한 Dto를 리턴합니다.")
        void it_return_saved_dto_after_saving_reservation_time(){
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
        @DisplayName("목록 List를 리턴합니다.")
        void it_return_reservation_times()  {
            it_return_saved_dto_after_saving_reservation_time();

            var response = RestAssured
                    .given().log().all()
                    .contentType(ContentType.JSON)
                    .when().get("/times")
                    .then().log().all().extract();

            var reservationTimes = response.as(List.class);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(reservationTimes).hasSize(1);
        }

        @Test
        @DisplayName("삭제하고, void를 리턴합니다.")
        void it_return_void_and_delete_reservation_time(){
            it_return_saved_dto_after_saving_reservation_time();

            var response = RestAssured
                    .given().log().all()
                    .contentType(ContentType.JSON)
                    .when().delete("/times/1")
                    .then().log().all().extract();

            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        }

    }
}