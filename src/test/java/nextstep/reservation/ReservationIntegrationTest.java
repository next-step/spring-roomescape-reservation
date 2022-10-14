package nextstep.reservation;

import nextstep.reservation.web.ReservationController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
class ReservationIntegrationTest {

    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ReservationController.class)
            .build();
    }

    @Test
    void makeReservation() {

    }

    @Test
    void listReservations() {

    }

    @Test
    void cancelReservation() {

    }
}
