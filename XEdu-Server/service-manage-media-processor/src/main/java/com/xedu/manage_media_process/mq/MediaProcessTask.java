package com.xedu.manage_media_process.mq;

import com.alibaba.fastjson.JSON;
import com.xedu.framework.domain.media.MediaFile;
import com.xedu.framework.domain.media.MediaFileProcess_m3u8;
import com.xedu.framework.utils.HlsVideoUtil;
import com.xedu.framework.utils.Mp4VideoUtil;
import com.xedu.manage_media_process.dao.MediaFileRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/5/9 12:29.
 * @Description: 执行视频处理任务
 */
@Component
public class MediaProcessTask {
    // 文件上传服务器路径
    @Value("${service-manage-media.video-location}")
    String server_path;
    // ffmpge路径
    @Value("${service-manage-media.ffmpeg-path}")
    String ffmpage_path;
    @Autowired
    MediaFileRepository mediaFileRepository;
    /**
     * 监听指定队列接收消息执行视频处理任务
     * @param msg
     */
    @RabbitListener(queues = "${service-manage-media.mq.queue-media-video-processor}", containerFactory = "customContainerFactory")
    public void receiveMediaProcessTask(String msg){
        // 1、解析消息内容，拿到mediaId
        // 消息为json字符串，解析为map
        Map map = JSON.parseObject(msg, Map.class);
        // 获取mediaId
        String mediaId = (String) map.get("mediaId");
        // 2、使用mediaId从数据库中查询文件信息
        Optional<MediaFile> optional = mediaFileRepository.findById(mediaId);
        // 文件不存在则返回
        if(!optional.isPresent()){
            return;
        }
        // 获取视频对象
        MediaFile mediaFile = optional.get();
        // 获取文件类型
        String fileType = mediaFile.getFileType();
        // 只处理avi格式的文件
        if(fileType == null || !fileType.equals("avi")){
            // 其他类型的文件无需处理
            mediaFile.setProcessStatus("303004");
            mediaFileRepository.save(mediaFile);
            return;
        }
        // 设置文件处理状态为处理中
        mediaFile.setProcessStatus("303001");
        mediaFileRepository.save(mediaFile);
        // 3、使用工具类将 avi 转为 mp4
        // 文件路径
        String video_path = server_path + mediaFile.getFilePath() + mediaFile.getFileName();
        String mp4_name = mediaFile.getFileId() + ".mp4";
        String mp4folder_path = server_path + mediaFile.getFilePath();
        // 创建工具类对象
        Mp4VideoUtil mp4VideoUtil = new Mp4VideoUtil(ffmpage_path,video_path,mp4_name,mp4folder_path);
        // 执行文件转换
        String result = mp4VideoUtil.generateMp4();
        // 判断文件是否转换成功
        if(result == null || !result.equals("success")){
            // 文件处理失败
            mediaFile.setProcessStatus("303003");
            // 保存错误信息
            MediaFileProcess_m3u8 mediaFileProcess_m3u8 = new MediaFileProcess_m3u8();
            mediaFileProcess_m3u8.setErrormsg(result);
            mediaFile.setMediaFileProcess_m3u8(mediaFileProcess_m3u8);
            mediaFileRepository.save(mediaFile);
            return;
        }
        // 4、使用工具类将 mp4 转为 m3u8 和 ts 文件
        // mp4文件路径
        String mp4_video_path = server_path + mediaFile.getFilePath() + mp4_name;
        // m3u8文件名称
        String m3u8_name = mediaFile.getFileId() + ".m3u8";
        // m3u8文件路径
        String m3u8folder_path = server_path + mediaFile.getFilePath() + "hls/";
        // 创建工具类对象
        HlsVideoUtil hlsVideoUtil = new HlsVideoUtil(ffmpage_path, mp4_video_path, m3u8_name,m3u8folder_path);
        // 执行转换
        String m3u8Result = hlsVideoUtil.generateM3u8();
        // 转换失败
        if(m3u8Result == null || !m3u8Result.equals("success")){
            // 文件处理失败
            mediaFile.setProcessStatus("303003");
            // 保存错误信息
            MediaFileProcess_m3u8 mediaFileProcess_m3u8 = new MediaFileProcess_m3u8();
            mediaFileProcess_m3u8.setErrormsg(m3u8Result);
            mediaFile.setMediaFileProcess_m3u8(mediaFileProcess_m3u8);
            mediaFileRepository.save(mediaFile);
            return;
        }
        // 获取 m3u8 列表
        List<String> ts_list = hlsVideoUtil.get_ts_list();
        MediaFileProcess_m3u8 mediaFileProcess_m3u8 = new MediaFileProcess_m3u8();
        mediaFileProcess_m3u8.setTslist(ts_list);
        mediaFile.setMediaFileProcess_m3u8(mediaFileProcess_m3u8);
        // 设置m3u8路径
        String m3u8_path = mediaFile.getFilePath() + m3u8_name;
        mediaFile.setFileUrl(m3u8_path);
        // 保存文件信息到数据库，完成文件处理
        mediaFileRepository.save(mediaFile);
    }
}
