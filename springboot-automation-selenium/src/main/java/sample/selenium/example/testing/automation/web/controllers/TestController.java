package sample.selenium.example.test.automation.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public final class TestController {

    @GetMapping("/test/{id}")
    public String test(@PathVariable("id") String id, Model model) {
        model.addAttribute("id", id);

        return "test";
    }
}
