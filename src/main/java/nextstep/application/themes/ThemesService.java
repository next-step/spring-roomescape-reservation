package nextstep.application.themes;

import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.application.themes.dto.Themes;
import nextstep.application.themes.dto.ThemesRes;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThemesService {

  public Long create(Themes themes) {
    return null;
  }

  public List<ThemesRes> findAllThemes() {
    return null;
  }

  public void delete(Long id) {

  }
}
