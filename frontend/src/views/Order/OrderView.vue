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
            <button class="address-link" @click="navigateToAddAddress">
              <span class="link-text">+배송지를 등록하세요</span>
            </button>            
          </div>
          <div class="info-row">
            <div class="info-label">배송지명</div>
            <input type="text" v-model="buyerAddressName" class="input-field" readonly>
          </div>
          <div class="info-row">
            <div class="info-label">받는 사람</div>
            <input type="text" v-model="buyerRecipientName" class="input-field" readonly>
          </div>
          <div class="info-row">
            <div class="info-label">휴대폰 번호</div>
            <input type="text" v-model="buyerPhoneNumber" class="input-field" readonly>
          </div>
          <div class="info-row">
            <div class="info-label">주소</div>
            <input type="text" v-model="buyerAddress" class="input-field" readonly>
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
        <div class="product-container">
          <div v-if="orderItems.length === 0">
            <p>주문할 상품이 없습니다. 장바구니를 확인해주세요.</p>
          </div>
          <div v-for="item in orderItems" :key="item.productId" class="order-item">
            <div class="product-item">
              <div class="item-thumbnail">
                <a :href="`/product/${item.productId}`">
                  <img :src="`/images/products/0${item.productId}/${item.productId}_thumbnail.webp`" alt="상품 썸네일" />
                </a>
              </div>
              <div class="item-description">
                <div class="brand-name2">{{ item.brandName }}</div>
                <div class="product-name">{{ item.productName }}</div>
                <div class="product-variant">{{ item.quantity }}개</div>
              </div>
              <div class="item-price">
                <strong>{{ formatPrice(item.productPrice) }}원</strong>
              </div>
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
            <img src="/images/icon/card-image.png" alt="Credit/Debit Card" class="method-icon">
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
  import { ref, computed } from 'vue';
  import { useRouter } from 'vue-router';
  import "@/assets/styles/order.css";
  
  const router = useRouter();
  
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
  
  const orderItems = ref([]);
  const selectedPaymentMethod = ref('');
  const agreements = ref([
    { text: '주문상품정보 및 서비스 이용약관에 동의', checked: false },
    { text: '구매조건 확인 및 결제 진행 동의(필수)', checked: false },
    { text: '개인정보 제 3자 제공 동의(필수)', checked: false },
    { text: '위 주문 내용을 확인하였으며, 결제에 모두 동의합니다', checked: false }
  ]);
  
  const formatPrice = (price) => {
    return price.toLocaleString();
  };
  
  const totalPrice = computed(() => {
    return orderItems.value.reduce((sum, item) => sum + (item.productPrice * item.quantity), 0);
  });
  
  const totalQuantity = computed(() => {
    return orderItems.value.reduce((sum, item) => sum + item.quantity, 0);
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
    alert('결제가 진행됩니다.');
  };
  
  const navigateToAddAddress = () => {
    router.push({ path: '/add-address' });
  };
  </script>
  

