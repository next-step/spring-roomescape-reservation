package roomescape.service;

import java.util.List;
import java.util.stream.Collectors;

import roomescape.controller.dto.ReservationTimeRequest;
import roomescape.controller.dto.ReservationTimeResponse;
import roomescape.domain.ReservationTime;
import roomescape.exception.ErrorCode;
import roomescape.exception.RoomEscapeException;
import roomescape.repository.ReservationTimeRepository;

import org.springframework.stereotype.Service;

@Service
public class ReservationTimeService {

	private final ReservationTimeRepository reservationTimeRepository;

	ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
		this.reservationTimeRepository = reservationTimeRepository;
	}

	public ReservationTimeResponse create(ReservationTimeRequest request) {
		var reservationTime = ReservationTime.builder().startAt(request.startAt()).build();
		var savedReservationTime = this.reservationTimeRepository.save(reservationTime);
		return ReservationTimeResponse.from(savedReservationTime);
	}

	public List<ReservationTimeResponse> getReservationTimes() {
		return this.reservationTimeRepository.findAll()
			.stream()
			.map(ReservationTimeResponse::from)
			.collect(Collectors.toList());
	}

	public void delete(long id) {
		var isExist = this.reservationTimeRepository.isExistId(id);
		if (isExist) {
			this.reservationTimeRepository.delete(id);
		}
		else {
			throw new RoomEscapeException(ErrorCode.NOT_FOUND_RESERVATION_TIME);
		}
	}

	public ReservationTime getReservationTimeById(long id) {
//		try {
			return this.reservationTimeRepository.findById(id);
//		}
//		catch (Exception ex) {
//			throw new RoomEscapeException(ErrorCode.NOT_FOUND_RESERVATION_TIME);
//		}

	}

}
