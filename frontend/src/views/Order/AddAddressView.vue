<template>
  <div v-if="showModal" class="modal-overlay">
    <div class="modal-content">
      <div class="delete-button">
        <button type="button" class="x-button" @click="closeModal">X</button>
      </div>
        <div class="info-header">
          <span class="info-title">신규 배송지 등록</span>
        </div>
        <div class="info-block">
          <span>배송지명</span>
          <input type="text" v-model="addressName" class="span-text" placeholder="배송지명 입력" />
        </div>
        <div class="info-block">
          <span>받는 사람</span>
          <input type="text" v-model="recipientName" class="span-text" placeholder="받는 사람 입력" />
        </div>
        <div class="info-block">
          <span>휴대폰 번호</span>
          <input type="text" v-model="phoneNumber" class="span-text" placeholder="휴대폰 번호 입력" />
        </div>
     <div class="search-address">
      <div class="label-address">
        <span>주소</span>
      </div>
      <div class="button-zipcode"  @click="searchPostalCode">우편번호 찾기
      </div>
     </div>
     <div class="address-block">
       <input type="text" v-model="postalCode" class="span-text" placeholder="우편번호 입력" />
       <input type="text" v-model="detailAddress" class="span-text" placeholder="상세 주소 입력" />
     </div>
     <div class="button-block">
       <div class="button-register" @click="registerAddress">등록하기</div>
     </div>
    </div>
  </div>
</template>

<script setup>
import { ref, defineEmits } from 'vue';

const emit = defineEmits(['addressRegistered', 'close']);

const showModal = ref(true); 
const addressName = ref('');
const recipientName = ref('');
const phoneNumber = ref('');
const postalCode = ref('');
const detailAddress = ref('');


const closeModal = () => {
  showModal.value = false;
  emit('close');
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

  const fullAddress = `${postalCode.value} ${detailAddress.value}`;

  // 부모 컴포넌트로 데이터를 전송
  emit('addressRegistered', {
    addressName: addressName.value,
    recipientName: recipientName.value,
    phoneNumber: phoneNumber.value,
    fullAddress: fullAddress,
  });

  // 창 닫기
  closeModal();
};
</script>

<style scoped>
.info-title {
  font-weight: bold;
}

.button-block {
  justify-items: center;
}

.info-header {
  margin-bottom: 20px;
}

.info-block {
  display: grid;
  gap: 10px;
  margin-bottom: 10px;
}

.address-block {
  display: grid;
  gap: 10px;
  margin-bottom: 10px;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background: #fff;
  padding: 20px;
  border-radius: 8px;
  width: 600px;
  max-height: 90vh;
  overflow-y: auto;
  position: relative;
}

.delete-button {
  position: absolute;
  right: 15px;
  top: 15px;
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

.new-address-registration {
  color: #101010;
  text-align: left;
  font-family: "Inter-Regular", sans-serif;
  font-size: 20px;
  line-height: 28px;
  font-weight: 400;
  height: 30px;
  display: flex;
  align-items: center;
  justify-content: flex-start;
  margin-bottom: 10px;
}

.label {
  margin-bottom: 10px;
  display: block;
}

.search-address {
  display: flex;
  align-items: center;
  gap: 5%;
  margin-bottom: 10px;
  margin-top: 20px;
}
.address-input,
.recipient-input,
.phone-input,
.address-input-2 {
  background: #ffffff;
  border: 1px solid #888686;
  width: 532px;
  height: 54px;
  margin-bottom: 20px;
  padding: 8px 10px;
}
.address-input-1 {
  background: #ffffff;
  border: 1px solid #888686;
  width: 532px;
  height: 54px;
  margin-bottom: 20px;
  margin-top: 20px;
  padding: 8px 10px;
}

.button-zipcode {
  border-radius: 20px;
    padding: 12px 22px;
    font-size: 15px;
    font-weight: bold;
    color: #404040;
    border: 1px solid gray;
    background-color: #ffffff;
    transition: background-color 0.3s ease;
    text-align: center;
}

.button-register {
  border-radius: 54px;
  padding: 14px 24px;
  font-size: 15px;
  font-weight: bold;
  color: #fff;
  background-color: #062236;
  transition: background-color 0.3s ease;
  text-align: center;
}


.button:hover {
  background-color: #1a4d6e;
}

.span-text {
  font-size: 14px;
  color: #101010;
  width: 100%;
  height: 100%;
  padding: 10px 10px;
}
</style>
