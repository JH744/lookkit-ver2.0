package synerjs.lookkit2nd.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // 루트("/") 요청도 index.html로 포워딩
    @GetMapping("/")
    public String root() {
        return "forward:/index.html";
    }

    @GetMapping("/{path:[^\\.]*}/**")
    public String forward() {
        return "forward:/index.html";
    }
}