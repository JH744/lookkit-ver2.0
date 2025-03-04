<template>
  <div>
    <div class="loginContainer">
      <div class="loginBox">
        <div class="loginHeading">로그인</div>
        <div v-if="loginError" class="loginFailMessage">
          <span>아이디 또는 비밀번호를 재확인해주세요</span>
        </div>
        <div class="formContainer">
          <form class="formBox" @submit.prevent="handleLogin">
            <div class="formLabel formLabelId"><label>아이디</label></div>
            <input
              name="username"
              v-model="username"
              type="text"
              placeholder="아이디를 입력하세요"
              id="idInputBox"
              class="inputField inputFieldId"
            />
            <div class="formLabel formLabelPassword">
              <label>비밀번호</label>
            </div>
            <input
              :type="inputPwdType"
              name="password"
              v-model="password"
              placeholder="비밀번호 영문/특수문자/숫자 8~16자"
              id="pwdInputBox"
              class="inputField inputFieldPassword"
            />
            <div
              id="showPwdButton"
              class="showPwdButton"
              @click="isPasswordVisible = !isPasswordVisible"
              :class="{ hidden: !isPasswordVisible }"
            >
              <img src="@/assets/icons/eyesShow.svg" width="27" height="27" />
            </div>
            <div
              id="NoShowPwdButton"
              class="NoShowPwdButton"
              @click="isPasswordVisible = !isPasswordVisible"
              :class="{ hidden: isPasswordVisible }"
            >
              <img src="@/assets/icons/eyesNoShow.svg" width="31" height="31" />
            </div>
            <div class="rememberIdContainer">
              <input
                type="checkbox"
                class="checkbox"
                id="rememberId"
                v-model="isRememberId"
              />
              <label class="rememberIdLabel" for="rememberId"
                >아이디 저장</label
              >
            </div>
            <button type="submit" class="loginButton">로그인</button>
            <button type="button" class="signupLink" id="signupLink">
              <router-link to="/auth/signup">회원가입</router-link>
            </button>
            <div class="findIdPasswordContainer">
              <div class="findIdLink">
                <router-link to="/auth/findId">아이디 찾기</router-link>
              </div>
              <div class="verticalDivider"></div>
              <div class="findPasswordLink">
                <router-link to="/auth/findPwd">비밀번호 찾기</router-link>
              </div>
            </div>
          </form>
        </div>
        <div class="social-login-container">
          <!-- <div class="social-login-subtitle">
            <h5>간편로그인</h5>
          </div> -->
          <div class="social-login-wrap">
            <div class="social-login-box" @click="handleKakaoLogin">
              <img src="@/assets/logos/kakao_logo.svg" width="60" />
              <span>카카오</span>
            </div>
            <div class="social-login-box" @click="handleNaverLogin">
              <img src="@/assets/logos/naver_logo.svg" width="60" />
              <span>네이버</span>
            </div>
            <div class="social-login-box" @click="handleGoogleLogin">
              <img src="@/assets/logos/google_logo.svg" width="60" />
              <span>구글</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, onMounted, reactive } from "vue";
import { useAuthStore } from "@/stores/authStore";
import axios from "axios";
import { useRouter } from "vue-router";

const router = useRouter();
const username = ref("");
const password = ref("");
const isPasswordVisible = ref(false);
const inputPwdType = ref("password");
const loginError = ref(false);
const isRememberId = ref(false);
const authStore = useAuthStore();

onMounted(() => {
  const savedId = localStorage.getItem("savedId");
  if (savedId) {
    isRememberId.value = true; // 아이디저장 체크박스 체크활성화
    username.value = savedId;
  }
});

watch(
  () => isPasswordVisible.value,
  (value) => {
    inputPwdType.value = value ? "text" : "password";
  }
);

const handleLogin = async () => {
  try {
    const response = await axios
      .post(
        "http://localhost:8081/api/auth/login",
        // "/api/auth/login",
        {
          username: username.value,
          password: password.value,
        },
        { withCredentials: true }
      )
      .then((res) => {
        console.log("res", res.data);
        let userInfo = {
          username: res.data.username,
          userId: res.data.userId,
          role: res.data.roles[0].authority,
        };
        authStore.setAuthData(res.data.jwt, userInfo);
        // 아이디 저장 체크시 로그인완료된 아이디를 로컬스토리지에 저장
        if (isRememberId.value) {
          localStorage.setItem("savedId", username.value);
        } else {
          // 미체크시 기존저장된 아이디를 삭제
          localStorage.removeItem("savedId");
        }
      });
    router.push("/main");
  } catch (error) {
    loginError.value = true; // 에러 메시지 출력
    console.error("로그인 오류:", error);
  }
};

const handleKakaoLogin = () => {
  window.location.href = `http://localhost:8081/oauth2/authorization/kakao`; // 카카오 로그인 페이지로 리디렉션
};
const handleGoogleLogin = () => {
  window.location.href = `http://localhost:8081/oauth2/authorization/google`; // 구글 로그인 페이지로 리디렉션
};
const handleNaverLogin = () => {
  window.location.href = `http://localhost:8081/oauth2/authorization/naver`; // 네이버 로그인 페이지로 리디렉션
};
</script>

<style scoped>
.loginContainer,
.loginBox * {
  box-sizing: border-box;
}
.loginContainer {
  height: 85vh;
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 80px;
}
.loginBox {
  width: 460px;
  height: 522px;
  position: relative;
}
.loginHeading {
  color: #101010;
  text-align: center;
  font-family: "Inter-Regular", sans-serif;
  font-size: 30px;
  line-height: 30px;
  font-weight: 400;
  top: 0px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.formContainer {
  width: 460px;
  height: 420px;
  position: absolute;
  left: 0px;
  top: 68px;
}
.formBox {
  height: 420px;
  position: absolute;
  right: 0px;
  left: 0px;
  top: 0px;
}
.formLabel {
  color: #101010;
  text-align: left;
  font-family: "Inter-Regular", sans-serif;
  font-size: 22px;
  line-height: 19px;
  font-weight: 400;
  position: absolute;
  left: 1px;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}
.formLabelId {
  top: 20px;
  font-size: 18px;
}
.formLabelPassword {
  top: 110px;
  font-size: 18px;
}
.inputField {
  height: 46px;
  position: absolute;
  right: 0px;
  left: 0px;
  border: 1px solid #cdcdcd;
  border-radius: 3px;
  box-sizing: border-box;
  font-size: 20px;
  padding: 14px;
}

.inputField::placeholder {
  color: #999999;
  font-size: 18px;
}

.inputFieldId {
  top: 50px;
}
.inputFieldPassword {
  top: 140px;
}
.rememberIdContainer {
  height: 20px;
  position: absolute;
  left: 0px;
  top: 200px;
  display: flex;
  align-items: center;
}
.rememberIdLabel {
  color: #101010;
  text-align: left;
  font-family: "Inter-Regular", sans-serif;
  font-size: 16px;
  line-height: 18px;
  font-weight: 400;
  margin-left: 28px;
}
.checkbox {
  width: 20px;
  height: 20px;
  border: 1px solid #b8b8b9;
  border-radius: 3px;
  position: absolute;
}
.loginButton {
  background: #062236;
  border-radius: 54px;
  width: 460px;
  height: 54px;
  position: relative;
  left: calc(50% - 230px);
  top: 242px;
  color: #ffffff;
  text-align: center;
  font-family: "Inter-Regular", sans-serif;
  font-size: 17px;
  line-height: 54px;
  font-weight: 500;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
}

.signupLink {
  background: #ffffff;
  border-radius: 54px;
  width: 460px;
  height: 54px;
  position: absolute;
  left: calc(50% - 229px);
  top: 305px;
  border: 1px solid #b8b8b9;
  color: #2b2b2b;
  text-align: center;
  font-family: "Inter-Regular", sans-serif;
  font-size: 17px;
  line-height: 54px;
  font-weight: 500;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
}

.findIdPasswordContainer {
  color: #6a6a6a;
  text-align: center;
  font-family: "Inter-Regular", sans-serif;
  font-size: 14px;
  line-height: 14px;
  font-weight: 400;
  position: absolute;
  left: calc(50% - 89px);
  top: 380px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.findIdLink {
  margin-right: 10px;
  font-size: 16px;
  cursor: pointer;
}
.findPasswordLink {
  margin-left: 10px;
  font-size: 16px;
  cursor: pointer;
}
.verticalDivider {
  background: #aeaeae;
  width: 1px;
  height: 10px;
  margin: 0 5px;
}
.loginFailMessage {
  color: #ff294f;
  text-align: center;
  padding: 14px;
}
.NoShowPwdButton {
  position: absolute;
  top: 148px;
  right: 12px;

  cursor: pointer;
}
.showPwdButton {
  position: absolute;
  top: 150px;
  right: 14px;
  cursor: pointer;
}

.hidden {
  display: none;
}

.social-login-container {
  width: 70%;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  position: relative;
  top: 470px;
  gap: 10px;
}
.social-login-subtitle {
  text-align: center;
  color: #6a6a6a;
  font-weight: 400;
  padding: 5px;
}
.social-login-wrap {
  display: flex;
  justify-content: space-around;
}
.social-login-box {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
  font-size: 14px;
  color: #aeaeae;

  img,
  span {
    cursor: pointer;
  }
}
</style>
