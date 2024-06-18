package roomescape.reservationtime.application;

import org.springframework.stereotype.Service;
import roomescape.reservationtime.ui.dto.ReservationTimeRequest;
import roomescape.reservationtime.ui.dto.ReservationTimeResponse;
import roomescape.reservationtime.domain.entity.ReservationTime;
import roomescape.exception.BadRequestException;
import roomescape.exception.NotFoundException;
import roomescape.reservationtime.domain.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;
    private final ReservationTimeValidator reservationTimeValidator;

    public ReservationTimeService(
            ReservationTimeRepository reservationTimeRepository,
            ReservationTimeValidator reservationTimeValidator) {
        this.reservationTimeRepository = reservationTimeRepository;
        this.reservationTimeValidator = reservationTimeValidator;
    }

    public List<ReservationTimeResponse> findAll() {
        List<ReservationTime> times = reservationTimeRepository.findAll();
        return ReservationTimeResponse.fromReservationTimes(times);
    }

    public ReservationTimeResponse findOne(Long id) {
        ReservationTime time = reservationTimeRepository.findById(id);
        return ReservationTimeResponse.from(time);
    }

    public long add(ReservationTimeRequest request) {
        reservationTimeValidator.validateRequest(request);
        return reservationTimeRepository.save(request.getStartAt());
    }

    public void delete(Long id) {
        checkReservationMatchWith(id);
        long deleteCount = reservationTimeRepository.deleteById(id);

        if (deleteCount == 0) {
            throw new NotFoundException("id와 일치하는 예약 시간이 없습니다.");
        }
    }

    private void checkReservationMatchWith(Long id) {
        if (reservationTimeRepository.countReservationMatchWith(id) > 0) {
            throw new BadRequestException("해당 시간에 대한 예약이 존재합니다.");
        }
    }
}
