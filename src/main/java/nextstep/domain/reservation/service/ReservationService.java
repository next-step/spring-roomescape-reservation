package nextstep.domain.reservation.service;

import nextstep.domain.reservation.model.Reservation;
import nextstep.domain.reservation.model.ReservationRepository;
import nextstep.domain.schedule.model.Schedule;
import nextstep.domain.schedule.model.ScheduleRepository;
import nextstep.exception.ClientException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ScheduleRepository scheduleRepository;

    public ReservationService(ReservationRepository reservationRepository, ScheduleRepository scheduleRepository) {
        this.reservationRepository = reservationRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public Long create(Reservation reservation) {
        if (scheduleRepository.findById(reservation.getScheduleId()).isEmpty()) {
            throw new ClientException("존재하지 않는 일정에 예약할 수 없습니다.");
        }

        if (reservationRepository.findByScheduleId(reservation.getScheduleId()).isPresent()) {
            throw new ClientException("해당 일정에 이미 존재하는 예약이 있습니다.");
        }

        return reservationRepository.create(reservation);
    }

    public void removeByScheduleId(Long scheduleId) {
        if (reservationRepository.findByScheduleId(scheduleId).isEmpty()) {
            throw new ClientException(String.format("해당 일정(ID:%d)에 대해 존재하지 않는 예약을 삭제할 수 없습니다.", scheduleId));
        }

        reservationRepository.removeByScheduleId(scheduleId);
    }

    public List<ReservationResponse> findAllByDate(String date) {
        List<Schedule> schedules = scheduleRepository.findAllByDate(date);

        List<Long> scheduleIds = schedules.stream()
            .map(Schedule::getId)
            .collect(Collectors.toList());

        return reservationRepository.findAllByScheduledIds(scheduleIds).stream()
            .map(reservation -> new ReservationResponse(reservation, schedules.stream().filter(schedule -> Objects.equals(schedule.getId(), reservation.getScheduleId())).findFirst().get()))
            .collect(Collectors.toList());
    }
}
