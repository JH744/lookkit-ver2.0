<template>
  <div class="singUpContainer">
    <div class="contentWrapper">
      <div class="titleHeader">
        <div class="title">회원가입</div>
        <div class="separator"></div>
      </div>
      <div class="formContainer">
        <form action="/auth/signUp" method="post" id="signUpForm">
          <div class="buttonContainer">
            <button type="submit" class="signupButton" id="signupButton">
              회원가입
            </button>
            <div class="cancelButton">취소하기</div>
          </div>
          <div class="inputContainer">
            <div class="label">아이디</div>
            <input
              id="userUuid"
              type="text"
              name="userUuid"
              class="inputField"
              placeholder="아이디를 입력하세요"
            />
            <button
              type="button"
              id="duplicateCheckButton"
              class="duplicateCheckButton"
            >
              중복확인
            </button>
            <label id="idCheckLabel" class="idCheckLabel">
              ✓ 사용가능한 아이디</label
            >
          </div>
          <div class="passwordContainer">
            <div class="passwordLabel">비밀번호</div>

            <input
              type="password"
              id="password"
              name="password"
              class="passwordInput"
              placeholder="비밀번호 영문/특수문자/숫자 8~16자"
            />

            <label id="pwCheckLabel" class="pwCheckLabel"> 비밀번호 체크</label>
          </div>
          <div class="nameContainer">
            <div class="nameLabel">이름</div>
            <input
              type="text"
              id="userName"
              name="userName"
              class="nameInput"
              placeholder="이름을 입력하세요"
            />
          </div>
          <div class="phoneContainer">
            <div class="phoneLabel">휴대폰 번호</div>
            <input
              type=""
              id="phone"
              name="phone"
              class="nameInput"
              placeholder="-없이 휴대폰 번호를 입력하세요"
            />
          </div>
          <div class="emailLabel">이메일 주소</div>
          <div class="emailContainer">
            <input
              type="email"
              id="email"
              name="email"
              class="emailInput"
              placeholder="이메일주소"
            />
            <label id="emailCheckLabel" class="emailCheckLabel">
              이메일 체크</label
            >
          </div>
          <div class="addressLabel">주소 정보</div>
          <div class="addressContainer">
            <div class="addrBox">
              <div id="addressSearch" class="postcodeInputBtn">주소검색</div>
              <input
                type="text"
                class="addressInput"
                id="user_address"
                name="user_address"
                placeholder="주소"
                readonly
              />
            </div>
            <input
              type="text"
              id="user_detail_address"
              name="user_detail_address"
              class="detailedAddressInput"
              placeholder="상세주소"
            />
            <input
              type="text"
              id="user_total_address"
              name="address"
              style="display: none"
            />
          </div>
          <div class="birthdateLabel">생년월일</div>
          <input
            type="date"
            id="birthDate"
            class="birthdateInput"
            name="birthDate"
            placeholder="생년월일 6자리를 입력하세요"
          />
          <div class="genderMaleContainer">
            <input
              type="radio"
              name="gender"
              class="hidden-input"
              value="M"
              id="genderInputMan"
            />
            <div class="genderMaleButton genderBtn checked-option">Man</div>
          </div>
          <div class="genderFemaleContainer">
            <input
              type="radio"
              name="gender"
              class="hidden-input"
              value="F"
              id="genderInputWoman"
            />
            <div class="genderFemaleButton genderBtn">Woman</div>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "SignUpView",
  setup() {},
  data() {},
};
</script>

<style scoped>
.singUpContainer,
.singUpContainer * {
  box-sizing: border-box;

  input {
    font-size: 20px;
    padding-left: 16px;
  }
}
.singUpContainer {
  height: 1218px;
  position: relative;
  display: flex;
  justify-content: center;
  margin-top: 100px;
  margin-bottom: 200px;
}
.contentWrapper {
  width: 1170px;
  height: 1218px;
  position: relative;
}
.titleHeader {
  width: 1170px;
  height: 47px;
}
.title {
  color: #363636;
  text-align: left;
  font-family: "Inter-Regular", sans-serif;
  font-size: 30px;
  font-weight: 400;
  display: flex;
  align-items: center;
  justify-content: center;
}
.separator {
  margin-top: -1px;
  border-style: solid;
  border-color: #000000;
  border-width: 1px 0 0 0;
  width: 1170px;
  height: 0px;
  position: absolute;
  left: 0px;
  top: 47px;
}
.formContainer {
  width: 499px;
  height: 1133px;
  position: absolute;
  left: 343px;
  top: 85px;
}
.buttonContainer {
  width: 499px;
  height: 100px;
  position: absolute;
  left: 0px;
  top: 1033px;
}
.signupButton {
  background: #0d1134;
  width: 499px;
  height: 44px;
  position: relative;
  top: -60px;
  cursor: pointer;
  border-radius: 50px;
  color: #ffffff;
  text-align: center;
  font-family: "Inter-SemiBold", sans-serif;
  font-size: 17px;
  font-weight: 600;
}
.signupButtonBg {
  background: #0d1134;
  border-radius: 35px;
  width: 499px;
  height: 44px;
  position: absolute;
  left: 0px;
  top: 0px;
  color: #ffffff;
  text-align: center;
  font-family: "Inter-SemiBold", sans-serif;
  font-size: 17px;
  font-weight: 600;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
}

.cancelButton {
  background: #ffffff;
  border-radius: 35px;
  border-style: solid;
  border-color: #555353;
  border-width: 1px;
  width: 499px;
  height: 44px;
  position: absolute;
  left: 0px;
  top: 0px;
  color: #000000;
  text-align: center;
  font-family: "Inter-SemiBold", sans-serif;
  font-size: 17px;
  font-weight: 600;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
}

.inputContainer {
  width: 498.62px;
  height: 86px;
  position: absolute;
  left: 0px;
  top: 0px;
}
.label {
  color: #000000;
  text-align: left;
  font-family: "Inter-Regular", sans-serif;
  font-size: 20px;
  font-weight: 400;
  position: absolute;
  left: 0px;
  top: 0px;
  width: 56px;
  height: 24px;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}
.duplicateCheckButton {
  background: #ffffff;
  border-radius: 35px;
  border-style: solid;
  border-color: #c5c5c5;
  border-width: 1px;
  width: 184.62px;
  height: 57px;
  position: absolute;
  left: 314px;
  top: 29px;
  color: #000000;
  font-family: "Inter-Medium", sans-serif;
  font-size: 18px;
  font-weight: 400;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
}

.duplicateCheckButton:hover {
  background-color: #0d1134;
  color: #ffffff;
  transition: all 5ms;
}
.inputField {
  background: #ffffff;
  border-style: solid;
  border-color: #555553;
  border-width: 1px;
  width: 293px;
  height: 57px;
  position: absolute;
  left: 0px;
  top: 29px;
}
.idCheckLabel {
  font-size: 14px;
  color: #ff294f;
  width: 293px;
  height: 57px;
  position: absolute;
  left: 5px;
  top: 93px;
  display: none;
}
.inputPlaceholder {
  color: #797979;
  text-align: left;
  font-family: "Inter-Medium", sans-serif;
  font-size: 20px;
  font-weight: 500;
  position: absolute;
  left: 20px;
  top: 46px;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}
.passwordContainer {
  width: 499px;
  height: 86px;
  position: absolute;
  left: 0px;
  top: 126px;
}
.passwordLabel {
  color: #000000;
  text-align: left;
  font-family: "Inter-Regular", sans-serif;
  font-size: 20px;
  font-weight: 400;
  position: absolute;
  left: 0px;
  top: 0px;
  width: 74px;
  height: 23px;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}
.passwordInput {
  background: #ffffff;
  border-style: solid;
  border-color: #555553;
  border-width: 1px;
  width: 499px;
  height: 57px;
  position: absolute;
  left: 0px;
  top: 29px;
}
.passwordPlaceholder {
  color: #797979;
  text-align: left;
  font-family: "Inter-Regular", sans-serif;
  font-size: 20px;
  line-height: 46px;
  font-weight: 400;
  position: absolute;
  left: 18px;
  top: 35px;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}
.nameContainer {
  width: 499px;
  height: 87px;
  position: absolute;
  left: 0px;
  top: 251px;
}
.nameLabel {
  color: #000000;
  text-align: left;
  font-family: "Inter-Regular", sans-serif;
  font-size: 20px;
  font-weight: 400;
  position: absolute;
  left: 0px;
  top: 0px;
  width: 37px;
  height: 24px;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}
.nameInput {
  background: #ffffff;
  border-style: solid;
  border-color: #555553;
  border-width: 1px;
  width: 499px;
  height: 57px;
  position: absolute;
  left: 0px;
  top: 30px;
}
.namePlaceholder {
  color: #797979;
  text-align: left;
  font-family: "Inter-Medium", sans-serif;
  font-size: 20px;
  font-weight: 500;
  position: absolute;
  left: 20px;
  top: 47px;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}
.phoneContainer {
  width: 499px;
  height: 87px;
  position: absolute;
  left: 0px;
  top: 377px;
}
.phoneLabel {
  color: #000000;
  text-align: left;
  font-family: "Inter-Regular", sans-serif;
  font-size: 20px;
  font-weight: 400;
  position: absolute;
  left: 0px;
  top: 0px;
  width: 98px;
  height: 23px;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}
.emailLabel {
  color: #000000;
  text-align: left;
  font-family: "Inter-Regular", sans-serif;
  font-size: 20px;
  font-weight: 400;
  position: absolute;
  left: 0px;
  top: 629px;
  width: 98px;
  height: 23px;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}
.emailContainer {
  width: 499px;
  height: 57px;
  position: relative;
}
.emailInput {
  background: #ffffff;
  border-style: solid;
  border-color: #555553;
  border-width: 1px;
  width: 499px;
  height: 57px;
  position: absolute;
  left: 0px;
  top: 659px;
}
.emailPlaceholder {
  color: #797979;
  text-align: left;
  font-family: "Inter-Medium", sans-serif;
  font-size: 20px;
  font-weight: 500;
  position: absolute;
  left: 20px;
  top: 676px;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}
.addressLabel {
  color: #000000;
  text-align: left;
  font-family: "Inter-Regular", sans-serif;
  font-size: 20px;
  font-weight: 400;
  position: absolute;
  left: 0px;
  top: 755px;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}
.addressContainer {
  width: 499px;
  height: 203px;
  position: static;
}
.postcodeInputBtn {
  background: #ffffff;
  border-style: solid;
  border-color: #c5c5c5;
  border-width: 1px;
  border-radius: 3px;
  width: 90px;
  height: 57px;
  position: absolute;
  right: 1px;
  top: 731px;
  cursor: pointer;
  text-align: center;
  align-content: center;
  color: #000000;
  font-family: "Inter-Medium", sans-serif;
  font-size: 18px;
  font-weight: 400;
}
.postcodeInputBtn:hover {
  background-color: #0d1134;
  color: #ffffff;
  transition: all 5ms;
}

.addressInput {
  background: #ffffff;
  border-style: solid;
  border-color: #555553;
  border-width: 1px;
  width: 400px;
  height: 57px;
  position: absolute;
  left: 0px;
  top: 731px;
}
.detailedAddressInput {
  background: #ffffff;
  border-style: solid;
  border-color: #555555;
  border-width: 1px;
  width: 499px;
  height: 57px;
  position: absolute;
  left: 0px;
  top: 867px;
}
.postcodeSearchText {
  color: #555553;
  text-align: left;
  font-family: "Inter-Medium", sans-serif;
  font-size: 20px;
  font-weight: 500;
  position: absolute;
  left: 242px;
  top: 808px;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}
.birthdateLabel {
  color: #000000;
  text-align: left;
  font-family: "Inter-Regular", sans-serif;
  font-size: 20px;
  font-weight: 400;
  position: absolute;
  left: 0px;
  top: 502px;
  width: 74px;
  height: 24px;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}
.birthdateInput {
  background: #ffffff;
  border-style: solid;
  border-color: #555553;
  border-width: 1px;
  width: 314px;
  height: 57px;
  position: absolute;
  left: 0px;
  top: 533px;
  padding-right: 10px;
}
.birthdatePlaceholder {
  color: #797979;
  text-align: left;
  font-family: "Inter-Medium", sans-serif;
  font-size: 20px;
  font-weight: 500;
  position: absolute;
  left: 18px;
  top: 550px;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}
.genderMaleContainer {
  width: 84px;
  height: 57px;
  position: static;
}
.genderMaleButton {
  border-style: solid;
  border-color: #c5c5c5;
  border-width: 1px;
  border-radius: 3px;
  width: 84px;
  height: 57px;
  position: absolute;
  left: 324px;
  top: 533px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
}
.genderFemaleContainer {
  width: 84px;
  height: 57px;
  position: static;
}
.genderFemaleButton {
  border-style: solid;
  border-color: #c5c5c5;
  border-width: 1px;
  border-radius: 3px;
  width: 84px;
  height: 57px;
  position: absolute;
  left: 409px;
  top: 533px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-left: 5px;
  cursor: pointer;
}
.checked-option {
  background: #d8d8d8;
}

.addrBox {
  position: relative;
}

.formContainer input {
  color: #9f9f9f;
  border-radius: 3px;
  border-color: #c5c5c5;
}

.pwCheckLabel {
  position: absolute;
  top: 92px;
  left: 3px;
  display: none;
}
.emailCheckLabel {
  position: absolute;
  top: 723px;
  left: 3px;
  display: none;
}

.passwordLabel,
.label,
.nameLabel,
.phoneLabel,
.birthdateLabel,
.emailLabel,
.addressLabel {
  min-width: 120px;
}
.hidden-input {
  display: none;
}
</style>
