package com.dragon.dgmall.manage.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.dragon.dgmall.api.bean.PmsBaseAttrInfo;
import com.dragon.dgmall.api.bean.PmsBaseAttrValue;
import com.dragon.dgmall.api.bean.PmsBaseCatalog3;
import com.dragon.dgmall.api.bean.PmsBaseSaleAttr;
import com.dragon.dgmall.api.service.AttrService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@CrossOrigin
public class AttrController {

    @Reference
    AttrService attrService;

    @RequestMapping("attrInfoList")
    @ResponseBody
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id){

        List<PmsBaseAttrInfo> list=attrService.attrInfoList(catalog3Id);

        return list;
    }

    @RequestMapping("getAttrValueList")
    @ResponseBody
    public List<PmsBaseAttrValue> getAttrValueList(String attrId){

        List<PmsBaseAttrValue> list=attrService.getAttrValueList(attrId);

        return list;
    }

    @RequestMapping("saveAttrInfo")
    @ResponseBody
    public String saveAttrInfo(@RequestBody PmsBaseAttrInfo pmsBaseAttrInfo){

//        pmsBaseAttrInfo.getId()==""|| pmsBaseAttrInfo.getId().equals("")
        if(pmsBaseAttrInfo.getId()==null){
            int InfoId = attrService.insertAttrInfo(pmsBaseAttrInfo);
            pmsBaseAttrInfo.setId(String.valueOf(InfoId));
            int result=attrService.insertValueList(pmsBaseAttrInfo);

            if(result==1){
                return "good";
            }

            return "bad";
        }else{
            attrService.updateAttrInfo(pmsBaseAttrInfo);

            attrService.deleteByAttrInfoId(pmsBaseAttrInfo);

            int result=attrService.insertValueList(pmsBaseAttrInfo);
            if(result==1){
                return "good";
            }



        }
        return "bad";

    }



}
