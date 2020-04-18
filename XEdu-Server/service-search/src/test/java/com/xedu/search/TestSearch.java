package com.xedu.search;

import org.apache.lucene.queryparser.xml.builders.BooleanQueryBuilder;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/4/12 19:01.
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSearch {
    // 注入对应的es客户端，优先使用高等级客户端
    @Autowired
    RestHighLevelClient restHighLevelClient;
    @Autowired
    RestClient restClient;

    // 搜索全部记录
    @Test
    public void testSearchAll() throws IOException, ParseException {
        // 搜索请求对象
        SearchRequest searchRequest = new SearchRequest("xedu_course");
        // 搜索源构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 搜索方式
        // matchAllQuery搜索全部
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        // 设置源字段过滤，第一个参数表示结果集包括哪些字段，第二个参数表示结果集不包括哪些字段
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel","price","timestamp"},new String[]{});
        // 向搜索请求对象设置搜索源
        searchRequest.source(searchSourceBuilder);
        // 执行搜索
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        // 搜索结果
        SearchHits hits = searchResponse.getHits();
        // 匹配到的总记录数
        TotalHits totalHits = hits.getTotalHits();
        // 得到匹配高的文档
        SearchHit[] searchHits = hits.getHits();
        // 日期格式化对象
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(SearchHit i : searchHits){
            // 文档的主键
            String id = i.getId();
            // 源文档内容
            Map<String,Object> map = i.getSourceAsMap();
            String name = (String) map.get("name");
            String studymodel = (String) map.get("studymodel");
            Double price = (Double) map.get("price");
            Date timestamp = dateFormat.parse((String) map.get("timestamp"));
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(price);
            System.out.println(timestamp);
        }
    }

    // 分页搜索全部记录
    @Test
    public void testSearchPage() throws IOException, ParseException {
        // 搜索请求对象
        SearchRequest searchRequest = new SearchRequest("xedu_course");
        // 搜索源构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 当前页码
        int page = 1;
        // 每页显示记录数
        int size = 1;
        // 当前页码数据起始角标
        int from = (page-1)*size;
        // 设置分页起始角标
        searchSourceBuilder.from(from);
        // 设置当前也的显示数据量
        searchSourceBuilder.size(size);
        // 搜索方式
        // matchAllQuery搜索全部
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        // 设置源字段过滤，第一个参数表示结果集包括哪些字段，第二个参数表示结果集不包括哪些字段
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel","price","timestamp"},new String[]{});
        // 向搜索请求对象设置搜索源
        searchRequest.source(searchSourceBuilder);
        // 执行搜索
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        // 搜索结果
        SearchHits hits = searchResponse.getHits();
        // 匹配到的总记录数
        TotalHits totalHits = hits.getTotalHits();
        // 得到匹配高的文档
        SearchHit[] searchHits = hits.getHits();
        // 日期格式化对象
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(SearchHit i : searchHits){
            // 文档的主键
            String id = i.getId();
            // 源文档内容
            Map<String,Object> map = i.getSourceAsMap();
            String name = (String) map.get("name");
            String studymodel = (String) map.get("studymodel");
            Double price = (Double) map.get("price");
            Date timestamp = dateFormat.parse((String) map.get("timestamp"));
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(price);
            System.out.println(timestamp);
        }
    }

    // term 关键字搜索
    @Test
    public void testTermSearch() throws IOException, ParseException {
        // 搜索请求对象
        SearchRequest searchRequest = new SearchRequest("xedu_course");
        // 搜索源构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 当前页码
        int page = 1;
        // 每页显示记录数
        int size = 1;
        // 当前页码数据起始角标
        int from = (page-1)*size;
        // 设置分页起始角标
        searchSourceBuilder.from(from);
        // 设置当前也的显示数据量
        searchSourceBuilder.size(size);
        // 搜索方式
        // termQuery按照关键字来查询
        searchSourceBuilder.query(QueryBuilders.termQuery("name","spring"));
        // 设置源字段过滤，第一个参数表示结果集包括哪些字段，第二个参数表示结果集不包括哪些字段
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel","price","timestamp"},new String[]{});
        // 向搜索请求对象设置搜索源
        searchRequest.source(searchSourceBuilder);
        // 执行搜索
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        // 搜索结果
        SearchHits hits = searchResponse.getHits();
        // 匹配到的总记录数
        TotalHits totalHits = hits.getTotalHits();
        // 得到匹配高的文档
        SearchHit[] searchHits = hits.getHits();
        // 日期格式化对象
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(SearchHit i : searchHits){
            // 文档的主键
            String id = i.getId();
            // 源文档内容
            Map<String,Object> map = i.getSourceAsMap();
            String name = (String) map.get("name");
            String studymodel = (String) map.get("studymodel");
            Double price = (Double) map.get("price");
            Date timestamp = dateFormat.parse((String) map.get("timestamp"));
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(price);
            System.out.println(timestamp);
        }
    }

    // 根据id搜索
    @Test
    public void testTermSearchById() throws IOException, ParseException {
        // 搜索请求对象
        SearchRequest searchRequest = new SearchRequest("xedu_course");
        // 搜索源构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        String[] ids = new String[]{"1","2"};
        // 搜索方式
        // termsQuery按照id查询，注意是termsQuery，中间有个s
        searchSourceBuilder.query(QueryBuilders.termsQuery("_id",ids));
        // 设置源字段过滤，第一个参数表示结果集包括哪些字段，第二个参数表示结果集不包括哪些字段
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel","price","timestamp"},new String[]{});
        // 向搜索请求对象设置搜索源
        searchRequest.source(searchSourceBuilder);
        // 执行搜索
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        // 搜索结果
        SearchHits hits = searchResponse.getHits();
        // 匹配到的总记录数
        TotalHits totalHits = hits.getTotalHits();
        // 得到匹配高的文档
        SearchHit[] searchHits = hits.getHits();
        // 日期格式化对象
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(SearchHit i : searchHits){
            // 文档的主键
            String id = i.getId();
            // 源文档内容
            Map<String,Object> map = i.getSourceAsMap();
            String name = (String) map.get("name");
            String studymodel = (String) map.get("studymodel");
            Double price = (Double) map.get("price");
            Date timestamp = dateFormat.parse((String) map.get("timestamp"));
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(price);
            System.out.println(timestamp);
        }
    }

    // matchQuery
    @Test
    public void testmatchQuery() throws IOException, ParseException {
        // 搜索请求对象
        SearchRequest searchRequest = new SearchRequest("xedu_course");
        // 搜索源构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        String[] ids = new String[]{"1","2"};
        // 搜索方式
        // matchQuery，对文本进行分词搜索
        searchSourceBuilder.query(QueryBuilders.matchQuery("description","Spring开发").minimumShouldMatch("80%"));
        // 设置源字段过滤，第一个参数表示结果集包括哪些字段，第二个参数表示结果集不包括哪些字段
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel","price","timestamp"},new String[]{});
        // 向搜索请求对象设置搜索源
        searchRequest.source(searchSourceBuilder);
        // 执行搜索
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        // 搜索结果
        SearchHits hits = searchResponse.getHits();
        // 匹配到的总记录数
        TotalHits totalHits = hits.getTotalHits();
        // 得到匹配高的文档
        SearchHit[] searchHits = hits.getHits();
        // 日期格式化对象
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(SearchHit i : searchHits){
            // 文档的主键
            String id = i.getId();
            // 源文档内容
            Map<String,Object> map = i.getSourceAsMap();
            String name = (String) map.get("name");
            String studymodel = (String) map.get("studymodel");
            Double price = (Double) map.get("price");
            Date timestamp = dateFormat.parse((String) map.get("timestamp"));
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(price);
            System.out.println(timestamp);
        }
    }

    // MultiMatchQuery
    @Test
    public void testMultiMatchQuery() throws IOException, ParseException {
        // 搜索请求对象
        SearchRequest searchRequest = new SearchRequest("xedu_course");
        // 搜索源构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        String[] ids = new String[]{"1","2"};
        // 搜索方式
        // MultiMatchQuery，多个条件匹配
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery("spring框架","name","description").
                field("name",10).
                minimumShouldMatch("50%"));
        // 设置源字段过滤，第一个参数表示结果集包括哪些字段，第二个参数表示结果集不包括哪些字段
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel","price","timestamp"},new String[]{});
        // 向搜索请求对象设置搜索源
        searchRequest.source(searchSourceBuilder);
        // 执行搜索
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        // 搜索结果
        SearchHits hits = searchResponse.getHits();
        // 匹配到的总记录数
        TotalHits totalHits = hits.getTotalHits();
        // 得到匹配高的文档
        SearchHit[] searchHits = hits.getHits();
        // 日期格式化对象
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(SearchHit i : searchHits){
            // 文档的主键
            String id = i.getId();
            // 源文档内容
            Map<String,Object> map = i.getSourceAsMap();
            String name = (String) map.get("name");
            String studymodel = (String) map.get("studymodel");
            Double price = (Double) map.get("price");
            Date timestamp = dateFormat.parse((String) map.get("timestamp"));
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(price);
            System.out.println(timestamp);
        }
    }

    // 布尔查询
    @Test
    public void testBooleanQuery() throws IOException, ParseException {
        // 搜索请求对象
        SearchRequest searchRequest = new SearchRequest("xedu_course");
        // 搜索源构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        String[] ids = new String[]{"1","2"};
        // multiBuilder查询条件
        MultiMatchQueryBuilder multiBuilder = QueryBuilders.multiMatchQuery("spring框架", "name", "description").
                field("name", 10).
                minimumShouldMatch("50%");
        // term查询条件
        TermQueryBuilder termBuilder = QueryBuilders.termQuery("studymodel", "201001");
        // 布尔查询条件
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(multiBuilder);
        boolQueryBuilder.must(termBuilder);
        // 搜索方式
        // boolQuery，and 查询
        searchSourceBuilder.query(boolQueryBuilder);
        // 设置源字段过滤，第一个参数表示结果集包括哪些字段，第二个参数表示结果集不包括哪些字段
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel","price","timestamp"},new String[]{});
        // 向搜索请求对象设置搜索源
        searchRequest.source(searchSourceBuilder);
        // 执行搜索
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        // 搜索结果
        SearchHits hits = searchResponse.getHits();
        // 匹配到的总记录数
        TotalHits totalHits = hits.getTotalHits();
        // 得到匹配高的文档
        SearchHit[] searchHits = hits.getHits();
        // 日期格式化对象
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(SearchHit i : searchHits){
            // 文档的主键
            String id = i.getId();
            // 源文档内容
            Map<String,Object> map = i.getSourceAsMap();
            String name = (String) map.get("name");
            String studymodel = (String) map.get("studymodel");
            Double price = (Double) map.get("price");
            Date timestamp = dateFormat.parse((String) map.get("timestamp"));
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(price);
            System.out.println(timestamp);
        }
    }

    // 查询过滤器
    @Test
    public void testFilerQuery() throws IOException, ParseException {
        // 搜索请求对象
        SearchRequest searchRequest = new SearchRequest("xedu_course");
        // 搜索源构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        String[] ids = new String[]{"1","2"};
        // multiBuilder查询条件
        MultiMatchQueryBuilder multiBuilder = QueryBuilders.multiMatchQuery("spring框架", "name", "description").
                field("name", 10).
                minimumShouldMatch("50%");
        // 布尔查询条件
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(multiBuilder);
        // 设置查询过滤器
        boolQueryBuilder.filter(QueryBuilders.termQuery("studymodel","201001"));
        boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").lte(100).gte(90));
        // 搜索方式
        // boolQuery，and 查询
        searchSourceBuilder.query(boolQueryBuilder);
        // 设置源字段过滤，第一个参数表示结果集包括哪些字段，第二个参数表示结果集不包括哪些字段
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel","price","timestamp"},new String[]{});
        // 向搜索请求对象设置搜索源
        searchRequest.source(searchSourceBuilder);
        // 执行搜索
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        // 搜索结果
        SearchHits hits = searchResponse.getHits();
        // 匹配到的总记录数
        TotalHits totalHits = hits.getTotalHits();
        // 得到匹配高的文档
        SearchHit[] searchHits = hits.getHits();
        // 日期格式化对象
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(SearchHit i : searchHits){
            // 文档的主键
            String id = i.getId();
            // 源文档内容
            Map<String,Object> map = i.getSourceAsMap();
            String name = (String) map.get("name");
            String studymodel = (String) map.get("studymodel");
            Double price = (Double) map.get("price");
            Date timestamp = dateFormat.parse((String) map.get("timestamp"));
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(price);
            System.out.println(timestamp);
        }
    }

    // 查询排序
    @Test
    public void testQuerySort() throws IOException, ParseException {
        // 搜索请求对象
        SearchRequest searchRequest = new SearchRequest("xedu_course");
        // 搜索源构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 布尔查询条件
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 设置查询过滤器
        boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").lte(100).gte(0));
        // 搜索方式
        // boolQuery，and 查询
        searchSourceBuilder.query(boolQueryBuilder);
        // 设置源字段过滤，第一个参数表示结果集包括哪些字段，第二个参数表示结果集不包括哪些字段
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel","price","timestamp"},new String[]{});
        // 设置查询结果排序
        searchSourceBuilder.sort("studymodel", SortOrder.DESC);//学习模式降序
        searchSourceBuilder.sort("price",SortOrder.ASC);//价格升序
        // 向搜索请求对象设置搜索源
        searchRequest.source(searchSourceBuilder);
        // 执行搜索
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        // 搜索结果
        SearchHits hits = searchResponse.getHits();
        // 匹配到的总记录数
        TotalHits totalHits = hits.getTotalHits();
        // 得到匹配高的文档
        SearchHit[] searchHits = hits.getHits();
        // 日期格式化对象
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(SearchHit i : searchHits){
            // 文档的主键
            String id = i.getId();
            // 源文档内容
            Map<String,Object> map = i.getSourceAsMap();
            String name = (String) map.get("name");
            String studymodel = (String) map.get("studymodel");
            Double price = (Double) map.get("price");
            Date timestamp = dateFormat.parse((String) map.get("timestamp"));
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(price);
            System.out.println(timestamp);
        }
    }

    // 查询结果高亮显示
    @Test
    public void testHighLight() throws IOException, ParseException {
        // 搜索请求对象
        SearchRequest searchRequest = new SearchRequest("xedu_course");
        // 搜索源构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        String[] ids = new String[]{"1","2"};
        // 搜索方式
        // matchQuery，对文本进行分词搜索
        searchSourceBuilder.query(QueryBuilders.matchQuery("name","开发").minimumShouldMatch("80%"));
        // 设置源字段过滤，第一个参数表示结果集包括哪些字段，第二个参数表示结果集不包括哪些字段
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel","price","timestamp"},new String[]{});

        // 设置高亮对象
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<tag>");//设置高亮前缀
        highlightBuilder.postTags("</tag>");//设置高亮后缀
        highlightBuilder.fields().add(new HighlightBuilder.Field("name"));//设置高亮对象
        searchSourceBuilder.highlighter(highlightBuilder);//将高亮设置到搜索对象上

        // 向搜索请求对象设置搜索源
        searchRequest.source(searchSourceBuilder);
        // 执行搜索
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        // 搜索结果
        SearchHits hits = searchResponse.getHits();
        // 匹配到的总记录数
        TotalHits totalHits = hits.getTotalHits();
        // 得到匹配高的文档
        SearchHit[] searchHits = hits.getHits();
        // 日期格式化对象
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(SearchHit i : searchHits){
            // 文档的主键
            String id = i.getId();
            // 源文档内容
            Map<String,Object> map = i.getSourceAsMap();
            String name = (String) map.get("name");

            // 取出高亮设置内容字段
            Map<String, HighlightField> highlightFields = i.getHighlightFields();
            if(highlightFields != null){
                // 对于属性中的高亮内容
                HighlightField highlightField = highlightFields.get("name");
                if(highlightField != null){
                    Text[] texts = highlightField.getFragments();//获取高亮内容
                    StringBuffer sb = new StringBuffer();
                    // 拼接高亮内容
                    for(Text text : texts){
                        sb.append(text);
                    }
                    // 替换原name
                    name = sb.toString();
                }
            }
            String studymodel = (String) map.get("studymodel");
            Double price = (Double) map.get("price");
            Date timestamp = dateFormat.parse((String) map.get("timestamp"));
            System.out.println(name);
            System.out.println(studymodel);
            System.out.println(price);
            System.out.println(timestamp);
        }
    }
}
