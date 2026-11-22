# foro-arquetipo

Arquetipo Maven propio para crear los microservicios del **Sistema de Foros en Línea**
(Desarrollo Full Stack III - DSY2205).

## Qué trae cada microservicio generado

| Elemento | Detalle |
|---|---|
| Framework | Spring Boot 4 (Java 21) con Spring Web MVC, Spring Data JPA y Bean Validation |
| Base de datos | Oracle Autonomous Database (Oracle Cloud) con wallet (`ojdbc17` + `oraclepki`) |
| Estructura | Capas `controller`, `service`, `repository`, `model`, `dto`, `exception`, `config` |
| Errores | `GlobalExceptionHandler`: respuestas JSON uniformes para 400, 403, 404, 409 y 500 |
| Patrón de diseño | **Builder** (`ErrorResponse.builder()`, `EstadoResponse.builder()`) |
| Salud | `GET /api/estado` para comprobar que el servicio está arriba (útil en el Cloud) |
| CORS | Orígenes configurables con la variable `CORS_ORIGENES` |
| Cloud | `Dockerfile` multi-stage, usuario sin privilegios, puerto desde la variable `PORT` |
| Seguridad | El wallet y las claves nunca van en el código: `.gitignore`, `.dockerignore` y variables de entorno |
| Pruebas | Test de la capa web que no necesita base de datos |

## Estructura del repositorio

```
foro-arquetipo/
├── proyecto-base/   Proyecto Spring Boot desde el que se creó el arquetipo
└── arquetipo/       Arquetipo Maven (packaging maven-archetype)
```

## Cómo se creó

```
cd proyecto-base
mvnw archetype:create-from-project -Darchetype.properties=archetype.properties
```

Luego, el resultado (`target/generated-sources/archetype`) se copió a `arquetipo/` y se ajustó
`archetype-metadata.xml` para pedir los parámetros `nombreClase` y `puerto`.

## Cómo se instala (repositorio local de Maven)

```
cd arquetipo
mvnw clean install -Darchetype.test.skip=true
```

## Cómo se genera un microservicio

```
mvn archetype:generate -DarchetypeGroupId=com.foro -DarchetypeArtifactId=foro-arquetipo -DarchetypeVersion=1.0.0 ^
    -DgroupId=com.foro -DartifactId=user-service -Dpackage=com.foro.user_service ^
    -DnombreClase=UserService -Dpuerto=8083 -DinteractiveMode=false
```

| Parámetro | Ejemplo | Uso |
|---|---|---|
| `artifactId` | `user-service` | Nombre del proyecto y de la aplicación |
| `package` | `com.foro.user_service` | Paquete base del código |
| `nombreClase` | `UserService` | Clase principal `UserServiceApplication` |
| `puerto` | `8083` | Puerto local (en el Cloud se usa `PORT`) |

## Variables de entorno del microservicio generado

| Variable | Por defecto | Descripción |
|---|---|---|
| `DB_PASSWORD` | (obligatoria) | Clave del usuario de Oracle |
| `DB_USERNAME` | `ADMIN` | Usuario de Oracle |
| `DB_SERVICE` | `bdfullstack3_low` | Servicio del `tnsnames.ora` del wallet |
| `TNS_ADMIN` | `./wallet` | Carpeta del wallet (en Render: `/etc/secrets`) |
| `PORT` | puerto del arquetipo | Puerto HTTP |
| `CORS_ORIGENES` | `http://localhost:4200` | Orígenes del frontend permitidos |

## Microservicios generados con este arquetipo

- `user-service` (8083): usuarios, roles e inicio de sesión
- `forum-service` (8084): categorías, temas y comentarios
- `moderation-service` (8085): reportes y moderación por parte del administrador
