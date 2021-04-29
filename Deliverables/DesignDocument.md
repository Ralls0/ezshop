# Design Document 

- [Design Document](#design-document)
- [Instructions](#instructions)
- [High Level Design](#high-level-design)
- [Low Level Design](#low-level-design)
- [Verification Traceability Matrix](#verification-traceability-matrix)
- [Verification sequence diagrams](#verification-sequence-diagrams)
    - [Add new product](#add-new-product)
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
* S29260  Davide Fersino
  
Date: 30/04/2021

Version: 1.0

# Instructions

The design must satisfy the Official Requirements document, notably functional and non functional requirements

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

    + boolean recordBalanceUpdate(double toBeAdded)
    + double computeBalance()
    + List<BalanceOperation> getCreditsAndDebits(LocalDate from, LocalDate to)
    + boolean addBalanceOperation()
    + boolean removeBalanceOperation()
    
}

class CreditCardCircuit {

    + boolean isValid(String creditCard)
    + boolean isExpired(String creditCard)
    + boolean hasEnoughCredit(String creditCard, double requiredBalance)
    + boolean processPayment(String creditCard, double requiredBalance)
    + boolean returnPayment(String creditCard, double requiredBalance)

}

class Credit {

}

class Customer {
    Integer id
    String name
    LoyaltyCard card

    + boolean attachCard(String customerCard)
    + void setName()
    + List<Customer> loadAllCustomers()
    + Customer loadCustomer()
    + void saveCustomer(Customer customer)

}

class Debit {

}

class BalanceOperation {
    description
    amount
    date
    + List<BalanceOperation> loadAllOperations()
    + BalanceOperation loadOperation()
    + void saveOperation(BalanceOperation operation)
}

class LoyaltyCard {
    String id
    int points
    
}

class Order {
    Integer orderId
    String productCode
    double pricePerUnit
    int quantity
    String status

    +boolean changeStatus(String status)

}

class ProductType{
    Integer id
    String productCode
    String description
    double pricePerUnit
    String note
    int quantity
    Position position

    + boolean updateQuantity(int toBeAdded) 
    + boolean updatePosition(String newPos) 
    + boolean updateProduct(String newDescription, String newCode, + + double newPrice, String newNote)
    + List<ProductType> loadAllProdcuts()
    + ProductType loadProdcut()
    + void saveProduct(ProductType product)

}


class Position {
    String aisleID
    String rackID
    String levelID
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

class ProductQuantityAndDiscount {
    Integer quantity
    double discountRate
    ProductType product

}

class SaleTransaction {
    Integer id 
    List<ProductQuantityAndDiscount> products
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
    'List<User> users
    'List<ProductType> products
    'List<Customer> customers
    User authenticatedUser
    AccountBook accountBook
    'AccountBook accountBook
    + void reset();
    + Integer createUser(String username, String password, String role) 
    + boolean deleteUser(Integer id) 
    + List<User> getAllUsers() 
    + User getUser(Integer id) 
    + boolean updateUserRights(Integer id, String role) 
    + User login(String username, String password) 
    + boolean logout();
    + Integer createProductType(String description, String productCode, double pricePerUnit, String note)
    + boolean updateProduct(Integer id, String newDescription, String newCode, double newPrice, String newNote)
    + boolean deleteProductType(Integer id) 
    + List<ProductType> getAllProductTypes() 
    + ProductType getProductTypeByBarCode(String barCode) 
    + List<ProductType> getProductTypesByDescription(String description) 
    + boolean updateQuantity(Integer productId, int toBeAdded) 
    + boolean updatePosition(Integer productId, String newPos) 
    + Integer issueOrder(String productCode, int quantity, double pricePerUnit)
    + Integer payOrderFor(String productCode, int quantity, double pricePerUnit)
    + boolean payOrder(Integer orderId) 
    + boolean recordOrderArrival(Integer orderId) 
    + List<Order> getAllOrders() 
    + Integer defineCustomer(String customerName) 
    + boolean modifyCustomer(Integer id, String newCustomerName, String newCustomerCard)
    + boolean deleteCustomer(Integer id) 
    + Customer getCustomer(Integer id) 
    + List<Customer> getAllCustomers() 
    + String createCard()
    + boolean attachCardToCustomer(String customerCard, Integer customerId)
    + boolean modifyPointsOnCard(String customerCard, int pointsToBeAdded)
    + Integer startSaleTransaction()
    + boolean addProductToSale(Integer transactionId, String productCode, int amount)
    + boolean deleteProductFromSale(Integer transactionId, String productCode, int amount)
    + boolean applyDiscountRateToProduct(Integer transactionId, String productCode, double discountRate)
    + boolean applyDiscountRateToSale(Integer transactionId, double discountRate)
    + int computePointsForSale(Integer transactionId)
    + boolean endSaleTransaction(Integer transactionId)
    + boolean deleteSaleTransaction(Integer transactionId)
    + SaleTransaction getSaleTransaction(Integer transactionId)
    + Integer startReturnTransaction(Integer transactionId)
    + boolean returnProduct(Integer returnId, String productCode, int amount)
    + boolean endReturnTransaction(Integer returnId, boolean commit)
    + boolean deleteReturnTransaction(Integer returnId)
    + double receiveCashPayment(Integer transactionId, double cash)
    + boolean receiveCreditCardPayment(Integer transactionId, String creditCard)
    + double returnCashPayment(Integer returnId)
    + double returnCreditCardPayment(Integer returnId, String creditCard)
    + boolean recordBalanceUpdate(double toBeAdded)
    + List<BalanceOperation> getCreditsAndDebits(LocalDate from, LocalDate to)
    + double computeBalance()
}


class User {
    Integer id
    String role
    String password
    String username
    +User loadUser()
    +List<User> loadAllUsers()
    +void saveUsers(User user)
}

AccountBook -up Shop
AccountBook - "*" BalanceOperation

User "0..*" -- Shop

CreditCardCircuit "0..*"-- Shop

Credit "0..*"--|> BalanceOperation

Debit "0..*"---|> BalanceOperation

Order --|> Debit
Order "*" -- ProductType

Shop --right "*" ProductType
SaleTransaction -- "*" ProductType

LoyaltyCard "0..1" -- Customer

Customer "0..*"-- Shop
ProductType - "0..1" Position

ReturnTransaction "*" --up SaleTransaction
ReturnTransaction "*" --up ProductType

ProductQuantityAndDiscount - SaleTransaction
ProductQuantityAndDiscount - ProductType

SaleTransaction --|> Credit
ReturnTransaction --|> Debit


note "Persistent class" as N1  
N1 .. User
note "Persistent class" as N2
N2 .. BalanceOperation
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
| Credit                    |       |       |       |       |       | **X** |       |
| CreditCardCircuit         |       |       |       |       |       | **X** |       |
| Debit                     |       |       | **X** |       |       | **X** |       |
| Customer                  |       |       |       | **X** | **X** |       |       |
| LoyaltyCard               |       |       |       | **X** | **X** |       |       |
| Order                     |       |       | **X** |       |       |       |       |
| Position                  |       | **X** | **X** |       |       |       |       |
| ProdctQuantityAndDiscount |       | **X** | **X** |       | **X** |       |       |
| ProductType               |       | **X** | **X** |       | **X** |       |       |
| Sale Transaction          |       |       |       |       | **X** | **X** |       |
| Shop                      | **X** | **X** | **X** | **X** | **X** | **X** | **X** |
| User                      | **X** | **X** | **X** | **X** | **X** | **X** | **X** |


# Verification sequence diagrams 

### Add new product
```plantuml
Shop -> Shop : login()
Shop -> Shop : createProductType()
Shop -> ProductType : updatePosition()
ProductType -> ProductType : updatePosition()
```
### Issue and Pay Order
```plantuml
Shop -> Shop : login()
Shop -> Shop : issueOrder()
Shop -> Shop : getOrder()
Shop -> Order : payOrder()
Order -> Order: changeStatus()
Shop -> AccountBook : recordBalanceUpdate()
AccountBook -> AccountBook : recordBalanceUpdate()
```
### Define Costumer and Card
```plantuml
Shop -> Shop : login()
Shop -> Shop : defineCustomer()
Shop -> Shop : createCard()
Shop -> Shop : getCustomer()
Shop -> Customer : attachCardToCustomer()
Customer -> Customer : attachCard()
```
### Modify Customer
```plantuml
Shop -> Shop : login()
Shop -> Shop : getCustomer()
Shop -> Customer : modifyCustomer()
Customer -> Customer : setName()
Customer -> Customer : attachCard()
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
Shop -> AccountBook : recordBalanceUpdate()
AccountBook -> AccountBook : recordBalanceUpdate()
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
Shop -> AccountBook : recordBalanceUpdate()
AccountBook -> AccountBook : recordBalanceUpdate()
```
### Accounting
```plantuml
Shop -> Shop : login()
Shop -> AccountingBook : getCreditsAndDebits()
AccountingBook -> AccountingBook : getCreditsAndDebits()
AccountingBook --> Shop : credits and debits
```


