// Import Bibliotecas
import React, { useState } from "react";

// Import css
import "./catalogo.css";

// Import Componentes
import Cards from '../components/cards/cards'
import Modal from '../components/modal/modal';

// Import Icon, img, Assets
import { BsSearch } from "react-icons/bs";
import ImgQICoreI7 from '../../assets/img-produtos/pcQICoreI7.jpeg'


const cardData = [
    {
        imgSrc: ImgQICoreI7,
        title: 'Computador Desktop Intel Core i7',
        price: '2.800,00',
        status: 'Disponível',
        description: 'Descrição detalhada do produto 1.'
    },
];

function Catalogo() {

    const [showModal, setShowModal] = useState(false);
    const [selectedProduct, setSelectedProduct] = useState(null);

    const handleCardClick = (product) => {
        setSelectedProduct(product);
        setShowModal(true);
    };

    const handleCloseModal = () => {
        setShowModal(false);
        setSelectedProduct(null);
    };



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
                        <select className="opcoes" name="Categoria" title="Categoria">
                            <option value="" disabled selected hidden >Categoria</option>
                            <option value="categoria1">Categoria 1</option>
                            <option value="categoria2">Categoria 2</option>
                        </select>
                    </div>

                    <button className="clear-filter">Limpar filtro</button>
                </div>

                <div className="cards-container">
                    {cardData.map((card, index) => (
                        <Cards
                            key={index}
                            imgSrc={card.imgSrc}
                            title={card.title}
                            price={card.price}
                            status={card.status}
                            onClick={() => handleCardClick(card)}
                        />
                    ))}
                </div>

            </section>
                
            {selectedProduct && (
                <Modal
                    show={showModal}
                    onClose={handleCloseModal}
                    imgSrc={selectedProduct.imgSrc}
                    title={selectedProduct.title}
                    price={selectedProduct.price}
                    status={selectedProduct.status}
                    description={selectedProduct.description}
                />
            )}

        </>
    );
}

export default Catalogo; // Exporta a função Catalogo