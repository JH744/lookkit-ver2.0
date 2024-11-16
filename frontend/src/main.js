import "./assets/styles/main.css";
import { createApp } from "vue";
import App from "./App.vue";
import { createPinia } from 'pinia';
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate';
import router from "./router";
import Header from "./components/layouts/Header.vue";


const pinia = createPinia();
pinia.use(piniaPluginPersistedstate); 
const app = createApp(App);


app.component("Header", Header); //공용 컴포넌트 등록
app.use(pinia);
app.use(router);
app.mount("#app");
