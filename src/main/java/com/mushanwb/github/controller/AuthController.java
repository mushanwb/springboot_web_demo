package com.mushanwb.github.controller;

import com.mushanwb.github.entity.Result;
import com.mushanwb.github.entity.User;
import com.mushanwb.github.service.UserService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
            return Result.failure("用户未登录");
        } else {
            User loginUser = userService.getUserByUsername(username);
            return Result.success("登录成功", loginUser);
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
            return Result.failure("用户名不存在");
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        try {
            authenticationManager.authenticate(token);
            // 把用户信息保存在 session 中
            SecurityContextHolder.getContext().setAuthentication(token);

            User loginUser = userService.getUserByUsername(username);
            return Result.success("登录成功", loginUser);
        } catch (BadCredentialsException e) {
            return Result.failure("用户名不正确");
        }
    }

    @PostMapping("/auth/register")
    @ResponseBody
    public Result register(@RequestBody Map<String, String> param) {
        String username = param.get("username");
        String password = param.get("password");

        if (username == null || password == null) {
            return Result.failure("username/password == null");
        }

        if (username.length() <= 1 || username.length() > 15) {
            return Result.failure("invalid username");
        }

        if (password.length() < 6 || password.length() > 16) {
            return Result.failure("invalid password");
        }

        // 将数据库用户名字段更改为唯一索引
        try {
            userService.save(username, password);
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
            return Result.failure("username is use");
        }

        return Result.success("success", null);
    }

    @GetMapping("/auth/logout")
    @ResponseBody
    public Result logout() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.getUserByUsername(username);

        if (user == null) {
            return Result.failure("用户没有登录");
        } else {
            SecurityContextHolder.clearContext();
            return Result.success("注销成功", null);
        }
    }

}
