package nextstep.app.web.theme.application.service;

import nextstep.app.web.theme.application.dto.CreateThemeCommand;
import nextstep.app.web.theme.application.dto.CreateThemeResult;
import nextstep.app.web.theme.application.usecase.CreateThemeUseCase;
import nextstep.app.web.theme.application.usecase.DeleteThemeUseCase;
import nextstep.domain.theme.domain.service.ThemeDomainService;
import org.springframework.stereotype.Service;

@Service
public class ThemeAppService implements CreateThemeUseCase, DeleteThemeUseCase {
    private final ThemeDomainService themeDomainService;

    public ThemeAppService(ThemeDomainService themeDomainService) {
        this.themeDomainService = themeDomainService;
    }

    @Override
    public CreateThemeResult create(CreateThemeCommand command) {
        Long id = themeDomainService.create(command.name(), command.desc(), command.price());
        return new CreateThemeResult(id);
    }

    @Override
    public void deleteById(Long id) {
        themeDomainService.deleteById(id);
    }
}
