package nextstep.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;

public record Reservation(LocalDate date, LocalTime time, String name) {
}
