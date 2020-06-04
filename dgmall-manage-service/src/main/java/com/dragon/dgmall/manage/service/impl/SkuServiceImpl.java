package com.dragon.dgmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.dragon.dgmall.api.bean.PmsSkuAttrValue;
import com.dragon.dgmall.api.bean.PmsSkuImage;
import com.dragon.dgmall.api.bean.PmsSkuInfo;
import com.dragon.dgmall.api.bean.PmsSkuSaleAttrValue;
import com.dragon.dgmall.api.service.SkuService;
import com.dragon.dgmall.manage.mapper.PmsSkuAttrValueMapper;
import com.dragon.dgmall.manage.mapper.PmsSkuImageMapper;
import com.dragon.dgmall.manage.mapper.PmsSkuInfoMapper;
import com.dragon.dgmall.manage.mapper.PmsSkuSaleAttrValueMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service
public class SkuServiceImpl implements SkuService {
    @Autowired
    PmsSkuInfoMapper pmsSkuInfoMapper;

    @Autowired
    PmsSkuAttrValueMapper pmsSkuAttrValueMapper;

    @Autowired
    PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;

    @Autowired
    PmsSkuImageMapper pmsSkuImageMapper;

    @Override
    public void saveSkuInfo(PmsSkuInfo pmsSkuInfo) {
        pmsSkuInfoMapper.insertSelective(pmsSkuInfo);
        //插入skuInfo
        String skuId=String.valueOf(pmsSkuInfo.getId());
        pmsSkuInfo.setId(skuId);
        //插入平台属性关联
        List<PmsSkuAttrValue> skuAttrValueList = pmsSkuInfo.getSkuAttrValueList();
        if(skuAttrValueList!=null){
            for (PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList) {
                pmsSkuAttrValue.setSkuId(skuId);
                pmsSkuAttrValueMapper.insertSelective(pmsSkuAttrValue);
            }
        }

        //插入销售属性关联
        List<PmsSkuSaleAttrValue> skuSaleAttrValues = pmsSkuInfo.getSkuSaleAttrValueList();
        if(skuSaleAttrValues!=null){
            for (PmsSkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValues) {
                skuSaleAttrValue.setSkuId(skuId);
                pmsSkuSaleAttrValueMapper.insertSelective(skuSaleAttrValue);
            }
        }

        //插入图片信息
        List<PmsSkuImage> skuImageList = pmsSkuInfo.getSkuImageList();
        if(skuImageList!=null){
            for (PmsSkuImage pmsSkuImage : skuImageList) {
                pmsSkuImage.setSkuId(skuId);
                pmsSkuImageMapper.insertSelective(pmsSkuImage);
            }
        }

    }
}
