package nextstep.api.theme.dto;

import nextstep.domain.theme.model.Theme;

public class ThemeCreateRequest {
    private String name;
    private String desc;
    private Long price;

    public ThemeCreateRequest(String name, String desc, Long price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public Theme toEntity() {
        return new Theme(null, name, desc, price);
    }
}
