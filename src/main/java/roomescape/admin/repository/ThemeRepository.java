package roomescape.admin.repository;

import org.springframework.stereotype.Repository;
import roomescape.admin.dto.SaveThemeRequest;
import roomescape.admin.entity.Theme;

import java.util.List;

@Repository
public class ThemeRepository {
    public List<Theme> findAll() {
        return null;
    }

    public Long save(SaveThemeRequest saveThemeRequest) {
        return null;
    }

    public Theme findById(Long id) {
        return null;
    }

    public void delete(Long id) {

    }
}
