<template>
  <div>
    <!--编写页面静态部分，即view部分-->
    <el-button type="primary" size="small" @click="query">查询</el-button>
    <!--  表格显示内容  -->
    <el-table
      :data="list"
      stripe
      style="width: 100%">
      <el-table-column
        type="index"
        width="60">
      </el-table-column>
      <el-table-column
        prop="pageName"
        label="页面名称"
        width="120">
      </el-table-column>
      <el-table-column
        prop="pageAliase"
        label="页面别名"
        width="120">
      </el-table-column>
      <el-table-column
        prop="pageType"
        label="页面类型"
        width="150">
      </el-table-column>
      <el-table-column
        prop="pageWebPath"
        label="访问路径"
        width="250">
      </el-table-column>
      <el-table-column
        prop="pagePhysicalPath"
        label="物理路径"
        width="250">
      </el-table-column>
      <el-table-column
        prop="pageCreateTime"
        label="创建时间"
        width="180">
      </el-table-column>
    </el-table>
    <!--  分页显示  -->
    <el-pagination
      background
      layout="prev, pager, next"
      :total="total"
      :page-size="this.params.size"
      :current-page="this.params.page"
      @current-change="changePage"
      style="float:right">
    </el-pagination>
  </div>
</template>
<script>
  /*编写页面静态部分，即model及vm部分。*/
  import * as cmsApi from '../api/cms'
  export default {
    // 页面所需的静态数据
    data() {
      return {
        list: [],
        total:0,
        params:{
          page:1,//页码
          size:10//每页显示个数
        }
      }
    },
    // 页面中的方法
    methods:{
      // 分页查询,page形参为当前页码
      changePage:function (page) {
        // 更新当前页码继续调用查询方法
        this.params.page = page;
        this.query();
      },
      // 查询页面内容
      query:function () {
        // 调用服务端的api接口请求数据，异步请求，采用then回调函数进行处理
        cmsApi.page_list(this.params.page,this.params.size,this.params).then(res=>{
          // 将请求到的数据进行保存
          this.list = res.queryResult.list;
          this.total = res.queryResult.total;
        })
      }
    },
    // 在DOM元素渲染生成完成后调用query方法直接填充数据
    mounted(){
      this.query();
    }
  }
</script>
<style>
  /*编写页面样式，不是必须*/
</style>
