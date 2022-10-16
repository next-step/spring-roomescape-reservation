package com.nextstep.web.theme.repository.entity;

import lombok.Getter;
import nextsetp.domain.theme.Theme;

@Getter
public class ThemeEntity {
    private Long id;
    private String name;
    private String desc;
    private Long price;

    public ThemeEntity(Long id, String name, String desc, Long price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public static ThemeEntity of(Theme theme) {
        return new ThemeEntity(null, theme.getName(), theme.getDesc(), theme.getPrice());
    }
}
