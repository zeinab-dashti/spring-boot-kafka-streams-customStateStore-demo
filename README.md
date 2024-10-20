# spring-boot-kafka-streams-customStateStore-demo


* This example defines a custom persistent state store using Kafka Streams.
* The custom state store is also backed by an external relational database using JdbcTemplate.
* The Kafka Streams application reads data from "input-topic", processes it using the custom state store, and writes to "output-topic".
* The `CustomStateProcessor` class interacts with both the Kafka state store and the external relational database.
* The relational database maintains state consistency using an `INSERT ... ON DUPLICATE KEY UPDATE` approach.

## compare the two approaches for using an external relational database instead of RocksDB in Kafka Streams
### Custom State Store API with Relational Database Integration (RelationalDbKeyValueStore)
* In this approach, a custom state store is created that directly interacts with the relational database. This custom store ***implements the KeyValueStore interface***, meaning Kafka Streams can use it in place of RocksDB.
* A custom state store class (RelationalDbKeyValueStore) is created, which implements methods like put(), get(), etc., by interacting with the relational database. Kafka Streams uses this store to handle state.
* The custom state store is tightly integrated with Kafka Streams' state management, meaning Kafka Streams can handle fault tolerance and log compaction in a way similar to RocksDB.
* This approach is preferred when you need Kafka Streams to manage state with the same features provided by RocksDB, such as local caching and easy fault recovery. It's particularly beneficial for complex applications that require fault tolerance, recovery, and optimized state access.

#### Advantages:
* The state store is managed by Kafka Streams, providing guarantees about state recovery, local caching, and consistency across rebalances.
* The state store is part of Kafka's managed lifecycle, making state recovery easier and more reliable.

#### Disadvantages:
* Implementing a custom state store can be complex and requires understanding Kafka Streams' state store API.
* The relational database access still needs to be managed in terms of performance, as Kafka Streams will frequently access the database.

For the custom state store approach, we essentially replace RocksDB with a relational database as the underlying storage for Kafka Streams' state management. This can be particularly useful in several scenarios, especially in a microservices architecture
* share state access: Unlike RocksDB, which is typically local to the Kafka Streams instance and not easily accessible by other services, using a relational database allows multiple microservices to access the state.
* A relational database, provides long-term persistence, making it more suitable for scenarios where state data needs to be retained, accessed, or queried by other systems, even if Kafka Streams is temporarily down.
* asier State Management for Scaling and Rebalancing:

In a distributed environment, Kafka Streams needs to handle rebalancing when instances are scaled up or down. If using RocksDB, each instance has its local copy of the state, which needs to be rebuilt from the changelog topic during rebalancing.
With a relational database, the state is already externalized and centralized, meaning there's less overhead in managing state across different Kafka Streams instances. This makes scaling and rebalancing easier, as the state does not need to be reloaded locally on each instance.


Yes, exactly! For the custom state store approach, we essentially replace RocksDB with a relational database as the underlying storage for Kafka Streams' state management. This can be particularly useful in several scenarios, especially in a microservices architecture:

Key Advantages in Microservice Architecture:
Shared State Access:

Unlike RocksDB, which is typically local to the Kafka Streams instance and not easily accessible by other services, using a relational database allows multiple microservices to access the state.
For example, if you need to share user session data or aggregated metrics across different microservices, the relational database is a centralized storage that can be queried by other services, providing real-time visibility.
Centralized Data Storage:

In a distributed system with multiple microservices, it's often useful to have centralized state that different services can rely on, especially if the data needs to be accessible beyond the Kafka Streams application.
By replacing RocksDB with a relational database, the state becomes persistent and centralized, providing the benefits of easy data sharing, data analytics, and integration with other microservices.
Persistent State:

RocksDB is primarily used for local storage and is optimized for quick access by Kafka Streams, but it’s not meant for long-term persistence across service instances. If a Kafka Streams instance crashes, it can recover from the changelog topic, but this is more of a restore-and-forget type of persistence.
A relational database, on the other hand, provides long-term persistence, making it more suitable for scenarios where state data needs to be retained, accessed, or queried by other systems, even if Kafka Streams is temporarily down.
Easier State Management for Scaling and Rebalancing:

In a distributed environment, Kafka Streams needs to handle rebalancing when instances are scaled up or down. If using RocksDB, each instance has its local copy of the state, which needs to be rebuilt from the changelog topic during rebalancing.
With a relational database, the state is already externalized and centralized, meaning there's less overhead in managing state across different Kafka Streams instances. This makes scaling and rebalancing easier, as the state does not need to be reloaded locally on each instance.
Interoperability:

The relational database approach provides interoperability between Kafka Streams and other services. Many services can natively interact with relational databases, making it easy to integrate the state data into other systems without needing to extract it from Kafka-specific stores.
This is particularly useful in a microservice setup where different services may be using different programming languages or databases and need a common point of data access.
Trade-Offs:
Performance:

RocksDB is a local, embedded key-value store that is highly optimized for the kinds of read and write operations that Kafka Streams requires, offering low-latency performance.
A relational database introduces network latency and transaction overhead, which could impact performance, especially under heavy load. This trade-off should be considered when deciding between RocksDB and an external database.
Complexity:

Implementing a custom state store that interacts with a relational database adds complexity compared to using RocksDB, which is built-in and optimized for Kafka Streams.
Developers need to implement custom methods (put(), get(), etc.) and handle interactions with the relational database, which adds to the development and maintenance effort.
Database Management:

With a relational database, you also need to manage the database infrastructure, such as setting up, scaling, and maintaining the database server, which could add operational complexity.
RocksDB, being embedded, does not require any external setup, making it simpler to get started.
Use Cases:
Microservices Architecture: If your architecture involves multiple microservices that need to share state data, using a relational database as a custom state store makes the most sense. The state is easily accessible by other services, enabling better data integration and shared access.
Analytics and Reporting: If you need to perform analytics or reporting on the state data, a relational database makes it easier to run SQL queries and generate insights from the state.
Long-Term State Persistence: When you need the state to be persisted beyond the lifetime of Kafka 
----------
