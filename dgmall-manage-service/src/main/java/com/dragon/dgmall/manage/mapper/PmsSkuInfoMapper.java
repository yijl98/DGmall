package com.dragon.dgmall.manage.mapper;

import com.dragon.dgmall.api.bean.PmsSkuInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PmsSkuInfoMapper extends Mapper<PmsSkuInfo> {
    List<PmsSkuInfo> selectSpuSaleAttrListCheckBySku(@Param("spuId") String spuId);
}
