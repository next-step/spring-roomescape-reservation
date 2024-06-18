package roomescape.application.service.reservationTime;

import org.springframework.stereotype.Service;
import roomescape.application.dto.result.CreateReservationTimeResult;
import roomescape.application.dto.result.GetReservationTimeResult;
import roomescape.domain.ReservationTime;
import roomescape.repository.reservationTime.ReservationTimeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<GetReservationTimeResult> getReservationTimes() {
        return reservationTimeRepository.findAll().stream()
                .map(reservationTime -> GetReservationTimeResult.builder()
                        .id(reservationTime.getId())
                        .startAt(reservationTime.getStartAt())
                        .build())
                .collect(Collectors.toList());
    }

    public CreateReservationTimeResult createReservationTime(String startAt) {
        return CreateReservationTimeResult.of(reservationTimeRepository.createReservationTime(ReservationTime.create(startAt)));
    }

    public void deleteReservationTime(Long id) {
        reservationTimeRepository.deleteReservationTime(id);
    }
}
