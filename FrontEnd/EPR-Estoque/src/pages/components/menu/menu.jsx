// Import Bibliotecas
import React from "react";
import { Link, useLocation } from "react-router-dom"; // Importa o Link para navegação

// Import css
import "./menu.css"; // Importa o css do componente Menu
import "../../../reset.css"; // Importa o css de reset
import "../../../global.css"; // Importa o css padrão do projeto

// Import Assets
import logo from "../../../assets/logo/logo.png"; // Importa a logo do projeto

// Import Componentes


function Menu() {
    // Definindo constante
    const location = useLocation(); // Define a constante location



    return (
        <>
            <header>
                <div className="logo">
                    <Link to="/">
                        <img src={logo} alt="Logo" />
                    </Link>
                </div>
                <nav>
                    <ul>
                        <li className={location.pathname === "/" ? "active" : ""}>
                                <Link to="/">Home</Link>
                        </li>
                        <li className={location.pathname === "/catalogo" ? "active" : ""}>
                            <Link to="/catalogo">Catálogo</Link>
                        </li>
                        <li className={location.pathname === "/fornecedores" ? "active" : ""}>
                            <Link to="#">Fornecedores</Link>
                        </li>
                        <li className={location.pathname === "/relatorios" ? "active" : "" }>
                            <Link to="#">Relatórios</Link>
                        </li >
                    </ul >
                </nav >
            </header >
        </>
    );

}

export default Menu; // Exporta o componente Menu