<template>
  <div class="tabContainer">
    <div class="tabActive">
      <div class="tabTextActive">비밀번호찾기</div>
    </div>
  </div>

  <div class="formContainer2">
    <input class="formInput" placeholder="인증번호 입력" v-model="inputCode" />
    <div style="margin: 15px">
      <span class="verificationNotice"
        >*3분 이내로 인증번호 6자리를 입력해주세요.</span
      >
      <!-- 인증번호를 재입력해주세요. -->
    </div>
    <div class="error-text-box">
      <span v-if="isFail" class="error-text"><slot></slot></span>
    </div>
    <div id="authenticationOKBtn" class="buttonContainer" @click="verifyCode">
      확인
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

const props = defineProps({
  verificationCode: String,
});

const inputCode = ref(""); //사용자 입력 인증코드

const emit = defineEmits(["completeAuthentication"]);
const completeAuthentication = () => {
  console.log("에밋발사전");
  console.log("verificationCode", props.verificationCode);
  emit("completeAuthentication");
};

/**인증코드 일치 확인 매소드 */
const verifyCode = () => {
  if (props.verificationCode == inputCode.value) {
    alert("일치");
    completeAuthentication(); // emit 메소드 호출
  } else {
    alert("불일치합니다.");
  }
};
</script>

<style scoped>
.formContainer2 {
  padding: calc(31.5px * 1.2) 20px 24px 20px;
  display: flex;
  flex-direction: column;
  gap: 20px;
  align-items: flex-start;
  justify-content: flex-start;
  align-self: stretch;
  flex-shrink: 0;
  position: relative;
  /* display: none; */

  input {
    width: 100%;
  }
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
}

.buttonContainer:hover {
  background: #0d1134;
  color: #ffffff;
  transition: all 5ms;
}
.tabContainer {
  background: var(--color-white-solid, #ffffff);
  display: flex;
  flex-direction: row;
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

.error-text-box {
  margin-top: 14px;
  height: 14px;
}

.error-text {
  color: #ff294f;
  font-size: 14px;
}
</style>
