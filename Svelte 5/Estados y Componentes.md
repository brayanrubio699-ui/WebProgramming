# Tutorial Svelte 5 - Parte 2.1: Estado Avanzado y Comunicación entre Componentes

## Manejo de Estado Avanzado en Svelte 5

### 1. Runes en profundidad

#### $state - Estado reactivo
```svelte
<script>
	// Estado primitivo
	let count = $state(0);
	
	// Estado de objetos
	let user = $state({
		name: 'Juan',
		email: 'juan@example.com',
		preferences: {
			theme: 'dark',
			notifications: true
		}
	});
	
	// Estado de arrays
	let todos = $state([
		{ id: 1, text: 'Aprender Svelte 5', done: false },
		{ id: 2, text: 'Construir una app', done: false }
	]);
	
	function updateUserName(newName) {
		user.name = newName; // Reactivo automáticamente
	}
	
	function addTodo(text) {
		todos.push({
			id: Date.now(),
			text,
			done: false
		});
	}
</script>

<div>
	<h2>Usuario: {user.name}</h2>
	<p>Email: {user.email}</p>
	<p>Tema: {user.preferences.theme}</p>
	
	<button on:click={() => updateUserName('María')}>
		Cambiar nombre
	</button>
</div>
```

#### $derived - Valores computados
```svelte
<script>
	let items = $state([
		{ name: 'Manzana', price: 1.5, quantity: 3 },
		{ name: 'Banana', price: 0.8, quantity: 5 },
		{ name: 'Naranja', price: 2.0, quantity: 2 }
	]);
	
	// Valor derivado simple
	let totalItems = $derived(items.length);
	
	// Valor derivado complejo
	let totalPrice = $derived(
		items.reduce((sum, item) => sum + (item.price * item.quantity), 0)
	);
	
	// Valor derivado con lógica condicional
	let expensiveItems = $derived(
		items.filter(item => item.price > 1.0)
	);
	
	// Derivado que depende de otro derivado
	let averagePrice = $derived(
		totalItems > 0 ? totalPrice / totalItems : 0
	);
	
	function addItem() {
		items.push({
			name: 'Nuevo Item',
			price: Math.random() * 3,
			quantity: Math.floor(Math.random() * 5) + 1
		});
	}
</script>

<div class="shopping-cart">
	<h3>Carrito de Compras</h3>
	
	<div class="stats">
		<p>Total items: {totalItems}</p>
		<p>Total price: ${totalPrice.toFixed(2)}</p>
		<p>Average price: ${averagePrice.toFixed(2)}</p>
		<p>Expensive items: {expensiveItems.length}</p>
	</div>
	
	<button on:click={addItem}>Agregar Item</button>
	
	<ul>
		{#each items as item, i}
			<li>
				{item.name} - ${item.price} x {item.quantity} = ${(item.price * item.quantity).toFixed(2)}
			</li>
		{/each}
	</ul>
</div>

<style>
	.shopping-cart {
		padding: 1rem;
		border: 1px solid #ccc;
		border-radius: 8px;
		margin: 1rem 0;
	}
	
	.stats {
		background: #f5f5f5;
		padding: 1rem;
		border-radius: 4px;
		margin: 1rem 0;
	}
	
	.stats p {
		margin: 0.5rem 0;
	}
</style>
```

#### $effect - Efectos secundarios
```svelte
<script>
	let searchTerm = $state('');
	let results = $state([]);
	let loading = $state(false);
	let error = $state(null);
	
	// Efecto que se ejecuta cuando cambia searchTerm
	$effect(() => {
		if (searchTerm.length < 3) {
			results = [];
			error = null;
			return;
		}
		
		loading = true;
		error = null;
		
		// Simular API call con delay
		const timeoutId = setTimeout(async () => {
			try {
				// Simulamos una búsqueda
				const mockResults = [
					`Resultado para "${searchTerm}" #1`,
					`Resultado para "${searchTerm}" #2`,
					`Resultado para "${searchTerm}" #3`
				];
				
				results = mockResults;
			} catch (err) {
				error = 'Error en la búsqueda';
				results = [];
			} finally {
				loading = false;
			}
		}, 500);
		
		// Cleanup function - se ejecuta cuando el efecto se vuelve a ejecutar
		return () => {
			clearTimeout(timeoutId);
		};
	});
	
	// Efecto para logging (solo para desarrollo)
	$effect(() => {
		console.log(`Search term changed to: "${searchTerm}"`);
		console.log(`Results count: ${results.length}`);
	});
</script>

<div class="search-container">
	<h3>Buscador Reactivo</h3>
	
	<input
		bind:value={searchTerm}
		placeholder="Escribe al menos 3 caracteres..."
		class="search-input"
	/>
	
	{#if loading}
		<p class="loading">Buscando...</p>
	{/if}
	
	{#if error}
		<p class="error">{error}</p>
	{/if}
	
	{#if results.length > 0}
		<ul class="results">
			{#each results as result}
				<li>{result}</li>
			{/each}
		</ul>
	{/if}
</div>

<style>
	.search-container {
		padding: 1rem;
		border: 1px solid #ddd;
		border-radius: 8px;
		margin: 1rem 0;
	}
	
	.search-input {
		width: 100%;
		padding: 0.5rem;
		border: 1px solid #ccc;
		border-radius: 4px;
		margin-bottom: 1rem;
	}
	
	.loading {
		color: #007acc;
		font-style: italic;
	}
	
	.error {
		color: #dc3545;
		font-weight: bold;
	}
	
	.results {
		list-style: none;
		padding: 0;
	}
	
	.results li {
		padding: 0.5rem;
		border-bottom: 1px solid #eee;
	}
</style>
```

### 2. Stores para estado global

Los stores son perfectos para manejar estado que necesita ser compartido entre múltiples componentes.

**src/lib/stores/userStore.js**
```javascript
import { writable, derived } from 'svelte/store';

// Store principal del usuario
export const userStore = writable({
	isAuthenticated: false,
	user: null,
	preferences: {
		theme: 'light',
		language: 'es',
		notifications: true
	}
});

// Store derivado para verificar si es admin
export const isAdmin = derived(
	userStore,
	$user => $user.user?.role === 'admin'
);

// Store derivado para el tema actual
export const currentTheme = derived(
	userStore,
	$user => $user.preferences.theme
);

// Acciones para el usuario
export const authActions = {
	login: (userData) => {
		userStore.update(state => ({
			...state,
			isAuthenticated: true,
			user: userData
		}));
	},
	
	logout: () => {
		userStore.update(state => ({
			...state,
			isAuthenticated: false,
			user: null
		}));
	},
	
	updatePreferences: (newPreferences) => {
		userStore.update(state => ({
			...state,
			preferences: { ...state.preferences, ...newPreferences }
		}));
	},
	
	updateProfile: (profileData) => {
		userStore.update(state => ({
			...state,
			user: { ...state.user, ...profileData }
		}));
	}
};
```

**src/lib/stores/notificationStore.js**
```javascript
import { writable } from 'svelte/store';

function createNotificationStore() {
	const { subscribe, update } = writable([]);
	
	return {
		subscribe,
		
		add: (message, type = 'info', duration = 5000) => {
			const id = Date.now();
			const notification = {
				id,
				message,
				type, // 'success', 'error', 'warning', 'info'
				duration
			};
			
			update(notifications => [...notifications, notification]);
			
			// Auto-remove después del duration
			if (duration > 0) {
				setTimeout(() => {
					update(notifications => 
						notifications.filter(n => n.id !== id)
					);
				}, duration);
			}
		},
		
		remove: (id) => {
			update(notifications => 
				notifications.filter(n => n.id !== id)
			);
		},
		
		clear: () => {
			update(() => []);
		}
	};
}

export const notifications = createNotificationStore();
```

**Uso de stores en componentes:**
```svelte
<script>
	import { userStore, authActions, isAdmin } from '$lib/stores/userStore.js';
	import { notifications } from '$lib/stores/notificationStore.js';
	
	// Acceso reactivo a los stores
	let user = $derived($userStore);
	let userIsAdmin = $derived($isAdmin);
	
	function handleLogin() {
		const userData = {
			id: 1,
			name: 'Juan Pérez',
			email: 'juan@example.com',
			role: 'user' // o 'admin'
		};
		
		authActions.login(userData);
		notifications.add('¡Bienvenido!', 'success');
	}
	
	function handleLogout() {
		authActions.logout();
		notifications.add('Sesión cerrada', 'info');
	}
	
	function toggleTheme() {
		const newTheme = user.preferences.theme === 'light' ? 'dark' : 'light';
		authActions.updatePreferences({ theme: newTheme });
	}
</script>

<div class="user-panel">
	{#if user.isAuthenticated}
		<div class="user-info">
			<h3>¡Hola, {user.user.name}!</h3>
			<p>Email: {user.user.email}</p>
			{#if userIsAdmin}
				<span class="admin-badge">Admin</span>
			{/if}
		</div>
		
		<div class="user-actions">
			<button on:click={toggleTheme}>
				Cambiar a tema {user.preferences.theme === 'light' ? 'oscuro' : 'claro'}
			</button>
			<button on:click={handleLogout}>Cerrar sesión</button>
		</div>
	{:else}
		<button on:click={handleLogin}>Iniciar sesión</button>
	{/if}
</div>

<style>
	.user-panel {
		padding: 1rem;
		border: 1px solid #ddd;
		border-radius: 8px;
		margin: 1rem 0;
	}
	
	.user-info {
		margin-bottom: 1rem;
	}
	
	.admin-badge {
		background: #dc3545;
		color: white;
		padding: 0.25rem 0.5rem;
		border-radius: 12px;
		font-size: 0.75rem;
		font-weight: bold;
	}
	
	.user-actions {
		display: flex;
		gap: 0.5rem;
	}
	
	button {
		padding: 0.5rem 1rem;
		border: 1px solid #007acc;
		background: white;
		color: #007acc;
		border-radius: 4px;
		cursor: pointer;
	}
	
	button:hover {
		background: #007acc;
		color: white;
	}
</style>
```

## Comunicación entre Componentes

### 1. Props (Padre a Hijo)

Los props son la forma principal de pasar datos de componentes padre a hijo.

```svelte
<!-- Parent.svelte -->
<script>
	import UserCard from './UserCard.svelte';
	import ProductList from './ProductList.svelte';
	
	let users = $state([
		{ id: 1, name: 'Juan', email: 'juan@example.com', avatar: '👨' },
		{ id: 2, name: 'María', email: 'maria@example.com', avatar: '👩' },
		{ id: 3, name: 'Carlos', email: 'carlos@example.com', avatar: '👨‍💼' }
	]);
	
	let products = $state([
		{ id: 1, name: 'Laptop', price: 999, category: 'tech' },
		{ id: 2, name: 'Mouse', price: 25, category: 'tech' },
		{ id: 3, name: 'Libro', price: 15, category: 'books' }
	]);
	
	let selectedCategory = $state('all');
</script>

<div class="app-container">
	<h2>Usuarios</h2>
	<div class="users-grid">
		{#each users as user}
			<UserCard 
				{user} 
				showEmail={true}
				theme="light"
			/>
		{/each}
	</div>
	
	<h2>Productos</h2>
	<ProductList 
		{products} 
		category={selectedCategory}
		maxItems={10}
		sortBy="name"
	/>
</div>

<!-- UserCard.svelte -->
<script>
	// Destructuring props con valores por defecto
	let { 
		user, 
		showEmail = false, 
		theme = 'light',
		size = 'medium'
	} = $props();
	
	// Props computados
	let cardClass = $derived(`user-card ${theme} ${size}`);
	let initials = $derived(
		user.name.split(' ').map(n => n[0]).join('').toUpperCase()
	);
</script>

<div class={cardClass}>
	<div class="avatar">
		{user.avatar || initials}
	</div>
	<div class="info">
		<h3>{user.name}</h3>
		{#if showEmail}
			<p class="email">{user.email}</p>
		{/if}
	</div>
</div>

<style>
	.user-card {
		display: flex;
		align-items: center;
		padding: 1rem;
		border-radius: 8px;
		border: 1px solid #ddd;
		margin: 0.5rem;
		transition: transform 0.2s ease;
	}
	
	.user-card:hover {
		transform: translateY(-2px);
		box-shadow: 0 4px 8px rgba(0,0,0,0.1);
	}
	
	.user-card.light {
		background: white;
		color: #333;
	}
	
	.user-card.dark {
		background: #333;
		color: white;
		border-color: #555;
	}
	
	.user-card.small {
		padding: 0.5rem;
	}
	
	.user-card.large {
		padding: 1.5rem;
	}
	
	.avatar {
		width: 3rem;
		height: 3rem;
		border-radius: 50%;
		background: #007acc;
		color: white;
		display: flex;
		align-items: center;
		justify-content: center;
		font-size: 1.5rem;
		margin-right: 1rem;
	}
	
	.info h3 {
		margin: 0 0 0.25rem 0;
	}
	
	.email {
		margin: 0;
		font-size: 0.875rem;
		opacity: 0.7;
	}
</style>

<!-- ProductList.svelte -->
<script>
	let { 
		products, 
		category = 'all',
		maxItems = Infinity,
		sortBy = 'name',
		showPrices = true
	} = $props();
	
	// Filtrar y ordenar productos
	let filteredProducts = $derived(() => {
		let filtered = category === 'all' 
			? products 
			: products.filter(p => p.category === category);
		
		// Ordenar
		filtered.sort((a, b) => {
			if (sortBy === 'price') {
				return a.price - b.price;
			}
			return a.name.localeCompare(b.name);
		});
		
		// Limitar cantidad
		return filtered.slice(0, maxItems);
	});
	
	let totalValue = $derived(
		filteredProducts.reduce((sum, p) => sum + p.price, 0)
	);
</script>

<div class="product-list">
	<div class="list-header">
		<p>
			Mostrando {filteredProducts.length} productos
			{#if showPrices}
				- Valor total: ${totalValue}
			{/if}
		</p>
	</div>
	
	<div class="products-grid">
		{#each filteredProducts as product}
			<div class="product-item">
				<h4>{product.name}</h4>
				<span class="category">{product.category}</span>
				{#if showPrices}
					<p class="price">${product.price}</p>
				{/if}
			</div>
		{/each}
	</div>
</div>

<style>
	.product-list {
		border: 1px solid #ddd;
		border-radius: 8px;
		padding: 1rem;
	}
	
	.list-header {
		border-bottom: 1px solid #eee;
		padding-bottom: 0.5rem;
		margin-bottom: 1rem;
	}
	
	.products-grid {
		display: grid;
		grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
		gap: 1rem;
	}
	
	.product-item {
		padding: 1rem;
		border: 1px solid #eee;
		border-radius: 4px;
		text-align: center;
	}
	
	.category {
		background: #f0f0f0;
		padding: 0.25rem 0.5rem;
		border-radius: 12px;
		font-size: 0.75rem;
		color: #666;
	}
	
	.price {
		font-weight: bold;
		color: #007acc;
		font-size: 1.1rem;
	}
</style>
```

### 2. Events (Hijo a Padre)

Los eventos permiten que los componentes hijos comuniquen cambios a sus padres.

```svelte
<!-- SearchBox.svelte -->
<script>
	import { createEventDispatcher } from 'svelte';
	
	const dispatch = createEventDispatcher();
	
	let { placeholder = 'Buscar...', debounceMs = 300 } = $props();
	
	let searchValue = $state('');
	let timeoutId;
	
	// Dispatch con debounce
	$effect(() => {
		clearTimeout(timeoutId);
		
		timeoutId = setTimeout(() => {
			dispatch('search', {
				query: searchValue,
				timestamp: Date.now()
			});
		}, debounceMs);
		
		return () => clearTimeout(timeoutId);
	});
	
	function handleClear() {
		searchValue = '';
		dispatch('clear');
	}
	
	function handleSubmit() {
		dispatch('submit', {
			query: searchValue,
			timestamp: Date.now()
		});
	}
</script>

<div class="search-box">
	<form on:submit|preventDefault={handleSubmit}>
		<input
			bind:value={searchValue}
			{placeholder}
			class="search-input"
		/>
		
		{#if searchValue}
			<button type="button" on:click={handleClear} class="clear-btn">
				✕
			</button>
		{/if}
		
		<button type="submit" class="search-btn">
			🔍
		</button>
	</form>
</div>

<style>
	.search-box {
		position: relative;
		display: inline-block;
	}
	
	form {
		display: flex;
		align-items: center;
	}
	
	.search-input {
		padding: 0.75rem;
		border: 2px solid #ddd;
		border-radius: 25px;
		outline: none;
		font-size: 1rem;
		min-width: 300px;
	}
	
	.search-input:focus {
		border-color: #007acc;
	}
	
	.clear-btn, .search-btn {
		background: none;
		border: none;
		cursor: pointer;
		padding: 0.5rem;
		margin-left: -3rem;
		border-radius: 50%;
	}
	
	.search-btn {
		margin-left: 0.5rem;
		background: #007acc;
		color: white;
	}
	
	.clear-btn:hover, .search-btn:hover {
		background: #005c99;
		color: white;
	}
</style>

<!-- Parent component usando SearchBox -->
<script>
	import SearchBox from './SearchBox.svelte';
	
	let searchResults = $state([]);
	let isSearching = $state(false);
	let searchHistory = $state([]);
	
	function handleSearch(event) {
		const { query } = event.detail;
		
		if (query.length < 2) {
			searchResults = [];
			return;
		}
		
		isSearching = true;
		
		// Simular búsqueda
		setTimeout(() => {
			searchResults = [
				`Resultado 1 para "${query}"`,
				`Resultado 2 para "${query}"`,
				`Resultado 3 para "${query}"`
			];
			isSearching = false;
		}, 500);
	}
	
	function handleSearchSubmit(event) {
		const { query, timestamp } = event.detail;
		
		// Agregar al historial
		searchHistory.unshift({
			query,
			timestamp,
			id: Date.now()
		});
		
		// Mantener solo las últimas 10 búsquedas
		searchHistory = searchHistory.slice(0, 10);
	}
	
	function handleClear() {
		searchResults = [];
		isSearching = false;
	}
</script>

<div class="search-app">
	<h2>Buscador Avanzado</h2>
	
	<SearchBox
		placeholder="Busca cualquier cosa..."
		debounceMs={500}
		on:search={handleSearch}
		on:submit={handleSearchSubmit}
		on:clear={handleClear}
	/>
	
	{#if isSearching}
		<p class="searching">Buscando...</p>
	{/if}
	
	{#if searchResults.length > 0}
		<div class="results">
			<h3>Resultados:</h3>
			<ul>
				{#each searchResults as result}
					<li>{result}</li>
				{/each}
			</ul>
		</div>
	{/if}
	
	{#if searchHistory.length > 0}
		<div class="history">
			<h3>Historial de búsquedas:</h3>
			<ul>
				{#each searchHistory as search}
					<li>
						{search.query} 
						<small>({new Date(search.timestamp).toLocaleTimeString()})</small>
					</li>
				{/each}
			</ul>
		</div>
	{/if}
</div>

<style>
	.search-app {
		max-width: 600px;
		margin: 2rem auto;
		padding: 2rem;
	}
	
	.searching {
		color: #007acc;
		font-style: italic;
		margin: 1rem 0;
	}
	
	.results, .history {
		margin: 2rem 0;
		padding: 1rem;
		border: 1px solid #ddd;
		border-radius: 8px;
	}
	
	.results ul, .history ul {
		list-style: none;
		padding: 0;
	}
	
	.results li, .history li {
		padding: 0.5rem 0;
		border-bottom: 1px solid #eee;
	}
	
	small {
		color: #666;
	}
</style>
```

## Próximos pasos

En la **Parte 2.2** cubriremos:
- Routing y navegación con SvelteKit
- Manejo de formularios avanzado
- Integración con APIs
- Primera aplicación práctica completa (Todo App)

## Referencias

- [Svelte 5 Runes Documentation](https://svelte-5-preview.vercel.app/docs/runes)
- [Svelte Stores Guide](https://svelte.dev/docs/svelte-store)
- [Component Communication Patterns](https://svelte.dev/tutorial/declaring-props)
- [Event Dispatching in Svelte](https://svelte.dev/tutorial/component-events)

---
