package roomescape.theme.application;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import roomescape.exception.BadRequestException;
import roomescape.exception.NotFoundException;
import roomescape.theme.domain.ThemeRepository;
import roomescape.theme.ui.dto.ThemeRequest;

@Service
public class ThemeValidator {
    private final ThemeRepository themeRepository;

    public ThemeValidator(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public void checkPresent(Long themeId) {
        try {
            themeRepository.findById(themeId);
        }
        catch (EmptyResultDataAccessException exception) {
            throw new NotFoundException("존재하지 않는 테마입니다.");
        }
    }

    public void validateRequest(ThemeRequest themeRequest) {
        checkDuplicated(themeRequest.getName());
    }

    private void checkDuplicated(String name) {
        try {
            themeRepository.findByName(name);
            throw new BadRequestException("이미 존재하는 테마입니다.");
        }
        catch (EmptyResultDataAccessException ignored) {
        }
    }
}
