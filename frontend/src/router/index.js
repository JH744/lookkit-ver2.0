import { createRouter, createWebHistory } from "vue-router";

const routes = [
  {
    path: "/main",
    children: [
      {
        path: "",
        component: () => import("@/views/Main/MainView.vue"),
      },
      {
        path: "category",
        component: () => import("@/views/Main/CategoryView.vue"),
      },
      {
        path: "search",
        component: () => import("@/views/Main/SearchView.vue"),
      },
    ],
  },
  {
    path: "/auth",
    children: [
      {
        path: "login",
        component: () => import("@/views/Auth/LoginView.vue"),
      },
      {
        path: "signup",
        component: () => import("@/views/Auth/SignUpView.vue"),
      },
      {
        path: "findId",
        component: () => import("@/views/Auth/FindIdView.vue"),
      },
      {
        path: "findPwd",
        component: () => import("@/views/Auth/FindPwdView.vue"),
      },
    ],
  },
  {
    path: "/admin",
    component: () => import("@/views/Admin/AdminDashboard.vue"),
  },
  {
<<<<<<< HEAD
    path: "/updateInfo",
    component: () => import("@/views/Mypage/UserInfoView.vue"),
=======
    path: "/userInfo",
    component: () => import("@/views/mypage/UserInfoView.vue"),
  },
  {
    path: "/vali",
    component: () => import("@/views/Admin/Vali.vue"),
>>>>>>> 650cad9da8f4d0be70d37ae49ebec7799a12af01
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
