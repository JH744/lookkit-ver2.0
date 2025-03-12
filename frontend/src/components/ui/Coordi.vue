<template>
  <div class="codi-item">
    <div class="codi-image" @click="navigateToDetail">
      <img :src="thumbnailUrl" :alt="codiName" @error="handleImageError" />
    </div>
    <div class="codi-info">
      <h3 class="codi-name">{{ codiName }}</h3>
      <p class="codi-price">{{ formatPrice(codiPrice) }}원</p>
    </div>
    <div>
      <img
        v-if="authStore.isLoggedIn"
        :src="isWishlisted ? heartIcon1 : heartIcon2"
        class="like-btn"
        @click="likeCodi(codiId)"
        width="20px"
        height="20px"
        alt=""
      />
    </div>
  </div>
</template>

<script setup>
import { computed, ref, onMounted } from "vue";
import defaultImage from "@/assets/img_none.png";
import heart1 from "@/assets/icons/heart1.svg";
import heart2 from "@/assets/icons/heart2.svg";
import { useRouter } from "vue-router";
import { useAuthStore } from "@/stores/authStore";
import api from "@/api/axios";
const authStore = useAuthStore();
const router = useRouter();
const heartIcon1 = heart1;
const heartIcon2 = heart2;
const isWishlisted = ref(false);

// props 정의
const props = defineProps({
  codiId: {
    type: Number,
    required: true,
  },
  codiName: {
    type: String,
    required: true,
  },
  codiPrice: {
    type: Number,
    required: true,
  },
  codiThumbnail: {
    type: String,
    required: true,
  },
  imageBaseUrl: {
    type: String,
    default:
      "https://firebasestorage.googleapis.com/v0/b/test-24a07.appspot.com/o/lookkit",
  },
});

// 이미지 URL 생성
const thumbnailUrl = computed(() => {
  const folderPath = `/codi/0${props.codiId}`;
  const fileName = `/${props.codiId}_thumbnail.webp`;
  const encodedPath = `${encodeURIComponent(folderPath)}${encodeURIComponent(
    fileName
  )}`;
  return `${props.imageBaseUrl}${encodedPath}?alt=media`;
});

// 상세 페이지 이동
const navigateToDetail = () => {
  router.push(`/codi/${props.codiId}`);
};

// 가격 포맷팅
const formatPrice = (price) => {
  return price.toLocaleString("ko-KR");
};

// 이미지 에러 처리
const handleImageError = (event) => {
  event.target.src = defaultImage;
};

const likeCodi = async (codiId) => {
  console.log("코디 좋아요 추가");
  try {
    const response = await api
      .post("/api/main/coordi/wish/add", {
        userId: authStore.user.userId,
        codiId: codiId,
      })
      .then((data) => {
        console.log("data", data);
      })
      .catch((err) => {
        console.log(err);
      });
    console.log("respnonse", response);
    isWishlisted.value = true;
  } catch (error) {}
};

// 위시리스트 체크 메소드 추가
const checkWishlist = async () => {
  try {
    const response = await api.get(
      `/api/main/checkBatchCodi/${authStore.user.userId}/${props.codiId}`
    );
    isWishlisted.value = response.data;
    // console.log("Wishlist Response:", response.data);
  } catch (error) {
    console.error("위시리스트 체크 중 오류 발생:", error);
  }
};

// 컴포넌트 마운트 시 위시리스트 체크
onMounted(() => {
  if (authStore.isLoggedIn) {
    checkWishlist();
  }
});
</script>

<style scoped>
.codi-item {
  width: 250px;
  margin: 10px;
  border-radius: 8px;
  overflow: hidden;
  background: #ffffff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.codi-image {
  width: 100%;
  height: 300px;
  overflow: hidden;
}

.codi-image img {
  width: 100%;
  height: 100%;
  margin-top: 15px;
  object-fit: contain;
  transition: transform 0.3s ease;
  cursor: pointer;
}

.codi-image img:hover {
  transform: scale(1.05);
}

.codi-info {
  padding: 15px;
}

.codi-name {
  font-size: 16px;
  font-weight: 500;
  margin-bottom: 8px;
  color: #333;
}

.codi-price {
  font-size: 18px;
  font-weight: 700;
  color: #000;
}

.like-btn {
  position: absolute;
  bottom: 10px;
  right: 10px;
  width: 24px;
  height: 24px;
  cursor: pointer;
  transition: transform 0.2s ease;
}

.like-btn:hover {
  transform: scale(1.1);
}

.codi-item {
  position: relative;
}
</style>
