<template>
  <section class="section-reviews">
    <div>
      <p id="section-title">작성한 리뷰</p>
    </div>
  </section>

  <table class="reviews-table">
    <thead>
      <tr>
        <th>상품/코디 정보</th>
        <th>작성일</th>
        <th>평점</th>
        <th>리뷰 내용</th>
        <th>삭제</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="review in reviews" :key="review.reviewId">
        <td>
          <div class="product-info">
            <img
              :src="review.thumbnailUrl || '/images/placeholder.png'"
              alt="상품/코디 이미지"
              class="product-thumbnail"
              @click="navigateToDetail(review)"
            />
            <div class="product-detail">
              <p class="brand-name">{{ review.brandName || "" }}</p>
              <p class="product-name">
                {{
                  review.productId ? review.productName : review.codiDescription
                }}
              </p>
            </div>
          </div>
        </td>
        <td>{{ formatDate(review.createdAt) }}</td>
        <td>{{ review.rating }} / 5</td>
        <td class="review-text">{{ review.reviewText }}</td>
        <td>
          <button
            @click="showDeleteConfirmModal(review.reviewId)"
            class="delete-btn"
          >
            삭제
          </button>
        </td>
      </tr>
    </tbody>
  </table>
</template>

<script setup>
import { ref, onMounted } from "vue";
import api from "@/api/axios";
import { useRouter } from "vue-router";
import { firebaseStorage } from "@/firebase/firebaseConfig";
import { ref as firebaseRef, getDownloadURL } from "firebase/storage";
import { useModalStore, useConfirmModalStore } from "@/stores/modalStore";
import { useAuthStore } from "@/stores/authStore";

const reviews = ref([]);
const authStore = useAuthStore();
const router = useRouter();

const loadReviews = async () => {
  try {
    const response = await api.get(
      `/api/reviews/list/${authStore.user.userId}`
    );
    reviews.value = response.data;

    // 각 리뷰에 대해 이미지 가져오기 호출
    for (let review of reviews.value) {
      if (review.productId) {
        await fetchImageForReview(review, "product");
      } else if (review.codiId) {
        await fetchImageForReview(review, "codi");
      }
    }

    console.log("User ID:", authStore.user?.userId);
    console.log("불러온 리뷰 데이터:", response.data);
  } catch (error) {
    console.error("리뷰를 불러오는 중 오류 발생:", error);
  }
};

const fetchImageForReview = async (review, type) => {
  let storagePath;
  if (type === "product") {
    storagePath = `lookkit/products/0${review.productId}/${review.productId}_thumbnail.webp`;
  } else if (type === "codi") {
    storagePath = `lookkit/codi/0${review.codiId}/${review.codiId}_thumbnail.webp`;
  }
  try {
    const imageRef = firebaseRef(firebaseStorage, storagePath);
    const url = await getDownloadURL(imageRef);
    review.thumbnailUrl = url;
  } catch (error) {
    console.error(`이미지 가져오기 실패: ${storagePath}`, error);
    review.thumbnailUrl = null;
  }
};

const navigateToDetail = (review) => {
  if (review.productId) {
    router.push(`/product/${review.productId}`);
  } else if (review.codiId) {
    router.push(`/codi/${review.codiId}`);
  } else {
    alert("상품 또는 코디 정보가 올바르지 않습니다.");
  }
};

const formatDate = (dateTime) => {
  const date = new Date(dateTime);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");

  return `${year}.${month}.${day}`;
};

const showDeleteConfirmModal = (reviewId) => {
  const confirmModalStore = useConfirmModalStore();
  confirmModalStore.showModal(
    "리뷰 삭제",
    "정말로 리뷰를 삭제하시겠습니까?",
    "삭제된 리뷰는 복구할 수 없습니다.",
    "삭제하기",
    () => deleteReview(reviewId)
  );
};

const deleteReview = async (reviewId) => {
  try {
    await api.delete(`/api/reviews/${reviewId}`);
    reviews.value = reviews.value.filter(
      (review) => review.reviewId !== reviewId
    );

    const modalStore = useModalStore();
    modalStore.showModal("리뷰 삭제", "리뷰 삭제가 완료되었습니다.");
  } catch (error) {
    console.error("Error deleting review:", error);
  }
};

onMounted(() => {
  loadReviews();
});
</script>

<style scoped>
#section-title {
  color: #363636;
  text-align: left;
  font-family: "Inter-Regular", sans-serif;
  font-size: 18px;
  font-weight: 400;
  display: flex;
  align-items: center;
  justify-content: flex-start;
  margin-bottom: 10px;
}

.space-between {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 7px;
}

/* 테이블 스타일 */
.reviews-table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 20px;
  border-top: 1px solid black;
}

td {
  padding: 10px;
  text-align: center;
  border-bottom: 1px solid #ddd;
  font-size: 15px;
  align-content: center;
}

th {
  background-color: #f5f5f5;
  font-weight: bold;
  padding: 20px;
  text-align: center;
  border-bottom: 1px solid #ddd;
  font-size: 15px;
}

.product-thumbnail {
  width: 60px;
  height: 60px;
  object-fit: cover;
}
.product-info {
  display: flex;
  gap: 10px;
}
.product-detail {
  text-align: left;
  padding-top: 20px;
}

.product-name {
  font-size: 13px;
  color: #777;
}

.brand-name {
  font-size: 13px;
  color: #2f2e2e;
}

.review-text {
  max-width: 200px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

td button {
  background: #0d1134;
  color: #fff;
  border-radius: 35px;
  width: 70px;
  height: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: "Inter-SemiBold", sans-serif;
  font-size: 15px;
  font-weight: 600;
  cursor: pointer;
}
</style>
