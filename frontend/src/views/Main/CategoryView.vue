<template>
  <div class="page-container">
    <div class="page-container">
      <div class="title-section">
        <h1 th:text="${type}">아우터</h1>
        <div class="filter-section">
          <div class="filter-btn">
            <img src="@/assets/icons/filterIcon.svg" alt="" />
            필터검색
            <img src="@/assets/icons/dropDownIcon.svg" alt="" width="10px" />
          </div>
        </div>
        <div class="sort-section">
          <div
            class="item-count"
            th:text="'상품갯수: '+${productsList.size()}+'개'"
          >
            2,120개
          </div>
          <div class="sort-box">
            <ul>
              <li>추천순</li>
              <li>최신순</li>
              <li>낮은 가격순</li>
              <li>높은 가격순</li>
              <li>판매순</li>
              <li>리뷰 많은순</li>
            </ul>
          </div>
        </div>
      </div>
      <div class="main-content">
        <div class="product-list">
          <div th:each="product :${productsList}" class="product-item">
            <img
              th:src="@{'/images/products/0'+${product.productId}+'/'+${product.productId}+'_detail_1.webp'}"
              alt="상품썸네일"
            />
            <!--좋아요버튼(로그인시에만 표시)-->
            <img
              th:src="@{/images/icon/heart2.svg}"
              class="like-btn"
              alt="좋아요버튼"
              sec:authorize="isAuthenticated()"
            />
            <div class="product-info">
              <a th:href="@{|/product/${product.productId}|}">
                <div class="brand-name" th:text="${product.brandName}">
                  브랜드
                </div>
                <div class="product-name" th:text="${product.productName}">
                  아우터
                </div>
              </a>
              <div class="product-price-box">
                <div
                  class="product-price"
                  th:text="${product.productPrice}+'원'"
                >
                  120,000원
                </div>
                <div class="product-price-discount">20%</div>
                <div class="hidden-id" th:text="${product.productId}">
                  상품ID
                </div>
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
                  <div>쿠폰</div>
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

<script>
import { useRoute } from "vue-router";

export default {
  setup() {
    const route = useRoute();

    // 쿼리 스트링 값
    const query = route.query;

    return {
      query,
    };
  },
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
  margin-top: 47px;
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
  color: #767676;
  width: 240px;
  height: 40px;
}
.brand-name {
  font-size: 18px;
  font-weight: 400;
  color: #000000;
  width: 240px;
  height: 40px;
}
.product-name:hover {
  text-decoration: underline;
}
.product-price {
  font-size: 20px;
  font-weight: 700;
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
</style>
