<template>
  <div class="container">
    <div class="image-section">
      <img :src="thumbnailUrl" alt="상품 썸네일" />
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
        <div v-for="(detailUrl, index) in detailImageUrls" :key="index">
          <img :src="detailUrl" :alt="'상품사진' + (index + 1)" />
        </div>
      </div>
      <div v-if="activeTab === 'reviews'" class="tab-content" id="reviews">
        <ReviewView :productId="productId"/>
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
      <img :src="thumbnailUrl" alt="상품 이미지" />
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
import { getDownloadURL, ref as firebaseRef } from "firebase/storage";
import { firebaseStorage } from "@/firebase/firebaseConfig";
import "@/assets/styles/product.css";
import ReviewView from '@/views/Review/ReviewView.vue'; 
import { useAuthStore } from "@/stores/authStore";

const authStore = useAuthStore();
const userId = authStore.user?.userId;
const API_BASE_URL = 'http://localhost:8081/api/products';

const activeTab = ref('details');
const product = ref({});
const reviews = ref([]); 
const route = useRoute(); 
const productId = ref(route.params.productId);

const thumbnailUrl = ref(""); 
const detailImageUrls = ref([]); // 여러 상세 이미지 URL을 저장할 배열

// Firebase에서 이미지 가져오기 함수
const fetchImages = async () => {
  try {
    // 썸네일 이미지 가져오기
    const thumbnailRef = firebaseRef(firebaseStorage, `lookkit/products/0${productId.value}/${productId.value}_thumbnail.webp`);
    thumbnailUrl.value = await getDownloadURL(thumbnailRef);

    // 상세 이미지 가져오기 (최소 4장, 최대 6장)
    for (let i = 1; i <= 6; i++) {
      try {
        const detailRef = firebaseRef(firebaseStorage, `lookkit/products/0${productId.value}/${productId.value}_detail_${i}.webp`);
        const detailUrl = await getDownloadURL(detailRef);
        detailImageUrls.value.push(detailUrl);
      } catch (error) {
        console.log(`디테일 이미지 ${i} 가져오기 실패 (존재하지 않을 수 있음)`, error);
      }
    }
  } catch (error) {
    console.error("이미지 가져오기 실패:", error);
  }
};

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
  fetchImages(); // 이미지 가져오기 호출
});

const quantity = ref(0);

const totalPrice = computed(() => product.value.productPrice * quantity.value);
const formattedTotalPrice = computed(() => `${totalPrice.value ? totalPrice.value.toLocaleString() : '0'}원`);

const resetQuantity = () => {
  quantity.value = 0;
};

const addToCart = async () => {
  try {
    
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

const buyNow = async () => { 
  try {
    const response = await axios.post(`${API_BASE_URL}/buy?productId=${product.value.productId}&quantity=${quantity.value}`);

    const orderDTO = response.data;

    const orderParams = new URLSearchParams({
      itemId: orderDTO.itemId,
      itemName: orderDTO.itemName,
      brandName: orderDTO.brandName,
      quantity: orderDTO.quantity,
      price: orderDTO.price,
      totalPrice: orderDTO.totalPrice,
      itemType: 'product'
    });

    alert('주문 페이지로 이동합니다.');
    window.location.href = `/order?${orderParams.toString()}`;
  } catch (error) {
    alert('구매 페이지로 이동하는 데 실패했습니다.');
    console.error("주문 오류:", error);
  }
};
</script>
