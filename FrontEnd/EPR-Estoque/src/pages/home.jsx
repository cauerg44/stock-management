// Import Bibliotecas
import React, { useEffect, useState } from 'react';

// Import css
import "../style/home.css";

// Import Componentes
import Header from "../components/header.jsx";
import ProductList from "../components/produtolList.jsx";
import GetUserData from "../hooks/getUserData.jsx";


const Home = () => {
    const [userData, setUserData] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const data = await GetUserData();
                setUserData(data);
            } catch (error) {
                console.error("Erro ao obter dados do usuário:", error);
            }
        };

        fetchData();
    }, []);

    console.log("User Data:", userData);
    return (
        <>
            <Header />
            <section className='home'>
                <ProductList />
            </section>

            {userData && (
                <section className='user-info'>
                    <h2>Bem-vindo, {userData.name}</h2>
                    <p>Email: {userData.email}</p>
                </section>
            )}

        </>
    );
}

export default Home; 
