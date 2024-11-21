<template>
  <div class="wishlist-container">
    <!-- 탭 메뉴 -->
    <div class="tabs">
      <button
        :class="{ active: currentTab === 'codi' }"
        @click="currentTab = 'codi'"
      >
        Codi ({{ codiWishlist.length }})
      </button>
      <button
        :class="{ active: currentTab === 'product' }"
        @click="currentTab = 'product'"
      >
        Product ({{ productWishlist.length }})
      </button>
    </div>

    <!-- 탭에 따른 데이터 렌더링 -->
    <div v-if="currentTab === 'codi'" class="wishlist-grid">
      <div
        class="wishlist-item"
        v-for="codi in codiWishlist"
        :key="'codi-' + codi.codiId"
      >
        <img
          class="wishlist-image"
          :src="codi.thumbnailUrl || '/images/placeholder.png'"
          alt="Codi Image"
        />
        <h3 class="wishlist-brand">{{ codi.codiName }}</h3>
        <p class="wishlist-name">{{ codi.codiDescription }}</p>
        <p class="wishlist-price">{{ formatPrice(codi.codiPrice) }} 원</p>
        <div class="wishlist-likes">
          <img
            class="like-icon"
            :src="codi.isLiked ? '/images/like.png' : '/images/dislike.png'"
            alt="Like Icon"
            @click="toggleLike(codi)"
          />
          <span>{{ codi.likeCount }}</span>
        </div>
      </div>
    </div>

    <div v-else class="wishlist-grid">
      <div
        class="wishlist-item"
        v-for="wish in productWishlist"
        :key="'product-' + wish.productId"
      >
        <img
          class="wishlist-image"
          :src="wish.thumbnailUrl || '/images/placeholder.png'"
          alt="Product Image"
        />
        <h3 class="wishlist-brand">{{ wish.brandName }}</h3>
        <p class="wishlist-name">{{ wish.productName }}</p>
        <p class="wishlist-price">{{ formatPrice(wish.productPrice) }} 원</p>
        <div class="wishlist-likes">
          <img
            class="like-icon"
            :src="wish.isLiked ? '/images/like.png' : '/images/dislike.png'"
            alt="Like Icon"
            @click="toggleLike(wish)"
          />
          <span>{{ wish.likeCount }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";
import { useAuthStore } from "@/stores/authStore";
import { getDownloadURL, ref as firebaseRef } from "firebase/storage";
import { firebaseStorage } from "@/firebase/firebaseConfig";
import { toggleLike } from '../../utils/likeFunction';

// 탭 상태 관리
const currentTab = ref("codi"); // 초기 탭은 "codi"

// 데이터 관리
const authStore = useAuthStore();
const wishlist = ref([]);
const productWishlist = ref([]);
const codiWishlist = ref([]);

// 숫자 포맷 함수
function formatPrice(price) {
  return price.toLocaleString("ko-KR");
}

// Firebase 이미지 로드 함수
const fetchImageForItem = async (item) => {
  const storagePath = item.productId
    ? `lookkit/products/0${item.productId}/${item.productId}_thumbnail.webp`
    : `lookkit/codi/0${item.codiId}/${item.codiId}_thumbnail.webp`;
  try {
    const imageRef = firebaseRef(firebaseStorage, storagePath);
    const url = await getDownloadURL(imageRef);
    item.thumbnailUrl = url;
  } catch (error) {
    console.error(`이미지 로드 실패: ${storagePath}`, error);
    item.thumbnailUrl = "/images/placeholder.png";
  }
};

// 데이터 로드
const loadWishlist = async () => {
  try {
    const response = await axios.get(
      `http://localhost:8081/api/mypage/wishlist/${authStore.user.userId}`
    );

    // 초기 데이터 설정
    wishlist.value = response.data.data.map((item) => ({
      ...item,
      isLiked: true,
      thumbnailUrl: "",
    }));

    // 상품 및 코디 데이터 분리
    productWishlist.value = wishlist.value.filter((item) => item.productId);
    codiWishlist.value = wishlist.value.filter((item) => item.codiId);

    // 이미지 로드 작업을 병렬로 처리
    const imagePromises = wishlist.value.map((item) => fetchImageForItem(item));
    await Promise.all(imagePromises); // 모든 이미지 로드 완료 대기
  } catch (error) {
    console.error("Error loading wishlist:", error);
  }
};


// 컴포넌트 마운트 시 데이터 로드
onMounted(() => {
  loadWishlist();
});
</script>

<style scoped>
/* 탭 스타일 */
.tabs {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.tabs button {
  background: none;
  border: none;
  padding: 15px 20px;
  font-size: 16px;
  cursor: pointer;
  color: #333;
  width: -webkit-fill-available;
}

.tabs button.active {
  font-weight: bold;
  border-bottom: 1px solid #878787;
}

.wishlist-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
}

.wishlist-image {
  width: 100%;
  border-radius: 8px;
  height: auto;
}

.wishlist-brand {
  font-weight: bold;
  margin-top: 10px;
  text-decoration: underline;
}

.wishlist-name {
  color: #555;
  font-size: 14px;
  margin: 8px 0;
}

.wishlist-price {
  font-size: 16px;
  color: #333;
  margin: 10px 0;
}

.wishlist-likes {
  display: flex;
  gap: 5px;
}

.like-icon {
  cursor: pointer;
  width: 16px;
  height: 16px;
}

.wishlist-likes span {
  color: #e74c3c;
  font-size: 14px;
}
</style>
