<template>
  <div>
    <el-form :model="pageForm" :rules="pageFormRules" label-width="80px" ref="pageForm">
      <!--   所属站点   -->
      <el-form-item label="所属站点" prop="siteId">
        <el-select v-model="pageForm.siteId" placeholder="请选择站点">
          <el-option
            v-for="item in siteList"
            :key="item.siteId"
            :label="item.siteName"
            :value="item.siteId">
          </el-option>
        </el-select>
      </el-form-item>
      <!--   所属模板   -->
      <el-form-item label="选择模版" prop="templateId">
        <el-select v-model="pageForm.templateId" placeholder="请选择">
          <el-option v-for="item in templateList"
                     :key="item.templateId"
                     :label="item.templateName"
                     :value="item.templateId">
          </el-option>
        </el-select>
      </el-form-item>
      <!--   页面名称   -->
      <el-form-item label="页面名称" prop="pageName">
        <el-input v-model="pageForm.pageName" auto-complete="off" ></el-input>
      </el-form-item>
      <!--   页面别名   -->
      <el-form-item label="页面别名" prop="pageAliase">
        <el-input v-model="pageForm.pageAliase" auto-complete="off" ></el-input>
      </el-form-item>
      <!--   访问路径   -->
      <el-form-item label="访问路径" prop="pageWebPath">
        <el-input v-model="pageForm.pageWebPath" auto-complete="off" ></el-input>
      </el-form-item>
      <!--   物理路径   -->
      <el-form-item label="物理路径" prop="pagePhysicalPath">
        <el-input v-model="pageForm.pagePhysicalPath" auto-complete="off" ></el-input>
      </el-form-item>
      <!--   dataUrl   -->
      <el-form-item label="数据路径" prop="pagePhysicalPath">
        <el-input v-model="pageForm.dataUrl" auto-complete="off" ></el-input>
      </el-form-item>
      <!--  页面类型   -->
      <el-form-item label="页面类型">
        <el-radio-group v-model="pageForm.pageType">
          <el-radio class="radio" label="0">静态</el-radio>
          <el-radio class="radio" label="1">动态</el-radio>
        </el-radio-group>
      </el-form-item>
      <!--  创建时间  -->
      <el-form-item label="创建时间">
        <el-date-picker type="datetime" placeholder="创建时间" v-model="pageForm.pageCreateTime">
        </el-date-picker>
      </el-form-item>
    </el-form>
    <!--  提交按钮  -->
    <div slot="footer" class="dialog‐footer">
      <el-button type="primary" @click="addSubmit" >提交</el-button>
      <el-button type="primary" @click="goBack" >返回</el-button>
    </div>
  </div>
</template>
<script>
/* 编写页面静态部分，即model及vm部分。 */
import * as cmsApi from '../api/cms'
export default {
  // 页面所需的静态数据
  data () {
    return {
      // 网站列表
      siteList: [],
      // 模板列表
      templateList: [],
      // 表单元素
      pageForm: {
        siteId: '',
        templateId: '',
        pageName: '',
        pageAliase: '',
        pageWebPath: '',
        pagePhysicalPath: '',
        dataUrl: '',
        pageType: '',
        pageCreateTime: new Date()
      },
      // 表单验证规则
      pageFormRules: {
        siteId: [
          {required: true, message: '请选择站点', trigger: 'blur'}
        ],
        templateId: [
          {required: true, message: '请选择模版', trigger: 'blur'}
        ],
        pageName: [
          {required: true, message: '请输入页面名称', trigger: 'blur'}
        ],
        pageWebPath: [
          {required: true, message: '请输入访问路径', trigger: 'blur'}
        ],
        pagePhysicalPath: [
          {required: true, message: '请输入物理路径', trigger: 'blur'}
        ]
      }
    }
  },
  // 页面中的方法
  methods: {
    // 提交表单
    addSubmit () {
      // 在提交表单前执行表单验证
      this.$refs.pageForm.validate((valid) => {
        if (valid) {
          // 让用户确认是否需要进行提交
          this.$confirm('此操作将新增一个页面, 是否继续?', '提示', {
          }).then(() => {
            // 执行新增页面后台api,传入pageForm作为json请求体
            cmsApi.page_add(this.pageForm).then(res => {
              if (res.success) {
                this.$message.success('新增成功')
                // 新增成功将页面表单进行清空
                this.$refs.pageForm.resetFields()
              } else if (res.message) {
                this.$message.error(res.message)
              } else {
                this.$message.error('新增失败')
              }
            })
          })
        }
      })
    },
    // 返回上一级页面
    goBack () {
      // 调用router方法来到达指定的页面
      this.$router.push({
        path: '/cms/page/list/',
        query: {
          page: this.$route.query.page, // 页面编号
          siteId: this.$route.query.siteId, // 站点id
          pageAliase: this.$route.query.pageAliase// 页面别名
        }
      })
    }
  },
  // 在DOM元素渲染生成完成后调用query方法直接填充数据
  created () {
    // 初始化站点列表
    this.siteList = [
      {
        siteId: '5a751fab6abb5044e0d19ea1',
        siteName: '门户主站'
      }
    ]
    // 模板列表
    this.templateList = [
      { templateId: '5a962b52b00ffc514038faf7', templateName: '首页' },
      { templateId: '5a962bf8b00ffc514038fafa', templateName: '轮播图' }
    ]
  }
}
</script>
<style>
  /*编写页面样式，不是必须*/
</style>
