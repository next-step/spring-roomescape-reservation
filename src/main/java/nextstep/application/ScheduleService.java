package nextstep.application;

import java.util.List;
import java.util.stream.Collectors;
import nextstep.domain.Schedule;
import nextstep.domain.repository.ScheduleRepository;
import nextstep.exception.ScheduleException;
import nextstep.presentation.dto.ReservationRequest;
import nextstep.presentation.dto.ReservationResponse;
import nextstep.presentation.dto.ScheduleRequest;
import nextstep.presentation.dto.ScheduleResponse;
import nextstep.presentation.dto.ThemeResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class ScheduleService {

    private final ThemeService themeService;
    private final ReservationService reservationService;
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(
        ThemeService themeService,
        ReservationService reservationService,
        ScheduleRepository scheduleRepository
    ) {
        this.themeService = themeService;
        this.reservationService = reservationService;
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional
    public Long make(ScheduleRequest request) {
        Long reservationId = reservationService.make(toReservationRequest(request));

        scheduleRepository.save(toSchedule(request, reservationId));
        return scheduleRepository.findBy(reservationId)
            .map(Schedule::getId)
            .orElseThrow(() -> new ScheduleException("존재하지 않는 스케줄입니다."));
    }

    private ReservationRequest toReservationRequest(ScheduleRequest request) {
        return new ReservationRequest(request.getDate(), request.getTime());
    }

    private Schedule toSchedule(ScheduleRequest request, Long reservationId) {
        return new Schedule(request.getThemeId(), reservationId);
    }

    public List<ScheduleResponse> checkAll(Long themeId, String date) {
        List<ReservationResponse> reservations = reservationService.checkAll(date);
        ThemeResponse theme = themeService.checkBy(themeId);

        return reservations.stream()
            .map(reservation -> toScheduleResponse(themeId, reservation, theme))
            .collect(Collectors.toList());
    }

    private ScheduleResponse toScheduleResponse(
        Long themeId,
        ReservationResponse reservation,
        ThemeResponse theme
    ) {
        Schedule schedule = scheduleRepository.findBy(themeId, reservation.getId())
            .orElseThrow(() -> new ScheduleException("존재하지 않는 스케줄입니다."));

        return toScheduleResponse(theme, reservation, schedule);
    }

    private ScheduleResponse toScheduleResponse(
        ThemeResponse theme,
        ReservationResponse reservation,
        Schedule schedule
    ) {
        return new ScheduleResponse(
            schedule.getId(),
            theme,
            reservation.getDate(),
            reservation.getTime()
        );
    }

    @Transactional
    public void cancel(Long id) {
        scheduleRepository.delete(id);
    }

    @Transactional
    public void cancelAll() {
        scheduleRepository.deleteAll();
    }
}
