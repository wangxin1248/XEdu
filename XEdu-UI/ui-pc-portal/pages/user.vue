<template>
    <div>
      用户管理导航，<nuxt-link :to="'/user/one'">one</nuxt-link>，<nuxt-link :to="'/user/101'">用户</nuxt-link>
      <nuxt-child/>
    </div>
</template>

<script>
    export default {
        layout:'test'
    }
</script>

<style scoped>

</style>
