// Import Bibliotecas
import React, { useState } from 'react';

// Import API
import { createSupplier } from '../API/api';

// Import CSS
import '../style/forms.css';

// Import Componentes
import Headers from '../components/header.jsx';

const Supplier = () => {
    const [name, setName] = useState('');
    const [contactInfo, setContactInfo] = useState('');
    const [foundationYear, setFoundationYear] = useState('');
    const [sector, setSector] = useState('');

    const handleSubmit = async (event) => {
        event.preventDefault();
        const formattedDate = manufactureDate.split('-').reverse().join('/'); // Formato YYYY-MM-DD para DD/MM/YYYY
        try {
            await createSupplier({ name, manufactureDate: formattedDate, contactInfo, foundationYear, sector });
            alert('Supplier created successfully');
        } catch (error) {
            console.error('Error creating supplier:', error);
            alert('Failed to create supplier');
        }
    };

    const handleDateChange = (e) => {
        // Validação básica para manter o formato DD/MM/YYYY
        const inputDate = e.target.value;
        // Verifica se a data está no formato DD/MM/YYYY usando uma expressão regular
        if (/^\d{2}\/\d{2}\/\d{4}$/.test(inputDate)) {
            setFoundationYear(inputDate); // Se estiver no formato válido, atualiza o estado
        } else {
            // Caso contrário, não atualiza o estado (ou poderia exibir uma mensagem de erro)
            console.log('Data inválida. Use o formato DD/MM/YYYY.');
        }
    };

    return (
        <div>
            <Headers />
            <div className="form-container">
                <h2>Cadastrar Fornecedor</h2>
                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label htmlFor="name">Nome do Fornecedor</label>
                        <input
                            type="text"
                            id="name"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            placeholder='Nome do Fornecedor'
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="contactInfo">Informações de Contato</label>
                        <input
                            type="text"
                            id="contactInfo"
                            value={contactInfo}
                            onChange={(e) => setContactInfo(e.target.value)}
                            placeholder='Ex: Telefone, E-mail, etc.'
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="foundationYear">Data de Fundação</label>
                        <input
                            type="text"
                            id="foundationYear"
                            value={foundationYear}
                            onChange={(e) => setFoundationYear(e.target.value)}
                            placeholder="DD/MM/YYYY"
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="sector">Setor</label>
                        <input
                            type="text"
                            id="sector"
                            value={sector}
                            onChange={(e) => setSector(e.target.value)}
                            placeholder='Ex: Alimentício, Eletrônico, etc.'
                        />
                    </div>
                    <div className="form-actions">
                        <button type="submit" className="btn-primary">Salvar</button>
                        <button type="button" className="btn-secondary">Cancelar</button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default Supplier;
