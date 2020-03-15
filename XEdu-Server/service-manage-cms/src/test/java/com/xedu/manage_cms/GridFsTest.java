package com.xedu.manage_cms;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/15 21:15.
 * @Description: GridFs测试
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class GridFsTest {
    @Autowired
    GridFsTemplate gridFsTemplate;
    @Autowired
    GridFSBucket gridFSBucket;

    // 测试通过GridFs向mongodb写入模版对象
    @Test
    public void write2GridFs() throws FileNotFoundException {
        // 所要存储的文件
        File file = new File("/Users/wx/project/XEduCode/XEdu-Server/test-freemarker/src/main/resources/templates/index_banner.ftl");
        // 定义输入流
        FileInputStream inputStream = new FileInputStream(file);
        // 通过GridFsTemplate写入流数据
        ObjectId objectId = gridFsTemplate.store(inputStream,"轮播图测试文件");
        System.out.println(objectId);
    }

    // 测试从mongodb中读取页面对象
    @Test
    public void readFromGridFs() throws IOException {
        // 所要下载的模版对象在mongodb中存储的objectid
        String id = "5e6e295d3e824393be0fd0b9";
        // 根据id查询文件
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(id)));
        // 打开下载流对象
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        // 创建gridFsResource，用于获取流对象
        GridFsResource gridFsResource = new GridFsResource(gridFSFile,gridFSDownloadStream);
        // 获取流中的数据
        String res = IOUtils.toString(gridFSDownloadStream,"utf-8");
        System.out.println(res);
    }
}
