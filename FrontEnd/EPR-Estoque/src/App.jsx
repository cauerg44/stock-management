// import Bibliotecas
import React from 'react'
import { BrowserRouter as Router } from 'react-router-dom'

// Import css
import './style/global.css' // Importa o css padrão do projeto


// Import Componentes
import Rotas from './routes/rotas.jsx' // Importa o componente Rotas






function App() {

    return (
        <Router>
            <Rotas />
        </Router>
    )
}

export default App // Exporta o componente App
