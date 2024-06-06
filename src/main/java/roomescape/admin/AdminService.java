package roomescape.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public List<ReadReservationTimeResponse> readReservationTime() {
        List<ReservationTime> reservationTime = this.adminRepository.readReservationTime();

        return ReadReservationTimeResponse.entityToList(reservationTime);
    }

    public ReadReservationTimeResponse saveReservationTime(SaveReservationTimeRequest saveReservationTimeRequest) {
        Long id = this.adminRepository.saveReservationTime(saveReservationTimeRequest);
        ReservationTime reservationTime = this.adminRepository.readReservationTimeById(id);

        return ReadReservationTimeResponse.entityToDTO(reservationTime);
    }

    public void deleteReservationTime(Long id) {
        this.adminRepository.deleteReservationTime(id);
    }

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