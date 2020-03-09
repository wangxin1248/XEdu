import http from './../../../base/api/public'
import querystring from 'querystring'
let sysConfig = require('@/../config/sysConfig')
let apiUrl = sysConfig.xApiUrlPre;

// 定义请求page list的方法
export const page_list = (page,size,params) => {
  // 调用get请求，拼接对应的url，将请求到的json返回，添加apiUrl解决跨域访问问题
  return http.requestQuickGet(apiUrl+'/cms/page/list/'+page+'/'+size)
}
