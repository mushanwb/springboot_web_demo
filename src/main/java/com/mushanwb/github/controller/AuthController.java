package com.mushanwb.github.controller;

import com.mushanwb.github.entity.User;
import com.mushanwb.github.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.Map;

@Controller
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserService userService;

    @Inject
    public AuthController(AuthenticationManager authenticationManager,
                          UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @GetMapping("/auth")
    @ResponseBody
    public Result auth() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if (username.contains("anonymous")) {
            return new Result("ok", "用户未登录", false);
        } else {
            User loginUser = userService.getUserByUsername(username);
            return new Result("ok", "用户登录成功", true, loginUser);
        }
    }

    // 接口返回格式
    private static class Result {
        String status;
        String msg;
        boolean isLogin;
        Object data;

        public Result(String status, String msg, boolean isLogin) {
            this(status, msg, isLogin, null);
        }

        public Result(String status, String msg, boolean isLogin, Object data) {
            this.status = status;
            this.msg = msg;
            this.isLogin = isLogin;
            this.data = data;
        }

        public String getStatus() {
            return status;
        }

        public String getMsg() {
            return msg;
        }

        public boolean isLogin() {
            return isLogin;
        }

        public Object getData() {
            return data;
        }
    }

    @PostMapping("/auth/login")
    @ResponseBody
    public Result login(@RequestBody Map<String, String> param) {
        String username = param.get("username");
        String password = param.get("password");

        UserDetails userDetails;
        try {
             userDetails = userService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            return new Result("fail", "用户名不存在", false);
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        try {
            authenticationManager.authenticate(token);
            // 把用户信息保存在 session 中
            SecurityContextHolder.getContext().setAuthentication(token);

            User loginUser = userService.getUserByUsername(username);
            return new Result("ok", "登录成功", true, loginUser);
        } catch (BadCredentialsException e) {
            return new Result("ok", "密码不正确", false);
        }
    }

    @PostMapping("/auth/register")
    @ResponseBody
    public Result register(@RequestBody Map<String, String> param) {
        String username = param.get("username");
        String password = param.get("password");

        if (username == null || password == null) {
            return new Result("fail", "username/password == null", false);
        }

        if (username.length() <= 1 || username.length() > 15) {
            return new Result("fail", "invalid username", false);
        }

        if (password.length() < 6 || password.length() > 16) {
            return new Result("fail", "invalid password", false);
        }

        User user = userService.getUserByUsername(username);

        if (user != null) {
            return new Result("fail", "username is use", false);
        }

        userService.save(username, password);

        return new Result("ok", "success", false);
    }

}
