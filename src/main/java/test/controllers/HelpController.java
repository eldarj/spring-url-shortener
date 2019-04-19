package test.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelpController {

    @GetMapping("/help")
    public String helpPage() {
        return "help";
    }
}
