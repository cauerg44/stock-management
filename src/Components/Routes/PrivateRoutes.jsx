import { useContext } from "react";
import { AuthGoogleContext } from "../../Contexts/AuthGoogle";
import { Navigate, Outlet } from "react-router-dom";




export const PrivateRoutes = () => {
    const { signed } = useContext(AuthGoogleContext); // Create a constant called signed

    return (
        signed ? <Outlet /> : <Navigate to="/" />
    ); 
}