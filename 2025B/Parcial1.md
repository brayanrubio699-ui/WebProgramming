# Examen de Programación Web  

## Objetivos:
- Poner en práctica el manejo de formularios en HTML y JavaScript.  
- Implementar solicitudes simuladas con métodos **GET** y **POST** utilizando **fetch()** y un servidor de prueba (ejemplo: [JSONPlaceholder](https://jsonplaceholder.typicode.com/)).  
- Diseñar un prototipo web interactivo para gestionar información de grupos de investigación y semilleros.  
- Aplicar estilos con Bootstrap u otra librería CSS.  

---

## Instrucciones

La **Facultad de Ingeniería** cuenta con dos grupos de investigación con objetivos y campos de estudio distintos: **COMBA I+D** y **GIEIAM**. Ambos grupos tienen profesores investigadores vinculados, algunos de los cuales tienen a su cargo **semilleros de investigación**, que son espacios formativos para los estudiantes.  

Su tarea es crear un **mini-sitio web prototipo** que permita:  

1. **Listar grupos de investigación** (COMBA I+D y GIEIAM).  
   - Al seleccionar un grupo, mostrar información básica (nombre, objetivos, director, etc.).  
   - Esta información debe obtenerse mediante una **solicitud GET simulada** con JavaScript.  

2. **Registrar un docente investigador en un grupo de investigación.**  
   - Formulario con: nombre, formación académica, horario de atención, grupo asignado.  
   - El registro debe enviarse simulando una **solicitud POST** a un servidor fake (ej. JSONPlaceholder).  

3. **Vincular un estudiante a un semillero del grupo COMBA I+D.**  
   - Formulario con: nombre, código, carrera y semillero (Comba o Informa).  

4. **Agregar actividad a un semillero.**  
   - Formulario con: tipo de actividad (taller, conferencia, evento), fecha, hora, límite de asistentes y un resumen.  
   - Las actividades registradas deben aparecer listadas en la página principal del grupo de investigación.  

 El prototipo puede desarrollarse en **una sola página dinámica** o en varias páginas. Se permite el uso de **Bootstrap** o cualquier otra librería CSS.  

---

## Código base a completar

```html
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Grupos de Investigación</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="container py-4">
  <h1 class="mb-4">Gestión de Grupos de Investigación</h1>

  <!-- Listado de grupos (GET) -->
  <button id="btnCargar" class="btn btn-primary mb-3">Cargar Grupos</button>
  <ul id="listaGrupos" class="list-group mb-4"></ul>

  <!-- Formulario registrar docente (POST) -->
  <h2>Registrar Docente</h2>
  <form id="formDocente" class="mb-4">
    <!-- Campo nombre docente -->
    <input type="text" class="form-control mb-2" name="nombre" placeholder="Nombre docente" required>

    <!-- Campo formación académica -->
    <!-- COMPLETAR -->

    <!-- Campo horario de atención -->
    <!-- COMPLETAR -->

    <!-- Campo grupo -->
    <select class="form-control mb-2" name="grupo">
      <option value="COMBA I+D">COMBA I+D</option>
      <option value="GIEIAM">GIEIAM</option>
    </select>

    <button type="submit" class="btn btn-success">Registrar</button>
  </form>

  <!-- Formulario estudiante (para completar por el estudiante) -->
  <!-- COMPLETAR -->

  <!-- Formulario actividad semillero (para completar por el estudiante) -->
  <!-- COMPLETAR -->

  <script>
    const lista = document.getElementById("listaGrupos");
    const btnCargar = document.getElementById("btnCargar");
    const formDocente = document.getElementById("formDocente");

    // Ejemplo GET - cargar grupos
    btnCargar.addEventListener("click", async () => {
      // COMPLETAR: usar fetch con GET hacia JSONPlaceholder
      // Mostrar al menos dos grupos en la lista
    });

    // Ejemplo POST - registrar docente
    formDocente.addEventListener("submit", async (e) => {
      e.preventDefault();
      // COMPLETAR: capturar los datos del formulario
      // Convertir a JSON y enviarlos con fetch (POST)
      // Mostrar el resultado en un alert o en consola
    });
  </script>
</body>
</html>
```

---

##  Rúbrica de evaluación (5 puntos)

| Criterio | Puntos |
|----------|--------|
| Implementación de **GET** para mostrar información de grupos | 1.0 |
| Implementación de **POST** para registrar docentes | 1.0 |
| Formularios adicionales (estudiante y actividad) correctamente diseñados | 1.0 |
| Uso adecuado de **Bootstrap** (diseño responsivo, formularios, listas) | 1.0 |
| Organización, comentarios en el código y creatividad en la solución | 1.0 |
| **Total** | **5.0** |
