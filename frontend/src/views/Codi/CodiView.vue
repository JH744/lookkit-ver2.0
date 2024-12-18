<template>
  <div class="container">
    <div class="image-section">
      <img :src="thumbnailUrl" alt="썸네일" />
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
            <li>3일 이후부터 대여 가능</li>
            <li>최소 대여 기간 3일</li>
            <li>3일 이후 대여료 + 추가요금</li>
            <li>추가 요금: 10,000원 / 1일</li>
          </ul>
        </div>
        <div class="rental-section">
          <div class="rental-label">대여기간</div>
          <Datepicker v-model="rentalStartDate" :format="format" :disabled-dates="disabledStartDates" @update:model-value="validateRentalStartDate" class="rental-input" placeholder="대여일"/>
          <Datepicker v-model="rentalEndDate" :format="format" class="rental-input" placeholder="반납일"/>
        </div>
        <div v-if="rentalStartDate && rentalEndDate" class="selected-rental-info">
          <div class="selected-rental-summary">
            <p>대여일 {{ formattedRentalStartDate }}</p>
            <p>반납일 {{ formattedRentalEndDate }}</p>
            <p>대여 일수 {{ totalRentalDays }}일</p>
            <p><strong style="font-size: 1.5em;">총 대여 금액 {{ formattedTotalPrice }}</strong></p>
          </div>
          <button @click="resetRentalDates" class="btn btn-reset">X</button>
        </div>
      </div>
      <div class="buttons">
        <button @click="confirmAddToCart" class="btn btn-cart">옷장담기</button>
        <button @click="confirmRentNow"  class="btn btn-rent">대여하기</button>
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
    <div class="container3v">
      <div v-if="activeTab === 'details'" class="tab-content" id="details">
        <div v-for="(detailUrl, index) in detailImageUrls" :key="index">
          <img :src="detailUrl" :alt="'코디사진' + (index + 1)" />
        </div>
      </div>
      <div v-if="activeTab === 'reviews'" class="tab-content" id="reviews">
        <ReviewView :codiId="codiId"/>
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
      <img :src="thumbnailUrl" alt="상품 이미지" />
      <div class="product-description">{{ codi.codiDescription }}</div>
    </div>
    <div class="selected-rental-summary2">
      <div>
        <p>대여일 {{ formattedRentalStartDate }}</p>
        <p>반납일 {{ formattedRentalEndDate }}</p>
      </div>
      <p><strong style="font-size: 1.5em;">총 대여 금액 {{ formattedTotalPrice }}</strong></p>
    </div>
    <div class="buttons">
      <button @click="confirmAddToCart" class="btn btn-cart">옷장담기</button>
      <button @click="confirmRentNow"  class="btn btn-rent">대여하기</button>
    </div>
  </div>
  <!-- 모달 컴포넌트 추가 -->
  <div v-if="confirmModalStore.isModalVisible" class="modal">
    <div class="modal-content">
      <h3>{{ confirmModalStore.modalActionMessage }}</h3>
      <p>{{ confirmModalStore.questionMessage }}</p>
      <p>{{ confirmModalStore.warningMessage }}</p>
      <div class="modal-actions">
        <button @click="confirmModalStore.confirmCallback">확인</button>
        <button @click="confirmModalStore.closeModal">취소</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { getDownloadURL, ref as firebaseRef } from "firebase/storage";
import { firebaseStorage } from "@/firebase/firebaseConfig";
import axios from 'axios';
import "@/assets/styles/codi.css";
import ReviewView from '@/views/Review/ReviewView.vue'; 
import Datepicker from 'vue3-datepicker';
import { useAuthStore } from "@/stores/authStore";
import { useConfirmModalStore } from "@/stores/modalStore";

const API_BASE_URL = 'http://localhost:8081/api/codi';
const authStore = useAuthStore();
const jwtToken = authStore.token;
const userId = authStore.user?.userId;

console.log("JWT Token:", jwtToken);
console.log("User ID from AuthStore:", userId); 

const activeTab = ref('details');
const codi = ref({});
const route = useRoute();
const codiId = ref(route.params.codiId);

const thumbnailUrl = ref(""); 
const detailImageUrls = ref([]); // 여러 상세 이미지 URL을 저장할 배열

// Firebase에서 이미지 가져오기 함수
const fetchImages = async () => {
  try {
    // 썸네일 이미지 가져오기
    const thumbnailRef = firebaseRef(firebaseStorage, `lookkit/codi/0${codiId.value}/${codiId.value}_thumbnail.webp`);
    thumbnailUrl.value = await getDownloadURL(thumbnailRef);

    // 상세 이미지 가져오기 (최소 4장, 최대 6장)
    for (let i = 1; i <= 6; i++) {
      try {
        const detailRef = firebaseRef(firebaseStorage, `lookkit/codi/0${codiId.value}/${codiId.value}_detail_${i}.webp`);
        const detailUrl = await getDownloadURL(detailRef);
        detailImageUrls.value.push(detailUrl);
      } catch (error) {
        console.log(`디테일 이미지 ${i} 가져오기 실패 (존재하지 않을 수 있음)`, error);
      }
    }
  } catch (error) {
    console.error("이미지 가져오기 실패:", error);
  }
};

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
  fetchImages(); // 이미지 가져오기 호출
});

const rentalStartDate = ref(null);
const rentalEndDate = ref(null);

// 오늘로부터 최소 3일 이후를 설정
const today = new Date();
const minStartDate = new Date(today);
minStartDate.setDate(today.getDate() + 3);

// 대여 시작일에 3일 이후 날짜만 선택 가능하도록 제한하는 함수
const disabledStartDates = (date) => {
  return date < minStartDate;
};

const confirmModalStore = useConfirmModalStore();

const validateRentalStartDate = (value) => {
  if (value && new Date(value) < minStartDate) {
    
    confirmModalStore.showModal(
      "날짜 선택 오류",                 // 모달의 제목
      "대여 시작일은 최소 3일 이후만 가능합니다.", // 모달의 질문 메시지
      "",                              // 경고 메시지 (빈 문자열로 전달해도 됨)
      "확인",                          // 확인 버튼 텍스트
      () => {
        rentalStartDate.value = null;  // 모달 확인 시 잘못된 선택 초기화
      }
    );
  }
};



// 날짜 포맷 함수 정의
const formatDate = (date) => {
  if (!date) return '';
  
  const options = {
    year: undefined,
    month: 'long',
    day: 'numeric',
    weekday: 'long',
  };
  return new Intl.DateTimeFormat('ko-KR', options).format(new Date(date));
};

// 포맷된 대여일 및 반납일 계산
const formattedRentalStartDate = computed(() => formatDate(rentalStartDate.value));
const formattedRentalEndDate = computed(() => formatDate(rentalEndDate.value));

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

const getFormattedDate = (date) => {
  if (!date) return '';
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  return `${year}-${month}-${day}`;
};

const addToCart = async () => {
  try {
    
    const API_BASE_URL = 'http://localhost:8081/api/cart';
    const formattedStartDate = getFormattedDate(new Date(rentalStartDate.value));
    const formattedEndDate = getFormattedDate(new Date(rentalEndDate.value));

    const response = await axios.post(
  `${API_BASE_URL}/add/codi/${codi.value.codiId}?rentalStartDate=${formattedStartDate}&rentalEndDate=${formattedEndDate}&userId=${userId}`,
  {},
  {
    headers: {
      Authorization: `Bearer ${authStore.token}`
    }
  }
);
  
    window.location.href = '/cart';
  } catch (error) {
    console.error("옷장에 추가 실패:", error);
    alert('옷장에 추가하는 데 실패했습니다.');
  }
};

const confirmAddToCart = () => {
  confirmModalStore.showModal(
    "옷장담기",
    "이 상품을 옷장에 담으시겠습니까?",
    "",
    "담기",
    () => {
      confirmModalStore.closeModal();
      addToCart();
    }
  );
};

const rentNow = async () => {
  try {
    const formattedStartDate = getFormattedDate(new Date(rentalStartDate.value));
    const formattedEndDate = getFormattedDate(new Date(rentalEndDate.value));

    const response = await axios.post(`${API_BASE_URL}/rent?codiId=${codi.value.codiId}&startDate=${formattedStartDate}&endDate=${formattedEndDate}`);

    const orderDTO = response.data;

    const orderParams = new URLSearchParams({
      itemId: orderDTO.itemId,
      itemName: orderDTO.itemName,
      startDate: orderDTO.startDate,
      endDate: orderDTO.endDate,
      price: orderDTO.price,
      totalPrice: orderDTO.totalPrice,
      itemType: 'codi'
    });

    window.location.href = `/order?${orderParams.toString()}`;
  } catch (error) {
    alert('대여 페이지로 이동하는 데 실패했습니다.');
    console.error("대여 오류:", error);
  }
};
const confirmRentNow = () => {
  confirmModalStore.showModal(
    "대여하기",
    "이 상품을 대여하시겠습니까?",
    "",
    "대여",
    () => {
      confirmModalStore.closeModal();
      rentNow();
    }
  );
};
</script>
