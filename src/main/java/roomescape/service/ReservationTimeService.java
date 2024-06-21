package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.dto.time.ReservationTimeRequest;
import roomescape.dto.time.ReservationTimeResponse;
import roomescape.dto.time.create.ReservationTimeCreateResponse;
import roomescape.exception.custom.DuplicatedReservationTimeException;
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
        checkDuplicatedReservationTime(request);
        Long id = reservationTimeRepository.createTime(request);
        ReservationTime entity = reservationTimeRepository.findReservationTimeById(id);
        return ReservationTimeCreateResponse.toDto(entity);
    }

    public void deleteTime(Long id) {
        reservationTimeRepository.deleteTime(id);
    }

    public void checkDuplicatedReservationTime(ReservationTimeRequest request) {
        int count = reservationTimeRepository.countReservationTimeByStartAt(request);
        if (count > 0) {
            throw new DuplicatedReservationTimeException("이미 존재하는 예약 시간입니다. 다른 시간대를 입력해주세요.");
        }
    }
}
