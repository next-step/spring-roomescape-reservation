package nextstep.themes.service;

import java.util.List;
import nextstep.themes.Themes;
import nextstep.themes.dao.ThemesDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ThemesService {

    private final ThemesDao themesDao;

    public ThemesService(ThemesDao themesDao) {
        this.themesDao = themesDao;
    }

    @Transactional
    public Long createThemes(String name, String desc, Long price) {
        return themesDao.insert(name, desc, price);
    }

    @Transactional(readOnly = true)
    public List<Themes> findThemes() {
        return themesDao.findAll();
    }

    @Transactional
    public void removeThemes(Long id) {
        themesDao.removeById(id);
    }
}
