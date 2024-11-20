package roomescape.domain.reservation.api;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.common.exception.CustomErrorCode;
import roomescape.support.RestAssuredTestSupport;

import static org.hamcrest.Matchers.equalTo;

class ReservationCommandApiTest extends RestAssuredTestSupport {

    @DisplayName("예약 삭제 시 해당 예약이 존재하지 않는 경우 404를 응답한다")
    @Test
    void delete_fail() {
        // given
        final long nonExistingReservationId = -1L;

        // when & then
        RestAssured.given().log().all()
                .when().pathParam("reservationId", nonExistingReservationId).post("/reservations/{reservationId}/cancel")
                .then().log().all()
                .statusCode(404)
                .body("statusCode", equalTo(404))
                .body("httpStatus", equalTo("NOT_FOUND"))
                .body("data.errorCode", equalTo(CustomErrorCode.R404.name()));
    }
}
