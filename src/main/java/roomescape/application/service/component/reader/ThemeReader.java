package roomescape.application.service.component.reader;

import org.springframework.stereotype.Component;
import roomescape.application.error.exception.NotFoundEntityException;
import roomescape.application.mapper.ThemeMapper;
import roomescape.domain.theme.Theme;
import roomescape.domain.theme.vo.ThemeId;
import roomescape.repository.ThemeRepository;

import static roomescape.application.error.code.ApplicationErrorCode.NOT_FOUND_ENTITY;

@Component
public class ThemeReader {

    private final ThemeRepository themeRepository;

    public ThemeReader(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public Theme readById(ThemeId themeId) {
        return ThemeMapper.toTheme(
                themeRepository.findById(themeId.id())
                        .orElseThrow(() -> new NotFoundEntityException(
                                NOT_FOUND_ENTITY,
                                String.format("[themeId:%d] 해당하는 엔티티를 찾을 수 없습니다.", themeId.id())
                        ))
        );
    }
}
