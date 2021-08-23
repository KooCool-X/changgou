package com.changgou.goods.controller;

import com.changgou.entity.StatusCode;
import com.changgou.goods.pojo.Goods;
import com.changgou.goods.pojo.Spu;
import com.changgou.goods.service.SpuService;
import com.changgou.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/spu")
public class SpuController {

    @Autowired
    private SpuService spuService;

    /***
     * 新增数据
     * @param goods
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Goods goods){
        spuService.add(goods);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable String id){
        Goods goods=spuService.findGoodsById(id);
        return new Result(true,StatusCode.OK,"查询成功",goods);
    }
    /***
     * 修改数据
     * @param goods
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    public Result update(@RequestBody Goods goods,@PathVariable String id ){
        spuService.update(goods);
        return new Result(true,StatusCode.OK,"更新成功");
    }
    /**
     * 审核
     * @param id
     * @return
     */
    @PutMapping("/autid/{id}")
    public Result audit(@PathVariable String id){
        spuService.audit(id);
        return new Result();
    }

    /**
     * 下架
     * @param id
     * @return
     */
    @PutMapping("/pull/{id}")
    public Result pull(@PathVariable String id){
        spuService.pull(id);
        return new Result();
    }
    /**
     * 上架
     * @param id
     * @return
     */
    @PutMapping("/put/{id}")
    public Result put(@PathVariable String id){
        spuService.put(id);
        return new Result();
    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable String id){
        spuService.delete(id);
        return new Result();
    }


    /**
     * 物理删除
     * @param id
     * @return
     */
    @DeleteMapping("/realdelete/{id}")
    public Result realDelete(@PathVariable String id){
        spuService.realDelete(id);
        return new Result();
    }

    /**
     * 恢复数据
     * @param id
     * @return
     */
    @PutMapping("/restore/{id}")
    public Result restore(@PathVariable String id){
     spuService.restore(id);
     return new Result();
    }

    /***
     * 根据ID查询数据
     * @param id
     * @return
     */
    @GetMapping("/findSpuById/{id}")
    public Result<Spu> findSpuById(@PathVariable String id){
        Spu spu = spuService.findById(id);
        return new Result(true,StatusCode.OK,"查询成功",spu);
    }
}
