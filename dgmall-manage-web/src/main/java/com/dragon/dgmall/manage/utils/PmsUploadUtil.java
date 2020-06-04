package com.dragon.dgmall.manage.utils;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLDecoder;

public class PmsUploadUtil {


    public static String uploadFile(MultipartFile multipartFile) {

        String fileUrl="";

        //上传图片到服务器
        String confPath=PmsUploadUtil.class.getResource("/tracker.conf").getPath();

        try {
            confPath= URLDecoder.decode(confPath,"utf-8");
            ClientGlobal.init(confPath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }

        TrackerClient trackerClient =new TrackerClient();

        TrackerServer trackerServer = null;
        try {
            trackerServer = trackerClient.getTrackerServer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        StorageClient storageClient =new StorageClient(trackerServer,null);


        try {
            byte[] bytes = multipartFile.getBytes();//获得上传的二进制数组
            String originalFilename = multipartFile.getOriginalFilename();
            int lastIndex=originalFilename.lastIndexOf(".");
            String ext_name = originalFilename.substring(lastIndex+1);

            String[]  uploadInfos = storageClient.upload_file(bytes,ext_name,null);

            fileUrl="http://192.168.3.4";

            for (String uploadInfo : uploadInfos) {

                fileUrl+="/"+uploadInfo;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }



        return fileUrl;
    }
}
