package com.dragon.dgmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.dragon.dgmall.api.bean.PmsBaseAttrInfo;
import com.dragon.dgmall.api.bean.PmsBaseAttrValue;
import com.dragon.dgmall.api.bean.PmsBaseSaleAttr;
import com.dragon.dgmall.api.service.AttrService;
import com.dragon.dgmall.manage.mapper.PmsBaseAttrInfoMapper;
import com.dragon.dgmall.manage.mapper.PmsBaseAttrValueMapper;
import com.dragon.dgmall.manage.mapper.PmsBaseSaleAttrMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Service
public class AttrServiceImpl implements AttrService {

    @Autowired
    PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper;
    @Autowired
    PmsBaseAttrValueMapper pmsBaseAttrValueMapper;
    @Autowired
    PmsBaseSaleAttrMapper pmsBaseSaleAttrMapper;

    @Override
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id) {

        PmsBaseAttrInfo pmsBaseAttrInfo = new PmsBaseAttrInfo();
        pmsBaseAttrInfo.setCatalog3Id(catalog3Id);

        return pmsBaseAttrInfoMapper.select(pmsBaseAttrInfo);
    }

    @Override
    public List<PmsBaseAttrValue> getAttrValueList(String attrId) {

        PmsBaseAttrValue pmsBaseAttrValue = new PmsBaseAttrValue();
        pmsBaseAttrValue.setAttrId(attrId);

        return pmsBaseAttrValueMapper.select(pmsBaseAttrValue);
    }

    @Override
    public int insertAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo) {
        int id = pmsBaseAttrInfoMapper.insertSelective(pmsBaseAttrInfo);
        if (id >= 1) {
            return Integer.valueOf(pmsBaseAttrInfo.getId());
        }
        return id;
    }

    @Override
    public int insertValueList(PmsBaseAttrInfo pmsBaseAttrInfo) {

        int i=0;

        for(PmsBaseAttrValue PmsBaseAttrValue: pmsBaseAttrInfo.getAttrValueList()){
            PmsBaseAttrValue.setAttrId(pmsBaseAttrInfo.getId());
            i+=pmsBaseAttrValueMapper.insertSelective(PmsBaseAttrValue);

        }

        if(i==pmsBaseAttrInfo.getAttrValueList().size()){
            return 1;
        }

        return 0;
    }

    @Override
    public int updateAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo) {
        return pmsBaseAttrInfoMapper.updateByPrimaryKeySelective(pmsBaseAttrInfo);
    }

    @Override
    public int deleteByAttrInfoId(PmsBaseAttrInfo pmsBaseAttrInfo) {
//        int i=0;
//
//        for(PmsBaseAttrValue PmsBaseAttrValue: pmsBaseAttrInfo.getAttrValueList()){
//
//            i+=pmsBaseAttrValueMapper.deleteByPrimaryKey(PmsBaseAttrValue);
//
//        }
//
//        if(i==pmsBaseAttrInfo.getAttrValueList().size()){
//            return 1;
//        }

        PmsBaseAttrValue pmsBaseAttrValue=new PmsBaseAttrValue();
        pmsBaseAttrValue.setAttrId(pmsBaseAttrInfo.getId());
        int id=-1;
        id=pmsBaseAttrValueMapper.delete(pmsBaseAttrValue);

        return id;
    }

    @Override
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        return pmsBaseSaleAttrMapper.selectAll();
    }
}
