// Importar bibliotecas necessárias
import axios from 'axios';

// Chamando a API de autenticação
const apiURL = 'http://localhost:3000/oauth2/token';

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

        return response.data;
    } catch (error) {
        console.error("Error during authentication", error);
        throw error;
    }

};

export default loginAuth;