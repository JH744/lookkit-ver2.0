import "./assets/styles/main.css";
import { createApp } from "vue";
import router from "./router";
import App from "./App.vue";
import Header from "./components/layouts/Header.vue";
import { createPinia } from "pinia";

const app = createApp(App);
const pinia = createPinia();

//공용 컴포넌트 등록
app.component("Header", Header);

app.use(router).use(pinia).mount("#app");
