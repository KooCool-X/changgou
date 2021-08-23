package com.changgou.goods.service;

import com.changgou.goods.pojo.Brand;

import java.util.List;
import java.util.Map;

public interface SpecService {

    public List<Map> findListByCategoryName(String categoryName);
}
