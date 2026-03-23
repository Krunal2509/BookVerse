package com.service.bookverse.feature.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Home page";
    }

    @GetMapping("/admin/home")
    public String adminhome() {
        return "admin Home page";
    }

    @GetMapping("/buyer/home")
    public String buyerhome() {
        return "buyer Home page";
    }

    @GetMapping("/seller/home")
    public String sellerhome() {
        return "seller Home page";
    }


}
