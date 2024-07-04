import { API_BASE_URL, getAuthToken } from './config';

const apiRequest = async (endpoint, method = 'GET', body = null) => {
    const token = getAuthToken();
    const headers = {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
    };

    const options = {
        method,
        headers,
    };

    if (body) {
        options.body = JSON.stringify(body);
    }

    const response = await fetch(`${API_BASE_URL}${endpoint}`, options);

    if (!response.ok) {
        throw new Error('Something went wrong');
    }

    return response.json();
};

export const getCategories = () => apiRequest('/categories');
export const createCategory = (data) => apiRequest('/categories', 'POST', data);
export const updateCategory = (id, data) => apiRequest(`/categories/${id}`, 'PUT', data);
export const deleteCategory = (id) => apiRequest(`/categories/${id}`, 'DELETE');

export const getSuppliers = () => apiRequest('/suppliers');
export const createSupplier = (data) => apiRequest('/suppliers', 'POST', data);
export const updateSupplier = (id, data) => apiRequest(`/suppliers/${id}`, 'PUT', data);
export const deleteSupplier = (id) => apiRequest(`/suppliers/${id}`, 'DELETE');

export const getProducts = () => apiRequest('/products');
export const createProduct = (data) => apiRequest('/products', 'POST', data);
export const updateProduct = (id, data) => apiRequest(`/products/${id}`, 'PUT', data);
export const deleteProduct = (id) => apiRequest(`/products/${id}`, 'DELETE');
