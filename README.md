# Fancy Bank

## Launching Application

[start](start) is a script to launch the application. Depends on geekness, you may launch in UI or in command line mode by commenting out appropiate lines at the very end of the script. Note that command line mode is non-interactive, meaning that you have to deal with main method under [FancyBank.java](src/fancybank/FancyBank.java) yourself ( ͡° ͜ʖ ͡°)

```(bash)
./start # May the money be with you
```

## Accounts

### [Account](src/fancybank/account/Account.java)

Account is an abstract class comprising of methods and attributes common to all account types such as transaction-related methods, id fields, currency. More detail information can be found at [account documentation](src/fancybank/account/AccountDocs.md). Concrete account is expected to inherit from this abstract class to standardize method signature of common mutators.

### [Checking Account](src/fancybank/account/CheckingAccount.java)

Checking account is a concrete class extending Account abstract class. Checking account is the most basic account type that only implements withdraw and deposit in addition to methods provided in Account abstract class. More detail information can be found at [account documentation](src/fancybank/account/AccountDocs.md).

### [Saving Account](src/fancybank/account/SavingAccount.java)

Saving account is a concrete class extending Account abstract class. Saving account imposing a restriction on withdrawal times and has a functionality to pay interest. Mentioned functionalities are not common with other account types, so they are implemented in saving account concrete class instead of Account abstract class. More detail information can be found at [account documentation](src/fancybank/account/AccountDocs.md).

### [Securities Account](src/fancybank/account/SecuritiesAccount.java)

Securities account is a concrete class extending Checking account concrete class. It shares common functionalities with Checking account, thus extending it. Securities account has a functionality to trade assets/stocks, which is implemented within this class along with other asset-related methods. Closing account method is overrided as owned assets/stocks need to be returned, so super method for closing account is called and amends that result with current owned assets/stocks. More detail information can be found at [account documentation](src/fancybank/account/AccountDocs.md).

### [Loan](src/fancybank/account/Loan.java)

Loan is a concrete class extending Saving account concrete class. Loan is similar to Saving account in terms of, for instance, interest. In order to simplify the implementation, Loan account starts out with negative balance indicating a debt. Interest incurred is negative as well as all fees imposed. Paying loan is the same as adding positive amount to the account. With all mentioned characteristics, Loan can inherit from Saving account with withdrawal limit set to large number. More detail information can be found at [account documentation](src/fancybank/account/AccountDocs.md).

## Characters

## IO

### [STDIN Wrapper](src/fancybank/io/StdinWrapper.java)

This class was taken from the previous assignment. It is used to handle input from the user and support checking for some defined special key through method call.

## Market

### [Stocks Data Reader](src/fancybank/market/StocksDataReader.java)

A supposed-to-be collection of static methods implementing different ways to gather stocks data into internal data structore of Stocks market. The current implementation supports only reading from csv; however, extending to fetching data from API or other sources is as easy as adding new static methods with same arguments.

### [Stocks Market](src/fancybank/market/StocksMarket.java)

Stocks market is a concrete singleton class. There MUST exist only one stocks market (*You don't want investors to be confused with multiple markets, do you?*). A single instance of the stocks market ensures that changes can be observed from all clients as we aims for consistency and latest views of the market details.

## UI

## Utilities

### [Error Response](src/fancybank/util/ErrorResponse.java)

### [Tuple](src/fancybank/util/Tuple.java)

Tuple was taken from the previous assignment. It is used to pack two object to be passed together. This implementation is tuple of size 2.

### [Variable](src/fancybank/util/Variable.java)

## Miscellaneous

### [Interest Rate DB Corrept Exception](src/fancybank/misc/InterestRateDBCorruptException.java)

A user-defined runtime exception to indicate more specific error on interest entry corruption occurred in Saving account or Loan. One reason that may raise this exception is that interest rate entries are not chronologically ordered.

### [Transaction](src/fancybank/misc/Transaction.java)

Transaction is a concrete immutable class. It is intended to mimic the real-world transaction: only getter and comparator methods are available, except the final balance setter, which reflects the balance after applying a transaction.

# General Notes

- Our implementation replies on Java implementation of time under ```java.time``` such as ```LocalDateTime```, ```YearMonth``` to abstract out difficulties dealing with time.

- Debug mode can be switched using a flag declared at the top of FancyBank class. If not in debug mode, all actions will use current time of the system and ignore argument-passed time.
