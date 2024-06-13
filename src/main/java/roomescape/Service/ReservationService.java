package roomescape.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.DTO.ReservationRequest;
import roomescape.DTO.ReservationResponse;
import roomescape.Entity.Reservation;
import roomescape.Exception.NotFoundException;
import roomescape.Repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponse> findAll() {
        List<Reservation> reservations = reservationRepository.findAll();

        if (reservations.isEmpty()) {
            throw new NotFoundException("등록된 예약이 없습니다.");
        }
        return ReservationResponse.toDTOList(reservations);
    }

    public ReservationResponse findOne(Long id) {
        Reservation reservation = reservationRepository.findById(id);
        return new ReservationResponse(reservation);
    }

    public long make(ReservationRequest request) {
        return reservationRepository.save(
                request.getName(),
                request.getDate(),
                request.getTimeId(),
                request.getThemeId()
        );
    }

    public void cancel(Long id) {
        long deleteCount = reservationRepository.deleteById(id);

        if (deleteCount == 0) {
            throw new NotFoundException("id와 일치하는 reservation이 없음");
        }
    }
}
