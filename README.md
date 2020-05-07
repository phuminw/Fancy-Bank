# Fancy Bank
## Team Info
Yizhou Mao <br />
Phumin Walaipatchara<br />
Jinshu Yang<br />
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

### [Character](src/fancybank/character/Character.java)

Character is an abstract class comprising of methods and attributes common to all character types such as realName, userName, password and so on. By implement Character as an abstract class, it will require all subclasses extended from Character class to have the methods and features defined in Character class. Concrete character is expected to inherit from this abstract class to standardize method signature of common mutators. In this way, it enhances code efficiency and standards.

### [Customer](src/fancybank/character/Customer.java)
Customer is a concrete class extending Character abstract class. With the features and methods implemented in Character class, Customer class includes features and methods that are specific to Customer, such as viewTransaction, deposit, withdraw and so on. It serves as a class to the achieve the functionality of Customer. Instead of directly calling the methods in Customer class,the system will use the methods in FancyBank class to input parameters to the corresponding methods in Customer class. In this way, it will limit the user's access to methods in other files and enhance the control of developer over this program. Similiar structure is enforced for Manager class.

### [Manager](src/fancybank/character/Manager.java)
Manager is a concrete class extending Character abstract class. With the features and methods implemented in Character class, Manager class includes features and methods that are specific to Manager, such as report, checkCustomer and so on. It serves as a class to the achieve the functionality of managers. 

## IO

### [STDIN Wrapper](src/fancybank/io/StdinWrapper.java)

This class was taken from the previous assignment. It is used to handle input from the user and support checking for some defined special key through method call.

## Market

### [Stocks Data Reader](src/fancybank/market/StocksDataReader.java)

A supposed-to-be collection of static methods implementing different ways to gather stocks data into internal data structore of Stocks market. The current implementation supports only reading from csv; however, extending to fetching data from API or other sources is as easy as adding new static methods with same arguments.

### [Stocks Market](src/fancybank/market/StocksMarket.java)

Stocks market is a concrete singleton class. There MUST exist only one stocks market (*You don't want investors to be confused with multiple markets, do you?*). A single instance of the stocks market ensures that changes can be observed from all clients as we aims for consistency and latest views of the market details.

## UI

### [BankUI](src/fancybank/ui/BankUI.java)

BankUI can is the main UI page of this application. It allows the users to switch between different pages and also provides different functions for users. It is the main class that stores the instance of fancybank and it can call all the methods from this class.

### [AmountTextField](src/fancybank/ui/AmountTextField.java)

The user in this application can enter both curreny and amount in order to continue the transaction. Thus, this component can be reused in many places. It contains two parts of input (currency and amount).

### [BankPanel](src/fancybank/ui/BankPanel.java)
This class is the superclass of all other panel classes. It provides the referece to the instance of bankUI.

### [CreateAccountPanel](src/fancybank/ui/CreateAccountPanel.java)
This class provides with users about functions of creating new accounts. For example, users can create checking, saving, and securities accounts.

### [Log](src/fancybank/ui/Log.java)
This class can pop a separate window which used to display all the transactions happened under this user or manager.

### [ManagerPanel](src/fancybank/ui/ManagerPanel.java)
This is the main panel class for manager page. It allows manager to see all accounts, users, pay intersts and also log all details.

### [Message](src/fancybank/ui/Message.java)
This is a sparate window allows to present the errors or message for users.

### [NormalAccountInfoPanel](src/fancybank/ui/NormalAccountInfoPanel.java)
This is the account panel that displays all details of someone's account. This is a reusable component (checking and saving) so this is a single component.

### [SecurityAccountInfoPanel](src/fancybank/ui/SecurityAccountInfoPanel.java)
This is the account panel that displays all details of someone's account. This is a reusable component (security) so this is a single component.

### [SingleNormalAccountComponent](src/fancybank/ui/SingleNormalAccountComponent.java)
This is part of NormalAccountInfoPanel component. It is the single component of each account. It can be used. It can display all necessary details of an account.

### [SingleSecurityAccountComponent](src/fancybank/ui/SingleSecurityAccountComponent.java)
This is part of SecurityAccountInfoPanel component. It is the single component of each account. It can be used. It can display all necessary details of an account.

### [UserDetailPanel](src/fancybank/ui/UserDetailPanel.java)
This is the main java panel that can provides the users with all the actiions they can perform such as creat new account, withdraw cash and deposit money. 

### [UserLoginPanel](src/fancybank/ui/UserLoginPanel.java)
This is the enter panel that allows the users to create or login the account.

### [UserUI](src/fancybank/ui/UserUI.java)
This is a reusable component for manager. This is a button that the manager can click to see different user's account details.

### [WelcomePage](src/fancybank/ui/WelcomePage.java)
This is the init page of the whole application. In this page, different role of users can choose to login as customer or login as manager.

## Utilities

### [Error Response](src/fancybank/util/ErrorResponse.java)
This is a wrapper class for String in java. It can be passed as reference to other methods. It can be easily edited and the fronend people can easily process the error message.

### [Tuple](src/fancybank/util/Tuple.java)

Tuple was taken from the previous assignment. It is used to pack two object to be passed together. This implementation is tuple of size 2. This class gives developer more freedom towards types. In this system, especially in the Variable class which is functioned as the loading and storing informations from and to the database, the developer can create tuple with different combinations simply as a key feature in Hashmap. In this way, we can use different input parameters to check and get objects.

### [Variable](src/fancybank/util/Variable.java)

Variable class serves as a class connects to the database. It contains the structures and methods dealing with informations in database. For example, every time when we start the system, the loading method in Variable class will be called to load each characters, accounts, stocks, transactions. And if a new operation is enforces, update methods in Variable class will be called to update the corresponding files in database. In this way, it enhances the simplicity in other files when dealing with updating data and the actual-complicated process is hidden and process is left in Variable class to finish. Additionally, Variable also contians structures to contains information, such as Lists and HashMaps. For example, other files can use these structure to check whether a specific customer is existed. With the setup of Variable class, we make the division of functionality more clearly. Everything associated with database is implemented in Variable class and it is also easier to modify and update in the future.

## Miscellaneous

### [Interest Rate DB Corrept Exception](src/fancybank/misc/InterestRateDBCorruptException.java)

A user-defined runtime exception to indicate more specific error on interest entry corruption occurred in Saving account or Loan. One reason that may raise this exception is that interest rate entries are not chronologically ordered.

### [Transaction](src/fancybank/misc/Transaction.java)

Transaction is a concrete immutable class. It is intended to mimic the real-world transaction: only getter and comparator methods are available, except the final balance setter, which reflects the balance after applying a transaction.

# General Notes

- Our implementation replies on Java implementation of time under ```java.time``` such as ```LocalDateTime```, ```YearMonth``` to abstract out difficulties dealing with time.

- Debug mode can be switched using a flag declared at the top of FancyBank class. If not in debug mode, all actions will use current time of the system and ignore argument-passed time.
