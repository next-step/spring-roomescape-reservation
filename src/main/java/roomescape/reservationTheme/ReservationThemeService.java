package roomescape.reservationTheme;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationThemeService {

    private final ReservationThemeRepository reservationThemeRepository;

    public ReservationThemeService(ReservationThemeRepository reservationThemeRepository) {
        this.reservationThemeRepository = reservationThemeRepository;
    }

    public List<ReservationThemeResponseDto> getThemes() {
        final List<ReservationTheme> themes = reservationThemeRepository.findAll();
        return themes.stream()
                .map(theme -> new ReservationThemeResponseDto(theme.getId(), theme.getName(), theme.getDescription(), theme.getThumbnail()))
                .collect(Collectors.toList());
    }

    @Transactional
    public ReservationThemeResponseDto createTheme(ReservationThemeRequestDto requestDto) {
        final ReservationTheme theme = new ReservationTheme.Builder()
                .name(requestDto.getName())
                .description(requestDto.getDescription())
                .thumbnail(requestDto.getThumbnail())
                .build();

        final Long id = reservationThemeRepository.save(theme);
        final ReservationTheme savedTheme = reservationThemeRepository.findById(id);

        return new ReservationThemeResponseDto(savedTheme.getId(), savedTheme.getName(), savedTheme.getDescription(), savedTheme.getThumbnail());

    }

    @Transactional
    public void deleteTheme(Long id) {
        final Boolean isExistedTheme = reservationThemeRepository.existById(id);
        if (!isExistedTheme) {
            throw new IllegalArgumentException("해당 테마가 존재하지 않습니다.");
        }
        reservationThemeRepository.deleteById(id);

    }
}
