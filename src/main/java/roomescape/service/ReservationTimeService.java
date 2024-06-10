package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.dto.time.ReservationTimeRequest;
import roomescape.dto.time.ReservationTimeResponse;
import roomescape.dto.time.create.ReservationTimeCreateResponse;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTimeResponse> findTimes() {
        return reservationTimeRepository.findTimes()
                .stream()
                .map(ReservationTimeResponse::new)
                .collect(Collectors.toList());
    }

    public ReservationTimeCreateResponse createTime(ReservationTimeRequest request) {
        Long id = reservationTimeRepository.createTime(request);
        ReservationTime entity = reservationTimeRepository.findReservationTimeById(id);
        return ReservationTimeCreateResponse.toDto(entity);
    }

    public void deleteTime(Long id) {
        reservationTimeRepository.deleteTime(id);
    }
}
