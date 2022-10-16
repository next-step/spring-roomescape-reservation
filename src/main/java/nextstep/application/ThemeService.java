package nextstep.application;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import nextstep.domain.Theme;
import nextstep.domain.repository.ThemeRepository;
import nextstep.exception.ThemeException;
import nextstep.presentation.dto.ThemeRequest;
import nextstep.presentation.dto.ThemeResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class ThemeService {

    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    @Transactional
    public Long create(ThemeRequest request) {
        Optional<Theme> theme = findBy(request);

        if (theme.isPresent()) {
            throw new ThemeException(String.format("%s는(은) 이미 존재하는 테마입니다.", request.getName()));
        }

        themeRepository.save(getTheme(request));
        return findBy(request)
            .map(Theme::getId)
            .orElseThrow(() -> new ThemeException("존재하지 않는 테마입니다."));
    }

    private Optional<Theme> findBy(ThemeRequest request) {
        return themeRepository.findBy(request.getName());
    }

    private Theme getTheme(ThemeRequest request) {
        return new Theme(request.getName(), request.getDesc(), request.getPrice());
    }

    public List<ThemeResponse> checkAll() {
        List<Theme> themes = themeRepository.findAll();

        return themes.stream()
            .map(this::getResponse)
            .collect(Collectors.toList());
    }

    private ThemeResponse getResponse(Theme theme) {
        return new ThemeResponse(theme.getId(), theme.getName(), theme.getDesc(), theme.getPrice());
    }

    @Transactional
    public void delete(Long id) {
        themeRepository.delete(id);
    }

    @Transactional
    public void deleteAll() {
        themeRepository.deleteAll();
    }
}
