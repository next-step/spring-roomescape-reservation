package roomescape.reservation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import roomescape.reservationTime.ReservationTimeRequestDto;

public class ReservationRequestDto {
    @NotBlank(message = "예약자 명 입력해주세요")
    private String name;
    @NotBlank(message = "예약일자를 입력해주세요")
    private String date;
    private ReservationTimeRequestDto reservationTimeRequestDto;

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public ReservationTimeRequestDto getReservationTimeRequestDto() {
        return reservationTimeRequestDto;
    }

    public ReservationRequestDto(String name, String date, ReservationTimeRequestDto reservationTimeRequestDto) {
        this.name = name;
        this.date = date;
        this.reservationTimeRequestDto = reservationTimeRequestDto;
    }

    public static class Builder {
        private String name;
        private String date;
        private ReservationTimeRequestDto reservationTimeRequestDto;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder date(String date) {
            this.date = date;
            return this;
        }

        public Builder reservationTimeRequestDto(ReservationTimeRequestDto reservationTimeRequestDto) {
            this.reservationTimeRequestDto = reservationTimeRequestDto;
            return this;
        }

        public ReservationRequestDto build() {
            return new ReservationRequestDto(name, date, reservationTimeRequestDto);
        }
    }
}
