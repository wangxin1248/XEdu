package com.xedu.manage_media;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/5/3 21:11.
 * @Description: 测试文件的分块和合并
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestFile {
    @Test
    public void testChunk() throws IOException {
        // 原文件
        File sourceFile = new File("/Users/wx/Documents/学习资料/learn/学成在线/day13 在线学习 HLS/资料/lucene.avi");
        // 块文件路径
        String chunkPath = "/Users/wx/Documents/学习资料/learn/学成在线/day13 在线学习 HLS/资料/chunk/";
        File chunkFolder = new File(chunkPath);
        // 块路径不存在则创建
        if(!chunkFolder.exists()){
            chunkFolder.mkdirs();
        }
        // 块大小 1M
        long chunkSize = 1 * 1024 * 1024;
        // 块数 原文件长度除以块大小
        long chunkSum = (long) Math.ceil(sourceFile.length()*1.0/chunkSize);
        if(chunkSum <= 0){
            chunkSum = 1;
        }
        // 读取缓冲区
        byte[] b = new byte[1024];
        // 原文件读取对象
        RandomAccessFile rafRead = new RandomAccessFile(sourceFile,"r");
        // 开始进行文件分块
        for(long i=0;i<chunkSum;i++){
            // 块文件
            File chunkFile = new File(chunkPath+sourceFile.getName()+"-"+i);
            // 新建块文件
            boolean newFile = chunkFile.createNewFile();
            if(newFile){
                // 块文件写入对象
                RandomAccessFile rafWrite = new RandomAccessFile(chunkFile,"rw");
                // 文件读取结束标识
                int len = -1;
                // 开始进行原文件读取
                while((len = rafRead.read(b))!=-1){
                    //  写入文件
                    rafWrite.write(b,0,len);
                    // 块文件已经写满
                    if(chunkFile.length()>=chunkSize){
                        break;
                    }
                }
                // 关闭流
                rafWrite.close();
            }
        }
        // 关闭流
        rafRead.close();
    }
    @Test
    public void testMerge() throws IOException {
        // 块文件路径
        String chunkPath = "/Users/wx/Documents/学习资料/learn/学成在线/day13 在线学习 HLS/资料/chunk/";
        // 块文件
        File chunkFolder = new File(chunkPath);
        // 获取所有的块文件
        File[] files = chunkFolder.listFiles();
        // 对块文件进行升序排序
        List<File> fileList = Arrays.asList(files);
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if(Integer.parseInt(o1.getName().substring(o1.getName().indexOf("-"),o1.getName().length()))>Integer.parseInt(o2.getName().substring(o2.getName().indexOf("-"),o2.getName().length()))){
                    return -1;
                }
                return 1;
            }
        });
        // 写文件
        File file = new File("/Users/wx/Documents/学习资料/learn/学成在线/day13 在线学习 HLS/资料/lucene-merge.avi");
        // 写文件写入对象
        RandomAccessFile rafWrite = new RandomAccessFile(file,"rw");
        // 开始遍历块文件进行文件的合并
        for(File f : fileList){
            // 块文件读取对象
            RandomAccessFile rafRead = new RandomAccessFile(f,"r");
            int len = -1;
            byte[] b = new byte[1024];
            // 每次读1024个byte进行写入
            while((len = rafRead.read(b)) != -1){
                rafWrite.write(b,0,len);
            }
            // 关流
            rafRead.close();
        }
        // 关流
        rafWrite.close();
    }
}
