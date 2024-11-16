<template>
  <div class="profile-container">
    <!-- 사이드바 추가 -->
    <div class="side-and-main">
      <!-- <th:block
        th:replace="~{common/fragments/sidebar :: sidebarFragment}"
      ></th:block> -->
      <div class="second-grid">
        <form action="/mypage/update" method="post" id="updateForm">
          <div class="section-title">회원정보 관리</div>
          <div class="profile-management-content">
            <div class="info-group1">
              <label for="user-id">아이디</label>
              <input id="user-id" type="text" readonly />
            </div>

            <div class="info-group2">
              <label for="password">비밀번호</label>
              <div class="inline">
                <input id="password" type="password" value="qwe123r" />
                <button type="button" class="btn-password-change">
                  변경하기
                </button>
              </div>
            </div>

            <div class="info-group1">
              <label for="name">이름</label>
              <input id="name" type="text" readonly />
              <div
                class="error"
                th:if="${#fields.hasErrors('userName')}"
                th:errors="*{userName}"
              >
                이름 오류
              </div>
            </div>

            <div class="info-group1">
              <label for="phone">휴대폰 번호</label>
              <input id="phone" type="text" />
              <!-- <div class="error"  th:if="${#fields.hasErrors('phone')}" -->
              <!-- th:errors="*{phone}" > 전화번호 오류 -->
              <!-- </div>  -->
            </div>

            <div class="info-group1">
              <label for="birthdate">생년월일</label>
              <input id="birthdate" type="date" />
              <div
                class="error"
                th:if="${#fields.hasErrors('birthDate')}"
                th:errors="*{birthDate}"
              >
                생년월일 오류
              </div>
            </div>

            <div class="info-group2">
              <label for="email">이메일 주소</label>
              <div class="inline">
                <input id="email" type="email" />
                <button type="button" class="btn-email-check">중복확인</button>
              </div>
              <span id="email-check-result"></span>
              <!-- <div
                class="error"
                th:if="${#fields.hasErrors('email')}"
                th:errors="*{email}"
              >
                > 이메일 오류
              </div> -->
            </div>

            <div class="address-group">
              <label>주소 정보</label>
              <div class="inline">
                <input type="text" id="user_address" />
                <button
                  type="button"
                  class="btn-postcode-search"
                  id="addressSearch"
                >
                  우편번호 검색
                </button>
              </div>
              <input
                type="text"
                class="hidden-input"
                id="user_detail_address"
              />
              <!--숨겨진 input에 기본주소+세부주소를 포함한 내용을 담아 서버로 전달-->
              <input
                type="text"
                id="user_total_address"
                name="address"
                style="display: none"
              />
            </div>
          </div>
          <div class="button-group">
            <button type="button" class="btn-cancel">취소하기</button>
            <button type="button" class="btn-save" id="updateBtn">
              저장하기
            </button>
          </div>
          <!-- 메시지 표시 부분을 alert으로 대체 -->
          <!-- <div th:if="${message}" th:text="${message}"></div> -->
          <!-- <script th:if="${message}" th:inline="javascript">
            /*<![CDATA[*/
            alert([[${message}]]);
            /*]]>*/
          </script> -->
        </form>
      </div>
    </div>
  </div>

  <!-- 비밀번호 변경 모달 -->
  <div id="pw-change-modal-container" class="modal-container">
    <div class="pw-change-modal">
      <span class="modal-title">비밀번호 변경</span>
      <span class="modal-subtitle">변경하실 비밀번호를 입력해주세요</span>

      <div class="input-container">
        <div class="input-group">
          <label for="current-password-change">현재 비밀번호</label>
          <input
            type="password"
            id="current-password-change"
            placeholder="현재 비밀번호를 입력하세요"
          />
        </div>

        <div class="input-group">
          <label for="new-password">새 비밀번호</label>
          <input
            type="password"
            id="new-password"
            placeholder="새 비밀번호를 입력하세요"
          />
        </div>

        <div class="input-group">
          <label for="confirm-password">새 비밀번호 확인</label>
          <input
            type="password"
            id="confirm-password"
            placeholder="한번 더 입력하세요"
          />
        </div>
      </div>

      <div class="modal-footer">
        <button id="updatePwBtn" @click="updateUserInfo">
          비밀번호 변경하기
        </button>
        <button id="cancleBtn">취소</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import axios from "axios";
import { ref, onMounted } from "vue";
const el = ref();

function updateUserInfo() {
  axios
    .post("/api/v1/user/updateinfo")
    .then((response) => {
      console.log("받아온 응답: ", response);
    })
    .catch(() => {});
}

// axios.get("/user?ID=12345")
//   .then(function (response) {
//     // 성공 핸들링
//     console.log(response);
//   })
//   .catch(function (error) {
//     // 에러 핸들링
//     console.log(error);
//   })
//   .finally(function () {
//     // 항상 실행되는 영역
//   });
</script>

<style scoped>
@charset "UTF-8";
.profile-container {
  display: grid;
  margin-top: 20px;
  width: 1100px;
  margin-left: auto;
  margin-right: auto;
  margin-bottom: 100px;
}

.second-grid {
  width: 100%;
  margin-left: 50px;
}

.welcome-header {
  padding: 20px;
  background-color: #ffffff;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border: 1px solid #555353;
  color: #555353;
}

.welcome-message {
  font-size: 23px;
  color: #555;
}

.profile-options {
  display: inline-grid;
  justify-items: center;
  align-items: center;
  gap: 3px;
}

.icon-settings {
  width: 25px;
  height: 25px;
}

.btn-change-info {
  font-size: 14px;
  color: #555;
  cursor: pointer;
}

.profile-management-content {
  margin-left: auto;
  margin-right: auto;
  width: 380px;
  flex: 1;
  padding: 40px;
  background-color: #fff;
}

.section-title {
  font-size: 24px;
  margin-top: 65px;
  border-bottom: 2px solid #727272;
  padding-bottom: 10px;
}

.info-group1 {
  display: grid;
  gap: 5px;
  align-items: center;
  margin-bottom: 20px;
}

.info-group2 {
  display: grid;
  gap: 5px;
  align-items: center;
  margin-bottom: 20px;
}

.inline {
  display: flex;
  gap: 12px;
}

.info-group1 label,
.info-group2 label {
  width: 150px;
  font-size: 16px;
  color: #333;
}

.info-group1 input,
.info-group2 input,
.address-group input {
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ddd;
  background-color: #f5f5f5;
  border-radius: 4px;
  width: -webkit-fill-available;
}

.info-group1 button,
.info-group2 button {
  padding: 10px 20px;
  background-color: #ffffff;
  border: 1px solid #ccc;
  border-radius: 25px;
  cursor: pointer;
  width: 150px;
}

.address-group button {
  padding: 10px 20px;
  background-color: #ffffff;
  border: 1px solid #ccc;
  border-radius: 25px;
  cursor: pointer;
  width: 200px;
}

.button-group {
  text-align: center;
}

.btn-save {
  background-color: #0d1134;
  color: white;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
  margin-left: 10px;
  border-radius: 25px;
  width: 125px;
}

.btn-cancel {
  background-color: white;
  border: 1px solid #ddd;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
  border-radius: 25px;
  width: 125px;
}

.address-group input {
  display: block;
  margin-bottom: 10px;
}

.btn-postcode-search {
  background-color: #ddd;
  color: #333;
  cursor: pointer;
  padding: 10px 20px;
  margin-bottom: 10px;
}

.outer-container {
  display: grid;
  margin-top: 20px;
  width: 1100px;
  margin-left: auto;
  margin-right: auto;
  margin-bottom: 100px;
}

.section-inquiries {
  border-bottom: 2px solid #727272;
}

.second-grid {
  width: 100%;
  margin-left: 50px;
}

.section-inquiries p:first-of-type {
  font-size: 24px;
  margin-top: 65px;
  margin-bottom: 0px;
}

.section-inquiries p:nth-of-type(2) {
  font-size: 13px;
  margin: 20px 0px;
}

.space-between {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 5px;
}

.inquiry-form,
.inquiry-form * {
  box-sizing: border-box;
}

.inquiry-form {
  height: auto;
  position: relative;
}
.form-content {
  margin-top: 50px;
}

.inquiry-content-title {
  font-size: 17px;
}

.button-group {
  display: flex;
  justify-content: center;
  gap: 7px;
  margin-top: 75px;
}

.btn-submit {
  background: #0d1134;
  color: #fff;
  border-radius: 35px;
  width: 184px;
  height: 44px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: "Inter-SemiBold", sans-serif;
  font-size: 15px;
  font-weight: 600;
  cursor: pointer;
}

.btn-cancel {
  background: #fff;
  color: #000;
  border: 1px solid #555353;
  border-radius: 35px;
  width: 184px;
  height: 44px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: "Inter-Medium", sans-serif;
  font-size: 15px;
  font-weight: 500;
  cursor: pointer;
}

.input-title {
  background: #fff;
  border: 1px solid #555553;
  padding: 10px;
  font-family: "Inter-Medium", sans-serif;
  font-size: 16px;
  width: 100%;
}

.inquiry-inline {
  display: inline-flex;
  margin-top: 40px;
  margin-bottom: 8px;
  align-items: center;
}

.input-content {
  background: #fff;
  border: 1px solid #555553;
  padding: 10px;
  font-family: "Inter-Medium", sans-serif;
  font-size: 16px;
  resize: none;
  height: 200px;
  width: 100%;
}

.error-message {
  color: #f44336;
  margin: 0px;
  font-size: 15px;
  margin-left: 15px;
  display: none;
}

.image-upload {
  display: flex;
  gap: 10px;
  margin-top: 7px;
  align-items: center;
}

.image-placeholder {
  background: #f0f0f0;
  width: 85px;
  height: 85px;
  border: 1px dashed #ccc;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 24px;
  color: #aaa;
  cursor: pointer;
}

.image-placeholder img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.upload-button {
  background: #fff;
  border: 1px solid #ccc;
  width: 150px;
  height: 85px;
  display: grid;
  align-content: center;
  justify-items: center;
  gap: 5px;
  cursor: pointer;
}

.upload-icon {
  width: 25px;
}

.upload-text {
  color: #555553;
  font-family: "Inter-Medium", sans-serif;
  font-size: 16px;
}

/* 유의사항 */
.notice {
  display: grid;
  align-items: center;
  margin-top: 80px;
}

.notice-icon {
  background: #b8b8b8;
  color: #fff;
  border-radius: 50%;
  width: 21px;
  height: 21px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: "Georgia-Bold", sans-serif;
  font-size: 15px;
  font-weight: 700;
  margin-right: 7px;
}

.notice-text {
  color: #818181;
  font-family: "Inter-Medium", sans-serif;
  font-size: 18px;
}

.notice-text ul {
  font-size: 13px;
  margin-top: 10px;
}

.notice-text ul li {
  margin-bottom: 5px;
}

.notice-icon-block {
  display: inline-flex;
  align-items: center;
}

.notice-icon-block span {
  color: #818181;
  font-size: 17px;
}

.error {
  color: red;
}

.inline {
  display: flex;
  align-items: center;
  gap: 10px;
}

.button-group {
  margin-top: 20px;
}

/* Modal Styles */
.modal-container {
  display: none;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5); /* Overlay */
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.pw-check-modal,
.pw-change-modal {
  display: grid;
  background-color: white;
  width: 530px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
  border-radius: 3px;
}

.modal-title {
  font-size: 16px;
  margin-bottom: 50px;
  margin-left: 3px;
  padding: 5px;
  color: #555353;
}

.modal-subtitle {
  font-size: 21px;
  margin-bottom: 30px;
  text-align: center;
  color: #555;
}

.input-group {
  margin-bottom: 20px;
}

.input-group label {
  font-size: 14px;
  margin-bottom: 8px;
  display: block;
}

.input-group input {
  width: 350px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 14px;
}

.input-container {
  justify-self: center;
}

.modal-footer {
  text-align: center;
  margin: 20px 0px;
}

.modal-footer button {
  padding: 12px 30px;
  background-color: #0d1134;
  color: white;
  border: none;
  border-radius: 30px;
  font-size: 15px;
  cursor: pointer;
  width: 200px;
  height: 42px;
}

.modal-footer button:hover {
  background-color: #1a1b5d;
}

.hidden-input {
  display: none !important;
}
</style>
