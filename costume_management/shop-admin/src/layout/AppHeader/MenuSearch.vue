<template>
  <el-autocomplete
    v-model="state1"
    class="inline-input"
    prefix-icon="el-icon-search"
    placeholder="请输入内容"
    value-key="menu_name"
    :fetch-suggestions="querySearch"
    @select="handleSelect"
  >
    <template #default="{ item }">
      <router-link
        :to="item.menu_path"
        custom
        v-slot="{ href }"
      >
        <el-link
          class="menu-link"
          :href="href"
          :disabled="item.type === 1"
          :underline="false"
        >
          <span
            class="link-text"
            @click="handleLinkClick(item, $event)"
          >{{ item.menu_name }}</span>
        </el-link>
      </router-link>
    </template>
  </el-autocomplete>
</template>

<script lang="ts" setup>
import { onMounted, ref } from 'vue'

const state1 = ref('')

onMounted(() => {
  loadMenus()
})

const loadMenus = () => {
}

const querySearch = (queryString: string, cb: (val: any) => void) => {
}

const handleSelect = (item) => {
  console.log(item)
  return false
}

const handleLinkClick = (item, e) => {
  if (item.type === 1) {
    e.preventDefault()
    e.stopPropagation()
  }
}
</script>

<style lang="scss" scoped>
.is-disabled {
  color: #c0c4cc;
  cursor: not-allowed;
}

.menu-link, .link-text {
  display: block;
}
</style>
