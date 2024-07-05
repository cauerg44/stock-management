// Import Bibliotecas
import React, { useState } from 'react';

// Import CSS
import '../style/forms.css';
import '../style/category.css';

// Import API
import { createCategory } from '../API/api.jsx';

// Import Componentes
import Header from './header.jsx';

const Category = () => {
    const [name, setName] = useState('');

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            await createCategory({ name });
            alert('Category created successfully');
        } catch (error) {
            console.error('Error creating category:', error);
            alert('Failed to create category');
        }
    };

    return (
        <>
            <Header />
            <div className="form-container">
                <h2>Cadastrar Categoria</h2>
                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label htmlFor="name">Nome da Categoria</label>
                        <input
                            type="text"
                            id="name"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            placeholder='Nova Categoria'
                        />
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

export default Category;
