package roomescape.theme;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.entities.Theme;
import roomescape.theme.data.ThemeAddRequestDto;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/themes")
public class ThemeController {

    final private ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    public List<Theme> searchThemes(){
        return new ArrayList<>();
    }

    public void addTheme(@RequestBody ThemeAddRequestDto themeAddRequestDto){
        themeService.addTheme(themeAddRequestDto);
    }

    public void deleteTheme(@PathVariable("id") Long id){
        themeService.deleteTheme(id);
    }

}
