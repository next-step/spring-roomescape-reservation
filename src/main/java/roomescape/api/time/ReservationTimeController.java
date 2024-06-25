package roomescape.api.time;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.time.CreateReservationTime;
import roomescape.domain.time.ReservationTime;
import roomescape.domain.time.ReservationTimeRepository;

@RestController
@RequestMapping("times")
public class ReservationTimeController {
  private final ReservationTimeRepository repository;

  public ReservationTimeController(ReservationTimeRepository repository) {
    this.repository = repository;
  }

  @GetMapping
  public List<ReservationTime> findAll() {
    return repository.findAll();
  }

  @PostMapping
  public ReservationTime create(@RequestBody CreateReservationTime newReservationTime) {
    return repository.save(newReservationTime);
  }

  @DeleteMapping("{id}")
  public void deleteById(@PathVariable("id") long id) {
    repository.deleteById(id);
  }
}
