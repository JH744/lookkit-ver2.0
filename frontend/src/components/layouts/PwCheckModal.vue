<template>
  <div class="pw-check-section">
    <div class="section-header">
      <h2>비밀번호 재입력</h2>
      <img src="/images/close-button.png" @click="closeSection" class="close-button">
    </div>
    <form @submit.prevent="verifyPassword" class="password-check-form">
      <span class="section-subtitle">
        정보 보호를 위해 비밀번호를 입력해주세요
      </span>
      <label for="current-password" class="current-password">비밀번호</label>
      <input
        id="current-password"
        v-model="currentPassword"
        placeholder="비밀번호를 입력하세요"
        required
        type="password"
      />
      <button type="submit" class="confirm-password">확인하기</button>
      <div class="error-message" v-if="errorMessage">{{ errorMessage }}</div>
    </form>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router"; // 라우터 임포트
import axios from "axios";

const emit = defineEmits(["close"]);
const props = defineProps({
  userId: {
    type: Number,
    required: true,
  },
});

const currentPassword = ref("");
const errorMessage = ref("");
const router = useRouter(); // 라우터 객체 생성

const verifyPassword = async () => {
  console.log("verifyPassword 함수가 호출되었습니다.");
  errorMessage.value = "";

  if (!currentPassword.value.trim()) {
    errorMessage.value = "비밀번호를 입력해주세요.";
    return;
  }

  try {
    const response = await axios.post(
      "http://localhost:8081/api/v1/mypage/verifyPassword",
      {
        currentPassword: currentPassword.value,
      },
      {
        params: {
          id: props.userId,
        },
      }
    );

    if (response.data.success) {
      // 비밀번호 확인에 성공하면 모달 창을 닫고 UserInfoView로 이동
      emit("close");
      router.push("/mypage/updateInfo");
    } else {
      // 비밀번호가 틀렸을 때 알럿 창 표시
      errorMessage.value = response.data.message;
      alert("비밀번호가 틀렸습니다. 다시 입력해주세요.");
    }
  } catch (error) {
    console.error("에러 발생:", error);
    errorMessage.value = "비밀번호 확인 중 오류가 발생했습니다.";
  }
};

const closeSection = () => {
  emit("close");
};
</script>

<style scoped>
.outer-container {
  display: flex;
  justify-content: center; /* 수평 가운데 정렬 */
  align-items: center; /* 수직 가운데 정렬 */
  height: 100vh; /* 부모 요소가 화면 전체 높이를 차지하도록 설정 */
}

.pw-check-section {
  padding: 20px;
  border-radius: 10px;
  width: 120%;
  max-width: 550px; /* 원하는 너비 설정 */
  margin: auto; /* 추가적인 가운데 정렬 */
  margin-top: 25px;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.section-header h2 {
  margin: 0;
  margin-bottom: 15px;
  font-size: 20px;
  flex: 1;
  text-align: center;
}

.section-header button {
  background: none;
  border: none;
  font-size: 20px;
  cursor: pointer;
}

.section-subtitle {
  display: block;
  text-align: center;
  margin-top: 10px; /* 필요에 따라 간격 추가 */
  font-size: 15px; /* 폰트 크기 조정 */
}

.current-password {
  font-size: 15px;
  margin-left: 2.5%;
  margin-top: 25px;
}

.password-check-form {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.password-check-form input {
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ddd;
  border-radius: 5px;
  width: 95%;
  margin: auto;
}

.password-check-form button {
  padding: 10px;
  background-color: #0d1134;
  color: white;
  border: none;
  border-radius: 30px;
  cursor: pointer;
  width: 160px;
  margin: 0 auto;
  margin-top: 15px;
}

.password-check-form button:hover {
  background-color: #1a1b5d;
}

.error-message {
  color: red;
  font-size: 14px;
  margin: auto;
}

.close-button {
  width: 18px;
}

.close-button:hover {
  cursor: pointer;
}
</style>
