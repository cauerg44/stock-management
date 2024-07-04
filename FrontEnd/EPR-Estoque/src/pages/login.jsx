// Import Bibliotecas
import React, { useState } from "react";
import {useNavigate } from 'react-router-dom';

// Import CSS
import "../style/login.css";

// Import Image, icon assets
import Banner from "../assets/img/Banner.png";
import { BsArrowRight, BsFillUnlockFill, BsFillPersonFill, BsFillEyeSlashFill, BsFillEyeFill } from "react-icons/bs";

// Import Components
import loginAuth from "../hooks/loginAuth.jsx";

const login = () => {
    const [email, setEmail] = useState(''); // Create a constant called email
    const [password, setPassword] = useState(''); // Create a constant called password
    const [showPassword, setShowPassword] = useState(false); // State to manage show/hide password
    const [isPasswordNotEmpty, setIsPasswordNotEmpty] = useState(false); // Estado para controlar a visibilidade do ícone de olho
    const navigate = useNavigate(); // Estado e navegaçao entre pages


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

    // Função para lidar com o login
    const handleLogin = async (e) => {
        e.preventDefault();
        console.log("Email:", email);
        console.log("Password:", password);
        try {
            const data = await loginAuth(email, password);
            console.log("Login API Response:", data);
            if (data.access_token) {
                localStorage.setItem('token', data.access_token);
                navigate('/home');
            } else {
                alert('Login failed');
            }
        } catch (error) {
            alert('Login failed');
        }
    };



    return (
        <main className="LoginContainer">
            <h1>Gestão de estoque</h1>
            <section className="LoginContainerLeft">
                <img src={Banner} alt="Banner" />
            </section>

            <section className="LoginConatinerRight">
                <div className="EntradaLogin">
                    <div className="LoginHeader">
                        <span>Login</span>
                    </div>

                    <form className="FormLogin" onSubmit={handleLogin}>

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

                    <button className="BtnLogin" type="submit">
                        Entrar <i><BsArrowRight /></i>
                    </button>

                    <div className="Divider">
                        <span className="DividerLine"></span>
                        <span className="DividerText">ou</span>
                        <span className="DividerLine"></span>
                    </div>

                </div>
            </section>

        </main>
    )
}

export default login;
