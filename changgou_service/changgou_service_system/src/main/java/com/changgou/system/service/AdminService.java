package com.changgou.system.service;

import com.changgou.system.pojo.Admin;

import java.util.List;

public interface AdminService {


    void add(Admin admin);

    boolean delete(int id);

    boolean update(Admin admin);

    boolean login(Admin admin);

    Admin findById(int id);

    List<Admin> findAll();
}
