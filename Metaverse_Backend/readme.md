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

### Calendario de Julio 2025 — Taller: Back-End para Metaverso Universitario

| Lun | Mar | Mié | Jue | Vie | Sáb | Dom |
|-----|-----|-----|-----|-----|-----|-----|
|     |  1  |  2  |  3  |  4  |  5  |  6  |
|  7  |  8  |  9  | 10  | 11  | 12  | 13  |
| 14* | 15  | 16* | 17  | 18* | 19  | 20  |
| 21* | 22  | 23* | 24  | 25* | 26  | 27  |
| 28  | 29  | 30  | 31  |     |     |     |

* Sesiones del taller:
- **Julio 14, salón 8206**: Arquitectura y base del proyecto. Una introducción breve a Typescript: primeros pasos. 
- **Julio 16, salón 8206**: Usuarios y base de datos  
- **Julio 18, Salón 8105**: Autenticación y seguridad  
- **Julio 21, salón 8206**: WebSockets y tiempo real  
- **Julio 23, salón 8206**: Persistencia y recuperación de estado  
- **Julio 25, Salón 8105**: Escalabilidad y despliegue

##  Recomendaciones:

- Preferiblemente usar un portátil propio donde se pueda instalar el software necesario. Se trabajará con NodeJS, Mongo DB (en la nube), Compass (para gestión de la base de datos), y demás librerías que se vayan necesitando.

### Pasos iniciales: 

1. Instalación de Node JS (entorno de ejecución de Javascript). Se recomienda la versión estable más reciente: https://nodejs.org/blog/release/v22.17.0
2. Puede usar el IDE de su preferencia, pero se recomienda uno que permita gestionar la instalación de librerías y paquetes de forma sencilla, como visual studio code (https://code.visualstudio.com/). IntelliJ WebStorm también es una buena opción, si hace uso de la licencia educativa a través del correo institucional (https://www.jetbrains.com/webstorm/download/download-thanks.html)
3. Una vez installado Node JS y el IDE con el que va a trabajar, verifique que Node haya quedado instalado: abra una ventana de comandos de windows o la consola de VS Code y escriba "node --version": debería aparecer el número de versión que instaló.
4. Ubíquese en la carpeta donde alojará su proyecto (que no sea una ruta con un nombre muy largo, o la instalación de paquetes tendrá problemas). Ejemplo:
   
   >C:\WebHome\
   
   E instale NestJS (el framework de Javascript que usaremos):
   
   >C:\WebHome\ **npm install -g @nestjs/cli**
   
   Una vez termine la instalación de NestJS de forma completa y correcta, cree la carpeta del proyecto de la siguiente forma:
   >C:\WebHome\ **nest new backend_metaverso**
   >
   Desde la carpeta del proyecto recién creada, vamos a instalar algunas dependencias adicionales para conectarnos con la base de datos y para manejar las clases y objetos del proyecto:
   >C:\WebHome\backend_metaverso\ **npm install @nestjs/mongoose mongoose**
   
   >C:\WebHome\backend_metaverso\ **npm install class-validator class-transformer**
   
   En nuestro ejercicio, vamos a crear **dos módulos**: uno para autenticación de usuarios y otro para manejo de puntajes. A continuación los creamos:
   
   >nest g module user_auth
   >
   >nest g controller user_auth
   >
   >nest g service user_auth
   >
   
   >nest g module user_score
   >
   >nest g controller user_score
   >
   >nest g service user_score
   >
   >nest g gateway metaverso_usc
