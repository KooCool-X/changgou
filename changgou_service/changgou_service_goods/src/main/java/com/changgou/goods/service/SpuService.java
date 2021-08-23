package com.changgou.goods.service;

import com.changgou.goods.pojo.Goods;
import com.changgou.goods.pojo.Spu;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface SpuService {
    /***
     * 新增
     * @param goods
     */
    void add(Goods goods);

    /**
     * 根据ID查询商品
     */
    public Goods findGoodsById(String id);

    /**
     * 根据ID查询Spu
     * @param id
     * @return
     */
    Spu findById(String id);

    /***
     * 修改数据
     * @param
     */
    void update(Goods goods);

    /**
     * 审核
     * @param id
     */
    public  void audit(String id);

    /**
     * 下架商品
     * @param id
     */
    public void pull(String id);
    /**
     * 上架商品
     * @param id
     */
    public void put(String id);

    /**
     * 逻辑删除
     * @param id
     */
    public void delete(String id);

    /**
     * 物理删除
     * @param id
     */
    public void realDelete(String id);

    /**
     * 恢复数据
     * @param id
     */
    public void restore(String id);
}
