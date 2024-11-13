import "./assets/styles/main.css";
import { createApp } from "vue";
import { createPinia } from 'pinia';
import router from "./router";
import App from "./App.vue";
import Header from "./components/layouts/Header.vue";

const app = createApp(App);

const pinia = createPinia();

app.component("Header", Header); //공용 컴포넌트 등록
app.use(pinia);
app.use(router).mount("#app");
