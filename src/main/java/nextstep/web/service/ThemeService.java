package nextstep.web.service;

import nextstep.domain.theme.model.Theme;
import nextstep.domain.theme.service.ThemeDomainService;
import nextstep.web.endpoint.theme.request.ThemeCreateRequest;
import nextstep.web.endpoint.theme.response.ThemeResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeService {
    private final ThemeDomainService themeDomainService;

    public ThemeService(ThemeDomainService themeDomainService) {
        this.themeDomainService = themeDomainService;
    }

    public Long create(ThemeCreateRequest request) {
        Theme theme = themeDomainService.create(request.getName(), request.getDesc(), request.getPrice());

        return theme.getId();
    }

    public void deleteById(Long id) {
        themeDomainService.delete(id);
    }

    public List<ThemeResponse> findAll() {
        List<Theme> themes = themeDomainService.findAll();

        return ThemeResponse.fromList(themes);
    }
}
