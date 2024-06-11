package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.model.Reservation;
import roomescape.model.ReservationCreateDto;
import roomescape.model.ReservationTime;
import roomescape.respository.ReservationDAO;
import roomescape.respository.ReservationTimeDAO;

import java.util.List;

@Service
public class ReservationAdminService {
    private final ReservationDAO reservationDAO;
    private final ReservationTimeDAO reservationTimeDAO;

    public ReservationAdminService(ReservationDAO reservationDAO, ReservationTimeDAO reservationTimeDAO) {
        this.reservationDAO = reservationDAO;
        this.reservationTimeDAO = reservationTimeDAO;
    }

    public Long createReservation(ReservationCreateDto reservationCreateDto) {
        ReservationTime reservationTime = reservationTimeDAO.findReservationTimeById(reservationCreateDto.getTimeId());
        return reservationDAO.insertReservation(reservationCreateDto, reservationTime.getId());
    }

    public List<Reservation> getReservations() {
        return reservationDAO.readReservations();
    }

    public void deleteReservation(Long id) {
        reservationDAO.deleteReservation(id);
    }
}
