package roomescape.admin;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AdminService {

    public List<ReadReservationResponse> readReservation() {
        return ReadReservationResponse.entityToList(Reservation.getReservations());
    }

    public ReadReservationResponse saveReservation(SaveReservationRequest saveReservationRequest) {
        Reservation reservation = Reservation.add(saveReservationRequest);

        return new ReadReservationResponse(reservation);
    }

    private List<ReadReservationResponse> read(){
        return ReadReservationResponse.entityToList(Reservation.getReservations());
    }

    public void deleteReservation(Long id) {
        Reservation.delete(id);
    }
}