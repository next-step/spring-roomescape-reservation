package nextstep.domain.theme.model;

import lombok.Getter;

@Getter
public class Theme {
    private final Long id;
    private final String name;
    private final String desc;
    private final Long price;

    public Theme(String name, String desc, Long price) {
        this(null, name, desc, price);
    }

    public Theme(Long id, String name, String desc, Long price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }
}
