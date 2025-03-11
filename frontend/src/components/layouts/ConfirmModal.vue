
<template>
  <Teleport to="body">
    <div v-if="modalStore.isModalVisible" class="modal-overlay">
      <div class="modal-content">
        <div class="header">
          <p>{{ modalStore.modalActionMessage }}</p>
          <img class="header-right" src="/images/close.png" @click="modalStore.closeModal"></img>
        </div>
        <div class="modal-inner">
          <img class="checkmark-icon" src="/images/warning.png" alt="checkmark" />
          <div class="confirm-message">{{ modalStore.questionMessage }}</div>
          <div class="warning-message">{{ modalStore.warningMessage }}</div>
          <div class="button-grid">
            <div class="confirm-button"  @click="handleConfirmAction">{{ modalStore.cofirmButton }}</div>
            <div class="close-button" @click="modalStore.closeModal">취소</div>

          </div>
        </div>
      </div>
    </div>

  </Teleport>
</template>

<script setup>
import { useConfirmModalStore } from '@/stores/modalStore'; // 스토어 가져오기
const modalStore = useConfirmModalStore(); // 스토어 인스턴스 사용

// 확인 버튼에 따른 동작 처리 함수
const handleConfirmAction = () => {
  if (modalStore.confirmCallback) {
    modalStore.confirmCallback(); // 콜백 함수 실행
  }
  modalStore.closeModal(); // 모달 닫기
};
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
  width: 420px;
  height: 350px;
}

.modal-inner {
  display: grid;
  align-self: center;
  justify-items: center;
  gap: 15px;
  width: 100%;
  height: 100%;
  place-content: center;
}

.confirm-message {
  color: #5c5c5c;
  font-family: "Inter-Regular", sans-serif;
  font-size: 20px;
  font-weight: bold;
}

.warning-message {
  color: #ff3d00;
  font-family: "Inter-Regular", sans-serif;
  font-size: 15px;
  margin-bottom: 15px;
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
  gap: 270px;
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
  background: white;
  border-radius: 35px;
  border: 1px solid #555353;
  width: 120px;
  height: 39.78px;
  color: black;
  font-family: "Inter-SemiBold", sans-serif;
  font-size: 15px;
  font-weight: 600;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
}

.button-grid {
  display: flex;
  gap: 5px;
}

.confirm-button {
  background: #0d1134;
  border-radius: 35px;
  width: 120px;
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
  