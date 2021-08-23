package com.changgou.goods.service.impl;

import com.alibaba.fastjson.JSON;

import com.changgou.goods.dao.*;
import com.changgou.goods.pojo.*;
import com.changgou.goods.service.SpuService;
import com.changgou.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SpuServiceImpl implements SpuService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private CategoryBrandMapper categoryBrandMapper;


    @Transactional
    @Override
    public void add(Goods goods) {
        Spu spu = goods.getSpu();
        long spuId = idWorker.nextId();
        spu.setId(String.valueOf(spuId));
        spu.setIsDelete("0");
        spu.setIsMarketable("0");
        spu.setStatus("0");
        spuMapper.insertSelective(spu);

        //保存sku集合数据到数据库
        saveSkuList(goods);
    }

    /**
     * 根据ID查询商品
     * @param id
     * @return
     */
    @Override
    public Goods findGoodsById(String id) {
        //查询spu
        Spu spu=spuMapper.selectByPrimaryKey(id);

        //查询Sku列表
        Example example=new Example(Sku.class);
        Example.Criteria criteria= example.createCriteria();
        criteria.andEqualTo("spuId",id);
        List<Sku> skuList=skuMapper.selectByExample(example);

        //封装,返回
        Goods goods=new Goods();
        goods.setSpu(spu);
        goods.setSkuList(skuList);
        return goods;
    }

    @Override
    public Spu findById(String id) {
        return spuMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新商品
     * @param goods
     */
    @Override
    public void update(Goods goods) {
        //取出spu部分
        Spu spu= goods.getSpu();
        spuMapper.updateByPrimaryKey(spu);
        //删除原SKU列表
        Example example=new Example(Sku.class);
        Example.Criteria criteria= example.createCriteria();
        criteria.andEqualTo("spuId",spu.getId());
        skuMapper.deleteByExample(example);
        //保存sku列表
        saveSkuList(goods);
    }

    /**
     * 审核商品
     * @param id
     */
    @Transactional
    @Override
    public void audit(String id) {
        //查询spu对象
        Spu spu=spuMapper.selectByPrimaryKey(id);
        if(spu==null){
            throw new RuntimeException("当前商品不存在");
        }
        //判断当前spu是否处于删除状态
        if("1".equals(spu.getIsDelete())){
            throw new RuntimeException("当前商品处于删除状态");
        }
        //不处于删除状态,修改审核状态为1,上下架状态为1
        spu.setStatus("1");
        spu.setIsMarketable("1");
        //执行修改操作
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /**
     * 上架商品
     * @param id
     */
    @Override
    public void put(String id) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        if(spu==null){
            throw new RuntimeException("当前商品不存在");
        }
        if(!spu.getStatus().equals("1")){
            throw new RuntimeException("未通过审核的商品不能上架！");
        }
        //判断当前商品是否处于删除状态
        if ("1".equals(spu.getIsDelete())){
            throw new RuntimeException("当前商品处于删除状态");
        }
        spu.setIsMarketable("1");//上架状态
        spuMapper.updateByPrimaryKeySelective(spu);
    }


    /**
     * 下架商品
     * @param id
     */
    @Transactional
    @Override
    public void pull(String id) {
        //查询spu
        Spu spu=spuMapper.selectByPrimaryKey(id);
        if(spu==null) {
            throw new RuntimeException("当前商品不存在");
        }
        //判断当前商品是否处于删除状态
        if ("1".equals(spu.getIsDelete())){
            throw new RuntimeException("当前商品处于删除状态");
        }
        //商品处于未删除状态的话,则修改上下架状态为已下架(0)
        spu.setIsMarketable("0");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /**
     * 逻辑删除商品
     * @param id
     */
    @Override
    public void delete(String id) {
        Spu spu=spuMapper.selectByPrimaryKey(id);
        //检查是否下架的商品
        if(!spu.getIsMarketable().equals("0")){
            throw new RuntimeException("必须先下架再删除");
        }
        spu.setIsDelete("1");//删除
        spu.setStatus("0");//未审核
        spuMapper.updateByPrimaryKeySelective(spu);
    }
    /**
     * 物理删除商品
     * @param id
     */
    @Override
    public void realDelete(String id) {
        Spu spu=spuMapper.selectByPrimaryKey(id);
        //检查是否删除的商品
        if(!spu.getIsDelete().equals("1")){
            throw new RuntimeException("此商品未删除！");
        }
        spuMapper.deleteByPrimaryKey(id);
    }

    /**
     * 恢复数据
     * @param id
     */
    @Override
    public void restore(String id) {
        Spu spu=spuMapper.selectByPrimaryKey(id);
        //检查是否删除的商品
        if(!spu.getIsDelete().equals("1")){
            throw new RuntimeException("此商品未删除！");
        }
        spu.setIsDelete("0");//未删除
        spu.setStatus("0");//未审核
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /**
     * 保存sku列表
     * @param goods
     */
    private void saveSkuList(Goods goods){
        //获取spu对象
        Spu spu = goods.getSpu();
        //当前日期
        Date date = new Date();
        //获取品牌对象
        Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());
        //获取分类对象
        Category category = categoryMapper.selectByPrimaryKey(spu.getCategory3Id());
        /**
         * 添加分类与品牌之间的关联
         */
        CategoryBrand categoryBrand=new CategoryBrand();
        categoryBrand.setBrandId(spu.getBrandId());
        categoryBrand.setCategoryId(spu.getCategory3Id());
        int count=categoryBrandMapper.selectCount(categoryBrand);
        //判断是否有这个品牌和分类的关系数据
        if(count==0){
            //如果没有关系数据则添加品牌和分类的关系数据
            categoryBrandMapper.insert(categoryBrand);
        }

        //获取sku集合对象
        List<Sku> skuList = goods.getSkuList();
        if (skuList != null) {
            for (Sku sku : skuList) {
                //设置sku主键ID
                sku.setId(String.valueOf(idWorker.nextId()));
                //设置sku规格
                if (sku.getSpec() == null || "".equals(sku.getSpec())) {
                    sku.setSpec("{}");
                }
                //设置sku名称(商品名称 + 规格)
                String name = spu.getName();
                //将规格json字符串转换成Map
                Map<String, String> specMap = JSON.parseObject(sku.getSpec(), Map.class);
                if (specMap != null && specMap.size() > 0) {
                    for(String value : specMap.values()){
                        name += " "+ value;
                    }
                }

                sku.setName(name);//名称
                sku.setSpuId(spu.getId());//设置spu的ID
                sku.setCreateTime(date);//创建日期
                sku.setUpdateTime(date);//修改日期
                sku.setCategoryId(category.getId());//商品分类ID
                sku.setCategoryName(category.getName());//商品分类名称
                sku.setBrandName(brand.getName());//品牌名称
                skuMapper.insertSelective(sku);//插入sku表数据
            }
        }
    }
}
