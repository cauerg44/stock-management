// Import Bibliotecas
import React from 'react';

// Import css
import "../style/home.css";

// Import Componentes
import Header from "../components/header.jsx";
import ProductList from "../components/produtolList.jsx";


const Home = () => {
    return (
        <>
            <Header />
            <section className='home'>
                <ProductList />
            </section>
        </>
    );
}

export default Home; 
