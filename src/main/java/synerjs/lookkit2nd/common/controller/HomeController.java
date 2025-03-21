package synerjs.lookkit2nd.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String root() {
        return "forward:/index.html";
    }

    // 점이 없는 경로에 한해서만 index.html로 포워딩
    @GetMapping("/{path:[^\\.]+}/**")
    public String forward() {
        return "forward:/index.html";
    }
}