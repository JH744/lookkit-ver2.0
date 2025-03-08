<template>
  <div class="second-grid">
    <h1 class="title">리뷰 작성하기</h1>

    <section v-if="selectedProduct" class="review-section">
      <h2 class="subtitle">상품은 어떠셨나요?</h2>
      <div class="product-details">
        <img
          :src="selectedProduct.thumbnailUrl || '/images/placeholder.png'"
          alt="상품 이미지"
          class="product-image"
        />
        <div>
          <p class="product-name">{{ selectedProduct.brandName }}</p>
          <p class="product-model">{{ selectedProduct.productName }}</p>
          <p class="product-price">
            {{ selectedProduct.productPrice.toLocaleString() }}원 / 수량
            {{ selectedProduct.quantity }}개
          </p>
        </div>
      </div>
      <div class="rating-section">
        <div class="stars" @click="setRating($event)">
          <img
            v-for="n in 5"
            :key="n"
            :src="
              n <= rating ? '/images/full_star.png' : '/images/empty_star.png'
            "
            :alt="'별 ' + n"
            class="star"
          />
        </div>
        <p class="rating-text">{{ ratingText }}</p>
      </div>

      <section class="review-input-section">
        <h2 class="subtitle">대여한 상품의 리뷰를 남겨주세요</h2>
        <p class="subtitle-detail">본문 입력(필수)</p>
        <textarea
          class="review-text"
          v-model="reviewText"
          placeholder="리뷰 작성란"
          minlength="20"
        ></textarea>
        <div class="text-state">
          <p class="text-limit">5자 이상</p>
          <p class="text-count">{{ reviewText.length }} / 500</p>
        </div>
      </section>

      <section class="photo-upload-section">
        <h2 class="subtitle">사진 첨부</h2>
        <p class="photo-limit">사진 첨부는 3장까지만 가능합니다.</p>
        <div class="image-upload">
          <!-- 각 개별 이미지 업로드 영역 -->
          <div
            v-for="(image, index) in imagePreview"
            :key="index"
            class="image-placeholder"
            @click="selectSingleFile(index)"
          >
            <input
              :ref="(el) => (fileInputs[index] = el)"
              style="display: none"
              type="file"
              accept="image/*"
              @change="(event) => handleSingleFileChange(event, index)"
            />
            <div v-if="!image" class="plus-text">+</div>
            <img v-else :src="image" />
          </div>

          <!-- 다중 파일 업로드 버튼 -->
          <div class="upload-button" @click="triggerFileInput">
            <input
              type="file"
              ref="multipleFileInput"
              style="display: none"
              accept="image/*"
              multiple
              @change="handleMultipleFileChange"
            />
            <img class="upload-icon" src="/images/add_img.png" />
            <span class="upload-text">여러장 선택하기</span>
          </div>
        </div>
      </section>
      <button @click="showConfirmModal" class="submit-button">
        리뷰 작성 완료
      </button>
    </section>

    <section v-if="selectedCodi" class="review-section">
      <h2 class="subtitle">코디는 마음에 드셨나요?</h2>
      <div class="product-details">
        <img
          :src="selectedCodi.thumbnailUrl || '/images/placeholder.png'"
          alt="코디 이미지"
          class="product-image"
        />
        <div>
          <p class="product-name">{{ selectedCodi.codiDescription }}</p>
          <p class="product-price">코디 ID: {{ selectedCodi.codiName }}</p>
          <p class="product-price">
            {{ selectedCodi.codiPrice.toLocaleString() }}원
          </p>
        </div>
      </div>
      <div class="rating-section">
        <div class="stars" @click="setRating($event)">
          <img
            v-for="n in 5"
            :key="n"
            :src="
              n <= rating ? '/images/full_star.png' : '/images/empty_star.png'
            "
            :alt="'별 ' + n"
            class="star"
          />
        </div>
        <p class="rating-text">{{ ratingText }}</p>
      </div>

      <section class="review-input-section">
        <h2 class="subtitle">대여한 상품의 리뷰를 남겨주세요</h2>
        <p class="subtitle-detail">본문 입력(필수)</p>
        <textarea
          class="review-text"
          v-model="reviewText"
          placeholder="리뷰 작성란"
          minlength="20"
        ></textarea>
        <div class="text-state">
          <p class="text-limit">5자 이상</p>
          <p class="text-count">{{ reviewText.length }} / 500</p>
        </div>
      </section>

      <section class="photo-upload-section">
        <h2 class="subtitle">사진 첨부</h2>
        <p class="photo-limit">사진 첨부는 3장까지만 가능합니다.</p>
        <div class="image-upload">
          <!-- 각 개별 이미지 업로드 영역 -->
          <div
            v-for="(image, index) in imagePreview"
            :key="index"
            class="image-placeholder"
            @click="selectSingleFile(index)"
          >
            <input
              :ref="(el) => (fileInputs[index] = el)"
              style="display: none"
              type="file"
              accept="image/*"
              @change="(event) => handleSingleFileChange(event, index)"
            />
            <div v-if="!image" class="plus-text">+</div>
            <img v-else :src="image" />
          </div>

          <!-- 다중 파일 업로드 버튼 -->
          <div class="upload-button" @click="triggerFileInput">
            <input
              type="file"
              ref="multipleFileInput"
              style="display: none"
              accept="image/*"
              multiple
              @change="handleMultipleFileChange"
            />
            <img class="upload-icon" src="/images/add_img.png" />
            <span class="upload-text">여러장 선택하기</span>
          </div>
        </div>
      </section>

      <button @click="showConfirmModal" class="submit-button">
        리뷰 작성 완료
      </button>
    </section>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import axios from "@/api/axios";
import { useAuthStore } from "@/stores/authStore";
import { useRoute, useRouter } from "vue-router";
import { firebaseStorage } from "@/firebase/firebaseConfig";
import { ref as firebaseRef, getDownloadURL } from "firebase/storage";
import { useModalStore, useConfirmModalStore } from "@/stores/modalStore";

const authStore = useAuthStore();
const userId = authStore.user?.userId;
const route = useRoute();
const router = useRouter();
const selectedProduct = ref(null);
const selectedCodi = ref(null);
const rating = ref(0);
const reviewText = ref("");
const imageFiles = ref([]);
const fileInput = ref(null);
const fileInputs = ref([]);
const multipleFileInput = ref(null);
const imagePreview = ref([null, null, null]);

const fetchImageForItem = async (item, type) => {
  let storagePath;
  if (type === "product") {
    storagePath = `lookkit/products/0${item.productId}/${item.productId}_thumbnail.webp`;
  } else if (type === "codi") {
    storagePath = `lookkit/codi/0${item.codiId}/${item.codiId}_thumbnail.webp`;
  }

  console.log("이미지 경로 확인:", storagePath);
  try {
    const imageRef = firebaseRef(firebaseStorage, storagePath);
    const url = await getDownloadURL(imageRef);
    item.thumbnailUrl = url;
  } catch (error) {
    console.error(`이미지 가져오기 실패: ${storagePath}`, error);
    item.thumbnailUrl = null;
  }
};

onMounted(() => {
  const productId = route.query.productId;
  const codiId = route.query.codiId;
  if (productId) {
    fetchPurchasedProduct(Number(productId), authStore.user.userId);
  } else if (codiId) {
    fetchPurchasedCodi(Number(codiId), authStore.user.userId);
  } else {
    alert("리뷰를 작성할 상품 또는 코디 정보가 없습니다.");
    router.push("/mypage/manage");
  }
});

const fetchPurchasedProduct = async (productId, userId) => {
  try {
    // 사용자 리뷰 가져오기
    const reviewResponse = await axios.get(`/api/reviews/list/${userId}`);
    const userReviews = reviewResponse.data;

    // 해당 상품에 대한 리뷰가 이미 작성되었는지 확인
    const existingReview = userReviews.find(
      (review) => review.productId === productId
    );
    if (existingReview) {
      // 이미 해당 상품에 대한 리뷰가 존재할 경우
      const modalStore = useModalStore();
      modalStore.showModal(
        "리뷰 작성 제한",
        "이미 해당 상품에 대한 리뷰를 작성하셨습니다.",
        "주문 관리 페이지로 이동합니다.",
        "확인"
      );
      router.push("/mypage/manage");
      return;
    }

    const response = await axios.get(`/api/products/${productId}`);
    selectedProduct.value = response.data;
    await fetchImageForItem(selectedProduct.value);
  } catch (error) {
    console.error("상품 정보를 가져오는 중 오류 발생:", error);
  }
};

const fetchPurchasedCodi = async (codiId, userId) => {
  try {
    const reviewResponse = await axios.get(`/api/reviews/list/${userId}`);
    const userReviews = reviewResponse.data;

    const existingReview = userReviews.find(
      (review) => review.codiId === codiId
    );
    if (existingReview) {
      const modalStore = useModalStore();
      modalStore.showModal(
        "리뷰 작성 제한",
        "이미 해당 코디에 대한 리뷰를 작성하셨습니다.",
        "주문 관리 페이지로 이동합니다.",
        "확인"
      );
      router.push("/mypage/manage");
      return;
    }

    const response = await axios.get(`/api/codi/${codiId}`);
    selectedCodi.value = response.data;
    await fetchImageForItem(selectedCodi.value, "codi");
  } catch (error) {
    console.error("코디 정보를 가져오는 중 오류 발생:", error);
  }
};

const setRating = (event) => {
  const clickedStarIndex =
    Array.from(event.target.parentNode.children).indexOf(event.target) + 1;
  rating.value = clickedStarIndex;
};

const ratingText = computed(() => {
  switch (rating.value) {
    case 1:
      return "별로에요";
    case 2:
      return "그저 그래요";
    case 3:
      return "괜찮아요";
    case 4:
      return "좋아요";
    case 5:
      return "최고에요";
    default:
      return "";
  }
});

// 파일 업로드 선택 트리거
const selectSingleFile = (index) => {
  const inputRef = fileInputs.value[index];
  if (inputRef) {
    inputRef.click();
  }
};

const triggerFileInput = () => {
  if (multipleFileInput.value) {
    multipleFileInput.value.click();
  }
};

const handleSingleFileChange = (event, index) => {
  const file = event.target.files[0];
  if (!file) return;
  imageFiles.value[index] = file;
  imagePreview.value[index] = URL.createObjectURL(file);
};

const handleMultipleFileChange = (event) => {
  const files = Array.from(event.target.files);
  if (files.length > 3) {
    alert("사진은 최대 3개까지만 업로드 가능합니다.");
    return;
  }
  imageFiles.value = [];
  imagePreview.value = [null, null, null];
  files.forEach((file, index) => {
    if (index < 3) {
      imageFiles.value[index] = file;
      imagePreview.value[index] = URL.createObjectURL(file);
    }
  });
};

const uploadImagesToFirebase = async () => {
  const urls = [];
  for (let i = 0; i < imageFiles.value.length; i++) {
    if (imageFiles.value[i]) {
      const file = imageFiles.value[i];
      const uniqueName = `${Date.now()}-${file.name}`;
      const storageRef = firebaseRef(
        firebaseStorage,
        `uploads/reviews/${uniqueName}`
      );
      try {
        if (file.size > 5 * 1024 * 1024) {
          throw new Error(`파일 ${file.name}이 5MB를 초과합니다`);
        }
        const snapshot = await uploadBytes(storageRef, file);
        const url = await getDownloadURL(storageRef);
        urls.push(url);
      } catch (error) {
        console.error(`Error uploading image ${file.name}:`, error);
      }
    }
  }
  return urls;
};
const showConfirmModal = () => {
  const confirmModalStore = useConfirmModalStore();
  confirmModalStore.showModal(
    "리뷰 작성",
    "이렇게 리뷰를 작성하시겠습니까?",
    "작성된 리뷰는 다시 볼 수 있습니다.",
    "작성하기",
    submitReview
  );
};

const submitReview = async () => {
  if (rating.value === 0 || reviewText.value.length < 5) {
    alert(
      "별점과 리뷰 내용을 모두 입력해주세요. 리뷰는 최소 5자 이상 작성해주세요."
    );
    return;
  }

  try {
    const imageUrls = await uploadImagesToFirebase();
    const reviewData = {
      userId: userId,
      rating: rating.value,
      reviewText: reviewText.value,
      imageUrls,
    };

    if (selectedProduct.value) {
      reviewData.productId = selectedProduct.value.productId;
    } else if (selectedCodi.value) {
      reviewData.codiId = selectedCodi.value.codiId;
    } else {
      alert("리뷰 작성할 상품이나 코디가 없습니다.");
      return;
    }

    // FormData 설정
    const formData = new FormData();
    formData.append(
      "reviewDTO",
      new Blob([JSON.stringify(reviewData)], { type: "application/json" })
    );

    await axios.post("/api/reviews/write", formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });

    const modalStore = useModalStore();
    modalStore.showModal("리뷰 작성", "리뷰 작성이 완료되었습니다.");
    router.push("/mypage/review");
  } catch (error) {
    console.error("리뷰 작성 실패:", error);
    alert("리뷰 작성에 실패했습니다. 다시 시도해주세요.");
  }
};
</script>

<style scoped>
.section-review {
  border-bottom: 2px solid #727272;
}

.section-review p:first-of-type {
  font-size: 24px;
  margin-top: 65px;
  margin-bottom: 0px;
}

.section-review p:nth-of-type(2) {
  font-size: 13px;
  margin: 20px 0px;
}

.review-container {
  width: 1170px;
  margin: 0 auto;
  padding: 20px;
}

.title {
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

.subtitle {
  font-size: 18px;
  font-weight: 600;
  color: #363636;
  margin-top: 20px;
  margin-bottom: 10px;
}

.subtitle-detail {
  margin-bottom: 5px;
  font-size: 13px;
  font-weight: bold;
  color: #363636;
}

.product-info {
  border-top: 1px solid #000;
  margin-bottom: 40px;
}

.product-details {
  display: flex;
  align-items: center;
  margin-top: 20px;
  margin-bottom: 20px;
}

.product-image {
  width: 86px;
  height: 86px;
  object-fit: cover;
  margin-right: 20px;
}

.product-name {
  font-size: 15px;
  font-weight: 500;
  color: #000;
}

.product-model {
  font-size: 13px;
  color: #000;
}

.product-price {
  font-size: 13px;
  color: #555353;
  font-weight: 500;
  margin-top: 10px;
}

.rating-section {
  display: flex;
  align-items: center;
  margin-top: 10px;
}

.rating-text {
  font-size: 13px;
  color: #555353;
  margin-right: 10px;
  margin-left: 20px;
}

.stars {
  display: flex;
}

.star {
  width: 30px;
  height: 30px;
  margin-right: 5px;
}

.review-input-section {
  margin-top: 20px;
  margin-bottom: 40px;
}

.review-text {
  width: 100%;
  height: 150px;
  border: 1px solid #f4f4f4;
  padding: 10px;
  font-size: 13px;
  color: #797979;
  resize: none;
}

.text-count,
.text-limit {
  font-size: 13px;
  color: #797979;
  margin-top: 5px;
}

.text-state {
  display: flex;
  place-content: space-between;
}

.photo-upload-section {
  margin-top: 20px;
}

.image-upload {
  display: flex;
  gap: 10px;
  margin-top: 7px;
  align-items: center;
}

.image-placeholder {
  background: #f0f0f0;
  width: 85px;
  height: 85px;
  border: 1px dashed #ccc;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 24px;
  color: #aaa;
  cursor: pointer;
}

.upload-button {
  background: #fff;
  border: 1px solid #ccc;
  width: 150px;
  height: 85px;
  display: grid;
  align-content: center;
  justify-items: center;
  gap: 5px;
  cursor: pointer;
}

.upload-icon {
  width: 25px;
}

.upload-text {
  color: #555553;
  font-family: "Inter-Medium", sans-serif;
  font-size: 13px;
}

.photo-limit {
  font-size: 13px;
}

.button-group {
  display: flex;
  justify-content: center;
  gap: 7px;
  margin-top: 75px;
}

.submit-button {
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

.btn-cancel {
  background: #fff;
  color: #000;
  border: 1px solid #b2b2b2;
  border-radius: 35px;
  width: 184px;
  height: 44px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: "Inter-Medium", sans-serif;
  font-size: 15px;
  font-weight: 500;
  cursor: pointer;
}

.notice {
  display: grid;
  align-items: center;
  margin-top: 80px;
}

.notice-icon {
  background: #b8b8b8;
  color: #fff;
  border-radius: 50%;
  width: 21px;
  height: 21px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: "Georgia-Bold", sans-serif;
  font-size: 15px;
  font-weight: 700;
  margin-right: 7px;
}

.notice-text {
  color: #818181;
  font-family: "Inter-Medium", sans-serif;
  font-size: 18px;
}

.notice-text ul {
  font-size: 13px;
  margin-top: 10px;
}

.notice-text ul li {
  margin-bottom: 5px;
}

.notice-icon-block {
  display: inline-flex;
  align-items: center;
}

.notice-icon-block span {
  color: #818181;
  font-size: 17px;
}

.plus-text {
  font-size: 50px;
  font-weight: lighter;
}
</style>
