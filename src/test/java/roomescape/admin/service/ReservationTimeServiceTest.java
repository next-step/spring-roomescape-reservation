package roomescape.admin.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import roomescape.admin.dto.*;
import roomescape.common.exception.PolicyException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static roomescape.common.exception.PolicyException.RESERVATION_TIME_IN_USE_RESERVATION_ERROR;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ReservationTimeServiceTest {

    private final ThemeService themeService;
    private final ReservationTimeService reservationTimeService;
    private final ReservationService reservationService;

    public ReservationTimeServiceTest(ApplicationContext context){
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
    @DisplayName("예약 시간은")
    class Describe_reservation_time{
        @Nested
        @DisplayName("예약된 목록에서 사용중이라면")
        class Context_with_in_use_reservations{

            long given_삭제_예약시간_id;
            long given_테마_id;
            long given_예약_id;

            void given_테마(){
                given_테마_삭제(this.given_테마_id);

                SaveThemeRequest given_테마 = new SaveThemeRequest("테마", "테마에 대한 설명", "good/wow.png");
                ReadThemeResponse readThemeResponse = themeService.saveTheme(given_테마);

                this.given_테마_id = readThemeResponse.id();
            }

            void given_삭제_예약시간(){
                given_예약시간_삭제(this.given_삭제_예약시간_id);
                SaveReservationTimeRequest given_예약시간_10_00 = new SaveReservationTimeRequest("10:00");
                ReadReservationTimeResponse reservationTime = reservationTimeService.saveReservationTime(given_예약시간_10_00);

                this.given_삭제_예약시간_id = reservationTime.id();
            }

            void given_예약_2024_12_12(){
                given_예약_삭제(this.given_예약_id);
                given_테마();
                given_삭제_예약시간();
                SaveReservationRequest given_예약_2024_12_12 = new SaveReservationRequest("김민기","2024-12-12", given_삭제_예약시간_id, given_테마_id);
                ReadReservationResponse reservation = reservationService.saveReservation(given_예약_2024_12_12);

                this.given_예약_id = reservation.id();
            }

            @Test
            @DisplayName("삭제할 수 없고, 에러가 발생합니다.")
            void it_return_error_and_not_delete_reservation(){
                given_예약_2024_12_12();

                assertThatThrownBy(() ->  reservationTimeService.deleteReservationTime(given_삭제_예약시간_id))
                        .isInstanceOf(PolicyException.class)
                        .hasMessage(RESERVATION_TIME_IN_USE_RESERVATION_ERROR);
            }
        }

        @Nested
        @DisplayName("예약된 목록에서 사용하지 않는다면")
        class Context_with_in_not_use_reservations{
            long given_예약시간_10_30_id;

            void given_예약시간_10_30(){
                given_예약시간_삭제(this.given_예약시간_10_30_id);
                SaveReservationTimeRequest given_예약시간_10_30 = new SaveReservationTimeRequest("10:30");
                ReadReservationTimeResponse reservationTime = reservationTimeService.saveReservationTime(given_예약시간_10_30);

                given_예약시간_10_30_id = reservationTime.id();
            }

            @Test
            @DisplayName("삭제하고, void를 리턴합니다.")
            void it_return_void_and_delete_reservation_time(){
                given_예약시간_10_30();
                reservationTimeService.deleteReservationTime(this.given_예약시간_10_30_id);

                assertThatThrownBy(() ->  reservationTimeService.readReservationById(given_예약시간_10_30_id))
                        .isInstanceOf(EmptyResultDataAccessException.class);
            }
        }
    }

}