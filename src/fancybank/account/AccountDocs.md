# Account API

## All [Account](Account.java)

By default, there are 3 currencies when creating an account: USD, GBP, CNY, except securities account that only has USD. All account types have the following methods

### Methods

**Retrieve Balance**: ```getBalance(String currency) -> double```
**Retrieve Open Date**: ```getOpenedDate() -> LocalDate```
**Retrieve Close Date**: ```getClosedDate() -> LocalDate```
**Retrieve Transaction**
```getTransaction(String currency, int i) -> Transaction``` *get at index i*
```getTransactionsAfter(String currency, long timestamp) -> List<Transaction>``` *All transactions after timestamp*
```getTransactionsBefore(String currency, long timestamp) -> List<Transaction>``` *All transactions before timestamp*
```getTransactions(String currency) -> List<Transaction>```
**Add New Currency**: ```addCurrency(String currency) -> boolean```
**Remove Existing Currency**: ```removeCurrency(String currency) -> Tuple<Double, List<Transaction>>``` *Return balance and all transactions*
**Retrieve Associated Currency**: ```getCurrencies() -> Set<Currency>```
**Add Transaction**: ```addTransaction(Transaction t) -> boolean``` *Add a transaction and process accordingly (see supported transaction type under Transaction.java)*
**Close Account**: ```closeAccount() -> Tuple<Set<Entry<Currency, Double>>, Set<Entry<String, Double>>>``` *Impose close fee and return a tuple of current balance of each currency and owned stocks for securities account*

## [Checking Account](CheckingAccount.java)

### Constructors

```java
CheckingAccount() // Zero balance. Already imposed open fee.
```

```java
CheckingAccount(double balance, LocalDate openedDate, LocalDate closedDate) // For debug
```

### Methods

**Deposit Money**: ```addBalance(double amount, String currency, String description, LocalDateTime time) -> boolean``` *Ignore last param if not in debug mode*
**Withdraw Money**: ```deductBalance(double amount, String currency, String description, LocalDateTime time) -> boolean``` *Ignore last param if not in debug mode*

## [Saving Account](SavingAccount.java)

### Constructors

```java
SavingAccount(double interest, int withdrawCountLimit) // Zero balance. Already imposed open fee.
```

```java
SavingAccount(double balance, double interest, int withdrawCountLimit, LocalDate openedDate, LocalDate closedDate) // For debug
```

### Methods

**Retrieve Current Interest Rate**: ```getInterestRate() -> Double```
**Retrieve Withdraw Count of Current Month**: ```getWithdrawCount() -> Integer```
**Retrieve Withdraw Count Limit**: ```getWithdrawCountLimit() -> int```
**Retrieve Interest Earned in Specified Year-Month**: ```getIntEarned(YearMonth ym) -> Double```
**Retrieve Total Interest Earned**: ```getIntEarnedTotal() -> Double```
**Set New Interest Rate**: ```setIntRate(double interest, YearMonth start, YearMonth end) -> boolean``` *Ignore second and third params if not in debug mode*
**Deposit Money**: ```addBalance(double amount, String currency, String description, LocalDateTime time) -> boolean``` *Ignore last param if not in debug mode*
**Withdraw Money**: ```deductBalance(double amount, String currency, String description, LocalDateTime time) -> boolean``` *Ignore last param if not in debug mode*
**Calculate Interest**: ```calculateInterest(LocalDate date)``` *Main Logic **should** call if needed before retriving current balance. Param is ignored if not in debug mode*

## [Securities Account](SecuritiesAccount.java)

Securities account operates on USD. In the other words, assets/stocks can be bought in exchange of USD only. Available assets/stocks is in [stocks.csv](../db/stocks/stocks.csv)

### Constructors

```java
SecuritiesAccount() // Zero balance
```

```java
SecuritiesAccount(double balance, LocalDate openedDate, LocalDate closedDate)// For debug
```

### Methods

**Purchase Asset**: ```buyAsset(String assetName, double shares, LocalDateTime time) -> boolean``` *Last param is ignored if not in debug mode*
**Sell Asset**: ```sellAsset(String assetName, double shares, LocalDateTime time) -> boolean``` *Last param is ignored if not in debug mode*
**Retrieve Owned Assets/Stocks**: ```getOwnedStocks() -> Set<Entry<String, Double>>```
**Close Account (Override)**: ```closeAccount() -> Tuple<Set<Entry<Currency, Double>>, Set<Entry<String, Double>>>```

## [Loan](Loan.java)

For now, only support USD currency. ```currency``` param is **ignored** at all.

### Constructors

```java
Loan(String currency, double balance, double interest)
```

```java
Loan(String currency, double balance, double interest, LocalDate openedDate, LocalDate closedDate) // For debug
```

### Methods

**Pay Off Loan**: ```payOff(String currency, double amount, LocalDateTime time) -> boolean```
**Calculate Interest**: ```calculateInterest(LocalDate date)``` *Main Logic **should** call if needed before retriving current balance. Param is ignored if not in debug mode*
**Close Account**: ```closeLoanAccount() -> Double``` *Return remaining balance in negative number (debt)*