// Import Bibliotecas
import React from "react";
import { Route, Routes } from 'react-router-dom';

// Import css
import '../style/rotas.css';

// Import Componentes
import Home from '../pages/home.jsx';
import Login from '../pages/login.jsx';
import Menu from '../components/menu.jsx';
import Product from '../pages/product.jsx';
import Supplier from '../pages/suppplier.jsx';
import Category from '../components/category.jsx';


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

          <Route path="/products/new" element={
              <div className="app-conatiner-geral">
                  <Menu />
                  <div className="app-container-home">
                      <Product />
                  </div>
              </div>
          } />
          <Route path="/suppliers/new" element={
              <div className="app-conatiner-geral">
                  <Menu />
                  <div className="app-container-home">
                      <Supplier />
                  </div>
              </div>
          } />
          <Route path="/categories/new" element={
              <div className="app-conatiner-geral">
                  <Menu />
                  <div className="app-container-home">
                      <Category />
                  </div>
              </div>
          } />

    </Routes>
  )
}

export default rotas