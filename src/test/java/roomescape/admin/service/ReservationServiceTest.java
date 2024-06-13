package roomescape.admin.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import roomescape.admin.dto.*;
import roomescape.common.exception.PolicyException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static roomescape.common.exception.PolicyException.DUPLICATE_RESERVATION_ERROR;
import static roomescape.common.exception.PolicyException.RESERVATION_PRIOR_TO_CURRENT_TIME_ERROR;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ReservationServiceTest {

    private final ThemeService themeService;
    private final ReservationTimeService reservationTimeService;
    private final ReservationService reservationService;

    public ReservationServiceTest(ApplicationContext context){
        this.themeService = context.getBean(ThemeService.class);
        this.reservationTimeService = context.getBean(ReservationTimeService.class);
        this.reservationService = context.getBean(ReservationService.class);
    }

    void given_테마_삭제(long id){
        this.themeService.deleteTheme(id);
    }

    void given_예약시간_삭제(long id){
        this.reservationTimeService.deleteReservationTime(id);
    }

    void given_예약_삭제(long id){
        this.reservationService.deleteReservation(id);
    }

    @Nested
    @DisplayName("방탈출 예약은")
    class Describe_Reservation{
        long given_예약_한시간전_id;
        long given_테마_id;
        long given_예약시간_10_00_id;
        long given_예약_id;

        void given_테마(){
            given_테마_삭제(given_테마_id);
            SaveThemeRequest given_테마 = new SaveThemeRequest("테마", "테마에 대한 설명", "good/wow.png");
            ReadThemeResponse readThemeResponse = themeService.saveTheme(given_테마);

            this.given_테마_id = readThemeResponse.id();
        }

        void given_예약시간_10_00(){
            given_예약시간_삭제(this.given_예약시간_10_00_id);
            SaveReservationTimeRequest given_예약시간_10_00 = new SaveReservationTimeRequest("10:00");
            ReadReservationTimeResponse reservationTime = reservationTimeService.saveReservationTime(given_예약시간_10_00);

            this.given_예약시간_10_00_id = reservationTime.id();
        }

        void given_예약_한시간전(){
            given_예약_삭제(this.given_예약_한시간전_id);

            LocalDateTime pastTime = LocalDateTime.now().minusHours(1);
            String formattedTime = pastTime.format(DateTimeFormatter.ofPattern("hh:mm"));

            SaveReservationTimeRequest given_예약_한시간_전 = new SaveReservationTimeRequest(formattedTime);
            ReadReservationTimeResponse reservationTime = reservationTimeService.saveReservationTime(given_예약_한시간_전);

            this.given_예약_한시간전_id = reservationTime.id();
        }

        void given_예약_2024_12_14(){
            given_예약_삭제(this.given_예약_id);
            given_테마();
            given_예약시간_10_00();
            SaveReservationRequest given_예약_2024_12_12 = new SaveReservationRequest("김민기","2024-12-14", given_예약시간_10_00_id, given_테마_id);

            ReadReservationResponse reservation = reservationService.saveReservation(given_예약_2024_12_12);

            this.given_예약_id = reservation.id();
        }

        @Test
        @DisplayName("중복된 예약을 할 경우, 에러를 리턴합니다.")
        void it_returns_error_for_duplicate_reservation(){
            given_예약_2024_12_14();
            SaveReservationRequest given_예약_2024_12_14= new SaveReservationRequest("김민기","2024-12-14", given_예약시간_10_00_id, given_테마_id);

            assertThatThrownBy(() -> reservationService.saveReservation(given_예약_2024_12_14))
                    .isInstanceOf(PolicyException.class)
                    .hasMessage(DUPLICATE_RESERVATION_ERROR);
        }

        @Test
        @DisplayName("지나간 시간으로 예약을 하면, 에러를 리턴합니다.")
        void it_returns_error_for_past_reservation_time(){
            given_예약_한시간전();
            SaveReservationRequest given_예약_2024_04_01= new SaveReservationRequest("김민기","2024-04-01", given_예약_한시간전_id, given_테마_id);

            assertThatThrownBy(() -> reservationService.saveReservation(given_예약_2024_04_01))
                    .isInstanceOf(PolicyException.class)
                    .hasMessage(RESERVATION_PRIOR_TO_CURRENT_TIME_ERROR);
        }

        @Test
        @DisplayName("지나간 날짜로 예약을 하면, 에러를 리턴합니다.")
        void it_returns_error_for_past_reservation_date(){
            given_예약시간_10_00();
            SaveReservationRequest given_예약_2024_01_01= new SaveReservationRequest("김민기","2024-01-01", given_예약시간_10_00_id, given_테마_id);

            assertThatThrownBy(() -> reservationService.saveReservation(given_예약_2024_01_01))
                    .isInstanceOf(PolicyException.class)
                    .hasMessage(RESERVATION_PRIOR_TO_CURRENT_TIME_ERROR);
        }
    }
}