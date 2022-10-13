package nextstep.domain;

import java.math.BigDecimal;

public class Fixtures {

    public static ThemePrice themePrice(long value) {
        return new ThemePrice(BigDecimal.valueOf(value));
    }
}
