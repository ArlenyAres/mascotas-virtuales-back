

# Mascotas Virtuales - Backend

## Descripción

Mascotas Virtuales es una aplicación backend que permite a los usuarios gestionar mascotas virtuales. Los usuarios pueden registrarse, iniciar sesión y gestionar sus mascotas. Cada usuario puede tener varias mascotas, las cuales pueden ser de tipos predefinidos o personalizadas. La aplicación ofrece funcionalidades de autenticación segura usando JWT (JSON Web Token) y un control de acceso basado en roles.

## Características

### Autenticación de Usuarios
- Registro e inicio de sesión de usuarios con autenticación basada en JWT.

### Control de Acceso  
- Implementación de roles de usuario (ROLE_USER) y administrador (ROLE_ADMIN).

### Gestión de Mascotas  
- Los usuarios pueden ver, crear, actualizar y eliminar sus mascotas virtuales.

### Mascotas Predefinidas y Personalizadas
- Se ofrecen varios tipos de mascotas predefinidas (dragón, unicornio, alienígena, fantasma) 
- La posibilidad de crear mascotas personalizadas.

### Documentación de API
- La API está documentada usando OpenAPI y accesible a través de Swagger.

## Estructura del Proyecto

El proyecto está estructurado de la siguiente manera:

### config
- DataInitializer: Clase para inicializar la base de datos con un usuario administrador y mascotas predefinidas.
- JwtTokenProvider: Clase para manejar la creación, validación y resolución de tokens JWT.
- OpenApiConfig: Configuración de OpenAPI para la documentación de la API.
- SecurityConfig: Configuración de seguridad para manejar la autenticación y autorización.
- WebConfig: Configuración de CORS para permitir el acceso desde el frontend.

### controllers
- AuthController: Controlador para manejar las operaciones de autenticación (/auth/login y /auth/register).
- MascotaController: Controlador para manejar las operaciones relacionadas con las mascotas (/mascotas).

### dto
- MascotaPersonalizadaRequest: DTO para manejar las solicitudes de creación de mascotas personalizadas.

### models
- Usuario: Entidad que representa a los usuarios de la aplicación.
- MascotaVirtual: Entidad que representa a las mascotas virtuales.
- TipoMascota: Enum que define los tipos de mascotas disponibles.

### repositories
- MascotaRepository: Repositorio JPA para manejar las operaciones CRUD de MascotaVirtual.
- UsuarioRepository: Repositorio JPA para manejar las operaciones CRUD de Usuario.

### services
- MascotaService: Servicio que contiene la lógica de negocio para manejar las mascotas.
- UsuarioService: Servicio que contiene la lógica de negocio para manejar los usuarios.

### resources
- application.properties: Archivo de configuración de la aplicación.

## Dependencias

El proyecto utiliza las siguientes dependencias:

- Spring Boot Starter Data JPA: Para operaciones de persistencia con JPA.
- Spring Boot Starter Security: Para la implementación de la seguridad y autenticación.
- Spring Boot Starter Web: Para crear una API RESTful.
- Spring Boot Starter Validation: Para la validación de datos.
- MySQL Connector: Para la conexión con una base de datos MySQL.
- Lombok: Para reducir el boilerplate de código.
- Spring Boot Starter Test: Para pruebas unitarias.
- Spring Security Test: Para pruebas relacionadas con la seguridad.
- Springdoc OpenAPI: Para la generación automática de documentación de API.
- JJWT: Para la creación y validación de tokens JWT.

## Configuración del Proyecto

Prerrequisitos:
- Java 22
- Maven
- MySQL (opcional si se desea usar una base de datos H2 en lugar de MySQL)

Instrucciones de Configuración:

1. Clonar el repositorio:
   ```
   git clone https://github.com/ArlenyAres/mascotas-virtuales-back
   ```

2. Navegar al directorio del proyecto:
   ```
   cd mascotas-virtuales-back
   ```

3. Compilar el proyecto:
   ```
   mvn clean install
   ```

4. Ejecutar la aplicación:
   ```
   mvn spring-boot:run
   ```

5. Acceder a la documentación de la API:
   Visita http://localhost:8080/swagger-ui.html para explorar la documentación de la API.

## Configuración de la Base de Datos

Por defecto, MySQL base de datos, actualiza el archivo application.properties con las credenciales de la base de datos:

```
spring.application.name=mascotas-virtuales-back
server.port=8080

spring.datasource.url=jdbc:mysql://localhost:8889/mascotas
spring.datasource.username=root
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
```

## Seguridad

Usuario Admin por defecto:
- Usuario: admin
- Contraseña: adminpassword

JWT Token:
- La aplicación utiliza tokens JWT para la autenticación. Los tokens se emiten al iniciar sesión y deben incluirse en el encabezado Authorization en todas las solicitudes API protegidas.

## Documentación de API

Endpoints de Autenticación (/auth)
- POST /auth/login: Autentica un usuario y devuelve un token JWT.
- POST /auth/register: Registra un nuevo usuario.

Endpoints de Mascotas (/mascotas)
- GET /mascotas/predefinidas: Devuelve una lista de mascotas predefinidas.
- POST /mascotas/personalizar: Permite crear una nueva mascota personalizada.
- GET /mascotas: Devuelve todas las mascotas del usuario autenticado.
- PUT /mascotas/{mascotaId}: Actualiza una mascota existente.
- GET /mascotas/{mascotaId}: Devuelve los detalles de una mascota por su ID.
- DELETE /mascotas/{id}: Elimina una mascota (solo el propietario o un administrador pueden realizar esta acción).

## Pruebas

Para realizar pruebas de la aplicación, puedes utilizar herramientas como Postman para interactuar con los endpoints. Asegúrate de incluir el token JWT en el encabezado Authorization después de iniciar sesión.
