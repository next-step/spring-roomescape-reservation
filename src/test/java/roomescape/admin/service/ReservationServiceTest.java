package roomescape.admin.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ReservationServiceTest {
    @Nested
    @DisplayName("방탈출 예약은")
    class Describe_Reservation{

        void given_예약_4월_1일_10시_1(){

        }

        void given_예약_4월_1일_10시_2(){

        }

        void given_현재시간보다_전_시간으로_예약(){

        }

        void given_현재날짜보다_전_날짜로_예약(){

        }

        @Test
        @DisplayName("중복된 예약을 할 경우, 에러를 리턴합니다.")
        void it_returns_error_for_duplicate_reservation(){
            given_예약_4월_1일_10시_1();
            given_예약_4월_1일_10시_2();
        }

        @Test
        @DisplayName("지나간 시간으로 예약을 하면, 에러를 리턴합니다.")
        void it_returns_error_for_past_reservation_time(){
            given_현재시간보다_전_시간으로_예약();
        }

        @Test
        @DisplayName("지나간 날짜로 예약을 하면, 에러를 리턴합니다.")
        void it_returns_error_for_past_reservation_date(){
            given_현재날짜보다_전_날짜로_예약();
        }


    }
}
