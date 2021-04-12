# DeuStock 

![example workflow](https://github.com/futotta-risu/DeuStock/actions/workflows/maven.yml/badge.svg)

Simulador de broker.

## Installation 
### Server
1. Initialize MySql Server
2. Run sql statements from the "create-DeustockBD.sql"
3. mvn clean compile
4. mvn exec:java

### Cliente
1. mvn clean compile
2. mvn exec:java


## Technologies used
  * Eclipse Jersey as REST framework
  * JavaFX for the client GUI.
  * JDO for database access.
  * Jacoco, JUnit 4  for testing.
  * Jreddit, twitter4j, twitter4j-stream, YahooFinanceAPI for data scraping.
  * Stanford-corenlp for data analysis. 
  
