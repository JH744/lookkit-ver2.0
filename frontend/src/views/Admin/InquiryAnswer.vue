<template>
  <nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
    <!-- Navbar Brand -->
    <a class="navbar-brand ps-3" :href="dashboardUrl">ADMIN PAGE</a>
    <!-- Sidebar Toggle -->
    <button
      class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0"
      id="sidebarToggle"
      type="button"
      @click="toggleSidebar"
    >
      <i class="fas fa-bars"></i>
    </button>
    <!-- Navbar Search -->
    <form
      class="d-none d-md-inline-block form-inline ms-auto me-0 me-md-3 my-2 my-md-0"
    >
      <div class="input-group">
        <input
          class="form-control"
          type="text"
          placeholder="Search for..."
          aria-label="Search for..."
          aria-describedby="btnNavbarSearch"
        />
        <button class="btn btn-primary" id="btnNavbarSearch" type="button">
          <i class="fas fa-search"></i>
        </button>
      </div>
    </form>
    <!-- Navbar -->
    <ul class="navbar-nav ms-auto ms-md-0 me-3 me-lg-4">
      <li class="nav-item dropdown">
        <a
          class="nav-link dropdown-toggle"
          id="navbarDropdown"
          href="#"
          role="button"
          data-bs-toggle="dropdown"
          aria-expanded="false"
        >
          <i class="fas fa-user fa-fw"></i>
        </a>
        <ul
          class="dropdown-menu dropdown-menu-end"
          aria-labelledby="navbarDropdown"
        >
          <li><a class="dropdown-item" href="#!">Settings</a></li>
          <li><a class="dropdown-item" href="#!">Activity Log</a></li>
          <li><hr class="dropdown-divider" /></li>
          <li><a class="dropdown-item" :href="logoutUrl">Logout</a></li>
        </ul>
      </li>
    </ul>
  </nav>

  <div id="layoutSidenav">
    <div id="layoutSidenav_nav">
      <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
        <div class="sb-sidenav-menu">
          <div class="nav">
            <div class="sb-sidenav-menu-heading">Core</div>
            <a class="nav-link" :href="dashboardUrl">
              <div class="sb-nav-link-icon">
                <i class="fas fa-tachometer-alt"></i>
              </div>
              Dashboard
            </a>
            <!-- 기타 메뉴 -->
          </div>
        </div>
      </nav>
    </div>
    <div id="layoutSidenav_content">
      <main>
        <div class="container-fluid px-4">
          <h2 class="mt-2">1:1 문의</h2>
          <ol class="breadcrumb mb-2">
            <li class="breadcrumb-item active">작성자 : {{ writer }}</li>
            <li class="breadcrumb-item active">문의번호 : {{ inquiryId }}</li>
          </ol>
          <div class="card mb-1"></div>
          <div style="height: 50vh">
            <div class="second-grid">
              <form
                class="inquiry-form"
                id="answerForm"
                action="/admin/answer"
                method="post"
              >
                <div class="form-title">
                  <div class="inquiry-inline">
                    <p class="inquiry-content-title">문의 제목</p>
                  </div>
                  <input
                    type="text"
                    class="input-title"
                    v-model="title"
                    readonly
                  />
                </div>
                <div class="form-content">
                  <div class="inquiry-inline">
                    <p class="inquiry-content-title">문의 내용</p>
                  </div>
                  <textarea
                    class="input-content"
                    v-model="contents"
                    readonly
                  ></textarea>
                </div>
                <div class="form-content">
                  <div class="inquiry-inline">
                    <p class="inquiry-content-title">문의 답변</p>
                  </div>
                  <textarea
                    class="input-content"
                    name="answerContents"
                    v-model="answer"
                    required
                  ></textarea>
                </div>
                <div class="button-group">
                  <div class="btn-submit" @click="submitForm">등록하기</div>
                  <div class="btn-cancel" @click="cancelForm">취소하기</div>
                </div>
              </form>
            </div>
          </div>
        </div>
      </main>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import api from "@/api/axios";
import { useModalStore } from "@/stores/modalStore";

const dashboardUrl = "/admin/dashboard"; // 대쉬보드 이동
const logoutUrl = "/logout"; // 로그아웃 이동
const router = useRouter();
const route = useRoute(); // 현재 경로 정보를 가져옵니다.
const inquiryId = ref(route.params.inquiryId); // 경로 변수에서 inquiryId를 추출합니다.
const writer = ref("");
const title = ref("");
const contents = ref("");
const answer = ref("");
const answerState = ref("");
// 폼 제출 함수
const submitForm = async () => {
  try {
    // 답변 내용이 비어있는지 확인
    if (!answer.value.trim()) {
      alert("답변 내용을 입력해주세요.");
      return;
    }

    const response = await api.post(
      `/api/admin/inquiries/${inquiryId.value}/answer`,
      {
        answerContents: answer.value,
      }
    );

    if (response.status === 200) {
      //완료 모달창 사용
      const modalStore = useModalStore(); // 스토어를 가져와 사용
      modalStore.showModal("문의 답변", "답변이 등록되었습니다.");
      // 대쉬보드 이동
      // router.push("/admin/dashboard");
    }
  } catch (err) {
    console.error("답변 등록 중 오류 발생:", err);
    alert("답변 등록 중 오류 발생");
  }
};

// 취소 함수
const cancelForm = () => {
  // 대시보드로 이동
  router.push("/admin/dashboard");
};

onMounted(async () => {
  try {
    // inquiryId.value를 사용하여 URL 구성
    const response = await api.get(`/api/admin/inquiries/${inquiryId.value}`);
    console.log("응답", response.data);
    writer.value = response.data.userUuid;
    title.value = response.data.inquiryTitle;
    contents.value = response.data.inquiryContents;
    answerState.value = response.data.answerState;
    if (answerState.value == "Y") {
      getAnswer();
    }
  } catch (err) {
    console.error("에러 발생:", err);
  }
});
// 등록된 문의 답변 가져오기
const getAnswer = async () => {
  try {
    const response = await api.get(
      `/api/admin/inquiries/${inquiryId.value}/answer`
    );
    if (response.status === 200) {
      answer.value = response.data.answerContents;
    }
  } catch (err) {
    console.error("답변 조회 중 오류 발생:", err);
  }
};
</script>

<style scoped>
@import "@/assets/styles/admin.style.css";
.outer-container {
  display: grid;
  margin-top: 20px;
  width: 1100px;
  margin-left: auto;
  margin-right: auto;
  margin-bottom: 100px;
}

.section-inquiries {
  border-bottom: 2px solid #727272;
}

.second-grid {
  width: 40%;
  margin: 0 auto;
}

.section-inquiries p:first-of-type {
  font-size: 24px;
  margin-top: 65px;
  margin-bottom: 0px;
}

.section-inquiries p:nth-of-type(2) {
  font-size: 13px;
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
  margin-top: 0px;
  height: 240px;
}

.inquiry-content-title {
  font-size: 17px;
  margin: 0;
}

.button-group {
  display: flex;
  justify-content: center;
  gap: 7px;
  margin-top: 65px;
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
  cursor: pointer;
}

.input-title {
  background: #fff;
  border: 1px solid #555553;
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
  border: 1px solid #555553;
  padding: 10px;
  font-family: "Inter-Medium", sans-serif;
  font-size: 16px;
  resize: none;
  height: 200px;
  width: 100%;
}

.error-message {
  color: #f44336;
  margin: 0px;
  font-size: 15px;
  margin-left: 15px;
  display: none;
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
</style>
