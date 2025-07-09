# Semillero Informa, 2025
# Taller: Desarrollo de un Back-End Robusto para un Metaverso Universitario

## Objetivo General

Diseñar e implementar un sistema back-end modular, seguro, escalable y orientado a eventos, capaz de manejar autenticación, personalización de usuario y conexión en tiempo real, con el fin de servir como infraestructura base para un metaverso universitario. Este sistema debe tener la capacidad de escalar para atender a una comunidad estudiantil de aproximadamente 25.000 usuarios, aunque no todos se conecten simultáneamente.

## Stack Tecnológico

* NestJS (con TypeScript)
* Node.js
* MongoDB (con Mongoose)
* WebSockets (Socket.IO a través de NestJS)
* JWT para autenticación
* Swagger para documentación
* Docker y Redis (opcional)

## Plan de Taller (6 sesiones)

### Sesión 1: Arquitectura y Base del Proyecto

**Fecha:** 14 de Julio

**Temas:**

* Introducción al concepto de metaverso y sus necesidades desde el back-end.
* Patrones arquitectónicos: monolito modular, microservicios, orientación a eventos.
* Diseño orientado a dominios: Usuarios, Autenticación, Entorno, Avatar, Movimiento.
* Estructura del proyecto utilizando NestJS CLI.
* Creación de módulos base: `users`, `auth`, `scene`, `gateway`.
* Configuración inicial de MongoDB (local o en la nube).

**Actividad:**

* Creación del proyecto NestJS.
* Definición de la estructura modular.
* Conexión y prueba de base de datos MongoDB.

### Sesión 2: Usuarios, Personalización y Base de Datos

**Fecha:** 16 de Julio

**Temas:**

* Definición del esquema de usuario.
* Integración con `@nestjs/mongoose`, uso de DTOs e interfaces.
* Validaciones con `class-validator`.
* Separación de responsabilidades en capas: controllers, services, schemas.

**Actividad:**

* Implementación del esquema `User`.
* Creación de endpoints REST para registrar, actualizar y consultar perfil.

### Sesión 3: Autenticación JWT y Seguridad

**Fecha:** 18 de Julio

**Temas:**

* Creación del módulo `AuthModule`.
* Implementación de `AuthService` y `JwtStrategy`.
* Uso de Guards y decoradores personalizados.
* Seguridad básica: hash de contraseñas, CORS, headers de seguridad.

**Actividad:**

* Implementación del sistema de login y registro.
* Protección de rutas mediante `@UseGuards`.
* Pruebas con Postman.

### Sesión 4: WebSockets y Tiempo Real

**Fecha:** 21 de Julio

**Temas:**

* Implementación de `@WebSocketGateway()` en NestJS.
* Autenticación en conexiones WebSocket.
* Emisión y recepción de eventos: `join`, `move`, `message`, `disconnect`.
* Almacenamiento de estado sincronizado en MongoDB.

**Actividad:**

* Desarrollo de Gateway con soporte para varios usuarios.
* Simulación de eventos de movimiento y conexión.

### Sesión 5: Persistencia, Avatares y Recuperación de Estado

**Fecha:** 23 de Julio

**Temas:**

* Creación de API para personalización de avatar.
* Guardado de posición y configuración de usuario.
* Recuperación de estado en reconexiones.
* Validación del esquema `AvatarConfig`.

**Actividad:**

* Desarrollo de endpoints REST para configuración de avatar.
* Implementación de recuperación de estado mediante WebSocket.

### Sesión 6: Escalabilidad, Monitoreo y Despliegue

**Fecha:** 25 de Julio

**Temas:**

* Estrategias de escalabilidad: clústeres, Redis pub/sub, balanceo de carga.
* Documentación de API con Swagger.
* Despliegue en servicios como Railway, Render o Docker.
* Introducción a logs con `winston` o `pino` y monitoreo básico.

**Actividad:**

* Generación de documentación Swagger.
* Despliegue de la aplicación y pruebas en entorno remoto.

## Resultados Esperados

Al finalizar el taller, los participantes contarán con un back-end funcional y escalable, compuesto por:

* Sistema de autenticación seguro con JWT.
* Conexión en tiempo real a través de WebSockets.
* Base de datos NoSQL estructurada y persistente.
* API REST documentada y organizada modularmente.
* Preparación para integración con motores como Unity o Three.js.
