package nextstep.web.endpoint.schedule.response;

import lombok.Getter;
import lombok.ToString;
import nextstep.domain.theme.model.Theme;

@Getter
@ToString
public class ScheduleThemeResponse {
    private final Long id;
    private final String name;
    private final String desc;
    private final Long price;

    public ScheduleThemeResponse(Long id, String name, String desc, Long price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public static ScheduleThemeResponse from(Theme theme) {
        return new ScheduleThemeResponse(
                theme.getId(),
                theme.getName(),
                theme.getDesc(),
                theme.getPrice()
        );
    }
}