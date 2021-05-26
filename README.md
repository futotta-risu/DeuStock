# DeuStock 

![example workflow](https://github.com/futotta-risu/DeuStock/actions/workflows/maven.yml/badge.svg)
[![codecov](https://codecov.io/gh/futotta-risu/DeuStock/branch/main/graph/badge.svg?token=1PB4Q43DD5)](https://codecov.io/gh/futotta-risu/DeuStock)

DeuStock es un aplicación de bolsa interactiva que ofrece al usuario la capacidad de simular cuentas de inversión con valores de activos reales.


## Table of Contents
1. [Ejecución](#Ejecucion)
2. [API](#API)
3. [Librerías](#Librerias)

## Ejecucion 


### Server
1. Iniciar un servidor SQL
2. Ejecutar el archivo SQL situado en "DeuStock/db/create-DeustockBD.sql"
3. Desde la carpeta del servidor ejecutar los siguientes comandos de maven:
   
        C:\**\DeuStock\DeuStock> mvn clean compile
        C:\**\DeuStock\DeuStock> mvn exec:java

### Cliente

1. Desde la carpeta del cliente ejecutar los siguientes comandos de maven

        C:\**\DeuStock\DeustockClient> mvn clean compile
        C:\**\DeuStock\DeustockClient> mvn exec:java


## API

En esta sección se muestra el mapa de la API

* **(Auth)** /auth/
    * **(Login)** */login/
      * **GET** |  /{username}/{password} | Devuelve un _token_ de usuario 
    * **(Register)** */register/ 
      * **POST** | JSON(User) |  Registra a un usuario
* **(Help)** /help/
    * **(FAQ)** */faq/list/
        * **GET** | Devuelve la lista de preguntas frecuentes
* **(User)** /tpuser/
    * **GET** | */{username}/ | Devuelve los detalles de un usuario
    * **PUT** | JSON(User) & Token | Actualiza la información de un usuario 
    * **DELETE** | Token | Elimina a un usuario
* **(Investment)** /stock/
    * **(Operation)** */operation/
        * **(Close)** */close/
            * **POST** | OperationID & Token | Cierra una operación
        * **(Open)** */open/
            * **GET** | */{operation}/{symbol}/{amount} & Token | Abre una operación
    * **(Stock)** */stock/
        * **(Detail)** */detail/
            * **GET** | */{symbol}/{interval} | Obtiene los detalles de una acción
        * **(List)** */list/
            * **GET** | */{size} | Devuelve la lista de acciones con el nombre _size_
        * **(Search)** */search/
            * **GET** | */{symbol} | Devuelve el stock solicitado

## Librerías
* **Framework REST:** Eclipse Jersey como framework REST
* **Interfaz:** JavaFX
* **DRMS:** Datanucleus JDO.
* **Unit Testing:** JUnit 4 & 5, Mockito
* **Cobertura de tests:** Jacoco
* **Test de Rendimiento:** ContiPerf
* **Data Scrapping** 
    * **Redes Sociales:** Jreddit, Twitter4j
    * **Exchanges:**  YahooFinanceAPI
* **NLP:** Stanford CoreNLP

  
