# Fancy Bank

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


## Market

## UI

## Utilities

## Miscellaneous

