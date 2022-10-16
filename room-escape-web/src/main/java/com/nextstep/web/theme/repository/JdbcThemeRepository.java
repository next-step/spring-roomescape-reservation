package com.nextstep.web.theme.repository;

import com.nextstep.web.theme.repository.entity.ThemeEntity;
import nextsetp.domain.theme.Theme;
import nextsetp.domain.theme.ThemeRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JdbcThemeRepository implements ThemeRepository {
    private final ThemeDao themeDao;

    public JdbcThemeRepository(ThemeDao themeDao) {
        this.themeDao = themeDao;
    }

    @Override
    public Long save(Theme theme) {
        return themeDao.save(ThemeEntity.of(theme));
    }

    @Override
    public List<Theme> findAll() {
        return themeDao.findAll().stream()
                .map(themeEntity -> new Theme(themeEntity.getName(), themeEntity.getDesc(), themeEntity.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        themeDao.delete(id);
    }
}
