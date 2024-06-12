package roomescape.reservation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservationTime.ReservationTime;
import roomescape.reservationTime.ReservationTimeResponseDto;

import java.util.List;

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
                                .reservationTimeResponseDto(new ReservationTimeResponseDto(
                                        reservation.getReservationTime().getId(),
                                        reservation.getReservationTime().getStartAt()))
                                .time(reservation.getTime())
                                .build()
                ).toList();
    }

    @Transactional
    public ReservationResponseDto save(ReservationRequestDto reservationRequestDto) {
        final Reservation reservation = new Reservation.Builder().name(reservationRequestDto.getName())
                .date(reservationRequestDto.getDate())
                .reservationTime(
                        new ReservationTime(
                                reservationRequestDto.getReservationTimeRequestDto().getId(),
                                reservationRequestDto.getReservationTimeRequestDto().getStartAt()))
                .time(reservationRequestDto.getTime())
                .build();

        final Long savedId = reservationRepository.save(reservation);
        final Reservation savedReservation = reservationRepository.findById(savedId);

        return new ReservationResponseDto.Builder()
                .id(savedReservation.getId())
                .name(savedReservation.getName())
                .date(savedReservation.getDate())
                .reservationTimeResponseDto(
                        new ReservationTimeResponseDto(savedReservation.getReservationTime().getId(),
                                savedReservation.getReservationTime().getStartAt()))
                .time(savedReservation.getTime())
                .build();
    }


    @Transactional
    public void delete(Long id) {
        final boolean isExistedReservation = reservationRepository.existsById(id);
        if (!isExistedReservation) {
            throw new IllegalArgumentException("해당 예약이 존재하지 않습니다.");
        }
        reservationRepository.deleteById(id);
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
