package nextstep.web.schedule.dto;

import nextstep.domain.schedule.service.ThemeDto;

public class ThemeWebDto {
    public Long id;
    public String name;
    public String desc;
    public Long price;

    public ThemeWebDto(ThemeDto dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.desc = dto.getDesc();
        this.price = dto.getPrice();
    }
}
