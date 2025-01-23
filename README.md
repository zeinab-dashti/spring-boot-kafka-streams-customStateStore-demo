# spring-boot-kafka-streams-customStateStore-demo

## using external datasource instead of RocksDB by Direct Interaction via Processor API (JdbcTemplate)

## Overview
This is a Java and Spring Boot and Kafka Streams API to illustrate the integration of a custom data source backed by an external database instead of RocksDB.

In this approach,
* we replace RocksDB with another database as the underlying storage for Kafka Streams' state management. This can be particularly useful in several scenarios, especially in a microservices architecture.
* A custom state store is created that directly interacts with the database.
* A custom store ***implements the KeyValueStore interface***, meaning Kafka Streams can use it in place of RocksDB.
* By also ***implementing the StoreBuilder interface***, we enable seamless integration of the custom state store into Kafka Streams, ensuring its lifecycle is managed efficiently while maintaining full compatibility with the framework.
* The custom state store is tightly integrated with Kafka Streams' state management, meaning Kafka Streams can handle fault tolerance and log compaction in a way similar to RocksDB.
* The database access still needs to be managed in terms of performance, as Kafka Streams will frequently access the database.
* The database table schema is provided in [schema.sql](./src/main/resources/schema.sql) frile.
* DB properties are configured using Spring Boot's `spring.datasource` configuration in `application.yml`.
* For simplicity the H2 database is configured as the datasource.

## Prerequisites
* Java 17 or higher
* Maven
* Docker (optional, for running Docker Compose which include Zookeeper and Apache Kafka)


## Running the Application
1. **Start Kafka and Zookeeper by using Docker Compose file in the repository**:
   ```sh
   docker-compose up
   ```

2. **Build**:
   ```sh
   mvn clean package
   ```

3. **Run the application**

   ```sh
   mvn spring-boot:run
   ```
   Once the application is in Running state, it will start to produce dummy data and the Kafka Streams application reads data from `input-topic`, processes it using the custom processor and publishing the data to the supported database while logging soe info, and writes to `output-topic`.
