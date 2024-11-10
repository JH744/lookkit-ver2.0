import "./assets/styles/main.css";
import { createApp } from "vue";
import router from "./router";
import App from "./App.vue";
import Header from "./components/layouts/Header.vue";

const app = createApp(App);

app.component("Header", Header); //공용 컴포넌트 등록

app.use(router).mount("#app");
