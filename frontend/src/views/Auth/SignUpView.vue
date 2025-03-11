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
              :class="{ 'error-input': userUuidError }"
              placeholder="아이디를 입력하세요."
            />
            <button
              type="button"
              id="duplicateCheckButton"
              class="duplicateCheckButton"
              @click="checkDuplicateID"
            >
              중복확인
            </button>
            <label id="idCheckLabel" class="idCheckLabel">
              {{ errors.userUuid }}</label
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
              :class="{ 'error-input': passwordError }"
              placeholder="비밀번호 영문/특수문자/숫자 8~16자"
            />

            <label id="pwCheckLabel" class="pwCheckLabel">
              {{ errors.password }}</label
            >
          </div>
          <div class="passwordCheckContainer">
            <div class="passwordLabel">비밀번호 확인</div>

            <input
              type="password"
              v-model="repeatPassword"
              class="passwordInput"
              :class="{ 'error-input': repeatPasswordError }"
              placeholder="비밀번호를 재입력해주세요."
            />

            <label id="pwCheckLabel2" class="pwCheckLabel">
              {{ repeatPasswordError }}</label
            >
          </div>
          <div class="nameContainer">
            <div class="nameLabel">이름</div>
            <input
              type="text"
              id="userName"
              v-model="userName"
              name="userName"
              class="nameInput"
              :class="{ 'error-input': userNameError }"
              placeholder="이름을 입력하세요."
            />
            <label id="userNameCheckLabel" class="userNameCheckLabel">
              {{ userNameError }}</label
            >
          </div>
          <div class="phoneContainer">
            <div class="phoneLabel">휴대폰 번호</div>
            <input
              type=""
              id="phone"
              v-model="phone"
              name="phone"
              class="nameInput"
              :class="{ 'error-input': phoneError }"
              placeholder="-없이 휴대폰 번호를 입력하세요."
            />
            <label class="phoneCheckLabel"> {{ phoneError }}</label>
          </div>
          <div class="emailLabel">이메일 주소</div>
          <div class="emailContainer">
            <input
              type="email"
              id="email"
              name="email"
              class="emailInput"
              :class="{ 'error-input': emailError }"
              placeholder="이메일주소"
              v-model="email"
            />
            <label id="emailCheckLabel" class="emailCheckLabel">
              {{ errors.email }}</label
            >
          </div>
          <div class="addressLabel">주소 정보</div>
          <div class="addressContainer">
            <div class="addrBox">
              <div
                id="addressSearch"
                class="postcodeInputBtn"
                @click="openPostcodePopup"
              >
                주소검색
              </div>
              <input
                type="text"
                class="addressInput"
                id="user_address"
                v-model="user_address"
                :class="{ 'error-input': addressError }"
                name="user_address"
                placeholder="주소"
                readonly
              />
            </div>
            <input
              type="text"
              id="user_detail_address"
              ref="user_detail_address_input"
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
            <label class="addressCheckLabel"> {{ addressError }}</label>
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
import { computed, ref } from "vue";
import axios from "@/api/axios";
import { useRouter } from "vue-router";
import { useForm, useField } from "vee-validate";
import * as yup from "yup";
import { useModalStore } from "@/stores/modalStore";
import { useConfirmModalStore } from "@/stores/modalStore";
const router = useRouter();

const gender = ref("M");
const user_detail_address = ref("");
const birthDate = ref("");
const isChecked = ref(false);
const user_detail_address_input = ref(null);
const modalStore = useModalStore(); // 스토어를 가져와 사용
const confirmModalStore = useConfirmModalStore();
const total_address = computed(() => {
  return `${user_address.value} ${user_detail_address.value}`;
});

// 유효성 검사 스키마 설정
const schema = yup.object({
  userName: yup
    .string()
    .matches(
      /^[가-힣a-zA-Z]{2,15}$/,
      "* 이름은 한글 또는 영문 2~15자여야 합니다."
    )
    .required("* 이름은 필수 항목입니다."),
  email: yup
    .string()
    .matches(
      /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/,
      "* 올바른 이메일 주소를 입력해주세요."
    )
    .required("* 이메일을 입력해주세요"),
  phone: yup
    .string()
    .matches(/^(010|011|016|070)\d{7,8}$/, "* 유효한 전화번호 형식이 아닙니다.")
    .required("* 전화번호는 필수 항목입니다."),
  userUuid: yup
    .string()
    .matches(
      /^[a-zA-Z0-9]{6,12}$/,
      "* 6~12자 영문 대소문자, 숫자만 입력하세요."
    )
    .required("* 아이디는 필수 입력 항목입니다."),
  password: yup
    .string()
    .matches(
      /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,16}$/,
      "* 8-16자 이내 영문자, 숫자, 특수문자를 모두 포함해야 합니다."
    )
    .required("* 비밀번호는 필수 항목입니다."),
  repeatPassword: yup
    .string()
    .oneOf([yup.ref("password")], "* 비밀번호가 일치하지 않습니다.") // 비밀번호 일치 검사
    .required("* 비밀번호 확인은 필수 항목입니다."),
  user_address: yup.string().required("* 주소는 필수 입력 항목입니다."), // 주소 필드 추가
});

const { validate, errors } = useForm({
  validationSchema: schema,
});

const { value: userName, errorMessage: userNameError } = useField("userName");
const { value: email, errorMessage: emailError } = useField("email");
const { value: phone, errorMessage: phoneError } = useField("phone");
const { value: user_address, errorMessage: addressError } =
  useField("user_address");
const { value: userUuid, errorMessage: userUuidError } = useField("userUuid");
const { value: password, errorMessage: passwordError } = useField("password");
const { value: repeatPassword, errorMessage: repeatPasswordError } =
  useField("repeatPassword");

// 유효성 검사 후 중복 체크
const checkDuplicateID = () => {
  console.log("userUuidError", userUuidError.value);
  if (!userUuidError.value && userUuid.value != null) {
    fetchDuplicateCheckId(userUuid.value);
  } else {
    confirmModalStore.showModal(
      "회원가입",
      "아이디를 올바르게 입력해주세요.",
      "입력된 아이디가 없습니다.",
      "확인"
    );
  }
};

/**아이디 중복 확인 */
const fetchDuplicateCheckId = async (Id) => {
  try {
    const response = await axios
      .get(`/api/users/check-id?userUuid=${Id}`)
      .then((res) => {
        console.log(res);
        modalStore.showModal("회원가입", "사용가능한 아이디입니다.");
      })
      .catch((error) => {
        console.log(error);
        //완료 모달창 사용
        confirmModalStore.showModal(
          "회원가입",
          "아이디를 올바르게 입력해주세요.",
          "중복된 아이디",
          "확인"
        );
      });
  } catch {
    console.error();
    alert("중복확인 에러");
  }
};

/**  회원가입 api 요청 핸들러*/
const handleSignUp = async () => {
  // 전체 유효성 검사 실행
  const isValid = await validate();

  if (!isValid) {
    console.log("유효성 검사 실패");
    return;
  }

  console.log(" total_address", total_address.value);

  try {
    const response = await axios
      // .post("/api/auth/signup", {
      .post("/api/auth/signup", {
        userName: userName.value,
        password: password.value,
        userUuid: userUuid.value,
        phone: phone.value,
        email: email.value,
        gender: gender.value,
        address: total_address.value,
        birthDate: birthDate.value,
      })
      .then((res) => {
        console.log("res", res);
      });
    router.push("/auth/login");
  } catch {
    console.error();
    confirmModalStore.showModal(
      "회원가입",
      "회원가입에 실패했습니다..",
      "오류발생",
      "확인"
    );
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

/** 주소 api 불러오기*/
const loadDaumPostcode = () => {
  return new Promise((resolve, reject) => {
    if (document.getElementById("daum-postcode-script")) {
      resolve(); // 이미 스크립트가 로드된 경우
      return;
    }

    const script = document.createElement("script");
    script.src =
      "https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js";
    script.id = "daum-postcode-script";
    script.async = true;

    script.onload = () => {
      console.log("Daum Postcode script loaded");
      resolve();
    };

    script.onerror = () =>
      reject(new Error("Failed to load Daum Postcode script"));

    document.body.appendChild(script);
  });
};

/**  다음 주소 팝업창 열기 */
const openPostcodePopup = () => {
  loadDaumPostcode()
    .then(() => {
      new window.daum.Postcode({
        oncomplete: (data) => {
          console.log("user_address:", data.address);
          user_address.value = data.address;
          user_detail_address_input.value.focus();
        },
      }).open();
    })
    .catch((error) => {
      console.error(error.message);
    });
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
  width: 293px;
  height: 57px;
  position: absolute;
  left: 5px;
  top: 93px;
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
}
.emailCheckLabel {
  position: absolute;
  top: 723px;
  left: 3px;
}

.userNameCheckLabel {
  position: absolute;
  top: 94px;
  left: 3px;
}

.phoneCheckLabel {
  position: absolute;
  top: 94px;
  left: 3px;
}
.addressCheckLabel {
  position: absolute;
  top: 1043px;
  left: 3px;
}
.passwordLabel,
.label,
.nameLabel,
.phoneLabel,
.birthdateLabel,
.emailLabel,
.addressLabel {
  min-width: 160px;
}
.addressCheckLabel,
.idCheckLabel,
.pwCheckLabel,
.emailCheckLabel,
.userNameCheckLabel,
.phoneCheckLabel {
  color: #ff294f;
  font-size: 14px;
}

.hidden-input {
  display: none;
}

.error-input {
  color: #ff294f !important;
  border: 1.5px solid #ff294f !important;
}

input:focus::placeholder {
  opacity: 0; /* 포커스 시 사라지도록 설정 */
}
</style>
