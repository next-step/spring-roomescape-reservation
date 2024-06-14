package roomescape.Repository;

import roomescape.DTO.ThemeRequest;
import roomescape.Entity.Theme;

import java.util.List;

public interface ThemeRepository {
    List<Theme> findAll();
    Theme findById(Long id);
    Theme findByName(String name);
    Long countReservationMatchWith(Long id);
    long save(String name, String description, String thumbnail);
    long deleteById(Long id);
}
