# Programación Web, 2025A
# Universidad Santiago de Cali

## Implementación de Autenticación en Spring Boot con MongoDB

Esta guía le ayudará a implementar un sistema de autenticación en una API Spring Boot utilizando MongoDB como base de datos.

## Requisitos previos

- JDK 17 o superior
- Maven o Gradle
- MongoDB (local o en la nube)
- Conocimiento básico de Spring Boot

## Paso 1: Configurar el proyecto

Cree un nuevo proyecto Spring Boot con [Spring Initializr](https://start.spring.io/) incluyendo las siguientes dependencias:

- Spring Web
- Spring Security
- Spring Data MongoDB
- Lombok (opcional pero recomendado)

## Paso 2: Configurar MongoDB

Agregue la configuración de MongoDB en el archivo `application.properties`:

```properties
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=auth_demo
spring.data.mongodb.auto-index-creation=true
```

## Paso 3: Crear el modelo de Usuario

```java
package com.example.auth.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Document(collection = "users")
public class User implements UserDetails {
    
    @Id
    private String id;
    
    @Indexed(unique = true)
    private String username;
    
    private String password;
    
    @Indexed(unique = true)
    private String email;
    
    private List<String> roles;
    
    private boolean enabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
```

## Paso 4: Crear el repositorio de Usuario

```java
package com.example.auth.repositories;

import com.example.auth.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
```

## Paso 5: Implementar el servicio de Usuario

```java
package com.example.auth.services;

import com.example.auth.models.User;
import com.example.auth.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }
    
    public User registerUser(User user) {
        // Verificar si el usuario ya existe
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }
        
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("El email ya está en uso");
        }
        
        // Codificar la contraseña
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Guardar el usuario
        return userRepository.save(user);
    }
}
```

## Paso 6: Configurar Spring Security

```java
package com.example.auth.config;

import com.example.auth.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> 
                auth.requestMatchers("/api/auth/**").permitAll()
                    .anyRequest().authenticated()
            )
            .httpBasic();
        
        http.authenticationProvider(authenticationProvider());
        
        return http.build();
    }
}
```

## Paso 7: Crear controladores para autenticación

```java
package com.example.auth.controllers;

import com.example.auth.models.User;
import com.example.auth.payload.LoginRequest;
import com.example.auth.payload.SignupRequest;
import com.example.auth.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        // En una implementación real, aquí se generaría un token
        Map<String, String> response = new HashMap<>();
        response.put("message", "Inicio de sesión exitoso");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(signupRequest.getPassword());
        user.setRoles(Collections.singletonList("USER"));
        
        userService.registerUser(user);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Usuario registrado con éxito");
        return ResponseEntity.ok(response);
    }
}
```

## Paso 8: Crear modelos de solicitud para el controlador

```java
package com.example.auth.payload;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
```

```java
package com.example.auth.payload;

import lombok.Data;

@Data
public class SignupRequest {
    private String username;
    private String email;
    private String password;
}
```

## Paso 9: Crear un controlador protegido para pruebas

```java
package com.example.auth.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> userAccess(Principal principal) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Contenido de usuario accesible");
        response.put("username", principal.getName());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> adminAccess() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Contenido de administrador accesible");
        return ResponseEntity.ok(response);
    }
}
```

## Paso 10: Ejecutar la aplicación

Ahora se puede ejecutar la aplicación y probar los endpoints de autenticación.

### Registro de usuario

```bash
POST http://localhost:8080/api/auth/signup
Content-Type: application/json

{
  "username": "usuario1",
  "email": "usuario1@example.com",
  "password": "password123"
}
```

### Inicio de sesión

```bash
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "username": "usuario1",
  "password": "password123"
}
```

### Acceder a un recurso protegido

```bash
GET http://localhost:8080/api/test/user
Authorization: Basic dXN1YXJpbzE6cGFzc3dvcmQxMjM=
```

## Conclusión

Con esto se implementa un sistema de autenticación básico en Spring Boot utilizando MongoDB como base de datos. Esta implementación utiliza el sistema de autenticación básico de Spring Security, pero en una aplicación real, se recomendaría utilizar un mecanismo más seguro como JWT o OAuth2.

Para mejorar esta implementación, se podría:

1. Agregar validación de datos
2. Implementar un sistema de verificación de email
3. Agregar funcionalidad de recuperación de contraseña
4. Integrar JWT para tokens de autenticación (ver la guía JWT)
5. Implementar OAuth2 para login con redes sociales (ver la guía OAuth2)
