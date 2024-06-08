import React from "react"; // Import React
import "./MainRightBottonContainer.css"; // Import CSS
import { BsEye } from "react-icons/bs"; // Import the BsEye icon
import Perfil_Import_IMG from "./Img_Imports"; // Import the Perfil_Import_IMG component

// Function to create the MainRightBottonContainer component
function MainRightBottonContainer() {
    return (
        <div className="MainRightBottonContainer">
            <div className="titleBottomName">
                <h2>Tops vendendores</h2>
                <i><BsEye /><a href="">Ver Mais</a></i>
            </div>
            
            {Perfil_Import_IMG && Perfil_Import_IMG.map((Perfil) => (
                <div className="PerfilVendedor" key={Perfil?.id}>
                    <div className="PerfilVendedorImg">
                        <img src={Perfil?.ImgSrc} alt="Perfil de usuario" />
                    </div>
                    <p className="NomeVendedor">{Perfil?.PerfilName} <span>{Perfil?.UserName}</span></p>

                    <button className="PerfilButton">
                        <a href="">Seguir</a>
                    </button>
                </div>

            ))};
        </div>
    );
}


export default MainRightBottonContainer; // Export the MainRightBottonContainer component