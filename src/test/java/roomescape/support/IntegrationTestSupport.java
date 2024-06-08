package roomescape.support;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import roomescape.domain.reservation.repository.MemoryReservationRepository;

@SpringBootTest
public abstract class IntegrationTestSupport {

    @Autowired
    private MemoryReservationRepository memoryReservationRepository;

    @BeforeEach
    void beforeEach() {
        memoryReservationRepository.clearAll();
    }

}
