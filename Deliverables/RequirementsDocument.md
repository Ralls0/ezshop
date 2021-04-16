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
	- [Functional Requirements](#functional-requirements)
	- [Non Functional Requirements](#non-functional-requirements)
- [Use case diagram and use cases](#use-case-diagram-and-use-cases)
	- [Use case diagram](#use-case-diagram)
		- [Use case 1, UC1](#use-case-1-uc1)
				- [Scenario 1.1](#scenario-11)
		- [Use case 2, UC2](#use-case-2-uc2)
		- [Use case 3, UC3](#use-case-3-uc3)
		- [Use case 4, UC3](#use-case-4-uc3)
		- [Use case 1, UC1 - Create fidelity card](#use-case-1-uc1---create-fidelity-card)
		- [Use case 2, UC2 - Delete fidelity card](#use-case-2-uc2---delete-fidelity-card)
		- [Use case 3, UC3 - Search and modify customer details](#use-case-3-uc3---search-and-modify-customer-details)
		- [Use case 4, UC4 - Add a product](#use-case-4-uc4---add-a-product)
		- [Use case 5, UC5 - delete a product](#use-case-5-uc5---delete-a-product)
		- [Use case 6, UC6 - Modify a product](#use-case-6-uc6---modify-a-product)
		- [Use case 6, UC6 - List all product](#use-case-6-uc6---list-all-product)
		- [Use Case 2, UC2 - Show incomes on fixed time window](#use-case-2-uc2---show-incomes-on-fixed-time-window)
			- [Use Case 3, UC3 - Show product sell statistics](#use-case-3-uc3---show-product-sell-statistics)
			- [Use Case 4, UC4 - Show product sell statistics](#use-case-4-uc4---show-product-sell-statistics)
		- [Use Case /PROGN/, UC /PROGN/ - Create Order](#use-case-progn-uc-progn---create-order)
		- [Use Case /PROGN+1/, UC /PROGN+1/ - Modify an Existing Order](#use-case-progn1-uc-progn1---modify-an-existing-order)
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


| Stakeholder name    |                         Description                          |
| ------------------- | :----------------------------------------------------------: |
| Owner               |                      Owner of the shop                       |
| Cashier             |         Workers that manage sales through the system         |
| Warehouse Worker    |              Workers that manage the inventory               |
| Customer            |                Customers with a fidelity card                |
| Anonymous Customers | People that interact with the sytems buying products but without a fidelity card |
| Fidelity Card       |                Card associated to a customer                 |
| POS                 |                        Point of sale                         |
| Product             |                   Product sold by the shop                   |
| Cash register       |                Register used to handle sales                 |
| Supplier            |               Person/company for the supplying               |

# Context Diagram and interfaces

## Context Diagram
```plantuml
@startuml

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

| ID      |                      Description                       |
| ------- | :----------------------------------------------------: |
| FR1     |            Authorizaton and authentication             |
| FR1.1   |                         Login                          |
| FR1.2   |                         Logout                         |
| FR2     |                     Manage workers                     |
| FR2.1   | Add a new worker (manager, warehouse workers, cashier) |
| FR2.2   |                    Modify a worker                     |
| FR2.3   |               Delete an exsisting worker               |
| FR2.4   |                    Search a worker                     |
| FR2.5   |                    List all worker                     |
| FR3     |                    Manage customers                    |
| FR3.1   |              Create/delete fidelity card               |
| FR3.2   |           Search and modify customer details           |
| FR3.3   |                   List all customer                    |
| FR4     |                    Manage inventory                    |
| FR4.1   |                      Add product                       |
| FR4.1.1 |               Read bar code of a product               |
| FR4.2   |                     Delete product                     |
| FR4.3   |                     Modify product                     |
| FR4.4   |                   List all products                    |
| FR4.5   |                    Manage supplying                    |
| FR4.5.1 |                      Create order                      |
| FR4.5.2 |                      Delete order                      |
| FR4.5.3 |                      Modify order                      |
| FR4.5.4 |                 Manage periodic orders                 |
| FR5     |                      Manage sales                      |
| FR5.1   |                    Open transaction                    |
| FR5.2   |                    Manage products                     |
| FR5.2.1 |                     Read bar code                      |
| FR5.2.2 |          Add the product to the shopping cart          |
| FR5.3   |              Visualize the amount to pay               |
| FR5.4   |                 Manage payment options                 |
| FR5.4.1 |                 Start POS transaction                  |
| FR5.4.2 |                  Enter payment amount                  |
| FR5.4.3 |                  End POS transaction                   |
| FR5.5   |           Read bar code of the fidelity card           |
| FR5.6   |                   Print the receipt                    |
| FR5.7   |                   Close transaction                    |
| FR6     |                   Support accounting                   |
| FR6.1   |                   List transactions                    |
| FR6.2   |                   Visualize incomes                    |
| FR6.3   |              Visualize product statistics              |
| FR6.4   |                  Customers statistics                  |

## Non Functional Requirements

| ID   | Type (efficiency, reliability, ..) |                                 Description                                 | Refers to |
| ---- | :--------------------------------: | :-------------------------------------------------------------------------: | --------: |
| NFR1 |             Usability              |       Application should be used with minimal training for the worker       |    All FR |
| NFR2 |              Privacy               | Data of one user must be encrypted and  visibile only to the manager/owner. |       FR3 |
| NFR3 |            Portability             |                               Cross platform                                |    All FR |
| NFR4 |            Reliability             |                The system can work just with a local network                |    All FR |


# Use case diagram and use cases


## Use case diagram
```plantuml
@startuml

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
| Scenario 1.1   |                     Worker                      |
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

##### Scenario 3.1 

| Scenario 3.1   |                                               |
| -------------- | :-------------------------------------------: |
| Precondition   |         Worker account doesn't exists         |
| Post condition |         Worker account doesn't exists         |
| Step           |                  Description                  |
| 1              |      The worker doesn't have an account       |
| 2              |  The manager is asked to create the account   |
| 3              | The manager is not able to create the account |

##### Scenario 3.2 

| Scenario 3.2   |                                            |
| -------------- | :----------------------------------------: |
| Precondition   |       Worker account doesn't exists        |
| Post condition |           Worker account exists            |
| Step           |                Description                 |
| 1              |     The worker doesn't have an account     |
| 2              | The manager is asked to create the account |
| 3              |         The worker has an account          |

### Use case 4, UC4

| Actors Involved  |                      Worker                       |
| ---------------- | :-----------------------------------------------: |
| Precondition     |                         -                         |
| Post condition   |       Worker personal details are modified        |
| Nominal Scenario |                   Modification                    |
| Variants         | Delete a worker, cannot find the worker to modify |

##### Scenario 4.1

| Scenario 4.1   |                                                      |
| -------------- | :--------------------------------------------------: |
| Precondition   |             Worker account has to exist              |
| Post condition |         Worker personal details are modified         |
| Step           |                     Description                      |
| 1              | Some personal data of the worker need to be modified |
| 2              |                  Search the worker                   |
| 3              |             Modify his/her personal data             |

##### Scenario 4.2

| Scenario 4.2   |                                                      |
| -------------- | :--------------------------------------------------: |
| Precondition   |            Worker account does not exist             |
| Post condition |         Worker personal details are modified         |
| Step           |                     Description                      |
| 1              | Some personal data of the worker need to be modified |
| 2              |                  Search the worker                   |
| 3              |                Cannot find the worker                |
| 4              |           Create the account of the worker           |

##### Scenario 4.3

| Scenario 4.3   |                                                      |
| -------------- | :--------------------------------------------------: |
| Precondition   |             Worker account has to exist              |
| Post condition |         Worker personal details are modified         |
| Step           |                     Description                      |
| 1              | Some personal data of the worker need to be modified |
| 2              |                  Search the worker                   |
| 3              |            Delete completely his account             |

### Use case 5, UC5

| Actors Involved  |                                     Cashier, Customer                                     |
| ---------------- | :---------------------------------------------------------------------------------------: |
| Precondition     | Customer has not a fidelity card, Cashier can create a new fidelity card and is logged in |
| Post condition   |                               Customer has a fidelity card                                |
| Nominal Scenario |                                         Creation                                          |
| Variants         |                                                                                           |

##### Scenario 5.1

| Scenario 5.1   |                                                                                           |
| -------------- | :---------------------------------------------------------------------------------------: |
| Precondition   | Customer has not a fidelity card, Cashier can create a new fidelity card and is logged in |
| Post condition |                               Customer has a fidelity card                                |
| Step#          |                                        Description                                        |
| 1              |                           The customer has not a fidelity card                            |
| 2              |                     The cashier is asked to create the fidelity card                      |
| 3              |                                The customer fill the form                                 |
| 4              |                      The cashier adds the new customer in the system                      |
| 5              |                             The customer has a fidelity card                              |

### Use case 6, UC6

| Actors Involved  |                                     Cashier, Customer                                     |
| ---------------- | :---------------------------------------------------------------------------------------: |
| Precondition     | Customer is owner of a fidelity card, Cashier can delete a fidelity card and is logged in |
| Post condition   |                             Customer has not a fidelity card                              |
| Nominal Scenario |                                         Deletion                                          |
| Variants         |                                                                                           |

##### Scenario 6.1

| Scenario 6.1   |                                                                                           |
| -------------- | :---------------------------------------------------------------------------------------: |
| Precondition   | Customer is owner of a fidelity card, Cashier can delete a fidelity card and is logged in |
| Post condition |                             Customer has not a fidelity card                              |
| Step#          |                                        Description                                        |
| 1              |                       The customer want to delete her fidelity card                       |
| 2              |                     The cashier is asked to delete the fidelity card                      |
| 4              |                       The cashier delete the customer in the system                       |
| 5              |               The customer has not a fidelity card and her data was deleted               |

### Use case 7, UC7

| Actors Involved  |                                                      Cashier, Customer                                                       |
| ---------------- | :--------------------------------------------------------------------------------------------------------------------------: |
| Precondition     | Customer has already a fidelity card and her data is in the system, Cashier can modify a fidelity card data and is logged in |
| Post condition   |                                                  Customer's data is updated                                                  |
| Nominal Scenario |                                                         Modification                                                         |
| Variants         |                                    Delete a customer, cannot find the customers to modify                                    |


##### Scenario 7.1

| Scenario 7.1   |                                                                                                                              |
| -------------- | :--------------------------------------------------------------------------------------------------------------------------: |
| Precondition   | Customer has already a fidelity card and her data is in the system, Cashier can modify a fidelity card data and is logged in |
| Post condition |                                                  Customer's data is updated                                                  |
| Step#          |                                                         Description                                                          |
| 1              |                                    Some personal data of the customer need to be modified                                    |
| 2              |                            The cashier is asked to modify the fidelity card data of the customer                             |
| 3              |                                       The cashier searches the customer in the system                                        |
| 4              |                              The customer tells the new personal data that need to be modified                               |
| 5              |                                    The cashier modifies the customer's data in the system                                    |
| 6              |                                               The customer's data are updated                                                |

##### Scenario 7.2

| Scenario 7.2   |                                                                                                                              |
| -------------- | :--------------------------------------------------------------------------------------------------------------------------: |
| Precondition   | Customer has already a fidelity card and her data is in the system, Cashier can modify a fidelity card data and is logged in |
| Post condition |                                                  Customer's data is updated                                                  |
| Step#          |                                                         Description                                                          |
| 1              |                                    Some personal data of the customer need to be modified                                    |
| 2              |                            The cashier is asked to modity the fidelity card data of the customer                             |
| 3              |                                       The cashier searches the customer in the system                                        |
| 4              |                              The customer tells the new personal data that need to be modified                               |
| 5              |                           The cashier makes a mistake and delete the customer's data in the system                           |
| 6              |                                   The cashier adds the customer's data again in the system                                   |
| 7              |                                The customer has a new fidelity card and her data are updated                                 |

##### Scenario 7.3

| Scenario 7.3   |                                                                                                                              |
| -------------- | :--------------------------------------------------------------------------------------------------------------------------: |
| Precondition   | Customer has already a fidelity card and her data is in the system, Cashier can modify a fidelity card data and is logged in |
| Post condition |                                                  Customer's data is updated                                                  |
| Step#          |                                                         Description                                                          |
| 1              |                                    Some personal data of the customer need to be modified                                    |
| 2              |                            The cashier is asked to modity the fidelity card data of the customer                             |
| 3              |                                       The cashier searches the customer in the system                                        |
| 4              |                                       The customer's data are not listed in the system                                       |
| 5              |                        The cashier adds the customer's data again in the system with updated version                         |
| 6              |                                The customer has a new fidelity card and her data are updated                                 |

### Use case 8, UC8

| Actors Involved  |                                     Warehouse Worker                                      |
| ---------------- | :---------------------------------------------------------------------------------------: |
| Precondition     | Warehouse Worker is logged in and can add a new product, the product is not in the system |
| Post condition   |                                 Product is in the system                                  |
| Nominal Scenario |                                         Creaction                                         |
| Variants         |                                                                                           |

##### Scenario 8.1

| Scenario 8.1   |                                                                                           |
| -------------- | :---------------------------------------------------------------------------------------: |
| Precondition   | Warehouse Worker is logged in and can add a new product, the product is not in the system |
| Post condition |                                 Product is in the system                                  |
| Step#          |                                        Description                                        |
| 1              |                       A new product must be added in the inventory                        |
| 2              |                   The Warehouse Worker fill the form with product data                    |
| 3              |                   The Warehouse Worker read the bar code of the product                   |
| 4              |                                   The product is added                                    |

### Use case 9, UC9

| Actors Involved  |                                   Warehouse Worker                                   |
| ---------------- | :----------------------------------------------------------------------------------: |
| Precondition     | Warehouse Worker is logged in and can delete a product, the product is in the system |
| Post condition   |                                  Product is deleted                                  |
| Nominal Scenario |                                       Deletion                                       |
| Variants         |                              Unable to find the product                              |

##### Scenario 9.1

| Scenario 9.1   |                                                                                     |
| -------------- | :---------------------------------------------------------------------------------: |
| Precondition   | Warehouse Worker is logged in and can delete products, the product is in the system |
| Post condition |                                 Product is deleted                                  |
| Step#          |                                     Description                                     |
| 1              |                    A product must be deleted from the inventory                     |
| 2              |                      The warehouse worker searches the product                      |
| 3              |                               The product is deleted                                |

##### Scenario 9.2

| Scenario 9.2   |                                                                                     |
| -------------- | :---------------------------------------------------------------------------------: |
| Precondition   | Warehouse Worker is logged in and can delete products, the product is in the system |
| Post condition |                           The product is not in the list                            |
| Step#          |                                     Description                                     |
| 1              |                    A product must be deleted from the inventory                     |
| 2              |                      The warehouse worker searches the product                      |
| 3              |                           The product is not in the list                            |

### Use case 10, UC10

| Actors Involved  |                                  Warehouse Worker                                   |
| ---------------- | :---------------------------------------------------------------------------------: |
| Precondition     | Warehouse Worker is logged in and can modify products, the product is in the system |
| Post condition   |                                 Product is updated                                  |
| Nominal Scenario |                                    Modification                                     |
| Variants         |                          Cannot find the product to modify                          |

##### Scenario 10.1

| Scenario 10.1   |                                                                                     |
| -------------- | :---------------------------------------------------------------------------------: |
| Precondition   | Warehouse Worker is logged in and can modify products, the product is in the system |
| Post condition |                                 Product is updated                                  |
| Step#          |                                     Description                                     |
| 1              |                      Some data of a product should be modifyed                      |
| 2              |                      The warehouse worker searches the product                      |
| 3              |                          Warehouse worker modifys the data                          |
| 4              |                                 Product is updated                                  |

##### Scenario 10.2

| Scenario 10.2   |                                                                                     |
| -------------- | :---------------------------------------------------------------------------------: |
| Precondition   | Warehouse Worker is logged in and can modify products, the product is in the system |
| Post condition |                                 Product is updated                                  |
| Step#          |                                     Description                                     |
| 1              |                      Some data of a product should be modifyed                      |
| 2              |                      The warehouse worker searches the product                      |
| 3              |                             Product is not in the list                              |
| 4              |          Warehouse worker adds the product in the system with updated data          |
| 5              |                                 Product is updated                                  |

### Use case 11, UC11

| Actors Involved  |   Warehouse Worker, Owner, Manager, Cashier    |
| ---------------- | :--------------------------------------------: |
| Precondition     |              Worker is lodded in               |
| Post condition   |           List of products is shown            |
| Nominal Scenario |                    Showing                     |
| Variants         | System bug with the result of no product shown |


### Use case 12, UC12

| Actors Involved  |                                     Cashier, Customer                                      |
| :--------------: | :----------------------------------------------------------------------------------------: |
|   Precondition   | Customer chose one or more products to buy, cashier is free and can manage the transaction |
|  Post condition  |                                             -                                              |
| Nominal Scenario |                                        Manage sale                                         |
|     Variants     |                         Customer can pay with cash or credit card                          |
|                  |                     Customer have not enough cash to pay all products                      |
|                  |              Customer have not enough credit on his card to pay all products               |
|                  |                      Payment with card fails due to network condition                      |

##### Scenario 12.1

|  Scenario 12.1  |                                     Customer pay with cash                                      |
| :------------: | :---------------------------------------------------------------------------------------------: |
|  Precondition  |   Customer chose one or more products to buy, cashier is free and can manage the transaction    |
| Post condition | Customer bought one or more products and receive a receipt, transaction is logged in the system |
|     Step #     |                                           Description                                           |
|       1        |                                The cashier open new transaction                                 |
|       2        |                 The cashier scans the bar code of all customer chosen products                  |
|       3        |                   The cashier says to customer the total shopping cart amount                   |
|       4        |                              The costumer chooses to pay with cash                              |
|       5        |                                         Payment is done                                         |
|       6        |                   The cashier close transaction and print transaction receipt                   |
|       7        |                   The customer has their products and the transaction receipt                   |

##### Scenario 12.2

|  Scenario 12.2  |                                  Customer pay with credit card                                  |
| :------------: | :---------------------------------------------------------------------------------------------: |
|  Precondition  |   Customer chose one or more products to buy, cashier is free and can manage the transaction    |
| Post condition | Customer bought one or more products and receive a receipt, transaction is logged in the system |
|     Step #     |                                           Description                                           |
|       1        |                                The cashier open new transaction                                 |
|       2        |                 The cashier scans the bar code of all customer chosen products                  |
|       3        |                   The cashier says to customer the total shopping cart amount                   |
|       4        |                          The costumer chooses to pay with credit card                           |
|       5        |                      The cashier prepare the POS for managing the payment                       |
|       6        |                                 The customer interact with POS                                  |
|       7        |                          Payment is done and POS print its own receipt                          |
|       8        |                   The cashier close transaction and print transaction receipt                   |
|       9        |            The customer has their products, the transaction receipt and POS receipt             |

##### Scenario 12.3

|  Scenario 12.3  | Customer chose to pay with cash but isn't enough for all products, customer chooses to cancel transaction |
| :------------: | :-------------------------------------------------------------------------------------------------------: |
|  Precondition  |        Customer chose one or more products to buy, cashier is free and can manage the transaction         |
| Post condition |                           Customer didn't buy products, transaction is deleted                            |
|     Step #     |                                                Description                                                |
|       1        |                                     The cashier open new transaction                                      |
|       2        |                      The cashier scans the bar code of all customer chosen products                       |
|       3        |                        The cashier says to customer the total shopping cart amount                        |
|       4        |                                   The costumer chooses to pay with cash                                   |
|       5        |                    The customer has not enough cash and chooses to cancels transaction                    |
|       6        |                                      The cashier deletes transaction                                      |

##### Scenario 12.4

|  Scenario 12.4  | Customer chose to pay with cash but isn't enough for all products, customer chooses to leave one or more products |
| :------------: | :---------------------------------------------------------------------------------------------------------------: |
|  Precondition  |            Customer chose one or more products to buy, cashier is free and can manage the transaction             |
| Post condition |        Customer bought part of initial products and receive a receipt, transaction is logged in the system        |
|     Step #     |                                                    Description                                                    |
|       1        |                                         The cashier open new transaction                                          |
|       2        |                          The cashier scans the bar code of all customer chosen products                           |
|       3        |                            The cashier says to customer the total shopping cart amount                            |
|       4        |                                       The costumer choose to pay with cash                                        |
|       5        |                                         The customer have not enough cash                                         |
|       6        |  The costumer leave one or more products until the total shopping cart amount is less than or equal to his cash   |
|       5        |                                                  Payment is done                                                  |
|       6        |                           The cashier closes transaction and prints transaction receipt                           |
|       7        |                       The customer has part of initial products and the transaction receipt                       |

##### Scenario 12.5

|  Scenario 12.5  | Customer chose to pay with credit card but have not enough credit for all products, customer chooses to cancel transaction |
| :------------: | :------------------------------------------------------------------------------------------------------------------------: |
|  Precondition  |                 Customer chose one or more products to buy, cashier is free and can manage the transaction                 |
| Post condition |                                    Customer didn't buy products, transaction is deleted                                    |
|     Step #     |                                                        Description                                                         |
|       1        |                                              The cashier open new transaction                                              |
|       2        |                               The cashier scans the bar code of all customer chosen products                               |
|       3        |                                The cashier says to customer the total shopping cart amount                                 |
|       4        |                                        The costumer chooses to pay with credit card                                        |
|       5        |                                    The cashier prepare the POS for managing the payment                                    |
|       6        |                                               The customer interact with POS                                               |
|       7        |           Payment fails due to not sufficient balance on credit card and customer chooses to cancel transaction            |
|       8        |                                              The cashier deletes transaction                                               |

##### Scenario 12.6

|  Scenario 12.6  |   Customer chose to pay with credit card but transaction fails due to network condition    |
| :------------: | :----------------------------------------------------------------------------------------: |
|  Precondition  | Customer chose one or more products to buy, cashier is free and can manage the transaction |
| Post condition |                    Customer didn't buy products, transaction is deleted                    |
|     Step #     |                                        Description                                         |
|       1        |                              The cashier open new transaction                              |
|       2        |               The cashier scans the bar code of all customer chosen products               |
|       3        |                The cashier says to customer the total shopping cart amount                 |
|       4        |                        The costumer chooses to pay with credit card                        |
|       5        |                    The cashier prepare the POS for managing the payment                    |
|       6        |                               The customer interact with POS                               |
|       7        |            Payment fails due to network condition, transaction must be canceled            |
|       8        |                              The cashier deletes transaction                               |

### Use Case 13, UC13

| Actors Involved  |                                 Owner                                  |
| :--------------: | :--------------------------------------------------------------------: |
|   Precondition   | System can access previously saved transactions data and products data |
|  Post condition  |                                   -                                    |
| Nominal Scenario |                              Show incomes                              |
|     Variants     |                 No transactions in the selected window                 |

##### Scenario 13.1

|  Scenario 13.1  |                                                                                        |
| :------------: | :------------------------------------------------------------------------------------: |
|  Precondition  |         System can access previously saved transactions data and products data         |
| Post condition |                                  System shows incomes                                  |
|     Step #     |                                      Description                                       |
|       1        |                The manager or owner requires the calculation of incomes                |
|       2        |                      The manager or owner selects the time window                      |
|       3        |                The system recovers transactions on selected time window                |
|       4        | For each transaction the system recovers purchase price of all products in transaction |
|       5        |                    The system computes the net and gross positions                     |
|       6        |                          System show net and gross positions                           |

##### Scenario 13.2

|  Scenario 13.2  |                 No transactions in the selected window                 |
| :------------: | :--------------------------------------------------------------------: |
|  Precondition  | System can access previously saved transactions data and products data |
| Post condition |                 System notifies the absence of incomes                 |
|     Step #     |                              Description                               |
|       1        |        The manager or owner requires the calculation of incomes        |
|       2        |              The manager or owner selects the time window              |
|       3        |        The system recovers transactions on selected time window        |
|       4        |                   System have not found transactions                   |
|       6        |                 System notifies the absence of incomes                 |

#### Use Case 14, UC14

| Actors Involved  |                             Manager, Owner                             |
| :--------------: | :--------------------------------------------------------------------: |
|   Precondition   | System can access previously saved transactions data and products data |
|  Post condition  |                                   -                                    |
| Nominal Scenario |                        Show product statistics                         |
|     Variants     |                           Product not found                            |

##### Scenario 14.1

|  Scenario 14.1  |                                                                                       |
| :------------: | :-----------------------------------------------------------------------------------: |
|  Precondition  |        System can access previously saved transactions data and products data         |
| Post condition |                         System shows product sell statistics                          |
|     Step #     |                                      Description                                      |
|       1        |       The manager or owner requires the calculation of product sell statistics        |
|       2        |                       The manager or owner selects the product                        |
|       3        |                        The system search the selected product                         |
|       4        |                  The system recovers transactions with this product                   |
|       5        | For each transaction the system saves number of copies sold for that product and date |
|       6        |                  The system computes the total copies on every date                   |
|       7        |                    System show sell statistics of selected product                    |

##### Scenario 14.2

|  Scenario 14.2  |                            Product not found                             |
| :------------: | :----------------------------------------------------------------------: |
|  Precondition  |  System can access previously saved transactions data and products data  |
| Post condition |                  System notifies the absence of product                  |
|     Step #     |                               Description                                |
|       1        | The manager or owner requires the calculation of product sell statistics |
|       2        |                 The manager or owner selects the product                 |
|       3        |                  The system search the selected product                  |
|       3        |                    The system cannot find the product                    |
|       4        |                  System notifies the absence of product                  |

#### Use Case 15, UC15

| Actors Involved  |          Manager, Owner          |
| :--------------: | :------------------------------: |
|   Precondition   | System can access customers data |
|  Post condition  | System shows customer statistics |
| Nominal Scenario |     Show customer statistics     |
|     Variants     |                                  |


#### Use Case 16, UC16

|  Actor Involved  |             Shop Owner              |
| :--------------: | :---------------------------------: |
|   Precondition   |             Items exist             |
|                  |         Items are available         |
|  Post Condition  |        The order is created         |
| Nominal Scenario |   Shop Owner creates a new order    |
|     Variants     |   Shop Owner repeats an old order   |
|                  | Shop Owner creates a periodic order |

##### Scenario 16.1

| Scenario 16.1 |        Succesful (Periodic) New Order        |
| :------------------: | :------------------------------------------: |
|     Precondition     |                 Items exist                  |
|    Post-Condition    |                Order Created                 |
|        Step#         |                 Description                  |
|          1           |         Shop Owner opens 'New Order'         |
|          2           |           Shop Owner picks an item           |
|          3           |         For each item picks quantity         |
|          4           | If periodic order mark it so, else skip to 5 |
|          5           |          Select how often to order           |
|          6           |      Select a maximum accepatble price       |
|          7           |          System shows order summary          |
|          8           |          Shop Owner confirms order           |
|          9           |               Order is created               |

##### Scenario 16.2

| Scenario 16.2 |                    Repeat Old Order                     |
| :------------------: | :-----------------------------------------------------: |
|     Precondition     |                       Order exist                       |
|    Post-Condition    |                   Order created anew                    |
|        Step#         |                       Description                       |
|          1           |             Shop Owner opens order history              |
|          2           |                Shop Owner picks an order                |
|          3           |         If not to repeat periodically skip to 5         |
|          4           | Select how often to repeat and maximum acceptable price |
|          5           |               System shows order summary                |
|          6           |                Shop Owner confirms order                |
|          7           |                    Order is created                     |

### Use Case 17, UC17

|  Actor Involved  |             Shop Owner              |
| :--------------: | :---------------------------------: |
|   Precondtion    |            Order exists             |
|  Post Condition  |                  -                  |
| Nominal Scenario |    Shop Owner modifies an order     |
|     Variants     |     Order paid but not shipped      |
|                  |       Order paid and shipped        |
|                  | Shop Owner modifes a periodic order |
|                  |     Shop Owner deletes an order     |

##### Scenario 17.1

| Scenario 17.1 |             Modify Order (Not Shipped)              |
| :------------------: | :-------------------------------------------------: |
|     Precondition     |                     Order exist                     |
|                      |         Order paid or not, but yet to ship          |
|    Post-Condition    |                   Order modified                    |
|        Step#         |                     Description                     |
|          1           |           Shop Owner opens order history            |
|          2           |              Shop Owner picks an order              |
|          3           | Alter quantities, items or mark order to be deleted |
|          4           |               System reports summary                |
|          5           |                 Shop Owner confirms                 |
|          6           |                  Order is modified                  |

##### Scenario 17.2

| Scenario 17.2 |           Modify Order (Shipped)            |
| :------------------: | :-----------------------------------------: |
|     Precondition     |                 Order exist                 |
|                      |           Order paid and shipped            |
|    Post-Condition    |         Order not modified modified         |
|        Step#         |                 Description                 |
|          1           |       Shop Owner opens order history        |
|          2           |          Shop Owner picks an order          |
|          3           | System prompts that order is not modifiable |
|          4           |            Order is not modified            |




# Glossary

```plantuml
@startuml
class order {
    int orderNumber
}

class supplier {
}

class catalogue {
}

class shop {
}

class Person {
    String name
    String surname
    int age
    String accountName
    String email
}

class owner {
}

class "Warehouse Worker" {
}

class cashier {
}

class customer {
}

class Product {
    String barCode
    int numberOfProducts
}

class "Product descriptor" {
    int ID
    String ProductAttribute
}

class "cash register" {
    int maxAmount
}

class inventory {
    int numberOfObjects
}

class transaction {
    int transactionNumber
    float amount
}

class "credit card" {
    int creditCardNumber
    int creditCardType
}

class cash {
    string currency
}

class "fidelity card" {
    int fideltyCardNumber
    string barCode
}

class POS {
}

shop --- inventory
shop --- catalogue
shop --- "*" order : issues
shop ---- "*" transaction : "manages"
shop ---up owner : manages 



catalogue -- "*" "Product descriptor" : contains

order "*" -- supplier
order -- "*" "Product descriptor"

Person <|-- owner
Person <|-- cashier
Person <|- "Warehouse Worker"
Person <|-- customer


customer --- "0..1" "fidelity card"
customer --- cash
customer --- "0..*" "credit card"

transaction ---up "0..1" cash
transaction "*" ---up "0..1" "credit card"
transaction "*" ---up "0..1" "fidelity card"

cash "*" --- "cash register" : contains

Product "*" ---up "Product descriptor" : is described by >
Product ---up transaction
Product "*" ---up inventory

"cash register" ---- cashier : < interacts with
"fidelity card" --- cashier : < manages

"Warehouse Worker" "*" -- "*" inventory

POS --up cashier : < interacts with
POS "0..1" --left "*" "credit card"
POS "0..1" --up "*" transaction 
@enduml
```

# System Design
Not really meaningful in this case. Only software components are needed.

# Deployment Diagram 

```plantuml
@startuml

node LocalServer
artifact EZShopApplication
node PCClient
node SmartphoneClient

LocalServer -- EZShopApplication
LocalServer -- "*" PCClient
LocalServer -- "*" SmartphoneClient 

@enduml
```



