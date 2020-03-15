package com.xedu.test.freemarker;

import com.xedu.test.freemarker.model.Student;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/15 16:17.
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class FreeMarkerTest {

    // 测试静态化，基于ftl模版文件生成html文件
    // 首先加载模版，然后创建数据，最后静态化
    @Test
    public void test2Html() throws IOException, TemplateException {
        // 定义配置类，传入Configuration的版本
        Configuration configuration = new Configuration(Configuration.getVersion());
        // 定义模版
        // 获取当前classpath路径
        String classpath = this.getClass().getResource("/").getPath();
        configuration.setDirectoryForTemplateLoading(new File(classpath+"/templates/"));
        // 获取模版文件的内容
        Template template = configuration.getTemplate("test01.ftl");
        // 定义数据模型
        Map map = getMap();
        // 静态化
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template,map);
        // 将静态化数据保存为文件
        InputStream inputStream = IOUtils.toInputStream(content);
        FileOutputStream outputStream = new FileOutputStream(new File("/Users/wx/project/XEduCode/XEdu-Server/test-freemarker/src/test/resources/templates/test.html"));
        // 拷贝数据
        IOUtils.copy(inputStream,outputStream);
        // 关流
        inputStream.close();
        outputStream.close();
    }

    // 测试静态化，使用string字符串来生成模版并静态化
    // 大概思路和从文件中读取模版类似，但比其更加灵活
    @Test
    public void test2HtmlByString() throws IOException, TemplateException {
        // 定义配置类，传入Configuration的版本
        Configuration configuration = new Configuration(Configuration.getVersion());
        // 获取模版
        //模板内容，这里测试时使用简单的字符串作为模板
        String templateString=""
                + "<html>\n"
                + " <head></head>\n"
                + " <body>\n"
                + " 名称：${name}\n"
                + " </body>\n"
                + "</html>";
        // 定义字符串模版加载器
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        // 加载模版字符串并生成虚拟的template模版对象
        stringTemplateLoader.putTemplate("template",templateString);
        // 使用配置器获取字符串模版加载器
        configuration.setTemplateLoader(stringTemplateLoader);
        // 生成模版对象
        Template template = configuration.getTemplate("template","utf-8");
        // -------------以下的内容和上面的方法一致-------------
        // 定义数据模型
        Map<String,Object> map = new HashMap<>();
        map.put("name","王鑫");
        // 静态化
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template,map);
        // 将静态化数据保存为文件
        InputStream inputStream = IOUtils.toInputStream(content);
        FileOutputStream outputStream = new FileOutputStream(new File("/Users/wx/project/XEduCode/XEdu-Server/test-freemarker/src/test/resources/templates/test.html"));
        // 拷贝数据
        IOUtils.copy(inputStream,outputStream);
        // 关流
        inputStream.close();
        outputStream.close();
    }
    public Map getMap(){
        Map<String,Object> map = new HashMap<>();
        // 设置需要传入页面的数据
        Student student1 = new Student();
        Student student2 = new Student();
        student1.setName("张三");
        student2.setName("李四");
        student1.setAge(18);
        student2.setAge(19);
        student1.setMoney(2000.1f);
        student2.setMoney(20f);
        student1.setBirthday(new Date());
        student2.setBirthday(new Date());
        List<Student> list = new ArrayList<>();
        list.add(student1);
        list.add(student2);
        Map<String,Student> stuMap = new HashMap<>();
        stuMap.put("student1",student1);
        stuMap.put("student2",student2);
        // 向模型传递数据
        map.put("students",list);
        map.put("student1",student1);
        map.put("stuMap",stuMap);
        return map;
    }
}
