package roomescape.admin.dto;

public record SaveReservationRequest(String name,
                                     String date,
                                     Long timeId,
                                     Long themeId) {
}