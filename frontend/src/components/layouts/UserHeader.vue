<template>
    <div class="welcome-header">
        <span class="welcome-message">{{ username }}님 환영합니다</span>
        <div class="profile-options">
        <a href="@{/mypage/enter-password}">
            <img class="icon-settings" src="/images/setting.png" alt="설정 아이콘"/>
            <span>개인정보 변경</span>
        </a>
        </div>
    </div>
</template>

<script setup>
import { useAuthStore } from '@/stores/authStore';
import { computed } from 'vue';

const authStore = useAuthStore();
const username = computed(() => authStore.username);
// const isAuthenticated = computed(() => authStore.isAuthenticated);
// const authorities = computed(() => authStore.authorities);
</script>

<style scoped>
.welcome-header {
    padding: 18px;
    background-color: #ffffff;
    display: flex;
    justify-content: space-between;
    align-items: center;
    border: 1px solid #555353;
    color: #555353;
}

.welcome-message {
    font-size: 20px;
    color: #555;
    margin-left: 15px;
}

.profile-options {
    display: inline-grid;
    justify-items: center;
    align-items: center;
    gap: 8px;
}

.profile-options span {
    font-size: 13px;
}

.profile-options:hover {
    color: #000;
}

.icon-settings {
    width: 25px;
    height: 25px;
}

.profile-options a {
    display: grid;
    justify-items: center;
    gap: 7px;
}
</style>