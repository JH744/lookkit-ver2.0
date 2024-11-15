<template>
  <div class="singUpContainer">
    <div class="contentWrapper">
      <div class="titleHeader">
        <div class="title">회원가입</div>
        <div class="separator"></div>
      </div>
      <div class="formContainer">
        <form @submit.prevent="handleSignUp" id="signUpForm">
          <div class="buttonContainer">
            <button type="submit" class="signupButton" id="signupButton">
              회원가입
            </button>
            <div class="cancelButton">
              <router-link to="/auth/login">취소하기</router-link>
            </div>
          </div>
          <div class="inputContainer">
            <div class="label">아이디</div>
            <input
              id="userUuid"
              type="text"
              name="userUuid"
              v-model="userUuid"
              class="inputField"
              placeholder="아이디를 입력하세요."
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
              v-model="password"
              class="passwordInput"
              placeholder="비밀번호 영문/특수문자/숫자 8~16자"
            />

            <label id="pwCheckLabel" class="pwCheckLabel"> 비밀번호 체크</label>
          </div>
          <div class="passwordCheckContainer">
            <div class="passwordLabel">비밀번호 확인</div>

            <input
              type="password"
              v-model="repeatPassword"
              class="passwordInput"
              placeholder="비밀번호를 재입력해주세요."
            />

            <label id="pwCheckLabel" class="pwCheckLabel"> 비밀번호 체크</label>
          </div>
          <div class="nameContainer">
            <div class="nameLabel">이름</div>
            <input
              type="text"
              id="userName"
              v-model="userName"
              name="userName"
              class="nameInput"
              placeholder="이름을 입력하세요."
            />
          </div>
          <div class="phoneContainer">
            <div class="phoneLabel">휴대폰 번호</div>
            <input
              type=""
              id="phone"
              v-model="phone"
              name="phone"
              class="nameInput"
              placeholder="-없이 휴대폰 번호를 입력하세요."
            />
          </div>
          <div class="emailLabel">이메일 주소</div>
          <div class="emailContainer">
            <input
              type="email"
              id="email"
              v-model="email"
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
                v-model="user_address"
                name="user_address"
                placeholder="주소"
                readonly
              />
            </div>
            <input
              type="text"
              id="user_detail_address"
              name="user_detail_address"
              v-model="user_detail_address"
              class="detailedAddressInput"
              placeholder="상세주소"
            />
            <input
              type="text"
              id="user_total_address"
              name="address"
              v-model="address"
              style="display: none"
            />
          </div>
          <div class="birthdateLabel">생년월일</div>
          <input
            type="date"
            id="birthDate"
            v-model="birthDate"
            class="birthdateInput"
            name="birthDate"
            placeholder="생년월일 6자리를 입력하세요."
          />
          <div class="genderMaleContainer">
            <input
              type="radio"
              name="gender"
              class="hidden-input"
              :checked="isChecked"
              value="M"
              id="genderInputMan"
            />
            <div
              :class="[
                'genderMaleButton',
                'genderBtn',
                { 'checked-option': gender === 'M' },
              ]"
              @click="chooseGenderMan"
            >
              Man
            </div>
          </div>
          <div class="genderFemaleContainer">
            <input
              type="radio"
              name="gender"
              class="hidden-input"
              :checked="isChecked"
              value="F"
              id="genderInputWoman"
            />
            <div
              :class="[
                'genderFemaleButton',
                'genderBtn',
                { 'checked-option': gender === 'F' },
              ]"
              @click="chooseGenderWoman"
            >
              Woman
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";

const router = useRouter();

const userUuid = ref("");
const password = ref("");
const repeatPassword = ref("");
const userName = ref("");
const phone = ref("");
const email = ref("");
const gender = ref("M");
const address = ref("경기도");
const birthDate = ref("");
const isChecked = ref(false);

// 회원가입 api 요청 핸들러
const handleSignUp = async () => {
  try {
    const response = await axios
      // .post("/api/auth/signup", {
      .post("http://localhost:8081/api/auth/signup", {
        userName: userName.value,
        password: password.value,
        userUuid: userUuid.value,
        phone: phone.value,
        email: email.value,
        gender: gender.value,
        address: address.value,
        birthDate: birthDate.value,
      })
      .then((res) => {
        console.log("res", res);
      });
    router.push("/auth/login");
  } catch (error) {
    console.error();
    console.log(error);
    alert("회원가입에 실패하였습니다.");
  }
};

// 성별 선택 이벤트
const chooseGenderWoman = () => {
  gender.value = "F";
  console.log(gender.value);
};
const chooseGenderMan = () => {
  gender.value = "M";
  console.log(gender.value);
};

// onMounted(() => {
//   const handlerDaum = () => {
//     let idDuplicateChecked = false;
//     //      -- [주소지 찾기] --
//     $("#addressSearch").click(function () {
//       new daum.Postcode({
//         oncomplete: function (data) {
//           // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
//           // 각 주소의 노출 규칙에 따라 주소를 조합한다.
//           // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
//           var addr = ""; // 주소 변수
//           var extraAddr = ""; // 참고항목 변수

//           //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
//           if (data.userSelectedType === "R") {
//             // 사용자가 도로명 주소를 선택했을 경우
//             addr = data.roadAddress;
//           } else {
//             // 사용자가 지번 주소를 선택했을 경우(J)
//             addr = data.jibunAddress;
//           }

//           // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
//           if (data.userSelectedType === "R") {
//             // 법정동명이 있을 경우 추가한다. (법정리는 제외)
//             // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
//             if (data.bname !== "" && /[동|로|가]$/g.test(data.bname)) {
//               extraAddr += data.bname;
//             }
//             // 건물명이 있고, 공동주택일 경우 추가한다.
//             if (data.buildingName !== "" && data.apartment === "Y") {
//               extraAddr +=
//                 extraAddr !== "" ? ", " + data.buildingName : data.buildingName;
//             }
//             // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
//             if (extraAddr !== "") {
//               extraAddr = " (" + extraAddr + ")";
//             }
//             // 조합된 참고항목을 해당 필드에 넣는다.
//           } else {
//           }

//           // 우편번호와 주소 정보를 해당 필드에 넣는다.

//           document.getElementById("user_address").value = addr;

//           //주소 검색이 완료된 후 변하는 css 목록
//           $(".field_address input").css("display", "block");
//           $("#addressNo").text("재검색");

//           // 커서를 상세주소 필드로 이동한다.
//           document.getElementById("user_detail_address").focus();
//           // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분입니다.
//           // 예제를 참고하여 다양한 활용법을 확인해 보세요.
//           // http://postcode.map.daum.net/guide  api주소
//         },
//       }).open();
//     });
//   };
// });
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
  top: 1130px;
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

.passwordCheckContainer {
  width: 499px;
  height: 86px;
  position: absolute;
  left: 0px;
  top: 251px;
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
  top: 377px;
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
  top: 755px;
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
  top: 880px;
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
  top: 855px;
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
  top: 855px;
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
  top: 980px;
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
