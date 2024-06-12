package roomescape.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.DTO.ThemeRequest;
import roomescape.DTO.ThemeResponse;
import roomescape.Entity.Theme;
import roomescape.Exception.NotFoundException;
import roomescape.Repository.ThemeRepository;

import java.util.List;

@Service
public class ThemeService {
    private final ThemeRepository themeRepository;

    @Autowired
    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public List<ThemeResponse> findAll() {
        List<Theme> themes = themeRepository.findAll();
        return ThemeResponse.toDTOList(themes);
    }

    public ThemeResponse findOne(Long id) {
        Theme theme = themeRepository.findById(id);
        return new ThemeResponse(theme);
    }

    public long add(ThemeRequest request) {
        return themeRepository.save(request);
    }

    public void delete(Long id) {
        long deleteCount = themeRepository.deleteById(id);

        if (deleteCount == 0) {
            throw new NotFoundException("id에 일치하는 테마가 없습니다.");
        }
    }
}
