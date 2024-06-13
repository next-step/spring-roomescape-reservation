package roomescape.reservationTime.service;

import org.springframework.stereotype.Service;
import roomescape.error.exception.ReferenceException;
import roomescape.error.exception.ReservationTimeReferenceException;
import roomescape.reservation.service.ReservationRepository;

import java.util.List;
import java.util.stream.Collectors;
import roomescape.reservationTime.ReservationTime;
import roomescape.reservationTime.dto.ReservationTimeRequest;
import roomescape.reservationTime.dto.ReservationTimeResponse;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;
    private final ReservationRepository reservationRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository,
                                    ReservationRepository reservationRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
        this.reservationRepository = reservationRepository;
    }

    public ReservationTimeResponse saveReservationTime(ReservationTimeRequest request) {
        ReservationTime reservationTime = new ReservationTime(request.getStartAt());
        return new ReservationTimeResponse(reservationTimeRepository.save(reservationTime));
    }

    public List<ReservationTimeResponse> findReservationTimes() {
        return reservationTimeRepository.find().stream()
            .map(ReservationTimeResponse::new)
            .collect(Collectors.toList());
    }

    public void deleteReservationTime(Long id) {
        if (reservationRepository.countByTime(id) > 0) {
            throw new ReservationTimeReferenceException();
        }

        reservationTimeRepository.delete(id);
    }
}
