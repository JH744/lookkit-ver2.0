<template>
  <section class="section-inquiries">
    <p id="section-title">1:1 문의하기</p>
  </section>

  <div class="inquiry">
    <div class="inquiry-meta">
      <img src="/images/question.png" alt="" />
      <div>
        <p class="inquiry-subject">{{ inquiry.inquiryTitle }}</p>
        <span class="inquiry-date">
          {{ formatDateTime(inquiry.inquiryCreatedAt) }}
        </span>
      </div>
      <span class="answer-state" v-if="answer && answer.answerContents">{{
        answer ? "✔ 답변 완료" : ""
      }}</span>
    </div>
    <div class="inquiry-content">
      <p class="inquiry-text">{{ inquiry.inquiryContents }}</p>
      <div v-if="imageUrls.length">
        <div
          v-for="(url, index) in imageUrls"
          :key="index"
          class="inquiry-image-container"
        >
          <img class="inquiry-image" :src="url" alt="" />
        </div>
      </div>
    </div>
  </div>

  <div class="reply" v-if="Object.keys(answer).length > 0">
    <div class="inquiry-meta">
      <img class="meta-icon question" src="/images/answer.png" alt="" />
      <div>
        <p class="inquiry-subject">루킷 담당자</p>
        <span class="inquiry-date">{{
          formatDateTime(answer.answerCreatedAt)
        }}</span>
      </div>
    </div>
    <div class="reply-content">
      <p class="reply-text">{{ answer.answerContents }}</p>
    </div>
  </div>

  <div class="inquiry-btn-block">
    <router-link class="back-to-inquiry" to="/mypage/inquiry"
      >문의 페이지로</router-link
    >
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import api from "@/api/axios";
import { useRouter } from "vue-router";
import { ref as firebaseRef, getDownloadURL } from "firebase/storage";
import { firebaseStorage } from "@/firebase/firebaseConfig";

const router = useRouter();
const route = router.currentRoute.value;
const inquiry = ref({});

//추후 답변 작업(관리자)
const answer = ref({});
const imageUrls = ref([]); // 다운로드된 이미지 URL 리스트

const loadDetailInquiry = async () => {
  try {
    const inquiryId = route.params.inquiryId;
    const response = await api.get(`/api/mypage/inquiry/${inquiryId}`);
    inquiry.value = response.data.data;

    // 이미지 경로가 있다면 Firebase에서 다운로드 URL을 가져옴
    if (inquiry.value.inquiryImages && inquiry.value.inquiryImages.length > 0) {
      const urls = await Promise.all(
        inquiry.value.inquiryImages.map(async (image) => {
          try {
            const storageRef = firebaseRef(firebaseStorage, image.imagePath); // Firebase Storage 참조 생성
            const url = await getDownloadURL(storageRef); // 다운로드 URL 얻기
            return url;
          } catch (error) {
            console.error(
              `이미지 URL을 가져오는 중 오류 발생: ${image.imagePath}`,
              error
            );
            return null; // 실패한 경우 null 반환
          }
        })
      );

      // null이 아닌 URL만 필터링하여 imageUrls에 추가
      imageUrls.value = urls.filter((url) => url !== null);
    }
  } catch (error) {
    console.log(error);
  }
};

onMounted(() => {
  loadDetailInquiry();
});

// 날짜와 시간을 포맷팅하는 함수
const formatDateTime = (dateTime) => {
  const date = new Date(dateTime);

  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");
  const hour = String(date.getHours()).padStart(2, "0");
  const minute = String(date.getMinutes()).padStart(2, "0");

  return `${year}.${month}.${day} ${hour}:${minute}`;
};
</script>

<style scoped>
.section-inquiries {
  border-bottom: 2px solid #727272;
}
.inquiry {
  margin-top: 50px;
  margin-bottom: 30px;
}

.section-inquiries {
  border-bottom: 2px solid #727272;
}

.second-grid {
  width: 100%;
  margin-left: 50px;
}

#section-title {
  font-size: 24px;
  margin-top: 65px;
  margin-bottom: 17px;
}

.inquiry-meta {
  display: flex;
  align-items: center;
  gap: 15px;
  margin-bottom: 10px;
}

.inquiry-content {
  margin-left: 60px;
}

.inquiry-image-container {
  display: inline-flex;
  gap: 7px;
}

.inquiry-btn-block {
  margin-top: 50px;
  text-align: -webkit-center;
}

.meta-icon {
  width: 44px;
  height: 44px;
  border-radius: 50%;
  background: #0d1134;
  color: #ffffff;
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: "Almarai-Bold", sans-serif;
  font-size: 26px;
  font-weight: 700;
}

.meta-icon.question {
  background: #0d1134;
}

.meta-icon.answer {
  background: #0d1134;
}

.inquiry-date {
  font-size: 15px;
  color: #8b8b8b;
  margin-left: 2px;
}

.inquiry-status {
  font-size: 16px;
  color: #49a5b9;
  margin-top: -21px;
  margin-left: 20px;
}

.inquiry-subject {
  font-size: 18px;
  color: #000000;
  font-weight: 500;
  margin: 0px;
  margin-bottom: 5px;
}

.inquiry-text,
.reply-text {
  font-size: 15px;
  line-height: 1.6;
  margin-bottom: 30px;
  margin-top: 25px;
}

.inquiry-image {
  width: 130px;
}

.reply {
  margin-top: 80px;
}

.reply-content {
  margin-left: 60px;
}

.back-to-inquiry {
  background: #ffffff;
  border: 1px solid #555353;
  border-radius: 35px;
  width: 184px;
  height: 44px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: "Inter-Medium", sans-serif;
  font-size: 15px;
  font-weight: 500;
  margin-top: 20px;
  cursor: pointer;
}

.answer-state {
  align-self: baseline;
  font-size: 13px;
  color: #ff5722;
  font-weight: bold;
  margin-top: 4px;
}
</style>
