package uz.dev.hmsproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by: asrorbek
 * DateTime: 6/29/25 16:05
 **/

@Controller
public class HomeController {

    @GetMapping("/login")
    public String loginPage() {

        return "login";

    }
}
