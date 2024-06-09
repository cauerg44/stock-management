import { useContext } from "react";
import { AuthGoogleContext } from "../../Contexts/AuthGoogle";
import { Navigate, Outlet } from "react-router-dom";




export const PrivateRoutes = () => { // Create a function called PrivateRoutes
    const { signed } = useContext(AuthGoogleContext); // Create a constant called signed

    return (
        signed ? <Outlet /> : <Navigate to="/" />
    );  // O operador ternário verifica se o usuário está logado, se sim, ele retorna o Outlet, caso contrário, ele redireciona para a página inicial 
}