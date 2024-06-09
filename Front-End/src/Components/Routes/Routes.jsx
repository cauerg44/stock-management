import React from 'react';

// Import biblioteca
import { Routes, Route } from 'react-router-dom';

// Import Componentes
/* import Login from '../Pages/Login/Login'; */
import Register from '../Pages/Login/Register';
import Home from '../Pages/Home/Home';
import { PrivateRoutes } from './PrivateRoutes';

function AppRoutes() {
    return (
        <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/Register" element={<Register />} />
            
            <Route path="/Home" element={<PrivateRoutes />}> 
                <Route path="/Home" element={<Home />} />
            </Route>
        </Routes>
    );
}

export default AppRoutes; // Exporta o componente
