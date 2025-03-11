<template>
  <div class="tabContainer">
    <div class="tabActive">
      <div class="tabTextActive">비밀번호찾기</div>
    </div>
    <div class="error-text-box">
      <span class="error-text"><slot></slot></span>
    </div>
  </div>
  <div class="formContainer">
    <div class="formLabel">아이디</div>
    <input
      class="formInput"
      placeholder="아이디를 입력해 주세요"
      v-model="userUuid"
    />
    <div class="formLabel">이메일</div>
    <input
      class="formInput"
      placeholder="이메일을 입력해 주세요"
      v-model="email"
    />
    <div
      id="authenticationBtn"
      class="buttonContainer"
      @click="handleAuthenticationUser"
    >
      인증받기
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

const userUuid = ref("");
const email = ref("");

// 이벤트 정의
const emit = defineEmits(["onEmailSender"]);
const handleAuthenticationUser = () => {
  console.log("에밋발사전");
  emit("onEmailSender", { userUuid: userUuid.value, email: email.value });
};
</script>

<style scoped>
.formContainer {
  padding: calc(20.5px * 1.2) 20px 24px 20px;
  display: flex;
  flex-direction: column;
  gap: 10px;
  align-items: flex-start;
  justify-content: flex-start;
  align-self: stretch;
  flex-shrink: 0;
  position: relative;
}

.formLabel {
  color: var(--wwwkurlycom-mine-shaft, #333333);
  text-align: left;
  font-family: "NotoSansKr-Thin", sans-serif;
  font-size: var(--font-size-14, 16px);
  line-height: var(--line-height-19, 19px);
  font-weight: var(--opacity-100, 400);
  position: relative;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}

.formInput::placeholder {
  font-size: 16px;
}

.formInput {
  background: var(--color-white-solid, #ffffff);
  border-radius: 4px;
  border-style: solid;
  border-color: var(--color-grey-87, #dddddd);
  border-width: calc(1px * 1.2);
  padding: 10px;
  display: flex;
  flex-direction: row;
  gap: 0px;
  align-items: flex-start;
  justify-content: center;
  align-self: stretch;
  flex-shrink: 0;
  height: calc(46px * 1.2);
  position: relative;
  overflow: hidden;
  font-size: 18px;
}

.buttonContainer {
  background: #dddddd;
  border-radius: 4px;
  display: flex;
  align-items: center;
  justify-content: center;
  align-self: stretch;
  flex-shrink: 0;
  height: 56px;
  position: relative;
  overflow: hidden;
  cursor: pointer;
  margin-top: 30px;
}

.tabContainer {
  background: var(--color-white-solid, #ffffff);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  align-self: stretch;
  flex-shrink: 0;
  position: relative;
}

.tabActive {
  background: rgba(255, 255, 255, 0);
  padding: calc(15px * 1.2) 45.96px 15px 45.95px;
  width: 380px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
  height: calc(48px * 1.2);
  position: relative;
  box-shadow: inset 0px -2px 0px 0px #0d1134;
  margin-top: 40px;
}

.tabTextActive {
  color: var(--wwwkurlycom-pigment-indigo, #0d1134);
  text-align: center;
  font-family: "NotoSansKr-Thin", sans-serif;
  font-size: var(--font-size-18, 18px);
  line-height: var(--font-size-18, 18px);
  font-weight: var(--opacity-100, 400);
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
}

.buttonContainer:hover {
  background: #0d1134;
  color: #ffffff;
  transition: all 5ms;
}

.error-text-box {
  margin-top: 14px;
  height: 14px;
}

.error-text {
  color: #ff294f;
  font-size: 14px;
}
</style>
