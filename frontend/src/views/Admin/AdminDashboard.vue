<template>
  <nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
    <!-- Navbar Brand-->
    <a class="navbar-brand ps-3" th:href="@{/admin/dashboard}">ADMIN PAGE</a>

    <button
      class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0"
      id="sidebarToggle"
      href="#!"
    >
      <i class="fas fa-bars"></i>
    </button>
    <!-- Navbar Search-->
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
    <!-- Navbar-->
    <ul class="navbar-nav ms-auto ms-md-0 me-3 me-lg-4">
      <li class="nav-item dropdown">
        <a
          class="nav-link dropdown-toggle"
          id="navbarDropdown"
          href="#"
          role="button"
          data-bs-toggle="dropdown"
          aria-expanded="false"
          ><i class="fas fa-user fa-fw"></i
        ></a>
      </li>
    </ul>
  </nav>
  <div id="layoutSidenav">
    <div id="layoutSidenav_nav">
      <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
        <div class="sb-sidenav-menu">
          <div class="nav">
            <div class="sb-sidenav-menu-heading">Menu</div>

            <router-link class="nav-link" to="/admin/order/status">
              <div class="sb-nav-link-icon"><i class="fas fa-table"></i></div>
              주문현황
            </router-link>
          </div>
        </div>
        <div class="sb-sidenav-footer">
          <div class="small">관리자로 로그인됨</div>
          <button
            class="btn btn-outline-light btn-sm mt-2 w-100"
            @click="handleLogout"
          >
            로그아웃
          </button>
        </div>
      </nav>
    </div>
    <div id="layoutSidenav_content">
      <main>
        <div class="container-fluid px-4">
          <h1 class="mt-4">Dashboard</h1>
          <ol class="breadcrumb mb-4">
            <li class="breadcrumb-item active">Dashboard</li>
          </ol>
          <!-- <div class="row">
            <div class="col-xl-3 col-md-6">
              <div class="card bg-primary text-white mb-4">
                <div class="card-body">Primary Card</div>
                <div
                  class="card-footer d-flex align-items-center justify-content-between"
                >
                  <a class="small text-white stretched-link" href="#"
                    >View Details</a
                  >
                  <div class="small text-white">
                    <i class="fas fa-angle-right"></i>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-xl-3 col-md-6">
              <div class="card bg-warning text-white mb-4">
                <div class="card-body">Warning Card</div>
                <div
                  class="card-footer d-flex align-items-center justify-content-between"
                >
                  <a class="small text-white stretched-link" href="#"
                    >View Details</a
                  >
                  <div class="small text-white">
                    <i class="fas fa-angle-right"></i>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-xl-3 col-md-6">
              <div class="card bg-success text-white mb-4">
                <div class="card-body">Success Card</div>
                <div
                  class="card-footer d-flex align-items-center justify-content-between"
                >
                  <a class="small text-white stretched-link" href="#"
                    >View Details</a
                  >
                  <div class="small text-white">
                    <i class="fas fa-angle-right"></i>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-xl-3 col-md-6">
              <div class="card bg-danger text-white mb-4">
                <div class="card-body">Danger Card</div>
                <div
                  class="card-footer d-flex align-items-center justify-content-between"
                >
                  <a class="small text-white stretched-link" href="#"
                    >View Details</a
                  >
                  <div class="small text-white">
                    <i class="fas fa-angle-right"></i>
                  </div>
                </div>
              </div>
            </div>
          </div> -->
          <div class="row">
            <div class="col-xl-6">
              <div class="card mb-4">
                <div class="card-header">
                  <i class="fas fa-chart-area me-1"></i>
                  월 매출액
                </div>
                <div class="card-body">
                  <canvas id="myAreaChart" width="100%" height="40"></canvas>
                </div>
              </div>
            </div>
            <div class="col-xl-6">
              <div class="card mb-4">
                <div class="card-header">
                  <i class="fas fa-chart-bar me-1"></i>
                  월 누적이익
                </div>
                <div class="card-body">
                  <canvas id="myBarChart" width="100%" height="40"></canvas>
                </div>
              </div>
            </div>
          </div>
          <div class="card mb-4">
            <div class="card-header">
              <i class="fas fa-table me-1"></i>
              1:1 문의현황
            </div>
            <div class="card-body">
              <table class="table table-striped">
                <thead>
                  <tr>
                    <th>문의번호</th>
                    <th>작성자ID</th>
                    <th>문의제목</th>
                    <th>문의내용</th>
                    <th>문의날짜</th>
                    <th>답변여부</th>
                    <th>삭제</th>
                  </tr>
                </thead>
                <tbody>
                  <tr
                    v-for="inquiry in paginatedInquiries"
                    :key="inquiry.inquiryId"
                  >
                    <td>
                      {{ inquiry.inquiryId }}
                    </td>
                    <td>{{ inquiry.userUuid }}</td>
                    <td>
                      <RouterLink :to="`/admin/inquiry/${inquiry.inquiryId}`">
                        {{ inquiry.inquiryTitle }}
                      </RouterLink>
                    </td>
                    <td>
                      <RouterLink :to="`/admin/inquiry/${inquiry.inquiryId}`">
                        {{ inquiry.inquiryContents }}
                      </RouterLink>
                    </td>
                    <td>
                      {{
                        new Date(inquiry.inquiryCreatedAt).toLocaleDateString(
                          "ko-KR"
                        )
                      }}
                    </td>
                    <td>{{ inquiry.answerState }}</td>
                    <td>삭제</td>
                  </tr>
                </tbody>
              </table>

              <!-- 페이지네이션 컴포넌트 추가 -->
              <nav aria-label="Page navigation" class="mt-4">
                <ul class="pagination justify-content-center">
                  <li
                    class="page-item"
                    :class="{ disabled: currentPage === 1 }"
                  >
                    <a
                      class="page-link"
                      href="#"
                      @click.prevent="changePage(currentPage - 1)"
                    >
                      이전
                    </a>
                  </li>
                  <li
                    v-for="page in totalPages"
                    :key="page"
                    class="page-item"
                    :class="{ active: currentPage === page }"
                  >
                    <a
                      class="page-link"
                      href="#"
                      @click.prevent="changePage(page)"
                    >
                      {{ page }}
                    </a>
                  </li>
                  <li
                    class="page-item"
                    :class="{ disabled: currentPage === totalPages }"
                  >
                    <a
                      class="page-link"
                      href="#"
                      @click.prevent="changePage(currentPage + 1)"
                    >
                      다음
                    </a>
                  </li>
                </ul>
              </nav>
            </div>
          </div>
        </div>
      </main>
      <footer class="py-4 bg-light mt-auto">
        <div class="container-fluid px-4">
          <div class="d-flex align-items-center justify-content-between small">
            <div class="text-muted">Copyright &copy; lookkit</div>
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
</template>

<script setup>
import axios from "axios";
import { onMounted, ref, computed, watchEffect } from "vue";
import { useRouter } from "vue-router";
import { useAuthStore } from "@/stores/authStore";

// 스크립트 로드 상태 ��리
const scriptsLoaded = ref(false);

// Chart.js 인스턴스 저장
const areaChart = ref(null);
const barChart = ref(null);

// DataTable 인스턴스 저장
const dataTable = ref(null);

const inquiryList = ref([]);
const currentPage = ref(1);
const itemsPerPage = ref(10);
const totalPages = ref(0);

// 페이지네이션된 데이터 계산
const paginatedInquiries = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage.value;
  const end = start + itemsPerPage.value;
  return inquiryList.value.slice(start, end);
});

// 총 페이지 수 계산
watchEffect(() => {
  totalPages.value = Math.ceil(inquiryList.value.length / itemsPerPage.value);
});

// 페이지 변경 함수
const changePage = (page) => {
  currentPage.value = page;
};

const router = useRouter();
const authStore = useAuthStore();

// 로그아웃 처리 함수 추가
const handleLogout = async () => {
  try {
    await axios.post("http://localhost:8081/api/auth/logout");
    authStore.clearAuthData(); // 로컬 인증 정보 삭제
    router.push("/auth/login"); // 로그인 페이지로 리다이렉트
  } catch (error) {
    console.error("로그아웃 처리 중 오류 발생:", error);
  }
};

onMounted(async () => {
  try {
    // Bootstrap 로드
    await loadScript(
      "https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
    );

    // Chart.js 로드 및 초기화
    await loadScript(
      "https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js"
    );
    initializeCharts();

    // DataTables 로드 및 초기화
    await loadScript(
      "https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js"
    );
    initializeDataTable();

    scriptsLoaded.value = true;
  } catch (error) {
    console.error("스크립트 로드 중 에러 발생:", error);
  }

  try {
    const response = await axios
      .get("http://localhost:8081/api/admin/dashboard")
      .then((res) => {
        console.log(res.data);
        inquiryList.value = res.data;
        console.log("리스트", inquiryList.value[0]);
      });
  } catch (err) {
    console.log(err);
  }
});

// 스크립트 로드 함수
const loadScript = (src) => {
  return new Promise((resolve, reject) => {
    // 이미 로드된 스크립트인지 확인
    if (document.querySelector(`script[src="${src}"]`)) {
      resolve();
      return;
    }

    const script = document.createElement("script");
    script.src = src;
    script.crossOrigin = "anonymous";
    script.onload = resolve;
    script.onerror = reject;
    document.head.appendChild(script);
  });
};

// Chart 초기화 함수
const initializeCharts = () => {
  // Area Chart 초기화
  const areaChartCanvas = document.getElementById("myAreaChart");
  if (areaChartCanvas) {
    areaChart.value = new Chart(areaChartCanvas, {
      type: "line",
      data: {
        labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul"],
        datasets: [
          {
            label: "Sessions",
            data: [10000, 30162, 26263, 18394, 18287, 28682, 31274],
            fill: true,
            borderColor: "rgba(2,117,216,1)",
            backgroundColor: "rgba(2,117,216,0.2)",
          },
        ],
      },
      options: {
        responsive: true,
        scales: {
          y: {
            beginAtZero: true,
          },
        },
      },
    });
  }

  // Bar Chart 초기화
  const barChartCanvas = document.getElementById("myBarChart");
  if (barChartCanvas) {
    barChart.value = new Chart(barChartCanvas, {
      type: "bar",
      data: {
        labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun"],
        datasets: [
          {
            label: "Revenue",
            data: [4215, 5312, 6251, 7841, 9821, 14984],
            backgroundColor: "rgba(2,117,216,1)",
          },
        ],
      },
      options: {
        responsive: true,
        scales: {
          y: {
            beginAtZero: true,
          },
        },
      },
    });
  }
};

// DataTable 초기화 함수
const initializeDataTable = () => {
  const datatable = document.getElementById("datatablesSimple");
  if (datatable) {
    dataTable.value = new simpleDatatables.DataTable(datatable);
  }
};
</script>

<style scoped>
@import "@/assets/styles/admin.style.css";

/* RouterLink 텍스트 스타일링 */
.router-link-active {
  color: #0d1134;
  text-decoration: none;
}

.router-link-exact-active {
  color: #0d1134;
  font-weight: bold;
}

a {
  color: #333;
  text-decoration: underline;
}

a:hover {
  color: #0d1134;
  text-decoration: underline;
}

.pagination {
  margin-bottom: 0;
}

.page-link {
  cursor: pointer;
}

.page-item.disabled .page-link {
  cursor: not-allowed;
}

.table th:nth-child(3) {
  width: 150px;
}
.table th:nth-child(4) {
  width: 40%;
}

th {
  white-space: nowrap; /* 텍스트 줄바꿈 방지 */
  overflow: hidden; /* 넘친 내용 숨기기 */
  text-overflow: ellipsis; /* 생략 부호(...) 표시 */
}
</style>
