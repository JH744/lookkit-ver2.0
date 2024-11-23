<template>
  <div class="page-container">
    <div class="page-container">
      <div class="title-section">
        <div class="title-box">
          <h1>{{ `'  ${keyword}  ' 검색완료` }}</h1>
          <h1>{{ "검색결과수 " + products.length }}</h1>
        </div>
        <div class="filter-section"></div>
        <div class="sort-section"></div>
      </div>
      <div class="main-content">
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

            <div class="product-info">
              <a :href="`/product/${product.productId}`">
                <div class="brand-name">{{ product.brandName }}</div>
                <div class="product-name">{{ product.productName }}</div>
              </a>
              <div class="product-price-box">
                <div class="product-price">{{ product.productPrice }}원</div>
                <!-- <div class="product-price-discount">20%</div> -->
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
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from "vue";
import { useRoute } from "vue-router";
import axios from "axios";
import defaultImage from "@/assets/img_none.png";
import { useAuthStore } from "@/stores/authStore";

const authStore = useAuthStore();

const products = ref([]); // 상품 리스트
const route = useRoute();
const keyword = ref(route.query.keyword); // 초기값 설정

const imageBaseUrl = ref(
  "https://firebasestorage.googleapis.com/v0/b/test-24a07.appspot.com/o/lookkit"
);

const handleImageError = (event) => {
  event.target.src = defaultImage; // 이미지 로드 실패 시 기본 이미지로 대체
};

// 쿼리스트링이 변경될 때마다 keyword를 업데이트하고 데이터를 다시 불러옴
watch(
  () => route.query.keyword,
  (newKeyword) => {
    keyword.value = newKeyword; // keyword 값 갱신
    fetchProductByKeyword(); // 데이터 재조회
  }
);

// 상품 데이터를 가져오는 함수
const fetchProductByKeyword = async () => {
  try {
    const response = await axios.get("http://localhost:8081/api/main/search", {
      params: {
        keyword: keyword.value,
      },
    });
    products.value = response.data;
    console.log("검색결과 리스트:", products.value);
  } catch (error) {
    console.error("상품 데이터 조회 오류 발생:", error);
  }
};

// 초기 데이터 로드
onMounted(() => {
  console.log("유저정보", authStore.user);

  fetchProductByKeyword();
});

//스토리지 이미지url 인코딩
const encodedProductImageUrl = (productId) => {
  const folderPath = `/products/0${productId}`;
  const fileName = `/${productId}_thumbnail.webp`;
  const encodedPath = `${encodeURIComponent(folderPath)}${encodeURIComponent(
    fileName
  )}`;
  return `${imageBaseUrl.value}${encodedPath}?alt=media`;
};
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
  width: 500px;
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
.product-name:hover {
  text-decoration: underline;
}
.product-price {
  /* font-size: 20px; */
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

.title-box {
  display: flex;
  justify-content: center;
  margin: 0 auto;
  gap: 20px;
  padding: 40px 120px 80px;
}
.title-box h1:nth-child(2) {
  color: #7d7c7c;
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

.brand-name {
  font-size: 14px;
  font-weight: 400;
  color: #767676;
  width: 240px;
  height: 20px;
}
</style>
