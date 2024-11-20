import { defineStore } from "pinia";

export const useAuthStore = defineStore("auth", {
  state: () => ({
    userId: null,
    username: null,
    authorities: null,
  }),
  actions: {
    setAuthData(decodedToken) {
      this.userId = decodedToken.userId;
      this.username = decodedToken.username;
      this.authorities = decodedToken.authorities;
    },
    clearAuthData() {
      this.userId = null;
      this.username = null;
      this.authorities = null;
    },
  },
  persist: {
    enabled: true,
    strategies: [
      {
        key: "auth",
        storage: localStorage,
      },
    ],
  },
});
