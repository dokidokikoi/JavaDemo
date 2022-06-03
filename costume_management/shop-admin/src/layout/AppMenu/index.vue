<template>
  <el-menu
    router
    :collapse-transition="true"
    default-active="2"
    :unique-opened="true"
    class="el-menu-vertical-demo"
    background-color="#304156"
    text-color="#fff"
    :collapse="$store.state.isCollapse"
  >
    <el-menu-item index="/">
      <el-icon><setting /></el-icon>
      <span>首页</span>
    </el-menu-item>
    <el-sub-menu index="/product">
      <template #title>
        <el-icon><Folder /></el-icon>
        <span>商品</span>
      </template>
      <el-menu-item index="/product/product_list">
        <el-icon><WindPower /></el-icon>
        商品列表
      </el-menu-item>
      <el-menu-item index="/product/product_classify">
        <el-icon><WindPower /></el-icon>
        商品分类
      </el-menu-item>
      <el-menu-item index="/product/product_attr">
        <el-icon><WindPower /></el-icon>
        商品规格
      </el-menu-item>
      <el-menu-item index="/product/product_reply">
        <el-icon><WindPower /></el-icon>
        商品评论
      </el-menu-item>
    </el-sub-menu>
    <el-sub-menu index="/order">
      <template #title>
        <el-icon><Folder /></el-icon>
        <span>订单</span>
      </template>
      <el-menu-item index="/order/order_list">
        <el-icon><WindPower /></el-icon>
        订单列表
      </el-menu-item>
      <el-menu-item index="/order/order_offline">
        <el-icon><WindPower /></el-icon>
        订单列表
      </el-menu-item>
    </el-sub-menu>
    <el-menu-item
      index="/media"
    >
      <el-icon><document /></el-icon>
      <span>媒体</span>
    </el-menu-item>
    <el-sub-menu index="/permission">
      <template #title>
        <el-icon><Folder /></el-icon>
        <span>权限</span>
      </template>
      <el-menu-item index="/permission/permission_role">
        <el-icon><WindPower /></el-icon>
        角色
      </el-menu-item>
      <el-menu-item index="/permission/permission_admin">
        <el-icon><WindPower /></el-icon>
        管理员
      </el-menu-item>
      <el-menu-item index="/permission/permission_rule">
        <el-icon><WindPower /></el-icon>
        权限规则
      </el-menu-item>
    </el-sub-menu>
  </el-menu>
</template>

<script lang="ts" setup>
// import { ref } from 'vue'
// const isCollapse = ref(false)

</script>

<style lang='scss' scoped>
.el-menu {
  border-right: none;
}

.el-menu:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}

.el-menu.el-menu--collapse span{
  display: none !important;
}

::-webkit-scrollbar {
  display: none;
}
</style>
