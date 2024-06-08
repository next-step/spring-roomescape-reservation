package roomescape.service;

import java.util.List;
import roomescape.dto.request.ReservationThemeRequest;
import roomescape.dto.response.ReservationThemeResponse;

public interface ReservationThemeService {

    ReservationThemeResponse createReservationTheme(ReservationThemeRequest request);

    List<ReservationThemeResponse> findAllReservationThemes();

    void deleteReservationTheme(Long id);
}
