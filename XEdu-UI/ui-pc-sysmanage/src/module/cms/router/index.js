import Home from '@/module/home/page/home.vue';
import Page_list from '@/module/cms/page/page_list.vue';
import Page_add from '@/module/cms/page/page_add.vue';
import Page_update from '@/module/cms/page/page_update.vue';

// 定义cms模块内部的url请求及其对应的vue page
export default [{
    path: '/cms',
    component: Home,
    name: 'CMS',
    hidden: false,
    children:[
      {path:'/cms/page/list',name:'页面列表',component: Page_list,hidden:false},
      {path:'/cms/page/add',name:'新增页面',component: Page_add,hidden:true},
      // :pageId将参数通过url进行传递
      {path:'/cms/page/update/:pageId',name:'更新页面',component: Page_update,hidden:true},
      ]
  }
]
