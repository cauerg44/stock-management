// Importando configurações básicas
import { API_BASE_URL, getAuthToken } from './config';

// Função para buscar produtos
export const getProducts = async () => {
    const response = await fetch(`${API_BASE_URL}/products`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${getAuthToken()}`,
        },
    });

    if (!response.ok) {
        throw new Error('Erro ao buscar produtos');
    }

    return response.json();
};

// Função para buscar categorias
export const getCategories = async () => {
    const token = getAuthToken();
    const response = await fetch(`${API_BASE_URL}/categories`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
        },
    });

    if (!response.ok) {
        throw new Error('Erro ao buscar categorias');
    }

    return response.json();
};

// Função para buscar fornecedores
export const getSuppliers = async () => {
    const token = getAuthToken();
    const response = await fetch(`${API_BASE_URL}/suppliers`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
        },
    });

    if (!response.ok) {
        throw new Error('Erro ao buscar fornecedores');
    }

    return response.json();
};


// Função para fazer uma requisição GET genérica
const fetchData = async (endpoint) => {
    const token = getAuthToken();
    const response = await fetch(`${API_BASE_URL}${endpoint}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
        },
    });

    if (!response.ok) {
        throw new Error(`Error: ${response.statusText}`);
    }

    return response.json();
};

// Função para fazer uma requisição POST genérica
const postData = async (endpoint, data) => {
    const token = getAuthToken();
    const response = await fetch(`${API_BASE_URL}${endpoint}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        },
        body: JSON.stringify(data),
    });

    if (!response.ok) {
        throw new Error(`Error: ${response.statusText}`);
    }

    return response.json();
};

// Função para fazer uma requisição PUT genérica
const putData = async (endpoint, data) => {
    const token = getAuthToken();
    const response = await fetch(`${API_BASE_URL}${endpoint}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        },
        body: JSON.stringify(data),
    });

    if (!response.ok) {
        throw new Error(`Error: ${response.statusText}`);
    }

    return response.json();
};

// Função para fazer uma requisição DELETE genérica
const deleteData = async (endpoint) => {
    const token = getAuthToken();
    const response = await fetch(`${API_BASE_URL}${endpoint}`, {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${token}`,
        },
    });

    if (!response.ok) {
        throw new Error(`Error: ${response.statusText}`);
    }

    return response.json();
};

// Função específica para fetchLoggedInUser
export const fetchLoggedInUser = () => fetchData('/users/me');

// Funções específicas para cada endpoint de Categorias
export const fetchCategories = () => fetchData('/categories');
export const fetchCategoryById = (id) => fetchData(`/categories/${id}`);
export const createCategory = (data) => postData('/categories', data);
export const updateCategory = (id, data) => putData(`/categories/${id}`, data);
export const deleteCategory = (id) => deleteData(`/categories/${id}`);

// Funções específicas para cada endpoint de Fornecedores
export const fetchSuppliers = () => fetchData('/suppliers');
export const fetchSupplierById = (id) => fetchData(`/suppliers/${id}`);
export const createSupplier = (data) => postData('/suppliers', data);
export const updateSupplier = (id, data) => putData(`/suppliers/${id}`, data);
export const deleteSupplier = (id) => deleteData(`/suppliers/${id}`);

// Funções específicas para cada endpoint de Produtos
export const fetchProducts = () => fetchData('/products');
export const fetchProductById = (id) => fetchData(`/products/${id}`);
export const createProduct = (data) => postData('/products', data);
export const updateProduct = (id, data) => putData(`/products/${id}`, data);
export const deleteProduct = (id) => deleteData(`/products/${id}`);

// Função específica para o endpoint de login
export const login = (data) => postData('/oauth2/token', data);

// Função específica para o endpoint de logout
export const logout = () => fetchData('/logout');
