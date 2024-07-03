// Import Bibliotecas
import React, { useState, useEffect, useContext } from "react";


// Import icon img assets
import { BiSearchAlt2 } from "react-icons/bi";
import Banner from "../assets/img/Banner.png";


// Import CSS
import "../style/header.css";

const header = () => {
    const [placeholder, setPlaceholder] = useState(""); // Estado para armazenar o placeholder
    const placeholderFull = "Consulta por ID / Nome"; // String completa do placeholder
    const [index, setIndex] = useState(0); // Índice para acompanhar a posição atual na string do placeholder

    useEffect(() => {
        // Função para adicionar o próximo caractere ao placeholder
        const addNextCharacter = () => {
            if (index < placeholderFull.length) {
                // Adiciona o próximo caractere ao placeholder
                setPlaceholder((prev) => prev + placeholderFull[index]);
                setIndex((prev) => prev + 1); // Atualiza o índice
            };
        };
        // Define um intervalo para chamar a função addNextCharacter a cada 200ms
        const timer = setInterval(addNextCharacter, 200);

        // Limpa o intervalo quando o componente é desmontado
        return () => clearInterval(timer);
    }, [index]);


    return (
        <header>
            <section>
                <div className="banner">
                    <img src={Banner} alt="" />
                </div>
                <div className="stock-conatainer">
                    <h2>Stock management</h2>
                    <h3>Controle e gestao de estoque</h3>
                </div>
            </section>

            <div className="InputBox">
                <input type="text" placeholder={placeholder} />
                <i className="SearchIcon"><BiSearchAlt2 /></i>
            </div>
        </header>
    )
}

export default header
