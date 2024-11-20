<template>
  <div class="welcome-header">
    <span class="welcome-message">{{ username }}님 환영합니다</span>
    <div class="profile-options">
      <button @click="openPwCheck">
        <img
          class="icon-settings"
          src="/images/setting.png"
          alt="설정 아이콘"
        /><span>개인정보 변경</span>
      </button>
    </div>
  </div>
</template>

<script setup>
import { computed } from "vue";
import { useAuthStore } from "@/stores/authStore";

const authStore = useAuthStore();
const username = computed(() => authStore.user.username);

const emit = defineEmits(["open-pw-check"]);
const openPwCheck = () => {
  emit("open-pw-check");
};
</script>
<style scoped>
.welcome-header {
  padding: 18px;
  background-color: #ffffff;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border: 1px solid #555353;
  color: #555353;
}

.welcome-message {
  font-size: 20px;
  color: #555;
  margin-left: 15px;
}

.profile-options {
  display: inline-grid;
  justify-items: center;
  align-items: center;
  gap: 8px;
}

.profile-options span {
  font-size: 13px;
}

.profile-options:hover {
  color: #000;
}

.icon-settings {
  width: 25px;
  height: 25px;
  display: grid;
  justify-items: center;
  gap: 6px;
  width: max-content;
  margin: auto;
}

.profile-options button {
  background-color: #ffffff; /* 버튼 배경색을 흰색으로 설정 */
  border: none; /* 필요에 따라 경계 제거 */
  padding: 5px 10px; /* 여백 조정 */
  cursor: pointer;
}
</style>
