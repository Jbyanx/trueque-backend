# Sistema de Trueque - Backend

Este es el backend del sistema de trueque de pertenencias entre estudiantes, desarrollado con **Spring Boot**, **PostgreSQL** y gestionado con **Flyway** para el versionado de la base de datos. Permite registrar usuarios, publicar artículos, realizar intercambios y gestionar reportes de contenido.

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
Copiar
Editar
docker-compose up -d
```
Esto levantará una base de datos PostgreSQL con los puertos y credenciales configuradas en application.properties.

### 3. Lanza la aplicación Spring Boot
Puedes ejecutar el proyecto desde tu IDE (IntelliJ o Eclipse) o por terminal con:
```
bash
Copiar
Editar
./gradlew bootRun
```
Asegúrate de tener Java 17 instalado y configurado correctamente.

### ⚙️ Configuración
El archivo src/main/resources/application.properties contiene la configuración de la base de datos, incluyendo:
```
properties
Copiar
Editar
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