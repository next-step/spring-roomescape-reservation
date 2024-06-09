package roomescape;

import org.springframework.stereotype.Service;

@Service
public class ReservationAdminService {
    private final ReservationDAO reservationDAO;

    public ReservationAdminService(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    public Reservation createReservation(Reservation reservation) {
        return reservationDAO.insertReservation(reservation);
    }
}
