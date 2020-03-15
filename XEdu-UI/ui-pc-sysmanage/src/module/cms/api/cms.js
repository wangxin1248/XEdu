import http from './../../../base/api/public'
import querystring from 'querystring'
let sysConfig = require('@/../config/sysConfig')
let apiUrl = sysConfig.xApiUrlPre;

// 该页面是与后端服务器进行请求的接口定义

// 定义请求page list的方法
export const page_list = (page,size,params) => {
  // 拼接请求参数
  let query = querystring.stringify(params)
  // 调用get请求，拼接对应的url，将请求到的json返回，添加apiUrl解决跨域访问问题
  return http.requestQuickGet(apiUrl+'/cms/page/list/'+page+'/'+size+'/?'+query)
}

// 定义新增页面的方法，post提交，将params作为body
export const page_add = params => {
  return http.requestPost(apiUrl+'/cms/page/add',params)
}

// 定义根据id来获取页面的方法。get请求
export const page_get = id => {
  return http.requestQuickGet(apiUrl+'/cms/page/get/'+id)
}

// 定义根据id和cmspage对象来更新cmspage对象方法，put请求
export const page_update = (id,params) => {
  return http.requestPut(apiUrl+'/cms/page/update/'+id,params)
}

// 定义根据id来删除cmspage对象方法，delete请求
export const page_delete = id =>{
  return http.requestDelete(apiUrl+'/cms/page/delete/'+id)
}
