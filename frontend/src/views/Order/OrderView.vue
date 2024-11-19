<template>
  <div class="order-container">
    
    <div class="order-title">
      <h2>결제/주문</h2>
    </div>
    <section class="recipient-info-section">
      <header class="section-header">
        <h2 class="section-title">받는 분 정보</h2>
      </header>
      <div class="info-table">
        <div class="info-row">
          <span class="label-title">배송지 정보</span>
          <button class="link-text" @click="showAddAddressModal = true">배송지 등록하기</button> 
          <AddAddressView v-if="showAddAddressModal" @close="showAddAddressModal = false" @addressRegistered="updateAddress"/>          
        </div>
        <div class="info-row">
          <div class="info-label">배송지명</div>
          <input type="text" v-model="address.addressName" class="input-field" readonly>
        </div>
        <div class="info-row">
          <div class="info-label">받는 사람</div>
          <input type="text" v-model="address.recipientName" class="input-field" readonly>
        </div>
        <div class="info-row">
          <div class="info-label">휴대폰 번호</div>
          <input type="text" v-model="address.phoneNumber" class="input-field" readonly>
        </div>
        <div class="info-row">
          <div class="info-label">주소</div>
          <input type="text" v-model="address.fullAddress" class="input-field" readonly>
        </div>
      </div>
      <div class="info-row2">
        <div class="info-label">배송 메모</div>
        <div class="info-data">
          <div class="memo-list">
            <label class="memo-item" v-for="(memo, index) in memoOptions" :key="index">
              <input type="radio" name="memo-option" class="memo-checkbox" :value="memo" v-model="selectedMemo">
              <span class="memo-text">{{ memo }}</span>
            </label>
          </div>
        </div>
      </div>
    </section>
    <section class="product-info-section">
      <header class="section-header">
        <h2 class="section-title">상품 정보</h2>
      </header>

      <!-- Cart에서 저장된 selectedItems 출력 -->
      <div v-if="orderStore.selectedItems.length > 0">
        <div v-for="item in orderStore.selectedItems" :key="item.itemId" class="item-description">
          <!-- 썸네일 이미지 -->
          <img class="item-thumbnail" :src="`/images/product/0${item.itemId}/${item.itemId}_thumbnail.webp`" alt="썸네일" />

          <!-- 상품 정보 -->
          <div class="item-description2">  
            <div v-if="item.type === 'product' && item.brandName" class="brand-name">
              {{ item.brandName }}
            </div>
            <div class="product-name"> {{ item.itemName }} </div>
          </div>

          <!-- 수량 또는 대여일 -->
          <div v-if="item.type === 'product'" class="product-variant">
            {{ item.quantity }}개
          </div>
          <div v-else-if="item.type === 'codi'" class="product-variant">
            대여일 {{ item.startDate }} ~ 반납일 {{ item.endDate }}
          </div>

          <!-- 가격 -->
          <div class="item-price">{{ formatPrice(item.totalPrice) }}원</div>
        </div>
      </div>


      <!-- 단일 상품 정보 출력 -->
      <div v-else-if="orderItem">
      <div v-if="orderItem.quantity">
      <!-- Product 정보 -->
        <div class="item-description">    
         <img class="item-thumbnail" :src="`/images/product/0${orderItem.itemId}/${orderItem.itemId}_thumbnail.webp`" alt="썸네일" />
         <div class="item-description2">  
           <div class="brand-name"> {{ orderItem.brandName }}</div>
           <div class="product-name"> {{ orderItem.itemName }}</div>
         </div>
         <div class="product-variant"> {{ orderItem.quantity }}개</div>
         <div class="item-price"> {{ orderItem.totalPrice }}원</div>
        </div>
      </div>  
      <div v-else>
      <!-- Codi 정보 -->
        <div class="item-description">
          <img class="item-thumbnail" :src="`/images/codi/0${orderItem.itemId}/${orderItem.itemId}_thumbnail.webp`" alt="썸네일" />
          <div class="product-name"> {{ orderItem.itemName }}</div>
          <div class="product-variant">대여일 {{ orderItem.startDate }} ~반납일 {{ orderItem.endDate }}</div>
          <div class="product-variant2">대여기간 {{ rentalDays }}일</div>
          <div class="item-price"> {{ orderItem.price }}원</div>
        </div>
      </div>
    </div>
    </section>
    <section class="payment-frame">
      <header class="section-header2">
        <h2 class="section-title">결제수단 선택</h2>
      </header>
      <div class="payment-method-list">
        <label class="payment-method-item">
          <input type="radio" name="payment-method" value="card" v-model="selectedPaymentMethod">
          <img src="@/assets/icons/card-image.png" alt="Credit/Debit Card" class="method-icon">
          <span class="method-title">신용/체크카드</span>
        </label>
      </div>
      <header class="section-header2">
        <h2 class="section-title">최종 결제금액</h2>
      </header>
      <div class="summary-row">
        <span class="summary-title">최종 결제금액</span>
        <span class="summary-value">{{ formatPrice(totalPrice) }}원</span>
      </div>
    </section>
    <section class="total-payment-section">
      <div class="agreement-list">
        <label class="agreement-item" v-for="(agreement, index) in agreements" :key="index">
          <input type="checkbox" v-model="agreements[index].checked">
          <span class="agreement-text">{{ agreement.text }}</span>
        </label>
      </div>
    </section>
    <button class="pay-button" @click="processPayment">
      총 {{ totalQuantity }}개 / {{ formatPrice(totalPrice) }}원 결제하기
    </button>
  </div>
</template>

<script setup>

import { ref, computed, onMounted, nextTick, onBeforeUnmount } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import "@/assets/styles/order.css";
import { useOrderStore } from '@/store/orderStore';
import AddAddressView from '@/views/Order/AddAddressView.vue';


const buyerAddressName = ref('');
const buyerRecipientName = ref('');
const buyerPhoneNumber = ref('');
const buyerAddress = ref('');
const selectedMemo = ref('선택 안 함');
const memoOptions = [
  '선택 안 함',
  '부재 시 경비실에 맡겨주세요',
  '부재 시 집 앞에 놔주세요',
  '부재 시 핸드폰으로 연락 바랍니다',
  '배송 전 연락 바랍니다'
];

const selectedPaymentMethod = ref('');
const agreements = ref([
  { text: '주문상품정보 및 서비스 이용약관에 동의', checked: false },
  { text: '구매조건 확인 및 결제 진행 동의(필수)', checked: false },
  { text: '개인정보 제 3자 제공 동의(필수)', checked: false },
  { text: '위 주문 내용을 확인하였으며, 결제에 모두 동의합니다', checked: false }
]);

const orderStore = useOrderStore();

onMounted(async () => {
  await nextTick();
  console.log("Selected items in orderStore after mount:", orderStore.selectedItems);
});

// 주문 완료 후 데이터 초기화
const completeOrder = () => {
  alert('주문이 완료되었습니다.');
  orderStore.clearSelectedItems();
  localStorage.removeItem('orderStore'); // 로컬 스토리지에서도 데이터 삭제
  window.location.href = '/order/complete';
};

// 사용자가 페이지를 떠날 때 selectedItems 초기화
onBeforeUnmount(() => {99/
  orderStore.clearSelectedItems();
  localStorage.removeItem('orderStore'); // 로컬 스토리지에서도 데이터 삭제
});

const orderItem = ref(null);
const route = useRoute();

orderItem.value = route.params.orderItem;

const params = route.query;

// 구매인지 대여인지 구분하여 데이터 할당
if (params.itemType === 'product') {
orderItem.value = {
  itemId: params.itemId,
  itemName: params.itemName,
  brandName: params.brandName,
  quantity: parseInt(params.quantity, 10),
  price: parseInt(params.price, 10),
  totalPrice: parseInt(params.totalPrice, 10),
  type: 'product'
};
} else if (params.itemType === 'codi') {
orderItem.value = {
  itemId: params.itemId,
  itemName: params.itemName,
  startDate: params.startDate,
  endDate: params.endDate,
  price: parseInt(params.price, 10),
  totalPrice: parseInt(params.totalPrice, 10),
  type: 'codi'
};
}



// 총 대여일 계산
const rentalDays = computed(() => {
if (orderItem.value && orderItem.value.startDate && orderItem.value.endDate) {
  const startDate = new Date(orderItem.value.startDate);
  const endDate = new Date(orderItem.value.endDate);
  return (endDate - startDate) / (1000 * 60 * 60 * 24);
}
return 0;
});


// 총 가격 계산
const totalPrice = computed(() => {
  if (orderItem.value) {
    if (orderItem.value.quantity) {
      // Product의 경우 수량에 따라 총 가격 계산
      return orderItem.value.quantity * orderItem.value.price;
    } else {
      // Codi의 경우 대여 가격 사용
      return orderItem.value.price;
    }
  }
  return 0;
});

onMounted(() => {
  // 아임포트 스크립트 동적 로드
  const script = document.createElement('script');
  script.src = "https://cdn.iamport.kr/js/iamport.payment-1.2.0.js";
  script.async = true;
  script.onload = () => {
    console.log("Iamport script loaded successfully");
    // 아임포트 초기화
    const IMP = window.IMP;
    if (IMP) {
      IMP.init("imp40354073");
    }
  };
  document.body.appendChild(script);
});



const processPayment = () => {
  if (!selectedPaymentMethod.value) {
    alert('결제 수단을 선택해주세요.');
    return;
  }

  if (agreements.value.some(agreement => !agreement.checked)) {
    alert('모든 필수 동의 항목에 체크해주세요.');
    return;
  }

  if (!address.value) {
    alert('배송지를 등록해주세요.');
    return;
  }

  // 아임포트 초기화
  const IMP = window.IMP; 
  IMP.init("imp40354073");


  const orderName = orderStore.selectedItems.length > 0 ? orderStore.selectedItems[0].itemName : '주문 상품';
  const buyerName = address.value.recipientName;
  const buyerTel = address.value.phoneNumber;
  const buyerAddr = address.value.fullAddress;

  IMP.request_pay({
    pg: 'kcp',
    pay_method: 'card',
    merchant_uid: 'merchant_' + new Date().getTime(),
    name: orderName,
    amount: totalPrice.value,
    buyer_name: buyerName,
    buyer_tel: buyerTel,
    buyer_addr: buyerAddr,
  }, function (rsp) {
    if (rsp.success) {
      alert("결제가 완료되었습니다.\n고유ID: " + rsp.imp_uid);

      // 주문 상세 정보 수집
      const orderDetails = orderStore.selectedItems.map(item => ({
        productId: item.type === 'product' ? item.itemId : null, // 단일 상품 ID
        codiId: item.type === 'codi' ? item.itemId : null, // 코디 상품 ID
        quantity: item.type === 'product' ? item.quantity : 1, // 수량 (상품의 경우 지정, 코디는 1로 설정)
        rentalStartDate: item.type === 'codi' ? item.startDate : null, // 대여 시작일 (코디에 해당)
        rentalEndDate: item.type === 'codi' ? item.endDate : null, // 대여 종료일 (코디에 해당)
        productPrice: item.price // 상품 또는 대여 가격
      }));

      const paymentData = {
        userId: 1, // 실제 유저 ID를 사용해야 합니다.
        totalAmount: totalPrice.value,
        orderAddress: buyerAddr,
        orderAddressee: buyerName,
        orderPhone: buyerTel,
        orderComment: selectedMemo.value,
        orderStatus: "결제완료",
        orderDetails: orderDetails
      };

      // 주문 정보를 서버로 전송
fetch("http://localhost:8081/api/order/complete", {
  method: "POST",
  headers: {
    "Content-Type": "application/json"
  },
  body: JSON.stringify(paymentData)
})
.then(response => response.json())
.then(orderId => {
  if (!orderId) {
    alert("주문 ID를 받지 못했습니다. 문제가 발생했습니다.");
    return;
  }

  // Vue 라우터를 사용해 주문 완료 페이지로 이동합니다.
  router.push({
    path: '/order/orderComplete',
    query: { orderId: orderId }
  });
})
.catch(() => {
  alert("결제를 완료하는 데 실패했습니다.");
});


    } else {
      alert("결제에 실패했습니다. 실패사유: " + rsp.error_msg);
    }
  });
};


//  총 수량 계산
const totalQuantity = computed(() => {
  if (orderItem.value && orderItem.value.quantity) {
    return orderItem.value.quantity;
  }
  return 1; // Codi의 경우 수량은 1개로 간주
});

  const formatPrice = (price) => {
    return price ? price.toLocaleString() : '0';
  };


  const showAddAddressModal = ref(false);
  const address = ref({
    addressName: '',
    recipientName: '',
    phoneNumber: '',
    fullAddress: ''
  });

  // 주소 업데이트 메서드
  const updateAddress = (newAddress) => {
    address.value = newAddress;
  };
</script>