package roomescape.Repository;

import roomescape.DTO.ThemeRequest;
import roomescape.Entity.Theme;

import java.util.List;

public interface ThemeRepository {
    List<Theme> findAll();
    Theme findById(Long id);
    long save(ThemeRequest request);
    long deleteById(Long id);
}
