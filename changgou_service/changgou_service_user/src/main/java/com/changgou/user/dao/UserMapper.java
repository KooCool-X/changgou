package com.changgou.user.dao;

import com.changgou.user.pojo.User;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
@Repository
public interface UserMapper extends Mapper<User> {
}
