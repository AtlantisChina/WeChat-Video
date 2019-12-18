package com.atlantis.controller;

import com.atlantis.domain.Admin;
import com.atlantis.utils.ResultByJSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class LoginExitController {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "123";

    @GetMapping("center")
    public String center() {
        return "center";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("login")
    @ResponseBody
    public ResultByJSON adminLogin(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return ResultByJSON.errorMsg("用户名和密码不能为空");
        } else if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            String token = UUID.randomUUID().toString();
            Admin admin = new Admin(token, username, password);
            request.getSession().setAttribute("sessionAdmin", admin);
            return ResultByJSON.ok();
        }
        return ResultByJSON.errorMsg("登陆失败，请重试...");
    }

    @GetMapping("/exit")
    public String exit(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("sessionAdmin");
        return "login";
    }
}