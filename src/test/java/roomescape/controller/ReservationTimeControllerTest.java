package roomescape.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dto.reservation.create.ReservationCreateRequest;
import roomescape.dto.time.ReservationTimeRequest;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeControllerTest {

    @Autowired
    ReservationTimeService reservationTimeService;

    @BeforeEach
    void init() {
        reservationTimeService.createTime(new ReservationTimeRequest("12:00"));
    }

    @Test
    void create() {
        var response = RestAssured
                .given().log().all()
                .body(new ReservationTimeRequest("12:00"))
                .contentType(ContentType.JSON)
                .when().post("/times")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void findTimes() {
        var response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .when().get("/times")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void delete() {
        var response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .when().delete("/times/1");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}