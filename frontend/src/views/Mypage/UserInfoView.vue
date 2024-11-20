<template>
  <div class="profile-container">
    <div class="side-and-main">
      <div class="second-grid">
        <form @submit.prevent="updateProfile">
          <div class="section-title">회원정보 관리</div>
          <div class="profile-management-content">
            <div class="info-group1">
              <label for="user-id">아이디</label>
              <input
                id="user-id"
                type="text"
                :value="userData.userUuid"
                readonly
              />
            </div>
            <div class="info-group2">
              <label for="password">비밀번호</label>
              <div class="inline">
                <input
                  id="password"
                  type="password"
                  v-model="userData.password"
                  readonly
                />
                <!-- 비밀번호 변경 버튼 -->
                <button
                  type="button"
                  class="btn-password-change"
                  @click="showPasswordChangeModal"
                >
                  변경하기
                </button>
              </div>
            </div>
            <div class="info-group1">
              <label for="name">이름</label>
              <input
                id="name"
                type="text"
                v-model="userData.userName"
                readonly
              />
            </div>
            <div class="info-group1">
              <label for="phone">휴대폰 번호</label>
              <input id="phone" type="text" v-model="userData.phone" />
            </div>
            <div class="info-group1">
              <label for="birthdate">생년월일</label>
              <input id="birthdate" type="date" v-model="userData.birthDate" />
            </div>
            <div class="info-group2">
              <label for="email">이메일 주소</label>
              <div class="inline">
                <input id="email" type="email" v-model="userData.email" />
                <!-- 이메일 중복 확인 버튼 -->
                <button
                  type="button"
                  class="btn-email-check"
                  @click="checkEmailDuplicate"
                >
                  중복확인
                </button>
              </div>
              <!-- 중복 확인 결과 표시 -->
              <span
                id="email-check-result"
                :class="{
                  'success-message': !emailCheckResult.isError,
                  'error-message': emailCheckResult.isError,
                }"
                >{{ emailCheckResult.message }}
              </span>
            </div>
            <div class="address-group">
              <label>주소 정보</label>
              <div class="inline">
                <input
                  type="text"
                  id="user_address"
                  v-model="userData.address"
                />
                <!-- 우편번호 검색 버튼 -->
                <button
                  type="button"
                  class="btn-postcode-search"
                  @click="searchPostcode"
                  id="addressSearch"
                >
                  우편번호 검색
                </button>
              </div>
              <input
                type="text"
                class="hidden-input"
                id="user_detail_address"
                v-model="userData.detailAddress"
              />
              <input
                type="text"
                id="user_total_address"
                name="address"
                style="display: none"
                :value="userData.address + ' ' + userData.detailAddress"
              />
            </div>
          </div>
          <!-- 저장 및 취소 버튼 -->
          <div class="button-group">
            <button type="submit" class="btn-save" id="updateBtn">
              수정완료
            </button>
          </div>
          <div class="withdrawal-group">
            <button type="button" class="btn-withdrawal" @click="deleteAccount">
              회원 탈퇴
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
  <!-- 비밀번호 변경 모달 -->
  <div v-if="showModal" id="pw-change-modal-container" class="modal-container">
    <div class="pw-change-modal">
      <span class="modal-title">비밀번호 변경</span>
      <span class="modal-subtitle">변경하실 비밀번호를 입력해주세요</span>
      <div class="input-container">
        <h3 class="modal-current-password"><span>현재 비밀번호</span></h3>
        <div class="input-group">
          <input
            type="password"
            id="current-password-change"
            v-model="currentPassword"
            placeholder="현재 비밀번호를 입력하세요"
          />
        </div>
        <h4 class="modal-new-password"><span>새 비밀번호</span></h4>
        <div class="input-group">
          <input
            type="password"
            id="new-password"
            v-model="newPassword"
            placeholder="새 비밀번호를 입력하세요"
          />
        </div>
        <h5 class="modal-confirm-password"><span>새 비밀번호 확인</span></h5>
        <div class="input-group">
          <input
            type="password"
            id="confirm-password"
            v-model="confirmPassword"
            placeholder="한번 더 입력하세요"
          />
        </div>
      </div>
      <div class="modal-footer">
        <button @click="updatePassword">비밀번호 변경하기</button>
        <button @click="closeModal">취소</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";

const userData = ref({
  userUuid: "",
  password: "",
  userName: "",
  phone: "",
  birthDate: "",
  email: "",
  address: "",
  detailAddress: "",
});

const showModal = ref(false);
const currentPassword = ref("");
const newPassword = ref("");
const confirmPassword = ref("");

onMounted(async () => {
  try {
    const response = await axios.get(
      "http://localhost:8081/api/v1/userinfo/10"
    );
    Object.assign(userData.value, response.data);
  } catch (error) {
    console.error("Error loading user data:", error);
  }
});

// 비밀번호 변경 모달 열기
const showPasswordChangeModal = () => {
  console.log("비밀번호 변경 버튼 클릭됨");
  showModal.value = true;
};

// 비밀번호 변경 모달 닫기
const closeModal = () => {
  showModal.value = false;
};

// 비밀번호 변경 로직
const updatePassword = async () => {
  if (newPassword.value !== confirmPassword.value) {
    alert("새 비밀번호가 일치하지 않습니다.");
    return;
  }
  try {
    await axios.post(
      "http://localhost:8081/api/v1/userinfo/10/change-password",
      {
        currentPassword: currentPassword.value,
        newPassword: newPassword.value,
        confirmNewPassword: confirmPassword.value,
      }
    );
    alert("비밀번호가 성공적으로 변경되었습니다.");
    closeModal();
    // 비밀번호 필드 초기화
    currentPassword.value = "";
    newPassword.value = "";
    confirmPassword.value = "";
  } catch (error) {
    console.error("Error updating password:", error);
    alert("비밀번호 변경에 실패했습니다.");
  }
};

// 프로필 업데이트 로직
const updateProfile = async () => {
  try {
    await axios.put(
      "http://localhost:8081/api/v1/userinfo/update",
      userData.value
    );
    alert("프로필이 성공적으로 업데이트되었습니다.");
  } catch (error) {
    console.error("Error updating profile:", error);
    alert("프로필 업데이트에 실패했습니다.");
  }
};

const emailCheckResult = ref({
  message: "",
  isError: false, // true이면 오류 메시지, false이면 성공 메시지
});

// 이메일 중복 확인 로직
const checkEmailDuplicate = async () => {
  if (!userData.value.email) {
    emailCheckResult.value.message = "이메일을 입력해주세요.";
    //emailCheckResult.value.isError = false;
    return;
  }

  console.log("이메일 중복 확인 요청: ", userData.value.email);

  try {
    const response = await axios.get(
      `http://localhost:8081/api/v1/userinfo/check-email`,
      {
        params: {
          email: userData.value.email,
        },
      }
    );
    console.log(response.data); // 응답 데이터 로그 확인
    if (response.data.exists === true) {
      emailCheckResult.value.message = "이미 사용 중인 이메일입니다.";
      //emailCheckResult.value.isError = false;
    } else {
      emailCheckResult.value.message = "사용 가능한 이메일입니다.";
      //emailCheckResult.value.isError = false;
    }
  } catch (error) {
    console.error("Error checking email:", error);
    emailCheckResult.value.message = "이메일 중복 확인에 실패했습니다.";
    //emailCheckResult.value.isError = false;
  }
};

// 우편번호 검색 로직 (예시, 실제 구현 필요)
const searchPostcode = () => {
  // 우편번호 검색 로직 구현
  alert("우편번호 검색 기능은 아직 구현되지 않았습니다.");
};

// 취소 버튼 동작
const cancelUpdate = () => {
  // 원하는 취소 동작 구현 (예: 초기 데이터로 되돌리기 또는 다른 페이지로 이동)
  alert("취소 버튼이 클릭되었습니다.");
};

// 탈퇴 요청 메서드
const deleteAccount = async () => {
  const userId = 10; // 여기에 실제 사용자 ID를 설정하세요
  const confirmation = confirm("정말 회원 탈퇴를 진행하시겠습니까?");
  if (confirmation) {
    try {
      await axios.delete(`http://localhost:8081/api/v1/userinfo/${userId}`);
      alert("회원 탈퇴가 완료되었습니다.");
      // 탈퇴 후 리다이렉트 동작 등 추가 가능
      window.location.href = "/"; // 홈 페이지로 리다이렉트
    } catch (error) {
      console.error("Error deleting account:", error);
      alert("회원 탈퇴에 실패했습니다.");
    }
  }
};
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
  width: 450px;
  flex: 1;
  padding: 30px;
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
  text-align: center;
  display: flex;
  justify-content: center;
  gap: 10px; /* 버튼 간 간격 조정 */
  margin-top: 0px;
}

/* Modal Styles */
.modal-container {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5); /* Overlay */
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.pw-change-modal {
  display: grid;
  background-color: white;
  width: 500px;
  height: 500px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
  border-radius: 3px;
}

.modal-title {
  width: 100%;
  height: 10px;
  font-size: 17px;
  padding-right: 52px;
  padding-left: 17px;
  color: #555353;
  letter-spacing: 0;
}

.modal-subtitle {
  font-size: 20px;
  text-align: center;
  border: 0;
  padding: 30px 0;
  height: 80px;
  line-height: 1;
  color: #555;
}

.input-group {
  margin-bottom: 50px;
}

.input-group label {
  font-size: 14px;
  margin-bottom: 8px;
  display: block;
}

.input-group input {
  height: 46px;
  display: block;
  overflow: hidden;
  position: absolute;
  margin-top: 0px;
  top: 0px;
  right: 0;
  bottom: 0;
  left: 0;
  padding: 10px 14px;
  line-height: 46px;
  white-space: nowrap;
  cursor: text;
  /* width: 350px; */
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 14px;
}

.modal-current-password {
  padding: 0 0 9px 1px;
  vertical-align: 0;
  font-size: 14px;
  line-height: 19px;
  text-align: left;
}

.modal-new-password {
  margin-top: 23px;
  padding: 0 0 9px 1px;
  vertical-align: 0;
  font-size: 14px;
  line-height: 19px;
  text-align: left;
}

.modal-confirm-password {
  margin-top: 23px;
  padding: 0 0 9px 1px;
  vertical-align: 0;
  font-size: 14px;
  line-height: 19px;
  text-align: left;
}

.input-container {
  min-width: 0;
  padding: 0 22px;
  width: 470px;
  justify-self: center;
}

.modal-footer {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.modal-footer button {
  padding: 12px 30px;
  background-color: #0d1134;
  color: white;
  border: none;
  border-radius: 30px;
  font-size: 15px;
  cursor: pointer;
  width: 186px;
  height: 35px;
}

.modal-footer button:hover {
  background-color: #1a1b5d;
}

.hidden-input {
  display: none !important;
}

.error-message {
  color: red;
  font-size: 14px;
}

.success-message {
  color: green;
  font-size: 14px;
}

.withdrawal-group {
  text-align: center;
  margin-top: 20px;
}

.btn-withdrawal {
  display: inline-block;
  padding: 10px 20px;
  color: #999;
  font-size: 13px;
  background-color: transparent;
  border: none;
  cursor: pointer;
  text-decoration: underline; /* 밑줄 추가 */
  width: auto;
}

.pw-change-moda h1 {
  overflow: hidden;
  text-overflow: ellipsis;
  line-height: 1.4;
  white-space: nowrap;
  position: static;
  margin-top: 12px;
  font-size: 17px;
}
</style>
