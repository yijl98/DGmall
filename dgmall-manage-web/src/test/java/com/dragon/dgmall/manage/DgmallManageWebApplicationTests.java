package com.dragon.dgmall.manage;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URLDecoder;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DgmallManageWebApplicationTests {

    //测试fastdfs图片上传
    @Test
    public void contextLoads() throws IOException, MyException {

        String confPath=DgmallManageWebApplicationTests.class.getResource("/tracker.conf").getPath();
        confPath= URLDecoder.decode(confPath,"utf-8");
        ClientGlobal.init(confPath);

        TrackerClient trackerClient =new TrackerClient();

        TrackerServer trackerServer = trackerClient.getTrackerServer();

        StorageClient storageClient =new StorageClient(trackerServer,null);

        String[] uploadInfos=storageClient.upload_file("c:/1.mp4","mp4",null);

        String url="http://192.168.3.4";

        for (String uploadInfo : uploadInfos) {

            url+="/"+uploadInfo;
        }

        System.out.println(url);

    }

}
