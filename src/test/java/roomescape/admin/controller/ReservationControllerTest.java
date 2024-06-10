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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ReservationControllerTest {

    @Nested
    @DisplayName("방탈출 예약은")
    class Describe_Reservation{

        @Nested
        @DisplayName("예약 시간을 저장하고")
        class Context_with_saving_reservation_time{


            void given_예약_시간_10_20(){
                SaveReservationTimeRequest 예약시간_10_20 = new SaveReservationTimeRequest("10:20");

                RestAssured.given().log().all()
                        .body(예약시간_10_20)
                        .contentType(ContentType.JSON)
                        .when().post("/times")
                        .then().log().all().extract();
            }

            @Test
            @DisplayName("예약한 뒤, 저장한 Dto를 리턴합니다.")
            @Rollback
            void it_save_reservation_and_return_saved_dto(){
                given_예약_시간_10_20();
                SaveReservationRequest 예약_10_20분 = new SaveReservationRequest("김민기","2024-01-10", 1L);

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
        @DisplayName("목록을 조회할 수 있습니다.")
        @Rollback
        void it_return_reservations(){
            Context_with_saving_reservation_time reservationTime = new Context_with_saving_reservation_time();
            reservationTime.it_save_reservation_and_return_saved_dto();

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
        @DisplayName("취소하고, void를 리턴합니다.")
        @Rollback
        void it_delete_reservation_and_return_void() {
            Context_with_saving_reservation_time reservationTime = new Context_with_saving_reservation_time();
            reservationTime.it_save_reservation_and_return_saved_dto();

            var response = RestAssured
                    .given().log().all()
                    .contentType(ContentType.JSON)
                    .when().delete("/reservations/1")
                    .then().log().all().extract();

            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        }
    }

}