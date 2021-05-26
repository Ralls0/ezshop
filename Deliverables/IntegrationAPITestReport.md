# Integration and API Test Documentation

Authors:
* S281564 Marco Manco
* S290136 Giovanni Pollo
* S292477 Matteo Quarta
* S292602  Davide Fersino

Date:

Version: 1.0

# Contents

- [Dependency graph](#dependency-graph)

- [Integration and API Test Documentation](#integration-and-api-test-documentation)
- [Contents](#contents)
- [Dependency graph](#dependency-graph)
- [Integration approach](#integration-approach)
- [Tests](#tests)
  - [Step 1](#step-1)
  - [Step 2](#step-2)
  - [Step 3](#step-3)
- [Coverage of Scenarios and FR](#coverage-of-scenarios-and-fr)
- [Coverage of Non Functional Requirements](#coverage-of-non-functional-requirements)

  - [](#)

- [Tests](#tests)

- [Scenarios](#scenarios)

- [Coverage of scenarios and FR](#scenario-coverage)
- [Coverage of non-functional requirements](#nfr-coverage)

# Dependency graph

```plantuml
@startuml
!theme spacelab
top to bottom direction

class GUI {}
class EZShop {}
class CreditCardCircuit {}
class DBConnector {}
class EZAccountBook {}
class EZBalanceOperation {}
class EZShopDBManager {}
class EZTicketEntry {}
class EZUser {}
class EZLoyaltyCard {}
class EZCustomer {}
class EZOrder {}
class EZProductType {}
class EZReturnTransaction {}
class EZSaleTransaction {}

GUI-->EZShop

EZShop-->CreditCardCircuit
EZShop-->EZAccountBook
EZShop-->EZBalanceOperation
EZShop-->EZCustomer
EZShop-->DBConnector
EZShop-->EZLoyaltyCard
EZShop-->EZOrder
EZShop-->EZProductType
EZShop-->EZReturnTransaction
EZShop-->EZSaleTransaction
EZShop-->EZShopDBManager
EZShop-->EZTicketEntry
EZShop-->EZUser

EZAccountBook-->EZShopDBManager
EZAccountBook-->EZBalanceOperation

EZReturnTransaction-->EZTicketEntry
EZSaleTransaction-->EZTicketEntry

@enduml
```

# Integration approach

For our tests we have chosen a bottom up approach.

Step 1: Unit testing

Step 2: Intermediate testing

Step : API testing

# Tests

<define below a table for each integration step. For each integration step report the group of classes under test, and the names of
JUnit test cases applied to them> JUnit test classes should be here src/test/java/it/polito/ezshop

## Step 1

| Classes | JUnit test cases |
| ------- | ---------------- |
| EZTicketEntry.java | TestEZTicketEntry.testSetBarCode()<br/>TestEZTicketEntry.testSetProductDescription()<br/>TestEZTicketEntry.testSetAmount()<br/>TestEZTicketEntry.testSetPricePerUnit()<br/>TestEZTicketEntry.testSetDiscountRate() |
| EZOrder.java | TestEZOrder.testSetBalanceId()<br/>TestEZOrder.testSetProductCode()<br/>TestEZOrder.testSetPricePerUnit()<br/>TestEZOrder.testSetQuantity()<br/>TestEZOrder.testSetStatus()<br/>TestEZOrder.testSetOrderID() |
| EZBalanceOperation.java | TestEZBalanceOperation.testSetBalanceId()<br/>TestEZBalanceOperation.testSetDate()<br/>TestEZBalanceOperation.testSetMoney()<br/>TestEZBalanceOperation.testSetType() |
| EZUser.java | TestEZUser.setId()<br/>TestEZUser.setUsername()<br/>TestEZUser.setPassword()<br/>TestEZUser.setRole() |
| EZProductType.java | TestEZProductType.setQuantity()<br/>TestEZProductType.setLocation()<br/>TestEZProductType.setNote()<br/>TestEZProductType.setProductDescription()<br/>TestEZProductType.setBarCode()<br/>TestEZProductType.setPricePerUnit()<br/>TestEZProductType.setId() |
| EZCustomer.java | TestEZCustomer.testXSetter()<br/>TestEZCustomer.testCustomerCardSetter()<br/>TestEZCustomer.testCustomerNameSetter()<br/>TestEZCustomer.testSetId()<br/>TestEZCustomer.testSetPoints() |
| CreditCardCircuit.java | TestCreditCardCircuit.testCardPresentInFile()<br/>TestCreditCardCircuit.testCardNotPresentInFile()<br/>TestCreditCardCircuit.testNegativeMinBalance()<br/>TestCreditCardCircuit.testCardNotPresentDuringHasEnoughBalance()<br/>TestCreditCardCircuit.testCardBalanceInsufficientDuringHasEnoughBalance()<br/>TestCreditCardCircuit.testCardBalanceSufficientDuringHasEnoughBalance()<br/>TestCreditCardCircuit.testNegativePayAmount()<br/>TestCreditCardCircuit.testCardNotPresentDuringPay()<br/>TestCreditCardCircuit.testCardBalanceInsufficientDuringPay()<br/>TestCreditCardCircuit.testCardBalanceSufficientDuringPay()<br/>TestCreditCardCircuit.testNegativeRefundAmount()<br/>TestCreditCardCircuit.testCardNotPresentDuringRefund()<br/>TestCreditCardCircuit.testRefundSuccess() |

## Step 2

| Classes | JUnit test cases |
| ------- | ---------------- |
| EZAccountBook.java | TestEZAccountBook.testSingleton()<br/>TestEZAccountBook.testGetBOList()<br/>TestEZAccountBook.testComputeBalanceAllPositive()<br/>TestEZAccountBook.testComputeBalanceAllNegative()<br/>TestEZAccountBook.testComputeBalanceMixed1()<br/>TestEZAccountBook.testComputeBalanceMixed2()<br/>TestEZAccountBook.testComputeBalanceMixed3() |
| EZReturnTransaction.java | TestEZReturnTransaction.testSetReturnId()<br/>TestEZReturnTransaction.testSetTransactionId()<br/>TestEZReturnTransaction.testSetProducts()<br/>TestEZReturnTransaction.testSetCommit<br/>TestEZReturnTransaction.testSetStatus<br/>TestEZReturnTransaction.testSetDiscountRate()<br/>TestEZReturnTransaction.testAddProductReturnedAndGetPrice()<br/>TestEZReturnTransaction.testAddProductReturnedAndGetPriceWithDiscount() |
| EZSaleTransaction.java | TestEZSaleTransaction.setUp()<br/>TestEZSaleTransaction.tearDown()<br/>TestEZSaleTransaction.testSetProducts()<br/>TestEZSaleTransaction.testSetEntries()<br/>TestEZSaleTransaction.testSetPaymentType()<br/>TestEZSaleTransaction.testSetStatus()<br/>TestEZSaleTransaction.testSetTicketNumber()<br/>TestEZSaleTransaction.testSetDiscountRate()<br/>TestEZSaleTransaction.testSetPrice()<br/>TestEZSaleTransaction.testSetPriceWithProduct()<br/>TestEZSaleTransaction.testComputePointsWithPrice()<br/>TestEZSaleTransaction.testComputePointsWithProduct()<br/>TestEZSaleTransaction.testReceiveCashPaymentValid()<br/>TestEZSaleTransaction.testReceiveCashPaymentInvalid()<br/>TestEZSaleTransaction.testReceiveCreditCardPayment()<br/>TestEZSaleTransaction.testGetEntry()<br/>TestEZSaleTransaction.testDeleteProductFromSaleValid()<br/>TestEZSaleTransaction.testDeleteProductFromSaleInvalid()<br/>TestEZSaleTransaction.testApplyDiscountRateToProduct()<br/>TestEZSaleTransaction.testEndSaleTransaction()<br/>TestEZSaleTransaction.testValidLuhnAlgorithm() |
| EZTicketEntry.java | TestEZTicketEntry.testSetBarCode()<br/>TestEZTicketEntry.testSetProductDescription()<br/>TestEZTicketEntry.testSetAmount()<br/>TestEZTicketEntry.testSetPricePerUnit()<br/>TestEZTicketEntry.testSetDiscountRate() |
| EZShopDBManager.java | TestEZShopDBManager.testGetNextUserID()<br/>TestEZShopDBManager.testLoadAllUsers()<br/>TestEZShopDBManager.testLoadUserFromID()<br/>TestEZShopDBManager.testLoadUserFromUsernameAndPass()<br/>TestEZShopDBManager.testUserExistsFromID()<br/>TestEZShopDBManager.testUserExistsFromUsername()<br/>TestEZShopDBManager.testUpdateUserRights()<br/>TestEZShopDBManager.testDeleteUser()<br/>TestEZShopDBManager.testGetNextCustomerID()<br/>TestEZShopDBManager.testLoadAllCustomers()<br/>TestEZShopDBManager.testLoadCustomerFromID()<br/>TestEZShopDBManager.testLoadCustomerFromCard()<br/>TestEZShopDBManager.testCustomerExistsFromID()<br/>TestEZShopDBManager.testCustomerExistsFromName()<br/>TestEZShopDBManager.testCustomerExistsFromCard()<br/>TestEZShopDBManager.testUpdateCustomer()<br/>TestEZShopDBManager.testDeleteCustomer()<br/>TestEZShopDBManager.testGetNextOrderID()<br/>TestEZShopDBManager.testLoadAllOrders()<br/>TestEZShopDBManager.testLoadOrderFromID()<br/>TestEZShopDBManager.testUpdateOrder()<br/>TestEZShopDBManager.testGetNextProductID()<br/>TestEZShopDBManager.testLoadAllProducts()<br/>TestEZShopDBManager.testLoadProductFromID()<br/>TestEZShopDBManager.testLoadProductFromBarCode()<br/>TestEZShopDBManager.testProductExistsFromID()<br/>TestEZShopDBManager.testProductExistsFromBarCode()<br/>TestEZShopDBManager.testProductExistsFromLocation()<br/>TestEZShopDBManager.testUpdateProduct()<br/>TestEZShopDBManager.testDeleteProduct()<br/>TestEZShopDBManager.testGetNextSaleID()<br/>TestEZShopDBManager.testLoadAllSales()<br/>TestEZShopDBManager.testLoadSaleFromID()<br/>TestEZShopDBManager.testUpdateSale()<br/>TestEZShopDBManager.testDeleteSale()<br/>TestEZShopDBManager.testGetNextBalanceOperationID()<br/>TestEZShopDBManager.testLoadAllBalanceOperations()<br/>TestEZShopDBManager.testGetNextReturnID()<br/>TestEZShopDBManager.testLoadReturnFromID()<br/>TestEZShopDBManager.testUpdateReturnStatus()<br/>TestEZShopDBManager.testDeleteReturn() |

## Step 3

| Classes | JUnit test cases |
| ------- | ---------------- |
| EZShop.java | TestEZShop.testCreateUser()<br/> TestEZShop.testDeleteUser()<br/>TestEZShop.testGetAllUsers()<br/>TestEZShop.testGetUser()<br/>TestEZShop.testUpdateUserRights()<br/>TestEZShop.testLogin()<br/>TestEZShop.testLogout()<br/>TestEZShop.createProductType()<br/>TestEZShop.updateProduct()<br/>TestEZShop.deleteProductType()<br/>TestEZShop.getAllProductTypes()<br/>TestEZShop.getProductTypeByBarCode()<br/>TestEZShop.getProductTypesByDescription()<br/>TestEZShop.updateQuantity()<br/>TestEZShop.updatePosition()<br/>TestEZShop.defineCustomer()<br/>TestEZShop.modifyCustomer()<br/>TestEZShop.deleteCustomer()<br/>TestEZShop.getCustomer()<br/>TestEZShop.getAllCustomers()<br/>TestEZShop.createCard()<br/>TestEZShop.attachCardToCustomer()<br/>TestEZShop.modifyPointsOnCard()<br/>TestEZShop.testStartSaleTransaction()<br/>TestEZShop.testAddProductToSale()<br/>TestEZShop.testDeleteProductFromSale()<br/>TestEZShop.testApplyDiscountRateToProduct()<br/>TestEZShop.testapplyDiscountRateToSale()<br/>TestEZShop.testComputePointsForSale()<br/>TestEZShop.testEndSaleTransaction()<br/>TestEZShop.testDeleteSaleTransaction()<br/>TestEZShop.testGetSaleTransactio()<br/>TestEZShop.testStartReturnTransaction()<br/>TestEZShop.testReturnProduct()<br/>TestEZShop.testEndReturnTransaction()<br/>TestEZShop.testDeleteReturnTransaction()<br/>TestEZShop.testReceiveCashPayment()<br/>TestEZShop.testReceiveCreditCardPayment()<br/>TestEZShop.testReturnCashPayment()<br/>TestEZShop.testReturnCreditCardPayment()<br/>TestEZShop.testIssueOrder()<br/>TestEZShop.testPayOrderFor()<br/>TestEZShop.testPayOrder()<br/>TestEZShop.testRecordOrderArrival()<br/>TestEZShop.testRecordBUAndCB()<br/>TestEZShop.testGetDebitsCreditsAndCB()<br/>TestEZShop.testValidBarCode() |

# Coverage of Scenarios and FR


| Scenario ID | FR covered | JUnit Test(s)                  |
| :---------: | :--------: | :----------------------------- |
|     1-*     |    FR3     | TestEZShop.testDeleteProductType()          |
|             |            | TestEZShop.testGetAllProductTypes()         |
|     1-1     |    FR3     | TestEZShop.testCreateProductType()          |
|             |            | TestEZShop.testValdiBarCode()               |
|     1-2     |    FR3     | TestEZShop.testUpdatePosition()             |
|             |            | TestEZShop.testUpdateProduct()              |
|     1-3     |    FR3     | TestEZShop.testUpdateProduct()              |
|     2-*     |    FR1     | TestEZShop.testGetAllUsers()                |
|     2-1     |    FR1     | TestEZShop.testCreateUser()                 |
|     2-2     |    FR1     | TestEZShop.testDeleteUsers()                |
|     2-3     |    FR1     | TestEZShop.testGetUser()                    |
|     2-3     |            | TestEZShop.testUpdateUserRights()           |
|     3-*     |    FR4     | TestEZShop.testGetAllOrders()               |
|     3-1     |    FR4     | TestEZShop.testIssueOrder()                 |
|     3-2     |    FR4     | TestEZShop.testPayOrder()                   |
|     3-2     |    FR4     | TestEZShop.testPayOrderFor()                |
|     3-3     |    FR4     | TestEZShop.testRecordOrderArrival()         |
|     4-*     |    FR5     | TestEZShop.testGetCustomer()                |
|             |            | TestEZShop.testGetAllCustomers()            |
|             |            | TestEZShop.testDeleteCustomers()            |
|     4-1     |    FR5     | TestEZShop.testDefineCustomer()             |
|     4-2     |    FR5     | TestEZShop.testAttachCardToCustomer()       |
|             |            | TestEZShop.testCreateCard()                 |
|     4-3     |    FR5     | TestEZShop.testAttachCardToCustomer()       |
|     4-4     |    FR5     | TestEZShop.testModifyCustomer()             |
|             |    FR5     | TestEZShop.testModifyPointsOnCard()         |
|     5-1     |    FR1     | TestEZShop.testLogin()                      |
|     5-2     |    FR1     | TestEZShop.testLogout()                     |
|     6-*     |    FR6     | TestEZShop.testGetSaleTransaction()         |
|     6-1     |    FR6     | TestEZShop.testStartSaleTransaction()       |
|             |            | TestEZShop.testAddProductToSale()           |
|             |            | TestEZShop.testDeleteProductFromSale()      |
|             |            | TestEZShop.testRecieveCashPayment()         |
|             |            | TestEZShop.testEndSaleTransaction()         |
|     6-2     |    FR6     | TestEZShop.testStartSaleTransaction()       |
|             |            | TestEZShop.testAddProductToSale()           |
|             |            | TestEZShop.testDeleteProductFromSale()      |
|             |            | TestEZShop.testApplyDiscountRateToProduct() |
|             |            | TestEZShop.testRecieveCashPayment()         |
|             |            | TestEZShop.testEndSaleTransaction()         |
|     6-3     |    FR6     | TestEZShop.testStartSaleTransaction()       |
|             |            | TestEZShop.testAddProductToSale()           |
|             |            | TestEZShop.testDeleteProductFromSale()      |
|             |            | TestEZShop.testApplyDiscountRateToSale()    |
|             |            | TestEZShop.testRecieveCashPayment()         |
|             |            | TestEZShop.testEndSaleTransaction()         |
|     6-4     |    FR6     | TestEZShop.testStartSaleTransaction()       |
|             |            | TestEZShop.testAddProductToSale()           |
|             |            | TestEZShop.testDeleteProductFromSale()      |
|             |            | TestEZShop.testApplyDiscountRateToSale()    |
|             |            | TestEZShop.testComputePointsForSale()       |
|             |            | TestEZShop.testRecieveCashPayment()         |
|             |            | TestEZShop.testEndSaleTransaction()         |
|     6-4     |    FR6     | TestEZShop.testStartSaleTransaction()       |
|             |            | TestEZShop.testEndSaleTransaction()         |
|     6-5     |    FR6     | TestEZShop.testEndSaleTransaction()         |
|             |            | TestEZShop.testRecieveCashPayment()         |
|     7-1     |    FR7     | TestEZShop.testRecieveCreditCardPayment()   |
|     7-2     |    FR7     | TestEZShop.testRecieveCreditCardPayment()   |
|     7-3     |    FR7     | TestEZShop.testRecieveCreditCardPayment()   |
|     7-4     |    FR7     | TestEZShop.testRecieveCashPayment()         |
|     8-*     |    FR7     | TestEZShop.testDeleteReturnTransaction()    |
|     8-1     |    FR7     | TestEZShop.testStartReturnTransaction()     |
|             |            | TestEZShop.testReturnProduct()              |
|             |            | TestEZShop.testEndReturnTransaction()       |
|             |            | TestEZShop.testReturnCreditCardPayment()    |
|     8-2     |    FR7     | TestEZShop.testStartReturnTransaction()     |
|             |            | TestEZShop.testReturnProduct()              |
|             |            | TestEZShop.testEndReturnTransaction()       |
|             |            | TestEZShop.testRetrunCashPayment()          |
|     9-1     |    FR8     | TestEZShop.testGetDebitdsAndCreditsAndCB()  |
|             |            | TestEZShop.testGetBODates()                 |
|    10-1     |    FR7     | TestEZShop.testReturnCreditCardPayment()    |
|    10-2     |    FR7     | TestEZShop.testReturnCashPayment()          |

# Coverage of Non Functional Requirements

<Report in the following table the coverage of the Non Functional Requirements of the application - only those that can be tested with automated testing frameworks.>

###

| Non Functional Requirement | Test name        |
| -------------------------- | ---------------- |
|                            |                  |
| NFR4 | TestEZShop.testValidBarCode() |
| NFR5 | TestEZSaleTransaction.testValidLuhnAlgorithm() |
| NFR6 | TestEZSaleTransaction.createCard() |
