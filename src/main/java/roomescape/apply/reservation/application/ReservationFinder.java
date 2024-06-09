package roomescape.apply.reservation.application;

import org.springframework.stereotype.Service;
import roomescape.apply.reservation.domain.repository.ReservationRepository;
import roomescape.apply.reservation.ui.dto.ReservationResponse;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationFinder {

    private final ReservationRepository reservationRepository;

    public ReservationFinder(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponse> findAll() {
        return reservationRepository.findAll()
                .stream()
                .map(it -> ReservationResponse.from(it, it.getTheme(), it.getTime()))
                .toList();
    }

    public boolean doesReservationExist(long timeId, long themeId) {
        Optional<Long> existedId = reservationRepository.findIdByTimeIdAndThemeId(timeId, themeId);
        return existedId.isPresent();
    }

    public Optional<Long> findAnyByTimeId(long timeId) {
        return reservationRepository.findAnyByTimeId(timeId);
    }

    public Optional<Long> findAnyByThemeId(long themeId) {
        return reservationRepository.findAnyByThemeId(themeId);
    }
}
