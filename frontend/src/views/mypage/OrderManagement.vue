<template>
    <div class="order-container">
      <div class="status-section">
        <div class="status-title">진행중인 주문</div>
        <div class="status-box">
          <div class="status-icons">
            <div class="step">
              <div class="circle-container">
                <div :class="['step-circle', counts.countPending > 0 ? 'blue-circle' : 'white-circle']"></div>
                <p :class="['step-number', counts.countPending > 0 ? 'white-text' : 'gray-text']">{{ counts.countPending }}</p>
              </div>
              <span class="step-description">결제 완료</span>
            </div>
            <img class="status-icon" src="/images/arrow.png" />
            <div class="step">
              <div class="circle-container">
                <div :class="['step-circle', counts.countShipped > 0 ? 'blue-circle' : 'white-circle']"></div>
                <p :class="['step-number', counts.countShipped > 0 ? 'white-text' : 'gray-text']">{{ counts.countShipped }}</p>
              </div>
              <span class="step-description">배송 준비</span>
            </div>
            <img class="status-icon" src="/images/arrow.png" />
            <div class="step">
              <div class="circle-container">
                <div :class="['step-circle', counts.countDelivered > 0 ? 'blue-circle' : 'white-circle']"></div>
                <p :class="['step-number', counts.countDelivered > 0 ? 'white-text' : 'gray-text']">{{ counts.countDelivered }}</p>
              </div>
              <span class="step-description">배송중</span>
            </div>
            <img class="status-icon" src="/images/arrow.png" />
            <div class="step">
              <div class="circle-container">
                <div :class="['step-circle', counts.countCompleted > 0 ? 'blue-circle' : 'white-circle']"></div>
                <p :class="['step-number', counts.countCompleted > 0 ? 'white-text' : 'gray-text']">{{ counts.countCompleted }}</p>
              </div>
              <span class="step-description">배송 완료</span>
            </div>
          </div>
        </div>
      </div>
      <div class="shipment-section">
        <div class="shipment-title">주문 배송 조회</div>
        <div class="shipment-header">
          <p class="shipment-header-item">상품 정보</p>
          <p class="shipment-header-item">진행 상태</p>
          <p class="shipment-header-item">구매확정 및 리뷰</p>
        </div>
        <div v-for="(product, index) in products" :key="product.productId">
          <div v-if="isFirstProduct(index)" class="shipment-info">
            <div class="shipment-info-item">
              <span class="shipment-label">주문일자</span>
              <span class="shipment-value">{{ product.orderDate }}</span>
            </div>
            <div class="shipment-info-item">
              <span class="shipment-label">주문번호</span>
              <span class="shipment-value">{{ product.orderId }}</span>
            </div>
          </div>
          <div class="shipment-product">
            <img class="product-image" :src="`/images/products/0${product.productId}/${product.productThumbnail}`" />
            <div class="product-details">
              <div class="product-brand">{{ product.brandName }}</div>
              <div class="product-name">{{ product.productName }}</div>
              <div class="product-price">{{ product.productPrice }}원 / 수량: {{ product.quantity }}</div>
            </div>
            <div class="status-section-text">
              <div class="status-text">{{ getOrderStatusText(product.orderStatus) }}</div>
              <button class="inquiry-button">1:1 문의하기</button>
            </div>
            <div class="actions">
              <button v-if="!product.isPurchaseConfirmed" class="confirm-button" @click="confirmPurchase(product.orderId, product.productId)">구매확정</button>
              <a v-else class="review-button" href="/mypage/inquiry/new">리뷰 작성</a>
              <span v-if="product.isPurchaseConfirmed" class="confirmed-text">구매확정</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, computed } from 'vue';
  
  // API 또는 props를 통해 서버에서 받아올 데이터
  const products = ref([]);
  
  // counts는 상태별로 주문의 개수를 의미합니다.
  const counts = computed(() => {
    const countPending = products.value.filter(product => product.orderStatus === 'pending').length;
    const countShipped = products.value.filter(product => product.orderStatus === 'shipped').length;
    const countDelivered = products.value.filter(product => product.orderStatus === 'delivered').length;
    const countCompleted = products.value.filter(product => product.orderStatus === 'completed').length;
    return { countPending, countShipped, countDelivered, countCompleted };
  });
  
  const isFirstProduct = (index) => {
    return index === 0 || products.value[index].orderId !== products.value[index - 1].orderId;
  };
  
  const getOrderStatusText = (status) => {
    switch (status) {
      case 'pending': return '결제 완료';
      case 'shipped': return '배송 준비중';
      case 'delivered': return '배송 중';
      case 'completed': return '배송 완료';
      default: return '배송 상태 없음';
    }
  };
  
  const confirmPurchase = (orderId, productId) => {
    // 구매확정 처리 로직
    console.log(`구매확정: 주문번호 ${orderId}, 상품번호 ${productId}`);
  };
  </script>
  
  <style scoped>
/* .order-container {
    width: 80%;
    margin-top: 25px;
    margin-left: 50px;
} */

.status-section,
.status-section * {
    box-sizing: border-box;
}
.status-section {
    width: 100%;
    height: 177px;
    margin-bottom: 30px;
}
.status-title {
    color: #363636;
    text-align: left;
    font-family: "Inter-Regular", sans-serif;
    font-size: 18px;
    font-weight: 400;
    display: flex;
    align-items: center;
    justify-content: flex-start;
    margin-bottom: 10px;
}
.status-box {
    border-top: 1px solid gray;
    border-bottom: 1px solid gray;
    text-align: center;
}

.status-card {
    width: 189px;
    height: 15px;
}

.status-icons {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 45px;
    margin: 20px 0px;
}
.status-icon {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 45px;
    margin: 35px 0px;
}

.step {
    display: grid;
}
.step-indicator {
    width: 41px;
    height: 45px;
    position: static;
}
.step-circle {
    width: 50px;
    height: 50px;
    background-color: #a5cfeb;
    border-radius: 50%;
}

.circle-container {
    display: flex;
    margin-bottom: 13px;
    gap: 10px;
    align-items: center;
    justify-content: center;
}
.step-number {
    position: absolute;
    font-size: 24px;
    margin: 0;
    font-weight: bold;
    text-align: center;
}
.step-description {
    color: #555353;
    font-size: 16px;
    align-items: center;
}
.shipment-section {
    width: 100%;
    margin: 0 auto;
    font-family: "Inter", sans-serif;
    color: #363636;
    padding: 10px 10px 10px 0;
    box-sizing: border-box;
}
.shipment-title {
    color: #363636;
    text-align: left;
    font-family: "Inter-Regular", sans-serif;
    font-size: 18px;
    font-weight: 400;
    margin-bottom: 15px;
}
.shipment-header {
    display: flex;
    border-top: 2px solid #000;
    padding: 20px 0px;
    border-bottom: 1px solid #9e9e9e;
}
.shipment-header-item {
    font-size: 16px;
    font-weight: 500;
    margin-left: 95px;
}

.shipment-header p:nth-child(2) {
    margin-left: 315px;
}

.shipment-header p:nth-child(3) {
    margin-left: 106px;
}
.shipment-info {
    display: flex;
    gap: 35px;
    padding-left: 23px;
    padding-top: 20px;
    border-bottom: 1px solid #9e9e9e;
    background: #f3f3f3;
    padding-bottom: 20px;
}
.shipment-info-item {
    font-size: 16px;
    font-weight: 400;
}
.shipment-label {
    font-weight: 500;
}
.shipment-value {
    font-weight: 700;
}
.shipment-product {
    display: flex;
    justify-content: space-around;
    border-bottom: 1px solid #9e9e9e;
    padding: 40px 40px;
}

.product-image {
    width: 100px;
    height: 100px;
    object-fit: cover;
}

.product-item {
    display: flex;
}

.product-details {
    display: flex;
    flex-direction: column;
    place-self: center;
    min-width: 250px;
}
.product-brand {
    font-size: 15px;
    font-weight: 500;
}
.product-name {
    font-size: 13px;
    font-weight: 700;
    margin-top: 5px;
}
.product-price {
    font-size: 13px;
    font-weight: 500;
    color: #555;
    margin-top: 23px;
}
.status-section-text {
    width: 110px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-around;
}
.status-date {
    font-size: 14px;
    font-weight: 500;
    color: #555;
}
.status-text {
    font-size: 20px;
    font-weight: 600;
}
.inquiry-button {
    background-color: #ffffff;
    border: 1px solid #d4c9c9;
    font-size: 13px;
    font-weight: 400;
    padding: 7px 15px;
    cursor: pointer;
}
.actions {
    display: flex;
    gap: 8px;
    flex-direction: column;
    justify-content: center;
}
.confirm-button {
    background-color: #0d1134;
    width: 130px;
    color: #ffffff;
    font-size: 12px;
    font-weight: 600;
    padding: 10px 20px;
    cursor: pointer;
    border: none;
}
.review-button {
    background-color: #ffffff;
    width: 88px;
    border: 1px solid #d4c9c9;
    font-size: 12px;
    font-weight: 600;
    padding: 10px 20px;
    cursor: pointer;
    text-align: center;
}
.confirmed-text {
    text-align: center;
    color: #7c7c7c;
    font-size: 14px;
    font-weight: bold;
}

/*조건에 따라 색 변경*/
.white-circle {
    background-color: white;
}

.blue-circle {
    background-color: #8cc6f4;
}

.white-text {
    color : white;
}

.gray-text {
    color : gray;
}

/*구매확정 모달창*/
.custom-popup {
    background-color: #f9f9f9;  /* 모달 배경색 */
    border-radius: 10px;  /* 모서리 둥글게 */
    padding: 20px;  /* 패딩 조정 */
}

.custom-title {
    color: #333;  /* 제목 텍스트 색상 */
    font-size: 23px;  /* 제목 크기 */
    font-weight: bold;  /* 제목 굵게 */
}

.custom-html {
    color: #ff0000;  /* 본문 텍스트 색상 */
    font-size: 16px;  /* 본문 텍스트 크기 */
}

.custom-confirm-button {
    background-color: #1a202c;  /* 확인 버튼 색상 */
    color: #fff;  /* 확인 버튼 텍스트 색상 */
    padding: 10px 20px;  /* 확인 버튼 패딩 */
    border-radius: 5px;  /* 확인 버튼 둥근 모서리 */
}

.custom-cancel-button {
    background-color: #ffffff;  /* 취소 버튼 배경 하얀색 */
    color: #333;  /* 취소 버튼 텍스트 색상 (검은색) */
    padding: 10px 20px;  /* 취소 버튼 패딩 */
    border: 1px solid #cccccc;  /* 취소 버튼 테두리 회색 */
    border-radius: 5px;  /* 취소 버튼 둥근 모서리 */
}

.custom-warning {
    color: #ff0000;  /* 경고 메시지 빨간색 */
    font-size: 14px;  /* 경고 메시지 크기 작게 */
}
  </style>
  