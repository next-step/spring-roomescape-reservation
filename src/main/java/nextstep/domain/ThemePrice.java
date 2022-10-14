package nextstep.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class ThemePrice {

    private final BigDecimal value;

    public ThemePrice(BigDecimal value) {
        this.value = value;
        validateNull(this.value);
        validateNegative(this.value);
    }

    private void validateNull(BigDecimal value) {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException("올바르지 않은 테마금액입니다.");
        }
    }

    private void validateNegative(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("테마금액은 0 보다 작을 수 없습니다.");
        }
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ThemePrice themePrice = (ThemePrice) o;
        return Objects.equals(value, themePrice.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
