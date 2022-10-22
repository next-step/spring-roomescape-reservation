package nextstep.web.theme.dto;

import nextstep.domain.theme.service.ThemeResponse;

public class ThemeWebResponse {
    public Long id;
    public String name;
    public String desc;
    public Long price;

    public ThemeWebResponse(ThemeResponse response) {
        this.id = response.getId();
        this.name = response.getName();
        this.desc = response.getDesc();
        this.price = response.getPrice();
    }
}
