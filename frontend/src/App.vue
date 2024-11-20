<template>
  <header>
    <Header v-if="!isAdminRoute"></Header>
    <div class="wrapper">
      <router-view></router-view>
      <SuccessModal />
      <ConfirmModal />
    </div>
  </header>
</template>

<script setup>
import { useRoute } from "vue-router";
import { computed } from "vue";
import SuccessModal from "./components/layouts/SuccessModal.vue";
import ConfirmModal from "./components/layouts/ConfirmModal.vue";
const route = useRoute();
const isAdminRoute = computed(() => route.path.startsWith("/admin"));
</script>

<style></style>
