package com.nextstep.web.reservation.app;

import com.nextstep.web.reservation.dto.CreateReservationRequest;
import com.nextstep.web.reservation.dto.ReservationResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReservationQueryServiceTest {
    @Autowired
    private ReservationQueryService reservationQueryService;
    @Autowired
    private ReservationCommandService reservationCommandService;

    String testDate = "2022-08-11";
    String testTime = "13:00";
    CreateReservationRequest testRequest = new CreateReservationRequest
            (LocalDate.parse(testDate), LocalTime.parse(testTime), "a");

    @BeforeEach
    void setUp() {
        reservationCommandService.save(testRequest);
    }

    @DisplayName("해당 날짜의 예약을 조회한다.")
    @Test
    void findAllBy() {
        List<ReservationResponse> reservations = reservationQueryService.findAllBy(testDate);
        Assertions.assertThat(reservations).hasSize(1);
    }

}