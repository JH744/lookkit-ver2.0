<template>
  <div>
    <div class="page-container">
      <div class="main-content">
        <div
          id="carouselExampleAutoplaying"
          class="carousel slide"
          data-bs-ride="carousel"
        >
          <div class="carousel-inner">
            <div class="carousel-item active" data-bs-interval="2000">
              <img
                src="@/assets/banner/main_sumnail1.png"
                class="d-block w-100"
                alt="..."
              />
            </div>
            <div class="carousel-item" data-bs-interval="2000">
              <img
                src="@/assets/banner/main_sumnail2.png"
                class="d-block w-100"
                alt="..."
              />
            </div>
            <div class="carousel-item" data-bs-interval="2000">
              <img
                src="@/assets/banner/main_sumnail3.png"
                class="d-block w-100"
                alt="..."
              />
            </div>
          </div>
          <button
            class="carousel-control-prev"
            type="button"
            data-bs-target="#carouselExampleAutoplaying"
            data-bs-slide="prev"
          >
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
          </button>
          <button
            class="carousel-control-next"
            type="button"
            data-bs-target="#carouselExampleAutoplaying"
            data-bs-slide="next"
          >
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
          </button>
        </div>
      </div>
      <div class="indicator">
        <!-- 슬라이드 -->
      </div>
      <div class="section-title">SELECT LOOK KIT</div>
      <div class="brand-container">
        <div class="brand-image-container">
          <img
            class="brand-image"
            src="@/assets/category/outer.png"
            alt="브랜드 이미지"
          />
          <span class="brand-name">outer</span>
        </div>
        <div class="brand-image-container">
          <img
            class="brand-image"
            src="@/assets/category/top.jpg"
            alt="브랜드 이미지"
          />
          <div class="brand-name">top</div>
        </div>
        <div class="brand-image-container">
          <img
            class="brand-image"
            src="@/assets/category/bottom1.jpg"
            alt="브랜드 이미지"
          />
          <div class="brand-name">bottom</div>
        </div>
        <div class="brand-image-container">
          <img
            class="brand-image"
            src="@/assets/category/bag1.png"
            alt="브랜드 이미지"
          />
          <div class="brand-name">bag</div>
        </div>
        <div class="brand-image-container">
          <img
            class="brand-image"
            src="@/assets/c1_item1.jpg"
            alt="브랜드 이미지"
          />
          <div class="brand-name">shoes</div>
        </div>
      </div>
      <!-- 코디상품리스트 -->
      <div class="codi-container">
        <div class="codi-title">
          <span class="circle"></span>
          <span class="codi-text">BEST CODI</span>
          <span class="view-more">+ VIEW MORE</span>
        </div>
        <div class="codi-main-container">
          <!--코디&상품 반복 시작-->
          <!-- <div th:each="codi:${coordiList}" class="codi-block">
            <img
              class="codi-sumnail"
              th:src="@{'/images/coordi/0' + ${codi.codiId} + '/' + ${codi.codiId} + '_thumbnail.webp'}"
              alt="코디 썸네일"
            />
            <div class="codi-product" th:each="product : ${codi.products}">
              <img
                class="codi-product-img"
                th:src="@{'/images/products/0' + ${product.productId} + '/'+ ${product.productThumbnail}}"
                alt="코디 썸네일"
              />
              <a th:href="@{|/product/${product.productId}|}">
                <div class="codi-text">
                  <p th:text="${product.productName}">DIEMME</p>
                  <p th:text="${product.productPrice}">
                    FOUND TIE FRONT PANT (BLACK)
                  </p>
                </div>
              </a>
            </div>
          </div> -->
          <!--반복 끝-->
        </div>
      </div>
      <div class="new-container">
        <p>NEW-IN</p>
        <div class="new-product-block">
          <img class="new-product-img" src="@/assets/38_thumbnail.webp" />
          <img class="new-product-img" src="@/assets/39_thumbnail.webp" />
          <img class="new-product-img" src="@/assets/40_thumbnail.webp" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "MainView",
};
</script>

<style scoped>
@charset "UTF-8";
.page-container {
  height: 100%;
  position: relative;
}

.main-content {
  background: #ffffff;
  width: 100%;
}

.main-banner {
  height: 250px;
  overflow: hidden;
}

.banner-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.title {
  font-size: 42px;
  font-weight: bold;
}

.banner-subtitle {
  font-size: 18px;
  font-weight: 300;
  margin-top: 10px;
}

.indicator {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.section-title {
  font-size: 20px;
  text-align: center;
  margin: 40px 0;
  font-weight: bold;
}

.brand-container {
  display: inline-flex;
  text-align: center;
  width: 100%;
  gap: 10px;
  justify-content: center;
}

.brand-image-container {
  position: relative;
  display: inline-block; /* 필요에 따라 변경 가능 */
  width: 477px;
  height: 680px;
  overflow: hidden;
}

.brand-image {
  width: 100%;
  height: 100%;
  object-fit: cover; /* 이미지가 컨테이너에 맞게 잘림 */
  filter: brightness(70%);
  transition: transform 0.5s ease, filter 0.5s ease; /* 지속 시간을 3초로 조정 */
}
.brand-image:hover {
  transform: scale(1.1);
}

.brand-name {
  position: absolute;
  top: 83%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: white;
  font-size: 21px;
  border-radius: 5px;
  text-align: center;
  z-index: 10;
}

/* 코디세트 컨테이너 */
.codi-container {
  width: 80%;
  margin: 0 auto;
}

.codi-main-container {
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
  padding: 0px 20px;
  gap: 30px;
}

.codi-title {
  display: flex;
  align-items: center; /* 수직 정렬 */
  justify-content: space-between; /* 양 끝에 요소 배치 */
  padding: 10px 0px;
  width: 100%;
  margin-top: 50px;
}

.circle {
  width: 15px;
  height: 15px;
  background-color: #6e2485;
  border-radius: 50%;
  margin-right: 8px;
}

.codi-text {
  font-size: 16px;
  font-weight: bold;
}

.codi-sumnail {
  width: 200px;
  margin-bottom: 10px;
}

.view-more {
  margin-left: auto; /* 오른쪽 끝으로 밀어내기 */
  color: #999;
  font-size: 14px;
  cursor: pointer;
}

.codi-product {
  display: flex;
  align-items: center;
}

.codi-product-img {
  width: 50px;
  height: 50px;
}

.codi-block {
  display: flex;
  flex-direction: column;
  justify-items: center;
}

.codi-text p {
  margin: 0px;
  font-size: 12px;
  width: 140px;
  /* margin-left: 10px; */
}

.codi-text p:nth-child(2) {
  font-size: 10px;
}

/* new-in */
.new-container {
  width: 90%;
  margin-top: 50px;
  display: grid;
  justify-content: center;
  margin: 40px auto;
}
.new-container p {
  font-size: 20px;
  text-align: center;
  margin: 40px 0;
  font-weight: bold;
}
.new-product-img {
  width: 400px;
  height: 500px;
}

.new-product-block {
  width: 100%;
  display: flex;
  justify-content: space-between;
  gap: 25px;
}
</style>
