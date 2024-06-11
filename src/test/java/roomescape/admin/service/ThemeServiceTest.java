package roomescape.admin.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ThemeServiceTest {
    @Nested
    @DisplayName("테마는")
    class Describe_Theme{
        @Nested
        @DisplayName("예약된 목록에서 사용중이라면")
        class Context_with_in_use_reservations{
            @Test
            @DisplayName("삭제할 수 없고, 에러를 리턴합니다.")
            void it_return_error_and_not_delete_theme(){

            }
        }

        @Nested
        @DisplayName("예약된 목록에서 사용하지 않는다면")
        class Context_not_use_theme_you_are_deleting_for_reservation{
            @Test
            @DisplayName("삭제하고, void를 리턴합니다.")
            void it_return_void_and_not_delete_theme(){

            }
        }
    }
}
