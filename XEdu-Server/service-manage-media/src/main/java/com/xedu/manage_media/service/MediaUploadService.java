package com.xedu.manage_media.service;

import com.xedu.framework.domain.media.MediaFile;
import com.xedu.framework.domain.media.response.CheckChunkResult;
import com.xedu.framework.domain.media.response.MediaCode;
import com.xedu.framework.exception.ExceptionCast;
import com.xedu.framework.model.response.CommonCode;
import com.xedu.framework.model.response.ResponseResult;
import com.xedu.manage_media.dao.MediaFileRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/5/4 11:02.
 * @Description: 媒资文件Service
 */
@Service
public class MediaUploadService {
    // 注入dao
    @Autowired
    MediaFileRepository mediaFileRepository;
    // 获取文件上传主目录
    @Value("${service-manage-media.upload-location}")
    String upload_location;
    /**
     * 文件注册
     * 根据文件md5得到文件路径
     * 规则：
     * 一级目录：md5的第一个字符
     * 二级目录：md5的第二个字符
     * 三级目录：md5
     * @param fileMd5
     * @param fileName
     * @param fileSize
     * @param mimetype
     * @param fileExt
     * @return
     */
    public ResponseResult register(String fileMd5, String fileName, Long fileSize, String mimetype, String fileExt) {
        // 1.检查文件是否存在
        // 获取文件目录路径
        String fileFolderPath = getFileFolderPath(fileMd5);
        // 获取文件路径
        String filePath = getFilePath(fileMd5, fileExt);
        // 判断文件是否存在
        File file = new File(filePath);
        boolean fileExist = file.exists();
        // 判断文件是否在mongodb数据库中
        Optional<MediaFile> optional = mediaFileRepository.findById(fileMd5);
        // 文件在目录中以及数据库中都存在则抛出文件以及存在的异常
        if(fileExist && optional.isPresent()){
            ExceptionCast.cast(MediaCode.UPLOAD_FILE_REGISTER_EXIST);
        }
        // 2.进行文件上传前前的准备
        // 创建文件的上传路径
        File folderFile = new File(fileFolderPath);
        if(!folderFile.exists()){
            folderFile.mkdirs();
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 根据文件的md5获取文件的路径
     * @param fileMd5
     * @param fileExt
     * @return
     */
    private String getFilePath(String fileMd5, String fileExt) {
        return this.getFileFolderPath(fileMd5) + fileMd5 + "." + fileExt;
    }

    private String getFileRelativePath(String fileMd5, String fileExt){
        return fileMd5.substring(0,1) + "/" + fileMd5.substring(1,2) + "/" + fileMd5 + "/" + fileMd5+"."+fileExt;
    }

    /**
     * 根据文件md5来获取上传路径
     * @param fileMd5
     * @return
     */
    private String getFileFolderPath(String fileMd5) {
        return upload_location + fileMd5.substring(0,1) + "/" + fileMd5.substring(1,2) + "/" + fileMd5 + "/";
    }

    /**
     * 根据文件md5来获取块文件路径
     * @param fileMd5
     * @return
     */
    private String getChunkFolderPath(String fileMd5) {
        return upload_location + "/" + fileMd5.substring(0,1) + "/" + fileMd5.substring(1,2) + "/" + fileMd5 + "/" + "chunk" + "/";
    }

    /**
     * 判断分块是否存在
     * @param fileMd5
     * @param chunk
     * @param chunkSize
     * @return
     */
    public CheckChunkResult checkchunk(String fileMd5, Integer chunk, Integer chunkSize) {
        // 获取块文件的路径
        String chunkPath = getChunkFolderPath(fileMd5);
        // 判断块文件是否存在
        File file = new File(chunkPath+chunk);
        if(file.exists()){
            return new CheckChunkResult(CommonCode.SUCCESS,true);
        }else{
            return new CheckChunkResult(CommonCode.SUCCESS,false);
        }
    }

    /**
     * 上传分块文件
     * @param file
     * @param chunk
     * @param fileMd5
     * @return
     */
    public ResponseResult uploadchunk(MultipartFile file, Integer chunk, String fileMd5) {
        // 获得块文件目录
        String chunkFolderPath = getChunkFolderPath(fileMd5);
        // 判断块文件目录是否存在
        File chunkFolderFile = new File(chunkFolderPath);
        if(!chunkFolderFile.exists()){
            // 不存在则创建块文件目录
            chunkFolderFile.mkdirs();
        }
        // 定义输入输出流
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            // 获取上传文件的输入流
            inputStream = file.getInputStream();
            // 定义上传文件的输出流
            outputStream = new FileOutputStream(new File(chunkFolderPath+chunk));
            // 将输入流拷贝到输出流中
            IOUtils.copy(inputStream,outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            // 关流
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 合并文件操作
     * @param fileMd5
     * @param fileName
     * @param fileSize
     * @param mimetype
     * @param fileExt
     * @return
     */
    public ResponseResult mergechunks(String fileMd5, String fileName, Long fileSize, String mimetype, String fileExt) {
        // 1、执行文件合并
        // 获取块文件目录
        String chunkFolderPath = this.getChunkFolderPath(fileMd5);
        // 创建块文件对象
        File chunkFolderFile = new File(chunkFolderPath);
        // 获取所有的块文件
        File[] files = chunkFolderFile.listFiles();
        // 将块文件转为列表
        List<File> fileList = Arrays.asList(files);
        // 合并之后的文件路径
        String filePath = this.getFilePath(fileMd5, fileExt);
        // 创建合并之后的文件路径
        File file = new File(filePath);
        // 执行合并文件操作
        File chunkFile = this.chunkFile(fileList, file);
        if(chunkFile == null){
            ExceptionCast.cast(MediaCode.MERGE_FILE_FAIL);
        }
        // 2、校验合并之后文件的md5
        boolean result = this.checkFileMd5(chunkFile, fileMd5);
        if(!result){
            ExceptionCast.cast(MediaCode.MERGE_FILE_CHECKFAIL);
        }
        // 3、将文件信息保存到数据库中
        MediaFile mediaFile = new MediaFile();
        mediaFile.setFileId(fileMd5);//文件id为md5
        mediaFile.setFileName(fileMd5+"."+fileExt);//文件名称
        mediaFile.setFileOriginalName(fileName);//文件原始名称
        mediaFile.setFilePath(this.getFileRelativePath(fileMd5,fileExt));//文件相对路径
        mediaFile.setFileSize(fileSize);//文件大小
        mediaFile.setUploadTime(new Date());//文件上传时间
        mediaFile.setMimeType(mimetype);//文件类型
        mediaFile.setFileType(fileExt);//文件扩展名
        mediaFile.setFileStatus("301002");//文件上传状态
        mediaFileRepository.save(mediaFile);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 校验文件是否和传入的md5相同
     * @param chunkFile
     * @param fileMd5
     * @return
     */
    private boolean checkFileMd5(File chunkFile, String fileMd5) {
        FileInputStream inputStream = null;
        try {
            // 获取文件的输入流
            inputStream = new FileInputStream(chunkFile);
            // 获取文件的md5
            String md5 = DigestUtils.md5Hex(inputStream);
            // 判断两个md5是否相同
            // 为什么要忽略大小写：
            if(md5.equalsIgnoreCase(fileMd5)){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 执行文件合并操作
     * @param fileList
     * @param file
     * @return
     */
    private File chunkFile(List<File> fileList, File file) {
        // 判断合并文件是否存在
        if(file.exists()){
            file.delete();
        }else{
            try {
                file.createNewFile();
                // 对分块文件进行排序
                Collections.sort(fileList, new Comparator<File>() {
                    @Override
                    public int compare(File o1, File o2) {
                        if(Integer.parseInt(o1.getName()) > Integer.parseInt(o2.getName())){
                            return 1;
                        }else{
                            return -1;
                        }
                    }
                });
                // 创建文件写入对象
                RandomAccessFile rafWrite = new RandomAccessFile(file,"rw");
                // 遍历所有块文件执行写入操作
                for(File f : fileList){
                    // 创建文件读取对象
                    RandomAccessFile rafRead = new RandomAccessFile(f, "r");
                    // 将所有的块文件都写入文件
                    byte[] b = new byte[1024];
                    int len = -1;
                    while((len = rafRead.read(b)) != -1){
                        rafWrite.write(b, 0, len);
                    }
                    rafRead.close();
                }
                rafWrite.close();
                return file;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
}
