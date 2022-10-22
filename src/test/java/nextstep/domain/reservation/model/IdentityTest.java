package nextstep.domain.reservation.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IdentityTest {
    @AfterEach
    void tearDown() {
        Identity.reset(Example.class);
    }

    @Test
    void getId_WhenReceiveNewClass_ReturnInitialId() {
        Long actual = Identity.getId(Example.class);

        assertThat(actual).isEqualTo(Identity.INITIAL_ID);
    }

    @Test
    void getId_WhenReceiveInitializedClass_ReturnBiggerThanInitialId() {
        Identity.getId(Example.class);
        Long actual = Identity.getId(Example.class);

        assertThat(actual).isGreaterThan(Identity.INITIAL_ID);
    }

    class Example {
    }
}
