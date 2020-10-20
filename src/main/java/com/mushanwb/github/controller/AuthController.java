package com.mushanwb.github.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuthController {

    @GetMapping("/auth")
    @ResponseBody
    public Object auth(ModelMap map) {
        return new Result();
    }

    private static class Result {
        public String getStatus() {
            return "ok";
        }

        public boolean getIsLogin() {
            return false;
        }

    }

}
