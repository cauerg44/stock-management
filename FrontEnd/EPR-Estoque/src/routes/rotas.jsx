// Import Bibliotecas
import React from "react";
import { Route, Routes } from 'react-router-dom';

// Import css
import '../style/rotas.css';

// Import Componentes
import Home from '../pages/home.jsx';
import Login from '../pages/login.jsx';
import Menu from '../components/menu.jsx';


const rotas = () => {
  return (
    <Routes> 
        <Route path="/" element={<Login />} />
        <Route path="/home" element={
              <div className="app-conatiner-geral">
                  <Menu />
                  <div className="app-container-home">
                      <Home />
                  </div>
              </div>
        } />
    </Routes>
  )
}

export default rotas