package nextstep.domain.reservation.service;

import nextstep.domain.reservation.exception.NotFoundReservationException;
import nextstep.domain.reservation.model.Reservation;
import nextstep.domain.reservation.exception.ExistReservationException;
import nextstep.domain.reservation.model.ReservationRepository;
import nextstep.domain.schedule.model.Schedule;
import nextstep.domain.schedule.exception.NotFoundScheduleException;
import nextstep.domain.schedule.model.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationDomainService {
    private final ReservationRepository reservationRepository;
    private final ScheduleRepository scheduleRepository;

    public ReservationDomainService(ReservationRepository reservationRepository, ScheduleRepository scheduleRepository) {
        this.reservationRepository = reservationRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional
    public Reservation create(Long scheduleId, LocalDate date, LocalTime time, String name) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> {
                    throw new NotFoundScheduleException();
                });
        if (reservationRepository.existByScheduleId(schedule.getId())) {
            throw new ExistReservationException(String.format("해당 스케쥴에 예약이 이미 존재합니다. scheduleId: %d", scheduleId));
        }

        return reservationRepository.save(new Reservation(schedule.getId(), date, time, name));
    }

    @Transactional
    public void cancelByDateTime(LocalDate date, LocalTime time) {
        if (!reservationRepository.existByDateTime(date, time)) {
            throw new NotFoundReservationException();
        }
        reservationRepository.deleteByDateTime(date, time);
    }

    @Transactional(readOnly = true)
    public List<Reservation> findAllByDate(LocalDate date) {
        return reservationRepository.findAllByDate(date);
    }
}
