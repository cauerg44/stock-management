// Objetivo: Arquivo principal do projeto

// import Bibliotecas
import React from 'react' 
// Import css
import './global.css' // Importa o css padrão do projeto
import './reset.css' // Importa o css de reset

// Import Componentes
import Menu from './pages/components/menu/menu' // Importa o componente Menu
import Home from './pages/home/home' // Importa o componente Home





function App() {
  

  return (
    <>
        <Menu />
        <Home />

    </>
  )
}

export default App // Exporta o componente App
