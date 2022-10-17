package nextstep.domain.reservation.domain.service;

import nextstep.domain.reservation.domain.model.Reservation;
import nextstep.domain.reservation.domain.model.ReservationRepository;
import nextstep.domain.schedule.domain.service.ScheduleDomainService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class ReservationDomainService {
    private final ReservationRepository reservationRepository;
    private final ScheduleDomainService scheduleDomainService;

    public ReservationDomainService(ReservationRepository reservationRepository, ScheduleDomainService scheduleDomainService) {
        this.reservationRepository = reservationRepository;
        this.scheduleDomainService = scheduleDomainService;
    }

    public Long create(Long scheduleId, LocalDateTime reservationDateTime, String name) {
        if (!scheduleDomainService.existsById(scheduleId)) {
            throw new IllegalArgumentException("존재하지 않는 스케줄입니다.");
        }

        Reservation reservation = new Reservation(null, scheduleId, reservationDateTime.toLocalDate(), reservationDateTime.toLocalTime(), name);

        if (reservationRepository.findByScheduleIdAndDateTime(scheduleId, reservationDateTime).isPresent()) {
            throw new IllegalArgumentException("해당 스케줄에 동일한 날짜와 시간의 예약이 존재합니다.");
        }

        Reservation saved = reservationRepository.save(reservation);
        return saved.id();
    }

    public void delete(Long scheduleId, LocalDate date, LocalTime time) {
        reservationRepository.findByScheduleIdAndDateTime(scheduleId, LocalDateTime.of(date, time))
                .orElseThrow(() -> new IllegalArgumentException("예약이 존재하지 않습니다."));

        reservationRepository.deleteByScheduleIdAndDateAndTime(scheduleId, date, time);
    }
}
