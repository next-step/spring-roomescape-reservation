package roomescape.admin.service;

import org.springframework.stereotype.Service;
import roomescape.admin.dto.ReadReservationResponse;
import roomescape.admin.dto.SaveReservationRequest;
import roomescape.admin.entity.Reservation;
import roomescape.admin.entity.ReservationTime;
import roomescape.admin.repository.ReservationRepository;
import roomescape.admin.repository.ReservationTimeRepository;
import roomescape.common.exception.DBException;
import roomescape.common.exception.PolicyException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static roomescape.common.exception.DBException.SEARCH_ERROR;
import static roomescape.common.exception.PolicyException.DUPLICATE_RESERVATION_ERROR;
import static roomescape.common.exception.PolicyException.RESERVATION_PRIOR_TO_CURRENT_TIME_ERROR;

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
        List<Reservation> reservation = this.reservationRepository.findAll()
                .orElseThrow(() -> new DBException(SEARCH_ERROR));

        return ReadReservationResponse.from(reservation);
    }

    public ReadReservationResponse saveReservation(SaveReservationRequest saveReservationRequest) {
        if(isDuplicatedReservation(saveReservationRequest)){
            throw new PolicyException(DUPLICATE_RESERVATION_ERROR);
        }

        if(isUnderDate(saveReservationRequest)){
            throw new PolicyException(RESERVATION_PRIOR_TO_CURRENT_TIME_ERROR);
        }

        Long id = this.reservationRepository.save(saveReservationRequest);

        return ReadReservationResponse.from(readReservationById(id));
    }

    public Reservation readReservationById(Long id) {
        return this.reservationRepository.findById(id)
                .orElseThrow(() -> new DBException(SEARCH_ERROR));
    }

    private boolean isUnderDate(SaveReservationRequest saveReservationRequest) {
        ReservationTime reservationTime = readReservationTimeById(saveReservationRequest);
        String startAt = reservationTime.getStartAt();
        LocalDateTime reserve = LocalDateTime.parse(saveReservationRequest.date() + " " + startAt, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        return reserve.isBefore(LocalDateTime.now());
    }

    private boolean isDuplicatedReservation(SaveReservationRequest saveReservationRequest) {
        ReservationTime reservationTime = readReservationTimeById(saveReservationRequest);
        String startAt = reservationTime.getStartAt();
        String date = saveReservationRequest.date();

        return isExistData(this.reservationRepository.countByDateAndStartAt(date, startAt));
    }

    private ReservationTime readReservationTimeById(SaveReservationRequest saveReservationRequest) {
        return this.reservationTimeRepository.findById(saveReservationRequest.timeId())
                .orElseThrow(() -> new DBException(SEARCH_ERROR));
    }

    private boolean isExistData(int count){
        return count > 0;
    }

    public void deleteReservation(Long id) {
        this.reservationRepository.delete(id);
    }
}