<template>
  <div class="tabContainer">
    <div class="tabActive">
      <div class="tabTextActive">비밀번호찾기</div>
    </div>
  </div>

  <form action="/auth/updatePwd" method="post" class="formContainer3">
    <span class="">새비밀번호입력</span>
    <input
      type="password"
      class="formInput"
      placeholder="새비밀번호를 입력해 주세요"
      name="newPassword"
      id="newPwd"
    />
    <span class="">새비밀번호 재입력</span>
    <input
      type="password"
      class="formInput"
      placeholder="새비밀번호를 재입력해 주세요"
      id="newPwd2"
    />
    <div id="ModifyBtn" class="buttonContainer">확인</div>
  </form>
</template>

<script setup></script>

<style scoped>
.formContainer3 {
  padding: calc(31.5px * 1.2) 10px 14px 10px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: flex-start;
  align-self: stretch;
  flex-shrink: 0;
  position: relative;
  gap: 16px;

  height: 400px;
  span {
    font-size: 18px;
  }
  input {
    width: 100%;
  }
  /* display: none; */
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
</style>
