package roomescape.admin.service;

import org.springframework.stereotype.Service;
import roomescape.admin.dto.ReadThemeResponse;
import roomescape.admin.dto.SaveThemeRequest;
import roomescape.admin.entity.Theme;
import roomescape.admin.repository.ThemeRepository;
import roomescape.common.exception.DBException;
import roomescape.common.exception.PolicyException;

import java.util.List;

import static roomescape.common.exception.DBException.SEARCH_ERROR;
import static roomescape.common.exception.PolicyException.THEME_IN_USE_RESERVATION_ERROR;

@Service
public class ThemeService {
    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository){
        this.themeRepository = themeRepository;
    }

    public List<ReadThemeResponse> readTheme() {
        List<Theme> reservationTime = this.themeRepository.findAll()
                .orElseThrow(() -> new DBException(SEARCH_ERROR));

        return ReadThemeResponse.from(reservationTime);
    }

    public ReadThemeResponse saveTheme(SaveThemeRequest saveThemeRequest) {
        Long id = this.themeRepository.save(saveThemeRequest);

        return ReadThemeResponse.from(readThemeById(id));
    }

    public Theme readThemeById(Long id) {
        return this.themeRepository.findById(id)
                .orElseThrow(() -> new DBException(SEARCH_ERROR));
    }

    public void deleteTheme(Long id) {
        if(isContainReservation(id)){
            throw new PolicyException(THEME_IN_USE_RESERVATION_ERROR);
        }

        this.themeRepository.delete(id);
    }

    private boolean isContainReservation(long themeId) {
        return isExistData(readThemeCount(themeId));
    }

    private Integer readThemeCount(long themeId) {
        return this.themeRepository.countById(themeId)
                .orElseThrow(() -> new DBException(SEARCH_ERROR));
    }

    private boolean isExistData(int count){
        return count > 0;
    }


}