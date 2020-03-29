package com.xedu.filesystem.service;

import com.alibaba.fastjson.JSON;
import com.xedu.filesystem.dao.FileSystemRepository;
import com.xedu.framework.domain.filesystem.FileSystem;
import com.xedu.framework.domain.filesystem.response.FileSystemCode;
import com.xedu.framework.domain.filesystem.response.UploadFileResult;
import com.xedu.framework.exception.ExceptionCast;
import com.xedu.framework.model.response.CommonCode;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/29 20:32.
 * @Description: FileSystem逻辑实现
 */
@Service
public class FileSystemService {

    @Autowired
    FileSystemRepository fileSystemRepository;

    // 从yml配置文件中获取配置信息
    @Value("${xedu.fastdfs.tracker_servers}")
    String tracker_servers;
    @Value("${xedu.fastdfs.connect_timeout_in_seconds}")
    int connect_timeout_in_seconds;
    @Value("${xedu.fastdfs.network_timeout_in_seconds}")
    int network_timeout_in_seconds;
    @Value("${xedu.fastdfs.charset}")
    String charset;

    /**
     * 上传图片
     * @param multipartFile 图片文件
     * @param filetag
     * @param businesskey
     * @param metadata
     * @return
     */
    public UploadFileResult upload(MultipartFile multipartFile,
                                          String filetag,
                                          String businesskey,
                                          String metadata){
        // 进行数据合法性判断
        if(multipartFile == null){
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_FILEISNULL);
        }
        // 将文件上传到FastDFS得到文件id
        String fileId = fdfsUpload(multipartFile);
        // 进行数据合法性判断
        if(StringUtils.isEmpty(fileId)){
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_SERVERFAIL);
        }
        // 将文件id及相关信息保存到mongodb中
        FileSystem fileSystem = new FileSystem();
        fileSystem.setFileId(fileId);// 文件id
        fileSystem.setFilePath(fileId);// 文件路径和文件id一致
        fileSystem.setFileName(multipartFile.getOriginalFilename());// 文件名称
        fileSystem.setFileSize(multipartFile.getSize());// 文件大小
        fileSystem.setFileType(multipartFile.getContentType());// 文件类型
        fileSystem.setBusinesskey(businesskey);// 传入的businesskey
        fileSystem.setFiletag(filetag);// 传入的filetag
        // 原数据存在则进行保存
        if(!StringUtils.isEmpty(metadata)){
            try{
                Map map = JSON.parseObject(metadata, Map.class);
                fileSystem.setMetadata(map);// 文件元数据
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        // 将信息保存到数据库中
        fileSystemRepository.save(fileSystem);
        return new UploadFileResult(CommonCode.SUCCESS,fileSystem);
    }

    /**
     * 上传图片文件到FastDFS
     * @param multipartFile 图片文件
     * @return 图片id
     */
    private String fdfsUpload(MultipartFile multipartFile){
        // 初始化FastDFS环境
        initFastDFSConfig();
        try {
            // 定义TrackerClient，用于请求TrackerServer
            TrackerClient tracker = new TrackerClient();
            // 获取trackerserver
            TrackerServer trackerServer = tracker.getTrackerServer();
            // 创建storageserver
            StorageServer storageServer = null;
            // 创建StorageClient1对象
            StorageClient1 client = new StorageClient1(trackerServer, storageServer);
            // 获取图片字节数组
            byte[] bytes = multipartFile.getBytes();
            // 获取图片扩展名
            String fileExtname = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".")+1);
            // 开始上传文件，返回文件id
            String  fileId = client.upload_file1(bytes,fileExtname,null);
            return fileId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 初始化FastDFS环境
     */
    private void initFastDFSConfig(){
        try {
            // 初始化tracker服务器地址
            ClientGlobal.initByTrackers(tracker_servers);
            ClientGlobal.setG_charset(charset);
            ClientGlobal.setG_network_timeout(network_timeout_in_seconds);
            ClientGlobal.setG_connect_timeout(connect_timeout_in_seconds);
        } catch (Exception e) {
            e.printStackTrace();
            // 抛出异常
            ExceptionCast.cast(FileSystemCode.FS_INIT_ERROR);
        }
    }
}
