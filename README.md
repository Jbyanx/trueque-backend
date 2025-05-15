# Sistema de Trueque - Backend

Este es el backend del sistema de trueque de pertenencias entre estudiantes, desarrollado con **Spring Boot**, **PostgreSQL** y gestionado con **Flyway** para el versionado de la base de datos. Permite registrar usuarios, publicar artículos, realizar intercambios y gestionar reportes de contenido.
Es una plataforma destinada a la comunidad universitaria. Su propósito es facilitar el intercambio de artículos de segunda mano, como ropa, libros y útiles académicos, promoviendo una economía circular dentro de la comunidad universitaria.

## Trueque estudiantil permite a los usuarios:
• Coordinar intercambios de articulos entre usuarios.
• Publicar artículos con su descripción.
• Buscar artículos por categoría, estado y nombre.
• Recibir notificaciones sobre los estados de los intercambios.
• Consultar el historial de intercambios realizados por el usuario.
• Moderar contenido inapropiado (administradores).
• Generar reportes sobre la actividad de la plataforma.

## Funcionalidades principales de los usuarios
• Intercambio de artículos: Los usuarios podrán solicitar, cancelar, aceptar y concretar intercambios de los artículos.
• Publicación de artículos: Subir artículos con nombre, descripción y categoría.
• Búsqueda y filtrado: Localizar artículos por categoría, nombre y estado.
• Moderación: Reporte y eliminación de contenido inapropiado.
• Generación de reportes: Análisis de uso de la plataforma.

---

## 🚀 ¿Cómo levantar el proyecto?

### 1. Clona el repositorio

```bash
git clone https://github.com/tu-usuario/trueque-backend.git
cd trueque-backend
```
### 2. Levanta la base de datos
Necesitas tener Docker y Docker Compose instalados. Luego ejecuta:

```bash
docker-compose up -d
```
Esto levantará una base de datos PostgreSQL con los puertos y credenciales configuradas en application.properties.

### 3. Lanza la aplicación Spring Boot
Puedes ejecutar el proyecto desde tu IDE (IntelliJ o Eclipse) o por terminal con:

```bash
./gradlew bootRun
```
Asegúrate de tener Java 17 instalado y configurado correctamente.

### ⚙️ Configuración
El archivo src/main/resources/application.properties contiene la configuración de la base de datos, incluyendo:

``` bash
application.properties

spring.datasource.url=jdbc:postgresql://localhost:5432/trueque_db
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### 🗂️ Estructura del Proyecto
model/: Entidades JPA
dto/: Objetos de transferencia (Request y Response)
repository/: Interfaces de acceso a datos
service/: Lógica de negocio
controller/: Endpoints REST
mapper/: Conversión entre entidades y DTOs
config/: Seguridad y configuración general

### 🐘 Flyway
Cada versión del esquema se encuentra en src/main/resources/db/migration.

### 🐘 Swagger
Documentacion de la api y esquema de seguridad con jwt
