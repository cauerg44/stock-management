import React from "react"; // Import React
import "./MainRightTopContainer.css"; // Import CSS
import { BsEye } from "react-icons/bs";

// Function to create the MainRightTopContainer component
function MainRightTopContainer() {
    return (
        <div className="MainRightTopContainer">
            <div className="TopTitle">
                <h2>Estatísticas</h2>
                <i><BsEye /><a href="">Ver Mais</a></i>
            </div> 

            <div className="EstatistGanhos">
                <p>Vendidos <span>500</span></p>
                <p>Cancelados <span>5</span></p>
                <p>Pendentes <span>25</span></p>
                <p>Entregue <span>470</span></p>
                <p>Total de Ganhos <span>750 Eht</span></p>
            </div>

        </div> /* End of the MainRightTopContainer component */
    );
}

export default MainRightTopContainer; // Export the MainRightTopContainer component