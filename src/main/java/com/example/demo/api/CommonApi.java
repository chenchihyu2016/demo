package com.example.demo.api;

import java.util.Map;

import com.example.demo.service.ICommonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class CommonApi {
    @Autowired
    ICommonService commonService;

    @GetMapping(value = "/test")
    public Map<String, Object> test() {
        return commonService.readLetters();
    }

}
