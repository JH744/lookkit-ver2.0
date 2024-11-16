<template>
  <div class="modal-container">
    <div class="pw-change-modal">
      <span class="modal-title">비밀번호 변경</span>
      <span class="modal-subtitle">변경하실 비밀번호를 입력해주세요</span>

      <div class="input-container">
        <div class="input-group">
          <label for="current-password-change">현재 비밀번호</label>
          <input
            type="password"
            id="current-password-change"
            placeholder="현재 비밀번호를 입력하세요"
            v-model="currentPassword"
          />
        </div>

        <div class="input-group">
          <label for="new-password">새 비밀번호</label>
          <input
            type="password"
            id="new-password"
            placeholder="새 비밀번호를 입력하세요"
            v-model="newPassword"
          />
        </div>

        <div class="input-group">
          <label for="confirm-password">새 비밀번호 확인</label>
          <input
            type="password"
            id="confirm-password"
            placeholder="한번 더 입력하세요"
            v-model="confirmPassword"
          />
        </div>
      </div>

      <div class="modalfooter">
        <button @click="submitPasswordChange">비밀번호 변경하기</button>
        <button @click="closeModal">취소</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "PasswordChangeModal",
  data() {
    return {
      currentPassword: "",
      newPassword: "",
      confirmPassword: "",
    };
  },
  methods: {
    submitPasswordChange() {
      if (this.newPassword !== this.confirmPassword) {
        alert("새 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
        return;
      }
      if (!this.currentPassword || !this.newPassword) {
        alert("모든 필드를 입력해주세요.");
        return;
      }
      // 실제 비밀번호 변경 로직 (예: API 호출)
      this.$emit("submit", this.newPassword);
    },
    closeModal() {
      this.$emit("close");
    },
  },
};
</script>

<style scoped>
.modal-container {
  display: flex;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5); /* Overlay */
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.pw-change-modal {
  background-color: white;
  width: 530px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
  border-radius: 3px;
  padding: 20px;
}

.modal-title {
  font-size: 16px;
  margin-bottom: 10px;
  color: #555353;
}

.modal-subtitle {
  font-size: 21px;
  margin-bottom: 20px;
  text-align: center;
  color: #555;
}

.input-group {
  margin-bottom: 15px;
}

.input-group label {
  font-size: 14px;
  margin-bottom: 5px;
  display: block;
}

.input-group input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.modalfooter {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.modalfooter button {
  padding: 10px 20px;
  background-color: #0d1134;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.modalfooter button:hover {
  background-color: #1a1b5d;
}
</style>
