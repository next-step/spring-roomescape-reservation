package roomescape.support;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public abstract class RestAssuredTestSupport {

    @BeforeAll
    public static void beforeAll() {
        RestAssured.port = 8081;
    }

}
