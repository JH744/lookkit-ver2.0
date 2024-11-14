import "./assets/styles/main.css";
import { createApp } from "vue";
import router from "./router";
import App from "./App.vue";
import Header from "./components/layouts/Header.vue";

const app = createApp(App);

//공용 컴포넌트 등록
app.component("Header", Header);

app.use(router).mount("#app");
