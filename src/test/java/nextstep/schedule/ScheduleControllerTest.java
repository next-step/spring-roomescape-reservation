package nextstep.schedule;

import nextstep.SpringControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ScheduleControllerTest extends SpringControllerTest {

    @Autowired
    private ScheduleJdbcRepository scheduleJdbcRepository;

    @Override
    @BeforeEach
    protected void setUp() {
        super.setUp();
        scheduleJdbcRepository.clear();
    }

    @Test
    void test1() {
        
    }
}
