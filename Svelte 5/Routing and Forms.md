# Tutorial Svelte 5 - Parte 2: Routing, Formularios y Primera App

## 2.2 Configuración de Routing con SvelteKit

### Instalación de SvelteKit
```bash
npm create svelte@latest mi-app-svelte
cd mi-app-svelte
npm install
npm run dev
```

### Estructura de carpetas para routing
```
src/
├── routes/
│   ├── +layout.svelte
│   ├── +page.svelte
│   ├── about/
│   │   └── +page.svelte
│   ├── productos/
│   │   ├── +page.svelte
│   │   └── [id]/
│   │       └── +page.svelte
│   └── contacto/
│       └── +page.svelte
```

### Layout principal (+layout.svelte)
```svelte
<script>
  import { page } from '$app/stores';
</script>

<nav>
  <a href="/" class:active={$page.url.pathname === '/'}>Inicio</a>
  <a href="/about" class:active={$page.url.pathname === '/about'}>Acerca</a>
  <a href="/productos" class:active={$page.url.pathname.startsWith('/productos')}>Productos</a>
  <a href="/contacto" class:active={$page.url.pathname === '/contacto'}>Contacto</a>
</nav>

<main>
  <slot />
</main>

<style>
  nav {
    padding: 1rem;
    background: #f0f0f0;
    display: flex;
    gap: 1rem;
  }
  
  a {
    text-decoration: none;
    color: #333;
    padding: 0.5rem;
  }
  
  .active {
    background: #007acc;
    color: white;
    border-radius: 4px;
  }
  
  main {
    padding: 2rem;
  }
</style>
```

### Navegación programática
```svelte
<script>
  import { goto } from '$app/navigation';
  
  function navegarAProductos() {
    goto('/productos');
  }
  
  function navegarConParametros() {
    goto('/productos/123?categoria=electronics');
  }
</script>

<button onclick={navegarAProductos}>Ver Productos</button>
<button onclick={navegarConParametros}>Producto Específico</button>
```

## 2.3 Manejo de Formularios

### Formulario básico con validación
```svelte
<script>
  let formData = $state({
    nombre: '',
    email: '',
    mensaje: ''
  });
  
  let errores = $state({});
  let enviando = $state(false);
  
  function validarFormulario() {
    errores = {};
    
    if (!formData.nombre.trim()) {
      errores.nombre = 'El nombre es requerido';
    }
    
    if (!formData.email.trim()) {
      errores.email = 'El email es requerido';
    } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.email)) {
      errores.email = 'Email inválido';
    }
    
    if (!formData.mensaje.trim()) {
      errores.mensaje = 'El mensaje es requerido';
    }
    
    return Object.keys(errores).length === 0;
  }
  
  async function enviarFormulario(event) {
    event.preventDefault();
    
    if (!validarFormulario()) return;
    
    enviando = true;
    
    try {
      // Simular envío
      await new Promise(resolve => setTimeout(resolve, 2000));
      
      alert('Formulario enviado correctamente!');
      
      // Limpiar formulario
      formData = { nombre: '', email: '', mensaje: '' };
      
    } catch (error) {
      alert('Error al enviar el formulario');
    } finally {
      enviando = false;
    }
  }
</script>

<form onsubmit={enviarFormulario}>
  <div class="campo">
    <label for="nombre">Nombre:</label>
    <input 
      id="nombre"
      type="text" 
      bind:value={formData.nombre}
      class:error={errores.nombre}
    />
    {#if errores.nombre}
      <span class="error-text">{errores.nombre}</span>
    {/if}
  </div>
  
  <div class="campo">
    <label for="email">Email:</label>
    <input 
      id="email"
      type="email" 
      bind:value={formData.email}
      class:error={errores.email}
    />
    {#if errores.email}
      <span class="error-text">{errores.email}</span>
    {/if}
  </div>
  
  <div class="campo">
    <label for="mensaje">Mensaje:</label>
    <textarea 
      id="mensaje"
      bind:value={formData.mensaje}
      class:error={errores.mensaje}
    ></textarea>
    {#if errores.mensaje}
      <span class="error-text">{errores.mensaje}</span>
    {/if}
  </div>
  
  <button type="submit" disabled={enviando}>
    {enviando ? 'Enviando...' : 'Enviar'}
  </button>
</form>

<style>
  .campo {
    margin-bottom: 1rem;
  }
  
  label {
    display: block;
    margin-bottom: 0.5rem;
    font-weight: bold;
  }
  
  input, textarea {
    width: 100%;
    padding: 0.5rem;
    border: 1px solid #ccc;
    border-radius: 4px;
  }
  
  .error {
    border-color: #e74c3c;
  }
  
  .error-text {
    color: #e74c3c;
    font-size: 0.875rem;
    margin-top: 0.25rem;
  }
  
  button {
    background: #007acc;
    color: white;
    padding: 0.75rem 1.5rem;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }
  
  button:disabled {
    opacity: 0.6;
    cursor: not-allowed;
  }
</style>
```

### Formularios con múltiples tipos de input
```svelte
<script>
  let userData = $state({
    nombre: '',
    edad: 18,
    genero: '',
    intereses: [],
    aceptaTerminos: false,
    fechaNacimiento: '',
    avatar: null
  });
  
  const opcionesGenero = ['masculino', 'femenino', 'otro', 'prefiero-no-decir'];
  const opcionesIntereses = ['tecnología', 'deportes', 'música', 'arte', 'cocina'];
  
  function toggleInteres(interes) {
    if (userData.intereses.includes(interes)) {
      userData.intereses = userData.intereses.filter(i => i !== interes);
    } else {
      userData.intereses = [...userData.intereses, interes];
    }
  }
  
  function handleFileChange(event) {
    userData.avatar = event.target.files[0];
  }
</script>

<form>
  <!-- Input texto -->
  <div>
    <label>Nombre:</label>
    <input type="text" bind:value={userData.nombre} />
  </div>
  
  <!-- Input número con rango -->
  <div>
    <label>Edad: {userData.edad}</label>
    <input type="range" min="13" max="100" bind:value={userData.edad} />
  </div>
  
  <!-- Radio buttons -->
  <div>
    <label>Género:</label>
    {#each opcionesGenero as opcion}
      <label>
        <input type="radio" bind:group={userData.genero} value={opcion} />
        {opcion}
      </label>
    {/each}
  </div>
  
  <!-- Checkboxes múltiples -->
  <div>
    <label>Intereses:</label>
    {#each opcionesIntereses as interes}
      <label>
        <input 
          type="checkbox" 
          checked={userData.intereses.includes(interes)}
          onchange={() => toggleInteres(interes)}
        />
        {interes}
      </label>
    {/each}
  </div>
  
  <!-- Checkbox simple -->
  <div>
    <label>
      <input type="checkbox" bind:checked={userData.aceptaTerminos} />
      Acepto los términos y condiciones
    </label>
  </div>
  
  <!-- Input fecha -->
  <div>
    <label>Fecha de nacimiento:</label>
    <input type="date" bind:value={userData.fechaNacimiento} />
  </div>
  
  <!-- Input archivo -->
  <div>
    <label>Avatar:</label>
    <input type="file" accept="image/*" onchange={handleFileChange} />
  </div>
  
  <button type="submit" disabled={!userData.aceptaTerminos}>
    Guardar
  </button>
</form>

<!-- Vista previa de datos -->
<div class="preview">
  <h3>Vista previa:</h3>
  <pre>{JSON.stringify(userData, null, 2)}</pre>
</div>

<style>
  form > div {
    margin-bottom: 1rem;
  }
  
  .preview {
    margin-top: 2rem;
    padding: 1rem;
    background: #f5f5f5;
    border-radius: 4px;
  }
  
  pre {
    font-size: 0.875rem;
  }
</style>
```

## 2.4 Primera Aplicación: Lista de Tareas

### Estructura del proyecto
```
src/
├── lib/
│   ├── components/
│   │   ├── TodoItem.svelte
│   │   ├── TodoForm.svelte
│   │   └── TodoFilter.svelte
│   └── stores/
│       └── todos.js
└── routes/
    └── +page.svelte
```

### Store para gestión de estado (src/lib/stores/todos.js)
```javascript
import { writable } from 'svelte/store';

function createTodosStore() {
  const { subscribe, set, update } = writable([]);
  
  return {
    subscribe,
    
    add: (text) => {
      const newTodo = {
        id: Date.now(),
        text: text.trim(),
        completed: false,
        createdAt: new Date()
      };
      
      update(todos => [...todos, newTodo]);
    },
    
    toggle: (id) => {
      update(todos => 
        todos.map(todo => 
          todo.id === id 
            ? { ...todo, completed: !todo.completed }
            : todo
        )
      );
    },
    
    remove: (id) => {
      update(todos => todos.filter(todo => todo.id !== id));
    },
    
    edit: (id, newText) => {
      update(todos =>
        todos.map(todo =>
          todo.id === id
            ? { ...todo, text: newText.trim() }
            : todo
        )
      );
    },
    
    clearCompleted: () => {
      update(todos => todos.filter(todo => !todo.completed));
    },
    
    load: () => {
      const saved = localStorage.getItem('svelte-todos');
      if (saved) {
        set(JSON.parse(saved));
      }
    },
    
    save: (todos) => {
      localStorage.setItem('svelte-todos', JSON.stringify(todos));
    }
  };
}

export const todos = createTodosStore();
```

### Componente TodoItem (src/lib/components/TodoItem.svelte)
```svelte
<script>
  import { todos } from '../stores/todos.js';
  
  let { todo } = $props();
  let editing = $state(false);
  let editText = $state(todo.text);
  
  function startEdit() {
    editing = true;
    editText = todo.text;
  }
  
  function saveEdit() {
    if (editText.trim()) {
      todos.edit(todo.id, editText);
    }
    editing = false;
  }
  
  function cancelEdit() {
    editing = false;
    editText = todo.text;
  }
  
  function handleKeydown(event) {
    if (event.key === 'Enter') {
      saveEdit();
    } else if (event.key === 'Escape') {
      cancelEdit();
    }
  }
</script>

<li class="todo-item" class:completed={todo.completed}>
  <input 
    type="checkbox" 
    checked={todo.completed}
    onchange={() => todos.toggle(todo.id)}
  />
  
  {#if editing}
    <input 
      type="text" 
      bind:value={editText}
      onblur={saveEdit}
      onkeydown={handleKeydown}
      class="edit-input"
    />
  {:else}
    <span 
      class="todo-text" 
      ondblclick={startEdit}
    >
      {todo.text}
    </span>
  {/if}
  
  <div class="actions">
    {#if !editing}
      <button onclick={startEdit} class="edit-btn">✏️</button>
    {/if}
    <button onclick={() => todos.remove(todo.id)} class="delete-btn">🗑️</button>
  </div>
</li>

<style>
  .todo-item {
    display: flex;
    align-items: center;
    padding: 0.75rem;
    border-bottom: 1px solid #eee;
    gap: 0.75rem;
  }
  
  .completed .todo-text {
    text-decoration: line-through;
    opacity: 0.6;
  }
  
  .todo-text {
    flex: 1;
    cursor: pointer;
  }
  
  .edit-input {
    flex: 1;
    padding: 0.5rem;
    border: 1px solid #007acc;
    border-radius: 4px;
  }
  
  .actions {
    display: flex;
    gap: 0.5rem;
  }
  
  button {
    background: none;
    border: none;
    cursor: pointer;
    padding: 0.25rem;
    border-radius: 4px;
  }
  
  button:hover {
    background: #f0f0f0;
  }
</style>
```

### Componente TodoForm (src/lib/components/TodoForm.svelte)
```svelte
<script>
  import { todos } from '../stores/todos.js';
  
  let newTodoText = $state('');
  
  function addTodo() {
    if (newTodoText.trim()) {
      todos.add(newTodoText);
      newTodoText = '';
    }
  }
  
  function handleKeydown(event) {
    if (event.key === 'Enter') {
      addTodo();
    }
  }
</script>

<div class="todo-form">
  <input 
    type="text" 
    bind:value={newTodoText}
    onkeydown={handleKeydown}
    placeholder="¿Qué necesitas hacer?"
    class="todo-input"
  />
  <button onclick={addTodo} disabled={!newTodoText.trim()}>
    Agregar
  </button>
</div>

<style>
  .todo-form {
    display: flex;
    gap: 0.75rem;
    margin-bottom: 1.5rem;
  }
  
  .todo-input {
    flex: 1;
    padding: 0.75rem;
    border: 1px solid #ddd;
    border-radius: 4px;
    font-size: 1rem;
  }
  
  button {
    padding: 0.75rem 1.5rem;
    background: #007acc;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }
  
  button:disabled {
    opacity: 0.6;
    cursor: not-allowed;
  }
</style>
```

### Aplicación principal (src/routes/+page.svelte)
```svelte
<script>
  import { onMount } from 'svelte';
  import { todos } from '$lib/stores/todos.js';
  import TodoForm from '$lib/components/TodoForm.svelte';
  import TodoItem from '$lib/components/TodoItem.svelte';
  
  let filter = $state('all'); // 'all', 'active', 'completed'
  
  // Cargar datos al montar el componente
  onMount(() => {
    todos.load();
  });
  
  // Guardar automáticamente cuando cambien los todos
  $effect(() => {
    todos.save($todos);
  });
  
  // Calcular estadísticas
  $derived totalTodos = $todos.length;
  $derived completedTodos = $todos.filter(todo => todo.completed).length;
  $derived activeTodos = totalTodos - completedTodos;
  
  // Filtrar todos según el filtro activo
  $derived filteredTodos = $todos.filter(todo => {
    if (filter === 'active') return !todo.completed;
    if (filter === 'completed') return todo.completed;
    return true;
  });
</script>

<svelte:head>
  <title>Lista de Tareas - Svelte 5</title>
</svelte:head>

<div class="app">
  <header>
    <h1>📝 Mi Lista de Tareas</h1>
  </header>
  
  <main>
    <TodoForm />
    
    {#if totalTodos > 0}
      <div class="filters">
        <button 
          class:active={filter === 'all'}
          onclick={() => filter = 'all'}
        >
          Todas ({totalTodos})
        </button>
        <button 
          class:active={filter === 'active'}
          onclick={() => filter = 'active'}
        >
          Activas ({activeTodos})
        </button>
        <button 
          class:active={filter === 'completed'}
          onclick={() => filter = 'completed'}
        >
          Completadas ({completedTodos})
        </button>
      </div>
      
      <ul class="todo-list">
        {#each filteredTodos as todo (todo.id)}
          <TodoItem {todo} />
        {/each}
      </ul>
      
      {#if completedTodos > 0}
        <div class="actions">
          <button onclick={todos.clearCompleted} class="clear-btn">
            Limpiar completadas ({completedTodos})
          </button>
        </div>
      {/if}
    {:else}
      <div class="empty-state">
        <p>¡No hay tareas! Agrega una para comenzar.</p>
      </div>
    {/if}
  </main>
</div>

<style>
  .app {
    max-width: 600px;
    margin: 0 auto;
    padding: 2rem;
    font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
  }
  
  header {
    text-align: center;
    margin-bottom: 2rem;
  }
  
  h1 {
    color: #333;
    margin: 0;
  }
  
  .filters {
    display: flex;
    gap: 0.5rem;
    margin-bottom: 1rem;
    justify-content: center;
  }
  
  .filters button {
    padding: 0.5rem 1rem;
    border: 1px solid #ddd;
    background: white;
    cursor: pointer;
    border-radius: 4px;
  }
  
  .filters button.active {
    background: #007acc;
    color: white;
    border-color: #007acc;
  }
  
  .todo-list {
    list-style: none;
    padding: 0;
    margin: 0;
    background: white;
    border-radius: 8px;
    box-shadow: 0 2px 8px rgba(0,0,0,0.1);
  }
  
  .actions {
    text-align: center;
    margin-top: 1rem;
  }
  
  .clear-btn {
    background: #e74c3c;
    color: white;
    padding: 0.5rem 1rem;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }
  
  .empty-state {
    text-align: center;
    padding: 3rem;
    color: #666;
  }
</style>
```

## Resumen de la Parte 2

En esta parte has aprendido:

1. **Routing con SvelteKit**: Estructura de carpetas, layouts, navegación
2. **Formularios**: Validación, diferentes tipos de input, manejo de eventos
3. **Primera aplicación completa**: Lista de tareas con gestión de estado, componentes reutilizables y persistencia
