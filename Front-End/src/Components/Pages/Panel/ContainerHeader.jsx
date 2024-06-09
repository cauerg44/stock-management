import React, { useState, useEffect, useContext } from "react"; // Import React
import "./ContainerHeader.css"; // Import CSS

// Import icon
import {
    FaBell, 
    FaChevronDown, 
    FaRegUserCircle, 
    FaUser,
    FaCog,
    FaSignOutAlt,
 } from "react-icons/fa"; // Import IconName from react-icons/fa
import { BiSearchAlt2 } from "react-icons/bi"; // Import IconName from react-icons/bi


// Import Bibliotecas
import { Link, useNavigate } from 'react-router-dom'; // Importando Link do react-router-dom
import { getAuth, signOut } from "firebase/auth"; // Import getAuth, signOut from firebase/auth


// Import context do login do google
import { AuthGoogleContext } from "../../../Contexts/AuthGoogle"; // Import AuthGoogleContext from AuthGoogle

// Declaraçao de variaveis
const auth = getAuth();

// Função para criar o componente ContainerHeader
function ContainerHeader() {
    const [profileImage, setProfileImage] = useState(null); // Estado para armazenar a imagem do perfil
    const [placeholder, setPlaceholder] = useState(""); // Estado para armazenar o placeholder
    const placeholderFull = "Coleções"; // String completa do placeholder
    const [index, setIndex] = useState(0); // Índice para acompanhar a posição atual na string do placeholder
    const { user } = useContext(AuthGoogleContext); // Recuperar o usuário do contexto

    useEffect(() => {

        if (user && user.photoURL) { // Verificar se o usuário está autenticado com o Google e se possui uma URL de foto
            setProfileImage(user.photoURL); // Atualiza o estado da imagem do perfil
        } else {
            setProfileImage(null);  // Define profileImage como null se não houver photoURL
        }
    }, [user]); // Dependência no usuário para atualizar quando ele mudar

    useEffect(() => { // Função para atualizar o estado da imagem do perfil 
        const MenuTarget = document.getElementById('MenuChevron'); // Recupera o elemento pelo ID
        const MenuContainer = document.getElementById('MenuConteiner'); // Recupera o elemento pelo ID

        // Add the event listener to the element
        MenuTarget.addEventListener("mouseenter", () => {
            MenuTarget.style.transform = "rotate(180deg)";
            MenuContainer.style.transform = "translateX(0px)";
        }); // Evento de mouse houver para abrir o menu dropdown

        MenuContainer.addEventListener("mouseleave", () => {
            MenuTarget.style.transform = "rotate(0deg)";
            MenuContainer.style.transform = "translateX(300px)";
        }); // Evento de mouse houver para fechar o menu dropdown


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

    }, [index]); // Atualiza o useEffect quando o index é alterado

    const navigate = useNavigate(); // Criar uma constante chamada navigate
    function logout(e) {
        e.preventDefault(); // Evita que o link redirecione
        signOut(auth).then(() => {
            navigate('/'); // Certifique-se de que '/login' é o caminho correto
        }).catch((error) => {
            console.error("Erro ao deslogar:", error);
        });
    }

    return (
        <div className="ContainerHeader">
            <div className="InputBox">
                <input type="text" placeholder={placeholder} />
                <i className="SearchIcon"><BiSearchAlt2 /></i>
            </div> {/* Container do imput */}

            <div className="PerfilConatiner">

                <i className="NotifiqueIcon"><FaBell /></i> {/* Icone de notificação */}

                <div className="PerfilImg">
                    {profileImage ? (
                        <img src={profileImage} alt="Perfill" />
                    ) : (
                        <FaRegUserCircle className="iconPerfill" />
                    )}
                </div>

                <p className="PerfilName">{user ? user.displayName : "Usuário"}</p> {/* Nome do perfil */}

                <i className="MenuChevron" id="MenuChevron"><FaChevronDown /></i> {/* Icone de seta para baixo */}

                <div className="MenuContainer" id="MenuConteiner">
                    <ul>
                        <Icon to="#" icon={<FaUser />} title="Perfill" /> {/* page perfil */}
                        <Icon to="#" icon={<FaCog />} title="Configurações" /> {/* page Settings */}
                        <Icon to="#" icon={<FaSignOutAlt />} title="Sair" onClick={logout} /> {/* page Logout */}
                    </ul>
                </div> {/* Container do menu drop*/}
            </div> {/* Container do perfil */}
        </div>
    );

}

const Icon = ({ to, icon, title, onClick }) => ( // Icon component
    <li style={{display: "flex"}}>
        <Link to={to} title={title} onClick={onClick} >
            {icon}
            <span style={{ marginLeft: '10px' }}>{title}</span>
        </Link>
    </li>
);

export default ContainerHeader; // Export the component