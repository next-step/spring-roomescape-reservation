package roomescape.support;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import roomescape.RoomescapeApplication;

@ActiveProfiles("test")
@SpringBootTest(classes = RoomescapeApplication.class)
public abstract class IntegrationTestSupport {

    @Autowired
    DataCleanser dataCleanser;

    @BeforeEach
    void beforeEach() {
        dataCleanser.clean();
    }
}
