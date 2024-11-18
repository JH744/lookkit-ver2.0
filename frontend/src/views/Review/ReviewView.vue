<template>
  <div class="review-container">
    <HeaderFragment />
    <div class="side-and-main">
      <SidebarFragment />
      <div class="review-section">
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
          <button type="button" class="submit-review" @click="submitReview">리뷰 제출하기</button>
        </div>
        <div class="average-rating">
          <h3>평균 평점: {{ averageRating }} / 5</h3>
        </div>
        <div v-if="reviews && reviews.length" class="review-list">
        <div v-for="review in paginatedReviews" :key="review.reviewId" class="review-item">
        <div class="review-header">
          <span class="review-rating">{{ review.rating }}점</span>
          <span class="review-user">{{ review.userId }}</span>
          <span class="review-date">{{ formatReviewDate(review.createdAt) }}</span>
        </div>
        <div class="review-content">
          <p>{{ review.reviewText }}</p>
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
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import axios from 'axios';

const reviews = ref([]); 
// const activeTab = ref('product');
const reviewRating = ref(5);
const reviewText = ref('');
const showReviewForm = ref(false);
const currentPage = ref(1);
const itemsPerPage = 5;

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
    };

    const response = await axios.post('http://localhost:8081/api/reviews/write', reviewData);
    if (response.status === 201) {
      alert('리뷰 작성 완료');
      reviews.value.push({
        ...reviewData,
        createdAt: new Date().toISOString(),
        reviewId: reviews.value.length + 1, // 임시로 추가
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
// const submitReview = async () => {
//   if (!reviewText.value || !reviewRating.value) {
//     alert('평점과 리뷰 내용을 입력해주세요.');
//     return;
//   }
//   // 리뷰 데이터 생성
//   const newReview = {
//     productId: activeTab.value === 'product' ? 1 : null, // 예시로 productId를 1로 설정
//     codiId: activeTab.value === 'codi' ? 1 : null, // 예시로 codiId를 1로 설정
//     userId: 1, // 예시로 userId를 1로 설정
//     rating: reviewRating.value,
//     reviewText: reviewText.value,
//   };
//   try {
//     const response = await fetch('http://localhost:8081/api/reviews/write', {
//       method: 'POST',
//       headers: {
//         'Content-Type': 'application/json'
//       },
//       body: JSON.stringify(newReview)
//     });
//     if (response.ok) {
//       alert('리뷰 작성 완료');
//       reviews.value.push({
//         ...newReview,
//         reviewId: reviews.value.length + 1,
//         createdAt: new Date().toISOString(),
//         thumbnail: '/images/placeholder.png' // 임시 이미지 경로
//       });
//       toggleReviewForm();
//     } else {
//       alert('리뷰 작성 실패');
//     }
//   } catch (error) {
//     console.error('Error submitting review:', error);
//     alert('리뷰 작성 중 오류가 발생했습니다.');
//   }
// };

// 날짜 포맷팅 함수
const formatReviewDate = (dateString) => {
  const options = { year: 'numeric', month: '2-digit', day: '2-digit' };
  return new Date(dateString).toLocaleDateString('ko-KR', options);
};
</script>

<style scoped>
.review-container {
  display: grid;
  margin-top: 20px;
  width: 1100px;
  margin-left: auto;
  margin-right: auto;
  margin-bottom: 100px;
}

.review-section {
  border-bottom: 2px solid #727272;
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

.review-list {
  margin-top: 50px;
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
  margin-bottom: 30px;
  padding-bottom: 20px;
  border-bottom: 1px solid #cdcdcd;
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
  padding: 15px;
  background: #f9f9f9;
  border-radius: 10px;
}

.rating-section {
  display: flex;
  align-items: center;
  gap: 10px;
}

.rating-section select {
  width: 100px;
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
  margin-bottom: 20px;
  padding: 10px 20px;
  background-color: #0d1134;
  color: white;
  border: none;
  cursor: pointer;
  font-size: 16px;
  border-radius: 5px;
}

.average-rating {
  margin-top: 20px;
  font-size: 18px;
  font-weight: bold;
}
</style>
