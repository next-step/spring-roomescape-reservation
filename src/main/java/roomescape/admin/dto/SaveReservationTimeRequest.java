package roomescape.admin.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record SaveReservationTimeRequest(
        @NotEmpty(message = "예약 시간은 비어 있을 수 없습니다.")
        @Pattern(regexp = "^[0-9:]+$", message = "예약 시간은 숫자와 ':'만 포함될 수 있습니다.")
        String startAt) {
}
