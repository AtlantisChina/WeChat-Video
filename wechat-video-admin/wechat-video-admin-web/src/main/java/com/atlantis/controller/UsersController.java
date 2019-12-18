package com.atlantis.controller;

import com.atlantis.domain.Users;
import com.atlantis.service.UsersService;
import com.atlantis.utils.ResultByPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @GetMapping("/showList")
    public String showList() {
        return "users/usersList";
    }

    @PostMapping("/list")
    @ResponseBody
    public ResultByPage list(Users user, Integer page) {
        ResultByPage result = usersService.queryUsers(user, page == null ? 1 : page, 10);
        return result;
    }
}