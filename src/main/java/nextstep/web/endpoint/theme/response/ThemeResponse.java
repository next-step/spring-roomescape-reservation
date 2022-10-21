
package nextstep.web.endpoint.theme.response;

import lombok.Getter;
import lombok.ToString;
import nextstep.domain.theme.model.Theme;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
public class ThemeResponse {
    private final Long id;
    private final String name;
    private final String desc;
    private final Long price;

    public ThemeResponse(Long id, String name, String desc, Long price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public static List<ThemeResponse> fromList(List<Theme> themes) {
        return themes.stream().map(it ->
                new ThemeResponse(
                        it.getId(),
                        it.getName(),
                        it.getDesc(),
                        it.getPrice()
                )
        ).collect(Collectors.toList());
    }
}
