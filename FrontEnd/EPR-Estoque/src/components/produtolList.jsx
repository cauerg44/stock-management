// Import Bibliotecas
import React, { useEffect, useState } from 'react';

// Import CSS
import '../style/productList.css';

// Import API
import { getProducts } from '../API/api';

const ProductList = () => {
    const [products, setProducts] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const data = await getProducts();
                setProducts(data);
            } catch (error) {
                setError(error.message);
                console.error('Error fetching products:', error);
            }
        };

        fetchData();
    }, []);

    return (
        <div className="product-list">
            <div className="header">
                <hr />
                <h2>lista dos produtos</h2>
            </div>
            <div className="table-container">
                <table>
                    <thead>
                        <tr>
                            <th style={{ whiteSpace: 'nowrap' }}>Name</th>
                            <th style={{ textAlign: 'center' }}>Preço</th>
                            <th style={{ textAlign: 'center' }}>Data de Fabricação</th>
                            <th style={{ textAlign: 'center' }}>Avaliação</th>
                            <th>Descrição</th>
                            <th style={{ textAlign: "center" }}> ID <span>Fornecedor</span></th>
                        </tr>
                    </thead>

                    <tbody>
                        {products.map((product) => (
                            <tr key={product.id}>
                                <td style={{ width: '250px' }}>{product.name}</td>
                                <td style={{ textAlign: 'center' }}>{product.price}</td>
                                <td style={{ textAlign: 'center' }}>{product.manufactureDate}</td>
                                <td style={{ textAlign: 'center' }}>{product.rating}</td>
                                <td>{product.description}</td>
                                <td style={{ textAlign: 'center' }}>{product.supplier.id}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default ProductList;