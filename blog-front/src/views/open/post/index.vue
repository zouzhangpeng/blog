<script lang="ts" setup>
import MdPreview from "@/components/MdPreview/index.vue"
</script>

<template>
  <div class="post-wrapper">
    <h1 class="post-title">
      这是一篇文章
    </h1>
    <div class="post-base-info">
      <a-space>
        <span>2023-10-29</span>
        <span>|</span>
        <span>0 评论</span>
        <span>|</span>
        <span>0 点赞</span>
        <span>|</span>
        <span>52 阅读</span>
      </a-space>
    </div>
    <md-preview/>
    <a-divider/>
    <!--    <div class="post-actions">-->
    <!--      <a-space>-->
    <!--        <svg-icon name="like" size="40"/>-->
    <!--        <svg-icon name="share" size="30"/>-->
    <!--      </a-space>-->
    <!--    </div>-->
  </div>
</template>

<style lang="scss" scoped>
.post-wrapper {
  width: 100%;
  height: 100%;
  background-color: #FFFFFF;
}

.post-title {
  font-size: 24px;
  color: #333;
  text-align: center;
  padding-top: 40px;
  word-break: break-word;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.25);
}

.post-base-info {
  font-size: 16px;
  padding: 20px 40px;
  color: #909399;
}

.post-actions {
  padding: 20px 0;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>