package com.changgou.system.controller;



import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;

import com.changgou.system.util.JwtUtil;
import com.changgou.system.pojo.Admin;
import com.changgou.system.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @PostMapping
    public Result add(@RequestBody Admin admin){
        if(admin == null){
            return new Result(false,StatusCode.ERROR,"注册失败");
        }
        adminService.add(admin);
        return new Result(true,StatusCode.OK,"注册成功");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        boolean delete = adminService.delete(id);
        if (!delete) {
            throw new RuntimeException();
        }
        return new Result(true,StatusCode.OK, "删除成功");
    }

    @PutMapping("/{id}")
    public Result update(@RequestBody Admin admin, @PathVariable("id") Integer id) {
        admin.setId(id);
        boolean update = adminService.update(admin);
        if (!update) {
            throw new RuntimeException();
        }
        return new Result(true,StatusCode.OK, "修改成功");
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable("id") int id) {
        Admin admin = adminService.findById(id);
        if (admin == null) {
            throw new RuntimeException();
        }
        return new Result(true,StatusCode.OK, "查找成功", admin);
    }

    @GetMapping
    public Result<List<Admin>> findAll() {
        List<Admin> adminList = adminService.findAll();
        return new Result<List<Admin>>(true,StatusCode.OK, "查询成功", adminList);
    }

    /**
     * 登录
     * @param admin
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody Admin admin){
        boolean login = adminService.login(admin);
        if(login){  //如果验证成功
            Map<String,String> info = new HashMap<>();
            info.put("username",admin.getLoginName());
            String token = JwtUtil.createJWT(UUID.randomUUID().toString(), admin.getLoginName(), null);
            info.put("token",token);
            return new Result(true, StatusCode.OK,"登录成功",info);
        }else{
            return new Result(false,StatusCode.LOGINERROR,"用户名或密码错误");
        }
    }

}
