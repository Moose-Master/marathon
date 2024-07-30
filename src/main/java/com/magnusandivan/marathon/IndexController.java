package com.magnusandivan.marathon;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
    // The main page
    @GetMapping("/greeting")
    public String index(Model model) {
        //model.addAttribute("message", "Successfully inserted message into html");
        return "index";
    }
}
