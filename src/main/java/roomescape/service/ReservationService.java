package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRq;
import roomescape.dto.ReservationRs;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;
import roomescape.model.Theme;
import roomescape.repository.ReservationRepo;
import roomescape.repository.ReservationTimeRepo;
import roomescape.repository.ThemeRepo;

import java.util.List;
import java.util.Optional;

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
        Optional<Long> optionalTimeId = Optional.ofNullable(reservationRq.getTimeId());
        Long timeId = optionalTimeId.orElseThrow(
                () -> new IllegalArgumentException("Invalid time ID")
        );

        Optional<ReservationTime> optionalReservationTime = Optional.ofNullable(reservationTimeRepo.findById(timeId));
        ReservationTime reservationTime = optionalReservationTime.orElseThrow(
                () -> new IllegalArgumentException("Reservation time not found")
        );

        Optional<Theme> optionalTheme = Optional.ofNullable(themeRepo.findById(reservationRq.getThemeId()));
        Theme theme = optionalTheme.orElseThrow(
                () -> new IllegalArgumentException("Theme not found")
        );

        // Reservation 객체 생성
        Reservation reservation = new Reservation(
                null,
                reservationRq.getName(),
                reservationRq.getDate(),
                reservationTime,
                theme
        );

        // 예약 저장
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
}
