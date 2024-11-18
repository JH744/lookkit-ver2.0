<template>
  <div class="container">
    <div class="image-section">
      <img :src="codiImage" alt="코디 썸네일" />
    </div>
    <div class="details-section">
      <div>
        <div class="brand">{{ codi.codiName }}</div>
        <div class="product-title">{{ codi.codiDescription }}</div>
        <div class="price-section">
          <span class="price" v-if="codi.codiPrice !== undefined">{{ codi.codiPrice.toLocaleString() }}원</span>
          <span v-else>가격 정보 없음</span>
        </div>
        <div class="rental-terms-section">
          <h4 class="rental-terms-title">대여 조건</h4>
          <ul class="rental-terms-list">
            <li>최소 대여 기간 3일</li>
            <li>3일이후 대여료 + 추가요금</li>
            <li>추가 요금: 10,000원 / 1일</li>
          </ul>
        </div>
        <div class="rental-section">
          <label for="rental-start-date" class="rental-label">대여일</label>
          <input type="date" v-model="rentalStartDate" id="rental-start-date" class="rental-input" />
          <label for="rental-end-date" class="rental-label">반납일</label>
          <input type="date" v-model="rentalEndDate" id="rental-end-date" class="rental-input" />
        </div>
        <div v-if="rentalStartDate && rentalEndDate" class="selected-rental-info">
          <div class="selected-rental-summary">
            <p>대여일 {{ rentalStartDate }}</p>
            <p>반납일 {{ rentalEndDate }}</p>
            <p>대여 일수 {{ totalRentalDays }}일</p>
            <p><strong style="font-size: 1.5em;">총 대여 금액 {{ formattedTotalPrice }}</strong></p>
          </div>
          <button @click="resetRentalDates" class="btn btn-reset">X</button>
        </div>
      </div>
      <div class="buttons">
        <button @click="addToCart" class="btn btn-cart">옷장담기</button>
        <button @click="rentNow" class="btn btn-rent">대여하기</button>
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
        <img :src="codiImage + '_detail_1.webp'" alt="코디사진1" />
        <img :src="codiImage + '_detail_2.webp'" alt="코디사진2" />
      </div>
      <div v-if="activeTab === 'reviews'" class="tab-content" id="reviews">
        <ReviewView :codiId="codiId"/>
        <!-- <div class="review-filters">
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
        </div> -->
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
      <div class="product-description">{{ codi.codiName }}</div>
    </div>
    <div class="selected-rental-summary2">
      <div>
        <p>대여일 {{ rentalStartDate }}</p>
        <p>반납일 {{ rentalEndDate }}</p>
      </div>
      <p><strong style="font-size: 1.5em;">총 대여 금액 {{ formattedTotalPrice }}</strong></p>
    </div>
    <div class="buttons">
      <button @click="addToCart" class="btn btn-cart">옷장담기</button>
      <button @click="rentNow" class="btn btn-rent">대여하기</button>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import axios from 'axios';
import "@/assets/styles/codi.css";
import ReviewView from '@/views/Review/ReviewView.vue'; 

const API_BASE_URL = 'http://localhost:8081/api/codi';

const activeTab = ref('details');
const codi = ref({});
const route = useRoute(); // 현재 라우터의 정보를 가져오는 훅
const codiId = ref(route.params.codiId); // URL에서 codiId 가져오기

const reviews = ref([]); // 빈 배열로 초기화하여 undefined 방지


const fetchCodi = async () => {
  try {
    const response = await axios.get(`${API_BASE_URL}/${codiId.value}`);
    codi.value = response.data;
  } catch (error) {
    console.error("코디 데이터를 불러오는데 실패했습니다.", error);
  }
};

onMounted(() => {
  fetchCodi();
});

const codiImage = computed(() => `/images/codi/${codi.value.codiId}/${codi.value.codiThumbnail}`);

const rentalStartDate = ref(null);
const rentalEndDate = ref(null);

const totalRentalDays = computed(() => {
  if (rentalStartDate.value && rentalEndDate.value) {
    const start = new Date(rentalStartDate.value);
    const end = new Date(rentalEndDate.value);
    const difference = end - start;
    return Math.ceil(difference / (1000 * 60 * 60 * 24));
  }
  return 0;
});

const rentalFee = computed(() => {
  if (totalRentalDays.value > 3) {
    return codi.value.codiPrice + (totalRentalDays.value - 3) * 10000;
  }
  return codi.value.codiPrice;
});

const formattedTotalPrice = computed(() => {
  return `${rentalFee.value ? rentalFee.value.toLocaleString() : '0'}원`;
});

const resetRentalDates = () => {
  rentalStartDate.value = null;
  rentalEndDate.value = null;
};

const addToCart = async () => {
  try {
    const userId = 1;
    const API_BASE_URL = 'http://localhost:8081/api/cart';
    const response = await axios.post(
      `${API_BASE_URL}/add/codi/${codi.value.codiId}?rentalStartDate=${rentalStartDate.value}&rentalEndDate=${rentalEndDate.value}&userId=${userId}`
    );
    alert('옷장에 추가되었습니다.');
    window.location.href = '/cart';
  } catch (error) {
    console.error("옷장에 추가 실패:", error);
    alert('옷장에 추가하는 데 실패했습니다.');
  }
};

const rentNow = async () => {
  try {
    const API_BASE_URL = 'http://localhost:8081/api/codi';
    // 대여 정보 API 호출
    const response = await axios.post(`${API_BASE_URL}/rent?codiId=${codi.value.codiId}&startDate=${rentalStartDate.value}&endDate=${rentalEndDate.value}`);

    // API 호출 결과로부터 OrderDTO 객체를 반환받음
    const orderDTO = response.data;

    // 주문 페이지로 이동 시 대여 정보를 URL 파라미터에 추가하여 전달
    const orderParams = new URLSearchParams({
      itemId: orderDTO.itemId,
      itemName: orderDTO.itemName,
      startDate: orderDTO.startDate,
      endDate: orderDTO.endDate,
      price: orderDTO.price,
      totalPrice: orderDTO.totalPrice,
      itemType: 'codi'  // 상품인지 코디인지 구분을 위한 파라미터
    });

    alert('대여가 완료되었습니다.');
    window.location.href = `/order?${orderParams.toString()}`;
  } catch (error) {
    alert('대여 페이지로 이동하는 데 실패했습니다.');
    console.error("대여 오류:", error);
  }
};

</script>

