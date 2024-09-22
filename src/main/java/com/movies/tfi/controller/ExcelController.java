package com.movies.tfi.controller;

import com.movies.tfi.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tfi")
public class ExcelController {

    @Autowired
    ExcelService excelService;

    @PostMapping("/uploadActors")
    public void uploadActors(@RequestParam("filePath") String filePath){
        excelService.saveActorsFromExcel(filePath);
    }
}
