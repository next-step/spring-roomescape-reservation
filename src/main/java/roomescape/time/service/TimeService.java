package roomescape.time.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.time.domain.Time;
import roomescape.time.domain.TimeRepository;

import java.util.List;

@Service
public class TimeService {

    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @Transactional
    public Time save(Time time) {
        Long id = timeRepository.save(time);
        return findById(id);
    }

    @Transactional
    public Time findById(Long id) {
        return timeRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Time> findAll() {
        return timeRepository.findAll();
    }

    @Transactional
    public void delete(Long id) {
        timeRepository.delete(id);
    }
}
