import React, { useEffect, useState, useRef } from "react"; // Import React
import "./MainContainer.css"; // Import CSS
import MainCardsContainer from "./MainCardsContainer"; // Import the MainCardsContainer component
import MainRightTopContainer from "./MainRightTopContainer"; // Import the MainRightTopContainer component
import MainRightBottonContainer from "./MainRightBottonContainer"; // Import the MainRightBottonContainer component
import { motion } from "framer-motion" // Import motion from framer-motion


// import img cardsNFT
import nft1 from "../../../Assets/Nft_Img/nft1.jpg"
import nft2 from "../../../Assets/Nft_Img/nft2.jpg"
import nft3 from "../../../Assets/Nft_Img/nft3.jpg"
import nft4 from "../../../Assets/Nft_Img/nft4.jpg"
import nft5 from "../../../Assets/Nft_Img/nft5.jpg"
import nft6 from "../../../Assets/Nft_Img/nft6.jpg"
import nft7 from "../../../Assets/Nft_Img/nft7.jpg"
import nft8 from "../../../Assets/Nft_Img/nft8.jpg"
import nft9 from "../../../Assets/Nft_Img/nft9.jpg"
import nft10 from "../../../Assets/Nft_Img/nft10.jpg"
import nft11 from "../../../Assets/Nft_Img/nft11.jpg"
import nft12 from "../../../Assets/Nft_Img/nft12.jpg"
import nft13 from "../../../Assets/Nft_Img/nft13.jpg"
import nft14 from "../../../Assets/Nft_Img/nft14.jpg"
import nft15 from "../../../Assets/Nft_Img/nft15.jpg"
import nft16 from "../../../Assets/Nft_Img/nft16.jpg"
import nft17 from "../../../Assets/Nft_Img/nft17.jpg"
import nft18 from "../../../Assets/Nft_Img/nft18.jpg"
import nft19 from "../../../Assets/Nft_Img/nft19.jpg"
import nft20 from "../../../Assets/Nft_Img/nft20.jpg"
import nft21 from "../../../Assets/Nft_Img/nft21.jpg"
import nft22 from "../../../Assets/Nft_Img/nft22.jpg"
import nft23 from "../../../Assets/Nft_Img/nft23.jpg"

// Criando um array com as img nft para adicionar ao banner
const Banner = [nft1, nft2, nft3, nft4, nft5, nft6, nft7, nft8, nft9, nft10]


// Function to create the MainContainer component
function MainContainer() {
    // Aplicando regras de slide no banner
    const slider = useRef(); // Criando uma referencia para o slider
    const [width, setWidth] = useState(0); // Criando um estado para o width
    const [currentSlide, setCurrentSlide] = useState(0); // Criando um estado para o slide atual
    const totalSlides = Banner.length; // criando um contador para o total de slides

    useEffect(() => {
        setWidth(
            slider.current ? slider.current.scrollWidth - slider.current.offsetWidth : 0
        );
    }, []); // UseEffect para aplicar as regras de slide no banner

    useEffect(() => {
        const timer = setInterval(() => {
            setCurrentSlide((currentSlide + 1) % totalSlides); // Muda o slide atual
        }, 3000); // Muda a imagem a cada 3 segundos

        return () => clearInterval(timer); // Limpa o intervalo quando o componente é desmontado
    }, [currentSlide, totalSlides]);



    return (
        <div className="MainContainer">
            <div className="left">
                <motion.div className="Banner" >
                    <motion.div ref={slider} className="BannerImg"
                        whileTap={{ cursor: "grabbing" }}
                        drag="x"
                        dragConstraints={{ right: 0, left: -width}}
                        style={{ x: -currentSlide * (slider.current?.offsetWidth || 0) }}
                        /* initial={{ x: 100 }} */
                        /* animate={{ x: 0 }} */
                        transition={{ ease: "easeOut", duration: 1}}
                    > 
                        {Banner.map(imglist => (
                            <motion.div className="ImgItem" key={imglist}>
                                <img src={imglist} alt="NFT Coleção" />
                            </motion.div>
                        ))}
                    </motion.div>
                    <div className="TextContainer">
                        <h1>Destaques do dia</h1>
                        <h2>1.5 ETH</h2>
                        <p>Com mais de 3.5 milhões downloads</p>
                    </div>
                </motion.div> {/* Container do Banner com motion*/}

                <div className="ContainerCards">
                    <div className="Filters">
                        <div className="Populares">
                            <h2>Feed</h2>
                            <div className="FilterButtons">
                                <button>
                                    <a href="">Todos</a>
                                </button>
                                <button>
                                    <a href="">Populares</a>
                                </button>
                                <button>
                                    <a href="">Personagens</a>
                                </button>
                                <button>
                                    <a href="">Objetos</a>
                                </button>
                                <button>
                                    <a href="">Flores</a>
                                </button>
                                <button>
                                    <a href="">Veículos</a>
                                </button>
                            </div>
                        </div>
                    </div>   {/* Container dos Filtros */}

                    <main>
                        <MainCardsContainer imgSrc={nft1} title={"Princesa"} hearts={"0"} />
                        <MainCardsContainer imgSrc={nft2} title={"Amigos"} hearts={"0"} />
                        <MainCardsContainer imgSrc={nft3} title={"Vovó Pipica"} hearts={"0"} />
                        <MainCardsContainer imgSrc={nft4} title={"Família"} hearts={"0"} />
                        <MainCardsContainer imgSrc={nft5} title={"Casal Feliz"} hearts={"0"} />
                        <MainCardsContainer imgSrc={nft6} title={"Nas Nuvens"} hearts={"0"} />
                        <MainCardsContainer imgSrc={nft7} title={"Realidade virtual"} hearts={"0"} />
                        <MainCardsContainer imgSrc={nft8} title={"Cubo virtual"} hearts={"0"} />
                        <MainCardsContainer imgSrc={nft9} title={"Notorio"} hearts={"00"} />
                        <MainCardsContainer imgSrc={nft10} title={"Flor Cristal"} hearts={"0"} />
                        <MainCardsContainer imgSrc={nft11} title={"Flor buque"} hearts={"0"} />
                        <MainCardsContainer imgSrc={nft12} title={"Flor Vidro"} hearts={"0"} />
                        <MainCardsContainer imgSrc={nft13} title={"EsferoTec"} hearts={"0"} />
                        <MainCardsContainer imgSrc={nft14} title={"Entrelaçado"} hearts={"0"} />
                        <MainCardsContainer imgSrc={nft15} title={"Oculos Futurista"} hearts={"00"} />
                        <MainCardsContainer imgSrc={nft16} title={"RosaLinna"} hearts={"0"} />
                        <MainCardsContainer imgSrc={nft17} title={"Flor"} hearts={"0"} />
                        <MainCardsContainer imgSrc={nft18} title={"Rosa"} hearts={"0"} />
                        <MainCardsContainer imgSrc={nft19} title={"Ferrary"} hearts={"0"} />
                        <MainCardsContainer imgSrc={nft20} title={"Lamborguine"} hearts={"0"} />
                        <MainCardsContainer imgSrc={nft21} title={"Lamb"} hearts={"0"} />
                        <MainCardsContainer imgSrc={nft22} title={"SUper CAr"} hearts={"0"} />
                        <MainCardsContainer imgSrc={nft23} title={"Carro"} hearts={"0"} />
                    </main>

                </div>

            </div> {/* Container Geral esquerdo */}



            <div className="Right">
                <MainRightTopContainer />
                <MainRightBottonContainer />
            </div> {/* Container Geral direito */}

        </div> // Container geral
    );
}

export default MainContainer; // Export the MainContainer component