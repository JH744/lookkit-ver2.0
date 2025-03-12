<template>
  <div class="page-container">
    <div class="title-section">
      <h1>{{ categoryType.toUpperCase() }}</h1>
      <div class="filter-section">
        <div class="filter-btn" @click="toggleFilterMenu">
          <img src="@/assets/icons/filterIcon.svg" alt="" />
          필터검색
          <img src="@/assets/icons/dropDownIcon.svg" alt="" width="10px" />
        </div>
        <div class="filter-dropdown" v-if="isFilterMenuOpen">
          <ul>
            <li @click="applySort('latest')">최신순</li>
            <li @click="applySort('oldest')">오래된순</li>
            <li @click="applySort('lowPrice')">낮은 가격순</li>
            <li @click="applySort('highPrice')">높은 가격순</li>
          </ul>
        </div>
      </div>
      <div class="sort-section">
        <div class="item-count">상품 갯수: {{ products.length }}개</div>
      </div>
    </div>

    <div class="main-content">
      <!--반복문시작  -->
      <div class="product-list">
        <div
          v-for="product in products"
          :key="product.productId"
          class="product-item"
        >
          <img
            class="product-img"
            :src="encodedProductImageUrl(product.productId)"
            alt="상품썸네일"
            @error="handleImageError"
          />
          <img
            v-if="authStore.isLoggedIn"
            :src="product.wishlist ? heartIcon1 : heartIcon2"
            class="like-btn"
            alt="좋아요버튼"
            @click="likeProduct(product.productId)"
          />
          <div class="product-info">
            <a :href="`/product/${product.productId}`">
              <div class="brand-name">{{ product.brandName }}</div>
              <div class="product-name">{{ product.productName }}</div>
            </a>
            <div class="product-price-box">
              <div class="product-price">{{ product.productPrice }}원</div>
              <!-- <div class="product-price-discount">20%</div> -->
              <!-- <div class="product-price-discount">{{ product.wishlist }}</div> -->
            </div>
            <div class="like-box">
              <img
                src="@/assets/icons/heart0.svg"
                alt=""
                width="20px"
                height="20px"
              />
              <span>120</span>
            </div>
            <div class="product-event">
              <div class="product-event-box">
                <!-- <div>쿠폰</div> -->
                <div>대여가능</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch, computed } from "vue";
import { useRoute } from "vue-router";
import api from "@/api/axios";
import defaultImage from "@/assets/img_none.png";
import { useAuthStore } from "@/stores/authStore";
import heart1 from "@/assets/icons/heart1.svg";
import heart2 from "@/assets/icons/heart2.svg";

const authStore = useAuthStore();
const products = ref([]); // 상품 리스트
const route = useRoute(); // 현재 라우트 정보를 가져옴
const categoryType = ref(route.query.type);
const imageBaseUrl = ref(
  "https://firebasestorage.googleapis.com/v0/b/test-24a07.appspot.com/o/lookkit"
);
const heartIcon1 = ref(heart1);
const heartIcon2 = ref(heart2);
const wishlistItemIds = ref([]);
const sortBy = ref("");
const isFilterMenuOpen = ref(false);

const toggleFilterMenu = () => {
  isFilterMenuOpen.value = !isFilterMenuOpen.value;
};

const applySort = (sort) => {
  sortBy.value = sort;
  isFilterMenuOpen.value = false; // 정렬 선택 후 메뉴 닫기
  fetchProducts();
};

const handleImageError = (event) => {
  event.target.src = defaultImage; // 이미지 로드 실패 시 기본 이미지로 대체
};

// 카테고리별 상품리스트 가져오기
const fetchProducts = async () => {
  try {
    const { data } = await api.get("/api/main/category", {
      params: {
        type: categoryType.value,
        sort: sortBy.value,
      },
    });
    products.value = data;
    console.log("리스트", data);
    // console.log("상품 리스트:", products.value);
    // 상품 ID 리스트 추출
    const productIds = products.value.map((product) => product.productId);
    // 위시리스트 상태 확인
    const wishlistResponse = await api.post(
      `/api/main/checkBatch/${authStore.user.userId}`,
      productIds
    );

    // 위시리스트에 포함된 상품 ID 가져오기
    wishlistItemIds.value = wishlistResponse.data.wishlistItemIds;
    console.log("wishlistItemIds", wishlistItemIds.value);

    // 상품 데이터에 wishlist 상태를 추가
    products.value = products.value.map((product) => ({
      ...product,
      wishlist: wishlistItemIds.value.includes(product.productId),
    }));
  } catch (error) {
    console.error("상품 데이터 조회 오류 발생:", error);
  }
};

//스토리지 이미지url 인코딩
const encodedProductImageUrl = computed(() => {
  return (productId) => {
    const folderPath = `/products/0${productId}`;
    const fileName = `/${productId}_thumbnail.webp`;
    const encodedPath = `${encodeURIComponent(folderPath)}${encodeURIComponent(
      fileName
    )}`;
    return `${imageBaseUrl.value}${encodedPath}?alt=media`;
  };
});

// 좋아요 토글 함수 수정
const likeProduct = async (productId) => {
  try {
    // 위시리스트 상태 업데이트
    const product = products.value.find((p) => p.productId === productId);
    if (!product) {
      console.error("상품을 찾을 수 없습니다:", productId);
      return;
    }

    // 위시리스트 상태 업데이트
    // 이미 위시리스트에 포함된 상품은 삭제 ,없는 상품은 새로 추가
    if (wishlistItemIds.value.includes(productId)) {
      console.log("찜삭제");
      const response = await api.post(
        `/api/main/wishlist/delete/${authStore.user.userId}`,
        {
          productId: productId,
        }
      );
      // 리스트에서 제외
      wishlistItemIds.value = wishlistItemIds.value.filter(
        (id) => id !== productId
      );
      product.wishlist = false; // 상태 업데이트
      console.log("찜리스트:", wishlistItemIds.value);
    } else {
      console.log("찜하기");

      const response = await api.post(
        `/api/mypage/wishlist/${authStore.user.userId}`,
        {
          productId: productId,
        }
      );
      // 위시리스트에 상품Id 추가
      wishlistItemIds.value.push(productId);
      product.wishlist = true; // 상태 업데이트
    }
  } catch (error) {
    console.error("Error toggling like:", error);
  }
};

// // 위시리스트 추가/제거 토글
// const toggleWishlist = async (itemId) => {
//   try {
//     // 현재 상품의 상태 확인
//     const product = products.value.find((p) => p.id === itemId);
//     if (!product) return;

//     if (product.wishlist) {
//       // 위시리스트에서 제거
//       await api.delete(`/wishlist?userId=${userId}&itemId=${itemId}`);
//       product.wishlist = false;
//     } else {
//       // 위시리스트에 추가
//       await api.post(`/wishlist/add`, {
//         userId,
//         itemId,
//       });
//       product.wishlist = true;
//     }
//   } catch (error) {
//     console.error("위시리스트 상태 변경 중 오류 발생:", error);
//   }
// };

// 쿼리스트링이 변경될 때마다 categoryType을 업데이트
watch(
  () => route.query.type,
  (newType) => {
    categoryType.value = newType; // categoryType 업데이트
    fetchProducts(); // 새로운 쿼리스트링에 따라 상품 데이터 다시 가져오기
  }
);

// 초기 데이터 로드
onMounted(() => {
  if (route.query.type == "all") {
    console.log("모든상품조회");
  }
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
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  margin: 0 auto;
  padding-left: 60px;
}
.product-item {
  width: 18%;
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
