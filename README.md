
### 🐳 Docker - Microservicios con Spring Boot

Repositorio de ejemplo para trabajar con Docker, Docker Compose y microservicios utilizando Spring Boot y PostgreSQL.
Este proyecto está pensado como apoyo educativo para estudiantes de Ingeniería en Informática y personas que estén comenzando en el mundo de los contenedores.

### 📦 Tecnologías utilizadas

- Docker
- Docker Compose
- Spring Boot
- PostgreSQL
- Maven
- Java 21

### 🐳 Dockerfile explicado

Ejemplo utilizado en los servicios:

FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 9090

ENTRYPOINT ["java", "-jar", "app.jar"]

### 🔍 Explicación

| Línea                     | Descripción                              |
| ------------------------- | ---------------------------------------- |
| `FROM maven...`           | Imagen con Maven y Java 21 para compilar |
| `WORKDIR /app`            | Directorio interno del contenedor        |
| `COPY`                    | Copia archivos del proyecto              |
| `RUN mvn clean package`   | Compila el proyecto                      |
| `FROM eclipse-temurin...` | Imagen liviana solo para ejecutar        |
| `COPY --from=build`       | Copia el `.jar` generado                 |
| `EXPOSE 9090`             | Expone el puerto del servicio            |
| `ENTRYPOINT`              | Ejecuta la aplicación                    |

### 🧩 Docker Compose

Ejemplo básico:
version: '3.9'

services:
  usuarios:
    build: ./servicio-usuarios
    ports:
      - "8081:8081"
  reservas:
    build: ./servicio-reservas
    ports:
      - "9090:9090"
  db-reservas:
    image: postgres:16
    environment:
      POSTGRES_DB: basereservas
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: *****
    ports:
      - "5434:5432"

### ▶️ Comandos básicos de Docker
- Construir una imagen
  docker build -t servicio-usuarios .

- Levantar un contenedor
  docker run -p 8081:8081 servicio-usuarios

- Ver contenedores activos
  docker ps

- Ver imágenes
  docker images

- Detener un contenedor
  docker stop ID_CONTENEDOR

- Eliminar un contenedor
  docker rm ID_CONTENEDOR

- Eliminar una imagen
  docker rmi ID_IMAGEN

### 🐙 Comandos Docker Compose

- Levantar servicios
  docker compose up

- Levantar en segundo plano
  docker compose up -d

- Detener servicios
  docker compose down

- Reconstruir imágenes
  docker compose up --build

### ✨ Autor
Desarrollado por Profe Vivitasol

