package com.bioinf.demo.controllers;

import com.bioinf.demo.models.Data;
import com.bioinf.demo.models.ORF;
import com.bioinf.demo.newick.Analyzer;
import com.bioinf.demo.services.DnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class MainController {

    @Autowired
    private DnaService mainService;

    @Autowired
    private Analyzer analyzer;

    @GetMapping("/")
    public String getMainPage(){
        return "index";
    }

    @PostMapping("/manual")
    @ResponseBody
    private ORF manual(@RequestParam("dna") String dna){
        return mainService.pretty(dna);
    }

    @PostMapping("/generate")
    @ResponseBody
    private ORF generate(@RequestParam("gcPercentCount") Integer gcpc, @RequestParam("length") Integer length){
        return mainService.pretty(length, gcpc);
    }

    @GetMapping("/task2")
    public void task2(HttpServletResponse response) throws IOException {
        mainService.task2(response.getWriter());
    }

    @GetMapping("/newick")
    public String newick(){
        return "newick";
    }

    @PostMapping("/newick")
    @ResponseBody
    public Data newick(@RequestParam("string") String string){
        return new Data(analyzer.processLine(string));
    }

}
