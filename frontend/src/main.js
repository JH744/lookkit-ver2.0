import "./assets/styles/main.css";
import { createApp } from "vue";
import App from "./App.vue";
import { createPinia } from 'pinia';
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate';
import router from "./router";
import Header from "./components/layouts/Header.vue";
import { createPinia } from "pinia";


const pinia = createPinia();
pinia.use(piniaPluginPersistedstate); 
const app = createApp(App);
const pinia = createPinia();

<<<<<<< HEAD

app.component("Header", Header); //공용 컴포넌트 등록
app.use(pinia);
app.use(router);
app.mount("#app");
=======
//공용 컴포넌트 등록
app.component("Header", Header);

app.use(router).use(pinia).mount("#app");
>>>>>>> 2215db5de9dd88465a06d018876349fc0d275b21
