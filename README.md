# spring-boot-kafka-streams-customStateStore-demo

## Overview
Hands-on example of managing state in Kafka Streams without relying on RocksDB, utilizing external database by direct interaction via processor API (JdbcTemplate).

In this approach, the custom state store is tightly integrated with Kafka Streams' state management, meaning Kafka Streams can handle fault tolerance and log compaction in a way similar to RocksDB. However, the database access still needs to be managed in terms of performance, as Kafka Streams will frequently access the database.

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
