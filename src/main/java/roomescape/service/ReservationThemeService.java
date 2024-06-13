package roomescape.service;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTheme;
import roomescape.dto.request.ReservationThemeRequest;
import roomescape.dto.response.ReservationThemeResponse;
import roomescape.exception.BusinessException;
import roomescape.repository.JdbcReservationDao;
import roomescape.repository.JdbcReservationThemeDao;

@Service
public class ReservationThemeService {

    private final JdbcReservationThemeDao reservationThemeDao;
    private final JdbcReservationDao reservationDao;

    public ReservationThemeService(JdbcReservationThemeDao reservationThemeDao, JdbcReservationDao reservationDao) {
        this.reservationThemeDao = reservationThemeDao;
        this.reservationDao = reservationDao;
    }

    public ReservationThemeResponse createReservationTheme(ReservationThemeRequest request) {
        Long count = reservationThemeDao.findByName(request.getName());
        if (count > 0) {
            throw new BusinessException("동일한 테마이름이 존재합니다.", HttpStatus.CONFLICT);
        }

        ReservationTheme reservationTheme = reservationThemeDao.save(this.convertToEntity(request));
        return this.convertToResponse(reservationTheme);
    }

    public List<ReservationThemeResponse> findAllReservationThemes() {
        return reservationThemeDao.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

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
