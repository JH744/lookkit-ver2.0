// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "AIzaSyB70VNYrexrv613Wfa1ZwU4ovet_9zI3OQ",
  authDomain: "lookkit-ver2.firebaseapp.com",
  projectId: "lookkit-ver2",
  storageBucket: "lookkit-ver2.firebasestorage.app",
  messagingSenderId: "633304919159",
  appId: "1:633304919159:web:afa2caf470bdbf119baa72",
  measurementId: "G-P2293NMVWW",
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const analytics = getAnalytics(app);
