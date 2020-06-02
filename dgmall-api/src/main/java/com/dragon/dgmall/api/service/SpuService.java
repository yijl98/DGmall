package com.dragon.dgmall.api.service;

import com.dragon.dgmall.api.bean.PmsBaseSaleAttr;
import com.dragon.dgmall.api.bean.PmsProductInfo;

import java.util.List;

public interface SpuService {
    List<PmsProductInfo> spuList(String catalog3Id);

    List<PmsBaseSaleAttr> baseSaleAttrList();

    String saveSpuInfo(PmsProductInfo pmsProductInfo);

    int saveProductSaleAttr(PmsProductInfo pmsProductInfo);

    int saveProductSaleAttrValue(PmsProductInfo pmsProductInfo);
}
