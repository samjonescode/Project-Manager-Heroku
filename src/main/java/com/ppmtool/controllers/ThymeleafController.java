package com.ppmtool.controllers;

import com.ppmtool.domain.Project;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ThymeleafController {

    @GetMapping("")
    public String serveHtml(){
        return "index";
    }

    @GetMapping("/projectForm")
    public String serveHtml2(){
        return "projectform";
    }

}
