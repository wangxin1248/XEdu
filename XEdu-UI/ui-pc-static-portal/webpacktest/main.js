var {add} = require('./model')
var Vue = require('./vue.min')

// 实例化vue对象
// VM作为viewModel
var VM = new Vue({
    el:"#app",//表示当前vue接管的app的div区域
    data:{
        name:'鑫课堂',// 相当于Model
        num1:0,
        num2:0,
        result:0,
        url:'https://wangxin1248.github.io'
    },
    methods:{
        change:function () {
            this.result = add(Number.parseInt(this.num1),Number.parseInt(this.num2))
        }
    }
});