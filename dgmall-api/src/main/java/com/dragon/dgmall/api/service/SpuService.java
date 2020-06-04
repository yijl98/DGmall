package com.dragon.dgmall.api.service;

import com.dragon.dgmall.api.bean.*;

import java.util.List;

public interface SpuService {
    List<PmsProductInfo> spuList(String catalog3Id);

    List<PmsBaseSaleAttr> baseSaleAttrList();

    String saveSpuInfo(PmsProductInfo pmsProductInfo);

    int saveProductSaleAttr(PmsProductInfo pmsProductInfo);

    int saveProductSaleAttrValue(PmsProductInfo pmsProductInfo);

    int saveProductImage(PmsProductInfo pmsProductInfo);

    List<PmsProductSaleAttr> getSpuSaleAttrList(String spuId);

    List<PmsProductSaleAttrValue> getSpuSaleAttrValueList(String spuId,String attrSaleId);

    List<PmsProductImage> getSpuImageList(String spuId);
}
