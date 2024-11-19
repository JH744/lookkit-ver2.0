<template>
  <div class="review-container">
      <div class="review-section">
        <div class="average-rating">
          <div class="star-ratings">
           <div class="star-ratings-fill" :style="{ width: `${(averageRating / 5) * 100}%` }"></div>
          </div>
          <h3>평균 평점 {{ averageRating }} / 5</h3>
        </div>
        <div class="add-review">
        <button class="add-review-button" @click="toggleReviewForm">리뷰 작성하기</button>
        <div v-if="showReviewForm" class="expanded-review-form">
          <div class="rating-section">
            <label>평점:</label>
            <select v-model="reviewRating">
              <option value="1">1</option>
              <option value="2">2</option>
              <option value="3">3</option>
              <option value="4">4</option>
              <option value="5">5</option>
            </select>
          </div>
          <textarea v-model="reviewText" placeholder="리뷰를 작성해주세요"></textarea>
          <div class="image-upload-section">
            <label for="image-upload" class="image-upload-label">이미지 업로드:</label>
            <input type="file" id="image-upload" @change="onImageUpload" accept="image/*" />
            <div v-if="uploadedImage" class="image-preview">
              <img :src="uploadedImage" alt="업로드된 이미지 미리보기" />
            </div>
          </div>
          <button type="button" class="submit-review" @click="submitReview">리뷰 제출하기</button>
        </div>
        </div>
      </div>
      <div class="review-section2">
        <div v-if="reviews && reviews.length" class="review-list">
        <div v-for="review in paginatedReviews" :key="review.reviewId" class="review-item">
        <div class="review-header">
          <span class="review-rating">{{ review.rating }}점</span>
          <div v-if="review.imageUrl" class="review-image">
            <img :src="review.imageUrl" alt="리뷰 이미지" />
          </div>
        </div>
        <div class="review-text">
          <p>{{ review.reviewText }}</p>
        </div>
        <div class="review-info">
          <span class="review-date">{{ formatReviewDate(review.createdAt) }}</span>
          <span class="review-user">{{ review.userId }}</span>
        </div>
        </div>
        <div class="pagination">
        <button @click="previousPage" :disabled="currentPage === 1">이전</button>
        <span>{{ currentPage }} / {{ totalPages }}</span>
        <button @click="nextPage" :disabled="currentPage === totalPages">다음</button>
      </div>
    </div>
        <div v-else>
          <p>작성된 리뷰가 없습니다.</p>
        </div>
      </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import axios from 'axios';

const reviews = ref([]); 
const reviewRating = ref(5);
const reviewText = ref('');
const showReviewForm = ref(false);
const currentPage = ref(1);
const itemsPerPage = 5;
const uploadedImage = ref(null);

const props = defineProps({
  codiId: {
    type:  String,
    default: null
  },
  productId: {
    type: String,
    default: null
  }
});

// 이미지 파일 업로드 핸들러
const onImageUpload = (event) => {
  const file = event.target.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = (e) => {
      uploadedImage.value = e.target.result; // 이미지 미리보기 URL
    };
    reader.readAsDataURL(file);
  }
};

onMounted(async () => {
  try {
    console.log('props.codiId:', props.codiId);
    console.log('props.productId:', props.productId);

    let url = '';
    if (props.codiId) {
      url = `http://localhost:8081/api/reviews/codi/${props.codiId}`;
    } else if (props.productId) {
      url = `http://localhost:8081/api/reviews/product/${props.productId}`;
    }
    
    console.log('API 요청 URL:', url);

    if (url) {
      const response = await axios.get(url);
      console.log('리뷰 데이터:', response.data);
      reviews.value = Array.isArray(response.data) ? response.data : [];
      console.log('리뷰 데이터:', reviews.value);

    }
  } catch (error) {
    console.error('리뷰 데이터를 불러오는데 실패했습니다:', error);
  }
});

// onMounted(async () => {
//   try {
//     const response = await fetch(`http://localhost:8081/api/reviews/product/1`); // 예시 productId 1로 설정
//     if (response.ok) {
//       reviews.value = await response.json();
//     } else {
//       console.error('Failed to fetch reviews');
//     }
//   } catch (error) {
//     console.error('Error fetching reviews:', error);
//   }
// });

// 리뷰 작성 폼 보이기/숨기기
const toggleReviewForm = () => {
  showReviewForm.value = !showReviewForm.value;
};

// 평균 평점 계산
const averageRating = computed(() => {
  if (!Array.isArray(reviews.value) || reviews.value.length === 0) return 0; // 배열이 아니거나 길이가 0이면 0 반환
  const total = reviews.value.reduce((sum, review) => sum + review.rating, 0);
  return (total / reviews.value.length).toFixed(1);
});


// 페이지네이션을 적용한 리뷰 목록
const paginatedReviews = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage;
  const end = start + itemsPerPage;
  return reviews.value.slice(start, end);
});

// 총 페이지 수 계산
const totalPages = computed(() => {
  return Math.ceil(reviews.value.length / itemsPerPage);
});

// 이전 페이지로 이동
const previousPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--;
  }
};

// 다음 페이지로 이동
const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++;
  }
};

// 리뷰 작성하기
const submitReview = async () => {
  try {
    const userId = 1; // 예시용 userId
    const reviewData = {
      productId: props.productId,
      codiId: props.codiId,
      userId,
      rating: reviewRating.value,
      reviewText: reviewText.value,
      imageUrl: uploadedImage.value
    };

    const response = await axios.post('http://localhost:8081/api/reviews/write', reviewData);
    if (response.status === 201) {
      alert('리뷰 작성 완료');
      reviews.value.push({
        ...reviewData,
        createdAt: new Date().toISOString(),
        reviewId: reviews.value.length + 1, //임시로
      });
      toggleReviewForm();
    } else {
      alert('리뷰 작성 실패');
    }
  } catch (error) {
    console.error('리뷰 작성 오류:', error);
    alert('리뷰 작성에 실패했습니다.');
  }
};

// 날짜 포맷팅 함수
const formatReviewDate = (dateString) => {
  const options = { year: 'numeric', month: '2-digit', day: '2-digit' };
  return new Date(dateString).toLocaleDateString('ko-KR', options);
};
</script>

<style scoped>
.review-container {
  display: grid;
  width: 1100px;
  margin-left: auto;
  margin-right: auto;
  margin-bottom: 100px;
}

.review-section {
  border-bottom: 2px solid #727272;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  gap: 10px;
}

.second-grid {
  width: 100%;
  margin-left: 50px;
}

.review-section h2 {
  font-size: 24px;
  margin-top: 65px;
  margin-bottom: 0px;
}

.review-tabs {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 5px;
  border-bottom: 2px solid #727272;
}

.tab {
  padding: 10px 20px;
  cursor: pointer;
}

.tab.active {
  border-bottom: 3px solid #0d1134;
  font-weight: bold;
}
.review-section2 {
  margin-top: 30px;
}

.review-header img {
  width: 100px;
  height: 100px;
  object-fit: cover;
  margin-right: 20px;
}

.image-upload-section {
  width:25%;
}

.image-preview img {
  width: 80px;
  height: 80px;
  margin-top: 10px;
}

.review-header {

}
.review-text {
width: 40%;
}
.review-info {
  display: flex;
  gap: 20px;
}
.empty-message {
  text-align: center;
  color: #818181;
  font-size: 18px;
  margin-top: 50px;
}

.review-item {
  display: flex;
    gap: 20px;
    padding: 20px;
    border-bottom: 1px solid #cdcdcd;
    flex-direction: row;
    justify-content: space-around;
    
}

.product-info {
  display: flex;
  align-items: center;
  gap: 10px;
}

.product-info img {
  width: 100px;
  height: 100px;
  object-fit: cover;
}

.product-details {
  font-size: 17px;
}

.review-form {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.review-form textarea {
  background: #fff;
  border: 1px solid #555553;
  padding: 10px;
  font-family: "Inter-Medium", sans-serif;
  font-size: 16px;
  resize: none;
  height: 100px;
  width: 400px;
}

.expanded-review-form {
  margin-top: 10px;
  padding-bottom: 5px;
  background: #f9f9f9;
  border-radius: 10px;
  display: flex;
  justify-content: space-evenly;
}

.rating-section {
  display: flex;
  align-items: center;
}

.rating-section select {
  width: 50px;
}

.order-section {
  display: flex;
  align-items: center;
  gap: 10px;
}

.order-section select {
  width: 150px;
}

.submit-review {
  background: #0d1134;
  color: #fff;
  border-radius: 35px;
  width: 184px;
  height: 44px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: "Inter-SemiBold", sans-serif;
  font-size: 15px;
  font-weight: 600;
  cursor: pointer;
}

.review-content {
  flex: 1;
}

.rating-display {
  margin-top: 10px;
  font-size: 16px;
}

.review-meta {
  display: flex;
  gap: 20px;
  font-size: 14px;
  color: #555;
  margin-top: 5px;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px;
  margin-top: 20px;
}

.pagination button {
  padding: 5px 10px;
  cursor: pointer;
}

.add-review-button {
  margin-bottom: 10px;
  padding: 10px 20px;
  background-color: #0d1134;
  color: white;
  border: none;
  cursor: pointer;
  font-size: 16px;
  border-radius: 5px;
}
.add-review {
  display: flex;
 
}

.average-rating {
  margin-bottom: 10px;
  font-size: 18px;
    font-weight: bold;
    gap: 10%;
    display: flex;
    flex-direction: row;
    align-items: center;
}
.star-ratings {
  display: inline-block;
  position: relative;
  font-size: 2rem;
  color: #d3d3d3; /* 별의 기본 색상 (비어 있는 별) */
  unicode-bidi: bidi-override;
  direction: rtl;
}
.star-ratings-fill {
  color: #ffc107; /* 채워진 별의 색상 */
  position: absolute;
  left: 10;
  top: 0;
  width: 0;
  overflow: hidden;
  white-space: nowrap;
  transition: width 0.3s ease-in-out;
}
.star-ratings::before {
  content: "★★★★★"; /* 별 모양을 5개로 고정 */
}
.star-ratings-fill::before {
  content: "★★★★★";
}

</style>
