import { defineStore } from "pinia";

export const useAuthStore = defineStore("auth", {
  state: () => ({
    token: null, // JWT 토큰
    user: null, // 사용자 정보
  }),
});
