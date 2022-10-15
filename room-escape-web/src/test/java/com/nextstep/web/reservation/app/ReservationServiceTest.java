package com.nextstep.web.reservation.app;

import com.nextstep.web.reservation.dto.CreateReservationRequest;
import com.nextstep.web.reservation.dto.ReservationResponse;
import nextsetp.domain.reservation.exception.DuplicationReservationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@SpringBootTest
class ReservationServiceTest {
    @Autowired
    private ReservationService reservationService;
    String testDate = "2022-08-11";
    String testTime = "13:00";

    @BeforeEach
    void setUp() {
        CreateReservationRequest request = new CreateReservationRequest
                (LocalDate.parse(testDate), LocalTime.parse(testTime), "a");

        reservationService.save(request);
    }

    @AfterEach
    void tearDown() {
        reservationService.delete(testDate, testTime);
    }

    @DisplayName("해당 날짜의 예약을 조회한다.")
    @Test
    void findAllBy() {
        List<ReservationResponse> reservations = reservationService.findAllBy(testDate);
        Assertions.assertThat(reservations).hasSize(1);
    }

    @DisplayName("예약을 생성한다.")
    @Test
    void save() {
        String date = "2022-01-01";
        String time = "00:00";
        CreateReservationRequest request = new CreateReservationRequest
                (LocalDate.parse(date), LocalTime.parse(time), "a");
        Long id = reservationService.save(request);
        Assertions.assertThat(id).isNotNull();
    }

    @DisplayName("해당 날짜와 시간의 예약을 삭제한다.")
    @Test
    void delete() {
        String time = "00:00";
        CreateReservationRequest request = new CreateReservationRequest
                (LocalDate.parse(testDate), LocalTime.parse(time), "a");
        reservationService.save(request);
        reservationService.delete(testDate, time);
        Assertions.assertThat(reservationService.findAllBy(testDate)).hasSize(1);
    }

    @DisplayName("같은 날 같은 시간에 예약은 중복 생성 할 수 없다.")
    @Test
    void checkSaveDuplicated() {
        String duplicateDate = "2022-08-11";
        String duplicateTime = "13:00";
        CreateReservationRequest failRequest = new CreateReservationRequest
                (LocalDate.parse(duplicateDate), LocalTime.parse(duplicateTime), "b");

        Assertions.assertThatThrownBy(() -> {
            reservationService.save(failRequest);
        }).isInstanceOf(DuplicationReservationException.class);
    }
}