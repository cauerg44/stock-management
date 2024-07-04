// src/components/CategoryForm.jsx
import React, { useState } from 'react';
import { createCategory } from '../API/api.jsx';

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
        <form onSubmit={handleSubmit}>
            <div>
                <label>Category Name:</label>
                <input
                    type="text"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                />
            </div>
            <button type="submit">Create Category</button>
        </form>
    );
};

export default Category;
