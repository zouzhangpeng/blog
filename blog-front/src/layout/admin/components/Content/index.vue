<template>
  <router-view v-slot="{ Component, route }">
    <transition name="fade" mode="out-in" appear>
      <div>
        <component
            :is="Component"
            :key="route.path"
        />
      </div>
    </transition>
  </router-view>
</template>

<script setup lang="ts">

</script>

<style scoped>

</style>