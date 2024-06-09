package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.model.Reservation;
import roomescape.model.ReservationCreateDto;
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

    public ReservationCreateDto createReservation(ReservationCreateDto reservationCreateDto) {
        reservationTimeDAO.findReservationTimeById(reservationCreateDto.getTimeId());
        return reservationDAO.insertReservation(reservationCreateDto);
    }

    public List<Reservation> getReservations() {
        return reservationDAO.readReservations();
    }

    public void deleteReservation(Long id) {
        reservationDAO.deleteReservation(id);
    }
}
