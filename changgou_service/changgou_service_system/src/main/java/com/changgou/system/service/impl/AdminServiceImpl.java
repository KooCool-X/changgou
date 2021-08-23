package com.changgou.system.service.impl;

import com.changgou.system.dao.AdminMapper;
import com.changgou.system.pojo.Admin;
import com.changgou.system.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Override
    public void add(Admin admin) {
        admin.setStatus("1");
        admin.setPassword(BCrypt.hashpw(admin.getPassword(),BCrypt.gensalt()));
        adminMapper.insert(admin);
    }

    @Override
    public boolean delete(int id) {
        return adminMapper.deleteByPrimaryKey(id) > 0 ? true : false;
    }

    @Override
    public boolean update(Admin admin) {
        return adminMapper.updateByPrimaryKeySelective(admin) > 0 ? true : false;
    }

    @Override
    public Admin findById(int id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Admin> findAll() {
        return adminMapper.selectAll();
    }

    @Override
    public boolean login(Admin admin) {
        //设置当前账户是可用
        Admin admin1 = new Admin();
        admin1.setStatus("1");
        admin1.setLoginName(admin.getLoginName());
        admin1 = adminMapper.selectOne(admin1);
        return BCrypt.checkpw(admin.getPassword(),admin1.getPassword());
    }



    /*public static void main(String[] args) {
        for(int i=0;i<4;i++) {
            String gensalt = BCrypt.gensalt();
            System.out.println("gensalt:"+gensalt);
            String hashpw = BCrypt.hashpw("123456", gensalt);
            System.out.println("hashpw:"+hashpw);
            System.out.println("长度:"+hashpw.length());

            boolean checkpw = BCrypt.checkpw("123456", hashpw);
            System.out.println(checkpw?"密码校验成功!":"密码校验失败!");

        }
    }*/
}
