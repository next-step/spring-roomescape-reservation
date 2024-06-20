package roomescape.reservationtime;


import org.springframework.stereotype.Service;
import roomescape.entities.ReservationTime;
import roomescape.errors.ErrorCode;
import roomescape.exceptions.SpringRoomException;
import roomescape.repositories.ReservationRepository;
import roomescape.repositories.ReservationTimeRepository;
import roomescape.reservationtime.data.ReservationTimeAddRequestDto;

import java.util.List;

@Service
public class ReservationTimeService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationRepository reservationRepository, ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTime saveTime(ReservationTimeAddRequestDto reservationTimeAddRequestDto){
        ReservationTime reservationTime = new ReservationTime(reservationTimeAddRequestDto.getStartAt());
        return reservationTimeRepository.save(reservationTime);
    }

    public ReservationTime findByStartAt(String startAt){
        return reservationTimeRepository.findByStartAt(startAt);
    }

    public List<ReservationTime> findAllTimes(){
        return reservationTimeRepository.findAll();
    }

    public void cancelReservationTime(Long id){
        if (reservationRepository.findByReservationTimeId(id).isPresent()) {
            throw new SpringRoomException(ErrorCode.RESERVATION_TIME_CANNOT_BE_DELETED, "Reservation time cannot be deleted because it is used in a reservation");
        }
        reservationTimeRepository.deleteById(id);
    }
}
