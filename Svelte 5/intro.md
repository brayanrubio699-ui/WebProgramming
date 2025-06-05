# Tutorial Svelte 5 - Parte 1: Instalación y Configuración

## Introducción a Svelte 5

Svelte 5 es la última versión del framework JavaScript que compila tu código a JavaScript vanilla optimizado. A diferencia de React o Vue, Svelte no necesita un virtual DOM, lo que resulta en aplicaciones más rápidas y con menor tamaño de bundle.

### Características principales de Svelte 5:
- **Compilación en tiempo de build**: No runtime overhead
- **Reactividad granular**: Actualizaciones precisas del DOM
- **Sintaxis simple**: Menos código boilerplate
- **Runes system**: Nueva forma de manejar el estado reactivo
- **Mejor TypeScript support**: Integración mejorada
- **Snippets**: Reutilización de fragmentos de template

## Prerrequisitos

Antes de comenzar, asegúrate de tener instalado:

- **Node.js** (versión 18 o superior)
- **npm**, **yarn**, o **pnpm** como package manager
- Un editor de código (recomendado: VS Code con la extensión de Svelte)

Verifica tu instalación:
```bash
node --version
npm --version
```

## Instalación de Svelte 5

### Método 1: Usando create-svelte (Recomendado)

```bash
# Crear nuevo proyecto
npm create svelte@latest mi-proyecto-svelte5

# Navegar al directorio
cd mi-proyecto-svelte5

# Instalar dependencias
npm install

# Iniciar servidor de desarrollo
npm run dev
```

Durante la creación, se te preguntará:
- **Tipo de proyecto**: Skeleton project (para empezar desde cero)
- **TypeScript**: Sí (recomendado)
- **ESLint**: Sí
- **Prettier**: Sí
- **Playwright**: Sí (para testing)
- **Vitest**: Sí (para unit testing)

### Método 2: Instalación manual

```bash
# Crear directorio
mkdir mi-proyecto-svelte5
cd mi-proyecto-svelte5

# Inicializar npm
npm init -y

# Instalar Svelte 5 y dependencias
npm install svelte@next
npm install -D @sveltejs/kit @sveltejs/adapter-auto vite
```

## Estructura del Proyecto

```
mi-proyecto-svelte5/
├── src/
│   ├── lib/
│   │   └── index.js
│   ├── routes/
│   │   ├── +layout.svelte
│   │   └── +page.svelte
│   ├── app.html
│   └── app.js
├── static/
├── tests/
├── package.json
├── svelte.config.js
├── vite.config.js
└── tsconfig.json
```

### Descripción de archivos clave:

- **src/routes/**: Páginas de tu aplicación (file-based routing)
- **src/lib/**: Componentes reutilizables y utilidades
- **src/app.html**: Template HTML principal
- **static/**: Archivos estáticos (imágenes, fonts, etc.)
- **svelte.config.js**: Configuración de Svelte
- **vite.config.js**: Configuración del bundler

## Configuración básica

### svelte.config.js
```javascript
import adapter from '@sveltejs/adapter-auto';

/** @type {import('@sveltejs/kit').Config} */
const config = {
	kit: {
		adapter: adapter(),
		// Configuraciones adicionales
		alias: {
			$components: 'src/components',
			$utils: 'src/utils'
		}
	},
	// Configuración específica de Svelte 5
	compilerOptions: {
		runes: true // Habilitar el sistema de runes
	}
};

export default config;
```

### vite.config.js
```javascript
import { sveltekit } from '@sveltejs/kit/vite';
import { defineConfig } from 'vite';

export default defineConfig({
	plugins: [sveltekit()],
	server: {
		port: 3000,
		open: true
	}
});
```

## Conceptos fundamentales de Svelte 5

### 1. Runes - Nueva forma de reactividad

Svelte 5 introduce "runes", símbolos especiales que manejan el estado reactivo:

```svelte
<script>
	// Estado reactivo con $state
	let count = $state(0);
	
	// Computed values con $derived
	let doubled = $derived(count * 2);
	
	// Efectos con $effect
	$effect(() => {
		console.log(`Count is: ${count}`);
	});
	
	function increment() {
		count++;
	}
</script>

<button on:click={increment}>
	Count: {count} (doubled: {doubled})
</button>
```

### 2. Snippets - Fragmentos reutilizables

```svelte
<script>
	let items = $state(['Apple', 'Banana', 'Cherry']);
</script>

{#snippet itemRenderer(item, index)}
	<li class="item-{index}">
		{item}
	</li>
{/snippet}

<ul>
	{#each items as item, i}
		{@render itemRenderer(item, i)}
	{/each}
</ul>
```

### 3. Componentes básicos

**src/lib/Counter.svelte**
```svelte
<script>
	// Props con TypeScript
	let { initialValue = 0 } = $props();
	
	let count = $state(initialValue);
	
	function increment() {
		count++;
	}
	
	function decrement() {
		count--;
	}
</script>

<div class="counter">
	<button on:click={decrement}>-</button>
	<span class="count">{count}</span>
	<button on:click={increment}>+</button>
</div>

<style>
	.counter {
		display: flex;
		align-items: center;
		gap: 1rem;
		padding: 1rem;
		border: 1px solid #ccc;
		border-radius: 0.5rem;
	}
	
	.count {
		font-size: 1.5rem;
		font-weight: bold;
		min-width: 2rem;
		text-align: center;
	}
	
	button {
		padding: 0.5rem 1rem;
		border: none;
		border-radius: 0.25rem;
		background: #007acc;
		color: white;
		cursor: pointer;
	}
	
	button:hover {
		background: #005c99;
	}
</style>
```

## Comandos útiles de desarrollo

```bash
# Iniciar servidor de desarrollo
npm run dev

# Build para producción
npm run build

# Preview del build
npm run preview

# Linting
npm run lint

# Formateo de código
npm run format

# Tests
npm run test
npm run test:unit
```

## Configuración del editor (VS Code)

### Extensiones recomendadas:
1. **Svelte for VS Code** - Sintaxis highlighting y IntelliSense
2. **TypeScript and JavaScript Language Features** - Soporte TS
3. **Prettier** - Formateo automático
4. **ESLint** - Linting

### settings.json para VS Code:
```json
{
	"[svelte]": {
		"editor.defaultFormatter": "svelte.svelte-vscode"
	},
	"svelte.enable-ts-plugin": true,
	"typescript.preferences.quoteStyle": "single",
	"editor.formatOnSave": true
}
```

## Estructura recomendada para proyectos

```
src/
├── lib/
│   ├── components/
│   │   ├── ui/           # Componentes base (Button, Input, etc.)
│   │   ├── layout/       # Layout components (Header, Footer, etc.)
│   │   └── feature/      # Componentes específicos de funcionalidad
│   ├── stores/           # Estado global
│   ├── utils/            # Funciones utilitarias
│   ├── types/            # Tipos TypeScript
│   └── constants/        # Constantes de la aplicación
├── routes/               # Páginas y rutas
└── styles/              # Estilos globales
```

## Próximos pasos

En la **Parte 2** del tutorial, cubriremos:
- Manejo de estado avanzado
- Comunicación entre componentes
- Routing y navegación
- Desarrollo de la primera aplicación práctica

En la **Parte 3**, desarrollaremos:
- Dos aplicaciones más complejas
- Integración con APIs
- Manejo de formularios
- Deployment y optimización

## Referencias

- [Documentación oficial de Svelte 5](https://svelte-5-preview.vercel.app/)
- [SvelteKit Documentation](https://kit.svelte.dev/)
- [Svelte Tutorial interactivo](https://learn.svelte.dev/)
- [Svelte REPL](https://svelte.dev/repl) - Playground online
- [Svelte Society](https://sveltesociety.dev/) - Comunidad y recursos

---

¡Listo para continuar con la Parte 2 donde construiremos nuestra primera aplicación!
