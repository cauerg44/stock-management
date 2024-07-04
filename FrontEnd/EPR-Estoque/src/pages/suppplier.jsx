import React, { useState } from 'react';
import { createSupplier } from '../API/api';
import Headers from '../components/header.jsx';

const Supplier = () => {
    const [name, setName] = useState('');
    const [contactInfo, setContactInfo] = useState('');
    const [foundationYear, setFoundationYear] = useState('');
    const [sector, setSector] = useState('');

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            await createSupplier({ name, contactInfo, foundationYear, sector });
            alert('Supplier created successfully');
        } catch (error) {
            console.error('Error creating supplier:', error);
            alert('Failed to create supplier');
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <Headers />
            <div>
                <label>Name:</label>
                <input
                    type="text"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                />
            </div>
            <div>
                <label>Contact Info:</label>
                <input
                    type="text"
                    value={contactInfo}
                    onChange={(e) => setContactInfo(e.target.value)}
                />
            </div>
            <div>
                <label>Foundation Year:</label>
                <input
                    type="number"
                    value={foundationYear}
                    onChange={(e) => setFoundationYear(e.target.value)}
                />
            </div>
            <div>
                <label>Sector:</label>
                <input
                    type="text"
                    value={sector}
                    onChange={(e) => setSector(e.target.value)}
                />
            </div>
            <button type="submit">Create Supplier</button>
        </form>
    );
};

export default Supplier;
