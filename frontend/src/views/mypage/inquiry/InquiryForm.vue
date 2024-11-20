<template>
  <section class="section-inquiries">
    <p>1:1 문의하기</p>
    <p>한번 등록한 상담내용은 수정이 불가능합니다.</p>
  </section>

  <div class="inquiry-form">
    <div class="form-title">
      <form @submit.prevent="submitForm" enctype="multipart/form-data">
        <div class="inquiry-inline">
          <p class="inquiry-content-title">문의 제목</p>
          <p class="error-message" v-if="titleError">문의명을 입력해주세요</p>
        </div>
        <input type="text" v-model="formData.title" class="input-title" placeholder="문의명을 입력하세요" required />
      </form>
    </div>
    <div class="form-content">
      <div class="inquiry-inline">
        <p class="inquiry-content-title">문의 내용</p>
        <p class="error-message" v-if="contentError">내용을 입력해 주세요</p>
      </div>
      <textarea v-model="formData.content" class="input-content" placeholder="문의 내용은 2,000자 이내로 입력하세요" required></textarea>
    </div>
    <div class="image-upload">
      <!-- 각 개별 이미지 업로드 영역 -->
      <div v-for="(image, index) in imagePreview" :key="index" class="image-placeholder" @click="selectSingleFile(index)">
        <input :ref="el => fileInputs[index] = el" style="display:none;" type="file" accept="image/*" @change="event => handleSingleFileChange(event, index)" />
        <div v-if="!image" class="plus-text">+</div>
        <img v-else :src="image" />
      </div>

      <!-- 다중 파일 업로드 버튼 -->
      <div class="upload-button" @click="triggerFileInput">
        <input type="file" ref="multipleFileInput" style="display:none;" accept="image/*" multiple @change="handleMultipleFileChange" />
        <img class="upload-icon" src="/images/add_img.png">
        <span class="upload-text">여러장 선택하기</span>
      </div>
    </div>

    <div class="button-group">
      <div class="btn-submit" @click="submitForm">등록하기</div>
      <div class="btn-cancel" @click="cancelForm">취소하기</div>
    </div>
    <div class="notice">
      <div class="notice-icon-block">
        <p class="notice-icon">!</p>
        <span>유의사항</span>
      </div>
      <div class="notice-text">
        <ul>
          <li>개인정보를 남기면 타인에 의해 도용될 수 있으니 문의하기 작성 시 주의해주세요</li>
          <li>사진은 최대 3개까지 업로드 가능합니다</li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import axios from 'axios';
import router from '@/router';
import { uploadBytes } from "firebase/storage";
import { storage } from "@/firebase/firebaseConfig";

const formData = reactive({
  title: '',
  content: '',
  imageFile: []
});

const titleError = ref(false);
const contentError = ref(false);

// 단일 파일 업로드 영역
const fileInputs = ref([]);
//다중 파일 업로드 영역
const multipleFileInput = ref(null);
//미리보기 영역
const imagePreview = ref([null,null,null]);

// 단일 파일 업로드 선택 트리거
const selectSingleFile = (index) => {
  const inputRef = fileInputs.value[index];
  if (inputRef) {
    inputRef.click();
  }
};

// 다중 파일 업로드 선택 트리거
const triggerFileInput = () => {
  if (multipleFileInput.value) {
    multipleFileInput.value.click();
  }
};

// 단일 파일 변경 처리
const handleSingleFileChange = (event, index) => {
  const file = event.target.files[0];

  if (!file) return;

  formData.imageFile[index] = file; // 파일 객체 저장
  imagePreview.value[index] = URL.createObjectURL(file); // 미리보기 URL 저장
};

// 여러 파일 변경 처리
const handleMultipleFileChange = (event) => {
  const files = Array.from(event.target.files);

  // 이미지 최대 개수 검사
  if (files.length > 3) {
    alert('사진은 최대 3개까지만 업로드 가능합니다.');
    return;
  }

  // 기존 파일 및 미리보기 초기화
  formData.imageFile = [];
  imagePreview.value = [null, null, null];

  // 새로 선택한 파일로 업데이트
  files.forEach((file, index) => {
    if (index < 3) {
      formData.imageFile[index] = file;
      imagePreview.value[index] = URL.createObjectURL(file);
    }
  });
};

const submitForm = async () => {
  titleError.value = !formData.title;
  contentError.value = !formData.content;

  if (!titleError.value && !contentError.value) {
    console.log('Form Submitted:', formData);
    
    const formDataToSend = new FormData();
     // JSON 데이터로 변경
     const requestPayload = {
      userId: 5,
      inquiryTitle: formData.title,
      inquiryContents: formData.content
    };
    formDataToSend.append('request', new Blob([JSON.stringify(requestPayload)], { type: 'application/json' }));

     // 파일이 있는 경우에만 추가
     if (formData.imageFile && formData.imageFile.length > 0) {
        formData.imageFile.forEach((file) => {
          formDataToSend.append('files', file);
        });
      }

      try {
        const response = await axios.post('http://localhost:8081/api/mypage/inquiry', formDataToSend, {
          headers: {
          'Content-Type': 'multipart/form-data'
        }
        });
        router.push({
          name: 'InquiryDetail',
          params: {inquiryId: response.data.data.inquiryId}
        })

        console.log(response.data.data);
      } catch (error) {
        console.error('Error loading inquiries:', error);
      }
  };
  }

const cancelForm = () => {
  history.back();
};


</script>

<style scoped>
.section-inquiries {
    border-bottom: 2px solid #727272;

}

.section-inquiries p:first-of-type {
    font-size: 24px;
    margin-top: 65px;
    margin-bottom: 0px;
}

.section-inquiries p:nth-of-type(2) {
   font-size : 13px;
   margin: 20px 0px;
}

.space-between {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 5px;
}

.inquiry-form,
.inquiry-form * {
    box-sizing: border-box;
}

.inquiry-form {
    height: auto;
    position: relative;
}
.form-content {
    margin-top: 50px;
}

.inquiry-content-title {
    font-size: 17px;
}

.button-group {
    display: flex;
    justify-content: center;
    gap: 7px;
    margin-top: 75px;
}

.btn-submit {
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
    border: 1px solid #b2b2b2;;
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

.input-title {
    background: #fff;
    border: 1px solid #b2b2b2;;
    padding: 10px;
    font-family: "Inter-Medium", sans-serif;
    font-size: 16px;
    width: 100%;
}

.inquiry-inline {
    display: inline-flex;
    margin-top: 40px;
    margin-bottom: 8px;
    align-items: center;
}

.input-content {
    background: #fff;
    border: 1px solid #b2b2b2;;
    padding: 10px;
    font-family: "Inter-Medium", sans-serif;
    font-size: 16px;
    resize: none;
    height: 200px;
    width: 100%;
}

.error-message {
    color : #F44336;
    margin: 0px;
    font-size: 15px;
    margin-left: 15px;
    display : none;
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

.image-placeholder img {
    width: 100%;
    height: 100%;
    object-fit: cover;
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
    font-size: 16px;
}

/* 유의사항 */
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

.notice-text ul{
    font-size: 13px;
    margin-top: 10px;
}

.notice-text ul li{
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