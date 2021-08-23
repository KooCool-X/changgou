package com.changgou.goods.service;

import com.changgou.goods.pojo.Brand;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface BrandService {

    public List<Brand> findAll();

    public Brand findById(Integer id);

    public void add(Brand brand);

    public void update(Brand brand);

    public void delete(Integer id);

    public List<Brand> findList(Map<String,Object>searchMap);

    public Page<Brand> findPage(int page, int size);

    Page<Brand> findPage(Map<String, Object> searchMap, int page, int size);

    public List<Map> findListByCategoryName(String categoryName);

}
