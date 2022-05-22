package sample.selenium.example.test.automation.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public final class HelloController {

    @GetMapping
    public String hello() {
        return "hello";
    }
}
