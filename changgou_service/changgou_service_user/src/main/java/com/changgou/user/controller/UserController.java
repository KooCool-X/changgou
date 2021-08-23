package com.changgou.user.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.user.pojo.User;
import com.changgou.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/getAll")
    public Result getAll(){
        List<User> userlist = userService.getAll();
        return new Result(StatusCode.OK,"查询成功",true,userlist);
    }
    @GetMapping
    public Result getUsers(){
        List<User> userlist = userService.getAll();
        return new Result(StatusCode.OK,"查询成功",true,userlist);
    }
    @GetMapping("/load/{username}")
    public User findUserInfo(@PathVariable("username") String username){
        User user = userService.findUserInfo(username);
        return user;
    }
}
