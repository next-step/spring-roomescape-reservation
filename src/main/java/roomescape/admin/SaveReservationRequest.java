package roomescape.admin;

public record SaveReservationRequest(String name,
                                     String date,
                                     Long timeId) {
}