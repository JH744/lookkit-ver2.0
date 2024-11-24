<template>
  <div class="page-container">
    <div class="title-section">
      <h1 class="title-text">전체 코디</h1>
      <div class="filter-section"></div>
      <div class="sort-section">
        <div class="item-count">코디수: {{ coordiList.length }}개</div>
      </div>
    </div>

    <div class="main-content">
      <div class="product-list">
        <Coordi
          v-for="coordi in coordiList"
          :key="coordi.codiId"
          :codi-id="coordi.codiId"
          :codi-name="coordi.codiName"
          :codi-thumbnail="coordi.codiThumbnail"
          :codi-price="coordi.codiPrice"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import axios from "axios";
import defaultImage from "@/assets/img_none.png";
import { useAuthStore } from "@/stores/authStore";
import heart1 from "@/assets/icons/heart1.svg";
import heart2 from "@/assets/icons/heart2.svg";
import Coordi from "@/components/ui/\bCoordi.vue";

const authStore = useAuthStore();
const coordiList = ref([]); // 상품 리스트
const route = useRoute(); // 현재 라우트 정보를 가져옴
const imageBaseUrl = ref(
  "https://firebasestorage.googleapis.com/v0/b/test-24a07.appspot.com/o/lookkit"
);
const heartIcon1 = ref(heart1);
const heartIcon2 = ref(heart2);
const wishlistItemIds = ref([]);

const handleImageError = (event) => {
  event.target.src = defaultImage; // 이미지 로드 실패 시 기본 이미지로 대체
};

// 코디 리스트 가져오기
const fetchProducts = async () => {
  try {
    const { data } = await axios.get(
      "http://localhost:8081/api/main/codis/all"
    );
    console.log("리스트", data);
    coordiList.value = data;
    // 상품 ID 리스트 추출
    // const productIds = products.value.map((product) => product.productId);
    // 위시리스트 상태 확인
    // const wishlistResponse = await axios.post(
    //   `http://localhost:8081/api/main/checkBatch/${authStore.user.userId}`,
    //   productIds
    // );

    // // 위시리스트에 포함된 상품 ID 가져오기
    // wishlistItemIds.value = wishlistResponse.data.wishlistItemIds;
    // console.log("wishlistItemIds", wishlistItemIds.value);

    // // 상품 데이터에 wishlist 상태를 추가
    // products.value = products.value.map((product) => ({
    //   ...product,
    //   wishlist: wishlistItemIds.value.includes(product.productId),
    // }));
  } catch (error) {
    console.error("상품 데이터 조회 오류 발생:", error);
  }
};

// //스토리지 이미지url 인코딩
// const encodedProductImageUrl = computed(() => {
//   return (productId) => {
//     const folderPath = `/products/0${productId}`;
//     const fileName = `/${productId}_thumbnail.webp`;
//     const encodedPath = `${encodeURIComponent(folderPath)}${encodeURIComponent(
//       fileName
//     )}`;
//     return `${imageBaseUrl.value}${encodedPath}?alt=media`;
//   };
// });

// // 좋아요 토글 함수 수정
// const likeProduct = async (productId) => {
//   try {
//     // 위시리스트 상태 업데이트
//     const product = products.value.find((p) => p.productId === productId);
//     if (!product) {
//       console.error("상품을 찾을 수 없습니다:", productId);
//       return;
//     }

// // 위시리스트 추가/제거 토글
// const toggleWishlist = async (itemId) => {
//   try {
//     // 현재 상품의 상태 확인
//     const product = products.value.find((p) => p.id === itemId);
//     if (!product) return;

//     if (product.wishlist) {
//       // 위시리스트에서 제거
//       await axios.delete(`/wishlist?userId=${userId}&itemId=${itemId}`);
//       product.wishlist = false;
//     } else {
//       // 위시리스트에 추가
//       await axios.post(`/wishlist/add`, {
//         userId,
//         itemId,
//       });
//       product.wishlist = true;
//     }
//   } catch (error) {
//     console.error("위시리스트 상태 변경 중 오류 발생:", error);
//   }
// };

// 초기 데이터 로드
onMounted(() => {
  fetchProducts();
});
</script>

<style scoped>
.page-container {
  width: 100%;
}
.title-section {
  padding: 80px 120px;
  text-align: center;
}
.title-section h1 {
  font-size: 28px;
}
.title-text {
  padding: 40px 120px;
}

.main-content {
  width: 90%;
  margin: 50px auto;
}
.filter-section {
  display: flex;
  justify-content: flex-end;
  border-bottom: 2px solid #000000;
  position: relative; /* 드롭다운 위치 기준점 */
}
.filter-btn {
  display: flex;
  align-items: center;
  gap: 5px;
  border: 1px solid #000000;
  border-top-left-radius: 5px;
  border-top-right-radius: 5px;
  padding: 10px;
  border-bottom: none;
}
.sort-section {
  display: flex;
  justify-content: space-between;
  padding: 10px;
}
.sort-section .item-count {
  font-size: 18px;
}
.sort-box ul {
  width: 300px;
  display: flex;
  justify-content: space-between;
  list-style: none;
  padding: 0;
  margin: 0;
  color: #999999;
  font-weight: 400;
  cursor: pointer;
}
.product-list {
  width: 100%;
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  gap: 25px;
  margin: 0 auto;
}
.product-item {
  width: 100%;
  position: relative;
}
.product-item > img:nth-child(1) {
  width: 100%;
}

.product-info {
  padding: 10px;
}
.product-name {
  font-size: 18px;
  font-weight: 400;
  color: #000000;
  width: 240px;
  height: 38px;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  text-overflow: ellipsis;
  overflow: hidden;
}
.brand-name {
  font-size: 14px;
  font-weight: 400;
  color: #000000;
  width: 240px;
  height: 20px;
}
.product-name:hover {
  text-decoration: underline;
}
.product-price {
  font-size: 16px;
  font-weight: 700;
  padding: 3px 0;
}
.product-price-box {
  display: flex;
  gap: 10px;
  align-items: center;
  margin: 3px 0;
}
.product-price-discount {
  font-size: 21px;
  color: #ff5c28;
  font-weight: 700;
}
.like-box {
  display: flex;
  align-items: center;
  gap: 5px;
}
.product-event {
  height: 76px;
  gap: 10px;
  display: flex;
  flex-direction: column;
  justify-content: end;
}

.product-event-box {
  padding: 5px 0px;
  display: flex;
  gap: 10px;
}
.product-event-box > div {
  padding: 5px;
  background-color: #cdcdcd;
  border-radius: 5px;
  font-size: 16px;
  font-weight: 400;
  color: #5c5c5c;
}
.like-btn {
  width: 25px;
  height: 25px;
  position: absolute;
  top: 10px;
  right: 15px;
}
.hidden-id {
  display: none;
}
.product-img {
  width: 200px;
  height: 270px;
}

.filter-dropdown {
  position: absolute;
  top: 100%;
  right: 0;
  background-color: white;
  border: 1px solid #000000;
  border-radius: 5px;
  z-index: 1000;
  min-width: 150px;
}

.filter-dropdown ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.filter-dropdown li {
  padding: 10px 15px;
  cursor: pointer;
}

.filter-dropdown li:hover {
  background-color: #f5f5f5;
}
</style>
