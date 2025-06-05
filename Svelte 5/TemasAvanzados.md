# Tutorial Svelte 5 - Parte 3: Temas Avanzados

## 3.1a Stores Personalizados Avanzados

### Store de Autenticación
```javascript
// stores/auth.js
import { writable } from 'svelte/store';

function createAuthStore() {
  const { subscribe, set, update } = writable({
    user: null,
    token: null,
    loading: false,
    error: null
  });

  return {
    subscribe,
    
    async login(email, password) {
      update(state => ({ ...state, loading: true, error: null }));
      
      try {
        // Simular llamada API
        await new Promise(resolve => setTimeout(resolve, 1000));
        
        if (email === 'user@example.com' && password === 'password') {
          const userData = {
            user: { id: 1, name: 'Usuario', email },
            token: 'abc123'
          };
          
          set({
            user: userData.user,
            token: userData.token,
            loading: false,
            error: null
          });
          
          localStorage.setItem('auth_token', userData.token);
        } else {
          throw new Error('Credenciales inválidas');
        }
        
      } catch (error) {
        update(state => ({
          ...state,
          loading: false,
          error: error.message
        }));
      }
    },
    
    logout() {
      set({ user: null, token: null, loading: false, error: null });
      localStorage.removeItem('auth_token');
    },
    
    clearError() {
      update(state => ({ ...state, error: null }));
    }
  };
}

export const auth = createAuthStore();
```

### Uso del Store de Autenticación
```svelte
<script>
  import { auth } from './stores/auth.js';
  
  let email = $state('');
  let password = $state('');
  
  async function handleLogin() {
    await auth.login(email, password);
  }
</script>

<div class="auth-container">
  {#if $auth.user}
    <div class="welcome">
      <h2>¡Bienvenido, {$auth.user.name}!</h2>
      <button onclick={auth.logout}>Cerrar Sesión</button>
    </div>
  {:else}
    <form onsubmit={handleLogin}>
      <h2>Iniciar Sesión</h2>
      
      {#if $auth.error}
        <div class="error">
          {$auth.error}
          <button type="button" onclick={auth.clearError}>×</button>
        </div>
      {/if}
      
      <input 
        type="email" 
        bind:value={email}
        placeholder="Email"
        disabled={$auth.loading}
      />
      
      <input 
        type="password" 
        bind:value={password}
        placeholder="Contraseña"
        disabled={$auth.loading}
      />
      
      <button type="submit" disabled={$auth.loading}>
        {$auth.loading ? 'Cargando...' : 'Entrar'}
      </button>
      
      <p class="hint">
        Prueba: user@example.com / password
      </p>
    </form>
  {/if}
</div>

<style>
  .auth-container {
    max-width: 400px;
    margin: 2rem auto;
    padding: 2rem;
    border: 1px solid #ddd;
    border-radius: 8px;
  }
  
  .error {
    background: #fee;
    color: #c33;
    padding: 0.75rem;
    border-radius: 4px;
    margin-bottom: 1rem;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  
  .welcome {
    text-align: center;
  }
  
  input, button {
    width: 100%;
    padding: 0.75rem;
    margin-bottom: 1rem;
    border: 1px solid #ddd;
    border-radius: 4px;
  }
  
  button {
    background: #007acc;
    color: white;
    cursor: pointer;
  }
  
  .hint {
    font-size: 0.875rem;
    color: #666;
    text-align: center;
  }
</style>
```

## 3.1b Derived Stores y Context API

### Derived Stores para Estado Calculado
```javascript
// stores/cart.js
import { writable, derived } from 'svelte/store';

export const cartItems = writable([]);

export const cartTotal = derived(
  cartItems,
  $items => $items.reduce((total, item) => total + (item.price * item.quantity), 0)
);

export const cartCount = derived(
  cartItems,
  $items => $items.reduce((count, item) => count + item.quantity, 0)
);

export const cartSummary = derived(
  [cartItems, cartTotal, cartCount],
  ([$items, $total, $count]) => ({
    items: $items,
    total: $total,
    count: $count,
    isEmpty: $count === 0
  })
);
```

### Context API para Compartir Estado
```svelte
<!-- App.svelte -->
<script>
  import { setContext } from 'svelte';
  import { writable } from 'svelte/store';
  import ThemeProvider from './ThemeProvider.svelte';
  import NotificationProvider from './NotificationProvider.svelte';
  
  // Crear contextos globales
  const theme = writable('light');
  const notifications = writable([]);
  
  setContext('theme', theme);
  setContext('notifications', notifications);
</script>

<ThemeProvider>
  <NotificationProvider>
    <main>
      <h1>Mi Aplicación</h1>
      <slot />
    </main>
  </NotificationProvider>
</ThemeProvider>
```

### Componente que usa Context
```svelte
<!-- ComponenteHijo.svelte -->
<script>
  import { getContext } from 'svelte';
  
  const theme = getContext('theme');
  const notifications = getContext('notifications');
  
  function toggleTheme() {
    theme.update(current => current === 'light' ? 'dark' : 'light');
  }
  
  function showNotification() {
    notifications.update(n => [...n, {
      id: Date.now(),
      message: '¡Notificación desde componente hijo!',
      type: 'info'
    }]);
  }
</script>

<div class="controls" class:dark={$theme === 'dark'}>
  <button onclick={toggleTheme}>
    Cambiar a tema {$theme === 'light' ? 'oscuro' : 'claro'}
  </button>
  
  <button onclick={showNotification}>
    Mostrar Notificación
  </button>
  
  <p>Notificaciones: {$notifications.length}</p>
</div>

<style>
  .controls {
    padding: 1rem;
    background: white;
    border-radius: 8px;
  }
  
  .controls.dark {
    background: #333;
    color: white;
  }
  
  button {
    margin-right: 1rem;
    padding: 0.5rem 1rem;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }
</style>
```

## 3.2a Lazy Loading de Componentes

### Carga Perezosa Básica
```svelte
<script>
  let HeavyComponent = $state(null);
  let loading = $state(false);
  let showComponent = $state(false);
  
  async function loadComponent() {
    if (HeavyComponent) {
      showComponent = !showComponent;
      return;
    }
    
    loading = true;
    
    try {
      // Simular carga lenta
      await new Promise(resolve => setTimeout(resolve, 1000));
      
      const module = await import('./HeavyComponent.svelte');
      HeavyComponent = module.default;
      showComponent = true;
    } catch (error) {
      console.error('Error cargando componente:', error);
    } finally {
      loading = false;
    }
  }
</script>

<div class="lazy-loader">
  <button onclick={loadComponent} disabled={loading}>
    {#if loading}
      Cargando...
    {:else if HeavyComponent}
      {showComponent ? 'Ocultar' : 'Mostrar'} Componente
    {:else}
      Cargar Componente Pesado
    {/if}
  </button>
  
  {#if loading}
    <div class="loading">
      <div class="spinner"></div>
      <p>Cargando componente...</p>
    </div>
  {/if}
  
  {#if showComponent && HeavyComponent}
    <div class="component-container">
      <svelte:component this={HeavyComponent} />
    </div>
  {/if}
</div>

<style>
  .lazy-loader {
    padding: 2rem;
  }
  
  .loading {
    text-align: center;
    padding: 2rem;
  }
  
  .spinner {
    width: 40px;
    height: 40px;
    border: 4px solid #f3f3f3;
    border-top: 4px solid #007acc;
    border-radius: 50%;
    animation: spin 1s linear infinite;
    margin: 0 auto 1rem;
  }
  
  @keyframes spin {
    0% { transform: rotate(0deg); }
    100% { transform: rotate(360deg); }
  }
  
  .component-container {
    margin-top: 2rem;
    padding: 1rem;
    border: 1px solid #ddd;
    border-radius: 8px;
  }
</style>
```

### HeavyComponent.svelte (ejemplo)
```svelte
<script>
  import { onMount } from 'svelte';
  
  let data = $state([]);
  let loading = $state(true);
  
  onMount(async () => {
    // Simular carga de datos pesados
    await new Promise(resolve => setTimeout(resolve, 2000));
    
    data = Array.from({ length: 1000 }, (_, i) => ({
      id: i,
      name: `Item ${i}`,
      value: Math.random() * 100
    }));
    
    loading = false;
  });
</script>

<div class="heavy-component">
  <h3>🚀 Componente Pesado Cargado</h3>
  
  {#if loading}
    <p>Cargando datos...</p>
  {:else}
    <p>Datos cargados: {data.length} elementos</p>
    
    <div class="data-preview">
      {#each data.slice(0, 10) as item}
        <div class="item">
          {item.name}: {item.value.toFixed(2)}
        </div>
      {/each}
      <p>... y {data.length - 10} elementos más</p>
    </div>
  {/if}
</div>

<style>
  .heavy-component {
    background: #f8f9fa;
    padding: 1.5rem;
    border-radius: 8px;
  }
  
  .data-preview {
    max-height: 200px;
    overflow-y: auto;
    border: 1px solid #dee2e6;
    border-radius: 4px;
    padding: 1rem;
    margin-top: 1rem;
  }
  
  .item {
    padding: 0.25rem 0;
    border-bottom: 1px solid #eee;
  }
</style>
```

## 3.2b Virtualización de Listas

### Lista Virtual Simple
```svelte
<script>
  let { 
    items = [], 
    itemHeight = 50, 
    containerHeight = 400,
    overscan = 5 
  } = $props();
  
  let scrollTop = $state(0);
  let containerRef = $state();
  
  // Calcular elementos visibles
  $derived startIndex = Math.max(0, Math.floor(scrollTop / itemHeight) - overscan);
  $derived endIndex = Math.min(
    items.length - 1,
    Math.floor((scrollTop + containerHeight) / itemHeight) + overscan
  );
  $derived visibleItems = items.slice(startIndex, endIndex + 1);
  $derived offsetY = startIndex * itemHeight;
  $derived totalHeight = items.length * itemHeight;
  
  function handleScroll(event) {
    scrollTop = event.target.scrollTop;
  }
</script>

<div 
  bind:this={containerRef}
  class="virtual-list"
  style="height: {containerHeight}px"
  onscroll={handleScroll}
>
  <div class="virtual-list-inner" style="height: {totalHeight}px">
    <div class="virtual-list-items" style="transform: translateY({offsetY}px)">
      {#each visibleItems as item, index (item.id || startIndex + index)}
        <div class="virtual-item" style="height: {itemHeight}px">
          <slot {item} index={startIndex + index} />
        </div>
      {/each}
    </div>
  </div>
</div>

<style>
  .virtual-list {
    overflow-y: auto;
    border: 1px solid #ddd;
    border-radius: 4px;
  }
  
  .virtual-list-inner {
    position: relative;
  }
  
  .virtual-list-items {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
  }
  
  .virtual-item {
    display: flex;
    align-items: center;
    padding: 0 1rem;
    border-bottom: 1px solid #eee;
  }
</style>
```

### Uso de la Lista Virtual
```svelte
<script>
  // Generar datos de prueba
  const items = Array.from({ length: 10000 }, (_, i) => ({
    id: i,
    name: `Elemento ${i}`,
    description: `Descripción del elemento número ${i}`,
    value: Math.floor(Math.random() * 1000)
  }));
</script>

<div class="demo">
  <h2>Lista Virtual - 10,000 elementos</h2>
  
  <VirtualList {items} itemHeight={60} containerHeight={400}>
    {#snippet children({ item, index })}
      <div class="item-content">
        <div class="item-header">
          <strong>{item.name}</strong>
          <span class="item-value">${item.value}</span>
        </div>
        <div class="item-description">
          {item.description}
        </div>
        <div class="item-index">
          Posición: {index}
        </div>
      </div>
    {/snippet}
  </VirtualList>
</div>

<style>
  .demo {
    max-width: 600px;
    margin: 2rem auto;
    padding: 1rem;
  }
  
  .item-content {
    flex: 1;
  }
  
  .item-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 0.25rem;
  }
  
  .item-value {
    color: #007acc;
    font-weight: bold;
  }
  
  .item-description {
    color: #666;
    font-size: 0.875rem;
    margin-bottom: 0.25rem;
  }
  
  .item-index {
    font-size: 0.75rem;
    color: #999;
  }
</style>
```

## 3.3a Transiciones Básicas

### Transiciones Incorporadas
```svelte
<script>
  import { fade, fly, slide, scale } from 'svelte/transition';
  import { quintOut } from 'svelte/easing';
  
  let showContent = $state(false);
  let currentTransition = $state('fade');
  
  const transitions = {
    fade: { transition: fade, props: { duration: 300 } },
    fly: { transition: fly, props: { y: 200, duration: 300 } },
    slide: { transition: slide, props: { duration: 300 } },
    scale: { transition: scale, props: { duration: 300, easing: quintOut } }
  };
</script>

<div class="transition-demo">
  <div class="controls">
    <button onclick={() => showContent = !showContent}>
      {showContent ? 'Ocultar' : 'Mostrar'}
    </button>
    
    <select bind:value={currentTransition}>
      {#each Object.keys(transitions) as type}
        <option value={type}>{type}</option>
      {/each}
    </select>
  </div>
  
  {#if showContent}
    <div 
      transition:transitions[currentTransition].transition={transitions[currentTransition].props}
      class="content"
    >
      <h3>¡Contenido con transición!</h3>
      <p>Esta caja aparece y desaparece con la transición: <strong>{currentTransition}</strong></p>
    </div>
  {/if}
</div>

<style>
  .transition-demo {
    padding: 2rem;
    max-width: 500px;
    margin: 0 auto;
  }
  
  .controls {
    display: flex;
    gap: 1rem;
    margin-bottom: 2rem;
  }
  
  button, select {
    padding: 0.5rem 1rem;
    border: 1px solid #ddd;
    border-radius: 4px;
    cursor: pointer;
  }
  
  .content {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    color: white;
    padding: 2rem;
    border-radius: 8px;
    text-align: center;
  }
</style>
```

### Transiciones Direccionales
```svelte
<script>
  import { fly } from 'svelte/transition';
  
  let items = $state([
    { id: 1, text: 'Elemento 1' },
    { id: 2, text: 'Elemento 2' },
    { id: 3, text: 'Elemento 3' }
  ]);
  
  function addItem() {
    const newId = Math.max(...items.map(i => i.id)) + 1;
    items = [...items, { id: newId, text: `Elemento ${newId}` }];
  }
  
  function removeItem(id) {
    items = items.filter(item => item.id !== id);
  }
</script>

<div class="list-demo">
  <button onclick={addItem}>Agregar Elemento</button>
  
  <ul class="animated-list">
    {#each items as item (item.id)}
      <li 
        in:fly={{ x: -200, duration: 300 }}
        out:fly={{ x: 200, duration: 300 }}
        class="list-item"
      >
        <span>{item.text}</span>
        <button onclick={() => removeItem(item.id)}>×</button>
      </li>
    {/each}
  </ul>
</div>

<style>
  .list-demo {
    max-width: 400px;
    margin: 2rem auto;
    padding: 1rem;
  }
  
  .animated-list {
    list-style: none;
    padding: 0;
    margin: 1rem 0;
  }
  
  .list-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0.75rem;
    margin-bottom: 0.5rem;
    background: #f8f9fa;
    border-radius: 4px;
    border: 1px solid #dee2e6;
  }
  
  .list-item button {
    background: #dc3545;
    color: white;
    border: none;
    border-radius: 50%;
    width: 24px;
    height: 24px;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
  }
</style>
```

## 3.3b Transiciones Personalizadas

### Transición de Escritura
```svelte
<script>
  let showText = $state(false);
  
  function typewriter(node, { speed = 1 }) {
    const valid = node.childNodes.length === 1 && 
                  node.childNodes[0].nodeType === Node.TEXT_NODE;
    
    if (!valid) {
      return { duration: 0 };
    }
    
    const text = node.textContent;
    const duration = text.length / (speed * 0.01);
    
    return {
      duration,
      tick: t => {
        const i = Math.trunc(text.length * t);
        node.textContent = text.slice(0, i);
      }
    };
  }
  
  function draw(node, { duration = 800 }) {
    const len = node.getTotalLength();
    
    return {
      duration,
      css: t => `
        stroke-dasharray: ${len};
        stroke-dashoffset: ${len * (1 - t)};
      `
    };
  }
</script>

<div class="custom-transitions">
  <button onclick={() => showText = !showText}>
    {showText ? 'Ocultar' : 'Mostrar'} Texto
  </button>
  
  {#if showText}
    <h2 transition:typewriter={{ speed: 2 }}>
      ¡Hola, mundo desde Svelte 5!
    </h2>
    
    <svg viewBox="0 0 200 100" class="drawing">
      <path 
        d="M 10 80 Q 100 10 190 80" 
        stroke="#007acc" 
        stroke-width="3" 
        fill="none"
        transition:draw={{ duration: 1000 }}
      />
    </svg>
  {/if}
</div>

<style>
  .custom-transitions {
    text-align: center;
    padding: 2rem;
  }
  
  h2 {
    color: #007acc;
    font-size: 2rem;
    margin: 2rem 0;
    min-height: 3rem;
  }
  
  .drawing {
    width: 200px;
    height: 100px;
    margin: 2rem auto;
    display: block;
  }
</style>
```

### Transición de Transformación
```svelte
<script>
  let expanded = $state(false);
  
  function morph(node, { duration = 400 }) {
    const style = getComputedStyle(node);
    const transform = style.transform === 'none' ? '' : style.transform;
    
    return {
      duration,
      css: t => {
        const scale = 1 + (Math.sin(t * Math.PI) * 0.3);
        const rotate = Math.sin(t * Math.PI * 2) * 360;
        
        return `
          transform: ${transform} scale(${scale}) rotate(${rotate}deg);
          filter: hue-rotate(${t * 360}deg);
        `;
      }
    };
  }
</script>

<div class="morph-demo">
  <button onclick={() => expanded = !expanded}>
    Transformar
  </button>
  
  {#if expanded}
    <div transition:morph={{ duration: 1000 }} class="morphing-box">
      <span>🎨</span>
    </div>
  {/if}
</div>

<style>
  .morph-demo {
    text-align: center;
    padding: 3rem;
  }
  
  .morphing-box {
    width: 100px;
    height: 100px;
    background: linear-gradient(45deg, #ff6b6b, #4ecdc4);
    border-radius: 8px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 2rem;
    margin: 2rem auto;
  }
</style>
```

## 3.4a Animaciones con flip

### Lista Reordenable
```svelte
<script>
  import { flip } from 'svelte/animate';
  import { fade } from 'svelte/transition';
  
  let items = $state([
    { id: 1, name: 'Manzana', color: '#ff4757' },
    { id: 2, name: 'Naranja', color: '#ff7f50' },
    { id: 3, name: 'Plátano', color: '#ffd700' },
    { id: 4, name: 'Uva', color: '#9b59b6' },
    { id: 5, name: 'Fresa', color: '#e74c3c' }
  ]);
  
  function shuffle() {
    items = items.sort(() => Math.random() - 0.5);
  }
  
  function moveUp(index) {
    if (index > 0) {
      [items[index], items[index - 1]] = [items[index - 1], items[index]];
      items = [...items];
    }
  }
  
  function moveDown(index) {
    if (index < items.length - 1) {
      [items[index], items[index + 1]] = [items[index + 1], items[index]];
      items = [...items];
    }
  }
  
  function removeItem(id) {
    items = items.filter(item => item.id !== id);
  }
</script>

<div class="flip-demo">
  <div class="controls">
    <button onclick={shuffle}>🔀 Mezclar</button>
  </div>
  
  <ul class="flip-list">
    {#each items as item, index (item.id)}
      <li 
        animate:flip={{ duration: 300 }}
        transition:fade={{ duration: 200 }}
        class="flip-item"
        style="background-color: {item.color}20; border-left: 4px solid {item.color}"
      >
        <span class="item-name">{item.name}</span>
        
        <div class="item-controls">
          <button onclick={() => moveUp(index)} disabled={index === 0}>
            ⬆️
          </button>
          <button onclick={() => moveDown(index)} disabled={index === items.length - 1}>
            ⬇️
          </button>
          <button onclick={() => removeItem(item.id)} class="remove-btn">
            🗑️
          </button>
        </div>
      </li>
    {/each}
  </ul>
</div>

<style>
  .flip-demo {
    max-width: 400px;
    margin: 2rem auto;
    padding: 1rem;
  }
  
  .controls {
    text-align: center;
    margin-bottom: 1rem;
  }
  
  .flip-list {
    list-style: none;
    padding: 0;
    margin: 0;
  }
  
  .flip-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 1rem;
    margin-bottom: 0.5rem;
    border-radius: 8px;
    background: white;
    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  }
  
  .item-name {
    font-weight: bold;
    font-size: 1.1rem;
  }
  
  .item-controls {
    display: flex;
    gap: 0.5rem;
  }
  
  .item-controls button {
    background: none;
    border: 1px solid #ddd;
    border-radius: 4px;
    padding: 0.25rem 0.5rem;
    cursor: pointer;
    font-size: 0.875rem;
  }
  
  .item-controls button:hover:not(:disabled) {
    background: #f0f0f0;
  }
  
  .item-controls button:disabled {
    opacity: 0.5;
    cursor: not-allowed;
  }
  
  .remove-btn {
    background: #fee !important;
    border-color: #fcc !important;
  }
</style>
```

