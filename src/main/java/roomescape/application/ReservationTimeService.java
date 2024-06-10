package roomescape.application;

import static roomescape.application.ReservationTimeServiceOutput.createReservationTimeServiceOutput;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.common.exception.NotFoundException;
import roomescape.domain.Time;
import roomescape.domain.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @Transactional
    public ReservationTimeServiceOutput create(ReservationTimeServiceInput input) {
        Time time = Time.createReservationTime(input.getStartAt());
        long id = reservationTimeRepository.save(time);
        Time saved = reservationTimeRepository.findById(id).get();
        return createReservationTimeServiceOutput(saved);
    }

    @Transactional(readOnly = true)
    public List<ReservationTimeServiceOutput> retrieveReservationTimes() {
        List<Time> times = reservationTimeRepository.findAll();
        return times.stream().map(ReservationTimeServiceOutput::createReservationTimeServiceOutput).toList();
    }

    @Transactional
    public void delete(Long id) {
        if (reservationTimeRepository.findById(id).isEmpty()) {
            throw new NotFoundException("이미 삭제된 시간입니다.");
        }
        reservationTimeRepository.deleteById(id);
    }
}
