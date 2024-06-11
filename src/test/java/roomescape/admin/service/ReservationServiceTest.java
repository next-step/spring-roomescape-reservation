package roomescape.admin.service;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import roomescape.admin.dto.*;
import roomescape.admin.entity.ReservationTime;
import roomescape.admin.entity.Theme;
import roomescape.admin.repository.ReservationRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ReservationServiceTest {

    @Nested
    @DisplayName("방탈출 예약은")
    class Describe_Reservation{
        long given_예약시간_id;

        long given_지나간_예약시간_id;


        long given_테마_id;


        void given_예약_시간_한시간전(){
            LocalDateTime pastTime = LocalDateTime.now().minusHours(1);
            String formattedTime = pastTime.format(DateTimeFormatter.ofPattern("hh:mm"));

            SaveReservationTimeRequest given_예약시간_10_00 = new SaveReservationTimeRequest(formattedTime);

            var response = RestAssured.given().log().all()
                    .body(given_예약시간_10_00)
                    .contentType(ContentType.JSON)
                    .when().post("/times");

            given_지나간_예약시간_id = response.jsonPath().getLong("id");
        }

        void given_예약_시간_10_00(){
            SaveReservationTimeRequest given_예약시간_10_00 = new SaveReservationTimeRequest("10:00");

            var response = RestAssured.given().log().all()
                    .body(given_예약시간_10_00)
                    .contentType(ContentType.JSON)
                    .when().post("/times");

            given_예약시간_id = response.jsonPath().getLong("id");
        }

        void given_테마(){
            SaveThemeRequest given_테마 = new SaveThemeRequest("테마", "테마에 대한 설명", "good/wow.png");

            var response = RestAssured.given().log().all()
                    .body(given_테마)
                    .contentType(ContentType.JSON)
                    .when().post("/themes");

            given_테마_id = response.jsonPath().getLong("id");
        }

        void given_예약_4월_1일_10시(){
            given_예약_시간_10_00();
            given_테마();
            SaveReservationRequest given_예약_2024_04_01 = new SaveReservationRequest("김민기","2024-04-01", given_예약시간_id, given_테마_id);

            RestAssured.given().log().all()
                    .body(given_예약_2024_04_01)
                    .contentType(ContentType.JSON)
                    .when().post("/reservations");
        }

        @Test
        @DisplayName("중복된 예약을 할 경우, 에러를 리턴합니다.")
        void it_returns_error_for_duplicate_reservation(){
            given_예약_4월_1일_10시();

            SaveReservationRequest given_예약_2024_04_01= new SaveReservationRequest("김민기","2024-04-01", given_예약시간_id, given_테마_id);

            var response = RestAssured
                    .given().log().all()
                    .body(given_예약_2024_04_01)
                    .contentType(ContentType.JSON)
                    .when().post("/reservations")
                    .then().log().all().extract();

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        @DisplayName("지나간 시간으로 예약을 하면, 에러를 리턴합니다.")
        void it_returns_error_for_past_reservation_time(){
            given_예약_시간_한시간전();
            given_테마();
            SaveReservationRequest given_예약_2024_04_01= new SaveReservationRequest("김민기","2024-04-01", given_지나간_예약시간_id, given_테마_id);

            var response = RestAssured
                    .given().log().all()
                    .body(given_예약_2024_04_01)
                    .contentType(ContentType.JSON)
                    .when().post("/reservations")
                    .then().log().all().extract();

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        @DisplayName("지나간 날짜로 예약을 하면, 에러를 리턴합니다.")
        void it_returns_error_for_past_reservation_date(){
            given_예약_시간_한시간전();
            given_테마();
            SaveReservationRequest given_예약_2024_01_01= new SaveReservationRequest("김민기","2024-01-01", given_지나간_예약시간_id, given_테마_id);

            var response = RestAssured
                    .given().log().all()
                    .body(given_예약_2024_01_01)
                    .contentType(ContentType.JSON)
                    .when().post("/reservations")
                    .then().log().all().extract();

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }
    }
}