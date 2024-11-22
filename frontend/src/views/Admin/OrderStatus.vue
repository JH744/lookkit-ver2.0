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
      <div id="layoutSidenav_content">
        <main>
          <div class="container-fluid px-4">
            <h1 class="mt-4">Tables</h1>
            <ol class="breadcrumb mb-4">
              <li class="breadcrumb-item">
                <a href="index.html">Dashboard</a>
              </li>
              <li class="breadcrumb-item active">Tables</li>
            </ol>
            <div class="card mb-4">
              <div class="card-body">
                주문현황 조회
                <a target="_blank" href="https://datatables.net/"
                  >-> 상세내역확인</a
                >
                .
              </div>
            </div>
            <div class="card mb-4">
              <div class="card-header">
                <i class="fas fa-table me-1"></i>
                DataTable Example
              </div>
              <div class="card-body">
                <table>
                  <thead>
                    <tr>
                      <th>주문번호</th>
                      <th>주문자ID</th>
                      <th>주문총액</th>
                      <th>주문상태</th>
                      <th>주문날짜</th>
                      <th>주문요청사항</th>
                      <th>작업</th>
                    </tr>
                  </thead>
                  <tfoot>
                    <tr>
                      <th>Name</th>
                      <th>Position</th>
                      <th>Office</th>
                      <th>Age</th>
                      <th>Start date</th>
                      <th>Salary</th>
                    </tr>
                  </tfoot>
                  <tbody>
                    <tr th:each="list:${orderList}">
                      <td th:text="${list.orderId}">주문번호</td>
                      <td th:text="${list.userUuid}">주문자ID</td>
                      <td th:text="${list.totalAmount}">주문총액</td>
                      <td>
                        <select>
                          <option
                            value="pending"
                            th:selected="${list.orderStatus == 'pending'}"
                          >
                            대기
                          </option>
                          <option
                            value="shipped"
                            th:selected="${list.orderStatus == 'shipped'}"
                          >
                            배송 중
                          </option>
                          <option
                            value="delivered"
                            th:selected="${list.orderStatus == 'delivered'}"
                          >
                            완료
                          </option>
                          <option
                            value="completed"
                            th:selected="${list.orderStatus == 'completed'}"
                          >
                            취소됨
                          </option>
                        </select>
                      </td>
                      <td th:text="${list.orderDate}">2024/11/04</td>
                      <td th:text="${list.orderComment}">주문요청사항</td>
                      <td>
                        <button
                          type="button"
                          class="btn btn-primary"
                          onclick="updateOrderStatus(event)"
                        >
                          변경
                        </button>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </main>
        <footer class="py-4 bg-light mt-auto">
          <div class="container-fluid px-4">
            <div
              class="d-flex align-items-center justify-content-between small"
            >
              <div class="text-muted">Copyright &copy; Your Website 2023</div>
              <div>
                <a href="#">Privacy Policy</a>
                &middot;
                <a href="#">Terms &amp; Conditions</a>
              </div>
            </div>
          </div>
        </footer>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import axios from "axios";
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
const fetchOrderStatus = async () => {
  try {
    const { data } = await axios.get(
      "http://localhost:8081/api/admin/orderStatus"
    );
    console.log("data", data);
  } catch (err) {
    console.error("주문목록 가져오는 중 오류 발생:", err);
  }
};

onMounted(() => {
  fetchOrderStatus();
});
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
