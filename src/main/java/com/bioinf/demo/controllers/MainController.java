package com.bioinf.demo.controllers;

import com.bioinf.demo.models.Answer;
import com.bioinf.demo.services.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @Autowired
    private MainService mainService;

    @GetMapping("/")
    public String getMainPage(){
        return "index";
    }

    @PostMapping("/manual")
    @ResponseBody
    private Answer manual(@RequestParam("dna") String dna){
        return mainService.manual(dna);
    }

    @PostMapping("/generate")
    @ResponseBody
    private Answer generate(@RequestParam("gcPercentCount") Integer gcpc, @RequestParam("length") Integer length){
        return mainService.generate(length, gcpc);
    }

}
