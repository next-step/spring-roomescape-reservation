package roomescape.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.DTO.ReservationTimeRequest;
import roomescape.DTO.ReservationTimeResponse;
import roomescape.Entity.ReservationTime;
import roomescape.Exception.NotFoundException;
import roomescape.Repository.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    @Autowired
    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTimeResponse> findAll() {
        List<ReservationTime> times = reservationTimeRepository.findAll();

        if (times.isEmpty()) {
            throw new NotFoundException("등록된 예약 시간이 없습니다.");
        }
        return ReservationTimeResponse.toDTOList(times);
    }

    public ReservationTimeResponse findOne(Long id) {
        ReservationTime time = reservationTimeRepository.findById(id);
        return new ReservationTimeResponse(time);
    }

    public long add(ReservationTimeRequest request) {
        return reservationTimeRepository.save(request.getStartAt());
    }

    public void delete(Long id) {
        long deleteCount = reservationTimeRepository.cancelById(id);

        if (deleteCount == 0) {
            throw new NotFoundException("id와 일치하는 예약 시간이 없습니다.");
        }
    }
}
