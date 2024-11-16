<template>
  <header>
    <Header></Header>
    <div class="wrapper">
      <router-view></router-view>
      <SuccessModal />
      <ConfirmModal />
    </div>
  </header>
</template>

<script setup>
import SuccessModal from './components/layouts/SuccessModal.vue';
import ConfirmModal from './components/layouts/ConfirmModal.vue';
</script>

<style></style>
