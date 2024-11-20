package roomescape.support;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import roomescape.RoomescapeApplication;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = RoomescapeApplication.class)
public abstract class RestAssuredTestSupport {

    @Autowired
    DataCleanser dataCleanser;

    @BeforeEach
    void beforeEach() {
        this.dataCleanser.clean();
    }

    @BeforeAll
    public static void beforeAll() {
        RestAssured.port = 8081;
    }
}
