<template>
  <div class="codi-item">
    <div class="codi-image">
      <img :src="thumbnailUrl" :alt="codiName" @error="handleImageError" />
    </div>
    <div class="codi-info">
      <h3 class="codi-name">{{ codiName }}</h3>
      <p class="codi-price">{{ formatPrice(codiPrice) }}원</p>
    </div>
  </div>
</template>

<script setup>
import { computed } from "vue";
import defaultImage from "@/assets/img_none.png";

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
    type: Number,
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

// 가격 포맷팅
const formatPrice = (price) => {
  return price.toLocaleString("ko-KR");
};

// 이미지 에러 처리
const handleImageError = (event) => {
  event.target.src = defaultImage;
};
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
  object-fit: cover;
  transition: transform 0.3s ease;
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
</style>
