// Import Bibliotecas
import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

// Import API
import { createProduct, getCategories, getSuppliers } from '../API/api';

// Import CSS
import '../style/forms.css';

// Import icons, img assets
import { BsPlusLg } from "react-icons/bs";

// Import Componentes
import Header from '../components/header';

const Product = () => {
    const [name, setName] = useState('');
    const [price, setPrice] = useState('');
    const [manufactureDate, setManufactureDate] = useState('');
    const [rating, setRating] = useState('');
    const [description, setDescription] = useState('');
    const [supplierId, setSupplierId] = useState('');
    const [categoryId, setCategoryId] = useState([]);
    const [categories, setCategories] = useState([]);
    const [suppliers, setSuppliers] = useState([]);

    const handlePriceChange = (e) => {
        const value = e.target.value;
        const formattedValue = formatPrice(value);
        setPrice(formattedValue);
    };

    const formatPrice = (value) => {
        const cleanedValue = value.replace(/\D/g, ""); // Remove tudo que não for dígito
        const options = { style: "currency", currency: "BRL" };
        const formatted = new Intl.NumberFormat("pt-BR", options).format(cleanedValue / 100);
        return formatted;
    };

    useEffect(() => {
        const fetchData = async () => {
            try {
                const fetchedCategories = await getCategories();
                const fetchedSuppliers = await getSuppliers();
                setCategories(fetchedCategories);
                setSuppliers(fetchedSuppliers);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };

        fetchData();
    }, []);

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const numericPrice = parseFloat(price.replace(/[^\d,]/g, '').replace(',', '.'));
            await createProduct({
                name,
                price: numericPrice,
                manufactureDate,
                rating,
                description,
                supplier: { id: supplierId },
                categories: categoryId.map(id => ({ id })),
            });
            alert('Product created successfully');
        } catch (error) {
            console.error('Error creating product:', error);
            alert('Failed to create product');
        }
    };

    return (
        <>
            <Header />
            <div className="form-container">
                <h2>Cadastrar Produto</h2>
                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label htmlFor="name">Nome do Produto</label>
                        <input
                            type="text"
                            id="name"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            placeholder='Nome do Produto'
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="price">Preço</label>
                        <input
                            type="text"
                            id="price"
                            value={price}
                            onChange={handlePriceChange}
                            placeholder="R$ 0,00"
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="manufactureDate">Data de Fabricação</label>
                        <input
                            type="date"
                            id="manufactureDate"
                            value={manufactureDate}
                            onChange={(e) => setManufactureDate(e.target.value)}
                            placeholder='Data de Fabricação'
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="rating">Classificação</label>
                        <input
                            type="text"
                            id="rating"
                            value={rating}
                            onChange={(e) => setRating(e.target.value)}
                            placeholder='Classificação'
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="description">Descrição</label>
                        <textarea
                            id="description"
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}
                            placeholder='Descrição do Produto'
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="supplierId">Fornecedor</label>
                        <select
                            id="supplierId"
                            value={supplierId}
                            onChange={(e) => setSupplierId(e.target.value)}
                        >
                            <option value="">Selecione um fornecedor</option>
                            {suppliers.map((supplier) => (
                                <option key={supplier.id} value={supplier.id}>
                                    {supplier.id} - {supplier.name}
                                </option>
                            ))}
                        </select>
                    </div>

                    <div className="form-group">
                        <label htmlFor="categoryId">Categoria</label>
                        <div className="category-input">
                            <select
                                id="categoryId"
                                multiple  // para permitir seleção múltipla
                                value={categoryId}
                                onChange={(e) => {
                                    const selectedOptions = Array.from(e.target.selectedOptions, option => option.value);
                                    setCategoryId(selectedOptions);
                                }}
                            >
                                <option value="">Selecione uma categoria</option>
                                {categories.map((category) => (
                                    <option key={category.id} value={category.id}>
                                        {category.id} - {category.name}
                                    </option>
                                ))}
                            </select>
                            <Link to="/categories/new" className="add-category-link">
                                <BsPlusLg className="add-category-icon" /> Categoria
                            </Link>
                        </div>
                    </div>

                    <div className="form-actions">
                        <button type="submit" className="btn-primary">Salvar</button>
                        <button type="button" className="btn-secondary">Cancelar</button>
                    </div>
                </form>
            </div>
        </>
    );
};

export default Product;
