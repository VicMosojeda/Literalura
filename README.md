Proyecto Literatura Alura
==========================

Este proyecto está desarrollado en Java utilizando Spring Boot para la gestión de una base de datos de literatura.

Requisitos
----------
- Java 17
- Docker

Configuración de la Base de Datos
---------------------------------
Para este proyecto, se utiliza PostgreSQL a través de una imagen Docker. A continuación, los pasos para descargar e iniciar la base de datos:

1. Descargar la imagen de PostgreSQL desde Docker Hub:

    ```bash
    docker pull postgres
    ```

2. Iniciar un contenedor Docker con PostgreSQL:

    ```bash
    docker run --name mi-postgres -e POSTGRES_PASSWORD=12345678 -p 5432:5432 -d postgres```

3. Verificar el estado del contenedor:

    ```bash
    docker ps
    ```

Esto iniciará un contenedor Docker con PostgreSQL y lo dejará en ejecución.



Ejecución del Proyecto
-----------------------
Para ejecutar el proyecto de Spring Boot:

1. Clona el repositorio desde GitHub:


    ```bash
    git clone https://github.com/metadeth/literatura.git
    ```

2. Navega hasta el directorio del proyecto:

    ```bash
    cd literatura
    ```

3. Compila el proyecto y ejecuta con Maven:

    ```bash
    mvn clean install
    ```
