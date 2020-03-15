<template>
  <div>
    <!--编写页面静态部分，即view部分-->
    <!--  自定义查询选择表单  -->
    <el-form :model="params">
      <!--   下拉选择   -->
      <el-select v-model="params.siteId" placeholder="请选择站点">
        <el-option v-for="item in siteList"
                   :key="item.siteId"
                   :label="item.siteName"
                   :value="item.siteId">
        </el-option>
      </el-select>
      页面别名：<el-input v-model="params.pageAliase" style="width: 100px"></el-input>
      页面名称：<el-input v-model="params.pageName" style="width: 100px"></el-input>
      页面类型：<el-radio-group v-model="params.pageType">
        <el-radio class="radio" label="0">静态</el-radio>
        <el-radio class="radio" label="1">动态</el-radio>
      </el-radio-group>
      <el-button type="primary" v-on:click="query" size="small">查询</el-button>
      <!--   router-link是vue提供的路由功能，用于在页面生成路由链接，最终在html渲染后就是<a标签。   -->
      <router-link class="mui-tab-item"
                   :to="{path:'/cms/page/add/', query:{
                     page:this.params.page,
                     siteId:this.params.siteId,
                     pageAliase:this.params.pageAliase,
                   }}">
        <el-button type="primary" size="small">新增页面</el-button>
      </router-link>
    </el-form>
    <!--  表格显示内容  -->
    <el-table
      :data="list"
      stripe
      style="width: 100%">
      <el-table-column
        type="index"
        width="30">
      </el-table-column>
      <el-table-column
        prop="pageName"
        label="页面名称"
        width="180">
      </el-table-column>
      <el-table-column
        prop="pageAliase"
        label="页面别名"
        width="130">
      </el-table-column>
      <el-table-column
        prop="pageType"
        label="页面类型"
        width="100">
      </el-table-column>
      <el-table-column
        prop="pageWebPath"
        label="访问路径"
        width="180">
      </el-table-column>
      <el-table-column
        prop="pagePhysicalPath"
        label="物理路径"
        width="200">
      </el-table-column>
      <el-table-column
        prop="pageCreateTime"
        label="创建时间"
        width="180">
      </el-table-column>
      <el-table-column
        label="操作"
        width="150">
        <template slot-scope="page">
          <el-button
            size="small"
            type="text"
            @click="update(page.row.pageId)">
            编辑
          </el-button>
          <el-button
            size="small"
            type="text"
            @click="del(page.row.pageId)">
            删除
          </el-button>
          <el-button
            size="small"
            type="text"
            @click="preview(page.row.pageId)">
            预览
          </el-button>
        </template>
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
/* 编写页面静态部分，即model及vm部分。 */
import * as cmsApi from '../api/cms'
export default {
  // 页面所需的静态数据
  data () {
    return {
      siteList: [], // 站点列表
      list: [], // 列表内容
      total: 0, // 总的数据量
      params: {
        page: 1, // 页码
        size: 10, // 每页显示个数
        siteId: '', // 站点id
        pagename: '', // 页面名称
        pageAliase: '', // 页面别名
        pageType: '' // 页面类型
      }
    }
  },
  // 页面中的方法
  methods: {
    // 分页查询,page形参为当前页码
    changePage: function (page) {
      // 更新当前页码继续调用查询方法
      this.params.page = page
      this.query()
    },
    // 查询页面内容
    query: function () {
      // 调用服务端的api接口请求数据，异步请求，采用then回调函数进行处理
      cmsApi.page_list(this.params.page, this.params.size, this.params).then(res => {
        // 将请求到的数据进行保存
        this.list = res.queryResult.list
        this.total = res.queryResult.total
      })
    },
    // 更新方法，直接前端进行访问
    update: function (pageId) {
      this.$router.push({
        path: '/cms/page/update/' + pageId,
        query: {
          page: this.params.page,
          siteId: this.params.siteId,
          pageAliase: this.params.pageAliase
        }
      })
    },
    // 删除方法。delete为关键字不能使用
    del: function (pageId) {
      // 让用户确认是否需要进行删除
      this.$confirm('此操作将删除当前页面, 是否继续?', '提示', {
      }).then(() => {
        // 执行页面删除
        cmsApi.page_delete(pageId).then(res => {
          if (res.success) {
            // 删除成功，刷新当前页面，直接在请求query方法即可，因为参数以及保存在params中了，双向绑定
            this.$message.success('删除成功')
            this.query()
          } else {
            this.$message.error('删除失败')
          }
        })
      }).catch(() => {
        this.$message.info('已取消删除')
      })
    },
    // 预览方法
    preview: function (pageId) {
      window.open('http://localhost/cms/preview/' + pageId)
    }
  },
  // 钩子方法，在页面加载前执行
  created () {
    // 将路由传递过来的值进行获取并赋值到指定的对象中
    // || 表示前面为true返回前面，否则返回后面
    this.params.page = Number.parseInt(this.$route.query.page || 1)
    this.params.siteId = this.$route.query.siteId || ''
    this.params.pageAliase = this.$route.query.pageAliase || ''
  },
  // 钩子方法，在DOM元素渲染生成完成后调用query方法直接填充数据
  mounted () {
    // 调用query查询数据
    this.query()
    // 填充sitelist数据
    this.siteList = [
      {
        siteId: '5a751fab6abb5044e0d19ea1',
        siteName: '门户主站'
      }
    ]
  }
}
</script>
<style>
  /*编写页面样式，不是必须*/
</style>
