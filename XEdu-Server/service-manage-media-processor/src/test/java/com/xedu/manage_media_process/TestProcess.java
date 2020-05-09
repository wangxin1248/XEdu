package com.xedu.manage_media_process;

import com.xedu.framework.utils.Mp4VideoUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/5/9 11:20.
 * @Description: 测试processBuilder调用第三方命令的实现
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestProcess {
    @Test
    public void testProcessBuilder() throws IOException {
        // 创建processBuilder对象
        ProcessBuilder processBuilder = new ProcessBuilder();
        // 传入所要执行的命令
        processBuilder.command("ifconfig");
        // 将标准输入流和错误输入流合并，通过标准输入流读取信息
        processBuilder.redirectErrorStream(true);
        // 启动命令获取到一个程序的进程对象
        Process start = processBuilder.start();
        // 获取输入流
        InputStream inputStream = start.getInputStream();
        // 将输入流转换为字符输入流
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");
        // 读取字符输入流
        int len = -1;
        char[] c = new char[1024];
        while((len = inputStreamReader.read(c)) != -1){
            String str = new String(c,0,len);
            System.out.print(str);
        }
        // 关流
        inputStreamReader.close();
        inputStream.close();
    }
    @Test
    public void testFFmpeg() throws IOException {
        // 创建processBuilder对象
        ProcessBuilder processBuilder = new ProcessBuilder();
        // 定义所要执行的命令
        List<String> command = new ArrayList<>();
        // ffmpeg -i lucene.avi -c:v libx264 -s 1280x720 -pix_fmt yuv420p -b:a 63k -b:v 753k -r 18 ./lucene.mp4
        command.add("ffmpeg");
        command.add("-i");
        command.add("/Users/wx/Documents/学习资料/learn/学成在线/day14 媒资管理/资料/lucene.avi");
        command.add("-y");//覆盖输出文件
        command.add("-c:v");
        command.add("libx264");
        command.add("-s");
        command.add("1280x720");
        command.add("-pix_fmt");
        command.add("yuv420p");
        command.add("-b:a");
        command.add("63k");
        command.add("-b:v");
        command.add("753k");
        command.add("-r");
        command.add("18");
        command.add("/Users/wx/Documents/学习资料/learn/学成在线/day14 媒资管理/资料/lucene.mp4");
        // 传入所要执行的命令
        processBuilder.command(command);
        // 将标准输入流和错误输入流合并，通过标准输入流读取信息
        processBuilder.redirectErrorStream(true);
        // 启动命令获取到一个程序的进程对象
        Process start = processBuilder.start();
        // 获取输入流
        InputStream inputStream = start.getInputStream();
        // 将输入流转换为字符输入流
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");
        // 读取字符输入流
        int len = -1;
        char[] c = new char[1024];
        while((len = inputStreamReader.read(c)) != -1){
            String str = new String(c,0,len);
            System.out.print(str);
        }
        // 关流
        inputStreamReader.close();
        inputStream.close();
    }
    @Test
    public void testMp4VideoUtil(){
        // ffmpeg的路径
        String ffmpeg_path = "ffmpeg";
        // 源avi视频的路径
        String video_path = "/Users/wx/Documents/学习资料/learn/学成在线/day14 媒资管理/资料/lucene.avi";
        // 转换后mp4文件的名称
        String mp4_name = "lucene.mp4";
        // 转换后mp4文件的路径
        String mp4folder_path = "/Users/wx/Documents/学习资料/learn/学成在线/day14 媒资管理/资料/";
        // 创建工具类对象
        Mp4VideoUtil mp4VideoUtil = new Mp4VideoUtil(ffmpeg_path,video_path,mp4_name,mp4folder_path);
        // 开始转换
        String s = mp4VideoUtil.generateMp4();
        System.out.println(s);
    }
}
