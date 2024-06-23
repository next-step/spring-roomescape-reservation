package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRq;
import roomescape.dto.ReservationRs;
import roomescape.exception.DuplicateReservationException;
import roomescape.exception.InvalidReservationException;
import roomescape.exception.ResourceNotFoundException;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;
import roomescape.model.Theme;
import roomescape.repository.ReservationRepo;
import roomescape.repository.ReservationTimeRepo;
import roomescape.repository.ThemeRepo;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepo reservationRepo;
    private final ReservationTimeRepo reservationTimeRepo;
    private final ThemeRepo themeRepo;

    public ReservationService(ReservationRepo reservationRepo, ReservationTimeRepo reservationTimeRepo, ThemeRepo themeRepo) {
        this.reservationRepo = reservationRepo;
        this.reservationTimeRepo = reservationTimeRepo;
        this.themeRepo = themeRepo;
    }

    public List<ReservationRs> getAllReservations() {
        return reservationRepo.findAll().stream()
                .map(reservation -> new ReservationRs(
                        reservation.getId(),
                        reservation.getName(),
                        reservation.getDate(),
                        reservation.getTime(),
                        reservation.getTheme()
                ))
                .toList();
    }

    public ReservationRs addReservation(ReservationRq reservationRq) {
        validateReservationRequest(reservationRq);

        ReservationTime reservationTime = reservationTimeRepo.findById(reservationRq.getTimeId())
                .orElseThrow(() -> new ResourceNotFoundException("Reservation time not found"));
        Theme theme = themeRepo.findById(reservationRq.getThemeId())
                .orElseThrow(() -> new ResourceNotFoundException("Theme not found"));

        checkForDuplicateReservation(reservationRq.getDate(), reservationRq.getTimeId());

        Reservation reservation = new Reservation(
                null,
                reservationRq.getName(),
                reservationRq.getDate(),
                reservationTime,
                theme
        );

        Long id = reservationRepo.save(reservation);

        return new ReservationRs(
                id,
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime(),
                reservation.getTheme()
        );
    }

    public void deleteReservation(Long id) {
        reservationRepo.deleteById(id);
    }

    private void validateReservationRequest(ReservationRq reservationRq) {
        if (reservationRq.getName() == null || reservationRq.getName().isEmpty()) {
            throw new InvalidReservationException("Reservation name is invalid");
        }
        if (reservationRq.getDate() == null || reservationRq.getDate().isBefore(LocalDate.now())) {
            throw new InvalidReservationException("Reservation date is invalid or in the past");
        }
    }

    private void checkForDuplicateReservation(LocalDate date, Long timeId) {
        boolean exists = reservationRepo.existsByDateAndTimeId(date, timeId);
        if (exists) {
            throw new DuplicateReservationException("A reservation already exists for this date and time");
        }
    }
}
