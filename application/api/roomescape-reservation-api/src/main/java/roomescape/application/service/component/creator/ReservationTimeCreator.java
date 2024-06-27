package roomescape.application.service.component.creator;

import org.springframework.stereotype.Component;
import roomescape.application.service.component.validator.CreateReservationTimeValidator;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.domain.reservationtime.ReservationTimeRepository;

@Component
public class ReservationTimeCreator {

    private final ReservationTimeRepository repository;
    private final CreateReservationTimeValidator validator;

    public ReservationTimeCreator(ReservationTimeRepository repository, CreateReservationTimeValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public ReservationTime create(ReservationTime time) {
        validator.validate(time);
        
        return repository.save(time);
    }
}
