package com.dragon.dgmall.item.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.dragon.dgmall.api.bean.PmsProductSaleAttr;
import com.dragon.dgmall.api.bean.PmsSkuInfo;
import com.dragon.dgmall.api.bean.PmsSkuSaleAttrValue;
import com.dragon.dgmall.api.service.SkuService;
import com.dragon.dgmall.api.service.SpuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@CrossOrigin
public class ItemController {

    @Reference
    SkuService SkuService;
    @Reference
    SpuService SpuService;

    @RequestMapping("index")
    //@ResponseBody
    public String index(ModelMap ModelMap) {

        ModelMap.put("hello", "fuck java");

        return "index";

    }

    @RequestMapping("{skuId}.html")
    public String item(@PathVariable String skuId, ModelMap ModelMap) {

        PmsSkuInfo PmsSkuInfo = SkuService.getSkuById(skuId);
        String spuId = PmsSkuInfo.getProductId();

        //sku对象
        ModelMap.put("skuInfo", PmsSkuInfo);

        //销售属性列表
        List<PmsProductSaleAttr> pmsProductSaleAttrList = SpuService.spuSaleAttrListCheckBySku(spuId, skuId);
        ModelMap.put("spuSaleAttrListCheckBySku", pmsProductSaleAttrList);

        //查询当前sku的spu的其他sku的集合的hash表
        HashMap<String, String> skuSaleAttrHashMap = new HashMap<>();

        List<PmsSkuInfo> SkuInfoList = SkuService.getSpuSaleAttrListCheckBySku(spuId);
        for (PmsSkuInfo pmsSkuInfo : SkuInfoList) {

            String value = pmsSkuInfo.getId();
            String key = "";

            List<PmsSkuSaleAttrValue> skuSaleAttrValueList = pmsSkuInfo.getSkuSaleAttrValueList();
            for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : skuSaleAttrValueList) {
                key += pmsSkuSaleAttrValue.getSaleAttrValueId() + "|";
            }

            skuSaleAttrHashMap.put(key,value);
        }

        //将sku的销售属性hash表放到页面
        String skuSaleAttrHashMapJson = JSON.toJSONString(skuSaleAttrHashMap);
        ModelMap.put("skuSaleAttrHashMapJson",skuSaleAttrHashMapJson);

        return "item";

    }


}
