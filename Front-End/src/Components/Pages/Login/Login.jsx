import React, { useState, useEffect, useContext } from "react"; // Import React
import "./Login.css"; // Import CSS
import imglogin from "../../../Assets/imgs/imglogin.png"; // Import Image from "nftlogin.jpg
import Facebook from "../../../Assets/imgs/Facebook.png"; // Import Image from "nftlogin.jpg
import { AuthGoogleContext } from "../../../Contexts/AuthGoogle";

// import auth faribase hook 
import { useSignInWithEmailAndPassword } from 'react-firebase-hooks/auth';
import { auth } from "../../../Server/FirebaseConfig";

// Import Biblioteca
import { Link, useLocation, useNavigate } from 'react-router-dom'; // Importing react-router-dom
import { BsArrowRight, BsFillUnlockFill, BsFillPersonFill } from "react-icons/bs";
import { FcGoogle } from "react-icons/fc";
import { fetchSignInMethodsForEmail, getAuth } from "firebase/auth";

// Create a functional component called Login
function Login() {
    // Create a constant called auth
    const [email, setEmail] = useState(''); // Create a constant called email
    const [password, setPassword] = useState(''); // Create a constant called password
    const [showPassword, setShowPassword] = useState(false); // State to manage show/hide password
    const [signInWithEmailAndPassword, user, error] = useSignInWithEmailAndPassword(auth); // Autenticar um usuário com email e senha
    const navigate = useNavigate(); // Estado e navegaçao entre pages
    const location = useLocation(); // Create a constant called location
    const [loading, setLoading] = useState(false);
    const [errorMessage, setErrorMessage] = useState(''); // State for error message
    const [isAlertVisible, setIsAlertVisible] = useState(false); // State for controlling alert visibility
    const { signInGoogle, loading: googleLoading } = useContext(AuthGoogleContext); // Create a constant called user

    // Altera nome da página
    useEffect(() => {
        document.title = "Login - ERP"; // Alterar o titulo da barra de navegaçao
        if (user) {
            setLoading(false); // Desativar o estado de carregamento
            if (user.emailVerified) {
                navigate("/Home"); // Redirecionar para a pagina Home apois a autenticaçao do usuario
            } else {
                showAlert("Verifique seu e-mail antes de fazer login!"); // Apresentando alerta 
                setIsAlertVisible(true);
            }
        }
    }, [location, user, navigate]); // Altera o nome da página

    // Function to show alert
    const showAlert = (message) => {
        setErrorMessage(message); // Define a mensagem de erro no estado errorMessage
        setIsAlertVisible(true); // Define o estado isAlertVisible como true para mostrar o alerta
        setTimeout(() => {
            setIsAlertVisible(false);
        }, 5000); // Alert disappears after 5 seconds
    };  // Após 5 segundos, define o estado isAlertVisible como false para ocultar o alerta

    // Função para verificar se o e-mail já está cadastrado
    const checkIfEmailExists = async () => {
        try {
            const auth = getAuth(); // Obtém os métodos de inscrição para o e-mail fornecido
            const methods = await fetchSignInMethodsForEmail(auth, email); // Retorna verdadeiro se a lista de métodos de inscrição tiver pelo menos um método, indicando que o e-mail já está cadastrado
            return methods.length > 0;
        } catch (error) {
            console.error("Erro na checagem do email", error); // Apresentar erro no console
            return false; // Retorna false para indicar que houve um erro ao verificar o e-mail
        }
    };


    // funçao para autenticar um usuário com email e senha
    const handleSignIn = async (e) => {
        e.preventDefault(); // previnir o comportamento padrão da página

        if (!email || !password) {
            showAlert("Por favor, preencha todos os campos!");
            return;
        }  // Verifica se o e-mail e a senha foram fornecidos


        setLoading(true); // Definir estado de loading como true
        const emailExists = await checkIfEmailExists(); // Verificar se o email fornecido ja esta cadastrado
        if (!emailExists) {  // Verificar se o email nao esta cadastrado
            setLoading(false); // Dasativa o estado de carregamento
            showAlert("E-mail ou senha inválidos!"); // Apresentar alerta
        } if (emailExists) { // Se o email existir 
            await signInWithEmailAndPassword(email, password);  // Tenta autenticar o usuário com o e-mail e senha fornecidos
            setLoading(false); // Desativar o carregamento 
            return;
        }

        try {
            await signInWithEmailAndPassword(email, password);  // Tenta autenticar o usuário com o e-mail e senha fornecidos
            if (auth.currentUser) {
                if (!auth.currentUser.emailVerified) {
                    throw new Error("Verifique seu e-mail antes de login! \nVerifique na sua caixa de entrada e sua pasta de spam ou lixeira eletrônica. ");
                } else {
                    navigate("/Home"); // Redirecionar o usuario para o Home 
                }
            }
        } catch (error) {
            setLoading(false); // Deasativar o carregamento
            showAlert("Erro ao fazer login, tente novamente mais tarde!" + error.message); // Apresentar alerta de erro ao efetuar o login
            console.error("Erro ao fazer login:", error); // Apresentar erro no console
        } finally {
            setLoading(false);  // Garante que o estado de carregamento seja desativado mesmo que ocorra uma exceção
        }

    };

    const loginGoogle = async (event) => {
        event.preventDefault(); // previnir o comportamento padrão da página
        try {
            await signInGoogle(); // Login com Google  
            if (auth.currentUser) { // Verifica se o usuario esta logado
                navigate("/Home"); // Redirecionando para a pagina Home
            } else {
                throw new Error("Falha, usuário não detectado."); // Apresentando erro ususario nao foi detectado
            }
        } catch (error) {
            console.error("Erro ao fazer login com o Google:", error);  // Apresentando erro no console
            showAlert("Por favor, tente novamente."); // Apresentando alerta de erro
        } finally {
            setLoading(false); // Desativa o estado de carregamento
        }
    } // Função para fazer login com o Google

    // Function to handle show/hide password
    const handleShowPassword = () => {
        setShowPassword(!showPassword);
    } // Mostra ou oculta a senha

    if (loading || googleLoading) { // Verificando se login ou googleLogin e true
        return (
            <div className="loading-container">
                <p>Entrando...</p>
            </div>
        ); // Retorna a mensagem de carregamento
    }
    if (error) { return (<div><p>Error: {error.message}</p></div>); } // Verifica se ocorreu algum erro durante o processo de logim

    return (
        <main className="LoginContainer">

            <section className="LoginContainerLeft">
                <img src={imglogin} alt="Controle de Estoque" />
            </section>

            <section className="LoginConatinerRight">
                <div className="EntradaLogin">
                    <div className="LoginHeader">
                        <span>Login</span>
                    </div>

                    {isAlertVisible && (
                        <div className="alertLogin">
                            {errorMessage}
                        </div>
                    )}

                    <form className="FormLogin">

                        <div className="InputWithIcon">
                            <BsFillPersonFill className="InputIcon" />
                            <input
                                id="email"
                                type="email"
                                name="email"
                                placeholder="Usuario com e-mail"
                                onChange={(e) => setEmail(e.target.value)}
                            />
                        </div>

                        <div className="InputWithIcon">
                            <BsFillUnlockFill className="InputIcon" />
                            <input
                                id="password"
                                type={showPassword ? "text" : "password"}
                                name="password"
                                placeholder="Senha"
                                onChange={(e) => setPassword(e.target.value)}
                            />
                        </div>
                        <div className="CheckBox">
                            <input type="checkbox" id="showPassword" checked={showPassword} onChange={handleShowPassword} />
                            <label htmlFor="showPassword">Mostrar senha</label>
                        </div>

                    </form>

                    <a className="EsqueseuSenha" href="/">Esqueceu sua senha ?</a>
                    <button className="BtnLogin" type="submit" onClick={handleSignIn}>
                        Entrar <i><BsArrowRight /></i>
                    </button>

                    <div className="Divider">
                        <span className="DividerLine"></span>
                        <span className="DividerText">ou</span>
                        <span className="DividerLine"></span>
                    </div>

                    <div className="SocialLogin">

                        <button className="BtnGoogleLogin" disabled={googleLoading} onClick={loginGoogle}>
                            <FcGoogle />
                        </button>

                        <button className="BtnFacebookLogin">
                            <img src={Facebook} alt="Facebook" title="Login com Facebook" />
                        </button>
                    </div>

                    <div className="LoginFooter">
                        <span>Ainda não tem conta?</span>
                        <Link to="/Register">Criar uma conta</Link>
                    </div>

                </div>
            </section>

        </main>
    );

}


export default Login; // Export the component