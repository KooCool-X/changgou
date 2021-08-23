package com.changgou.controller;

import com.changgou.entity.Page;
import com.changgou.search.pojo.SkuInfo;
import com.changgou.service.ESManagerService;
import com.changgou.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/sku_search")
public class SearchController {
    @Autowired
    private ESManagerService esManagerService;
    @Autowired
    private SearchService searchService;
    //对搜索入参带有特殊符号进行处理
    public void handlerSearchMap(Map<String,String> searchMap){
        if(null != searchMap){
            Set<Map.Entry<String,String>> entries = searchMap.entrySet();
            for (Map.Entry<String,String> entry:entries) {
                if(entry.getKey().startsWith("spec_")){
                    searchMap.put(entry.getKey(),entry.getValue().replace("+","%2B"));
                }
            }
        }
    }
    @GetMapping("/list")
    public String list(@RequestParam Map<String,String> searchMap,Model model)throws Exception{
        if(searchMap.size()<=0){
            return "search";
        }
        //处理特殊字符
        handlerSearchMap(searchMap);
        Map resultMap = searchService.search(searchMap);
        model.addAttribute("searchMap",searchMap);
        model.addAttribute("resultMap",resultMap);
        //封装分页数据并返回
        //第一个参数:总记录数
        //第二个参数:当前页
        //第三个参数:每一页显示多少条
        Page<SkuInfo> page = new Page<>(Long.valueOf(String.valueOf(resultMap.get("total"))),
                Integer.valueOf(String.valueOf(resultMap.get("pageNum"))),
                Page.pageSize
        );
        model.addAttribute("page",page);
        //条件搜索实现
        //拼装url
        StringBuilder url = new StringBuilder("/sku_search/list");
        if(searchMap != null && searchMap.size()>0){
            //是由查询条件
            url.append("?");
            for(String paramKey : searchMap.keySet()){
                if(!"sortRule".equals(paramKey)&& !"sortField".equals(paramKey)&&!"pageNum".equals(paramKey)){
                    url.append(paramKey).append("=").append(searchMap.get(paramKey)).append("&");
                }
            }
            String urlString = url.toString();
            //除去路径上的最后一个&
            urlString = urlString.substring(0,urlString.length()-1);
            model.addAttribute("url",urlString);
        }else{
            model.addAttribute("url",url);
        }
        return "search";
    }
    @GetMapping
    @ResponseBody
    public Map search(@RequestParam Map<String,String> searchMap)throws Exception{
        //特殊符号处理
        handlerSearchMap(searchMap);
        Map resultMap=searchService.search(searchMap);
        return resultMap;
    }
}
