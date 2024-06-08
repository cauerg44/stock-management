// Import as funçoes SDK nescessarias para o funcionamento do Firebase
import { initializeApp } from "firebase/app";
import { getAuth } from "firebase/auth";

// Configuração do Firebase
const firebaseConfig = {
    apiKey: "AIzaSyBPENGKvGLhFaYM6z7nupg2fpzZfjxHIPk",
    authDomain: "nftcollection-3f297.firebaseapp.com",
    projectId: "nftcollection-3f297",
    storageBucket: "nftcollection-3f297.appspot.com",
    messagingSenderId: "790537634085",
    appId: "1:790537634085:web:2e14bc4da68aac9c7b9acc",
    measurementId: "G-Y1GVL6Q5DX"
};

// Initialize Firebase
export const app = initializeApp(firebaseConfig);
export const auth = getAuth(app);