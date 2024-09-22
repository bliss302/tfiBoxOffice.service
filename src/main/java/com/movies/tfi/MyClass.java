package com.movies.tfi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyClass {

    @GetMapping("abc")
    public String hello(){
        return "Hello World!";
    }
}
