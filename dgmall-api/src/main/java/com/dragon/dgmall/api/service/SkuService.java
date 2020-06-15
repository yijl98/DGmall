package com.dragon.dgmall.api.service;

import com.dragon.dgmall.api.bean.PmsSkuInfo;

import java.util.List;

public interface SkuService {
    void saveSkuInfo(PmsSkuInfo pmsSkuInfo);

    PmsSkuInfo getSkuById(String skuId);

    List<PmsSkuInfo> getSpuSaleAttrListCheckBySku(String spuId);

    List<PmsSkuInfo> getAllSku();
}
