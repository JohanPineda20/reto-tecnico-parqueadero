# Reto técnico parqueadero

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.5-green)

## Descripción

Este proyecto es una aplicación web desarrollada con Spring Boot que proporciona servicios RESTful para gestionar el control de vehículos en los parqueaderos de varios socios y además almacena un histórico de los vehículos parqueados para la generación de indicadores clave. Se manejan dos roles: ADMIN y SOCIO.

usuario: admin@mail.com 
pass: admin 

El administrador es el único que puede agregar usuarios de tipo SOCIO.

## Tecnologías Utilizadas
- Java 17
- Spring Boot
- Spring Security - JWT
- Spring Data JPA
- PostgreSQL
- Lombok
- Mapstruct
- OpenFeign
- OpenApi
- JavaMail
- Intellij Community
- Postman

## Modelo relacional
![Modelo relacional parqueadero](https://github.com/JohanPineda20/reto-tecnico-parqueadero/assets/80443897/67d76376-e27c-4986-a4bc-d27c33e9b32c)
## Estructura del Proyecto
![arquitectura hexagonal](https://github.com/JohanPineda20/reto-tecnico-parqueadero/assets/80443897/348e80e9-0c9a-4dc4-9d2b-118b01dc4fbb)

Los paquetes están organizados utilizando la arquitectura hexagonal (application, domain, infraestructure) donde en domain están los puertos y toda la lógica de negocio y en infraestructure están las configuraciones y los adaptadores/tecnologia externa
## Instalación
Para desplegar el proyecto en local, sigue los siguientes pasos:
1. Clona este repositorio: `git clone https://github.com/JohanPineda20/reto-tecnico-parqueadero.git`
2. Importa el proyecto en tu IDE favorito y configura las variables de entorno:
   ### parking-api
   ![variables entorno parking-api](https://github.com/JohanPineda20/reto-tecnico-parqueadero/assets/80443897/4af22f3a-0381-4028-acf6-c252eafd821b)

   Crear la base de datos postgresql en local y luego añadir las siguientes variables de entorno:
   - DATASOURCE_URL, DATASOURCE_USERNAME y DATASOURCE_PASSWORD de la base de datos recién creada
   - DATASOURCE_DRIVER el driver de postgreSQL para java
   - JPA_HIBERNATE_DDL_AUTO colocarla como create la primera vez para que se creen las tablas y se importe el archivo import.sql a la base de datos. Luego cambiarla a update o none
   - JWT_SECRET_KEY con una clave de al menos 256 bits para firmar los tokens y JWT_EXPIRATION_TIME con un tiempo de 21600000 que serían 6 horas de expiracion del token requeridos por el proyecto
   - JPA_SHOW_SQL y JPA_PROPERTIES_HIBERNATE_FORMAT_SQL en true o false si quieres ver y formatear las sentencias sql
   ### email-api
   ![variables entorno email-api](https://github.com/JohanPineda20/reto-tecnico-parqueadero/assets/80443897/b6e8ceff-f7eb-40bd-9358-afc4ac2cff03)
    - MAIL_USERNAME con el correo electrónico gmail el cual quieres enviar emails y MAIL_PASSWORD con la contraseña de aplicaciones del correo electrónico (ir a configuraciones de la cuenta gmail y generar una contraseña de aplicaciones)
3. Ejecuta el microservicio de email-api y luego el de parking-api. Para correr los tests, ejecutar lo siguiente: `./gradlew test`
4. Importa la colección de postman y prueba los diferentes endpoints. Tener en cuenta que los endpoints están protegidos asi que primero hay que iniciar sesión y luego añadir el token en cada petición
5. Para obtener más detalles sobre cómo utilizar cada endpoint, consulta la documentación detallada de la API en http://localhost:8090/api/swagger-ui/index.html
