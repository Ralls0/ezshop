# Design Document 

- [Design Document](#design-document)
- [High Level Design](#high-level-design)
- [Low Level Design](#low-level-design)
- [Verification Traceability Matrix](#verification-traceability-matrix)
- [Verification Sequence Diagrams](#verification-sequence-diagrams)
    - [Add New Product](#add-new-product)
    - [Issue and Pay Order](#issue-and-pay-order)
    - [Define Costumer and Card](#define-costumer-and-card)
    - [Modify Customer](#modify-customer)
    - [Manage Sale with Credit Card](#manage-sale-with-credit-card)
    - [Manage Return with Credit Card](#manage-return-with-credit-card)
    - [Accounting](#accounting)

Authors: 
* S281564 Marco Manco
* S290136 Giovanni Pollo
* S292477 Matteo Quarta
* S292602  Davide Fersino
  
Date: 30/04/2021

Version: 1.0

# High Level Design 

We chose the 3-Tier Architectural Pattern


```plantuml

[GUI] as GUI

[Model and Logic Application] as MLA

[Data Storage] as DS


GUI --> MLA
MLA --> DS

note "Contains API, \nExceptions and Classes" as N2
N2 ..left.. MLA 
```



# Low Level Design

```plantuml
@startuml
class AccountBook {
    double totalBalance
    List<BalanceOperation> operations

    + List<BalanceOperation> getCreditsAndDebits(LocalDate from, LocalDate to)
    + boolean recordBalanceUpdate(double toBeAdded)
    + double computeBalance()
    + boolean addBalanceOperation()
    + boolean removeBalanceOperation()
    
}

class BalanceOperation {
    String description
    double amount
    Date date
}


class CreditCardCircuit {

    + boolean isValid(String creditCard)
    + boolean isExpired(String creditCard)
    + boolean hasEnoughCredit(String creditCard, double requiredBalance)
    + boolean processPayment(String creditCard, double requiredBalance)
    + boolean returnPayment(String creditCard, double requiredBalance)

}

class Customer {
    Integer id
    String name
    String card
    Integer points

    + boolean attachCard(String customerCard)
    + void setName()
}

class DatabaseConnector {
    String driver
    String URL
    DatabaseConnector db

    + DatabaseConnector getInstance()
    + boolean executeQuery(String query)
    + ResultSet selectData(String query)
    + boolean closeConnection()
}

class Order {
    Integer orderId
    String productCode
    double pricePerUnit
    int quantity
    String status

    + boolean changeStatus(String status)
}

class ProductType{
    Integer id
    String productCode
    String description
    double pricePerUnit
    String note
    int quantity
    String position

    + boolean updateQuantity(int toBeAdded) 
    + boolean updatePosition(String newPos) 
    + boolean updateProduct(String newDescription, String newCode, + + double newPrice, String newNote)

}

class TicketEntry {
    String productCode
    String productDescription
    Integer quantity
    Double pricePerUnit
    Double discountRate
}

class ReturnTransaction {
    Integer transactionId
    Integer returnId 
    Map<ProductType, Integer> productAndAmount
    boolean commit
    

    +double returnCashPayment()
    +double returnCreditCardPayment(CreditCardCircuit circuit)
    +boolean addProductToReturn(String productCode, int amount)
    +boolean endReturnTransaction(Integer returnId, boolean commit)
    +boolean commit()

}

class SaleTransaction {
    Integer id 
    List<TicketEntry> products
    double cost
    String paymentType
    String status
    double discountRate

    +Integer computePoints()
    +double receiveCashPayment()
    +boolean receiveCreditCardPayment(CreditCardCircuit circuit)
    +boolean addProductToSale(String productCode, int amount)
    +boolean deleteProductFromSale(String productCode, int amount)
    +boolean applyDiscountRateToProduct(String productCode, double discountRate)
    +boolean applyDiscountRateToSale(double discountRate)
    +boolean endSaleTransaction()

}

class Shop {
    List<ProductType> products
    List<Customer> customers
    List<User> users
    User authenticatedUser
    AccountBook accountBook
    ShopDBManager db

    + void reset();

    -- User --
    + Integer createUser(String username, String password, String role) 
    + boolean deleteUser(Integer id) 
    + List<User> getAllUsers() 
    + User getUser(Integer id) 
    + boolean updateUserRights(Integer id, String role) 
    + User login(String username, String password) 
    + boolean logout();

    -- Product --
    + Integer createProductType(String description, String productCode, double pricePerUnit, String note)
    + boolean updateProduct(Integer id, String newDescription, String newCode, double newPrice, String newNote)
    + boolean deleteProductType(Integer id) 
    + List<ProductType> getAllProductTypes() 
    + ProductType getProductTypeByBarCode(String barCode) 
    + List<ProductType> getProductTypesByDescription(String description) 
    + boolean updateQuantity(Integer productId, int toBeAdded) 
    + boolean updatePosition(Integer productId, String newPos) 
    -- Order --
    + Integer issueOrder(String productCode, int quantity, double pricePerUnit)
    + Integer payOrderFor(String productCode, int quantity, double pricePerUnit)
    + boolean payOrder(Integer orderId) 
    + boolean recordOrderArrival(Integer orderId) 
    + List<Order> getAllOrders() 

    -- Customer & Fidelity Card--
    + Integer defineCustomer(String customerName) 
    + boolean modifyCustomer(Integer id, String newCustomerName, String newCustomerCard)
    + boolean deleteCustomer(Integer id) 
    + Customer getCustomer(Integer id) 
    + List<Customer> getAllCustomers() 
    + String createCard()
    + boolean attachCardToCustomer(String customerCard, Integer customerId)
    + boolean modifyPointsOnCard(String customerCard, int pointsToBeAdded)

    -- Sale Transaction --
    + Integer startSaleTransaction()
    + boolean addProductToSale(Integer transactionId, String productCode, int amount)
    + boolean deleteProductFromSale(Integer transactionId, String productCode, int amount)
    + boolean applyDiscountRateToProduct(Integer transactionId, String productCode, double discountRate)
    + boolean applyDiscountRateToSale(Integer transactionId, double discountRate)
    + int computePointsForSale(Integer transactionId)
    + boolean endSaleTransaction(Integer transactionId)
    + boolean deleteSaleTransaction(Integer transactionId)
    + SaleTransaction getSaleTransaction(Integer transactionId)

    --Return Transaction --
    + Integer startReturnTransaction(Integer transactionId)
    + boolean returnProduct(Integer returnId, String productCode, int amount)
    + boolean endReturnTransaction(Integer returnId, boolean commit)
    + boolean deleteReturnTransaction(Integer returnId)

    -- Payment --
    + double receiveCashPayment(Integer transactionId, double cash)
    + boolean receiveCreditCardPayment(Integer transactionId, String creditCard)
    + double returnCashPayment(Integer returnId)
    + double returnCreditCardPayment(Integer returnId, String creditCard)

    -- Balance --
    + boolean recordBalanceUpdate(double toBeAdded)
    + List<BalanceOperation> getCreditsAndDebits(LocalDate from, LocalDate to)
    + double computeBalance()
}

class ShopDBManager {
    DatabaseConnector connector

    --Operations--
    + boolean saveOperation(BalanceOperation operation)
    + BalanceOperation loadOperation()
    + List<BalanceOperation> loadAllOperations()
    + boolean updateBalanceOperation()
    + boolean deleteBalanceOperation()
    --Products--
    + ProductType loadProdcut()
    + List<ProductType> loadAllProdcuts()
    + boolean saveProduct(ProductType product)
    + boolean updateProduct(ProductType product)
    + boolean deleteProduct(ProductType product)
    --Users--
    + User loadUser()
    + List<User> loadAllUsers()
    + boolean saveUser(User user)
    + boolean updateUser(User user)
    + boolean deleteUser(User user)
    --Customers--
    + Customer loadCustomer()
    + List<Customer> loadAllCustomers()
    + boolean saveCustomer(Customer customer)
    + boolean updateCustomer(Customer customer)
    + boolean deleteCustomer(Customer customer)

}


class User {
    Integer id
    String role
    String password
    String username

}

SaleTransaction -- BalanceOperation

AccountBook -- Shop
AccountBook --up "*" BalanceOperation

CreditCardCircuit "0..*"-- Shop

Customer "0..*"-- Shop

Order "*" --up  ProductType
Order --  BalanceOperation

TicketEntry --down SaleTransaction
TicketEntry --down ProductType

ReturnTransaction "*" --right SaleTransaction
ReturnTransaction "*" --left ProductType
ReturnTransaction -- BalanceOperation

ShopDBManager --up AccountBook
ShopDBManager --right Shop
ShopDBManager --left DatabaseConnector

User "0..*" -- Shop

note "Persistent class" as N1  
N1 .. User
note "Persistent class" as N2
N2 ..left BalanceOperation
note "Persistent class" as N3
N3 .. Customer
note "Persistent class" as N4
N4 .. ProductType
@enduml
```

# Verification Traceability Matrix

|                           |  FR1  |  FR2  |  FR3  |  FR4  |  FR5  |  FR6  |  FR7  |
| ------------------------- | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| Account Book              |       |       | **X** |       |       | **X** | **X** |
| BalanceOperation          |       |       | **X** |       |       | **X** | **X** |
| Return Transaction        |       |       |       |       | **X** | **X** |       |
| CreditCardCircuit         |       |       |       |       |       | **X** |       |
| DatabaseConnector         | **X** | **X** | **X** | **X** | **X** | **X** | **X** |
| Customer                  |       |       |       | **X** | **X** |       |       |
| Order                     |       |       | **X** |       |       |       |       |
| TicketEntry               |       | **X** | **X** |       | **X** |       |       |
| ProductType               |       | **X** | **X** |       | **X** |       |       |
| Sale Transaction          |       |       |       |       | **X** | **X** |       |
| Shop                      | **X** | **X** | **X** | **X** | **X** | **X** | **X** |
| ShopDBManager             | **X** | **X** | **X** | **X** | **X** | **X** | **X** |
| User                      | **X** | **X** | **X** | **X** | **X** | **X** | **X** |


# Verification Sequence Diagrams 

### Add New Product 
```plantuml
Shop -> Shop : login()
Shop -> Shop : createProductType()
Shop -> ProductType : updatePosition()
ProductType -> ProductType : updatePosition()
Shop -> ShopDBManager : saveProduct()
ShopDBManager -> DBConnector : executeQuery()
DBConnector --> Shop: executed
```
### Issue and Pay Order
```plantuml
Shop -> Shop : login()
Shop -> Shop : issueOrder()
Shop -> Shop : getOrder()
Shop -> Order : payOrder()
Order -> Order: changeStatus()
Order --> Shop: changed
Shop -> AccountBook : addBalanceOperation()
AccountBook -> AccountBook : recordBalanceUpdate()
AccountBook -> ShopDBManager : saveOperation()
ShopDBManager -> DBConnector : executeQuery()
DBConnector --> AccountBook : executed
AccountBook -> ShopDBManager : updateProduct()
ShopDBManager -> DBConnector : executeQuery()
DBConnector --> Shop : executed
```
### Define Costumer and Card
```plantuml
Shop -> Shop : login()
Shop -> Shop : defineCustomer()
Shop -> Shop : createCard()
Shop -> Shop : getCustomer()
Shop -> Customer : attachCardToCustomer()
Customer -> Customer : attachCard()
Shop -> ShopDBManager : saveCustomer()
ShopDBManager -> DBConnector : executeQuery()
DBConnector --> Shop: executed
```
### Modify Customer
```plantuml
Shop -> Shop : login()
Shop -> Shop : getCustomer()
Shop -> Customer : modifyCustomer()
Customer -> Customer : setName()
Customer -> Customer : attachCard()
Shop -> ShopDBManager : updateCustomer()
ShopDBManager -> DBConnector : executeQuery()
DBConnector --> Shop: executed
```
### Manage Sale with Credit Card
```plantuml
Shop -> Shop : login()
Shop -> Shop : startSaleTransaction()
Shop -> SaleTransaction: addProductToSale()
SaleTransaction -> SaleTransaction: addProduct()
SaleTransaction -> ProductType : updateQuantity()
ProductType -> ProductType : updateQuantity()
Shop -> SaleTransaction : endSaleTransaction()
SaleTransaction -> SaleTransaction : endSaleTransaction()
SaleTransaction--> Shop : status
Shop -> SaleTransaction : receiveCreditCardPayment()
SaleTransaction -> CreditCardCircuit : isValid()
CreditCardCircuit --> SaleTransaction : valid
SaleTransaction -> CreditCardCircuit : hasEnoughCash()
CreditCardCircuit --> SaleTransaction : enough
SaleTransaction -> CreditCardCircuit : processPayment()
CreditCardCircuit --> Shop: processed
Shop -> AccountBook : addBalanceOperation()
AccountBook -> AccountBook : recordBalanceUpdate()
AccountBook -> ShopDBManager : saveOperation()
ShopDBManager -> DBConnector : executeQuery()
DBConnector --> AccountBook : executed
AccountBook -> ShopDBManager : updateProduct()
ShopDBManager -> DBConnector : executeQuery()
DBConnector --> Shop : executed
```
### Manage Return with Credit Card
```plantuml
Shop -> Shop : login()
Shop -> Shop : startReturnTransaction()
Shop -> ReturnTransaction : returnProduct()
ReturnTransaction -> ProductType : updateQuantity()
ProductType -> ProductType : updateQuantity()
Shop -> ReturnTransaction : endReturnTransaction()
ReturnTransaction -> ReturnTransaction : commit()
ReturnTransaction --> Shop : commit
Shop -> ReturnTransaction : returnCreditCardPayment()
ReturnTransaction -> CreditCardCircuit : isValid()
CreditCardCircuit --> ReturnTransaction : valid
ReturnTransaction -> CreditCardCircuit : returnPayment()
CreditCardCircuit --> Shop: returned
Shop -> AccountBook : addBalanceOperation()
AccountBook -> AccountBook : recordBalanceUpdate()
AccountBook -> ShopDBManager : saveOperation()
ShopDBManager -> DBConnector : executeQuery()
DBConnector --> AccountBook : executed
AccountBook -> ShopDBManager : updateProduct()
ShopDBManager -> DBConnector : executeQuery()
DBConnector --> Shop : executed
```
### Accounting
```plantuml
Shop -> Shop : login()
Shop -> AccountingBook : getCreditsAndDebits()
AccountingBook -> AccountingBook : getCreditsAndDebits()
AccountingBook --> Shop : credits and debits
```


