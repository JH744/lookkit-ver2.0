import { defineStore } from "pinia";

export const useAuthStore = defineStore("auth", {
  state: () => ({
    token: null, // JWT 토큰
    user: null, // 사용자 정보
  }),
  getters: {
    // 사용자 로그인 확인 용도
    isLoggedIn: (state) => !!state.token,
  },
  actions: {
    setAuthData(token, user) {
      this.token = token;
      this.user = user;
    },
    clearAuthData() {
      this.token = null;
      this.user = null;
    },
    // 인증 헤더 가져오기 (API 요청 시 사용-- 향후 사용해야함)
    getAuthHeader() {
      if (!this.token) {
        throw new Error("인증 토큰이 없습니다.");
      }
      return { Authorization: `Bearer ${this.token}` };
    },
  },
  persist: {
    enabled: true,
    strategies: [
      {
        key: "auth",
        storage: localStorage, // 로컬 스토리지에 저장
      },
    ],
  },
});
