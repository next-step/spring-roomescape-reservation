package com.nextstep.web.theme.dto;

import com.nextstep.web.theme.repository.entity.ThemeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ThemeResponse {
    private Long id;
    private String name;
    private String desc;
    private Long price;

    private ThemeResponse(Long id, String name, String desc, Long price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public static ThemeResponse of(ThemeEntity themeEntity) {
        return new ThemeResponse(themeEntity.getId(), themeEntity.getName(),
                themeEntity.getDesc(), themeEntity.getPrice());
    }
    public static List<ThemeResponse> toListFromEntity(List<ThemeEntity> themeEntities) {
        return themeEntities.stream()
                .map(themeEntity -> new ThemeResponse(themeEntity.getId(), themeEntity.getName(),
                        themeEntity.getDesc(), themeEntity.getPrice()))
                .collect(Collectors.toList());
    }
}
