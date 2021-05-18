# Unit Testing Documentation

Authors:

- S281564 Marco Manco
- S290136 Giovanni Pollo
- S292477 Matteo Quarta
- S292602 Davide Fersino

Date:

Version: 1.0

# Contents

- [Black Box Unit Tests](#black-box-unit-tests)

- [White Box Unit Tests](#white-box-unit-tests)

# Black Box Unit Tests

### **Class EZTicketEntry - method setBarCode**

**Criteria for method setBarCode:**

- barCode

**Predicates for method setBarCode:**

| Criteria      | Predicate              |
| ------------- | ---------------------- |
| _**barCode**_ | _**arbitrary values**_ |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|          |                 |

_**No boundaries**_

**Combination of predicates**:

| barCode         | Valid / Invalid | Description of the test case                                        | JUnit test case                |
| --------------- | --------------- | ------------------------------------------------------------------- | ------------------------------ |
| _**any value**_ | valid           | setBarCode("9999999999999") -> getBarCode().equals("9999999999999") | TestEZTicketEntry.setBarCode() |

### **Class EZTicketEntry - method getAmount**

**Criteria for method getAmount:**

- amount

**Predicates for method getAmount:**

| Criteria     | Predicate              |
| ------------ | ---------------------- |
| _**amount**_ | _**arbitrary values**_ |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|          |                 |

_**No boundaries**_

**Combination of predicates**:

| amount          | Valid / Invalid | Description of the test case       | JUnit test case               |
| --------------- | --------------- | ---------------------------------- | ----------------------------- |
| _**any value**_ | valid           | setAmount(10) -> getAmount() == 10 | TestEZTicketEntry.setAmount() |

### **Class EZTicketEntry - method setProductDescription**

**Criteria for method setProductDescription:**

- productDescription

**Predicates for method setProductDescription:**

| Criteria                 | Predicate              |
| ------------------------ | ---------------------- |
| _**productDescription**_ | _**arbitrary values**_ |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|          |                 |

_**No boundaries**_

**Combination of predicates**:

| productDescription | Valid / Invalid | Description of the test case                                                         | JUnit test case                           |
| ------------------ | --------------- | ------------------------------------------------------------------------------------ | ----------------------------------------- |
| _**any value**_    | valid           | setProductDescription("Test descrizione") -> getBarCode().equals("Test descrizione") | TestEZTicketEntry.setProductDescription() |

### **Class EZTicketEntry - method setPricePerUnit**

**Criteria for method setPricePerUnit:**

- pricePerUnit

**Predicates for method setPricePerUnit:**

| Criteria           | Predicate              |
| ------------------ | ---------------------- |
| _**pricePerUnit**_ | _**arbitrary values**_ |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |

_**No boundaries**_

**Combination of predicates**:

| | pricePerUnit | -inf, 0, +inf |
| Valid / Invalid | Description of the test case | JUnit test case |
| ---------- | --------------- | ---------------------------- | --------------- |
| _**any value**_ | valid | setPricePerUnit(10.25) -> getPricePerUnit() == 10.25|TestEZTicketEntry.setPricePerUnit()|

### **Class EZTicketEntry - method setDiscountRate**

**Criteria for method setDiscountRate:**

- discountRate

**Predicates for method setDiscountRate:**

| Criteria           | Predicate              |
| ------------------ | ---------------------- |
| _**pricePerUnit**_ | _**arbitrary values**_ |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |

_**No boundaries**_

**Combination of predicates**:

| | discountRate | -inf, 0, +inf |
| Valid / Invalid | Description of the test case | JUnit test case |
| ---------- | --------------- | ---------------------------- | --------------- |
| _**any value**_ | valid | setDiscountRate(10.25) -> getDiscountRate() == 10.25|TestEZTicketEntry.setDiscountRate()|

# White Box Unit Tests

### Test cases definition

    <JUnit test classes must be in src/test/java/it/polito/ezshop>
    <Report here all the created JUnit test cases, and the units/classes under test >
    <For traceability write the class and method name that contains the test case>

| Unit name | JUnit test case |
| --------- | --------------- | --- |
|           |                 |
|           |                 |
|           |                 |     |

### Code coverage report

    <Add here the screenshot report of the statement and branch coverage obtained using
    the Eclemma tool. >

### Loop coverage analysis

    <Identify significant loops in the units and reports the test cases
    developed to cover zero, one or multiple iterations >

| Unit name | Loop rows | Number of iterations | JUnit test case |
| --------- | --------- | -------------------- | --------------- | --- |
|           |           |                      |                 |
|           |           |                      |                 |
|           |           |                      |                 |     |
