package nextstep.ui;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import nextstep.application.RoomEscapeService;
import nextstep.application.dto.RoomReservationReq;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class RoomEscapeController {

  private final RoomEscapeService service;

  @PostMapping
  public ResponseEntity create(@RequestBody RoomReservationReq req) {
    Long id = service.create(req);
    return ResponseEntity.created(URI.create("/reservations/" + id)).build();
  }

}
