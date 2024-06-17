// Import Bibliotecas
import React from 'react';

// Import css
import './home.css';
import '../../reset.css';
import '../../global.css';

// Import Assets, icon e Imagens
import Banner from '../../assets/img/banner.png';
import Seta from '../../assets/img/seta.gif';
import { BsChevronDoubleRight } from "react-icons/bs";

// Import Componentes


function Home() {
    return (
        <>
            <section className='home'>
                <div className="container">
                    <div className="container-left">
                        <h1>Seja bem vindo ao nosso Catálogo online</h1>
                        <p>Ajudaremos a encontra os melhores produos do mercado.</p>
                        <div className="setabutton">
                            <img src={Seta} alt="Seta" />
                        </div>

                        <div className="button-busca">
                           <a href="">
                            <button>
                                <p>Divulgue seus produtos aqui!</p>
                                <i>
                                    <BsChevronDoubleRight />
                                </i>
                            </button>
                           </a>
                        </div>
                    </div>


                    <div className="container-right">
                        <img src={Banner} alt="Banner" />
                    </div>
                </div>
            </section>
        </>
    );
}

export default Home; // Exporta a função Home
