package nextstep.controller;

import javax.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nextstep.controller.dto.ThemeViewRequest;
import nextstep.domain.theme.ThemeRepository;
import nextstep.domain.theme.dto.ThemeCommandDto.Delete;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/themes")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ThemeCommandController {

  ThemeRepository themeRepository;

  @PostMapping
  public ResponseEntity<Void> createTheme(@Valid @RequestBody ThemeViewRequest.Create createReq) {
    themeRepository.save(createReq.toDomainCommand());
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTheme(@Valid @PathVariable Long id) {
    themeRepository.delete(Delete.builder()
        .id(id)
        .build());
    return ResponseEntity.ok().build();
  }
}
