// Configurnado a URL base da API e a função para obter o token de autenticação
export const API_BASE_URL = 'http://localhost:8080';

export const getAuthToken = () => {
    // Obter o token do localStorage ou de onde você estiver armazenando
    return localStorage.getItem('token');
};
