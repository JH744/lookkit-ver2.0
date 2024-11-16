<template>
    <div class="address-registration">
      <div class="new-address-registration">
        <span>신규 배송지 등록</span>
      </div>
      <div class="delete-button">
        <button type="button" class="x-button" @click="closeWindow">X</button>
      </div>
      <div class="label label-destination">
        <span>배송지명</span>
      </div>
      <div class="address-input">
        <input type="text" v-model="addressName" class="span-text" placeholder="배송지명 입력" />
      </div>
      <div class="label label-recipient">
        <span>받는 사람</span>
      </div>
      <div class="recipient-input">
        <input type="text" v-model="recipientName" class="span-text" placeholder="받는 사람 입력" />
      </div>
      <div class="label label-phone">
        <span>휴대폰 번호</span>
      </div>
      <div class="phone-input">
        <input type="text" v-model="phoneNumber" class="span-text" placeholder="휴대폰 번호 입력" />
      </div>
      <div class="label label-address">
        <span>주소</span>
      </div>
      <div class="address-input-1">
        <input type="text" v-model="postalCode" class="span-text" placeholder="우편번호 입력" />
      </div>
      <div class="button button-zipcode">
        <button type="button" @click="searchPostalCode">우편번호 찾기</button>
      </div>
      <div class="address-input-2">
        <input type="text" v-model="detailAddress" class="span-text" placeholder="상세 주소 입력" />
      </div>
      <div class="button button-register">
        <button type="button" @click="registerAddress">등록하기</button>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  
  const addressName = ref('');
  const recipientName = ref('');
  const phoneNumber = ref('');
  const postalCode = ref('');
  const detailAddress = ref('');
  
  const closeWindow = () => {
    window.close();
  };
  
  const searchPostalCode = () => {
    new window.daum.Postcode({
      oncomplete: function (data) {
        let addr = data.userSelectedType === 'R' ? data.roadAddress : data.jibunAddress;
        postalCode.value = addr;
      },
    }).open();
  };
  
  const registerAddress = () => {
    if (!addressName.value || !recipientName.value || !phoneNumber.value || !postalCode.value || !detailAddress.value) {
      alert('모든 필드를 입력해주세요.');
      return;
    }
  
    const totalAddress = `${postalCode.value} ${detailAddress.value}`;
  
    // 부모 창으로 데이터 전달
    window.opener.postMessage(
      {
        type: 'address',
        address: {
          addressName: addressName.value,
          recipientName: recipientName.value,
          phoneNumber: phoneNumber.value,
          fullAddress: totalAddress,
        },
      },
      '*'
    );
  
    // 창 닫기
    closeWindow();
  };
  </script>
  
  <style scoped>
  /* 스타일은 기존과 동일하게 유지 */
  .address-registration,
  .address-registration * {
    box-sizing: border-box;
  }
  .address-registration {
    background: #ffffff;
    height: 905px;
    position: relative;
  }
  .new-address-registration {
    color: #101010;
    text-align: left;
    font-family: "Inter-Regular", sans-serif;
    font-size: 20px;
    line-height: 28px;
    font-weight: 400;
    position: absolute;
    left: 41px;
    top: 25px;
    height: 30px;
    display: flex;
    align-items: center;
    justify-content: flex-start;
  }
  .delete-button {
    position: absolute;
    left: 555px;
    top: 25px;
  }
  .x-button {
    cursor: pointer;
    display: inline-flex;
    align-items: center;
    justify-content: center;
    border-radius: 54px;
    padding: 6px 10px;
    font-size: 16px;
    font-weight: bold;
    color: #ffffff;
    background-color: #062236;
  }
  .label {
    margin-bottom: 10px;
    display: block;
  }
  .address-input,
  .recipient-input,
  .phone-input,
  .address-input-1,
  .address-input-2 {
    background: #ffffff;
    border: 1px solid #888686;
    width: 532px;
    height: 54px;
    margin-bottom: 20px;
    padding: 8px 10px;
  }
  .button-zipcode {
    margin-top: 10px;
  }
  .button-register {
    margin-top: 30px;
  }
  .button {
    border-radius: 54px;
    padding: 14px 24px;
    font-size: 15px;
    font-weight: bold;
    color: #fff;
    background-color: #062236;
    transition: background-color 0.3s ease;
  }
  .button:hover {
    background-color: #1a4d6e;
  }
  .span-text {
    font-size: 14px;
    color: #101010;
    width: 100%;
    height: 100%;
    padding: 0px 10px;
  }
  </style>
  