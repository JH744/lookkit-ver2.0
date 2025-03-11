<template>
  <div class="header">
    <div class="headerWrapper">
      <div class="navBox1">
        <router-link to="/main">
          <img class="logo" src="/src/assets/logos/Logo1.png" />
        </router-link>
        <ul class="navMenu">
          <li>
            <router-link to="/main/category?type=all" class="menu"
              >ALL</router-link
            >
          </li>
          <li>
            <router-link to="/main/category?type=outer" class="menu"
              >OUTER</router-link
            >
          </li>
          <li>
            <router-link to="/main/category?type=top" class="menu"
              >TOP</router-link
            >
          </li>
          <li>
            <router-link to="/main/category?type=bottom" class="menu"
              >BOTTOM</router-link
            >
          </li>
          <li>
            <router-link to="/main/category?type=bag" class="menu"
              >BAG</router-link
            >
          </li>
          <li>
            <router-link to="/main/category?type=shoes" class="menu"
              >SHOES</router-link
            >
          </li>
          <li>
            <router-link to="/main/category?type=etc" class="menu"
              >ETC</router-link
            >
          </li>
        </ul>
      </div>
      <div class="navBox2">
        <div class="searchBox">
          <input
            id="searchInputEl"
            class="searchInput"
            v-model="inputKeyword"
          />
          <span class="searchBtn" @click="goToSearch">
            <img src="@/assets/icons/search.png" />
          </span>
        </div>

        <div class="navIcon">
          <!-- 비로그인시 로그인버튼 노출-->
          <div id="LoginBtn" class="iconBtn" v-if="!authStore.isLoggedIn">
            <router-link to="/auth/login" class="btn-a">
              <img
                src="@/assets/icons/login.png"
                class="simptip-position-top simptip-success"
                data-tooltip="Login"
                width="26px"
              />
              <p class="btn-text">LOG IN</p>
            </router-link>
          </div>
          <!-- 로그인시 로그아웃버튼 노출-->
          <div id="LogoutBtn" class="iconBtn" v-if="authStore.isLoggedIn">
            <div class="btn-a logout-btn" @click="logout">
              <img
                src="@/assets/icons/logout.png"
                class="simptip-position-top"
                width="26px"
              />
              <p class="btn-text">LOG OUT</p>
            </div>
          </div>
          <div class="iconBtn">
            <router-link to="/mypage/manage" class="btn-a">
              <img
                src="@/assets/icons/user.png"
                class="simptip-position-top"
                data-tooltip="My Page"
                width="26px"
              />
              <p class="btn-text">MY PAGE</p>
            </router-link>
          </div>
          <div class="iconBtn">
            <router-link to="/cart" class="btn-a">
              <img
                src="@/assets/icons/cart.png"
                class="simptip-position-top"
                data-tooltip="Cart"
              />
              <p class="btn-text">CART</p>
            </router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";
import { useRouter } from "vue-router";
import { useAuthStore } from "@/stores/authStore";
import axios from "@/api/axios";

const authStore = useAuthStore();
const router = useRouter();
const inputKeyword = ref("");

const goToSearch = () => {
  let keyword = inputKeyword.value;
  inputKeyword.value = "";
  router.push(`/main/search?keyword=${keyword}`);
};

const logout = async () => {
  try {
    //로그아웃-서버
    await axios.post("/api/auth/logout", null, {
      withCredentials: true, // 쿠키 전송 허용
    });

    //로그아웃-프론트
    // document.cookie = "jwt=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";프론트단에서 세션 무효화
    authStore.clearAuthData();
    localStorage.removeItem("auth");
    // 로그아웃 후 리다이렉트
    router.push("/auth/login");
  } catch (error) {
    console.error("로그아웃 실패:", error);
  }
};
</script>

<style scoped>
/** <초기화 코드/>   **/

.header,
.header * {
  box-sizing: border-box;
  /* width: 100%; */
}
.header {
  height: 95px;
}
.headerWrapper {
  background: #ffffff;
  width: 100%;
  height: 95px;

  display: flex;
  padding: 0 35px;
  justify-content: space-between;
  align-items: center;
}
.navBox1 {
  display: flex;
  gap: 65px;
  align-items: center;
  flex-basis: 60%; /* 헤더의 60% 차지 */
}

.navBox1-menu {
  width: fit-content;
}

.navBox1-logo {
  min-width: fit-content;
  max-width: fit-content;
}

.navMenu {
  font-family: "InstrumentSans-Bold", sans-serif;
  display: flex;
  text-align: center;
  gap: 50px;
  max-width: fit-content;
}

.logo {
  /* color: #232b6e;
  font-size: 28px;
  font-weight: 700;
  min-width: fit-content;
  max-width: fit-content; */
  width: 160px;
}
.menu {
  color: #000000;
  font-weight: 500;
}

.navBox2 {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  flex-basis: 40%;
  gap: 20px;
}
.searchBox {
  display: flex;
  position: relative;
  align-items: center;
  width: 250px;
  height: 35px;
}

.searchInput {
  width: 250px;
  height: 30px;
  border-radius: 10px;
  padding-left: 15px;
  border: 1px solid #a9a9a9;
}

.searchBtn {
  position: relative;
  width: 20px;
  height: 20px;
  left: -30px;
  top: 1px;
  cursor: pointer;
}

.navIcon {
  display: flex;
  align-items: center;
  text-align: center;
  gap: 20px;
  width: fit-content;
}

#LogoutBtn {
  cursor: pointer;
}

.iconBtn img {
  width: 15px;
}

.btn-text {
  font-size: 11px;
}

.btn-a {
  display: grid;
  justify-items: center;
  gap: 6px;
  width: max-content;
}

.navMenu a:hover {
  border-bottom: 3px solid rgb(45, 43, 43);
}
</style>
