package roomescape.reservation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponseDto> findAll() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(
                        reservation -> new ReservationResponseDto.Builder()
                                .id(reservation.getId())
                                .name(reservation.getName())
                                .date(reservation.getDate())
                                .time(reservation.getTime())
                                .build()
                ).toList();
    }

    @Transactional
    public ReservationResponseDto save(ReservationRequestDto reservationRequestDto) {
        final Reservation reservation = new Reservation.Builder().name(reservationRequestDto.getName())
                .date(reservationRequestDto.getDate())
                .time(reservationRequestDto.getTime())
                .build();

        final Long savedId = reservationRepository.save(reservation);
        final Reservation savedReservation = reservationRepository.findById(savedId);

        return new ReservationResponseDto.Builder()
                .id(savedReservation.getId())
                .name(savedReservation.getName())
                .date(savedReservation.getDate())
                .time(savedReservation.getTime())
                .build();
    }


    @Transactional
    public void delete(Long id) {
        if(!reservationRepository.existsById(id)) {
            throw new IllegalArgumentException("해당 예약이 존재하지 않습니다.");
        } else if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
        }
    }

    public ReservationResponseDto findById(Long id) {
        Reservation reservation = reservationRepository.findById(id);
        return new ReservationResponseDto.Builder()
                .id(reservation.getId())
                .name(reservation.getName())
                .date(reservation.getDate())
                .time(reservation.getTime())
                .build();
    }


}
