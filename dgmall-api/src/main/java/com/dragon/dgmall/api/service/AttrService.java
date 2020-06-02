package com.dragon.dgmall.api.service;

import com.dragon.dgmall.api.bean.PmsBaseAttrInfo;
import com.dragon.dgmall.api.bean.PmsBaseAttrValue;
import com.dragon.dgmall.api.bean.PmsBaseSaleAttr;

import java.util.List;

public interface AttrService {
    List<PmsBaseAttrInfo> attrInfoList(String catalog3Id);

    List<PmsBaseAttrValue> getAttrValueList(String attrId);

    int insertAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo);

    int insertValueList(PmsBaseAttrInfo pmsBaseAttrInfo);

    int updateAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo);

    int deleteByAttrInfoId(PmsBaseAttrInfo pmsBaseAttrInfo);

    List<PmsBaseSaleAttr> baseSaleAttrList();
}
