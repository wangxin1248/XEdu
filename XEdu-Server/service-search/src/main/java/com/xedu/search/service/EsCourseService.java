package com.xedu.search.service;

import com.xedu.framework.domain.course.CoursePub;
import com.xedu.framework.domain.course.TeachplanMediaPub;
import com.xedu.framework.domain.search.CourseSearchParam;
import com.xedu.framework.exception.ExceptionCast;
import com.xedu.framework.model.response.CommonCode;
import com.xedu.framework.model.response.QueryResponseResult;
import com.xedu.framework.model.response.QueryResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/4/18 20:37.
 * @Description:
 */
@Service
public class EsCourseService {
    // 从配置文件中获取所需信息
    @Value("${xedu.elasticsearch.course.index}")
    private String index;
    @Value("${xedu.elasticsearch.course.source_field}")
    private String source_field;
    @Value("${xedu.elasticsearch.media.index}")
    private String media_index;
    @Value("${xedu.elasticsearch.media.source_field}")
    private String media_source_field;
    // ES查询客户端
    @Autowired
    RestHighLevelClient restHighLevelClient;

    /**
     * 实现课程搜索功能
     * @param page 当前页码
     * @param size 每页数量
     * @param courseSearchParam 查询对象
     * @return QueryResponseResult
     */
    public QueryResponseResult<CoursePub> list(int page, int size, CourseSearchParam courseSearchParam) throws IOException {
        // 判断查询对象是否为null
        if(courseSearchParam == null){
            courseSearchParam = new CourseSearchParam();
        }
        // 创建搜索请求对象
        SearchRequest searchRequest = new SearchRequest(index);
        // 创建搜索源对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 设置源字段过滤
        String[] sourse_strings = source_field.split(",");//过滤的源字段为string数组
        searchSourceBuilder.fetchSource(sourse_strings,new String[]{});
        // 创建bool查询对象
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        // 根据关键字来执行搜索
        if(StringUtils.isNoneEmpty(courseSearchParam.getKeyword())){
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(courseSearchParam.getKeyword(), "name", "description", "teachplan")
                    .minimumShouldMatch("70%")// 搜索最小占比
                    .field("name",10);// 提升name搜索比重
            // bool查询对象中添加关键字查询
            boolQueryBuilder.must(multiMatchQueryBuilder);
        }

        // 根据课程等级和难易级别进行搜索
        if(StringUtils.isNotEmpty(courseSearchParam.getMt())){
            // 根据一级等级
            boolQueryBuilder.filter(QueryBuilders.termQuery("mt",courseSearchParam.getMt()));
        }
        if(StringUtils.isNotEmpty(courseSearchParam.getSt())){
            // 根据二级等级
            boolQueryBuilder.filter(QueryBuilders.termQuery("st",courseSearchParam.getSt()));
        }
        if(StringUtils.isNotEmpty(courseSearchParam.getGrade())){
            // 根据课程难易程度
            boolQueryBuilder.filter(QueryBuilders.termQuery("grade",courseSearchParam.getGrade()));
        }

        // 设置分页参数
        if(page<=0){
            page = 1;
        }
        if(size<=0){
            size = 12;
        }
        int start = (page-1)*size;
        searchSourceBuilder.from(start);
        searchSourceBuilder.size(size);

        // 搜索源对象设置查询对象为bool查询对象
        searchSourceBuilder.query(boolQueryBuilder);

        // 高亮设置
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font class='eslight'>");
        highlightBuilder.postTags("</font>");
        // 设置高亮字段
        highlightBuilder.fields().add(new HighlightBuilder.Field("name"));
        searchSourceBuilder.highlighter(highlightBuilder);

        // 搜索请求对象设置搜索源对象
        searchRequest.source(searchSourceBuilder);
        // 执行搜索任务
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        // 创建查询结果对象
        QueryResult<CoursePub> queryResult = new QueryResult<>();
        List<CoursePub> list = new ArrayList<>();
        // 获取响应结果
        SearchHits hits = searchResponse.getHits();
        // 获取匹配的总记录数
        TotalHits totalHits = hits.getTotalHits();
        queryResult.setTotal(totalHits.value);
        // 获取匹配度高的数据
        SearchHit[] searchHits = hits.getHits();
        // 遍历所有数据封装对象
        for(SearchHit hit:searchHits){
            CoursePub coursePub = new CoursePub();
            // 获取原始数据文档
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            // 取出id
            String id = (String) sourceAsMap.get("id");
            coursePub.setId(id);
            // 取出name
            String name = (String) sourceAsMap.get("name");
            // 取出高亮字段内容
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if(highlightFields!=null){
                HighlightField nameField = highlightFields.get("name");
                if(nameField!=null){
                    Text[] fragments = nameField.getFragments();
                    StringBuffer stringBuffer = new StringBuffer();
                    for (Text str : fragments) {
                        stringBuffer.append(str.string());
                    }
                    name = stringBuffer.toString();
                }
            }
            coursePub.setName(name);
            // 取出pic
            String pic = (String) sourceAsMap.get("pic");
            coursePub.setPic(pic);
            // 取出charge
            String charge = (String) sourceAsMap.get("charge");
            coursePub.setCharge(charge);
            // 取出价格
            Double price = null;
            try {
                if(sourceAsMap.get("price")!=null ){
                    price = (Double) sourceAsMap.get("price");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            coursePub.setPrice(price);
            Double price_old = null;
            try {
                if(sourceAsMap.get("price_old")!=null ){
                    price_old = (Double) sourceAsMap.get("price_old");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            coursePub.setPrice_old(price_old);
            list.add(coursePub);
        }

        // 构造返回结果
        queryResult.setList(list);
        QueryResponseResult<CoursePub> queryResponseResult = new QueryResponseResult<>(CommonCode.SUCCESS,queryResult);
        return queryResponseResult;
    }

    /**
     * 根据id查询课程的详细信息
     * @param id
     * @return
     */
    public Map<String, CoursePub> getall(String id) {
        if(StringUtils.isEmpty(id)){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        // 创建搜索请求对象
        SearchRequest searchRequest = new SearchRequest(index);
        // 创建搜索源对象
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 设置查询条件为按照课程id查询
        sourceBuilder.query(QueryBuilders.termQuery("id",id));
        // 将搜索源对象绑定到搜索请求对象中
        searchRequest.source(sourceBuilder);
        // 搜索结果
        SearchResponse response = null;
        try {
            // 执行搜索
            response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response == null){
            ExceptionCast.cast(CommonCode.FAIL);
        }
        // 获取搜索结果
        SearchHits hits = response.getHits();
        // 获取搜索内容
        SearchHit[] searchHits = hits.getHits();
        // 返回结果map
        Map<String, CoursePub> map = new HashMap<>();
        // 遍历所有的搜索内容
        for(SearchHit hit : searchHits){
            // 获取查询到的内容
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            // 依次取出所需内容
            String courseId = (String) sourceAsMap.get("id");
            String name = (String) sourceAsMap.get("name");
            String grade = (String) sourceAsMap.get("grade");
            String charge = (String) sourceAsMap.get("charge");
            String pic = (String) sourceAsMap.get("pic");
            String description = (String) sourceAsMap.get("description");
            String teachplan = (String) sourceAsMap.get("teachplan");
            // 创建CoursePub对象
            CoursePub coursePub = new CoursePub();
            coursePub.setId(courseId);
            coursePub.setName(name);
            coursePub.setPic(pic);
            coursePub.setGrade(grade);
            coursePub.setTeachplan(teachplan);
            coursePub.setDescription(description);
            // 设置返回内容
            map.put(courseId,coursePub);
        }
        // 返回结果
        return map;
    }

    /**
     * 根据课程计划查询媒资信息
     * @param teacplanIds
     * @return
     */
    public QueryResponseResult<TeachplanMediaPub> getmedias(String[] teacplanIds) {
        // 创建搜索请求对象
        SearchRequest searchRequest = new SearchRequest(media_index);
        // 创建搜索源对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 设置源字段过滤
        String[] source_strings = media_source_field.split(",");//过滤的源字段为string数组
        searchSourceBuilder.fetchSource(source_strings,new String[]{});
        // 设置termsquery查询对象
        searchSourceBuilder.query(QueryBuilders.termsQuery("teachplan_id", teacplanIds));
        // 将搜索源对象绑定到搜索请求对象中
        searchRequest.source(searchSourceBuilder);
        // 搜索结果
        SearchResponse response = null;
        try {
            // 执行搜索
            response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response == null){
            ExceptionCast.cast(CommonCode.FAIL);
        }
        // 获取搜索结果
        SearchHits hits = response.getHits();
        SearchHit[] searchHits = hits.getHits();
        //数据列表
        List<TeachplanMediaPub> teachplanMediaPubList = new ArrayList<>();
        for (SearchHit hit : searchHits) {
            TeachplanMediaPub teachplanMediaPub =new TeachplanMediaPub();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            //取出课程计划媒资信息
            String courseid = (String) sourceAsMap.get("courseid");
            String media_id = (String) sourceAsMap.get("media_id");
            String media_url = (String) sourceAsMap.get("media_url");
            String teachplan_id = (String) sourceAsMap.get("teachplan_id");
            String media_fileoriginalname = (String) sourceAsMap.get("media_fileoriginalname");
            teachplanMediaPub.setCourseId(courseid);
            teachplanMediaPub.setMediaUrl(media_url);
            teachplanMediaPub.setMediaFileOriginalName(media_fileoriginalname);
            teachplanMediaPub.setMediaId(media_id);
            teachplanMediaPub.setTeachplanId(teachplan_id);
            //将数据加入列表
            teachplanMediaPubList.add(teachplanMediaPub);
        }
        //构建返回课程媒资信息对象
        QueryResult<TeachplanMediaPub> queryResult = new QueryResult<>();
        queryResult.setList(teachplanMediaPubList);
        QueryResponseResult<TeachplanMediaPub> queryResponseResult = new QueryResponseResult<TeachplanMediaPub>(CommonCode.SUCCESS,queryResult);
        return queryResponseResult;
    }
}
