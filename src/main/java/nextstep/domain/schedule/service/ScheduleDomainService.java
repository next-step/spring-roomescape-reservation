package nextstep.domain.schedule.service;

import nextstep.domain.reservation.model.ReservationRepository;
import nextstep.domain.schedule.exception.UnableDeleteScheduleException;
import nextstep.domain.schedule.model.Schedule;
import nextstep.domain.schedule.model.ScheduleRepository;
import nextstep.domain.theme.exception.NotFoundThemeException;
import nextstep.domain.theme.model.Theme;
import nextstep.domain.theme.model.ThemeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ScheduleDomainService {
    private final ScheduleRepository scheduleRepository;
    private final ReservationRepository reservationRepository;
    private final ThemeRepository themeRepository;

    public ScheduleDomainService(ScheduleRepository scheduleRepository, ReservationRepository reservationRepository, ThemeRepository themeRepository) {
        this.scheduleRepository = scheduleRepository;
        this.reservationRepository = reservationRepository;
        this.themeRepository = themeRepository;
    }

    @Transactional
    public Schedule create(Long themeId, LocalDate date, LocalTime time) {
        Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> {
                    throw new NotFoundThemeException();
                });
        return scheduleRepository.save(new Schedule(theme.getId(), date, time));
    }

    @Transactional(readOnly = true)
    public List<Schedule> findAllByThemeIdAndDate(Long themeId, LocalDate date) {
        return scheduleRepository.findAllByThemeIdAndDate(themeId, date);
    }

    @Transactional
    public void deleteById(Long id) {
        if (reservationRepository.existByScheduleId(id)) {
            throw new UnableDeleteScheduleException("예약이 있는 스케쥴은 삭제할 수 없습니다.");
        }
        scheduleRepository.deleteById(id);
    }
}
