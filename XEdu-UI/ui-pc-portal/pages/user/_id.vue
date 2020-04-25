<template>
    <div>
      id:{{id}},name:{{name}}
    </div>
</template>

<script>
    export default {
        layout:'test',
        // 在此处进行数据获取
        async asyncData(){
          var a =await new Promise(function (resolve, reject) {
            setTimeout(function () {
              console.log(1);
              resolve(1)
            },2000)
          });
          var b =await new Promise(function (resolve, reject) {
            setTimeout(function () {
              console.log(2);
              resolve(2)
            },1000)
          });
          return{
            name:'wangxin'
          }
        },
        data(){
          return {
            id:''
          }
        },
        methods:{
          getUser:function () {
            return name="123";
          },
          a(){
            return new Promise(function (resolve, reject) {
              setTimeout(function () {
                resolve(1)
              },2000)
            })
          },
          b(){
            return new Promise(function (resolve, reject) {
              setTimeout(function () {
                resolve(2)
              },1000)
            })
          }
        },
        mounted() {
          // 在钩子方法中获取从url传入的id
          this.id = this.$route.params.id;
          // this.getUser();
          // this.a().then(res=>{
          //   alert(res);
          //   console.log(res);
          // });
          // this.b().then(res=>{
          //   alert(res);
          //   console.log(res);
          // })
        }
    }
</script>

<style scoped>

</style>
