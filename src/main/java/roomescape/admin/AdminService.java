package roomescape.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public List<ReadReservationResponse> readReservation() {
        List<Reservation> reservation = this.adminRepository.readReservation();

        return ReadReservationResponse.entityToList(reservation);
    }

    public ReadReservationResponse saveReservation(SaveReservationRequest saveReservationRequest) {
        Reservation reservation = Reservation.add(saveReservationRequest);
        Long id = this.adminRepository.saveReservation(saveReservationRequest);

        return new ReadReservationResponse(id, reservation);
    }

    public void deleteReservation(Long id) {
        this.adminRepository.deleteReservation(id);
    }
}