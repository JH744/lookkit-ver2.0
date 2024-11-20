<template>
  <h2 class="product-amount">PRODUCT(<span>{{ wishlist.length }}</span>)</h2>
  <div class="product-grid">
    <!-- Product Item 반복 출력 -->
    <div class="product-item" v-for="wish in wishlist" :key="wish.productId">
      <img class="product-image" :src="`/images/products/0${wish.productId}/${wish.productThumbnail}`" />
      <div class="fix-height">
        <h3 class="product-brand">{{ wish.brandName }}</h3>
        <p class="product-name">{{ wish.productName }}</p>
      </div>
      <p class="product-price">
        <span class="price-detail">{{ formatPrice(wish.productPrice) }}</span> 원
      </p>
      <div class="product-likes">
        <img class="like-icon" :src="wish.isLiked ? '/images/like.png' : '/images/dislike.png'" 
             alt="Like Icon" 
             @click="toggleLike(wish)"/>
        <span>{{ wish.likeCount }}</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { toggleLike } from '../../utils/likeFunction';
import { useAuthStore } from '@/stores/authStore';

const authStore = useAuthStore();
const wishlist = ref([]);

// 숫자 포맷 함수
function formatPrice(price) {
  return price.toLocaleString('ko-KR');
}

const loadWishlist = async () => {
  try {
    const response = await axios.get(`http://localhost:8081/api/mypage/wishlist/${authStore.user.userId}`);
    wishlist.value = response.data.data.map(item => ({
      ...item,
      isLiked: true,
    }));
  } catch (error) {
    console.error('Error loading:', error);
  }
}

onMounted(() => {
  loadWishlist();
});
</script>

<style scoped>
  .product-grid {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 20px;
    width: -webkit-fill-available;
  }

  .product-amount {
    font-size: 24px;
    font-weight: bold;
    margin-bottom: 70px;
    margin-top: 90px;
    text-align: center;
    min-width: 800px;
  }
  
  .product-item {
    border-radius: 10px;
  }
  
  .product-image {
    width: 100%;
    height: auto;
    border-radius: 3px;
  }
  
  .product-brand {
    font-size: 15px;
    font-weight: bold;
    margin-top: 3px;
    margin-bottom: 3px;
    text-decoration: underline;
  }
  
  .product-name {
    font-size: 13px;
    color: #454545;
    margin-top: 7px;
  }
  
  .product-price {
    font-size: 16px;
    color: #000;
    margin-top: 15px;
    margin-bottom: 15px;
  }
  
  .price-detail {
    font-size: 18px;
    font-weight: bold;
  }
  
  .product-likes {
    display: flex;
    align-items: center;
  }
  
  .product-likes img.like-icon {
    width: 20px;
    height: 20px;
    margin-right: 5px;
  }
  .like-icon:hover {
    cursor: pointer;
  }
  
  .product-likes span {
    font-size: 16px;
    color: #e74c3c;
  }

  .fix-height {
    height: 50px;
  }
  </style>
  