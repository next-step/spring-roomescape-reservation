package roomescape.admin.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ThemeControllerTest {

    @Nested
    @DisplayName("테마는")
    class Describe_Theme{
        @Test
        @DisplayName("저장할 수 있습니다.")
        void it_return_theme_object(){

        }

        @Test
        @DisplayName("목록을 조회할 수 있습니다.")
        void it_return_list_theme(){

        }

        @Test
        @DisplayName("취소할 수 있습니다.")
        void it_return_void(){

        }

        @Nested
        @DisplayName("예약에서 사용되었다면")
        class Context_use_theme_you_are_deleting_for_reservation{
            @Test
            @DisplayName("취소할 수 없고, 에러를 리턴합니다.")
            void it_not_delete_theme_and_return_error(){

            }
        }

        @Nested
        @DisplayName("예약에서 사용되지_않았다면")
        class Context_not_use_theme_you_are_deleting_for_reservation{
            @Test
            @DisplayName("취소하고, 에러를 리턴합니다.")
            void it_not_delete_theme_and_return_error(){

            }
        }
    }

}