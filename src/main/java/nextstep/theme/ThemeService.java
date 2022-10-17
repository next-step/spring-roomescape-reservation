package nextstep.theme;

import nextstep.CurrentDateTimeService;
import nextstep.reservation.ReservationRepository;
import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleJdbcRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeService {
    private final ThemeJdbcRepository themeJdbcRepository;
    private final ScheduleJdbcRepository scheduleJdbcRepository;
    private final ReservationRepository reservationRepository;
    private final CurrentDateTimeService currentDateTimeService;

    public ThemeService(
            ThemeJdbcRepository themeJdbcRepository,
            ScheduleJdbcRepository scheduleJdbcRepository,
            ReservationRepository reservationRepository,
            CurrentDateTimeService currentDateTimeService
    ) {
        this.themeJdbcRepository = themeJdbcRepository;
        this.scheduleJdbcRepository = scheduleJdbcRepository;
        this.reservationRepository = reservationRepository;
        this.currentDateTimeService = currentDateTimeService;
    }

    public Theme createTheme(ThemeCreateRequest request) {
        if (this.themeJdbcRepository.existByName(request.getName())) {
            throw new IllegalStateException("테마 생성 실패: 이미 존재하는 테마 이름입니다.");
        }
        return this.themeJdbcRepository.create(request.toObject());
    }

    public List<ThemeResponse> getThemes() {
        return this.themeJdbcRepository.findAll().stream()
                .map(ThemeResponse::from)
                .toList();
    }

    public void deleteTheme(Long themeId) {
        if (existReservationInTheme(themeId)) {
            throw new IllegalStateException("테마 삭제 실패: 테마에 예약이 존재합니다.");
        }
        boolean result = this.themeJdbcRepository.deleteThemeById(themeId);
        if (!result) {
            throw new IllegalStateException("테마 삭제 실패.: 해당 ID의 테마가 존재하지 않음");
        }
    }

    /**
     * 해당 테마에 예약이 존재하는지 여부를 반환합니다.
     *
     * @param themeId 조회하는 테마 ID
     * @return true: 테마에 예약이 존재함 / false: 테마에 예약이 존재하지 않음
     */
    private boolean existReservationInTheme(Long themeId) {
        List<Schedule> schedules = scheduleJdbcRepository.findByThemeIdAndDateGreaterThanAndTimeGreaterThan(
                themeId,
                currentDateTimeService.nowDate(),
                currentDateTimeService.nowTime()
        );
        List<Long> scheduleIds = schedules.stream()
                .map(Schedule::getId)
                .toList();
        return reservationRepository.existsByScheduleIds(scheduleIds);
    }
}
