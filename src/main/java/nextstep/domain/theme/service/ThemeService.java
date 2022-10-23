package nextstep.domain.theme.service;

import nextstep.domain.reservation.model.Reservation;
import nextstep.domain.reservation.model.ReservationRepository;
import nextstep.domain.schedule.model.Schedule;
import nextstep.domain.schedule.model.ScheduleRepository;
import nextstep.domain.theme.model.Theme;
import nextstep.domain.theme.model.ThemeRepository;
import nextstep.exception.ClientException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThemeService {
    private final ThemeRepository themeRepository;
    private final ScheduleRepository scheduleRepository;
    private final ReservationRepository reservationRepository;

    public ThemeService(ThemeRepository themeRepository, ScheduleRepository scheduleRepository, ReservationRepository reservationRepository) {
        this.themeRepository = themeRepository;
        this.scheduleRepository = scheduleRepository;
        this.reservationRepository = reservationRepository;
    }

    public Long create(Theme theme) {
        return themeRepository.create(theme);
    }

    public List<ThemeResponse> findAll() {
        return themeRepository.findAll().stream()
            .map(ThemeResponse::new)
            .collect(Collectors.toList());
    }

    public void remove(Long id) {
        List<Long> scheduleIds = scheduleRepository.findAllByThemeId(id).stream()
            .map(Schedule::getId)
            .collect(Collectors.toList());

        List<Reservation> reservations = reservationRepository.findAllByScheduledIds(scheduleIds);

        if (reservations.size() > 0) {
            throw new ClientException("예약이 존재하는 테마는 삭제할 수 없습니다.");
        }

        themeRepository.remove(id);
    }
}
