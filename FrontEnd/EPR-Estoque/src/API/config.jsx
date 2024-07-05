// Configurnado a URL base da API e a função para obter o token de autenticação
export const API_BASE_URL = 'http://localhost:8080';

export const getAuthToken = () => {
    const token = localStorage.getItem('token');
    if (!token) {
        console.error('No token found in localStorage');
    }
    return token;
};

const token = 'seu_token_aqui';
localStorage.setItem('token', token);