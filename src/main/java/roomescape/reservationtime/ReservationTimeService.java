package roomescape.reservationtime;


import org.springframework.stereotype.Service;
import roomescape.entities.ReservationTime;
import roomescape.errors.ErrorCode;
import roomescape.exceptions.SpringRoomException;
import roomescape.repositories.ReservationRepository;
import roomescape.repositories.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationTimeService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationRepository reservationRepository, ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public void saveTime(ReservationTime reservationTime){
        reservationTimeRepository.save(reservationTime);
    }

    public ReservationTime findByTime(String time){
        return reservationTimeRepository.findByTime(time);
    }

    public List<ReservationTime> findAllTimes(){
        return reservationTimeRepository.findAll();
    }

    public void cancelReservationTime(Long id){
        if (reservationRepository.findByReservationTimeId(id) != null) {
            throw new SpringRoomException(ErrorCode.RESERVATION_TIME_CANNOT_BE_DELETED);
        }
        reservationTimeRepository.deleteById(id);
    }
}
