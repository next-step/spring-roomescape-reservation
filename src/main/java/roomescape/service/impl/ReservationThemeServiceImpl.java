package roomescape.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTheme;
import roomescape.dto.request.ReservationThemeRequest;
import roomescape.dto.response.ReservationThemeResponse;
import roomescape.repository.ReservationThemeDao;
import roomescape.service.ReservationThemeService;

@Service
public class ReservationThemeServiceImpl implements ReservationThemeService {

    private final ReservationThemeDao reservationThemeDao;

    public ReservationThemeServiceImpl(ReservationThemeDao reservationThemeDao) {
        this.reservationThemeDao = reservationThemeDao;
    }

    @Override
    public ReservationThemeResponse createReservationTheme(ReservationThemeRequest request) {
        ReservationTheme reservationTheme = reservationThemeDao.save(this.convertToEntity(request));
        return this.convertToResponse(reservationTheme);
    }

    @Override
    public List<ReservationThemeResponse> findAllReservationThemes() {
        return reservationThemeDao.findAll()
                .stream()
                .map(reservationTheme -> this.convertToResponse(reservationTheme))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void deleteReservationTheme(Long id) {
        reservationThemeDao.delete(id);
    }

    private ReservationThemeResponse convertToResponse(ReservationTheme reservationTheme) {
        return new ReservationThemeResponse(
                reservationTheme.getId()
                , reservationTheme.getName()
                , reservationTheme.getDescription()
                , reservationTheme.getThumbnail());
    }

    private ReservationTheme convertToEntity(ReservationThemeRequest reservationThemeRequest) {
        return new ReservationTheme(
                reservationThemeRequest.getName()
                , reservationThemeRequest.getDescription()
                , reservationThemeRequest.getThumbnail());
    }
}
