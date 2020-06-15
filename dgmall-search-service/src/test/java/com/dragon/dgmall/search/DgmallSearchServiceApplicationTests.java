package com.dragon.dgmall.search;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dragon.dgmall.api.bean.PmsSearchSkuInfo;
import com.dragon.dgmall.api.bean.PmsSkuInfo;
import com.dragon.dgmall.api.service.SkuService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.commons.beanutils.BeanUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DgmallSearchServiceApplicationTests {

    @Reference
    SkuService SkuService;

    @Autowired
    JestClient JestClient;


    //从mysql中查询数据并导入到es数据库中
    @Test
    public void put() throws IOException, InvocationTargetException, IllegalAccessException {

        //从mysql查询数据

        List<PmsSkuInfo> pmsSkuInfoList = SkuService.getAllSku();


        //转换为es的数据结构

        List<PmsSearchSkuInfo> pmsSearchSkuInfoList = new ArrayList<>();

        for (PmsSkuInfo pmsSkuInfo : pmsSkuInfoList) {

            PmsSearchSkuInfo pmsSearchSkuInfo = new PmsSearchSkuInfo();

            BeanUtils.copyProperties(pmsSearchSkuInfo, pmsSkuInfo);

            pmsSearchSkuInfoList.add(pmsSearchSkuInfo);

        }


        //导入es

        for (PmsSearchSkuInfo pmsSearchSkuInfo : pmsSearchSkuInfoList) {

            Index put = new Index.Builder(pmsSearchSkuInfo).index("dgmallpms").type("PmsSkuInfo").id(pmsSearchSkuInfo.getId()).build();

            JestClient.execute(put);


        }

    }

    //es复杂查询
    @Test
    public void search() throws IOException {
        //用api执行复杂查询
        List<PmsSearchSkuInfo> pmsSearchSkuInfoList = new ArrayList<>();

        Search search = new Search.Builder("{\n" +
                "  \"query\": {\n" +
                "    \"bool\": {\n" +
                "      \"filter\": [\n" +
                "        {\n" +
                "          \"term\":{\n" +
                "            \"skuAttrValueList.valueId\": \"48\"\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          \"term\":{\n" +
                "            \"skuAttrValueList.valueId\": \"62\"\n" +
                "          }\n" +
                "        }\n" +
                "      ],\n" +
                "      \"must\": [\n" +
                "        {\n" +
                "          \"match\": {\n" +
                "            \"skuName\": \"文龙\"\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                "}  ").addIndex("dgmallpms").addType("PmsSkuInfo").build();

        SearchResult execute = JestClient.execute(search);

        List<SearchResult.Hit<PmsSearchSkuInfo, Void>> hits = execute.getHits(PmsSearchSkuInfo.class);

        for (SearchResult.Hit<PmsSearchSkuInfo, Void> hit : hits) {

            PmsSearchSkuInfo source = hit.source;
            pmsSearchSkuInfoList.add(source);

        }

        System.out.println(pmsSearchSkuInfoList.size());

    }

    @Test
    public void jestDslUtils() throws IOException {
        //jest-dsl工具

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //boll

        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        //filter

        TermQueryBuilder termQueryBuilder = new TermQueryBuilder("skuAttrValueList.valueId","48");
        boolQueryBuilder.filter(termQueryBuilder);

        //must

        MatchQueryBuilder matchQueryBuilder=new MatchQueryBuilder("skuName","文龙");
        boolQueryBuilder.must(matchQueryBuilder);

        //querry

        searchSourceBuilder.query(boolQueryBuilder);

        //from

        searchSourceBuilder.from(0);

        //size

        searchSourceBuilder.size(200);

        //highlight

        searchSourceBuilder.highlight(null);

        String dslStr=searchSourceBuilder.toString();

        System.out.println(dslStr);

        //用api执行复杂查询
        List<PmsSearchSkuInfo> pmsSearchSkuInfoList = new ArrayList<>();

        Search search = new Search.Builder(dslStr).addIndex("dgmallpms").addType("PmsSkuInfo").build();

        SearchResult execute = JestClient.execute(search);

        List<SearchResult.Hit<PmsSearchSkuInfo, Void>> hits = execute.getHits(PmsSearchSkuInfo.class);

        for (SearchResult.Hit<PmsSearchSkuInfo, Void> hit : hits) {

            PmsSearchSkuInfo source = hit.source;
            pmsSearchSkuInfoList.add(source);

        }

        System.out.println(pmsSearchSkuInfoList.size());

    }

}
