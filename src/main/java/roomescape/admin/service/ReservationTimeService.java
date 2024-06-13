package roomescape.admin.service;

import org.springframework.stereotype.Service;
import roomescape.admin.dto.ReadReservationTimeResponse;
import roomescape.admin.dto.SaveReservationTimeRequest;
import roomescape.admin.entity.ReservationTime;
import roomescape.admin.repository.ReservationTimeRepository;
import roomescape.common.exception.DBException;
import roomescape.common.exception.PolicyException;

import java.util.List;

import static roomescape.common.exception.DBException.SEARCH_ERROR;
import static roomescape.common.exception.PolicyException.RESERVATION_TIME_IN_USE_RESERVATION_ERROR;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository){
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReadReservationTimeResponse> readReservationTime() {
        List<ReservationTime> reservationTime = this.reservationTimeRepository.findAll()
                .orElseThrow(() -> new DBException(SEARCH_ERROR));

        return ReadReservationTimeResponse.from(reservationTime);
    }

    public ReadReservationTimeResponse saveReservationTime(SaveReservationTimeRequest saveReservationTimeRequest) {
        Long id = this.reservationTimeRepository.save(saveReservationTimeRequest);

        return ReadReservationTimeResponse.from(readReservationById(id));
    }

    public ReservationTime readReservationById(Long id) {
        return this.reservationTimeRepository.findById(id)
                .orElseThrow(() -> new DBException(SEARCH_ERROR));
    }

    public void deleteReservationTime(Long id) {
        if(isContainReservation(id)){
            throw new PolicyException(RESERVATION_TIME_IN_USE_RESERVATION_ERROR);
        }

        this.reservationTimeRepository.delete(id);
    }

    private boolean isContainReservation(long reservationId) {
        return isExistData(readReservationTimeCount(reservationId));
    }

    private Integer readReservationTimeCount(long reservationId) {
        return this.reservationTimeRepository.countById(reservationId)
                .orElseThrow(() -> new DBException(SEARCH_ERROR));
    }

    private boolean isExistData(int count){
        return count > 0;
    }
}