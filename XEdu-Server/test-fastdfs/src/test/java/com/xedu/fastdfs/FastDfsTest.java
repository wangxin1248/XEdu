package com.xedu.fastdfs;

import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/29 16:17.
 * @Description: FastDfs测试类
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class FastDfsTest {

    // 测试上传功能
    @Test
    public void testUpload(){
        // 加载配置文件
        try {
            ClientGlobal.initByProperties("config/fastdfs-client.properties");
            // 定义TrackerClient，用于请求TrackerServer
            TrackerClient tracker = new TrackerClient();
            // 获取trackerserver
            TrackerServer trackerServer = tracker.getTrackerServer();
            // 创建storageserver
            StorageServer storageServer = null;
            // 创建StorageClient1对象
            StorageClient1 client = new StorageClient1(trackerServer, storageServer);
            // 打开文件
            String filePath = "/Users/wx/Downloads/timg.jpeg";
            // 开始上传文件，返回文件id
            String  fileId = client.upload_file1(filePath,"jpeg",null);
            System.out.println(fileId);
            // group1/M00/00/00/wKh0gV6Ai3qAIrL8AAPk5r6YTL026.jpeg
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 测试下载功能
    @Test
    public void testDownload(){
        // 加载配置文件
        try {
            ClientGlobal.initByProperties("config/fastdfs-client.properties");
            // 定义TrackerClient，用于请求TrackerServer
            TrackerClient tracker = new TrackerClient();
            // 获取trackerserver
            TrackerServer trackerServer = tracker.getTrackerServer();
            // 创建storageserver
            StorageServer storageServer = null;
            // 创建StorageClient1对象
            StorageClient1 client = new StorageClient1(trackerServer, storageServer);
            // 开始下载文件
            byte[] bytes = client.download_file1("group1/M00/00/00/wKh0gV6Ai3qAIrL8AAPk5r6YTL026.jpeg");
            // 使用输出流来保存文件
            FileOutputStream fileOutputStream = new FileOutputStream(new File("/Users/wx/Downloads/timg01.jpeg"));
            fileOutputStream.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
