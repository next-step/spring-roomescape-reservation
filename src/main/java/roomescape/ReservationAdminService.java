package roomescape;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationAdminService {
    private final ReservationDAO reservationDAO;

    public ReservationAdminService(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    public Reservation createReservation(Reservation reservation) {
        return reservationDAO.insertReservation(reservation);
    }

    public List<Reservation> getReservations() {
        return reservationDAO.readReservations();
    }

    public void deleteReservation(Long id) {
        reservationDAO.deleteReservation(id);
    }
}
