package roomescape.reservationtime.application;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import roomescape.reservationtime.ui.dto.ReservationTimeRequest;
import roomescape.reservationtime.ui.dto.ReservationTimeResponse;
import roomescape.reservationtime.domain.entity.ReservationTime;
import roomescape.exception.BadRequestException;
import roomescape.exception.NotFoundException;
import roomescape.reservationtime.domain.ReservationTimeRepository;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
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
        validateTime(request.getStartAt());
        return reservationTimeRepository.save(request.getStartAt());
    }


    private void validateTime(String startAt) {
        try {
            LocalTime.parse(startAt);
            validateDuplicated(startAt);
        }
        catch (DateTimeParseException exception) {
            throw new BadRequestException("유효하지 않은 시간 형식입니다.");
        }
    }

    private void validateDuplicated(String startAt) {
        try {
            reservationTimeRepository.findByStartAt(startAt);
            throw new BadRequestException("이미 존재하는 시간입니다.");
        }
        catch (EmptyResultDataAccessException ignore) {
        }
    }

    public void delete(Long id) {
        checkReservationMatchWith(id);
        long deleteCount = reservationTimeRepository.cancelById(id);

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
