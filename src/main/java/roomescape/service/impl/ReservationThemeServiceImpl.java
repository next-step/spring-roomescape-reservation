package roomescape.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTheme;
import roomescape.dto.request.ReservationThemeRequest;
import roomescape.dto.response.ReservationThemeResponse;
import roomescape.exception.BusinessException;
import roomescape.repository.ReservationDao;
import roomescape.repository.ReservationThemeDao;
import roomescape.service.ReservationThemeService;

@Service
public class ReservationThemeServiceImpl implements ReservationThemeService {

    private final ReservationThemeDao reservationThemeDao;
    private final ReservationDao reservationDao;

    public ReservationThemeServiceImpl(ReservationThemeDao reservationThemeDao, ReservationDao reservationDao) {
        this.reservationThemeDao = reservationThemeDao;
        this.reservationDao = reservationDao;
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
        long count = reservationDao.countByThemeId(id);
        if (count > 0) {
            throw new BusinessException("예약 테마를 사용중인 예약이 존재합니다. 예약 삭제 후 다시 시도해주세요."
                    , HttpStatus.CONFLICT);
        }

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
