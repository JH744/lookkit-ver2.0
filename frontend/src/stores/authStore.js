import { defineStore } from "pinia";

export const useAuthStore = defineStore("auth", {
  state: () => ({
    token: null, // JWT 토큰
    user: null, // 사용자 정보
  }),
  actions: {
    setAuthData(token, user) {
      this.token = token;
      this.user = user;
    },
    clearAuthData() {
      this.token = null;
      this.user = null;
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
