package com.dragon.dgmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.dragon.dgmall.api.bean.PmsSkuAttrValue;
import com.dragon.dgmall.api.bean.PmsSkuImage;
import com.dragon.dgmall.api.bean.PmsSkuInfo;
import com.dragon.dgmall.api.bean.PmsSkuSaleAttrValue;
import com.dragon.dgmall.api.service.SkuService;
import com.dragon.dgmall.manage.mapper.PmsSkuAttrValueMapper;
import com.dragon.dgmall.manage.mapper.PmsSkuImageMapper;
import com.dragon.dgmall.manage.mapper.PmsSkuInfoMapper;
import com.dragon.dgmall.manage.mapper.PmsSkuSaleAttrValueMapper;
import com.dragon.dgmall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.UUID;

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

    @Autowired
    RedisUtil RedisUtil;

    @Override
    public void saveSkuInfo(PmsSkuInfo pmsSkuInfo) {
        pmsSkuInfoMapper.insertSelective(pmsSkuInfo);
        //插入skuInfo
        String skuId = String.valueOf(pmsSkuInfo.getId());
        pmsSkuInfo.setId(skuId);
        //插入平台属性关联
        List<PmsSkuAttrValue> skuAttrValueList = pmsSkuInfo.getSkuAttrValueList();
        if (skuAttrValueList != null) {
            for (PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList) {
                pmsSkuAttrValue.setSkuId(skuId);
                pmsSkuAttrValueMapper.insertSelective(pmsSkuAttrValue);
            }
        }

        //插入销售属性关联
        List<PmsSkuSaleAttrValue> skuSaleAttrValues = pmsSkuInfo.getSkuSaleAttrValueList();
        if (skuSaleAttrValues != null) {
            for (PmsSkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValues) {
                skuSaleAttrValue.setSkuId(skuId);
                pmsSkuSaleAttrValueMapper.insertSelective(skuSaleAttrValue);
            }
        }

        //插入图片信息
        List<PmsSkuImage> skuImageList = pmsSkuInfo.getSkuImageList();
        if (skuImageList != null) {
            for (PmsSkuImage pmsSkuImage : skuImageList) {
                pmsSkuImage.setSkuId(skuId);
                pmsSkuImageMapper.insertSelective(pmsSkuImage);
            }
        }

    }

    //    @Override
    public PmsSkuInfo getSkuFromDB(String skuId) {
        PmsSkuInfo PmsSkuInfo = new PmsSkuInfo();
        PmsSkuInfo.setId(skuId);
        //sku商品对象
        PmsSkuInfo = pmsSkuInfoMapper.selectOne(PmsSkuInfo);
        //sku图片集合
        PmsSkuImage pmsSkuImage = new PmsSkuImage();
        pmsSkuImage.setSkuId(skuId);
        List<PmsSkuImage> imageList = pmsSkuImageMapper.select(pmsSkuImage);
        PmsSkuInfo.setSkuImageList(imageList);

        return PmsSkuInfo;
    }

    @Override
    public PmsSkuInfo getSkuById(String skuId) {

        PmsSkuInfo pmsSkuInfo = null;

        //链接缓存
        Jedis jedis = RedisUtil.getJedis();

        //查询缓存
        String skuKey = "sku:" + skuId + "info";
        String skuJson = jedis.get(skuKey);

        if (StringUtils.isNotBlank(skuJson)) {
            //如果查询到缓存，则把redis存储的json转换为Pojo对象返回
            pmsSkuInfo = JSON.parseObject(skuJson, PmsSkuInfo.class);
        } else {
            //如果缓存中没有数据查询mysql
            //设置分布式锁
            String token = UUID.randomUUID().toString();
            String ok=jedis.set("sku:" + skuId + "lock",token,"nx","px",10*1000);//拿到锁的线程有10秒的过期时间

            if(StringUtils.isNotBlank(ok)&& ok.equals("OK")){
                //获取锁成功，有权在10s内访问数据库
                pmsSkuInfo = getSkuFromDB(skuId);

                //查询结果输入redis
                if(pmsSkuInfo!=null){
                    jedis.set("sku:" + skuId + "info",JSON.toJSONString(pmsSkuInfo));


                }else{
                    //数据库不存在该sku
                    //为了防止缓存穿透
                    jedis.setex("sku:" + skuId + "info",60,JSON.toJSONString(""));


                }

                //在访问mysql后，将mysql的分布式锁释放
                if(jedis.get("sku:" + skuId + "lock")==token){
                    jedis.del("sku:" + skuId + "lock");


                }


            }else{
                //设置失败,自旋
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return getSkuById(skuId);
            }




        }

        jedis.close();

        return pmsSkuInfo;
    }

    @Override
    public List<PmsSkuInfo> getSpuSaleAttrListCheckBySku(String spuId) {
        return pmsSkuInfoMapper.selectSpuSaleAttrListCheckBySku(spuId);
    }

    @Override
    public List<PmsSkuInfo> getAllSku() {

        List<PmsSkuInfo> pmsSkuInfoList=pmsSkuInfoMapper.selectAll();

        for (PmsSkuInfo pmsSkuInfo : pmsSkuInfoList) {

            String skuId = pmsSkuInfo.getId();
            PmsSkuAttrValue pmsSkuAttrValue=new PmsSkuAttrValue();

            pmsSkuAttrValue.setSkuId(skuId);

            List<PmsSkuAttrValue> pmsSkuAttrValueList=pmsSkuAttrValueMapper.select(pmsSkuAttrValue);

            pmsSkuInfo.setSkuAttrValueList(pmsSkuAttrValueList);
        }

        return pmsSkuInfoList;
    }

    public void test(){


        try {
            Thread.sleep(11212);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        test();
    }
}
