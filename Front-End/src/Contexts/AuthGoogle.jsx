import { createContext, useEffect, useState } from "react";
import { GoogleAuthProvider, getAuth, signInWithPopup} from "firebase/auth";
import { app } from "../Server/FirebaseConfig"

const provider = new GoogleAuthProvider();  // Create a constant called provider

export const AuthGoogleContext = createContext({}); // Create a constant called AuthGoogleContext

export const AuthGoogleProvider = ({ children }) => {
    const auth = getAuth(app); // Create a constant called auth
    const [user, setUser] = useState(null); // Estado inicial do usuário
    const [loading, setLoading] = useState(false); // Estado inicial do loading

    useEffect(() => {
        // Subscreve-se para observar alterações no estado de autenticação do Firebase
        const unsubscribe = auth.onAuthStateChanged((user) => {
            // Quando ocorre uma mudança no estado de autenticação do Firebase
            if (user) { // Se houver um usuário autenticado
                // Define o usuário no estado local
                setUser(user);
                // Armazena os detalhes do usuário autenticado no sessionStorage para persistência
                sessionStorage.setItem("@AuthFirebase:user", JSON.stringify({ uid: user.uid, email: user.email, photoURL: user.photoURL, displayName: user.displayName })); // Salva o usuário no sessionStorage
            } else { // Se não houver usuário autenticado
                // Define o estado local do usuário como nulo
                setUser(null);
                // Remove os detalhes do usuário armazenados no sessionStorage
                sessionStorage.removeItem("@AuthFirebase:user"); // Remove o usuário do sessionStorage
            }
        });
        // Retorna uma função de limpeza para desinscrever o observador quando o componente é desmontado
        return () => unsubscribe(); // Função de limpeza
    }, [auth]); // Dependência do useEffect: será reexecutado sempre que a referência 'auth' mudar


    useEffect(() => {
        const loadStorage = () => {
            const sessionToken = sessionStorage.getItem("@AuthFirebase:token"); // Recuperar o token do sessionStorage
            const sessionUser = sessionStorage.getItem("@AuthFirebase:user"); // Recuperar o usuário do sessionStorage
            if (sessionToken && sessionUser) {
                setUser(sessionUser !== "undefined" ? JSON.parse(sessionUser) : null); // Salvar o usuário no estado
            }
        }
        loadStorage(); // Carregar o estado do usuário
    }, []); // Array de dependências vazio: executa o useEffect apenas uma vez

    const signInGoogle = async () => {
        setLoading(false); // Desativa loading
        try {
            const result = await signInWithPopup(auth, provider); // Autenticar com o Google
            const credential = GoogleAuthProvider.credentialFromResult(result); // Recuperar credenciais
            const token = credential.accessToken; // Recuperar token
            const user = result.user; // Recuperar usuário

            setUser(user); // Salva o usuário no estado
            sessionStorage.setItem("@AuthFirebase:token", token); // Salva o token no sessionStorage
            sessionStorage.setItem("@AuthFirebase:user", JSON.stringify({ uid: user.uid, email: user.email, photoURL: user.photoURL, displayName: user.displayName })); // Salva o usuário no sessionStorage
            setLoading(false); // Desativar loading
            return user; // Retornar o usuário autenticado

        } catch (error) {
            console.error("Erro ao fazer login com Google: ", error);
            throw error; // Propagar o erro
            // showAlert("Erro ao tentar login com Google: " + error.message);
        } finally {
            setLoading(false); // Desativar loading
        }
    };

   
    return ( // Início do componente Provider do contexto AuthGoogleContext
        <AuthGoogleContext.Provider
            value={{ signInGoogle, user, loading }}>
            {children}
        </AuthGoogleContext.Provider>
    ); // Fim do componente Provider do contexto AuthGoogleContext

};