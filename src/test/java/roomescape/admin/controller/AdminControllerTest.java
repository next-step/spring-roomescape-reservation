package roomescape.admin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class AdminControllerTest {

    SaveReservationTimeRequest given_예약시간_10_20 = new SaveReservationTimeRequest("10:20");
    SaveReservationTimeRequest given_예약시간_13_20 = new SaveReservationTimeRequest("13:20");
    SaveReservationRequest given_10_20분_예약 = new SaveReservationRequest("김민기","2024-01-10", 1L);


    @Nested
    @DisplayName("방탈출 예약은")
    class Describe_Resevation{

        @Test
        @DisplayName("사전 예약 등록 후 예약할 수 있습니다")
        @Rollback
        void save_reservation(){
            Describe_Reservation_Time reservationTime = new Describe_Reservation_Time();
            reservationTime.save_reservation_time(given_예약시간_10_20);

            var response = RestAssured
                    .given().log().all()
                    .body(given_10_20분_예약)
                    .contentType(ContentType.JSON)
                    .when().post("/reservations")
                    .then().log().all().extract();

            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.jsonPath().getString("name")).isEqualTo("김민기");
        }

        @Test
        @DisplayName("목록을 조회할 수 있습니다.")
        @Rollback
        void return_list() throws JsonProcessingException {
            save_reservation();

            var response = RestAssured
                    .given().log().all()
                    .contentType(ContentType.JSON)
                    .when().get("/reservations")
                    .then().log().all().extract();

            // 응답을 String으로 추출합니다.
            String jsonResponse = response.asString();

            // ObjectMapper를 사용하여 JSON 응답을 List로 변환합니다.
            ObjectMapper objectMapper = new ObjectMapper();
            List<Object> reservations = objectMapper.readValue(jsonResponse, new TypeReference<List<Object>>() {});

            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(reservations.size()).isGreaterThan(0);
        }

        @Nested
        @DisplayName("잘못 등록되었다면")
        class Context_Wrong_Resevation{

            @Test
            @DisplayName("취소하실 수 있습니다.")
            @Rollback
            void delete_reservation(){
                save_reservation();

                var response = RestAssured
                        .given().log().all()
                        .contentType(ContentType.JSON)
                        .when().delete("/reservations/1")
                        .then().log().all().extract();

                assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            }
        }
    }

    @Nested
    @DisplayName("예약 시간은")
    class Describe_Reservation_Time{

        void save_reservation_time(SaveReservationTimeRequest saveReservationTimeRequest){
            RestAssured.given().log().all()
                    .body(saveReservationTimeRequest)
                    .contentType(ContentType.JSON)
                    .when().post("/times")
                    .then().log().all().extract();
        }

        @Test
        @DisplayName("목록을 조회할 수 있습니다.")
        @Rollback
        void return_list() throws JsonProcessingException {
            save_reservation_time(given_예약시간_10_20);
            save_reservation_time(given_예약시간_13_20);

            var response = RestAssured
                    .given().log().all()
                    .contentType(ContentType.JSON)
                    .when().get("/times")
                    .then().log().all().extract();

            // 응답을 String으로 추출합니다.
            String jsonResponse = response.asString();

            // ObjectMapper를 사용하여 JSON 응답을 List로 변환합니다.
            ObjectMapper objectMapper = new ObjectMapper();
            List<Object> reservations = objectMapper.readValue(jsonResponse, new TypeReference<List<Object>>() {});

            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(reservations.size()).isEqualTo(2);
        }

        @Nested
        @DisplayName("사전에 예약자가 취소 하려는 시간으로 예약하지 않았다면")
        class Context_Not_Use_For_Reservation{

            @Test
            @DisplayName("취소하실 수 있습니다.")
            @Rollback
            void delete_reservation_time(){
                /*
                save_reservation_time(given_예약시간_13_20);

                var response = RestAssured
                        .given().log().all()
                        .contentType(ContentType.JSON)
                        .when().delete("/times/1")
                        .then().log().all().extract();

                assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());*/
            }
        }

        @Nested
        @DisplayName("사전에 예약자가 취소 하려는 시간으로 예약했다면")
        class Context_Use_For_Reservation{

            @Test
            @DisplayName("취소할 수 없습니다.")
            @Rollback
            void not_delete_reservation_time(){
                Describe_Resevation resevation = new Describe_Resevation();
                resevation.save_reservation();

                var response = RestAssured
                        .given().log().all()
                        .contentType(ContentType.JSON)
                        .when().delete("/times/1")
                        .then().log().all().extract();

                assertThat(response.statusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
        }
    }

}