package roomescape.Service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import roomescape.DTO.ThemeRequest;
import roomescape.DTO.ThemeResponse;
import roomescape.Entity.Theme;
import roomescape.Exception.BadRequestException;
import roomescape.Exception.NotFoundException;
import roomescape.Repository.ThemeRepository;

import java.util.List;

@Service
public class ThemeService {
    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public List<ThemeResponse> findAll() {
        List<Theme> themes = themeRepository.findAll();

        if (themes.isEmpty()) {
            throw new NotFoundException("등록된 테마가 없습니다.");
        }
        return ThemeResponse.toResponses(themes);
    }

    public ThemeResponse findOne(Long id) {
        Theme theme = themeRepository.findById(id);
        return new ThemeResponse(theme);
    }

    public long add(ThemeRequest request) {
        validateDuplicated(request.getName());
        return themeRepository.save(
                request.getName(),
                request.getDescription(),
                request.getThumbnail()
        );
    }

    private void validateDuplicated(String name) {
        try {
            Theme theme = themeRepository.findByName(name);

            throw new BadRequestException("이미 존재하는 테마입니다.");
        }
        catch (EmptyResultDataAccessException ignored) {
        }
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
