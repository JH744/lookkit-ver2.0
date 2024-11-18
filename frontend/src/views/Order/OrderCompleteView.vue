<template>
  <div class="order-complete">
    <div class="order-info">
      <div class="order-header">
        <div class="heading">
          <div class="order-complete-text">주문이 완료되었습니다</div>
        </div>
        <div class="order-number-container">
          <div class="order-number-wrapper">
            <div class="order-number">주문번호: <span>{{ orderId }}</span></div>
          </div>
        </div>
        <div class="order-links">
          <button class="button view-order-link" @click="viewOrder">
            <span>구매내역 보기</span>
          </button>
          <button class="button home-link" @click="goHome">
            <span>쇼핑 홈 가기</span>
          </button>
        </div>
      </div>
      <div class="product-info" v-if="orderItems.length > 0">
        <div class="product-header">
          <div class="product-title">상품 정보</div>
        </div>
        <div class="product-list">
          <div v-for="item in orderItems" :key="item.orderItemId" class="product-item">
            <div class="product-price">
              <div class="price-amount">{{ formatPrice(item.productPrice) }}원</div>
            </div>
            <div class="product-details-wrapper">
              <div class="product-image-wrapper">
                <img class="product-image" :src="getImageUrl(item)" alt="상품 이미지" />
              </div>
              <div class="product-description">
                <div class="product-brand-name">{{ item.brandName }}</div>
                <div class="product-name">{{ item.productName }}</div>
                <div class="product-variant">{{ item.quantity }}개</div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div v-else>
        <p>주문한 상품 정보가 없습니다.</p>
      </div>
      <div class="shipping-info">
        <div class="shipping-header">
          <div class="shipping-info-title">배송지 정보</div>
        </div>
        <div class="shipping-table">
          <div class="shipping-row" v-for="(value, label, index) in shippingInfo" :key="index">
            <div class="shipping-cell">{{ label }}</div>
            <div class="shipping-data">
              <span class="span shipping-value">{{ value }}</span>
            </div>
          </div>
        </div>
      </div>
      <div class="payment-info">
        <div class="payment-header">
          <div class="payment-info-title">결제 정보</div>
        </div>
        <div class="payment-body">
          <div class="payment-row">
            <div class="payment-cell">결제수단</div>
            <div class="payment-data">
              <span class="span payment-value">{{ paymentMethod }}</span>
            </div>
          </div>
        </div>
      </div>
      <div class="final-payment">
        <div class="final-payment-header">
          <div class="final-payment-title">최종 결제금액</div>
        </div>
        <div class="final-payment-border">
          <div class="final-payment-table">
            <div class="final-payment-body">
              <div class="final-payment-row">
                <div class="final-payment-cell">총 상품금액</div>
                <div class="final-payment-data">
                  <span class="span final-payment-amount">{{ formatPrice(totalAmount) }}</span>
                </div>
              </div>
            </div>
            <div class="final-payment-footer">
              <div class="final-payment-row">
                <div class="final-payment-cell">최종 결제금액</div>
                <div class="final-payment-data">
                  <span class="span final-payment-amount">{{ formatPrice(finalAmount) }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import "@/assets/styles/orderComplete.css";

const route = useRoute();
const router = useRouter();

const orderId = ref('');
const orderItems = ref([]);
const shippingInfo = ref({});
const paymentMethod = ref('카드');
const totalAmount = ref(0);
const finalAmount = ref(0);

const fetchOrderData = async () => {
  const orderIdFromParams = route.query.orderId;
  if (!orderIdFromParams) {
    alert("주문 정보가 없습니다.");
    return;
  }

  orderId.value = orderIdFromParams;

  try {
    const response = await fetch(`http://localhost:8081/api/order/orderComplete?orderId=${orderId.value}`);
    if (!response.ok) {
      throw new Error('네트워크 응답에 문제가 있습니다.');
    }
    const orderData = await response.json();

    // API로부터 가져온 주문 데이터를 화면에 반영
    orderId.value = orderData.orderId;
    paymentMethod.value = '카드';  // 현재는 '카드'로 고정
    totalAmount.value = orderData.totalAmount;
    finalAmount.value = orderData.totalAmount;

    if (orderData.orderDetails && Array.isArray(orderData.orderDetails)) {
      orderItems.value = orderData.orderDetails.map((item) => ({
        orderItemId: item.orderItemId,
        productId: item.productId,
        codiId: item.codiId,
        productName: item.productId ? "상품명 불러오기 필요" : "코디 이름 불러오기 필요", 
        quantity: item.quantity,
        rentalStartDate: item.rentalStartDate,
        rentalEndDate: item.rentalEndDate,
        productPrice: item.productPrice || 10000 // 예시용 가격 또는 실제 데이터 사용
      }));
    }

    shippingInfo.value = {
      '배송지명': orderData.orderAddress,
      '받는 사람': orderData.orderAddressee,
      '휴대폰번호': orderData.orderPhone,
      '주소': orderData.orderAddress,
    };
  } catch (error) {
    console.error('주문 정보를 불러오는 데 실패했습니다:', error);
    alert('주문 정보를 불러오는 데 실패했습니다.');
  }
};

const formatPrice = (price) => {
  return price ? price.toLocaleString() : '0';
};

const getImageUrl = (item) => {
  if (item.productId) {
    return `/images/products/0${item.productId}/${item.productId}_thumbnail.webp`;
  } else if (item.codiId) {
    return `/images/codis/0${item.codiId}/${item.codiId}_thumbnail.webp`;
  }
  return ''; // 이미지가 없는 경우 빈 문자열 반환
};

const viewOrder = () => {
  router.push('/mypage/manage');
};

const goHome = () => {
  router.push('/main');
};

onMounted(() => {
  fetchOrderData();
});
</script>
