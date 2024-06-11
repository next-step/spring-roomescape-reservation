package roomescape.admin.service;

import org.springframework.stereotype.Service;
import roomescape.admin.dto.ReadReservationResponse;
import roomescape.admin.dto.SaveReservationRequest;
import roomescape.admin.entity.Reservation;
import roomescape.admin.entity.ReservationTime;
import roomescape.admin.repository.ReservationRepository;
import roomescape.admin.repository.ReservationTimeRepository;
import roomescape.common.exception.CustomException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository){
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReadReservationResponse> readReservation() {
        List<Reservation> reservation = this.reservationRepository.findAll();

        return ReadReservationResponse.from(reservation);
    }

    public ReadReservationResponse saveReservation(SaveReservationRequest saveReservationRequest) {
        if(isDuplicatedReservation(saveReservationRequest)){
            throw new CustomException("중복된 예약이 존재합니다.");
        }

        if(isUnderDate(saveReservationRequest)){
            throw new CustomException("예약 시간은 현재 시간보다 이전일 수 없습니다.");
        }

        Long id = this.reservationRepository.save(saveReservationRequest);
        Reservation reservation = this.reservationRepository.findById(id);

        return ReadReservationResponse.from(reservation);
    }

    private boolean isUnderDate(SaveReservationRequest saveReservationRequest) {
        ReservationTime reservationTime = this.reservationTimeRepository.findById(saveReservationRequest.timeId());
        String startAt = reservationTime.getStartAt();
        LocalDateTime reserve = LocalDateTime.parse(saveReservationRequest.date() + " "+startAt, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        if (reserve.isBefore(LocalDateTime.now())) {
            return true;
        }

        return false;
    }

    private boolean isDuplicatedReservation(SaveReservationRequest saveReservationRequest) {
        ReservationTime reservationTime = this.reservationTimeRepository.findById(saveReservationRequest.timeId());
        String startAt = reservationTime.getStartAt();
        String date = saveReservationRequest.date();
        int duplicate = this.reservationRepository.countByDateAndStartAt(date, startAt);

        if(duplicate > 0){
            return true;
        }

        return false;
    }

    public void deleteReservation(Long id) {
        this.reservationRepository.delete(id);
    }
}