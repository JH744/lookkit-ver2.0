<template>
  <div>로그인 요청 중입니다..</div>
</template>

<script setup>
import { onMounted } from "vue";
import axios from "axios";

// 카카오 인증 코드 처리 함수
onMounted(() => {
  const code = new URLSearchParams(window.location.search).get("code");
  if (code) {
    console.log("인증코드:", code);
    handleKakaoCallback(code);
  }
});

// 백엔드에 인증 코드 전송해 jwt 받아오기
async function handleKakaoCallback(code) {
  try {
    const response = await axios.post(
      "http://localhost:8081/api/v1/auth/callback",
      { code }
    );
    const { token } = response.data.data;
    localStorage.setItem("token", token);
    router.push("/main");
  } catch (error) {
    console.error("카카오 로그인 에러:", error);
  }
}
</script>

<style scoped></style>
