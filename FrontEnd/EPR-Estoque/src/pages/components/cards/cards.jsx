// Import Bibliotecas
import React from 'react';

// Import CSS
import './cards.css';

// Import Imagens, assets



// import componetes


function cards({ imgSrc, title, price, data_fab, status, star, onClick }) {
    return (
        <>
            <div className="cards" onClick={onClick}>
                <div className="img-produto">
                    <img src={imgSrc} alt="Produto" />
                    <span>{star}</span>
                </div>

                <hr />

                <div className="card-title">
                    <h2>{title}</h2>

                    <div className="card-price">
                        <span>{price}</span>
                    </div>
                </div>
                
                <div className="card-status">
                    <span>{data_fab}</span>
                </div>

                <div className="card-status">
                    <span>{status}</span>
                </div>

            </div>
        </>
    )
}

export default cards; // Exporta a função cards