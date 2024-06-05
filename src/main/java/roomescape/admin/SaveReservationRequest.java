package roomescape.admin;

public record SaveReservationRequest(String name,
                                     String date,
                                     String time) {
}