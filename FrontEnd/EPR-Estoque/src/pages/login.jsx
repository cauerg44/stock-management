// Import Bibliotecas
import React, { useState, useEffect, useContext } from "react";

// Import CSS
import "../style/login.css";

// Import Image, icon assets
import Banner from "../assets/img/Banner.png";
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { BsArrowRight, BsFillUnlockFill, BsFillPersonFill, BsFillEyeSlashFill, BsFillEyeFill } from "react-icons/bs";

const login = () => {
    // Create a constant called auth
    const [email, setEmail] = useState(''); // Create a constant called email
    const [password, setPassword] = useState(''); // Create a constant called password
    const [showPassword, setShowPassword] = useState(false); // State to manage show/hide password
    const navigate = useNavigate(); // Estado e navegaçao entre pages
    const [isPasswordNotEmpty, setIsPasswordNotEmpty] = useState(false); // Estado para controlar a visibilidade do ícone de olho


    // Function to handle show/hide password
    const handlePasswordChange = (e) => {
        setPassword(e.target.value);
        setIsPasswordNotEmpty(e.target.value.length > 0);
    }; // Mostra ou oculta a senha

    const toggleShowPassword = () => {
        setShowPassword(!showPassword);
    }; // Alterna a visibilidade da senha

    // Adicione estilos para o container do input e do ícone
    const inputWithIconStyle = {
        position: 'relative',
    };

    const iconStyle = {
        position: 'absolute',
        right: '10px',
        top: '50%',
        transform: 'translateY(-50%)',
        cursor: 'pointer',
        width: '50px',
        height: '20px',
        backgroundColor: '#ffff',
        border: 'none',
        borderRadius: '50%',
    };


    return (
        <main className="LoginContainer">
            <h1>Stocks management</h1>
            <section className="LoginContainerLeft">
                <img src={Banner} alt="Nft Login" />
            </section>

            <section className="LoginConatinerRight">
                <div className="EntradaLogin">
                    <div className="LoginHeader">
                        <span>Login</span>
                    </div>

                    <form className="FormLogin">

                        <div className="InputWithIcon">
                            <BsFillPersonFill className="InputIcon" />
                            <input
                                id="email"
                                type="email"
                                name="email"
                                placeholder="E-mail"
                                onChange={(e) => setEmail(e.target.value)}
                            />
                        </div>

                        <div className="InputWithIcon" style={inputWithIconStyle}>
                            <BsFillUnlockFill className="InputIcon" />
                            <input
                                id="password"
                                type={showPassword ? "text" : "password"}
                                name="password"
                                placeholder="Senha"
                                onChange={handlePasswordChange}
                            />
                            {isPasswordNotEmpty && (
                                <button type="button" onClick={toggleShowPassword} style={iconStyle}>
                                    {showPassword ? <BsFillEyeSlashFill /> : <BsFillEyeFill />}
                                </button>
                            )}
                        </div>

                    </form>

                    <button className="BtnLogin" type="submit" onClick={""}>
                        Entrar <i><BsArrowRight /></i>
                    </button>

                    <div className="Divider">
                        <span className="DividerLine"></span>
                        <span className="DividerText">ou</span>
                        <span className="DividerLine"></span>
                    </div>

                    <div className="LoginFooter">
                        <span>Ainda não tem conta?</span>
                        <Link to="/Register">Criar uma conta</Link>
                    </div>

                </div>
            </section>

        </main>
    )
}

export default login;
