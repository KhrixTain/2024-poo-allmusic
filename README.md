# All Music - Backend para una Aplicación de Música

Este proyecto implementa el backend de una aplicación para una comunidad de usuarios vinculados a la música, usando un servicio web RESTful que sigue la arquitectura REST. El desarrollo se ha realizado con Java y Spring Framework.

## Descripción General
El sistema permite gestionar canciones y playlists por parte de dos tipos de usuarios:

1. **Music Enthusiasts**:
    - Acceden a las canciones en la plataforma.
    - Visualizan playlists creadas por otros usuarios.
    - Crean sus propias playlists.

2. **Music Artists**:
    - Tienen todas las funcionalidades de los Music Enthusiasts.
    - Además, pueden crear sus propias canciones que estarán disponibles para otros usuarios.

### Funcionalidades Principales

- **Playlists**:
    - Cada usuario puede crear múltiples playlists.
    - Las playlists tienen un único propietario.
    - Una playlist puede contener canciones creadas por otros usuarios.

- **Canciones**:
    - Cada canción pertenece a un género musical específico (enumerativo).
    - Los géneros disponibles son: `Rock`, `Techno`, `Pop`, `Jazz`, `Folk`, y `Classical`.
    - Una canción puede estar en múltiples playlists.

### Restricciones
- Solo los usuarios propietarios pueden modificar o borrar sus canciones y playlists.

---

## Requisitos del Proyecto

### Persistencia
- Uso de **JPA** para mapear las clases modelo a entidades de base de datos.
- Estrategia seleccionada para manejar la herencia entre tipos de usuarios.
- Configuración del repositorio de usuarios y métodos personalizados, como `findByUsername`.

### Autenticación y Autorización
- Implementación de autenticación basada en tokens JWT:
    - Generación de tokens con algoritmo HMAC 512 y una expiración de 10 días.
    - Verificación y extracción del payload del token.
- Restricción de acceso a endpoints según roles de usuario.

### Servicios REST
El backend expone los siguientes endpoints:

#### Canciones
- **Listar canciones:** Filtrar por artista y/o género. `GET /songs`
- **Obtener canción específica:** `GET /songs/:id`
- **Crear canción:** Solo para usuarios artistas. `POST /songs`
- **Actualizar canción:** Solo el creador puede hacerlo. `PUT /songs/:id`
- **Eliminar canción:** Solo el creador puede hacerlo. `DELETE /songs/:id`
- **Listar canciones del usuario actual:** `GET /me/songs`

#### Playlists
- **Listar playlists:** Incluye nombre y cantidad de canciones. `GET /playlists`
- **Obtener detalles de una playlist:** Incluye listado de canciones. `GET /playlists/:id`
- **Crear playlist:** Inicialmente vacía. `POST /playlists`
- **Actualizar playlist:** Solo el creador puede hacerlo. `PUT /playlists/:id`
- **Eliminar playlist:** Solo el creador puede hacerlo. `DELETE /playlists/:id`
- **Agregar canción a una playlist:** Solo el creador puede hacerlo. `POST /playlists/:id/songs`
- **Eliminar canción de una playlist:** Solo el creador puede hacerlo. `DELETE /playlists/:id/songs/:song_id`
- **Listar playlists del usuario actual:** `GET /me/playlists`

---

## Dependencias y Herramientas

- **Java 17**
- **Spring Framework** (Boot, Web, Data JPA)
- **JWT** para autenticación
- **Password4j** para hashing de contraseñas
- **ModelMapper** para conversión entre DTOs y entidades
- **MySQL** como base de datos

---

## Estructura del Proyecto

### Packages
- `ar.edu.unnoba.poo2024.allmusic.model`: Clases modelo del dominio.
- `ar.edu.unnoba.poo2024.allmusic.repository`: Repositorios JPA.
- `ar.edu.unnoba.poo2024.allmusic.service`: Lógica de negocio y servicios.
- `ar.edu.unnoba.poo2024.allmusic.util`: Utilidades (JWT, codificación de contraseñas, etc.).
- `ar.edu.unnoba.poo2024.allmusic.resource`: Controladores REST.

---

## Entregables
El proyecto incluye:
1. Implementación de todas las funcionalidades descritas.
2. Estructura organizada según buenas prácticas de programación orientada a objetos.
3. Un README detallado (este documento).