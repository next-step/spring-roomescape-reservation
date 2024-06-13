package roomescape.time;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeService {
    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    private final TimeRepository timeRepository;

    public List<Time> reservationTimes() {
        return timeRepository
                .reservationTimes()
                .stream()
                .map(Time::from)
                .toList();
    }

    public Time add(Time newTime) {
         TimeEntity entity = timeRepository.addReservationTime(newTime.toEntity());
         return Time.from(entity);
    }

    public void delete(Long id) {
        timeRepository.delete(id);
    }
}
