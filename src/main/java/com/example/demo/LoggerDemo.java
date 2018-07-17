package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoggerDemo {
    public static final Logger logger = LoggerFactory.getLogger(LoggerDemo.class);
    @RequestMapping(path = "/logger", method = RequestMethod.GET)
    public String getLogger() {
        logger.trace("this is trace");
        logger.warn("this is warn");
        logger.error("this is error");
        logger.debug("this is debug");
        return "this is logger test";

    }

}
