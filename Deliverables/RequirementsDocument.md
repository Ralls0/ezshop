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
	
- [Stories and personas](#stories-and-personas)
- [Functional and non functional requirements](#functional-and-non-functional-requirements)
	+ [Functional Requirements](#functional-requirements)
	+ [Non functional requirements](#non-functional-requirements)
- [Use case diagram and use cases](#use-case-diagram-and-use-cases)
	+ [Use case diagram](#use-case-diagram)
	+ [Use cases](#use-cases)
    	+ [Relevant scenarios](#relevant-scenarios)
- [Glossary](#glossary)
- [System design](#system-design)
- [Deployment diagram](#deployment-diagram)

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
| Manager | Manager of the shop |
| Warehouse Worker | Workers that manage the inventory |
| Maintainers | People that interact (remotely of phisically) with the systems by maintaining it (technical support) |
| Customers        |     People that interact with the sytems buying products     |
| Devices          | All devices that are needed by the systems (computers, smartphones etc..) |
| Company          | Company that developes the system. It's workers are developers and engineers |

# Context Diagram and interfaces

## Context Diagram
\<Define here Context diagram using UML use case diagram>

\<actors are a subset of stakeholders>

## Interfaces

\<GUIs will be described graphically in a separate document>

| Actor            | Logical Interface       | Physical Interface                 |
| ---------------- | ----------------------- | ---------------------------------- |
| Owner, manager   | GUI of the application  | Screen, keyboard on PC             |
| Cashier          | GUI of the application  | Screen, touch of the cash register |
| Warehouse Worker | GUI of the application  | Touch screen of the smartphone     |
| Maintainers      | Phone call/screen share | Screen, keyboard on PC             |
| Devices          | **Database query**      | Network link                       |

# Stories and personas
\<A Persona is a realistic impersonation of an actor. Define here a few personas and describe in plain text how a persona interacts with the system>

\<Persona is-an-instance-of actor>

\<stories will be formalized later as scenarios in use cases>


# Functional and non functional requirements

## Functional Requirements

\<In the form DO SOMETHING, or VERB NOUN, describe high level capabilities of the system>

\<they match to high level use cases>

| ID        | Description  |
| ------------- |:-------------:|
|  FR1     | Authorizaton and authentication |
|  FR1.1  | Login |
| FR1.2 | Logout |
| FR2 | Manage workers |
| FR2.1 | Add a new user (manager, warehouse workers, cashier) |
| FR2.2 | Modify a worker |
| FR2.3 | Delete an exsisting worker |
| FR2.4 | Search a worker |
| FR3 | Manage customers |
| FR3.1 | Create/delete fidelity card |
| FR3.2 | Search and modify customer details |
| FR4 | Manage inventory |
| FR4.1 | Add/delete/modify a product |
| FR4.2 | List product |
| FR5 | Contact the maintainers |
| FR6 | Manage sales |
| FR6.1 | Open transaction |
| FR6.2 | Manage the shopping cart |
| FR6.3 | Visualize the amount to pay |
| FR6.4 | Select payment option |
| FR6.5 | Manage fidelity card |
| FR6.6 | Print the receipt |
| FR6.7 | Close transaction |
| FR7 | Support accounting |
| FR7.1 | List transactions |
| FR7.2 |                  Visualize incomes                   |
| FR7.3 |             Visualize product statistics             |
| FR7.4 |                 Customers statistics                 |
| FR8   |                   Manage supplying                   |
| FR8.1 | Create order |
| FR8.2 | Delete order |
| FR8.3 | Modify order |
| FR8.4 | Manage periodic orders |
| FR9 | Send promotion email to all customers |

## Non Functional Requirements

\<Describe constraints on functional requirements>

| ID        | Type (efficiency, reliability, ..)           | Description  | Refers to |
| ------------- |:-------------:| :-----:| -----:|
|  NFR1     | Usability | Application should be used with minimal training for the worker | All FR |
|  NFR2     | Privacy | Data of one user must be visibile only to the manager/owner | FR3 |
|  NFR3     | Portability | Cross platform | All FR |
| NFR4 | Reliability | The system can work just with a local network | All FR |


# Use case diagram and use cases


## Use case diagram
\<define here UML Use case diagram UCD summarizing all use cases, and their relationships>


\<next describe here each use case in the UCD>
### Use case 1, UC1
| Actors Involved        |  |
| ------------- |:-------------:|
|  Precondition     | \<Boolean expression, must evaluate to true before the UC can start> |
|  Post condition     | \<Boolean expression, must evaluate to true after UC is finished> |
|  Nominal Scenario     | \<Textual description of actions executed by the UC> |
|  Variants     | \<other executions, ex in case of errors> |

##### Scenario 1.1 

\<describe here scenarios instances of UC1>

\<a scenario is a sequence of steps that corresponds to a particular execution of one use case>

\<a scenario is a more formal description of a story>

\<only relevant scenarios should be described>

| Scenario 1.1 | |
| ------------- |:-------------:|
|  Precondition     | \<Boolean expression, must evaluate to true before the scenario can start> |
|  Post condition     | \<Boolean expression, must evaluate to true after scenario is finished> |
| Step#        | Description  |
|  1     |  |
|  2     |  |
|  ...     |  |

##### Scenario 1.2

##### Scenario 1.x

### Use case 2, UC2
..

### Use case x, UCx
..



# Glossary

\<use UML class diagram to define important terms, or concepts in the domain of the system, and their relationships> 

\<concepts are used consistently all over the document, ex in use cases, requirements etc>

# System Design
\<describe here system design>

\<must be consistent with Context diagram>

# Deployment Diagram 

\<describe here deployment diagram >

