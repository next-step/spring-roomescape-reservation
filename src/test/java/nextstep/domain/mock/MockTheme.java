package nextstep.domain.mock;

import nextstep.domain.theme.model.Theme;

public class MockTheme {
    private final Long id;
    private final String name;
    private final String desc;
    private final Long price;

    public MockTheme(Long id, String name, String desc, Long price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public static Theme from(String name, String desc, Long price) {
        return new Theme(
                1L,
                name,
                desc,
                price
        );
    }

    public static Theme from() {
        return new Theme(
                1L,
                "신지혜",
                "서얼명",
                1000L
        );
    }
}
