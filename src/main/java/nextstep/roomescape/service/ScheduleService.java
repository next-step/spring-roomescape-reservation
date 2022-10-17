package nextstep.roomescape.service;

import java.util.List;
import java.util.stream.Collectors;
import nextstep.roomescape.core.domain.ReservationRepository;
import nextstep.roomescape.core.domain.Schedule;
import nextstep.roomescape.core.domain.ScheduleRepository;
import nextstep.roomescape.core.domain.ThemeRepository;
import nextstep.roomescape.core.exception.ScheduleException;
import nextstep.roomescape.core.exception.ThemeException;
import nextstep.roomescape.web.dto.ScheduleRequest;
import nextstep.roomescape.web.dto.ScheduleResponse;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    private final ReservationRepository reservationRepository;
    private final ThemeRepository themeRepository;
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(
        ReservationRepository reservationRepository,
        ThemeRepository themeRepository,
        ScheduleRepository scheduleRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.themeRepository = themeRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public Integer create(ScheduleRequest scheduleRequest) {
        final Schedule schedule = scheduleRequest.toEntity();
        if (!themeRepository.existsById(schedule.themeId())) {
            throw new ThemeException("존재하지 않는 테마입니다.");
        }
        if (scheduleRepository.exists(schedule.themeId(), schedule.date(), schedule.time())) {
            throw new ScheduleException("이미 존재하는 스케줄입니다.");
        }
        return scheduleRepository.add(schedule);
    }

    public List<ScheduleResponse> findAll() {
        return scheduleRepository.findAll().stream()
            .map(it -> ScheduleResponse.of(themeRepository.findById(it.themeId()).orElseThrow(), it))
            .collect(Collectors.toUnmodifiableList());
    }

    public void deleteById(Integer id) {
        if (reservationRepository.containsBySchedule(id)) {
            throw new ScheduleException("예약이 존재하는 스케줄은 제거할 수 없습니다.");
        }
        if (!scheduleRepository.existsById(id)) {
            throw new ScheduleException("존재하지 않는 스케줄입니다.");
        }
        scheduleRepository.deleteById(id);
    }
}
