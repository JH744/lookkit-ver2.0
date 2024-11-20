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
        <p class="shipment-header-item" id="space-header">상품 정보</p>
        <p class="shipment-header-item">진행 상태</p>
        <p class="shipment-header-item">대여 기간</p>
        <p class="shipment-header-item">구매확정/리뷰</p>
      </div>
      <div v-for="(order, index) in productsGroupedByOrderId" :key="index">
        <div class="shipment-info">
          <div class="shipment-info-item">
            <span class="shipment-label">주문일자 </span>
            <span class="shipment-value">{{ formatDate(order.orderDate) }}</span>
          </div>
          <div class="shipment-info-item">
            <span class="shipment-label">주문번호 </span>
            <span class="shipment-value">{{ order.orderId }}</span>
          </div>
        </div>
        <div v-for="(product) in order.products" :key="product.orderItemId" class="shipment-product">
          <img class="product-image" :src="product.thumbnailUrl || '/images/placeholder.png'" alt="상품 이미지" />
          <div class="product-details">
            <div class="product-brand">{{ product.brandName || '브랜드 정보 없음' }}</div>
            <div class="product-name">{{ product.productName }}</div>
            <div class="product-price">{{ formatPrice(product.productPrice) }}원 / 수량: {{ product.quantity }}</div>
          </div>
          <div class="status-section-text">
            <div class="status-text">{{ getOrderStatusText(product.orderStatus) }}</div>
            <router-link class="inquiry-button" to="/mypage/inquiry/create">1:1 문의하기</router-link>
          </div>
          <div class="rental-grid">
            <p v-show="product.rentalStartDate">{{ product.rentalStartDate }}</p>
            <img v-show="product.rentalStartDate && product.rentalEndDate" width="18px" src="/images/under-arrow.png" alt="기간 화살표">
            <p v-show="product.rentalEndDate">{{ product.rentalEndDate }}</p>
          </div>
          <div class="actions">
            <template v-if="!product.isPurchaseConfirmed">
              <button v-if="product.codiId === null" class="confirm-button" @click="showConfirmModal(product)">구매 확정</button>
              <button v-else class="confirm-button" @click="showConfirmModal(product)">대여 확정</button>
            </template>
            <span v-else class="confirmed-text">
              {{ product.codiId === null ? '구매 확정' : '대여 확정' }}
            </span>
            <a class="review-button" href="/mypage/inquiry/new">리뷰 작성</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import axios from 'axios';
import { format } from 'date-fns';
import { useModalStore, useConfirmModalStore } from '@/stores/modalStore';
import { useAuthStore } from '@/stores/authStore';
import { getDownloadURL, ref as firebaseRef } from "firebase/storage";
import { firebaseStorage } from "@/firebase/firebaseConfig";

const products = ref([]);
const authStore = useAuthStore();

// 주문을 주문번호별로 그룹화하는 계산 속성
const productsGroupedByOrderId = computed(() => {
  return products.value.reduce((acc, product) => {
    if (!acc[product.orderId]) {
      acc[product.orderId] = {
        orderDate: product.orderDate,
        orderId: product.orderId,
        products: [],
      };
    }
    acc[product.orderId].products.push(product);
    return acc;
  }, {});
});


// 상태별 주문 개수 계산
const counts = computed(() => {
  return products.value.reduce((acc, product) => {
    if (product.orderStatus === 'pending') acc.countPending++;
    if (product.orderStatus === 'shipped') acc.countShipped++;
    if (product.orderStatus === 'delivered') acc.countDelivered++;
    if (product.orderStatus === 'completed') acc.countCompleted++;
    return acc;
  }, { countPending: 0, countShipped: 0, countDelivered: 0, countCompleted: 0 });
});

// 주문 상태 텍스트 반환
const getOrderStatusText = (status) => {
  switch (status) {
    case 'pending': return '결제 완료';
    case 'shipped': return '배송 준비중';
    case 'delivered': return '배송 중';
    case 'completed': return '배송 완료';
    default: return '배송 상태 없음';
  }
};

// 대여 확정 처리 함수
const showConfirmModal = (product) => {
  const confirmModalStore = useConfirmModalStore();
  if(product.codiId===null){
    confirmModalStore.showModal(
      '구매 확정',
      '구매를 확정 하시겠습니까?',
      '구매 확정시 반품 할 수 없습니다',
      '구매 확정',
      () => confirmRental(product)
    );
  } else {
    confirmModalStore.showModal(
      '대여 확정',
      '대여를 확정 하시겠습니까?',
      '대여 확정시 대여 취소를 할 수 없습니다',
      '대여확정',
      () => confirmRental(product)
    );
  }
  

};

const confirmRental = async (product) => {
  product.isPurchaseConfirmed = true;
  try {
    await axios.patch('http://localhost:8081/api/mypage/manage', {
      orderId: product.orderId,
      productId: product.productId
    });
    const modalStore = useModalStore();
    if(product.codiId === null) {
      modalStore.showModal('구매 확정', '감사합니다. 멋진 소개팅하세요 :)');
    } else {
      modalStore.showModal('대여 확정', '감사합니다. 멋진 소개팅하세요 :)');
    }

  } catch (error) {
    console.log(error);
  }
};

const fetchImageForProduct = async (product) => {
  const storagePath = product.productId ? `lookkit/products/0${product.productId}/${product.productId}_thumbnail.webp` : `lookkit/codi/0${product.codiId}/${product.codiId}_thumbnail.webp`;
  console.log('이미지 경로 확인:', storagePath);
  try {
    const imageRef = firebaseRef(firebaseStorage, storagePath);
    const url = await getDownloadURL(imageRef);
    product.thumbnailUrl = url;
  } catch (error) {
    console.error(`이미지 가져오기 실패: ${storagePath}`, error);
    product.thumbnailUrl = '/images/placeholder.png';
  }
};

// 주문 정보 불러오기 및 이미지 설정
const loadOrder = async () => {
  try {
    const response = await axios.get(`http://localhost:8081/api/mypage/manage/${authStore.user.userId}`);
    products.value = response.data.data.products.flatMap(order => order.orderDetails.map(detail => ({
      ...detail,
      orderId: order.orderId,
      orderDate: order.orderDate,
      orderStatus: order.orderStatus
    })));

    // 각 제품에 대해 Firebase 이미지 불러오기 호출
    for (let product of products.value) {
      await fetchImageForProduct(product);
    }
  } catch (error) {
    console.log(error);
  }
};

const formatDate = (dateString) => {
  return format(new Date(dateString), 'yyyy-MM-dd');
};

const formatPrice = (price) => {
  return price ? price.toLocaleString() : '0';
};

onMounted(() => {
  loadOrder();
});
</script>

  
  <style scoped>
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
    color: black;
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

.shipment-header p:nth-child(2) {
    margin-left: 104px;
}
.shipment-header p:nth-child(3) {
    margin-left: 65px;
}
.shipment-header p:nth-child(4) {
    margin-left: 51px;
}

.shipment-info {
    display: flex;
    gap: 35px;
    padding-left: 23px;
    padding-top: 17px;
    border-bottom: 1px solid #9e9e9e;
    background: #f3f3f3;
    padding-bottom: 17px;
    color: #404040;
    font-size: 17px;
}
.shipment-value {
    font-weight: 700;
}
.shipment-product {
    display: flex;
    justify-content: space-around;
    border-bottom: 1px solid #9e9e9e;
    padding: 40px 5px;
    align-items: center;
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
    white-space: nowrap; 
    overflow: hidden; 
    text-overflow: ellipsis; 
    max-width: 150px;
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
    gap : 10px;
}
.status-date {
    font-size: 14px;
    font-weight: 500;
    color: #555;
}
.status-text {
    font-size: 18px;
    font-weight: 600;
}
.inquiry-button {
    background-color: #ffffff;
    border: 1px solid #d4c9c9;
    font-size: 13px;
    font-weight: 400;
    padding: 7px 10px;
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
    width: 115px;
    color: #ffffff;
    font-size: 12px;
    font-weight: 600;
    padding: 10px 20px;
    cursor: pointer;
    border: none;
}
.review-button {
    background-color: #ffffff;
    width: 115px;
    border: 1px solid #d4c9c9;
    font-size: 12px;
    font-weight: 600;
    padding: 10px 20px;
    cursor: pointer;
    text-align: center;
}
.confirmed-text {
    text-align: center;
    color: #ff5e00;
    font-size: 14px;
    font-weight: bold;
}

#space-header {
  margin-right: 150px;
  margin-left: 160px;
}

.rental-grid {
  display: grid;
  align-items: center;
  justify-items: center;
  gap: 10px;
  min-width: 75px;
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
  