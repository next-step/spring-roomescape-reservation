package roomescape.admin;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AdminService {

    public List<ReadReservationResponse> readReservation() {
        Reservation.init();
        insertTempData();
        return read();
    }

    private void insertTempData(){
        SaveReservationRequest Reservation1 = new SaveReservationRequest("브라운", "2023-01-01", "10:00");
        SaveReservationRequest Reservation2 = new SaveReservationRequest("브라운", "2023-01-02", "11:00");

        Reservation.add(Reservation1);
        Reservation.add(Reservation2);
    }
    public List<ReadReservationResponse> saveReservation(SaveReservationRequest saveReservationRequest) {
        Reservation.init();
        Reservation.add(saveReservationRequest);
        return read();
    }

    private List<ReadReservationResponse> read(){
        return ReadReservationResponse.entityToList(Reservation.getReservations());
    }

    public void deleteReservation(Long id) {
        Reservation.init();
        insertTempData();
        Reservation.delete(id);
    }
}