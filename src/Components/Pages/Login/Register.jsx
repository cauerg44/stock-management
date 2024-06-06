import React, { useState, useEffect } from "react"; // Import React com o useState
import "./Register.css"; // Import CSS
import imglogin from "../../../Assets/imgs/imglogin.png"; // Import Image from "nftlogin.jpg
import Facebook from "../../../Assets/imgs/Facebook.png"; // Import Image from "nftlogin.jpg

// import auth faribase
import { auth } from "../../../Server/FirebaseConfig"; // Import auth from FirebaseConfig
import {
    updateProfile,
    getAuth,
    createUserWithEmailAndPassword,
    fetchSignInMethodsForEmail,
    sendEmailVerification,
} from 'firebase/auth'; // Import de funçoes 

// Import Biblioteca
import { Link, useNavigate, useLocation } from 'react-router-dom'; // Importing react-router-dom
import { BsArrowRight, BsFillUnlockFill, BsFillPersonFill, BsFillEnvelopeAtFill } from "react-icons/bs";
import { FcGoogle } from "react-icons/fc";

// Create a function component called Login
function Register() {
    // Create a constant called auth
    const [name, setName] = useState(''); // Estado para gerenciar o nome
    const [email, setEmail] = useState(''); // Create a constant called email
    const [password, setPassword] = useState(''); // Create a constant called password
    const [confirmPassword, setConfirmPassword] = useState(''); // Create a constant called password
    const [showPassword, setShowPassword] = useState(false); // State to manage show/hide password
    const navigate = useNavigate(); // useNavigate hook for navigation
    const [errorMessage, setErrorMessage] = useState(''); // State for error message
    const [isAlertVisible, setIsAlertVisible] = useState(false); // State for controlling alert visibility
    const [isSuccessMessageVisible, setIsSuccessMessageVisible] = useState(false); // State for controlling success message visibility
    const [isRegistering, setIsRegistering] = useState(false); // State to manage registration loading


    // Altera nome da página
    const location = useLocation(); // Create a constant called location
    useEffect(() => {
        document.title = "Register - NFT Colletion"; // Altera o título da página
    }, [location]);

    // Function para mudar o visual dos alerts
    const showAlert = (message) => {
        setErrorMessage(message); // Apresentar mensagem de erro
        setIsAlertVisible(true); // Definir alerta como true
        setTimeout(() => { // Definir tempo nos alertas
            setIsAlertVisible(false); // Definir alerta como false
        }, 5000); // Alert disappears after 5 seconds
    };

    // Function to show success message
    const showSuccessMessage = () => {
        setIsSuccessMessageVisible(true); // Definir mensagem visivelç
        setTimeout(() => { // Definir tempo para mensagem
            setIsSuccessMessageVisible(false); // Definir mensagem de cadastro de sucesso como false
            navigate("/Login"); // redirecionar para a página de login
        }, 5000); // Success message disappears after 5 seconds and then redirect to login
    };

    // Função para verificar se o e-mail já está cadastrado
    const checkIfEmailExists = async () => { // Declaar funçao assincrona
        try {
            const auth = getAuth(); // Declara comstante auth recebendo getauth
            const methods = await fetchSignInMethodsForEmail(auth, email); // Declarar const methos recebendo wait fet de auth e email
            return methods.length > 0; // retorna qauntidade dos methods
        } catch (error) {
            console.error("Erro na checagem do email", error); // Apresentar erro no console
            return false; // Retorno como falso
        }
    };

    // Funçao para verificar se formato do email e valido
    const isEmailValid = (email) => {
        // Retorna o resultado do método test() aplicado à expressão regular
        // A expressão regular valida o formato de um endereço de email
        return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)
        // ^[^\s@]+  - Inicia com um ou mais caracteres que não sejam espaço ou '@'
        // @         - Seguido de um símbolo '@'
        // [^\s@]+   - Seguido por um ou mais caracteres que não sejam espaço ou '@'
        // \.        - Seguido de um ponto literal '.'
        // [^\s@]+$  - Termina com um ou mais caracteres que não sejam espaço ou '@'
    }

    // Funçao para criar um usuário com email e senha
    const handleSignUp = async (e) => {
        e.preventDefault(); // previnir o comportamento padrão da página

        if (!email || !password || !confirmPassword || !name) {
            showAlert('Name, E-mail e senha são obrigatórios!');
            return;
        } // Verificar se Nome, email e a senha foram iseridos

        if (!isEmailValid(email)) {
            showAlert("Email invalido");
            return;
        } // Verificando e email e valido 

        if (password.length < 6) {
            showAlert('A senha deve ter pelo menos 6 caracteres!');
            return;
        } // Verificar se a senha tem pelo menos 6 caracteres


        if (password !== confirmPassword) {
            showAlert('As senhas não são iguais');
            return;
        }  // Verificar se a password e confirmPassword são iguais 


        const emailExists = await checkIfEmailExists(); // Verificar se email exists
        if (emailExists) { // Se emil existir 
            showAlert('Este e-mail já está cadastrado!'); // apresentar alert
            return;
        } // Verificar se o e-mail já está cadastrado

        try {
            setIsRegistering(true); // Definir isRegistering como true
            const userCredencial = await createUserWithEmailAndPassword(auth, email, password); // Criar usuário com email e senha
            await updateProfile(userCredencial.user, { displayName: name }); // Atualizar o nome do usuário
            await sendEmailVerification(auth.currentUser); // Enviar e-mail de verificação
            showSuccessMessage(); // Apresentar mensagem de sucesso
        } catch (error) {
            if (error.code === "auth/email-already-in-use") {
                showAlert('Este e-mail já está cadastrado!'); // Apresentar alert
            } else {
                showAlert(error.message); // Apresentar alert de erro
            }
        } finally {
            setIsRegistering(false); // Definir isRegistering como truee
        }
    }

    // Function to handle show/hide password
    const handleShowPassword = () => {
        setShowPassword(!showPassword);
    } // Mostra ou oculta a senha

    return (
        <main className="RegisterContainer">

            <section className="RegisterContainerLeft">
                <img src={imglogin} alt="Controle de Estoque" />
            </section>

            <section className="RegisterConatinerRight">
                <div className="EntradaRegister">
                    <div className="RegisterHeader">
                        <span>Cadastro</span>
                    </div>


                    <form className="FormRegister">

                        <div className="InputWithIcon">
                            <BsFillPersonFill className="InputIcon" />
                            <input
                                type="text"
                                name="name"
                                placeholder="Nome"
                                value={name}
                                onChange={(e) => setName(e.target.value)}
                            />
                        </div>

                        <div className="InputWithIcon">
                            <BsFillEnvelopeAtFill className="InputIcon" />
                            <input
                                type="email"
                                name="email"
                                placeholder="Adicione seu e-mail"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                            />
                        </div>

                        <div className="InputWithIcon">
                            <BsFillUnlockFill className="InputIcon" />
                            <input
                                type={showPassword ? "text" : "password"}
                                name="password"
                                placeholder="Criar senha"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                            />
                        </div>

                        <div className="InputWithIcon">
                            <BsFillUnlockFill className="InputIcon" />
                            <input
                                type={showPassword ? "text" : "password"}
                                name="confirmPassword"
                                placeholder="Confirme sua senha"
                                value={confirmPassword}
                                onChange={(e) => setConfirmPassword(e.target.value)}
                            />
                        </div>

                        <div className="CheckBox">
                            <input type="checkbox" id="showPassword" checked={showPassword} onChange={handleShowPassword} />
                            <label htmlFor="showPassword">Mostrar senha</label>
                        </div>

                        {isAlertVisible && (
                            <div className="alert">
                                {errorMessage}
                            </div>
                        )}

                        {isSuccessMessageVisible && (
                            <div className="success">
                                Usuário cadastrado com sucesso!
                            </div>
                        )}

                    </form>

                    <button className="BtnRegister" type="button"
                        onClick={handleSignUp}
                        disabled={isRegistering}
                    >
                        {isRegistering ? 'Cadastrando...' : 'Cadastrar'} <i><BsArrowRight /></i>
                    </button>

                    <div className="Divider">
                        <span className="DividerLine"></span>
                        <span className="DividerText">ou</span>
                        <span className="DividerLine"></span>
                    </div>

                    <div className="SocialRegister">
                        <button className="BtnGoogleRegister">
                            <FcGoogle />
                        </button>
                        <button className="BtnFacebookRegister">
                            <img src={Facebook} alt="Facebook" title="Register com Facebook" />
                        </button>
                    </div>

                    <div className="RegisterFooter">
                        <span>Já possue uma conta?</span>
                        <Link to="/">Login aqui!</Link>
                    </div>

                </div>
            </section>

        </main>
    );

}


export default Register; // Export the component