package nextstep.domain.theme;

import java.util.List;
import nextstep.domain.theme.dto.ThemeCommandDto;

public interface ThemeRepository {

  default Theme save(ThemeCommandDto.Create createReq) {
    throw new UnsupportedOperationException();
  }

  default boolean delete(ThemeCommandDto.Delete deleteReq) {
    throw new UnsupportedOperationException();
  }

  default List<Theme> findAll() {
    throw new UnsupportedOperationException();
  }
}
