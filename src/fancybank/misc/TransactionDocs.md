# [Transaction](Transaction.java) API

## Constructors

```java
Transaction(String operation, String assetName, double amount, String currency, String description)
```

```java
Transaction(String operation, double amount, String currency, String description)
```

```java
Transaction(String operation, String assetName, double amount, String currency, String description, long timestamp)
```

## Methods

**Getters are available for all attributes**

**Comparing Transactions**: ```isAfter(Transaction t) -> boolean``` ```isBefore(Transaction t) -> boolean```