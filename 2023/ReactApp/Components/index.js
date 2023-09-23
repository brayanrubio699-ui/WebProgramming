import React from 'react';
import ReactDOM from 'react-dom/client';
import Encabezado from "./components/header.js"
import Pie from "./components/footer.js"
import Contenido from "./components/content.js"
import Carrusel from "./components/carrusel.js"

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <Carrusel />
    <Encabezado title="Titulo del header" subtitle="Subtitulo"/>
    <Contenido content="Componente Principal" />
    <Pie content="Pie de página"/>
  </React.StrictMode>
);

