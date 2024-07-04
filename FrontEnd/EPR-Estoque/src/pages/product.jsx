import React, { useState, useEffect } from 'react';
import Header from '../components/header';      
import { createProduct, getCategories, getSuppliers } from '../API/api';

const Product = () => {
    const [name, setName] = useState('');
    const [price, setPrice] = useState('');
    const [manufactureDate, setManufactureDate] = useState('');
    const [rating, setRating] = useState('');
    const [description, setDescription] = useState('');
    const [supplierId, setSupplierId] = useState('');
    const [categoryId, setCategoryId] = useState('');
    const [categories, setCategories] = useState([]);
    const [suppliers, setSuppliers] = useState([]);

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
            await createProduct({
                name,
                price: parseFloat(price),
                manufactureDate,
                rating,
                description,
                supplier: { id: supplierId },
                categories: [{ id: categoryId }],
            });
            alert('Product created successfully');
        } catch (error) {
            console.error('Error creating product:', error);
            alert('Failed to create product');
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <Header />
            <div>
                <label>Name:</label>
                <input
                    type="text"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                />
            </div>
            <div>
                <label>Price:</label>
                <input
                    type="number"
                    value={price}
                    onChange={(e) => setPrice(e.target.value)}
                />
            </div>
            <div>
                <label>Manufacture Date:</label>
                <input
                    type="date"
                    value={manufactureDate}
                    onChange={(e) => setManufactureDate(e.target.value)}
                />
            </div>
            <div>
                <label>Rating:</label>
                <input
                    type="text"
                    value={rating}
                    onChange={(e) => setRating(e.target.value)}
                />
            </div>
            <div>
                <label>Description:</label>
                <textarea
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                />
            </div>
            <div>
                <label>Supplier:</label>
                <select
                    value={supplierId}
                    onChange={(e) => setSupplierId(e.target.value)}
                >
                    {suppliers.map((supplier) => (
                        <option key={supplier.id} value={supplier.id}>
                            {supplier.name}
                        </option>
                    ))}
                </select>
            </div>
            <div>
                <label>Category:</label>
                <select
                    value={categoryId}
                    onChange={(e) => setCategoryId(e.target.value)}
                >
                    {categories.map((category) => (
                        <option key={category.id} value={category.id}>
                            {category.name}
                        </option>
                    ))}
                </select>
            </div>
            <button type="submit">Create Product</button>
        </form>
    );
};

export default Product;
