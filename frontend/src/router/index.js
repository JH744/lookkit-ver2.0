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
    path: "/vali",
    component: () => import("@/views/Admin/Vali.vue"),
  },
  {
    path: "/codi/:codiId",
    component: () => import("@/views/Codi/CodiView.vue"),
  },
  {
    path: "/product/:productId",
    component: () => import("@/views/Product/ProductView.vue"),
  },
  {
    path: "/order",
    component: () => import("@/views/Order/OrderView.vue"),
    children: [
      {
        path: "addaddress",
        component: () => import("@/views/Order/AddAddressView.vue"),
      },
      {
        path: "complete",
        component: () => import("@/views/Order/OrderCompleteView.vue"),
      },
    ],
  },
  {
    path: "/mypage",
    component: () => import("@/views/mypage/Mypage.vue"),
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
        props: true,
      },
      {
        path: "wishlist",
        component: () => import("@/views/mypage/WishList.vue"),
      },
    ],
  },
  {
    path: "/updateInfo",
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
