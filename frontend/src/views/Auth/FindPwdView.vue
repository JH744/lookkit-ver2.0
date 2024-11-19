<template>
  <div v-if="isShowModal">
    <FindResultModal
      :resultMessage="resultMessage"
      @onCloseModal="HandleShowModal"
      ><p>비밀번호 변경완료</p>
    </FindResultModal>
  </div>
  <ConfirmModal
    v-if="isShowConfirmModal"
    :confirmMessage="confirmMessage"
    @onCloseModal="HandleShowConfirmModal"
  />
  <div class="findIdContainer">
    <div class="findIdTitle">
      <img src="@/assets/logos/Logo2.png" width="300px" />
    </div>
    <div class="findIdBackground">
      <FindPassword v-if="selectBox == 'box1'" @onEmailSender="sendEmail">
        <span>{{ errorMessage }}</span>
      </FindPassword>
      <AuthenticationEmail
        v-if="selectBox == 'box2'"
        :verification-code="verificationCode"
        @completeAuthentication="completeAuthentication"
      >
        <span>{{ errorMessage2 }}</span>
      </AuthenticationEmail>
      <UpdatePassword
        v-if="selectBox == 'box3'"
        :userUuid="userUuid"
        @onOpenModal="HandleShowModal"
      />
    </div>
  </div>
</template>

<script setup>
import AuthenticationEmail from "./components/AuthenticationEmail.vue";
import UpdatePassword from "./components/UpdatePassword.vue";
import FindPassword from "./components/FindPasswordForm.vue";
import { ref } from "vue";
import axios from "axios";
import FindResultModal from "./components/FindResultModal.vue";

const selectBox = ref("box1");
const verificationCode = ref("");
const userUuid = ref("");
const email = ref("");
const isShowModal = ref(false);
const resultMessage = ref("");
const errorMessage = ref("");
const errorMessage2 = ref("");
const isShowConfirmModal = ref(false);
const confirmMessage = ref("");
// 모달창 오픈 토글이벤트
const HandleShowModal = () => {
  console.log("HandleShowModal 호출");
  isShowModal.value = !isShowModal.value;
  resultMessage.value = "비밀번호를 성공적으로 변경하였습니다.";
};

/**이메일 인증 발송 */
const sendEmail = (data) => {
  console.log("userUuid", data.userUuid);
  console.log("email", data.email);
  userUuid.value = data.userUuid;
  email.value = data.email;
  let emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (data.userUuid.trim() === "") {
    errorMessage.value = "아이디를 입력해 주세요.";
    // alert(errorMessage.value);
    return;
  }
  if (!emailPattern.test(email.value)) {
    errorMessage.value = "올바른 이메일 형식을 입력해 주세요.";
    // alert(errorMessage.value);
    return;
  }
  errorMessage.value = "이메일 인증 요청중입니다....";
  sendEmailVerification();
};

/** 이메일 인증 요청 함수 */
const sendEmailVerification = async () => {
  await axios
    // .post("/api/mailsender", { userName: userName.value, email: email.value })
    .post("http://localhost:8081/api/mailsender", {
      userUuid: userUuid.value,
      email: email.value,
    })
    .then((res) => {
      console.log("인증코드", res.data);
      verificationCode.value = String(res.data);
      isShowConfirmModal.value = true;
      confirmMessage.value = "입력하신 이메일로 인증번호를 발송했습니다.";
      alert("입력하신 이메일로 인증번호를 발송했습니다.");
      alert("인증코드: " + verificationCode.value);
      selectBox.value = "box2"; // 박스 체인지
    })
    .catch((error) => {
      console.error("Error:", error);
      errorMessage2.value = "이메일 인증에 실패했습니다. 다시 시도해 주세요.";
    });
};

const completeAuthentication = () => {
  selectBox.value = "box3"; // 박스 체인지
};
</script>

<style scoped>
body {
  input:focus {
    outline: none;
  } /* outline 테두리 없애기 */
}

.findIdContainer,
.findIdContainer * {
  box-sizing: border-box;
}
.findIdContainer {
  flex-shrink: 0;
  height: calc(423.09px * 1.2);
  position: relative;
  margin-top: 100px;
}
.findIdTitle {
  color: var(--wwwkurlycom-mine-shaft, #333333);
  text-align: center;
  font-family: "Inter-Thin", sans-serif;
  font-size: calc(28px * 1.2);
  line-height: var(--line-height-322, 32.2px);
  font-weight: var(--opacity-100, 400);
  left: calc(50% - 68.5px);
  top: 0px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.findIdBackground {
  background: #ffffff;
  padding: calc(0px * 1.2) 10px 6px 10px;
  display: flex;
  flex-direction: column;
  gap: 0px;
  align-items: center;
  justify-content: flex-start;
  width: calc(400px * 1.2);
  max-width: var(--width-400, 400px);
  position: absolute;
  left: calc(50% - 200px);
  top: calc(50% - 148.45px);
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

.formContainer {
  padding: calc(25px * 1.2) 20px 24px 20px;
  display: flex;
  flex-direction: column;
  gap: 20px;
  align-items: flex-start;
  justify-content: flex-start;
  align-self: stretch;
  flex-shrink: 0;
  position: relative;
}

.formInput::placeholder {
  font-size: 16px;
}

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
.inputContainer {
  display: flex;
  flex-direction: column;
  gap: 0px;
  align-items: flex-start;
  justify-content: flex-start;
  flex: 1;
  position: relative;
  overflow: hidden;
}
.inputPlaceholder {
  color: var(--color-grey-46, #757575);
  text-align: left;
  font-family: var(
    --wwwkurlycom-semantic-input-font-family,
    "NotoSansKr-Thin",
    sans-serif
  );
  font-size: var(--wwwkurlycom-semantic-input-font-size, 16px);
  font-weight: var(--wwwkurlycom-semantic-input-font-weight, 100);
  position: relative;
  align-self: stretch;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}
.inputPlaceholderSecondary {
  color: var(--color-grey-46, #757575);
  text-align: left;
  font-family: "NotoSansKr-Thin", sans-serif;
  font-size: var(--font-size-18, 18px);
  font-weight: var(--opacity-100, 400);
  position: relative;
  align-self: stretch;
  display: flex;
  align-items: center;
  justify-content: flex-start;
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
.buttonText {
  color: #ffffff;
  text-align: center;
  font-family: "NotoSansKr-Thin", sans-serif;
  font-size: var(--font-size-18, 18px);
  line-height: var(--line-height-184, 18.4px);
  font-weight: var(--opacity-100, 400);
  position: relative;
  align-self: stretch;
  display: flex;
  align-items: center;
  justify-content: center;
}
.formNotice {
  position: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  width: 100%;
  font-size: 16px;
  margin-bottom: 30px;
}
</style>
