// Import Bibliotecas
import React, { useEffect } from "react";
import { Link } from 'react-router-dom';

// Import icon, img assets
import Logo from "../assets/img/Logo.png";

// Import CSS
import "../style/menu.css";

// Import icon
import {
    FaHome,
    FaShoppingCart,
    FaChartLine,
    FaRegClock,
    FaCog,
    FaTruck,
    FaSignOutAlt,
} from "react-icons/fa";

const menu = () => {

    useEffect(() => {
        const mainMenuLi = document.getElementById("mainMenu").querySelectorAll("li");

        function changeActive() { /* função para mudar a classe active */
            mainMenuLi.forEach(n => n.classList.remove("active")); /* removendo a classe active */
            this.classList.add("active"); /* adicionando a classe active */
        };
        mainMenuLi.forEach((n) => n.addEventListener("click", changeActive)); /* adicionando evento de click */
    }, []);


    return (
        <nav>
            <Link to="/">
                <img src={Logo} alt="Logo da pagina" />
            </Link>

            <ul id="mainMenu">
                <Icon to="/home" icon={<FaHome />} title="Home" /> {/* page Home */}
                <Icon to="/products/new" icon={<FaShoppingCart />} title="Produtos" /> {/* page Products */}
                <Icon to="/suppliers/new" icon={< FaTruck />} title="Fornecedor" /> {/* page Wallet */}
                <Icon to="#" icon={<FaChartLine />} title="Gráficos" /> {/* page Reports */}
                <Icon to="#" icon={<FaRegClock />} title="Histórico" /> {/* page History */}
            </ul>
            <ul className="lasttMenu">
                <Icon to="#" icon={<FaCog />} title="Configurações" /> {/* page Settings */}
                <Icon to="#" icon={<FaSignOutAlt />} title="Sair" onClick={""} /> {/* page Logout */}
            </ul>
        </nav>
    )
}

const Icon = ({ to, icon, title, onClick }) => ( // Icon component
    <li>
        <Link to={to} title={title} onClick={onClick} >
            {icon}
        </Link>
    </li>
);

export default menu