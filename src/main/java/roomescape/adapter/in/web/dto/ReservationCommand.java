package roomescape.adapter.in.web.dto;

import roomescape.annotation.DateCheck;
import roomescape.annotation.TimeCheck;

public record ReservationCommand(String name, @DateCheck String date, @TimeCheck String time) {

}
