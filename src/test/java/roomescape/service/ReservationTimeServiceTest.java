package roomescape.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import roomescape.domain.ReservationTime;
import roomescape.dto.time.ReservationTimeRequest;
import roomescape.dto.time.ReservationTimeResponse;
import roomescape.dto.time.create.ReservationTimeCreateResponse;
import roomescape.exception.custom.DuplicatedReservationTimeException;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReservationTimeServiceTest {

    @Autowired
    ReservationTimeService reservationTimeService;

    @BeforeEach
    void init() {
        reservationTimeService.createTime(new ReservationTimeRequest("12:00"));
        reservationTimeService.createTime(new ReservationTimeRequest("13:00"));
    }


    @Test
    @DisplayName("예약 시간 리스트 테스트")
    void findTimes() {
        List<String> collect = reservationTimeService.findTimes().stream()
                .map(t -> t.getStartAt().toString())
                .collect(Collectors.toList());

        assertThat(collect.size()).isEqualTo(2);
        assertThat(collect).contains("12:00", "13:00");
    }

    @Test
    @DisplayName("예약 시간 추가")
    void createTime() {
        //given
        ReservationTimeRequest createTime = new ReservationTimeRequest("14:00");
        ReservationTimeRequest createTime2 = new ReservationTimeRequest("15:00");

        //when @BeforeEach init 메서드는 주석 처리
        //현재 두개의 예약시간만 등록한다는 가정
        ReservationTimeCreateResponse time = reservationTimeService.createTime(createTime);
        ReservationTimeCreateResponse time2 = reservationTimeService.createTime(createTime2);

        //then
        List<ReservationTimeResponse> list = reservationTimeService.findTimes();

        assertThat(list.size()).isEqualTo(2);
        assertThat(list.get(0).getStartAt()).isEqualTo(createTime.getStartAt());
    }

    @Test
    @DisplayName("예약 시간 삭제")
    void deleteTime() {

        reservationTimeService.deleteTime(1L);
        List<ReservationTimeResponse> times = reservationTimeService.findTimes();
        assertThat(times.size()).isEqualTo(1);
        assertThat(times).isNotEqualTo(2);
    }

    @Test
    @DisplayName("예외 발생")
    void checkException() {
        //init 메서드에 12:00 존재.
        //DuplicatedReservationTimeException 이미 등록된 예약시간에서 예외 발생
        assertThrows(DuplicatedReservationTimeException.class, ()->{
            reservationTimeService.createTime(new ReservationTimeRequest("12:00"));
        });
    }
}