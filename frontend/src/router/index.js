import MainView from "@/views/MainView.vue";
import { createRouter, createWebHistory } from "vue-router";

const routes = [
  { path: "/main", component: MainView },
  //   { path: "/about", component: About },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
