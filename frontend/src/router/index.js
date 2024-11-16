import { createRouter, createWebHistory } from "vue-router";
import Mypage from "@/views/mypage/Mypage.vue";

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
    path: "/mypage",
    component: Mypage,
    children: [
      {
        path: "manage",
        component: () => import("@/views/mypage/OrderManagement.vue"),
      },
      {
        path: "inquiry",
        component: () => import("@/views/mypage/inquiry/UserInquiryList.vue"),
      },
      {
        path: "inquiry/create",
        component: () => import("@/views/mypage/inquiry/InquiryForm.vue"),
      },
      {
        path: "inquiry/:inquiryId",
        name: "InquiryDetail",
        component: () => import("@/views/mypage/inquiry/InquiryDetail.vue"),
        props: true, // URL의 파라미터를 컴포넌트의 props로 전달할 수 있게 함
      },
      {
        path: "wishlist",
        component: () => import("@/views/mypage/WishList.vue"),
      },
    ],
    path: "/userInfo",
    component: () => import("@/views/mypage/UserInfoView.vue"),
  },
  {
    path: "/vali",
    component: () => import("@/views/Admin/Vali.vue"),
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
