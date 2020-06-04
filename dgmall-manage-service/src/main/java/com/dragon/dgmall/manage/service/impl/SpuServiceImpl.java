package com.dragon.dgmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.dragon.dgmall.api.bean.*;
import com.dragon.dgmall.api.service.SpuService;
import com.dragon.dgmall.manage.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    PmsProductInfoMapper pmsProductInfoMapper;
    @Autowired
    PmsBaseSaleAttrMapper pmsBaseSaleAttrMapper;
    @Autowired
    PmsProductSaleAttrMapper pmsProductSaleAttrMapper;
    @Autowired
    PmsProductSaleAttrValueMapper pmsProductSaleAttrValueMapper;
    @Autowired
    PmsProductImageMapper pmsProductImageMapper;

    @Override
    public List<PmsProductInfo> spuList(String catalog3Id) {
        return pmsProductInfoMapper.selectAll();
    }

    @Override
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        return pmsBaseSaleAttrMapper.selectAll();
    }

    @Override
    public String saveSpuInfo(PmsProductInfo pmsProductInfo) {
        String infoId=null;
        pmsProductInfoMapper.insertSelective(pmsProductInfo);
        infoId=pmsProductInfo.getId();
        return infoId;
    }

    @Override
    public int saveProductSaleAttr(PmsProductInfo pmsProductInfo) {

        int i=0;
        for (PmsProductSaleAttr pmsProductSaleAttr : pmsProductInfo.getSpuSaleAttrList()) {
            pmsProductSaleAttr.setProductId(pmsProductInfo.getId());
            i+=pmsProductSaleAttrMapper.insertSelective(pmsProductSaleAttr);

        }
        return i;
    }

    @Override
    public int saveProductSaleAttrValue(PmsProductInfo pmsProductInfo) {
        int i=0;
        int j=0;
        List<PmsProductSaleAttr> attrList=pmsProductInfo.getSpuSaleAttrList();
        for (PmsProductSaleAttr pmsProductSaleAttr : attrList) {
            List<PmsProductSaleAttrValue> valueList=pmsProductSaleAttr.getSpuSaleAttrValueList();
            for (PmsProductSaleAttrValue pmsProductSaleAttrValue : valueList) {
                pmsProductSaleAttrValue.setProductId(pmsProductInfo.getId());
                i+=pmsProductSaleAttrValueMapper.insertSelective(pmsProductSaleAttrValue);
            }
            if(i==valueList.size()){
                j++;
            }
        }
        if(j==attrList.size()){
            return 1;
        }

        return 0;
    }

    @Override
    public int saveProductImage(PmsProductInfo pmsProductInfo) {
        int i=0;
        for (PmsProductImage pmsProductImage : pmsProductInfo.getSpuImageList()) {
            pmsProductImage.setProductId(pmsProductInfo.getId());
            i+=pmsProductImageMapper.insertSelective(pmsProductImage);

        }
        return i;
    }

    @Override
    public List<PmsProductSaleAttr> getSpuSaleAttrList(String spuId) {

        PmsProductSaleAttr PmsProductSaleAttr=new PmsProductSaleAttr();
        PmsProductSaleAttr.setProductId(spuId);

        return pmsProductSaleAttrMapper.select(PmsProductSaleAttr);
    }

    @Override
    public List<PmsProductSaleAttrValue> getSpuSaleAttrValueList(String spuId,String attrSaleId) {

        PmsProductSaleAttrValue PmsProductSaleAttrValue=new PmsProductSaleAttrValue();
        PmsProductSaleAttrValue.setProductId(spuId);
        PmsProductSaleAttrValue.setSaleAttrId(attrSaleId);

        return pmsProductSaleAttrValueMapper.select(PmsProductSaleAttrValue);
    }

    @Override
    public List<PmsProductImage> getSpuImageList(String spuId) {
        PmsProductImage pmsProductImage=new PmsProductImage();
        pmsProductImage.setProductId(spuId);
        return pmsProductImageMapper.select(pmsProductImage);
    }
}
