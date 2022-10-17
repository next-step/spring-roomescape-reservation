package com.nextstep.web.theme.app;

import com.nextstep.web.theme.dto.ThemeResponse;
import com.nextstep.web.theme.repository.ThemeDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ThemeQueryService {
    private final ThemeDao themeDao;

    public ThemeQueryService(ThemeDao themeDao) {
        this.themeDao = themeDao;
    }

    public List<ThemeResponse> findAll() {
        return ThemeResponse.toListFromEntity(themeDao.findAll());
    }
}
