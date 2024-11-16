<template>
    <div class="container">
      <div class="image-section">
        <img :src="productImage" alt="상품 썸네일" />
      </div>
      <div class="details-section">
        <div>
          <div class="brand">{{ product.brandName }}</div>
          <div class="product-title">{{ product.productName }}</div>
          <div class="price-section">
            <span class="price" v-if="product.productPrice !== undefined">{{ product.productPrice.toLocaleString() }}원</span>
            <span v-else>가격 정보 없음</span>
          </div>
          <div class="quantity-section">
            <label for="quantity" class="quantity-label">수량</label>
            <select v-model.number="quantity" id="quantity" class="quantity-select">
              <option v-for="n in 10" :key="n" :value="n-1">{{ n-1 }}</option>
            </select>
          </div>
          <div v-if="quantity > 0" class="selected-summary-info">
            <div class="selected-summary">
              <p>선택한 수량 {{ quantity }}개</p>
              <p><strong style="font-size: 1.5em;">총 구매 금액: {{ formattedTotalPrice }}</strong></p>
            </div>
            <button @click="resetQuantity" class="btn btn-reset">X</button>
          </div>
        </div>
        
        <div class="buttons">
          <button @click="addToCart" class="btn btn-cart">장바구니</button>
          <button @click="buyNow" class="btn btn-buy">구매하기</button>
        </div>
      </div>
    </div>
    <div class="container2">
    <div class="container">
      <div class="tab-section">
        <div :class="{ active: activeTab === 'details' }" @click="activeTab = 'details'">상세정보</div>
        <div :class="{ active: activeTab === 'reviews' }" @click="activeTab = 'reviews'">리뷰</div>
        <div :class="{ active: activeTab === 'qna' }" @click="activeTab = 'qna'">상품 Q&A</div>
        <div :class="{ active: activeTab === 'seller' }" @click="activeTab = 'seller'">판매자 정보</div>
      </div>
    </div>
    <div class="container">
      <div v-if="activeTab === 'details'" class="tab-content" id="details">
        <img :src="productImage + '_detail_1.webp'" alt="상품사진1" />
        <img :src="productImage + '_detail_2.webp'" alt="상품사진2" />
        <img :src="productImage + '_detail_3.webp'" alt="상품사진3" />
        <img :src="productImage + '_detail_4.webp'" alt="상품사진4" />
      </div>
      <div v-if="activeTab === 'reviews'" class="tab-content" id="reviews">
        <div class="review-filters">
          <button class="filter-button" @click="sortReviews('latest')">최신순</button>
          <button class="filter-button" @click="sortReviews('highRating')">별점 높은 순</button>
          <button class="filter-button" @click="sortReviews('lowRating')">별점 낮은 순</button>
        </div>
        <div v-if="reviews.length === 0">
          <p>작성된 리뷰가 없습니다.</p>
        </div>
        <div v-else class="review-list">
          <div v-for="review in reviews" :key="review.id" class="review-item">
            <div class="review-header">
              <span class="review-rating">{{ review.rating }}점</span>
              <span class="review-user">{{ review.userId }}</span>
              <span class="review-date">{{ formatReviewDate(review.createdAt) }}</span>
            </div>
            <div class="review-content">
              <p>{{ review.reviewText }}</p>
            </div>
          </div>
        </div>
      </div>
      <div v-if="activeTab === 'qna'" class="tab-content" id="qna">
        <p>상품 Q&A 내용이 여기에 표시됩니다.</p>
      </div>
      <div v-if="activeTab === 'seller'" class="tab-content" id="seller">
        <p>판매자 정보가 여기에 표시됩니다.</p>
      </div>
    </div>
  </div>
    <div class="bottom-fixed-bar">
      <div class="product-info">
        <img :src="productThumbnail" alt="상품 이미지" />
        <div>
          <div class="product-description">{{ product.brandName }}</div>
          <div class="product-description">{{ product.productName }}</div>
        </div>  
      </div>
      <div class="quantity-section">
        <p>선택한 수량: {{ quantity }}개</p>
      </div>
      <div class="total-price">총 상품 금액 {{ formattedTotalPrice }}</div>
      <div class="buttons">
        <button @click="addToCart" class="btn btn-cart">장바구니</button>
        <button @click="buyNow" class="btn btn-buy">구매하기</button>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, computed, onMounted } from 'vue';
  import axios from 'axios';
  import { useRoute } from 'vue-router';
  import "@/assets/styles/product.css";
  
  const API_BASE_URL = 'http://localhost:8081/api/products';

  const activeTab = ref('details');

  const product = ref({});
  const reviews = ref([]); 
  const route = useRoute(); 
  const productId = ref(route.params.productId);

  const fetchProduct = async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/${productId.value}`);
      product.value = response.data;
    } catch (error) {
      console.error("상품 데이터를 불러오는데 실패했습니다.", error);
    }
  };

  onMounted(() => {
    fetchProduct();
  });

  const productImage = computed(() => `/images/products/0${product.value.productId}/${product.value.productThumbnail}`);
  const productThumbnail = computed(() => `/images/products/0${product.value.productId}/${product.value.productId}_thumbnail.webp`);

  const formatReviewDate = (date) => {
    const options = { year: '2-digit', month: '2-digit', day: '2-digit' };
    return new Date(date).toLocaleDateString('ko-KR', options);
  };
  const quantity = ref(0);
  const quantityBottom = ref(0);
  
  const totalPrice = computed(() => product.value.productPrice * quantity.value);
  const formattedTotalPrice = computed(() => `${totalPrice.value ? totalPrice.value.toLocaleString() : '0'}원`);
  
  const updateQuantity = (newQuantity) => {
    quantity.value = newQuantity;
    quantityBottom.value = newQuantity;
  };

  const resetQuantity = () => {
    quantity.value = 0;
  };
  
  const addToCart = async () => {
    try {
    const userId = 1;
    const API_BASE_URL = 'http://localhost:8081/api/cart';
    const response = await axios.post(
      `${API_BASE_URL}/add/product/${product.value.productId}?quantity=${quantity.value}&userId=${userId}`
    );
     
      alert('장바구니에 추가되었습니다.');
      window.location.href = '/cart';
    } catch (error) {
      console.error("옷장에 추가 실패:", error);
        alert('장바구니에 추가하는 데 실패했습니다.');
    }
  };
  
  // const updateCartQuantity = async (additionalQuantity) => {
  //   try {
  //     await axios.put('/cart/update', {
  //       productId: product.value.productId,
  //       additionalQuantity: additionalQuantity
  //     });
  //     alert('장바구니 수량이 업데이트되었습니다.');
  //     window.location.href = '/cart';
  //   } catch (error) {
  //     alert('장바구니 수량 업데이트에 실패했습니다.');
  //   }
  // };
  
  const buyNow = async () => { 
  try {
    const API_BASE_URL = 'http://localhost:8081/api/products';
    // 주문 정보 API 호출
    const response = await axios.post(`${API_BASE_URL}/buy?productId=${product.value.productId}&quantity=${quantity.value}`);

    // API 호출 결과로부터 OrderDTO 객체를 반환받음
    const orderDTO = response.data;

    // 주문 페이지로 이동 시 주문 정보를 URL 파라미터에 추가하여 전달
    const orderParams = new URLSearchParams({
      itemId: orderDTO.itemId,
      itemName: orderDTO.itemName,
      brandName: orderDTO.brandName,
      quantity: orderDTO.quantity,
      price: orderDTO.price,
      totalPrice: orderDTO.totalPrice,
      itemType: 'product'  // 상품인지 코디인지 구분을 위한 파라미터
    });

    alert('주문 페이지로 이동합니다.');
    window.location.href = `/order?${orderParams.toString()}`;
  } catch (error) {
    alert('구매 페이지로 이동하는 데 실패했습니다.');
    console.error("주문 오류:", error);
  }
};

</script>
  