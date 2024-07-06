// Importar bibliotecas necessárias
import axios from 'axios';

// Chamando a API de autenticação
const apiURL = 'http://localhost:8080/oauth2/token';

// Função para autenticação do usuário
const loginAuth = async (email, password) => {
    try {
        const response = await axios.post(apiURL, null, {
            params: {
                username: email,
                password: password,
                grant_type: 'password',
                client_id: 'myclientid',
                'client_secret': 'myclientsecret'
            },
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        });

        return response.data;
    } catch (error) {
        console.error("Error during authentication", error);
        throw error;
    }
};


export default loginAuth;