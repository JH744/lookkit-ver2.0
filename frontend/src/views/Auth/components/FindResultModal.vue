<template>
  <div class="modal-section">
    <div class="modal-container">
      <div class="btn-modal-close" @click="handleHideModal">
        <img src="@/assets/icons/closeBtn.svg" />
      </div>
      <div class="modal-wrapper">
        <div class="modal-title-box">
          <slot></slot>
        </div>
        <div class="modal-content-box">
          <div class="modal-content">
            <!-- <slot name="result"></slot> -->
            <span>{{ resultMessage }}</span>
          </div>
          <RouterLink to="/auth/login">
            <div class="btn-toLoginPage">로그인 페이지로</div>
          </RouterLink>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
defineProps({
  resultMessage: String,
});

// 이벤트 정의
const emit = defineEmits(["onCloseModal"]);
const handleHideModal = () => {
  emit("onCloseModal");
};
</script>

<style scoped>
.modal-section {
  width: 528px;
  margin: 100px auto;
}

.modal-container {
  z-index: 99;
  background-color: #ffffff;
  position: fixed;
  width: 528px;
  height: 578px;
  display: flex;
  justify-content: center;
  align-items: center;
  /* place-content: center; */
  border: 1px solid #e2e2e2;
  border-radius: 10px;

  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
  overflow: hidden;
}

.btn-modal-close {
  position: absolute;
  top: 10px;
  right: 10px;
  box-shadow: 0px;
  cursor: pointer;
}

.modal-wrapper {
  width: 412px;
  height: 264px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.modal-title-box {
  font-weight: 500;
  font-size: 20px;
  border-bottom: 1px solid gray;
  width: 412px;
  height: 69px;
  display: flex;
  justify-content: center;
  align-items: center;
  /* padding: 10px; */
}
.modal-content {
  height: 151px;
  width: 412px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.modal-content-box {
  height: 195px;
  width: 412px;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
}

.btn-toLoginPage {
  width: 265px;
  height: 44px;
  background-color: #0d1134;
  color: #ffffff;
  border-radius: 35px;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
