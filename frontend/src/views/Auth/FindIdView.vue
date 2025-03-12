<template>
  <div v-if="isShowModal">
    <FindResultModal
      @onCloseModal="HandleShowModal"
      :resultMessage="resultMessage"
      ><p>아이디 찾기 완료</p>
    </FindResultModal>
  </div>
  <div class="findIdContainer">
    <div class="findIdTitle">
      <img src="@/assets/logos/Logo2.png" width="300px" />
    </div>
    <div class="findIdBackground">
      <div class="tabContainer">
        <div class="tabActive">
          <div class="tabTextActive">아이디찾기</div>
        </div>
      </div>
      <div class="error-text-box">
        <span v-if="isFail" class="error-text"
          >조회되는 아이디가 없습니다.</span
        >
      </div>
      <form
        action="/auth/findID"
        method="post"
        id="findIdForm"
        class="formContainer"
      >
        <div class="formLabel">이름</div>
        <input
          class="formInput"
          placeholder="이름을 입력해 주세요"
          v-model="userName"
        />
        <div class="formLabel">이메일</div>
        <input
          class="formInput"
          placeholder="이메일을 입력해 주세요"
          v-model="email"
        />
        <div id="findIdBtn" class="buttonContainer" @click="HandleFindId">
          확인
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import FindResultModal from "./components/FindResultModal.vue";
import { ref } from "vue";
import api from "@/api/axios";
import { useRouter } from "vue-router";
const router = useRouter();
const isShowModal = ref(false);
const userName = ref("");
const email = ref("");
const userId = ref("");
const isFail = ref("");
const resultMessage = ref("");

// 모달창 오픈 토글이벤트
const HandleShowModal = () => {
  isShowModal.value = !isShowModal.value;
};

const HandleFindId = async () => {
  try {
    const response = await api
      .post(
        "/api/users/find/id",
        {
          userName: userName.value,
          email: email.value,
        },
        { withCredentials: true }
      )
      .then((res) => {
        console.log("res", res);
        userId.value = res.data; //서버에서 받아온 아이디를 저장
        HandleShowModal(); // 모달창 오픈해 아이디를 표시
        isFail.value = false;
        resultMessage.value = `${userName.value}님의 아이디는 ${res.data}입니다.`; // 유저에게 보여줄 조회결과 텍스트 작성
      });
  } catch (error) {
    console.log("error", error);
    isFail.value = true;
  }
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
  margin-top: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
  height: calc(48px * 1.2);
  position: relative;
  box-shadow: inset 0px -2px 0px 0px #0d1134;
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
  padding: calc(16.5px * 1.2) 20px 24px 20px;
  display: flex;
  flex-direction: column;
  gap: var(--item-spacing-115, 3.5px);
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
  font-size: var(--font-size-14, 18px);
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
  margin-bottom: 18px;
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

.formInput::placeholder {
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
  margin-top: 10px;
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

.notice-label {
  display: none;
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
