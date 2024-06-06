package roomescape.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.admin.dto.ReadReservationResponse;
import roomescape.admin.dto.ReadReservationTimeResponse;
import roomescape.admin.dto.SaveReservationRequest;
import roomescape.admin.dto.SaveReservationTimeRequest;
import roomescape.admin.entity.Reservation;
import roomescape.admin.entity.ReservationTime;
import roomescape.admin.repository.AdminRepository;

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
        Long id = this.adminRepository.saveReservation(saveReservationRequest);
        Reservation reservation = this.adminRepository.readReservationById(id);

        return ReadReservationResponse.entityToDTO(reservation);
    }

    public void deleteReservation(Long id) {
        this.adminRepository.deleteReservation(id);
    }
}