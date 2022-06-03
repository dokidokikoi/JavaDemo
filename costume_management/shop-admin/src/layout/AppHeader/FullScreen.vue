<template>
  <svg-icon
    name="full"
    class="mini"
    @click="toggleFullScreen()"
  />
</template>

<script lang="ts" setup>
const toggleFullScreen = () => {
  if (!document.fullscreenElement) {
    document.documentElement.requestFullscreen()
  } else {
    if (document.exitFullscreen) {
      document.exitFullscreen()
    }
  }
}

</script>

<style lang='scss' scoped>
.mini {
  width: 30px;
  height: 30px;

  &:hover {
    cursor: pointer;
  }
}

</style>
