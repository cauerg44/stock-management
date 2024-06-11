// Import Bibliotecas
import React from "react";

// Import css
import "./catalogo.css";

// Import Componentes

// Import Icon, img, Assets
import { BsSearch } from "react-icons/bs";


function Catalogo() {
    return (
        <>
            <section className="container-geral">
                <h2 className="titulo-catalogo"> Catálogo de <span>Produtos</span></h2>

                <div className="filter">
                    <div className="inputs">
                        <input type="text" placeholder="Nome do produto" />
                        <i className="filter-icon-Search"><BsSearch /></i>
                    </div>
                    <div className="inputs">
                        <label htmlFor="Categoria">Categoria</label>
                        <select className="opcoes" name="Categoria" title="Categoria "/>
                    </div>

                    <button className="clear-filter">Limpar filtro</button>
                </div>
            </section>
        </>
    );
}

export default Catalogo; // Exporta a função Catalogo