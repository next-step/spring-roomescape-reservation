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

        @Nested
        @DisplayName("데이터 유효성 검증을 진행할 때")
        class Context_with_validate_data{

            @Test
            @DisplayName("name에 문자 이외의 값이 입력되면, 에러를 리턴합니다.")
            void it_return_error_for_non_string_in_name(){

            }

            @Test
            @DisplayName("name에 null, (공백) 값이 입력되면, 에러를 리턴합니다.")
            void it_return_error_for_null_or_empty_name(){

            }



        }

        @Test
        @DisplayName("입력한 뒤, 저장한 Dto를 리턴합니다.")
        void it_return_theme_dto_after_saving_theme(){

        }

        @Test
        @DisplayName("목록 List를 리턴합니다.")
        void it_return_list_theme(){

        }

        @Test
        @DisplayName("삭제하고, void를 리턴합니다.")
        void it_return_void_and_delete_theme(){

        }

    }

}