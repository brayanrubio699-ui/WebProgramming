# Programación Web, 2025A
# Universidad Santiago de Cali
## Unidad 3: Integración de Backend Spring Boot REST API con Frontend HTML/JavaScript

En este tutorial, Se va a crear una aplicación sencilla de gestión de tareas que consiste en:
1. Un backend desarrollado con Spring Boot que expone servicios REST
2. Un frontend desarrollado con HTML, JavaScript y Bootstrap que consume estos servicios

La aplicación permitirá:
- Listar todas las tareas disponibles
- Crear nuevas tareas

### Estructura del proyecto

```
todo-app/
├── backend/          # Proyecto Spring Boot
│   └── src/
│       └── main/
│           ├── java/com/example/todoapp/
│           │   ├── controller/
│           │   │   └── TaskController.java
│           │   ├── model/
│           │   │   └── Task.java
│           │   ├── repository/
│           │   │   └── TaskRepository.java
│           │   └── TodoAppApplication.java
│           └── resources/
│               └── application.properties
├── frontend/         # Frontend HTML/JS
│   ├── index.html
│   ├── css/
│   │   └── styles.css
│   └── js/
│       └── app.js
```

## Parte 1: Desarrollo del Backend con Spring Boot

### 1.1 Configuración del proyecto Spring Boot

Primero, Se necesita crear un proyecto Spring Boot. Puedes hacerlo usando [Spring Initializr](https://start.spring.io/) con las siguientes dependencias:
- Spring Web
- Spring Data JPA
- H2 Database (para desarrollo)

### 1.2 Creación del modelo

Se crea la clase `Task.java` para representar una tarea:

```java
package com.example.todoapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private boolean completed;
    
    // Constructor vacío requerido por JPA
    public Task() {
    }
    
    public Task(String title, boolean completed) {
        this.title = title;
        this.completed = completed;
    }
    
    // Getters y setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public boolean isCompleted() {
        return completed;
    }
    
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", completed=" + completed +
                '}';
    }
}
```

### 1.3 Creación del repositorio

Se crea una interfaz `TaskRepository.java` para manejar las operaciones de la base de datos:

```java
package com.example.todoapp.repository;

import com.example.todoapp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // Spring Data JPA proporciona métodos CRUD básicos automáticamente
    // No necesitamos implementar nada más por ahora
}
```

### 1.4 Creación del controlador REST

Ahora se crea `TaskController.java` que expondrá los endpoints REST:

```java
package com.example.todoapp.controller;

import com.example.todoapp.model.Task;
import com.example.todoapp.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")  // Permitir solicitudes de cualquier origen para desarrollo
public class TaskController {
    
    @Autowired
    private TaskRepository taskRepository;
    
    // Endpoint 1: Obtener todas las tareas
    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    
    // Endpoint 2: Crear una nueva tarea
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskRepository.save(task);
    }
    
    // Endpoint adicional: Obtener una tarea por ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskRepository.findById(id)
                .orElse(null);
        
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(task);
    }
}
```

### 1.5 Configuración de la aplicación Spring Boot

Se configura `application.properties` para el entorno de desarrollo:

```properties
# Configuración del servidor
server.port=8080

# Configuración de la base de datos H2 (en memoria para desarrollo)
spring.datasource.url=jdbc:h2:mem:todoapp
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# Configuración de JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Configuración para H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Inicializar la base de datos con datos de ejemplo
spring.jpa.defer-datasource-initialization=true
spring.sql.init.mode=always
```

### 1.6 Configuración para inicializar datos de ejemplo (opcional)

Se crea un archivo `data.sql` en la carpeta `src/main/resources`:

```sql
INSERT INTO task (title, completed) VALUES ('Aprender Spring Boot', false);
INSERT INTO task (title, completed) VALUES ('Crear una API REST', true);
INSERT INTO task (title, completed) VALUES ('Integrar con frontend', false);
```

### 1.7 Clase principal de la aplicación

La clase principal `TodoAppApplication.java` ya debería existir:

```java
package com.example.todoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodoAppApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(TodoAppApplication.class, args);
    }
}
```

## Parte 2: Desarrollo del Frontend con HTML, JavaScript y Bootstrap

### 2.1 Estructura de archivos frontend

Se crean los siguientes archivos en la carpeta `frontend`:

**index.html**
```html
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Todo App</title>
    <!-- Bootstrap CSS CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="container mt-5">
        <h1 class="mb-4 text-center">Lista de Tareas</h1>
        
        <!-- Formulario para agregar nuevas tareas -->
        <div class="card mb-4">
            <div class="card-header">
                <h5>Agregar Nueva Tarea</h5>
            </div>
            <div class="card-body">
                <form id="task-form">
                    <div class="mb-3">
                        <label for="task-title" class="form-label">Título de la tarea</label>
                        <input type="text" class="form-control" id="task-title" required>
                    </div>
                    <div class="mb-3 form-check">
                        <input type="checkbox" class="form-check-input" id="task-completed">
                        <label class="form-check-label" for="task-completed">Completada</label>
                    </div>
                    <button type="submit" class="btn btn-primary">Agregar</button>
                </form>
            </div>
        </div>
        
        <!-- Lista de tareas -->
        <div class="card">
            <div class="card-header d-flex justify-content-between align-items-center">
                <h5>Mis Tareas</h5>
                <button id="refresh-btn" class="btn btn-sm btn-outline-secondary">
                    Actualizar
                </button>
            </div>
            <div class="card-body">
                <ul id="task-list" class="list-group">
                    <!-- Las tareas se insertarán aquí con JavaScript -->
                </ul>
            </div>
        </div>
        
        <!-- Indicador de carga -->
        <div id="loading" class="text-center mt-3 d-none">
            <div class="spinner-border text-primary" role="status">
                <span class="visually-hidden">Cargando...</span>
            </div>
        </div>
        
        <!-- Mensajes de alerta -->
        <div id="alert-container" class="mt-3">
            <!-- Las alertas se insertarán aquí con JavaScript -->
        </div>
    </div>

    <!-- Bootstrap JS Bundle CDN -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="js/app.js"></script>
</body>
</html>
```

**css/styles.css**
```css
.completed-task {
    text-decoration: line-through;
    color: #6c757d;
}

.task-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.alert {
    animation: fadeOut 5s forwards;
}

@keyframes fadeOut {
    0% { opacity: 1; }
    70% { opacity: 1; }
    100% { opacity: 0; }
}
```

**js/app.js**
```javascript
// Configuración
const API_URL = 'http://localhost:8080/api/tasks';

// Elementos del DOM
const taskList = document.getElementById('task-list');
const taskForm = document.getElementById('task-form');
const taskTitle = document.getElementById('task-title');
const taskCompleted = document.getElementById('task-completed');
const refreshBtn = document.getElementById('refresh-btn');
const loading = document.getElementById('loading');
const alertContainer = document.getElementById('alert-container');

// Función para mostrar indicador de carga
function showLoading() {
    loading.classList.remove('d-none');
}

// Función para ocultar indicador de carga
function hideLoading() {
    loading.classList.add('d-none');
}

// Función para mostrar alertas
function showAlert(message, type = 'success') {
    const alert = document.createElement('div');
    alert.className = `alert alert-${type} alert-dismissible fade show`;
    alert.role = 'alert';
    alert.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    `;
    
    alertContainer.appendChild(alert);
    
    // Eliminar la alerta después de 5 segundos
    setTimeout(() => {
        alert.remove();
    }, 5000);
}

// Función para cargar todas las tareas desde la API
async function loadTasks() {
    try {
        showLoading();
        
        const response = await fetch(API_URL);
        
        if (!response.ok) {
            throw new Error('Error al cargar las tareas');
        }
        
        const tasks = await response.json();
        
        // Limpiar la lista actual
        taskList.innerHTML = '';
        
        // Mostrar las tareas en la lista
        tasks.forEach(task => {
            const li = document.createElement('li');
            li.className = 'list-group-item task-item';
            
            const taskText = document.createElement('span');
            taskText.textContent = task.title;
            if (task.completed) {
                taskText.classList.add('completed-task');
            }
            
            li.appendChild(taskText);
            
            // Agregar un badge para mostrar el estado
            const badge = document.createElement('span');
            badge.className = task.completed 
                ? 'badge bg-success' 
                : 'badge bg-warning text-dark';
            badge.textContent = task.completed ? 'Completada' : 'Pendiente';
            li.appendChild(badge);
            
            taskList.appendChild(li);
        });
        
        hideLoading();
        
    } catch (error) {
        console.error('Error:', error);
        showAlert('No se pudieron cargar las tareas', 'danger');
        hideLoading();
    }
}

// Función para crear una nueva tarea
async function createTask(title, completed) {
    try {
        showLoading();
        
        const response = await fetch(API_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ title, completed })
        });
        
        if (!response.ok) {
            throw new Error('Error al crear la tarea');
        }
        
        // Recargamos la lista de tareas
        loadTasks();
        showAlert('¡Tarea creada con éxito!');
        
    } catch (error) {
        console.error('Error:', error);
        showAlert('No se pudo crear la tarea', 'danger');
        hideLoading();
    }
}

// Event Listeners
document.addEventListener('DOMContentLoaded', () => {
    // Cargar tareas al iniciar
    loadTasks();
    
    // Evento para el botón de actualizar
    refreshBtn.addEventListener('click', loadTasks);
    
    // Evento para el formulario de agregar tarea
    taskForm.addEventListener('submit', (e) => {
        e.preventDefault();
        
        const title = taskTitle.value.trim();
        const completed = taskCompleted.checked;
        
        if (title) {
            createTask(title, completed);
            
            // Limpiar el formulario
            taskTitle.value = '';
            taskCompleted.checked = false;
        }
    });
});
```

## Parte 3: Ejecutando y probando la aplicación

### 3.1 Ejecutando el backend

1. Navegue a la carpeta del proyecto backend
2. Ejecute `./mvnw spring-boot:run` (Linux/Mac) o `mvnw.cmd spring-boot:run` (Windows)
3. El servidor de Spring Boot debería iniciar en http://localhost:8080

### 3.2 Ejecutando el frontend

Para un desarrollo sencillo, se puede usar cualquier servidor web ligero:

- Usando Python:
  ```
  cd frontend
  python -m http.server 3000
  ```

- Usando Node.js con `http-server`:
  ```
  npm install -g http-server
  cd frontend
  http-server -p 3000
  ```

### 3.3 Probando la integración

1. Abra su navegador y ve a http://localhost:3000
2. Debería ver la interfaz de la aplicación "Todo"
3. El frontend automáticamente cargará las tareas desde el backend
4. Pruebe a crear una nueva tarea y verifique que aparezca en la lista

## Parte 4: Explicación de la integración Backend-Frontend

### 4.1 Cómo funciona la API REST

Nuestra API REST sigue los principios RESTful:

- **GET /api/tasks**: Devuelve todas las tareas (Servicio 1)
- **POST /api/tasks**: Crea una nueva tarea (Servicio 2)
- **GET /api/tasks/{id}**: Obtiene una tarea específica por su ID (servicio adicional)

### 4.2 Cómo consume el frontend estos servicios

El frontend utiliza la API Fetch de JavaScript para realizar peticiones HTTP al backend:

1. **Consumiendo el servicio GET /api/tasks** (listar tareas):
   ```javascript
   const response = await fetch(API_URL);
   const tasks = await response.json();
   ```

2. **Consumiendo el servicio POST /api/tasks** (crear tarea):
   ```javascript
   const response = await fetch(API_URL, {
       method: 'POST',
       headers: {
           'Content-Type': 'application/json'
       },
       body: JSON.stringify({ title, completed })
   });
   ```

### 4.3 Puntos importantes de la integración

1. **CORS (Cross-Origin Resource Sharing)**:
   - En el backend utilizamos la anotación `@CrossOrigin(origins = "*")` para permitir peticiones desde el frontend
   - En producción, se recomendaría limitar los orígenes permitidos

2. **Formato de datos**:
   - El backend devuelve y acepta datos en formato JSON
   - El frontend envía y recibe datos en formato JSON

3. **Manejo de errores**:
   - El frontend incluye try/catch para manejar errores de la API
   - Se muestran alertas al usuario cuando ocurren errores

## Conclusión

Con esta guía se crea una aplicación web completa con una clara separación entre:

- **Backend**: Una API REST desarrollada con Spring Boot
- **Frontend**: Una interfaz de usuario sencilla desarrollada con HTML, JavaScript y Bootstrap

El frontend consume dos servicios principales de la API:
1. Obtener todas las tareas (GET)
2. Crear una nueva tarea (POST)

Esta arquitectura permite escalar y mantener cada parte de forma independiente, además de poder reutilizar la API para otros clientes como aplicaciones móviles o de escritorio.

## Recursos adicionales

- [Documentación de Spring Boot](https://spring.io/projects/spring-boot)
- [Documentación de Fetch API](https://developer.mozilla.org/es/docs/Web/API/Fetch_API)
- [Documentación de Bootstrap](https://getbootstrap.com/docs/5.3/getting-started/introduction/)
