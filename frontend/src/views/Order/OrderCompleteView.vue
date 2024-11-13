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
        <div class="product-info">
          <div class="product-header">
            <div class="product-title">상품 정보</div>
          </div>
          <div class="product-list">
            <div v-if="orderItems.length === 0">주문한 상품 정보가 없습니다.</div>
            <div v-for="item in orderItems" :key="item.productId" class="product-item">
              <div class="product-price">
                <div class="price-amount">{{ formatPrice(item.productPrice) }}원</div>
              </div>
              <div class="product-details-wrapper">
                <div class="product-image-wrapper">
                  <img class="product-image" :src="`/images/products/0${item.productId}/${item.productId}_thumbnail.webp`" alt="상품 이미지" />
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
  import { useRouter } from 'vue-router';
  import "@/assets/styles/orderComplete.css";
  
  const router = useRouter();
  
  const orderId = ref('');
  const orderItems = ref([]);
  const shippingInfo = ref({});
  const paymentMethod = ref('카드');
  const totalAmount = ref(0);
  const finalAmount = ref(0);
  
  const formatPrice = (price) => {
    return price.toLocaleString();
  };
  
  const fetchOrderData = () => {
    const storedOrderId = localStorage.getItem("latestOrderId");
    if (!storedOrderId) {
      alert("주문 정보가 없습니다.");
      return;
    }
  
    orderId.value = storedOrderId;
  
    // Mockup API request - replace this with a real API call
    fetch(`/order/complete/details/${storedOrderId}`)
      .then(response => response.json())
      .then((responseData) => {
        if (responseData) {
          const orderData = responseData.order;
          const items = responseData.orderDetails;
  
          if (orderData) {
            orderId.value = orderData.orderId;
            paymentMethod.value = orderData.paymentMethod || '카드';
            totalAmount.value = orderData.totalAmount;
            finalAmount.value = orderData.totalAmount;
  
            if (items && Array.isArray(items)) {
              orderItems.value = items;
            }
  
            shippingInfo.value = {
              '배송지명': orderData.orderAddress,
              '받는 사람': orderData.orderAddressee,
              '휴대폰번호': orderData.orderPhone,
              '주소': orderData.orderAddress,
            };
          }
        }
      })
      .catch(() => {
        alert("주문 완료 정보를 불러오는 데 실패했습니다.");
      });
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
  
 