package com.nextstep.web.theme.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateThemeRequest {
    private String name;
    private String desc;
    private Long price;

    public CreateThemeRequest(String name, String desc, Long price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }
}
