package com.dragon.dgmall.search.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
public class SearchController {

    @RequestMapping("index")
    public String index(){


        return "index";
    }
}
