# Design Document 


Authors: 

Date:

Version:


# Contents

- [Design Document](#design-document)
- [Contents](#contents)
- [Instructions](#instructions)
- [High level design](#high-level-design)
- [Low level design](#low-level-design)
- [Verification Traceability Matrix](#verification-traceability-matrix)
- [Verification sequence diagrams](#verification-sequence-diagrams)

# Instructions

The design must satisfy the Official Requirements document, notably functional and non functional requirements

# High level design 

We choose the MVC pattern
<report package diagram>

```plantuml
@startuml

note "Architecture \nIt is a stand alone application \nThe architectural pattern used is MVC \nWe have 2 layers, as show in the diagram" as N1


package "GUI" {
  
}
package "Model And Application Logic" {
  
}

@enduml
```


# Low level design

# Verification Traceability Matrix

for each functional requirement from the requirement document, list which classes concur to implement it

|                           |  FR1  |  FR2  |  FR3  |  FR4  |  FR5  |  FR6  |  FR7  |  FR8  |  FR9  |
| ------------------------- | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| Account Book              |       |   ?   |       | **X** |       |       |       |       |       |
| Return Transaction        |       |   ?   |       |       |       |       |       |       |       |
| Order                     |       |   ?   |       | **X** |       |       |       |       |       |
| BalanceOperation          |       |   ?   |       | **X** |       |       |       |       |       |
| Shop                      | **X** |   ?   | **X** | **X** | **X** |       |       |       |       |
| User                      | **X** |   ?   |       |       |       |       |       |       |       |
| CreditCardCircuit         |       |   ?   |       |       |       |       |       |       |       |
| Sale Transaction          |       |   ?   |       |       |       |       |       |       |       |
| ProdctQuantityAndDiscount |       |   ?   | **X** | **X** |       |       |       |       |       |
| ProductType               |       |   ?   | **X** | **X** |       |       |       |       |       |
| Position                  |       |   ?   | **X** | **X** |       |       |       |       |       |
| LoyaltyCard               |       |   ?   |       |       | **X** |       |       |       |       |
| Customer                  |       |   ?   |       |       | **X** |       |       |       |       |

(Ho messo FR2 ma negli OR non c'è...)












# Verification sequence diagrams 
\<select key scenarios from the requirement document. For each of them define a sequence diagram showing that the scenario can be implemented by the classes and methods in the design>

