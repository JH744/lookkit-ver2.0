<template>
        <section class="section-inquiries">
          <div>
            <p id="section-title">1:1 문의내역</p>
          </div>
          <div class="space-between">
            <p style="font-size:13px">한번 등록한 상담내용은 수정이 불가능합니다.</p>
            <button class="inquiry-btn" @click="createInquiry">+ 문의하기</button>
          </div>
        </section>

        <table class="inquiries-table">
          <thead>
            <tr>
              <th>문의 제목</th>
              <th>작성일</th>
              <th>답변여부</th>
              <th></th> <!-- 삭제 버튼 공간 -->
            </tr>
          </thead>
          <tbody>
            <tr v-for="inquiry in inquiries" :key="inquiry.inquiryId">
              <td class="right-padding">
                <a :href="`/mypage/inquiry/view/${inquiry.inquiryId}`">{{ inquiry.inquiryTitle }}</a>
              </td>
              <td>{{ formatDate(inquiry.inquiryCreatedAt) }}</td>
              <td class="answer-text">{{ inquiry.answerState === 'Y' ? 'YES' : 'NO' }}</td>
              <td><button @click="deleteInquiry(inquiry.inquiryId)" class="delete-btn">삭제</button></td>
            </tr>
          </tbody>
        </table>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import router from '@/router';

// 데이터 상태 관리
const inquiries = ref([]);

// 데이터 로드 함수
const loadInquiries = async () => {
  try {
    const response = await axios.get('http://localhost:8081/api/inquiry/5');
    inquiries.value = response.data.data;
    console.log(inquiries.value);
  } catch (error) {
    console.error('Error loading inquiries:', error);
  }
};

// 날짜 형식 변환 함수
const formatDate = (dateString) => {
  const options = { year: 'numeric', month: '2-digit', day: '2-digit' };
  return new Date(dateString).toLocaleDateString('ko-KR', options);
};

// 문의 삭제 함수
const deleteInquiry = async (inquiryId) => {
  try {
    await axios.delete(`/api/inquiries/${inquiryId}`);
    inquiries.value = inquiries.value.filter((inquiry) => inquiry.inquiryId !== inquiryId);
  } catch (error) {
    console.error('Error deleting inquiry:', error);
  }
};

// 문의 생성 함수
const createInquiry = () => {
    router.push('/mypage/inquiry/create');
};

// 컴포넌트 마운트 시 데이터 로드
onMounted(() => {
  loadInquiries();
});
</script>

<style scoped>

#section-title {
    font-size: 24px;
    margin-top: 65px;
    margin-bottom: 0px;
}

.space-between {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 7px;
}

/* 테이블 스타일 */
.inquiries-table {
    width: 100%;
    border-collapse: collapse;
    margin-bottom: 20px;
    border-top: 1px solid black;
}

th, td {
    padding: 15px;
    text-align: center;
    border-bottom: 1px solid #ddd;
    font-size: 15px;
}

th {
    background-color: #f5f5f5;
    font-weight: bold;
}

.right-padding {
  padding: 0px 100px;
}


td button {
    padding: 5px 10px;
    background-color: #ffffff;
    border: 1px solid #ccc;
    cursor: pointer;
}

td button:hover {
    background-color: #aaa;
}

/* "문의하기" 버튼 스타일 */
.inquiry-btn {
    display: inline-block;
    background-color: #0d1134;
    color: white;
    padding: 10px 20px;
    text-decoration: none;
    width: 140px;
}

.inquiry-btn:hover {
    cursor: pointer;
}

/* 답변 여부 강조 */
.answer-text {
    font-weight: bolder;
    font-size: 20px;
}
</style>