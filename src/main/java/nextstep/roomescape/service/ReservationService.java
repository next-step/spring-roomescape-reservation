package nextstep.roomescape.service;

import java.util.List;
import java.util.stream.Collectors;
import nextstep.roomescape.core.domain.Reservation;
import nextstep.roomescape.core.domain.ReservationRepository;
import nextstep.roomescape.core.domain.ScheduleRepository;
import nextstep.roomescape.core.exception.ReservationException;
import nextstep.roomescape.core.exception.ScheduleException;
import nextstep.roomescape.web.dto.ReservationDto;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ScheduleRepository scheduleRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(ScheduleRepository scheduleRepository, ReservationRepository reservationRepository) {
        this.scheduleRepository = scheduleRepository;
        this.reservationRepository = reservationRepository;
    }

    public Integer register(ReservationDto request) {
        final Reservation reservation = request.toEntity();
        if (!scheduleRepository.existsAliveByThemeId(reservation.scheduleId())) {
            throw new ScheduleException("존재하지 않는 스케줄이거나 이미 지난 날짜의 스케줄입니다.");
        }
        if (reservationRepository.containsBySchedule(reservation.scheduleId())) {
            throw new ReservationException("해당 스케줄에 예약 정보가 이미 존재합니다.");
        }
        return reservationRepository.add(reservation);
    }

    public void delete(Integer id) {
        if (!reservationRepository.existsById(id)) {
            throw new ReservationException("해당 예약 정보가 존재하지 않습니다.");
        }
        reservationRepository.delete(id);
    }

    public List<ReservationDto> findAll() {
        return reservationRepository.findAll().stream()
            .map(it -> new ReservationDto(it.id(), it.scheduleId(), it.name()))
            .collect(Collectors.toUnmodifiableList());
    }
}
