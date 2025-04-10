import { createRouter, createWebHistory } from "vue-router";

const routes = [
  {
    path: "/",
    component: () => import("@/views/Main/MainView.vue"),
  },
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
        props: (route) => ({ keyword: route.query.keyword }),
      },
      {
        path: "coordi",
        component: () => import("@/views/Main/CoordiView.vue"),
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
    children: [
      {
        path: "dashboard",
        component: () => import("@/views/Admin/AdminDashboard.vue"),
      },
      {
        path: "inquiry/:inquiryId",
        component: () => import("@/views/Admin/InquiryAnswer.vue"),
      },
      {
        path: "order/status",
        component: () => import("@/views/Admin/OrderStatus.vue"),
      },
    ],
  },
  {
    path: "/storage", // 파이어베이스 테스트 경로
    children: [
      {
        path: "upload",
        component: () => import("@/firebase/StorageUploader.vue"),
      },
      {
        path: "dragUpload",
        component: () => import("@/firebase/StorageDragDropUploader.vue"),
      },
      {
        path: "load",
        component: () => import("@/firebase/StorageImageDownloader.vue"),
      },
    ],
  },

  {
    path: "/vali", //임시 뷰 파일
    component: () => import("@/views/Admin/Vali.vue"),
  },
  {
    path: "/codi/:codiId",
    component: () => import("@/views/Codi/CodiView.vue"),
    children: [
      {
        path: "review",
        component: () => import("@/views/Review/ReviewView.vue"),
        props: (route) => ({ codiId: route.params.codiId }),
      },
    ],
  },

  {
    path: "/product/:productId",
    component: () => import("@/views/Product/ProductView.vue"),
    children: [
      {
        path: "review",
        component: () => import("@/views/Review/ReviewView.vue"),
        props: (route) => ({ productId: route.params.productId }),
      },
    ],
  },
  {
    path: "/order",
    component: () => import("@/views/Order/OrderView.vue"),
  },
  {
    path: "/order/orderComplete",
    component: () => import("@/views/Order/OrderCompleteView.vue"),
  },
  {
    path: "/cart",
    component: () => import("@/views/Cart/CartView.vue"),
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
        path: "review/create",
        component: () => import("@/views/Review/ReviewWriteView.vue"),
        props: (route) => ({ productId: route.query.productId }),
      },
      {
        path: "review",
        component: () => import("@/views/Review/ReviewListView.vue"),
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
      {
        path: "enter-password",
        component: () => import("@/components/layouts/PwCheckModal.vue"),
      },
      {
        path: "updateInfo",
        component: () => import("@/views/mypage/UserInfoView.vue"),
      },
    ],
  },
  {
    path: "/:catchAll(.*)",
    component: () => import("@/views/Error/Error404.vue"),
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
