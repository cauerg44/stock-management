// Objetivo: Arquivo principal do projeto
// import Bibliotecas
import React from 'react'
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'
// Import css
import './global.css' // Importa o css padrão do projeto
import './reset.css' // Importa o css de reset

// Import Componentes
import Menu from './pages/components/menu/menu' // Importa o componente Menu
import Home from './pages/home/home' // Importa o componente Home
import Catalogo from './pages/catalogo/catalogo' // Importa o componente Catalogo





function App() {

    return (
        <Router>
            <>
                <Menu />
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/catalogo" element={<Catalogo />} />
                </Routes>

            </>
        </Router>    )
}

export default App // Exporta o componente App
