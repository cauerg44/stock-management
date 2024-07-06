// Import Bibliotecas
import axios from 'axios';

// Função para obter dados do usuário
const getUserData = async () => {
    const token = localStorage.getItem('token');
    if (!token) {
        throw new Error('Token não encontrado');
    }

    try {
        const response = await axios.get('http://localhost:8080/users/me', {
            headers: {
                'Authorization': `Bearer ${token}`,
                'accept': 'application/json'
            }
        });
        return response.data;
    } catch (error) {
        console.error("Erro ao obter dados do usuário", error);
        throw error;
    }
};

export default getUserData;