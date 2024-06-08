package roomescape.adapter.in.web.dto;

import roomescape.annotation.TimeCheck;

public record ReservationTimeCommand(@TimeCheck String startAt) {

}
