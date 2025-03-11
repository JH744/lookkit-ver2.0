
<template>
  <Teleport to="body">
    <div v-if="modalStore.isModalVisible" class="modal-overlay">
      <div class="modal-content">
        <div class="header">
          <p>{{ modalStore.modalAction }}</p>
          <img class="header-right" src="/images/close.png" @click="modalStore.closeModal"></img>
        </div>
        <div class="modal-inner">
          <img class="checkmark-icon" src="/images/success.png" alt="checkmark" />
          <div class="message">{{ modalStore.message }}</div>
          <div class="close-button" @click="modalStore.closeModal">닫기</div>

        </div>
      </div>
    </div>

  </Teleport>
</template>

<script setup>
import { useModalStore } from '@/stores/modalStore'; // 스토어 가져오기
const modalStore = useModalStore(); // 스토어 인스턴스 사용
</script>
  
  
<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 9999;
}

.modal-content {
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
  min-width: 300px;
  text-align: center;
  width: 400px;
  height: 320px;
}

.modal-inner {
  display: grid;
  align-self: center;
  justify-items: center;
  gap: 25px;
  width: 100%;
  height: 100%;
  place-content: center;
}

.message {
  color: #000000;
  font-family: "Inter-Regular", sans-serif;
  font-size: 15px;
  font-weight: 400;
}

.checkmark-icon {
  width: 86px;
  height: 86px;
  object-fit: cover;
}

.header {
  color: #555555;
  font-family: "Inter-Medium", sans-serif;
  font-size: 15px;
  font-weight: 500;
  display: flex;
  align-items: center;
  justify-content: space-around;
  gap: 250px;
  margin-top: 20px;
}

.header-right {
  cursor: pointer;
}

.close-icon {
  width: 2.27%;
  height: 3.18%;
  position: absolute;
  right: 4.33%;
  top: 6%;
  overflow: visible;
  cursor: pointer;
}

.close-button {
  background: #0d1134;
  border-radius: 35px;
  width: 166.9px;
  height: 39.78px;
  color: #ffffff;
  font-family: "Inter-SemiBold", sans-serif;
  font-size: 15px;
  font-weight: 600;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
}
</style>
  