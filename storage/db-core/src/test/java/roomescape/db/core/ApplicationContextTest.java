package roomescape.db.core;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(properties = "spring.config.location=classpath:db-core-test.yml")
public abstract class ApplicationContextTest {

    @Autowired
    DataCleanser dataCleanser;

    @BeforeEach
    void beforeEach() {
        dataCleanser.clean();
    }
}
