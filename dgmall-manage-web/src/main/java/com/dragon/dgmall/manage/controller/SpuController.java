package com.dragon.dgmall.manage.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.dragon.dgmall.api.bean.*;
import com.dragon.dgmall.api.service.SpuService;
import com.dragon.dgmall.manage.utils.PmsUploadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@CrossOrigin
public class SpuController {

    @Reference
    SpuService spuService;

    @RequestMapping("spuList")
    @ResponseBody
    public List<PmsProductInfo> spuList(String catalog3Id){


        List<PmsProductInfo> list=spuService.spuList(catalog3Id);

        return list;
    }

    @RequestMapping("spuSaleAttrList")
    @ResponseBody
    public List<PmsProductSaleAttr> spuSaleAttrList(String spuId){


        List<PmsProductSaleAttr> productSaleAttrList=spuService.getSpuSaleAttrList(spuId);
        for (PmsProductSaleAttr pmsProductSaleAttr : productSaleAttrList) {

            List<PmsProductSaleAttrValue> productSaleAttrValueList=spuService.getSpuSaleAttrValueList(spuId,pmsProductSaleAttr.getSaleAttrId());
            pmsProductSaleAttr.setSpuSaleAttrValueList(productSaleAttrValueList);
        }

        return productSaleAttrList;
    }

    @RequestMapping("baseSaleAttrList")
    @ResponseBody
    public List<PmsBaseSaleAttr> baseSaleAttrList(){
        List<PmsBaseSaleAttr>  List = spuService.baseSaleAttrList();

        return List;

    }

    @RequestMapping("saveSpuInfo")
    @ResponseBody
    public String saveSpuInfo(@RequestBody PmsProductInfo pmsProductInfo){

        if(pmsProductInfo.getId()==null){
            String infoId=spuService.saveSpuInfo(pmsProductInfo);
            pmsProductInfo.setId(infoId);
            spuService.saveProductSaleAttr(pmsProductInfo);
            spuService.saveProductImage(pmsProductInfo);
            int i=spuService.saveProductSaleAttrValue(pmsProductInfo);
            if(i==1){
                return "succeed";
            }
        }




        return "false";

    }

    @RequestMapping("fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("file") MultipartFile multipartFile){
        String fileUrl="";
        //将图片或者音视频上传到分布式文件存储系统
        fileUrl= PmsUploadUtil.uploadFile(multipartFile);

        //将图片的存储路径返回给页面
        System.out.println(fileUrl);

        return fileUrl;

    }
    @RequestMapping("spuImageList")
    @ResponseBody
    public List<PmsProductImage> spuImageList(String spuId){

        List<PmsProductImage> imageList=spuService.getSpuImageList(spuId);

        return imageList;

    }


}
