# Guía de Implementación WCAG 2.2 - W3C

## Principios Fundamentales (POUR)

### 1. **Perceptible** - La información debe ser presentable de formas que los usuarios puedan percibir
### 2. **Operable** - Los componentes de la interfaz deben ser operables
### 3. **Comprensible** - La información y el manejo de la interfaz debe ser comprensible
### 4. **Robusto** - El contenido debe ser robusto para ser interpretado por una amplia variedad de agentes de usuario

---

## Niveles de Conformidad

- **Nivel A**: Básico (mínimo)
- **Nivel AA**: Estándar (recomendado para la mayoría de sitios)
- **Nivel AAA**: Mejorado (más restrictivo, no siempre aplicable)

---

## 1. PERCEPTIBLE

### 1.1 Alternativas de Texto
- **Imágenes**: Proporcionar texto alternativo descriptivo (`alt=""` para decorativas)
- **Imágenes complejas**: Usar descripciones largas cuando sea necesario
- **CAPTCHAs**: Ofrecer alternativas en audio y otros formatos

```html
<img src="grafico.jpg" alt="Ventas aumentaron 25% en Q1 2024">
<img src="decoracion.jpg" alt="" role="presentation">
```

### 1.2 Medios Basados en Tiempo
- **Videos**: Subtítulos sincronizados y audiodescripción
- **Audio**: Transcripciones textuales
- **Contenido en vivo**: Subtítulos en tiempo real

### 1.3 Adaptable
- **Estructura semántica**: Usar encabezados (h1-h6) jerárquicamente
- **Orden de lectura**: Contenido lógico sin CSS
- **Instrucciones**: No depender solo de características sensoriales

```html
<h1>Título Principal</h1>
<main>
  <section>
    <h2>Sección</h2>
    <h3>Subsección</h3>
  </section>
</main>
```

### 1.4 Distinguible
- **Contraste de color**: 
  - Texto normal: 4.5:1 (AA), 7:1 (AAA)
  - Texto grande: 3:1 (AA), 4.5:1 (AAA)
- **Redimensionamiento**: Hasta 200% sin pérdida de funcionalidad
- **Imágenes de texto**: Evitar, usar texto real cuando sea posible
- **Reflow**: Contenido adaptable a 320px de ancho

---

## 2. OPERABLE

### 2.1 Accesible por Teclado
- **Navegación por teclado**: Toda funcionalidad accesible con teclado
- **Sin trampas**: El foco no debe quedar atrapado
- **Atajos de teclado**: Documentar y permitir desactivación

```html
<button onclick="submitForm()" onkeypress="handleKeyPress(event)">
  Enviar
</button>
```

### 2.2 Tiempo Suficiente
- **Límites de tiempo**: Permitir extender, deshabilitar o ajustar
- **Pausar contenido**: Control sobre contenido en movimiento
- **Sin límites**: Para tareas esenciales sin límite de tiempo

### 2.3 Convulsiones y Reacciones Físicas
- **Parpadeo**: Máximo 3 parpadeos por segundo
- **Animaciones**: Permitir desactivar animaciones no esenciales

```css
@media (prefers-reduced-motion: reduce) {
  * {
    animation-duration: 0.01ms !important;
    animation-iteration-count: 1 !important;
  }
}
```

### 2.4 Navegable
- **Saltar bloques**: Enlaces para saltar navegación repetitiva
- **Títulos de página**: Descriptivos y únicos
- **Orden del foco**: Lógico y predecible
- **Propósito del enlace**: Claro desde el contexto

```html
<a href="#main" class="skip-link">Saltar al contenido principal</a>
<nav aria-label="Navegación principal">
  <ul>
    <li><a href="/inicio">Inicio</a></li>
    <li><a href="/productos">Productos</a></li>
  </ul>
</nav>
```

### 2.5 Modalidades de Entrada
- **Gestos**: Alternativas para gestos multitáctiles
- **Cancelación de puntero**: Permitir cancelar acciones
- **Etiquetas**: Coincidencia entre etiquetas visibles y accesibles

---

## 3. COMPRENSIBLE

### 3.1 Legible
- **Idioma de la página**: Especificar con `lang`
- **Idioma de partes**: Identificar cambios de idioma
- **Palabras inusuales**: Proporcionar definiciones

```html
<html lang="es">
<p>El término <span lang="en">responsive design</span> se refiere a...</p>
```

### 3.2 Predecible
- **Al recibir el foco**: Sin cambios automáticos de contexto
- **Al introducir datos**: Sin cambios inesperados
- **Navegación consistente**: Misma ubicación en todas las páginas
- **Identificación consistente**: Misma funcionalidad, misma identificación

### 3.3 Asistencia para la Entrada
- **Identificación de errores**: Describir errores claramente
- **Etiquetas o instrucciones**: Proporcionar cuando se requiera entrada
- **Sugerencias de error**: Ofrecer correcciones cuando sea posible
- **Prevención de errores**: Confirmación para acciones importantes

```html
<label for="email">Correo electrónico (requerido)</label>
<input type="email" id="email" required aria-describedby="email-error">
<div id="email-error" class="error" aria-live="polite">
  Por favor ingrese un correo válido
</div>
```

---

## 4. ROBUSTO

### 4.1 Compatible
- **Análisis sintáctico**: HTML válido y bien formado
- **Nombre, función, valor**: Elementos programáticamente determinables
- **Mensajes de estado**: Comunicar cambios importantes

```html
<button aria-expanded="false" aria-controls="menu">
  Menú
</button>
<ul id="menu" hidden>
  <li><a href="/opcion1">Opción 1</a></li>
</ul>
```

---

## Nuevos Criterios en WCAG 2.2

### 2.4.11 Foco No Oscurecido (Mínimo) - AA
El foco del teclado no debe ser completamente oscurecido por contenido superpuesto.

### 2.4.12 Foco No Oscurecido (Mejorado) - AAA
El foco del teclado no debe ser oscurecido en absoluto.

### 2.4.13 Apariencia del Foco - AAA
El indicador de foco debe tener suficiente contraste y tamaño.

### 2.5.7 Movimientos de Arrastre - AA
Funcionalidad que usa arrastrar debe tener alternativa con un solo puntero.

### 2.5.8 Tamaño del Objetivo - AA
Objetivos táctiles de al menos 24x24 píxeles CSS.

### 3.2.6 Ayuda Consistente - A
Mecanismos de ayuda en ubicación consistente.

### 3.3.7 Entrada Redundante - A
No requerir información ya proporcionada en el mismo proceso.

### 3.3.8 Autenticación Accesible (Mínimo) - AA
Métodos de autenticación que no dependan de función cognitiva.

### 3.3.9 Autenticación Accesible (Mejorado) - AAA
Autenticación sin requerir reconocimiento de objetos o contenido específico del usuario.

---

## Herramientas de Evaluación

### Automáticas
- **axe DevTools**: Extensión de navegador
- **WAVE**: Evaluador web de accessibilidad
- **Lighthouse**: Auditoría integrada en Chrome
- **Pa11y**: Herramienta de línea de comandos

### Manuales
- **Navegación por teclado**: Tab, Shift+Tab, Enter, Espacio
- **Lectores de pantalla**: NVDA, JAWS, VoiceOver
- **Contraste de color**: WebAIM Contrast Checker
- **Validación HTML**: W3C Markup Validator

---

## Lista de Verificación Rápida

### Antes de Publicar
- [ ] Todas las imágenes tienen texto alternativo apropiado
- [ ] Contraste de color cumple estándares AA (4.5:1)
- [ ] Navegación completa por teclado funciona
- [ ] Formularios tienen etiquetas y manejo de errores
- [ ] Videos tienen subtítulos
- [ ] Estructura de encabezados es lógica (h1-h6)
- [ ] HTML es válido
- [ ] Página tiene título descriptivo
- [ ] Idioma está especificado
- [ ] Enlaces tienen contexto claro

### Pruebas de Usuario
- [ ] Navegar solo con teclado
- [ ] Usar lector de pantalla
- [ ] Aumentar zoom al 200%
- [ ] Desactivar imágenes
- [ ] Probar con usuarios con discapacidades

---

## Recursos Adicionales

- **Especificación oficial**: [W3C WCAG 2.2](https://www.w3.org/WAI/WCAG22/quickref/)
- **Técnicas WCAG**: Ejemplos específicos de implementación
- **WebAIM**: Recursos y herramientas prácticas
- **MDN Web Docs**: Documentación sobre accesibilidad web
- **A11y Project**: Patrones y ejemplos de código


---

*Esta guía cubre los aspectos más importantes de WCAG 2.2. Para implementaciones específicas, siempre consultar la documentación oficial del W3C y realizar pruebas con usuarios reales.*
