# RabbitMQ & Kafka

## 📑 Table of Contents

- [Postman Collection](#-postman-collection)
- [Prerequisites & Important Warnings](#️-important-warnings)
- [Before Running the Application](#-before-running-the-application)
  - [Why Avro Generation is Necessary](#why-is-this-necessary)
  - [Required Schemas](#required-schemas)
  - [Step-by-Step Generation Process](#-step-by-step-generation-process)
  - [Generated Files Location](#-generated-files-location)
  - [IDE Configuration](#-ide-configuration)
  - [Common Errors Without Generation](#-common-errors-without-generation)
  - [When to Re-generate](#-when-to-re-generate)
  - [Maven Plugin Configuration](#-maven-plugin-configuration)
- [Project Information](#-information)
  - [Key Features](#key-features)
  - [Message Broker Comparison](#message-broker-comparison)
- [Technologies](#-technologies)
- [API Endpoints Summary](#-endpoints-summary)
- [Technical Concepts](#technical-concepts)
  - [RabbitMQ Use Cases](#-rabbitmq-use-cases---technical-concepts)
  - [Kafka Use Cases](#-kafka-use-cases---technical-concepts)
- [Project Structure](#-project-structure)
- [Configuration](#️-configuration)
- [Running the Application](#-running-the-application)
  - [Starting Infrastructure](#starting-infrastructure)
  - [Accessing Management UIs](#accessing-management-uis)
- [RabbitMQ Use Cases & API Examples](#-rabbitmq-use-cases--api-examples)
  - [1. Direct Exchange - E-Commerce Order Processing](#1-direct-exchange---e-commerce-order-processing)
  - [2. Topic Exchange - Dry Cleaning Service](#2-topic-exchange---dry-cleaning-service)
  - [3. Fanout Exchange - Farm Animal Feeding](#3-fanout-exchange---farm-animal-feeding)
  - [4. Headers Exchange - Weather Alert System](#4-headers-exchange---weather-alert-system)
  - [5. Dead Letter Exchange (DLX) - Email Validation](#5-dead-letter-exchange-dlx---email-validation)
  - [6. Priority Queue - Customer Support System](#6-priority-queue---customer-support-system)
  - [7. Manual Acknowledgment - Tea Brewing](#7-manual-acknowledgment---tea-brewing)
  - [8. Load Balancing - Water Distribution](#8-load-balancing---water-distribution)
  - [9. Circuit Breaker - Draw Bridge Control](#9-circuit-breaker---draw-bridge-control)
  - [10. Saga Pattern - Wedding Planning](#10-saga-pattern---wedding-planning)
- [Kafka Use Cases & API Examples](#-kafka-use-cases--api-examples)
  - [1. Basic Producer-Consumer](#1-basic-producer-consumer)
  - [2. Partitioning - Library Books](#2-partitioning---library-books)
  - [3. Offset Management - Baggage Tracking](#3-offset-management---baggage-tracking)
  - [4. Log Compaction - Fruit Prices](#4-log-compaction---fruit-prices)
  - [5. Delivery Semantics - Game Leaderboard](#5-delivery-semantics---game-leaderboard)
  - [6. Retry Mechanism & DLT - Ticket Purchase](#6-retry-mechanism--dlt---ticket-purchase)
  - [7. Transactional Outbox - Player Transfers](#7-transactional-outbox---player-transfers)
  - [8. Avro & Schema Registry - Stock Trading](#8-avro--schema-registry---stock-trading)
  - [9. Kafka Streams - Stock Market Analytics](#9-kafka-streams---stock-market-analytics)
  - [10. SSL Security - Secure Trading](#10-ssl-security---secure-trading)
- [Docker Compose Infrastructure](#-docker-compose-infrastructure)
- [Screenshots](#-screenshots)
  - [RabbitMQ Screenshots](#rabbitmq)
  - [Kafka Screenshots](#kafka)

---

## 📬 Postman Collection

You can explore and test all API endpoints directly using my public Postman collection:

[![Run in Postman](https://run.pstmn.io/button.svg)](https://www.postman.com/maslan34/workspace/spring-messaging/collection/34103070-794bebb3-258e-42ff-9032-f9b32e1400b7?action=share&creator=34103070)

### ⚠️ Important Warnings

- ✅ The requests work correctly on **Postman**.
- ✅ Please make sure that you are running the application with the correct profile.
- ✅ Please ensure that the certificates are generated correctly according to **create_certs.txt** for the SSL application.
- ✅ **Avro Class Generation Required** - See details below.

---

## 🚨 Before Running the Application

**You MUST generate Avro classes before running the application, especially for `kafka-streams-stock` profile.**

### Why is this necessary?

The project uses **Apache Avro** for efficient data serialization. Avro schema files (`.avsc`) are located in `src/main/resources/avro/` directory, but the actual Java classes need to be generated from these schemas.

### Required Schemas

- `StockAvro.avsc` → `StockAvro.java`
- `Company.avsc` → `Company.java`
- `CandleStick.avsc` → `CandleStick.java`
- `StockStats.avsc` → `StockStats.java`

---

## 📋 Step-by-Step Generation Process

### Option 1: Full Build (Recommended)

```bash
mvn clean install
```

### Option 2: Compile Only

```bash
mvn clean compile
```

### Option 3: Generate Avro Classes Only

```bash
mvn avro:schema
```

---

## 📁 Generated Files Location

After running the commands above, Avro classes will be generated at:

```
target/
└── generated-sources/
    └── avro/
        └── com/
            └── KafkaRabbitMQ/
                └── avro/
                    ├── StockAvro.java
                    ├── Company.java
                    ├── CandleStick.java
                    └── StockStats.java
```

---

## 🔧 IDE Configuration

### IntelliJ IDEA

After generation, you may need to refresh your IDE:

1. **Mark as Generated Sources Root**:
   - Right-click on `target/generated-sources/avro`
   - Select `Mark Directory as` → `Generated Sources Root`

2. **Invalidate Caches** (if classes still not recognized):
   - Go to `File` → `Invalidate Caches / Restart`
   - Select `Invalidate and Restart`

### Eclipse

1. Right-click project → `Maven` → `Update Project`
2. Check `Force Update of Snapshots/Releases`
3. Click `OK`

---

## ❌ Common Errors Without Generation

If you try to run the application without generating Avro classes, you'll encounter errors like:

```
java.lang.ClassNotFoundException: com.KafkaRabbitMQ.avro.StockAvro
```

```
cannot find symbol
  symbol:   class StockAvro
  location: package com.KafkaRabbitMQ.avro
```

---

## 🔄 When to Re-generate

Re-run the generation command whenever:

- You modify any `.avsc` schema file
- You pull changes that include schema updates
- You clean the project (`mvn clean`)
- Generated classes are missing from `target/` directory

---

## 📦 Maven Plugin Configuration

The generation is handled by `avro-maven-plugin` configured in `pom.xml`:

```xml
<plugin>
    <groupId>org.apache.avro</groupId>
    <artifactId>avro-maven-plugin</artifactId>
    <version>1.11.3</version>
    <executions>
        <execution>
            <phase>generate-sources</phase>
            <goals>
                <goal>schema</goal>
            </goals>
            <configuration>
                <sourceDirectory>src/main/resources/avro</sourceDirectory>
                <outputDirectory>${project.basedir}/target/generated-sources/avro</outputDirectory>
            </configuration>
        </execution>
    </executions>
</plugin>
```

**Remember**: This step is **MANDATORY** for Kafka Streams functionality. Without it, the application will not compile or run properly!

---

## 📖 Information

**RabbitMQ & Kafka Integration** is a comprehensive Spring Boot application that demonstrates advanced message broker implementations with both RabbitMQ and Apache Kafka. The project showcases real-world messaging patterns, stream processing, event-driven architectures, and various messaging scenarios including transactional messaging, dead letter queues, priority queues, and saga patterns.

### Key Features

#### RabbitMQ Features

- **Multiple Exchange Types**: Direct, Topic, Fanout, and Headers exchanges
- **Dead Letter Exchange (DLX)**: Error handling and message retry mechanisms
- **Priority Queue**: Message prioritization based on urgency
- **Manual Acknowledgment**: Fine-grained control over message processing
- **Load Balancing**: Round-robin message distribution across multiple consumers
- **Circuit Breaker Pattern**: Resilience4j integration for fault tolerance
- **Saga Design Pattern**: Distributed transaction management for complex workflows

#### Kafka Features

- **Kafka Streams**: Real-time stream processing and aggregation
- **Apache Avro**: Schema evolution and efficient serialization
- **Schema Registry**: Centralized schema management
- **At-Most-Once, At-Least-Once, Exactly-Once**: Different delivery semantics
- **Transactional Outbox Pattern**: Reliable event publishing
- **Partitioning & Replication**: Scalability and fault tolerance
- **SSL Security**: Encrypted communication between clients and brokers
- **Retry Mechanism & DLT**: Error handling with Dead Letter Topics
- **Log Compaction**: Topic cleanup policies
- **Offset Management**: Consumer offset control

#### Message Broker Comparison

- **RabbitMQ**: Traditional message broker with rich routing capabilities
- **Kafka**: Distributed streaming platform with high throughput and durability

---

## 🚀 Technologies

- **Java 21** - Programming Language
- **Spring Boot 3.5.0** - Application Framework
- **Spring AMQP** - RabbitMQ Integration
- **Spring Kafka** - Kafka Integration
- **Kafka Streams** - Stream Processing
- **Apache Avro 1.11.3** - Data Serialization
- **Confluent Schema Registry 7.5.0** - Schema Management
- **PostgreSQL** - Relational Database (Outbox Pattern)
- **Resilience4j 3.3.0** - Circuit Breaker Implementation
- **Spring Data JPA** - Data Access Layer
- **Lombok** - Code Generation
- **Maven** - Dependency Management
- **Docker & Docker Compose** - Containerization

---

## 🚀 Endpoints Summary

| Method | URL | Description | Profile | Request Body | Response |
|--------|-----|-------------|---------|--------------|----------|
| **RabbitMQ - Order Processing (Direct Exchange)** | | | | | |
| POST | `/api/payment` | Create order and process payment | rabbit | Order | String |
| **RabbitMQ - Dry Cleaning (Topic Exchange)** | | | | | |
| POST | `/api/dry/` | Separate dress by color and type | rabbit | Dress | String |
| **RabbitMQ - Farm Feeding (Fanout Exchange)** | | | | | |
| POST | `/api/farm` | Feed all animals with forage | rabbit | Forage | String |
| **RabbitMQ - Weather Alert (Headers Exchange)** | | | | | |
| POST | `/api/weather` | Send weather alert by region | rabbit | Weather | - |
| **RabbitMQ - Email Validation (DLX)** | | | | | |
| POST | `/api/email` | Validate and process email | rabbit | Email | String |
| **RabbitMQ - Support System (Priority Queue)** | | | | | |
| POST | `/api/support` | Submit support call with priority | rabbit | Call | - |
| **RabbitMQ - Tea Brewing (Manual ACK)** | | | | | |
| POST | `/api/tea` | Brew tea with retry mechanism | rabbit | Tea | Boolean |
| **RabbitMQ - Water Distribution (Load Balancing)** | | | | | |
| POST | `/api/water` | Distribute water task to consumers | rabbit | Query Params | - |
| **RabbitMQ - Bridge Control (Circuit Breaker)** | | | | | |
| POST | `/api/bridge` | Control bridge based on wind speed | rabbit | WindData | String |
| **RabbitMQ - Wedding Planning (Saga Pattern)** | | | | | |
| POST | `/api/wedding/` | Plan wedding with saga orchestration | rabbit | Wedding | String |
| **Kafka - Basic Messaging** | | | | | |
| POST | `/api/kafka/string` | Send string message to Kafka | kafka | Query Param | - |
| POST | `/api/kafka/json` | Send JSON email message | kafka | Email | String |
| **Kafka - Partitioning** | | | | | |
| GET | `/api/kafka/library/send` | Send book to specific partition | kafka | Query Params | String |
| **Kafka - Offset Management** | | | | | |
| GET | `/api/kafka/baggage/send` | Send random baggage with offset tracking | kafka | - | String |
| **Kafka - Log Compaction** | | | | | |
| GET | `/api/kafka/greengrocer/compact` | Update fruit price (compact policy) | kafka | Query Params | String |
| GET | `/api/kafka/greengrocer/delete` | Update fruit price (delete policy) | kafka | Query Params | String |
| GET | `/api/kafka/greengrocer/last` | Get last message from topic | kafka | Query Param | Map |
| **Kafka - Delivery Semantics** | | | | | |
| GET | `/api/kafka/game/atmost` | Send score with at-most-once | kafka | Query Params | String |
| GET | `/api/kafka/game/atleast` | Send score with at-least-once | kafka | Query Params | String |
| GET | `/api/kafka/game/exactly` | Send score with exactly-once | kafka | Query Params | String |
| **Kafka - Retry Mechanism** | | | | | |
| POST | `/api/kafka/ticket` | Purchase ticket with retry logic | kafka | Ticket | String |
| **Kafka - Transactional Outbox** | | | | | |
| POST | `/api/kafka/transfers` | Create player transfer with outbox | kafka | TransferRequest | FootballPlayer |
| **Kafka - Avro Serialization** | | | | | |
| POST | `/api/kafka/avro` | Send stock data with Avro | kafka-streams-stock | StockAvro | String |
| **Kafka Streams - Stock Market Analytics** | | | | | |
| GET | `/api/kafka/stats` | Get all stock statistics | kafka-streams-stock | - | List\<StockStats\> |
| GET | `/api/kafka/candlestick/{window}/{symbol}` | Get candlestick data | kafka-streams-stock | Path Variables | List\<CandleStick\> |
| GET | `/api/kafka/joined-trades` | Get joined trade information | kafka-streams-stock | - | List\<String\> |
| **Kafka - SSL Security** | | | | | |
| POST | `/api/kafka/trade/long` | Execute long trade with SSL | kafka-security | Query Params + Header | String |
| POST | `/api/kafka/trade/short` | Execute short trade with SSL | kafka-security | Query Params + Header | String |

---

## 📊 RabbitMQ Use Cases - Technical Concepts

| # | Use Case | Exchange Type | Technical Concept | What It Demonstrates | Real-World Application |
|---|----------|---------------|-------------------|----------------------|------------------------|
| 1 | E-Commerce Order | Direct | **Direct Exchange Routing** | Point-to-point message routing using exact routing keys. Messages are routed to queues based on exact key matches. | Order processing pipelines where each step (payment, shipping, receipt) needs dedicated processing |
| 2 | Dry Cleaning | Topic | **Topic-Based Routing with Wildcards** | Pattern matching with wildcards (`*` matches one word, `#` matches zero or more words). Allows flexible message routing based on hierarchical topics. | Content categorization systems, log routing based on severity levels, multi-tenant applications |
| 3 | Farm Feeding | Fanout | **Broadcast Messaging** | Publishes the same message to all bound queues simultaneously. Ignores routing keys - all consumers receive all messages. | System-wide notifications, cache invalidation across multiple services, event broadcasting |
| 4 | Weather Alert | Headers | **Header-Based Routing** | Routes messages based on header attributes rather than routing keys. Supports complex matching logic (ALL headers or ANY header must match). | Complex routing scenarios, multi-criteria message filtering, metadata-based routing |
| 5 | Email Validation | DLX | **Dead Letter Exchange (DLX)** | Automatically routes failed/rejected messages to a separate queue for error handling. Prevents message loss and enables retry mechanisms. | Failed payment processing, invalid data handling, poison message management |
| 6 | Support System | Priority Queue | **Message Prioritization** | Processes high-priority messages before low-priority ones (1-24 priority levels). Ensures critical messages are handled first. | Customer support ticketing, critical alerts, VIP user requests, emergency notifications |
| 7 | Tea Brewing | Manual ACK | **Manual Acknowledgment & Requeue** | Consumer explicitly controls when to acknowledge/reject messages. Enables retry logic by rejecting and republishing messages. | Data processing with quality checks, tasks requiring multiple attempts, idempotent operations |
| 8 | Water Distribution | Load Balancing | **Round-Robin Load Balancing** | Distributes messages evenly across multiple consumers. Enables horizontal scaling and parallel processing. | Task distribution, worker pools, parallel job processing, microservice scaling |
| 9 | Bridge Control | Circuit Breaker | **Resilience Pattern with Fallback** | Monitors service health and prevents cascading failures. Opens circuit on repeated failures, provides fallback mechanisms. | External API integration, database connection management, microservice communication |
| 10 | Wedding Planning | Saga | **Distributed Transaction Management** | Implements long-running transactions across multiple services with compensating transactions for rollback. | Multi-step booking systems, complex workflow orchestration, distributed data consistency |

---

## 📊 Kafka Use Cases - Technical Concepts

| # | Use Case | Topic/Feature | Technical Concept | What It Demonstrates | Real-World Application |
|---|----------|---------------|-------------------|----------------------|------------------------|
| 1 | Basic Messaging | String & JSON | **Serialization Formats** | Demonstrates different data serialization methods. String for simple messages, JSON for structured data with automatic serialization/deserialization. | Simple event publishing, log aggregation, basic inter-service communication |
| 2 | Library Books | Partitioning | **Topic Partitioning** | Distributes messages across multiple partitions for parallel processing. Partition key determines which partition receives the message, enabling ordered processing per key. | User activity tracking (partition by userId), sensor data (partition by deviceId), multi-tenant systems |
| 3 | Baggage Tracking | Offset Management | **Consumer Offset Control** | Tracks consumer position in the topic. Enables message replay, skipping messages, or reprocessing from specific points in time. | Event sourcing, data recovery, debugging production issues, reprocessing historical data |
| 4 | Fruit Prices | Log Compaction | **Retention & Cleanup Policies** | **Compaction**: Keeps only the latest value for each key, deleting older versions. **Delete**: Removes messages after a time-based retention period. Optimizes storage for key-value style data. | Configuration management, latest product prices, user profile updates, state snapshots |
| 5 | Game Leaderboard | Delivery Semantics | **At-Most-Once / At-Least-Once / Exactly-Once** | **At-Most**: No retries, message may be lost. **At-Least**: Retries enabled, may duplicate. **Exactly**: Transactional, no loss or duplication. Different guarantees for different use cases. | Gaming scores (at-most), financial transactions (exactly-once), analytics (at-least) |
| 6 | Ticket Purchase | Retry & DLT | **Error Handling with Dead Letter Topics** | Automatically retries failed messages with configurable backoff. After max retries, moves to Dead Letter Topic (DLT) for manual investigation. | Payment processing failures, external API timeouts, temporary service unavailability |
| 7 | Player Transfers | Outbox Pattern | **Transactional Outbox for Reliability** | Ensures atomicity between database writes and event publishing. Writes to outbox table in same transaction, background job publishes to Kafka. Prevents data inconsistency. | Financial transactions, inventory updates, any scenario requiring database + messaging consistency |
| 8 | Stock Trading | Avro & Schema Registry | **Schema Evolution & Binary Serialization** | Uses Apache Avro for efficient binary format. Schema Registry manages schema versions, enables backward/forward compatibility. Validates data structure at runtime. | High-throughput data pipelines, microservice contracts, event-driven architectures with evolving schemas |
| 9 | Stock Analytics | Kafka Streams | **Stream Processing & Real-Time Aggregation** | **Aggregation**: Computes running totals, averages. **Windowing**: Time-based grouping (1min, 5min, 10min). **Joins**: Enriches streams with reference data. Processes millions of events per second. | Real-time dashboards, fraud detection, IoT data processing, clickstream analytics |
| 10 | Secure Trading | SSL/TLS | **Encrypted Communication** | Encrypts data in transit between clients and brokers using SSL/TLS. Requires keystore/truststore configuration for certificate management. | Financial systems, healthcare data, PII handling, compliance requirements (GDPR, HIPAA) |

---

## 📋 Project Structure

```
src/main/java/com/RabbitMQ_Kafka/
├── Config/
│   ├── rabbit/
│   │   ├── AckConfig.java
│   │   ├── CircuitBreakerConfig.java
│   │   ├── DirectExchangeConfig.java
│   │   ├── DlxConfig.java
│   │   ├── FanoutExchangeConfig.java
│   │   ├── HeadersExchangeConfig.java
│   │   ├── LoadBalancingConfig.java
│   │   ├── PriorityQueueConfig.java
│   │   ├── RabbitMQConfig.java
│   │   ├── SagaDesignConfig.java
│   │   └── TopicExchangeConfig.java
│   └── kafka/
│       ├── KafkaAvroConfig.java
│       ├── KafkaCompactionConfig.java
│       ├── KafkaConfig.java
│       ├── KafkaOffsetConfig.java
│       ├── KafkaOutboxPatternConfig.java
│       ├── KafkaPartitionConfig.java
│       ├── KafkaProcessingConfig.java
│       ├── KafkaRetryMechanismConfig.java
│       ├── KafkaSecurityConfig.java
│       └── KafkaStreamsConfig.java
├── Controller/
│   ├── rabbit/
│   │   ├── DrawBridgeController.java
│   │   ├── DryCleaningController.java
│   │   ├── EmailController.java
│   │   ├── FarmController.java
│   │   ├── PaymentController.java
│   │   ├── SupportCallController.java
│   │   ├── TeaController.java
│   │   ├── WaterController.java
│   │   ├── WeatherController.java
│   │   └── WeddingController.java
│   └── kafka/
│       ├── KafkaBaggageController.java
│       ├── KafkaBasicsController.java
│       ├── KafkaFruitPriceController.java
│       ├── KafkaLibraryController.java
│       ├── KafkaOnlineGameController.java
│       ├── KafkaStockController.java
│       ├── KafkaStockMarketController.java
│       ├── KafkaTicketController.java
│       ├── KafkaTradeController.java
│       └── KafkaTransferController.java
├── messaging/
│   ├── rabbit/
│   │   ├── consumer/
│   │   └── producer/
│   └── kafka/
│       ├── consumer/
│       ├── producer/
│       └── streams/
│           └── StockStreamProcessor.java
├── Service/
│   ├── rabbit/
│   └── kafka/
├── Model/
│   ├── enums/
│   └── [Various POJOs]
├── Repository/
│   ├── OutboxRepository.java
│   └── TransferRepository.java
└── KafkaRabbitMqApplication.java
```

---

## ⚙️ Configuration

### Application Properties

```properties
# Active Profile (choose one)
spring.profiles.active=rabbit
# spring.profiles.active=kafka
# spring.profiles.active=kafka-streams-stock
# spring.profiles.active=kafka-security

# RabbitMQ Configuration
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

# Kafka Configuration
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=my-group
spring.kafka.consumer.auto-offset-reset=earliest

# PostgreSQL Configuration (for Outbox Pattern)
spring.datasource.url=jdbc:postgresql://localhost:5432/kafka_outbox
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
```

---

## 🚀 Running the Application

### Starting Infrastructure

```bash
# Start all services (RabbitMQ, Kafka, PostgreSQL)
docker-compose up -d

# Start only RabbitMQ
docker-compose up -d rabbitmq

# Start only Kafka ecosystem
docker-compose up -d zookeeper kafka1 kafka2 kafka3 schema-registry kafka-ui

# Check service status
docker-compose ps

# View logs
docker-compose logs -f rabbitmq
docker-compose logs -f kafka1
```

### Running the Application

```bash
# Build the project (generates Avro classes)
mvn clean install

# Run with RabbitMQ profile
mvn spring-boot:run -Dspring-boot.run.profiles=rabbit

# Run with Kafka profile
mvn spring-boot:run -Dspring-boot.run.profiles=kafka

# Run with Kafka Streams profile
mvn spring-boot:run -Dspring-boot.run.profiles=kafka-streams-stock

# Run with Kafka SSL profile
mvn spring-boot:run -Dspring-boot.run.profiles=kafka-security
```

### Accessing Management UIs

- **RabbitMQ Management**: http://localhost:15672 (guest/guest)
- **Kafka UI**: http://localhost:8081
- **Schema Registry**: http://localhost:8082

---

## 📬 RabbitMQ Use Cases & API Examples

### 1. Direct Exchange - E-Commerce Order Processing

**Technical Concept**: Direct exchange routes messages to queues based on exact routing key matches, enabling point-to-point communication.

```bash
curl -X POST http://localhost:8080/api/payment \
  -H "Content-Type: application/json" \
  -d '{
    "orderId": "ORD-001",
    "productName": "Laptop",
    "quantity": 1
  }'
```

**Flow**: Payment Queue → Shipping Queue → Receipt Queue (Sequential processing)

### 2. Topic Exchange - Dry Cleaning Service

**Technical Concept**: Topic exchange uses wildcard patterns for flexible routing (`*` = one word, `#` = zero or more words).

```bash
curl -X POST http://localhost:8080/api/dry/ \
  -H "Content-Type: application/json" \
  -d '{
    "color": "BLACK",
    "width": 50.0,
    "height": 100.0,
    "isDelicate": false
  }'
```

**Routing Keys**:
- `color.black.normal` → Black queue
- `color.white.normal` → White queue
- `color.*.delicate` → Delicate queue
- `color.#` → All colors queue

### 3. Fanout Exchange - Farm Animal Feeding

**Technical Concept**: Fanout broadcasts messages to all bound queues, ignoring routing keys entirely.

```bash
curl -X POST http://localhost:8080/api/farm \
  -H "Content-Type: application/json" \
  -d '{
    "forageName": "Fresh Hay",
    "forageQuality": "Premium"
  }'
```

**Behavior**: Single message → Both Cow and Horse consumers receive it simultaneously

### 4. Headers Exchange - Weather Alert System

**Technical Concept**: Routes based on message headers with `whereAll()` (AND logic) or `whereAny()` (OR logic) matching.

```bash
curl -X POST http://localhost:8080/api/weather \
  -H "Content-Type: application/json" \
  -d '{
    "cityName": "Izmir",
    "region": "EGE",
    "event": "storm",
    "temperature": 25.5
  }'
```

**Matching Logic**:
- `whereAll(region=EGE, event=storm)` → Both headers must match
- `whereAny(region=EGE, event=storm)` → At least one header must match

### 5. Dead Letter Exchange (DLX) - Email Validation

**Technical Concept**: Failed/rejected messages automatically route to DLX for error handling, preventing message loss.

```bash
# Valid email
curl -X POST http://localhost:8080/api/email \
  -H "Content-Type: application/json" \
  -d '{"email": "user@example.com"}'

# Invalid email (goes to DLX)
curl -X POST http://localhost:8080/api/email \
  -H "Content-Type: application/json" \
  -d '{"email": "invalid-email"}'
```

### 6. Priority Queue - Customer Support System

**Technical Concept**: Messages processed based on priority level (1-24), ensuring critical messages handled first.

> ⚠️ **Note**: To properly observe the behavior of this application, run this request in Postman with 10 iterations.

```bash
# Low priority
curl -X POST http://localhost:8080/api/support \
  -H "Content-Type: application/json" \
  -d '{"message": "General inquiry", "priority": 5}'

# High priority
curl -X POST http://localhost:8080/api/support \
  -H "Content-Type: application/json" \
  -d '{"message": "System down!", "priority": 24}'
```

### 7. Manual Acknowledgment - Tea Brewing

**Technical Concept**: Consumer controls when to ACK/NACK messages, enabling custom retry logic.

```bash
curl -X POST http://localhost:8080/api/tea \
  -H "Content-Type: application/json" \
  -d '{"elapsedTime": 30.0}'
```

**Logic**: If time < 45s → NACK + republish with +0.1s. If time >= 45s → ACK.

### 8. Load Balancing - Water Distribution

**Technical Concept**: Multiple consumers process messages in round-robin fashion for horizontal scaling.

> ⚠️ **Note**: To properly observe the behavior of this application, run this request in Postman with 10 iterations.

```bash
curl -X POST "http://localhost:8080/api/water?faucet=Faucet-A&liter=10"
```

**Distribution**: Consumers A and B extract water simultaneously.

### 9. Circuit Breaker - Draw Bridge Control

**Technical Concept**: Monitors service health; opens circuit on failures to prevent cascading issues.

```bash
curl -X POST http://localhost:8080/api/bridge \
  -H "Content-Type: application/json" \
  -d '{"speed": 30.5, "timestamp": "2024-01-15T10:30:00"}'
```

**Circuit Breaker States**:
- **CLOSED**: Wind speed OK, bridge open
- **OPEN**: Wind speed high, bridge closed
- **HALF_OPEN**: Testing if conditions improved, partially open

### 10. Saga Pattern - Wedding Planning

**Technical Concept**: Distributed transactions with compensating transactions for rollback across services.

```bash
curl -X POST http://localhost:8080/api/wedding/ \
  -H "Content-Type: application/json" \
  -d '{
    "isVenueSuccess": true,
    "isCateringSuccess": true,
    "isEntertainmentSuccess": false,
    "isInvitationSuccess": true
  }'
```

**Saga Flow**:
1. **Venue** reservation → Success → Publish to catering
2. **Catering** arrangement → Success → Publish to entertainment
3. **Entertainment** booking → **Failure** → Trigger rollback
4. **Rollback**: Entertainment → Catering → Venue

---

## 📬 Kafka Use Cases & API Examples

### 1. Basic Producer-Consumer

**Technical Concept**: Demonstrates different serialization formats (String vs JSON) for Kafka messages.

```bash
# String message
curl -X POST "http://localhost:8080/api/kafka/string?msg=Hello Kafka"

# JSON message
curl -X POST http://localhost:8080/api/kafka/json \
  -H "Content-Type: application/json" \
  -d '{"email": "user@example.com"}'
```

### 2. Partitioning - Library Books

**Technical Concept**: Messages distributed across partitions based on key, enabling parallel processing and ordering guarantees per partition.

```bash
curl -X GET "http://localhost:8080/api/kafka/library/send?genre=Fiction&book=1984"
```

**Partitioning**: Genre acts as partition key → Same genre always goes to same partition

### 3. Offset Management - Baggage Tracking

**Technical Concept**: Consumer tracks position in topic via offsets, enabling replay, skip, or reprocess from specific points.

```bash
curl -X GET http://localhost:8080/api/kafka/baggage/send
```

**Offset Control**: Can reset to earliest, latest, or specific timestamp for reprocessing

### 4. Log Compaction - Fruit Prices

**Technical Concept**: **Compaction** keeps only latest value per key. **Delete** policy removes messages after retention period.

```bash
# Compaction policy (keeps latest price per fruit)
curl -X GET "http://localhost:8080/api/kafka/greengrocer/compact?fruit=Apple&price=5.99"

# Delete policy (removes after 30 seconds)
curl -X GET "http://localhost:8080/api/kafka/greengrocer/delete?fruit=Banana&price=2.99"

# Read last messages
curl -X GET "http://localhost:8080/api/kafka/greengrocer/last?topicName=greengrocer-compact"
```

### 5. Delivery Semantics - Game Leaderboard

**Technical Concept**: Three delivery guarantees with different trade-offs.

```bash
# At-Most-Once: No retry, may lose
curl -X GET "http://localhost:8080/api/kafka/game/atmost?playerId=player1&points=100"

# At-Least-Once: Retry enabled, may duplicate
curl -X GET "http://localhost:8080/api/kafka/game/atleast?playerId=player2&points=200"

# Exactly-Once: Transactional, no loss or duplication
curl -X GET "http://localhost:8080/api/kafka/game/exactly?playerId=player3&points=300"
```

### 6. Retry Mechanism & DLT - Ticket Purchase

**Technical Concept**: Failed messages retry with backoff, then move to Dead Letter Topic after max attempts.

```bash
curl -X POST http://localhost:8080/api/kafka/ticket \
  -H "Content-Type: application/json" \
  -d '{"userId": "user123", "busId": "BUS-001", "seatNo": "A15"}'
```

**Flow**:
1. `ticket-purchase-requests` → Process (60% failure rate simulation)
2. If fails → `ticket-purchase-retry` (Max 3 attempts)
3. If still fails → `ticket-purchase-dlt` (Dead Letter Topic)

### 7. Transactional Outbox - Player Transfers

**Technical Concept**: Atomically writes to database and outbox table in single transaction, background job publishes to Kafka.

```bash
curl -X POST http://localhost:8080/api/kafka/transfers \
  -H "Content-Type: application/json" \
  -d '{
    "playerName": "Lionel Messi",
    "fromTeam": "PSG",
    "toTeam": "Inter Miami",
    "fee": 50000000
  }'
```

**Outbox Pattern**:
1. Save transfer to `football_players` table
2. Save event to `outbox_events` table (Same transaction)
3. Background job polls outbox every 5 seconds
4. Publish unprocessed events to Kafka
5. Mark events as processed

**Database Schema**:
```sql
CREATE TABLE football_players (
    id BIGSERIAL PRIMARY KEY,
    player_name VARCHAR(255),
    from_team VARCHAR(255),
    to_team VARCHAR(255),
    fee DECIMAL
);

CREATE TABLE outbox_events (
    id BIGSERIAL PRIMARY KEY,
    aggregate_type VARCHAR(255),
    aggregate_id BIGINT,
    event_type VARCHAR(255),
    payload TEXT,
    processed BOOLEAN DEFAULT FALSE
);
```

### 8. Avro & Schema Registry - Stock Trading

**Technical Concept**: Binary serialization with schema validation and evolution support via Schema Registry.

```bash
curl -X POST http://localhost:8080/api/kafka/avro \
  -H "Content-Type: application/json" \
  -d '{"symbol": "AAPL", "price": 175.50, "volume": 1000}'
```

**Benefits**: Compact format, schema versioning, backward/forward compatibility

### 9. Kafka Streams - Stock Market Analytics

**Technical Concept**: Real-time stream processing with aggregations, windowing, and joins.

```bash
# Get aggregated statistics
curl -X GET http://localhost:8080/api/kafka/stats

# Get candlestick data (1min/5min/10min windows)
curl -X GET http://localhost:8080/api/kafka/candlestick/1min/AAPL

# Get joined trades (stream-table join)
curl -X GET http://localhost:8080/api/kafka/joined-trades
```

**Operations**:
- Aggregation (sum, avg, count)
- Windowing (time-based grouping 1m/5m/10m candlestick)
- Joins (enrich streams with reference data)

### 10. SSL Security - Secure Trading

**Technical Concept**: Encrypts data in transit using SSL/TLS with keystore/truststore certificates.

```bash
# Long trade
curl -X POST "http://localhost:8080/api/kafka/trade/long?symbol=TSLA&quantity=100&price=250.50" \
  -H "userId: trader123"

# Short trade
curl -X POST "http://localhost:8080/api/kafka/trade/short?symbol=AMZN&quantity=50&price=180.75" \
  -H "userId: trader456"
```

**Security**: Client authenticates broker, data encrypted end-to-end

---

## 🐳 Docker Compose Infrastructure

### Services Overview

The project includes a comprehensive Docker Compose setup with the following services:

- **RabbitMQ** - Message broker (Port 5672, Management UI 15672)
- **PostgreSQL** - Database for outbox pattern (Port 5432)
- **Zookeeper** - Kafka coordination service (Port 2181)
- **Kafka Brokers 1-3** - Kafka cluster (Ports 9092-9094)
- **Kafka Broker 4 with SSL** - Secure Kafka instance (Port 9095)
- **Schema Registry** - Avro schema management (Port 8082)
- **Kafka UI** - Web interface for Kafka (Port 8081)

### Quick Start Commands

```bash
# Start all services
docker-compose up -d

# Start only RabbitMQ
docker-compose up -d rabbitmq

# Start only Kafka ecosystem
docker-compose up -d zookeeper kafka1 kafka2 kafka3 schema-registry kafka-ui

# Stop all services
docker-compose down

# View logs
docker-compose logs -f [service-name]

# Check service health
docker-compose ps
```

---

## 📸 Screenshots

<details>
<summary><strong>Click to view figures</strong></summary>

### RabbitMQ

<details>
<summary><strong>Dead Letter Exchanges (DLX)</strong></summary>

<img width="1900" height="809" alt="dlx-2" src="https://github.com/user-attachments/assets/fb91a5ec-da3f-4de1-95df-ae93350d3032" />

</details>

<details>
<summary><strong>Circuit Breaker</strong></summary>

<img width="1742" height="823" alt="cb" src="https://github.com/user-attachments/assets/b136aa1a-8671-4648-8955-e1561dcd8f50" />

</details>

<details>
<summary><strong>Load Balancing</strong></summary>

<img width="1794" height="909" alt="loadbalancing" src="https://github.com/user-attachments/assets/3ad07ac7-26ea-4b1b-bda1-31eae3082ab0" />

</details>

<details>
<summary><strong>Saga Pattern</strong></summary>
  
<img width="1815" height="890" alt="saga" src="https://github.com/user-attachments/assets/dc899e70-5cae-4c27-9887-1ca214fc8600" />

</details>

### Kafka

<details>
<summary><strong>Offset Management</strong></summary>

<img width="1838" height="966" alt="offset" src="https://github.com/user-attachments/assets/8cf414ce-3ba8-4cd8-8ad1-92b34ca24474" />

</details>

<details>
<summary><strong>Retry Mechanism</strong></summary>

<img width="984" height="244" alt="retry" src="https://github.com/user-attachments/assets/bf2445af-5e51-4162-86f9-20c6afad3a27" />

</details>

<details>
<summary><strong>Kafka Streams - 5 Minute Window</strong></summary>

<img width="1847" height="954" alt="5min" src="https://github.com/user-attachments/assets/d087ed4e-ea2c-49cd-b166-b40c3e8b237a" />

</details>

<details>
<summary><strong>Kafka Streams - 10 Minute Window</strong></summary>

<img width="1792" height="941" alt="10min" src="https://github.com/user-attachments/assets/8518806a-d9df-4ad2-9381-2e870ea6e467" />

</details>

</details>

---
