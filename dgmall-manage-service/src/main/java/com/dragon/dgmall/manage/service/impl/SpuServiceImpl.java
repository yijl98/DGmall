package com.dragon.dgmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.dragon.dgmall.api.bean.PmsBaseSaleAttr;
import com.dragon.dgmall.api.bean.PmsProductInfo;
import com.dragon.dgmall.api.bean.PmsProductSaleAttr;
import com.dragon.dgmall.api.bean.PmsProductSaleAttrValue;
import com.dragon.dgmall.api.service.SpuService;
import com.dragon.dgmall.manage.mapper.PmsBaseSaleAttrMapper;
import com.dragon.dgmall.manage.mapper.PmsProductInfoMapper;
import com.dragon.dgmall.manage.mapper.PmsProductSaleAttrMapper;
import com.dragon.dgmall.manage.mapper.PmsProductSaleAttrValueMapper;
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
        for (PmsProductSaleAttr pmsProductSaleAttr : pmsProductInfo.getSpuSaleAttrList()) {
            List<PmsProductSaleAttrValue> valueList=pmsProductSaleAttr.getSpuSaleAttrValueList();
            for (PmsProductSaleAttrValue pmsProductSaleAttrValue : valueList) {
                pmsProductSaleAttrValue.setProductId(pmsProductInfo.getId());
                i+=pmsProductSaleAttrValueMapper.insertSelective(pmsProductSaleAttrValue);
            }
            if(i==valueList.size()){
                return 1;
            }

        }
        return 0;
    }
}
