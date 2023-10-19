# HotelAlura
# API de Reservación de Hotel

Esta es una API desarrollada en Spring Boot que permite realizar reservaciones en un hotel. Esta API fue creada como parte del desafío de programación de Alura Latam, como parte del programa ONE.

## Tecnologías Utilizadas

- Spring Boot
- MySQL (base de datos)
- JPA (Java Persistence API)
- Hibernate (ORM)
- Spring Security (para autenticación y autorización)
- JWT (JSON Web Tokens) para manejar la autenticación
- Swagger (para documentación de la API)

## Endpoints y Funcionalidades

### User Controller

- `GET /api/users/{id}`: Obtiene un usuario por ID.
- `PUT /api/users/{id}`: Actualiza la información de un usuario existente.
- `DELETE /api/users/{id}`: Elimina un usuario.
- `POST /api/users`: Crea un nuevo usuario.
- `POST /api/users/login`: Inicia sesión de un usuario.

### Reservation Controller

- `GET /api/reservation/{id}`: Obtiene una reserva por ID.
- `PUT /api/reservation/{id}`: Actualiza una reserva existente.
- `DELETE /api/reservation/{id}`: Elimina una reserva.
- `GET /api/reservation`: Obtiene una lista de todas las reservas.
- `POST /api/reservation`: Crea una nueva reserva.
- `DELETE /api/reservation/companion`: Elimina un acompañante de una reserva.

### Guest Controller

- `GET /api/guest/{id}`: Obtiene un huésped por ID.
- `PUT /api/guest/{id}`: Actualiza la información de un huésped existente.
- `DELETE /api/guest/{id}`: Elimina un huésped.
- `GET /api/guest`: Obtiene una lista de todos los huéspedes.
- `POST /api/guest`: Crea un nuevo huésped.

## Configuración

Para ejecutar esta API en tu entorno de desarrollo, sigue estos pasos:

1. Clona este repositorio.

   ```bash
   git clone https://github.com/DominicRG/HotelAlura.git

2. Configura la base de datos MySQL. Puedes modificar las credenciales y la configuración de la base de datos en `application.properties`.
3. La API estará disponible en `http://localhost:8080`. Puedes acceder a la documentación de la API a través de Swagger en `http://localhost:8080/swagger-ui/index.html`.

## Uso de JWT

Esta API utiliza JSON Web Tokens (JWT) para la autenticación y autorización. Para acceder a ciertas rutas protegidas, debes incluir un token JWT en la cabecera de tus solicitudes.

## Documentación

Puedes encontrar la documentación completa de la API en Swagger. Accede a `http://localhost:8080/swagger-ui/index.html` en tu navegador o `<URL-desplegado>/swagger-ui/index.html` si la API se encuentra en un entorno de producción.

## Autor

Dominic Roman.
- Email: romandominic30@gmail.com
- Linkedin: https://www.linkedin.com/in/dominic-roman-649332204/

## Agradecimientos
Agradezco a Alura Latam y el programa ONE por proporcionar el desafío y la oportunidad de aprender y crear esta API.

