// Import Bibliotecas
import React from "react";

// Import css
import "./menu.css"; // Importa o css do componente Menu
import "../../../reset.css"; // Importa o css de reset
import "../../../global.css"; // Importa o css padrão do projeto

// Import Assets
import logo from "../../../assets/logo/logo.png"; // Importa a logo do projeto

// Import Componentes


function Menu() {
    return (
        <>
            <header>
                <div className="logo">
                    <a href="#">
                        <img src={logo} alt="Logo" />
                    </a>
                </div>
                <nav>
                    <ul>
                        <li><a href="#">Home</a></li>
                        <li><a href="#">Produtos</a></li>
                        <li><a href="#">Fornecedores</a></li>
                        <li><a href="#">Categorias</a></li>
                        <li><a href="#">Usuários</a></li>
                        <li><a href="#">Relatórios</a></li>
                    </ul>
                </nav>
            </header>
        </>
    );

}

export default Menu; // Exporta o componente Menu