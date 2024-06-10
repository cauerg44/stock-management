import React from 'react' // import das bibliotecas do React
import ReactDOM from 'react-dom/client' // import do ReactDOM
import App from './App.jsx' // import do componente App

import './global.css' // import do css global
import './reset.css' // import do css reset

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
)

