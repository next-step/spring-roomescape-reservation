package roomescape.admin.service;

import org.springframework.stereotype.Service;
import roomescape.admin.dto.ReadReservationTimeResponse;
import roomescape.admin.dto.SaveReservationTimeRequest;
import roomescape.admin.entity.ReservationTime;
import roomescape.admin.repository.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository){
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReadReservationTimeResponse> readReservationTime() {
        List<ReservationTime> reservationTime = this.reservationTimeRepository.readReservationTime();

        return ReadReservationTimeResponse.from(reservationTime);
    }

    public ReadReservationTimeResponse saveReservationTime(SaveReservationTimeRequest saveReservationTimeRequest) {
        Long id = this.reservationTimeRepository.saveReservationTime(saveReservationTimeRequest);
        ReservationTime reservationTime = this.reservationTimeRepository.readReservationTimeById(id);

        return ReadReservationTimeResponse.from(reservationTime);
    }

    public void deleteReservationTime(Long id) {
        this.reservationTimeRepository.deleteReservationTime(id);
    }
}