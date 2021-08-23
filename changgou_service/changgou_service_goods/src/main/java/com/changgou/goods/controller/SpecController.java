package com.changgou.goods.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.goods.pojo.Brand;
import com.changgou.goods.service.BrandService;
import com.changgou.goods.service.SpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

//@RestController
//@RequestMapping("/brand")
public class SpecController {
//    @Autowired
//    private SpecService specService;
//
////    @GetMapping("/categroy/{category}")
////    public Result findListByCategoryName(@PathVariable String category) {
////        List<Map> specList = specService.findListByCategoryName(category);
////        return new Result(true, StatusCode.OK, "", specList);
////    }
}
