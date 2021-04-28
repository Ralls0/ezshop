###Add new product
```plantuml
Shop -> Shop : login()
Shop -> Shop : createProductType()
Shop -> ProductType : updatePosition()
ProductType -> ProductType : updatePosition()
```
###Issue and pay order
```plantuml
Shop -> Shop : login()
Shop -> Shop : issueOrder()
Shop -> Shop : getOrder()
Shop -> Order : payOrder()
Order -> Order: changeStatus()
Shop -> AccountBook : recordBalanceUpdate()
AccountBook -> AccountBook : recordBalanceUpdate()
```
###Define costumer and card
```plantuml
Shop -> Shop : login()
Shop -> Shop : defineCustomer()
Shop -> Shop : createCard()
Shop -> Shop : getCustomer()
Shop -> Customer : attachCardToCustomer()
Customer -> Customer : attachCard()
```
###Modify customer
```plantuml
Shop -> Shop : login()
Shop -> Shop : getCustomer()
Shop -> Customer : modifyCustomer()
Customer -> Customer : setName()
Customer -> Customer : attachCard()
```
###Manage sale with credit card
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
```
###Manage return with credit card
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
###Accounting
```plantuml
Shop -> Shop : login()
Shop -> AccountingBook : getCreditsAndDebits()
AccountingBook -> AccountingBook : getCreditsAndDebits()
AccountingBook --> Shop : credits and debits
```
