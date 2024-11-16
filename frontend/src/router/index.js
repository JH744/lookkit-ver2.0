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
<<<<<<< HEAD

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
    path: "/cart",
    component: () => import("@/views/Cart/CartView.vue"),

    // path: "/updateInfo",
    // component: () => import("@/views/Mypage/UserInfoView.vue"),
=======
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
>>>>>>> 2215db5de9dd88465a06d018876349fc0d275b21
  },
  {
    path: "/vali",
    component: () => import("@/views/Admin/Vali.vue"),
<<<<<<< HEAD

=======
>>>>>>> 2215db5de9dd88465a06d018876349fc0d275b21
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
