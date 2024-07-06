// Importar bibliotecas necessárias
import axios from 'axios';

// Chamando a API de autenticação
const apiURL = 'http://localhost:8080/oauth2/token';

// Função para autenticação do usuário
const loginAuth = async (email, password) => {
    try {
        const params = new URLSearchParams();
        params.append('username', email);
        params.append('password', password);
        params.append('grant_type', 'password');
        params.append('client_id', 'myclientid');
        params.append('client_secret', 'myclientsecret');

        const response = await axios.post(apiURL, params, {
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