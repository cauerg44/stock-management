// Import Bibliotecas
import React from 'react';

// Import icon - img - Assest
import { BsArrowLeftShort } from "react-icons/bs";

// Import CSS
import './modal.css';

function Modal({ show, onClose, imgSrc, title, price, status, description }) {
    if (!show) {
        return null;
    }

    return (
        <div className="modal-overlay">
            <div className="modal-content">
                <button className="close-button" title='Voltar' onClick={onClose}><BsArrowLeftShort /></button>

                <div className="modal-containe-left">
                    <div className="modal-img-produto">
                        <img src={imgSrc} alt="Produto" />
                        <div className="star">
                            <span>⭐</span>
                            <span>⭐</span>
                            <span>⭐</span>
                            <span>⭐</span>
                            <span>⭐</span>
                            <span>4.8</span>
                        </div>
                    </div>

                    <div className="title-status">
                        <h2>{title}</h2>
                        <div className="modal-price">
                            <span>{price}</span>
                        </div>

                        <div className="desconto-price">
                            <p>Desconto de 10%</p>
                            <p>Em 10x sem juros</p>
                        </div>

                        <div className="entrega">
                            <h3>Frete grátis</h3>
                            <p>Saiba os prazos de entrega e as formas de envio.</p>
                            <a href="#">Calcular o prazo de entrega</a>
                        </div>

                        <div className="modal-status">
                            <span>{status}</span>
                        </div>
                    </div>

                </div>
                <div className="modal-container-right">
                    <div className="modal-description">
                        <h3>Descrição</h3>
                        <p>Computador perfeito para uso doméstico ou empresarial, com gabinete tipo mini que cabe em qualquer espaço e com design moderno Tecnologia com baixíssimo consumo de energia. <br />
                            <br />
                            - Windows 10 Trial <br />
                            - Intel HD Graphics, experiência visual em alta definição
                            Execute suas atividades de forma rápida com o Processador Intel i7 e sua memória RAM de 16GB que oferece capacidade e desempenho confiável. <br />
                            <br />
                        
                            ESPECIFICAÇÕES TÉCNICAS: <br />

                            <ul>
                                <li>Processador: Intel i7</li>
                                <li>Memória 16 GB DDR3</li>
                                <li>SSD 240GB</li>
                                <li>Fonte Bivolt Manual</li>
                                <li>Placa de Vídeo Onboard Intel HD Graphics</li>
                                <li>Sistema Operacional Windows 10 Trial</li>
                                <li>Monitor: 20 Polegadas</li>
                                <li>Teclado e Mouse</li>
                                <li>Conexões e Redes</li>
                                <li>1x Rede RJ45</li>
                                <li>6x Entradas USB 2.0</li>
                                <li>1x Saída VGA</li>
                                <li>1x Saída HDMI</li>
                                <li>1x line-in (microfone)</li>
                                <li>1x line-out (fone ouvido)</li>
                                <li>Entrada de Rede: 10/100 Mbps</li>
                                <li>Áudio de alta definição (HD Áudio)</li>
                            </ul>

                            Garantia de 6 meses contra defeitos de fabricação.6 <br /> 
                            Garantia do vendedor: 6 meses</p>
                        <p>{description}</p>
                    </div>
                </div>
            </div>

        </div>
    );
}

export default Modal;
