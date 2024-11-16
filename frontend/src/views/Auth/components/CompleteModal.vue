<template>
  <div class="tabContainer">
    <div class="tabActive">
      <div class="tabTextActive">비밀번호찾기</div>
    </div>
  </div>

  <div class="formContainer4">
    <div>
      <p>비밀번호를 성공적으로 변경하였습니다.</p>
    </div>
    <div id="goToLoginBtn" class="buttonContainer">로그인하러가기</div>
  </div>
</template>

<script setup></script>

<style scoped>
.formContainer4 {
  /* display: none; */
  height: 450px;
  p {
    font-size: 20px;
    padding: 40px 20px;
  }
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
