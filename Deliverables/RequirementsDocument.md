# Requirements Document 

Authors:

Date:

Version:

# Contents

- [Essential description](#essential-description)
- [Stakeholders](#stakeholders)
- [Context Diagram and interfaces](#context-diagram-and-interfaces)
	+ [Context Diagram](#context-diagram)
	+ [Interfaces](#interfaces) 
	
- [Requirements Document](#requirements-document)
- [Contents](#contents)
- [Essential description](#essential-description)
- [Stakeholders](#stakeholders)
- [Context Diagram and interfaces](#context-diagram-and-interfaces)
	- [Context Diagram](#context-diagram)
	- [Interfaces](#interfaces)
- [Stories and personas](#stories-and-personas)
- [Functional and non functional requirements](#functional-and-non-functional-requirements)
	- [Functional Requirements](#functional-requirements)
	- [Non Functional Requirements](#non-functional-requirements)
- [Use case diagram and use cases](#use-case-diagram-and-use-cases)
	- [Use case diagram](#use-case-diagram)
		- [Use case 1, UC1](#use-case-1-uc1)
				- [Scenario 1.1](#scenario-11)
				- [Scenario 1.2](#scenario-12)
				- [Scenario 1.x](#scenario-1x)
		- [Use case 2, UC2](#use-case-2-uc2)
		- [Use case x, UCx](#use-case-x-ucx)
- [Glossary](#glossary)
- [System Design](#system-design)
- [Deployment Diagram](#deployment-diagram)

# Essential description

Small shops require a simple application to support the owner or manager. A small shop (ex a food shop) occupies 50-200 square meters, sells 500-2000 different item types, has one or a few cash registers 
EZShop is a software application to:
* manage sales
* manage inventory
* manage customers
* support accounting


# Stakeholders


| Stakeholder name  | Description |
| ----------------- |:-----------:|
|   Owner   | Owner of the shop |
| Cashier | Workers that manage sales through the system |
| Warehouse Worker | Workers that manage the inventory |
| Customer |     Customers with a fidelity card     |
| Anonymous Customers | People that interact with the sytems buying products but without a fidelity card |
| Fidelity Card | Card associated to a customer |
| POS | Point of sale |
| Product | Product sold by the shop |
| Cash register | Register used to handle sales |

# Context Diagram and interfaces

## Context Diagram
```plantuml
@startuml ContextDiagram

(EZShop)
:Worker:
:Cashier:
:WarehouseWorker:
:Owner:
:Customer:
:AnonymousCustomer:
:CashRegister:
:FidelityCard:
:POS:
:Product:

Worker<|--Cashier
Worker<|--WarehouseWorker
Worker<|--Owner

Customer--|>AnonymousCustomer

(EZShop)<--Worker
(EZShop)<--AnonymousCustomer
(EZShop)<--CashRegister
(EZShop)<--FidelityCard
(EZShop)<--POS
(EZShop)<--Product

@enduml
```

## Interfaces

| Actor            | Logical Interface                                      | Physical Interface                 |
| ---------------- | ------------------------------------------------------ | ---------------------------------- |
| Owner, manager   | GUI of the application                                 | Screen, keyboard on PC             |
| Cashier          | GUI of the application                                 | Screen, touch of the cash register |
| Warehouse Worker | GUI of the application                                 | Touch screen of the smartphone     |
| Maintainers      | Phone call/screen share                                | Screen, keyboard on PC             |
| Fidelity Card    | Serial number                                          | Bar Code                           |
| POS              | Mastercard API (https://developer.mastercard.com/apis) | Card reader                        |
| Product          | Serial number                                          | Bar Code                           |

# Stories and personas
Giovanni is 21, he is a customer of the shop. He is a student so is usually out of cash. For this reason he is interested in the fidelty card.

Angelo is a doctor and he is 40. He is a fan of credit card. For this reason when he goes to the shop he likes to pay with it.

Franco is a shop manager and he likes to have all pieces of informations under control. For this reason he is interested in the system because he will use a lot all the accounting functions. 

Anna is 25 and she work as a cashier. She is not very good in math so she wants, on her tablet the preview of all the articles the customer is buying and also the preview of the total price to pay. 

Giulia is 45 and she works as a warehouse. She handles supplying and so she is interested is being able to managing periodic orders from a wholesaler.

## Functional Requirements

| ID        | Description  |
| ------------- |:-------------:|
|  FR1     | Authorizaton and authentication |
|  FR1.1  | Login |
| FR1.2 | Logout |
| FR2 | Manage workers |
| FR2.1 | Add a new worker (manager, warehouse workers, cashier) |
| FR2.2 | Modify a worker |
| FR2.3 | Delete an exsisting worker |
| FR2.4 | Search a worker |
| FR2.5 | List all worker |
| FR3 | Manage customers |
| FR3.1 | Create/delete fidelity card |
| FR3.2 | Search and modify customer details |
| FR3.3 | List all customer |
| FR4 | Manage inventory |
| FR4.1 | Add product |
| FR4.1.1 | Read bar code of a product |
| FR4.2 | Delete product |
| FR4.3 | Modify product |
| FR4.4 | List all products |
| FR4.5 | Manage supplying |
| FR4.5.1 | Create order |
| FR4.5.2 | Delete order |
| FR4.5.3 | Modify order |
| FR4.5.4 | Manage periodic orders |
| FR6 | Manage sales |
| FR6.1 | Open transaction |
| FR6.2 | Manage products |
| FR6.2.1 | Read bar code |
| FR6.2.2 | Add the product to the shopping cart |
| FR6.3 | Visualize the amount to pay |
| FR6.4 | Manage payment options |
| FR6.4.1 | Start POS transaction |
| FR6.4.2 | Enter payment amount |
| FR6.4.3 | End POS transaction |
| FR6.5 | Read bar code of the fidelity card |
| FR6.6 | Print the receipt |
| FR6.7 | Close transaction |
| FR7 | Support accounting |
| FR7.1 | List transactions |
| FR7.2 |                  Visualize incomes                   |
| FR7.3 |             Visualize product statistics             |
| FR7.4 |                 Customers statistics                 |

## Non Functional Requirements

| ID        | Type (efficiency, reliability, ..)           | Description  | Refers to |
| ------------- |:-------------:| :-----:| -----:|
|  NFR1     | Usability | Application should be used with minimal training for the worker | All FR |
|  NFR2     | Privacy | Data of one user must be encrypted and  visibile only to the manager/owner. | FR3 |
|  NFR3     | Portability | Cross platform | All FR |
| NFR4 | Reliability | The system can work just with a local network | All FR |


# Use case diagram and use cases


## Use case diagram
```plantuml
@startuml UseCaseDiagram

:Worker:
:Cashier:
:WarehouseWorker:
:Owner:
:Customer:
:AnonymousCustomer:
:POS:
:CashRegister:

Worker<|--Cashier
Worker<|--WarehouseWorker
Worker<|--Owner

AnonymousCustomer<|--Customer
(EZShop)
(EZShop)
(Autorization and authentication)
(Manage workers)
(Manage customers)
(Manage inventory)
(Manage sales)
(Support accounting)

(EZShop)-up..>(Autorization and authentication) : <<include>>
(EZShop)-up.>(Manage workers) : <<include>>
(EZShop)-up.>(Manage customers) : <<include>>
(EZShop)-up.>(Manage inventory) : <<include>>
(EZShop)-up.>(Manage sales) : <<include>>
(EZShop)-up.>(Support accounting) : <<include>>

:Worker: --> (Autorization and authentication)

:Owner:-->(Manage workers)

:Cashier:-->(Manage customers)
(Manage customers)<--:Customer:
(Manage sales)<--:AnonymousCustomer:


:WarehouseWorker:-->(Manage inventory)

:Cashier:-->(Manage sales)

:Owner:-->(Support accounting)

:POS:-->(Manage sales)

:CashRegister:-->(Manage sales)

@enduml
```



### Use case 1, UC1
| Actors Involved  |                     Worker                     |
| ---------------- | :--------------------------------------------: |
| Precondition     |             A worker is logged out             |
| Post condition   |             A worker is logged in              |
| Nominal Scenario |                     Login                      |
| Variants         | Worker doesn't exist, user is not authenticate |

##### Scenario 1.1 
| Scenario 1.1   |               Worker                                  |
| -------------- | :---------------------------------------------: |
| Precondition   |              Worker is logged out               |
| Post condition | Worker account is created & worker is logged in |
| Step           |                   Description                   |
| 1              |            The worker is logged out             |
| 2              |     Worker tries to log in but is not able      |
| 3              |    The account of the worker must be created    |
| 4              |           The worker is able to login           |

### Use case 2, UC2

| Actors Involved  |         Worker         |
| ---------------- | :--------------------: |
| Precondition     | A worker is logged in  |
| Post condition   | A worker is logged out |
| Nominal Scenario |         Logout         |
| Variants         |                        |

### Use case 3, UC3

| Actors Involved  |            Worker             |
| ---------------- | :---------------------------: |
| Precondition     | Worker account doesn't exists |
| Post condition   |               -               |
| Nominal Scenario |        Create account         |
| Variants         |    Unable to add the user     |

| Scenario 1.1   |                                               |
| -------------- | :-------------------------------------------: |
| Precondition   |         Worker account doesn't exists         |
| Post condition |         Worker account doesn't exists         |
| Step           |                  Description                  |
| 1              |      The worker doesn't have an account       |
| 2              |  The manager is asked to create the account   |
| 3              | The manager is not able to create the account |

| Scenario 1.2   |                                            |
| -------------- | :----------------------------------------: |
| Precondition   |       Worker account doesn't exists        |
| Post condition |           Worker account exists            |
| Step           |                Description                 |
| 1              |     The worker doesn't have an account     |
| 2              | The manager is asked to create the account |
| 3              |         The worker has an account          |

### Use case 4, UC3

| Actors Involved  |                      Worker                       |
| ---------------- | :-----------------------------------------------: |
| Precondition     |                         -                         |
| Post condition   |       Worker personal details are modified        |
| Nominal Scenario |                   Modification                    |
| Variants         | Delete a worker, cannot find the worker to modify |

| Scenario 1.1   |                                                      |
| -------------- | :--------------------------------------------------: |
| Precondition   |             Worker account has to exist              |
| Post condition |         Worker personal details are modified         |
| Step           |                     Description                      |
| 1              | Some personal data of the worker need to be modified |
| 2              |                  Search the worker                   |
| 3              |             Modify his/her personal data             |



| Scenario 1.2   |                                                      |
| -------------- | :--------------------------------------------------: |
| Precondition   |            Worker account does not exist             |
| Post condition |         Worker personal details are modified         |
| Step           |                     Description                      |
| 1              | Some personal data of the worker need to be modified |
| 2              |                  Search the worker                   |
| 3              |                Cannot find the worker                |
| 4              |           Create the account of the worker           |

| Scenario 1.1   |                                                      |
| -------------- | :--------------------------------------------------: |
| Precondition   |             Worker account has to exist              |
| Post condition |         Worker personal details are modified         |
| Step           |                     Description                      |
| 1              | Some personal data of the worker need to be modified |
| 2              |                  Search the worker                   |
| 3              |            Delete completely his account             |

### Use case 1, UC1 - Create fidelity card
| Actors Involved        | Cashier, Customer |
| ------------- |:-------------:|
|  Precondition     | Customer has not a fidelity card, Cashier can create a new fidelity card and is logged in |
|  Post condition     | Customer has a fidelity card |
|  Nominal Scenario     | Creation |
|  Variants     |  |


| Scenario 1.1 | |
| ------------- |:-------------:|
|  Precondition     | Customer has not a fidelity card, Cashier can create a new fidelity card and is logged in |
|  Post condition     | Customer has a fidelity card |
| Step#        | Description  |
|  1     | The customer has not a fidelity card |
|  2     | The cashier is asked to create the fidelity card |
| 3 | The customer fill the form |
| 4 | The cashier adds the new customer in the system |
| 5 | The customer has a fidelity card |

### Use case 2, UC2 - Delete fidelity card
| Actors Involved        | Cashier, Customer |
| ------------- |:-------------:|
|  Precondition     | Customer is owner of a fidelity card, Cashier can delete a fidelity card and is logged in |
|  Post condition     | Customer has not a fidelity card |
|  Nominal Scenario     | Deletion |
|  Variants     |  |


| Scenario 2.1 | |
| ------------- |:-------------:|
|  Precondition     |  Customer is owner of a fidelity card, Cashier can delete a fidelity card and is logged in |
|  Post condition     | Customer has not a fidelity card |
| Step#        | Description  |
|  1     | The customer want to delete her fidelity card |
|  2     | The cashier is asked to delete the fidelity card |
| 4 | The cashier delete the customer in the system |
| 5 | The customer has not a fidelity card and her data was deleted |

### Use case 3, UC3 - Search and modify customer details
| Actors Involved        | Cashier, Customer |
| ------------- |:-------------:|
|  Precondition     | Customer has already a fidelity card and her data is in the system, Cashier can modify a fidelity card data and is logged in |
|  Post condition     | Customer's data is updated |
|  Nominal Scenario     | Modification |
|  Variants     | Delete a customer, cannot find the customers to modify |


| Scenario 3.1 | |
| ------------- |:-------------:|
|  Precondition     | Customer has already a fidelity card and her data is in the system, Cashier can modify a fidelity card data and is logged in |
|  Post condition     | Customer's data is updated |
| Step#        | Description  |
| 1 | Some personal data of the customer need to be modified |
| 2 | The cashier is asked to modify the fidelity card data of the customer |
| 3 | The cashier searches the customer in the system |
| 4 | The customer tells the new personal data that need to be modified |
| 5 | The cashier modifies the customer's data in the system |
| 6 | The customer's data are updated |


| Scenario 3.2 | |
| ------------- |:-------------:|
|  Precondition     | Customer has already a fidelity card and her data is in the system, Cashier can modify a fidelity card data and is logged in |
|  Post condition     | Customer's data is updated |
| Step#        | Description  |
|  1     | Some personal data of the customer need to be modified |
|  2     | The cashier is asked to modity the fidelity card data of the customer |
| 3 | The cashier searches the customer in the system |
| 4 | The customer tells the new personal data that need to be modified |
| 5 | The cashier makes a mistake and delete the customer's data in the system |
| 6 | The cashier adds the customer's data again in the system |
| 7 | The customer has a new fidelity card and her data are updated |

| Scenario 3.3 | |
| ------------- |:-------------:|
|  Precondition     | Customer has already a fidelity card and her data is in the system, Cashier can modify a fidelity card data and is logged in |
|  Post condition     | Customer's data is updated |
| Step#        | Description  |
|  1     | Some personal data of the customer need to be modified |
|  2     | The cashier is asked to modity the fidelity card data of the customer |
| 3 | The cashier searches the customer in the system |
| 4 | The customer's data are not listed in the system |
| 5 | The cashier adds the customer's data again in the system with updated version |
| 6 | The customer has a new fidelity card and her data are updated |

### Use case 4, UC4 - Add a product

| Actors Involved        | Warehouse Worker |
| ------------- |:-------------:|
|  Precondition     | Warehouse Worker is logged in and can add a new product, the product is not in the system |
|  Post condition     | Product is in the system |
|  Nominal Scenario     | Creaction |
|  Variants     |  |


| Scenario 4.1 | |
| ------------- |:-------------:|
|  Precondition     | Warehouse Worker is logged in and can add a new product, the product is not in the system |
|  Post condition     | Product is in the system |
| Step#        | Description  |
| 1 | A new product must be added in the inventory |
| 2 | The Warehouse Worker fill the form with product data |
| 3 | The Warehouse Worker read the bar code of the product |
| 4 | The product is added |

### Use case 5, UC5 - delete a product
| Actors Involved        | Warehouse Worker |
| ------------- |:-------------:|
|  Precondition     | Warehouse Worker is logged in and can delete a product, the product is in the system |
|  Post condition     | Product is deleted |
|  Nominal Scenario     | Deletion |
|  Variants     | Unable to find the product |

| Scenario 5.1 | |
| ------------- |:-------------:|
|  Precondition     | Warehouse Worker is logged in and can delete products, the product is in the system |
|  Post condition     | Product is deleted |
| Step#        | Description  |
| 1 | A product must be deleted from the inventory |
| 2 | The warehouse worker searches the product |
| 3 | The product is deleted |

| Scenario 5.1   |                                                              |
| -------------- | :----------------------------------------------------------: |
| Precondition   | Warehouse Worker is logged in and can delete products, the product is in the system |
| Post condition |                The product is not in the list                |
| Step#          |                         Description                          |
| 1              |         A product must be deleted from the inventory         |
| 2              |          The warehouse worker searches the product           |
| 3              |                The product is not in the list                |

### Use case 6, UC6 - Modify a product

| Actors Involved        | Warehouse Worker |
| ------------- |:-------------:|
|  Precondition     | Warehouse Worker is logged in and can modify products, the product is in the system |
|  Post condition     | Product is updated |
|  Nominal Scenario     | Modification |
|  Variants     | Cannot find the product to modify |


| Scenario 6.1 | |
| ------------- |:-------------:|
|  Precondition     | Warehouse Worker is logged in and can modify products, the product is in the system |
|  Post condition     | Product is updated |
| Step#        | Description  |
| 1 | Some data of a product should be modifyed |
| 2 | The warehouse worker searches the product |
| 3 | Warehouse worker modifys the data |
| 4 | Product is updated |

| Scenario 6.2 | |
| ------------- |:-------------:|
|  Precondition     | Warehouse Worker is logged in and can modify products, the product is in the system |
|  Post condition     | Product is updated |
| Step#        | Description |
| 1 | Some data of a product should be modifyed |
| 2 | The warehouse worker searches the product |
| 3 | Product is not in the list |
| 4 | Warehouse worker adds the product in the system with updated data |
| 5 | Product is updated |

### Use case 6, UC6 - List all product
| Actors Involved        | Warehouse Worker, Owner, Manager, Cashier |
| ------------- |:-------------:|
|  Precondition     | Worker is lodded in |
|  Post condition     | List of products is shown |
|  Nominal Scenario     | Showing |
|  Variants     | System bug with the result of no product shown |


Use case 1, UC1 - Manage sale

| Actors Involved  |                      Cashier, Customer                       |
| :--------------: | :----------------------------------------------------------: |
|   Precondition   | Customer chose one or more products to buy, cashier is free and can manage the transaction |
|  Post condition  |                              -                               |
| Nominal Scenario |                         Manage sale                          |
|     Variants     |          Customer can pay with cash or credit card           |
|                  |      Customer have not enough cash to pay all products       |
|                  | Customer have not enough credit on his card to pay all products |
|                  |       Payment with card fails due to network condition       |

|  Scenario 1.1  |                    Customer pay with cash                    |
| :------------: | :----------------------------------------------------------: |
|  Precondition  | Customer chose one or more products to buy, cashier is free and can manage the transaction |
| Post condition | Customer bought one or more products and receive a receipt, transaction is logged in the system |
|     Step #     |                         Description                          |
|       1        |               The cashier open new transaction               |
|       2        | The cashier scans the bar code of all customer chosen products |
|       3        | The cashier says to customer the total shopping cart amount  |
|       4        |            The costumer chooses to pay with cash             |
|       5        |                       Payment is done                        |
|       6        | The cashier close transaction and print transaction receipt  |
|       7        | The customer has their products and the transaction receipt  |

|  Scenario 1.2  |                Customer pay with credit card                 |
| :------------: | :----------------------------------------------------------: |
|  Precondition  | Customer chose one or more products to buy, cashier is free and can manage the transaction |
| Post condition | Customer bought one or more products and receive a receipt, transaction is logged in the system |
|     Step #     |                         Description                          |
|       1        |               The cashier open new transaction               |
|       2        | The cashier scans the bar code of all customer chosen products |
|       3        | The cashier says to customer the total shopping cart amount  |
|       4        |         The costumer chooses to pay with credit card         |
|       5        |     The cashier prepare the POS for managing the payment     |
|       6        |                The customer interact with POS                |
|       7        |        Payment is done and POS print its own receipt         |
|       8        | The cashier close transaction and print transaction receipt  |
|       9        | The customer has their products, the transaction receipt and POS receipt |

|  Scenario 1.3  | Customer chose to pay with cash but isn't enough for all products, customer chooses to cancel transaction |
| :------------: | :----------------------------------------------------------: |
|  Precondition  | Customer chose one or more products to buy, cashier is free and can manage the transaction |
| Post condition |     Customer didn't buy products, transaction is deleted     |
|     Step #     |                         Description                          |
|       1        |               The cashier open new transaction               |
|       2        | The cashier scans the bar code of all customer chosen products |
|       3        | The cashier says to customer the total shopping cart amount  |
|       4        |            The costumer chooses to pay with cash             |
|       5        | The customer has not enough cash and chooses to cancels transaction |
|       6        |               The cashier deletes transaction                |

|  Scenario 1.4  | Customer chose to pay with cash but isn't enough for all products, customer chooses to leave one or more products |
| :------------: | :----------------------------------------------------------: |
|  Precondition  | Customer chose one or more products to buy, cashier is free and can manage the transaction |
| Post condition | Customer bought part of initial products and receive a receipt, transaction is logged in the system |
|     Step #     |                         Description                          |
|       1        |               The cashier open new transaction               |
|       2        | The cashier scans the bar code of all customer chosen products |
|       3        | The cashier says to customer the total shopping cart amount  |
|       4        |             The costumer choose to pay with cash             |
|       5        |              The customer have not enough cash               |
|       6        | The costumer leave one or more products until the total shopping cart amount is less than or equal to his cash |
|       5        |                       Payment is done                        |
|       6        | The cashier closes transaction and prints transaction receipt |
|       7        | The customer has part of initial products and the transaction receipt |

|  Scenario 1.5  | Customer chose to pay with credit card but have not enough credit for all products, customer chooses to cancel transaction |
| :------------: | :----------------------------------------------------------: |
|  Precondition  | Customer chose one or more products to buy, cashier is free and can manage the transaction |
| Post condition |     Customer didn't buy products, transaction is deleted     |
|     Step #     |                         Description                          |
|       1        |               The cashier open new transaction               |
|       2        | The cashier scans the bar code of all customer chosen products |
|       3        | The cashier says to customer the total shopping cart amount  |
|       4        |         The costumer chooses to pay with credit card         |
|       5        |     The cashier prepare the POS for managing the payment     |
|       6        |                The customer interact with POS                |
|       7        | Payment fails due to not sufficient balance on credit card and customer chooses to cancel transaction |
|       8        |               The cashier deletes transaction                |

|  Scenario 1.6  | Customer chose to pay with credit card but transaction fails due to network condition |
| :------------: | :----------------------------------------------------------: |
|  Precondition  | Customer chose one or more products to buy, cashier is free and can manage the transaction |
| Post condition |     Customer didn't buy products, transaction is deleted     |
|     Step #     |                         Description                          |
|       1        |               The cashier open new transaction               |
|       2        | The cashier scans the bar code of all customer chosen products |
|       3        | The cashier says to customer the total shopping cart amount  |
|       4        |         The costumer chooses to pay with credit card         |
|       5        |     The cashier prepare the POS for managing the payment     |
|       6        |                The customer interact with POS                |
|       7        | Payment fails due to network condition, transaction must be canceled |
|       8        |               The cashier deletes transaction                |

### Use Case 2, UC2 - Show incomes on fixed time window

| Actors Involved  |                            Owner                             |
| :--------------: | :----------------------------------------------------------: |
|   Precondition   | System can access previously saved transactions data and products data |
|  Post condition  |                              -                               |
| Nominal Scenario |                         Show incomes                         |
|     Variants     |            No transactions in the selected window            |

|  Scenario 2.1  |                                                              |
| :------------: | :----------------------------------------------------------: |
|  Precondition  | System can access previously saved transactions data and products data |
| Post condition |                     System shows incomes                     |
|     Step #     |                         Description                          |
|       1        |   The manager or owner requires the calculation of incomes   |
|       2        |         The manager or owner selects the time window         |
|       3        |   The system recovers transactions on selected time window   |
|       4        | For each transaction the system recovers purchase price of all products in transaction |
|       5        |       The system computes the net and gross positions        |
|       6        |             System show net and gross positions              |

|  Scenario 2.2  |            No transactions in the selected window            |
| :------------: | :----------------------------------------------------------: |
|  Precondition  | System can access previously saved transactions data and products data |
| Post condition |            System notifies the absence of incomes            |
|     Step #     |                         Description                          |
|       1        |   The manager or owner requires the calculation of incomes   |
|       2        |         The manager or owner selects the time window         |
|       3        |   The system recovers transactions on selected time window   |
|       4        |              System have not found transactions              |
|       6        |            System notifies the absence of incomes            |

#### Use Case 3, UC3 - Show product sell statistics

| Actors Involved  |                        Manager, Owner                        |
| :--------------: | :----------------------------------------------------------: |
|   Precondition   | System can access previously saved transactions data and products data |
|  Post condition  |                              -                               |
| Nominal Scenario |                   Show product statistics                    |
|     Variants     |                      Product not found                       |

|  Scenario 3.1  |                                                              |
| :------------: | :----------------------------------------------------------: |
|  Precondition  | System can access previously saved transactions data and products data |
| Post condition |             System shows product sell statistics             |
|     Step #     |                         Description                          |
|       1        | The manager or owner requires the calculation of product sell statistics |
|       2        |           The manager or owner selects the product           |
|       3        |            The system search the selected product            |
|       4        |      The system recovers transactions with this product      |
|       5        | For each transaction the system saves number of copies sold for that product and date |
|       6        |      The system computes the total copies on every date      |
|       7        |       System show sell statistics of selected product        |

|  Scenario 3.2  |                      Product not found                       |
| :------------: | :----------------------------------------------------------: |
|  Precondition  | System can access previously saved transactions data and products data |
| Post condition |            System notifies the absence of product            |
|     Step #     |                         Description                          |
|       1        | The manager or owner requires the calculation of product sell statistics |
|       2        |           The manager or owner selects the product           |
|       3        |            The system search the selected product            |
|       3        |              The system cannot find the product              |
|       4        |            System notifies the absence of product            |

#### Use Case 4, UC4 - Show product sell statistics

| Actors Involved  |          Manager, Owner          |
| :--------------: | :------------------------------: |
|   Precondition   | System can access customers data |
|  Post condition  | System shows customer statistics |
| Nominal Scenario |     Show customer statistics     |
|     Variants     |                                  |


### Use Case /PROGN/, UC /PROGN/ - Create Order

|  Actor Involved  | Shop Owner                                                   |
| :--------------: | :----------------------------------------------------------- |
|   Precondition   | Items I exists (i.e.:  known by the system)                  |
|                  | Iitems are available                                         |
|  Post Condition  | The order is created                                         |
| Nominal Scenario | The shop owner wants to order some I. <br> The SO selects I and quantity. <br> O summary is displayed. <br> Confirmation is asked. |
|     Variants     | One or more I are not available. <br> The System reports the issue prompting to remove items or cancel O. |
|                  | SO can repeat a previous O. <br> O summary is shown. <br> Confirmation is required. |
|                  | SO can make an O periodic (PO). <br> A maximum price and O timing is required. |


### Use Case /PROGN+1/, UC /PROGN+1/ - Modify an Existing Order

|  Actor Involved  | Shop Owner                                                                                                        |
| :--------------: | :---------------------------------------------------------------------------------------------------------------- |
|   Precondtion    | Order O exists                                                                                                    |
|                  | O not shipped yet                                                                                                 |
|  Post Condition  | O is modified                                                                                                     |
| Nominal Scenario | SO wants to modify O. <br>  He adds/removes items or alters quantities. <br>                                      |
|     Variants     | O paid but not shipped. <br> System S lists possibile modifications.                                              |
|                  | SO can modify a PO. <br> He can additionally modify timing an maximum price.                                      |
|                  | SO decides to delete an order. <br> If the order is not shipped yet the system cancels the order and marks it so. |


# Glossary

\<use UML class diagram to define important terms, or concepts in the domain of the system, and their relationships> 

\<concepts are used consistently all over the document, ex in use cases, requirements etc>

# System Design
\<describe here system design>

\<must be consistent with Context diagram>

# Deployment Diagram 

\<describe here deployment diagram >

