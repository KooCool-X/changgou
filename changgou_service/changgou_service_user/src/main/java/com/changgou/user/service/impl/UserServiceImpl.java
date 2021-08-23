package com.changgou.user.service.impl;

import com.changgou.user.dao.UserMapper;
import com.changgou.user.pojo.User;
import com.changgou.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    /**
     * 查询所有的用户信息
     * @return
     */
    @Override
    public List<User> getAll() {
        return userMapper.selectAll();
    }

    @Override
    public User findUserInfo(String username) {
        return userMapper.selectByPrimaryKey(username);
    }
}
