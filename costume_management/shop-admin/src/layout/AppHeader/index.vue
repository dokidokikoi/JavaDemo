<template>
  <el-space size="large">
    <ToggleSidebar />
    <Breadcrumb />
  </el-space>
  <el-space size="large">
    <MenuSearch />
    <FullScreen />
    <UserInfo />
  </el-space>
</template>

<script lang="ts" setup>
import ToggleSidebar from './ToggleSidebar.vue'
import Breadcrumb from './Breadcrumb.vue'

import MenuSearch from './MenuSearch.vue'
import FullScreen from './FullScreen.vue'
import UserInfo from './UserInfo.vue'

</script>

<style lang="scss" scoped>
</style>
