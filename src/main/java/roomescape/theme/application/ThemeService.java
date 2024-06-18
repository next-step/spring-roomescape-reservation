package roomescape.theme.application;

import org.springframework.stereotype.Service;
import roomescape.theme.ui.dto.ThemeRequest;
import roomescape.theme.ui.dto.ThemeResponse;
import roomescape.theme.domain.entity.Theme;
import roomescape.exception.BadRequestException;
import roomescape.exception.NotFoundException;
import roomescape.theme.domain.ThemeRepository;

import java.util.List;

@Service
public class ThemeService {
    private final ThemeRepository themeRepository;
    private final ThemeValidator themeValidator;

    public ThemeService(
            ThemeRepository themeRepository,
            ThemeValidator themeValidator) {
        this.themeRepository = themeRepository;
        this.themeValidator = themeValidator;
    }

    public List<ThemeResponse> findAll() {
        List<Theme> themes = themeRepository.findAll();
        return ThemeResponse.fromThemes(themes);
    }

    public ThemeResponse findOne(Long id) {
        Theme theme = themeRepository.findById(id);
        return ThemeResponse.from(theme);
    }

    public long add(ThemeRequest request) {
        themeValidator.validateRequest(request);
        return themeRepository.save(
                request.getName(),
                request.getDescription(),
                request.getThumbnail()
        );
    }

    public void delete(Long id) {
        checkMatchingReservation(id);
        long deleteCount = themeRepository.deleteById(id);

        if (deleteCount == 0) {
            throw new NotFoundException("id에 일치하는 테마가 없습니다.");
        }
    }

    private void checkMatchingReservation(Long id) {
        if (themeRepository.countReservationMatchWith(id) > 0) {
            throw new BadRequestException("해당 테마에 대한 예약이 존재합니다.");
        }
    }
}
