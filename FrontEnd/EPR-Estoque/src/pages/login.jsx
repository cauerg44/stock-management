// Import Bibliotecas
import React, { useState } from "react";
import {useNavigate } from 'react-router-dom';
import axios from 'axios';

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

        // Basic validation
        if (!email || !password) {
            alert('Por favor, preencha todos os campos.');
            return;
        }

        try {
            const data = await loginAuth(email, password);
            console.log("Login API Response:", data);

            if (data.access_token) {
                localStorage.setItem('token', data.access_token);
                console.log("Token saved to localStorage:", data.access_token); // Confirma se o token está sendo salvo corretamente
                navigate('/home');
            } else {
                alert('Credenciais inválidas. Por favor, tente novamente.');
            }
        } catch (error) {
            console.error("Erro durante o login:", error.message);
            alert('Login failed');
        }
    };

    // Função para autenticação do usuário
    const loginAuth = async (email, password) => {
        console.log("Executing loginAuth function with email:", email);
        try {
            const response = await axios.post(apiURL, null, {
                params: {
                    username: email,
                    password: password,
                }
            });

            console.log("API Response:", response.data); // Verifica a resposta da API de autenticação

            return response.data;
        } catch (error) {
            console.error("Error during authentication", error);
            throw error;
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
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                            />
                        </div>

                        <div className="InputWithIcon" style={inputWithIconStyle}>
                            <BsFillUnlockFill className="InputIcon" />
                            <input
                                id="password"
                                type={showPassword ? "text" : "password"}
                                name="password"
                                value={password}
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

                    <button className="BtnLogin" type="submit" onClick={handleLogin}>
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
