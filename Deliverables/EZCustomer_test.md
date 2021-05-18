## EZCustomer

### setCustomerName(String customerName)

**Criteria**

* None

**Predicates**

* None

**Boundaries**

* None

**Combination of Predicates**

* None

| None | Valid / Invalid |         Description          |     Example [ Name -> Name ]     |    JUnit test case     |
| :--: | :-------------: | :--------------------------: | :------------------------------: | :--------------------: |
|  *   |    **Valid**    | Sets name equal to parameter | T1("Mario Rossi") -> Mario Rossi | TestCustomerNameSetter |

### 

### setCustomerCard(String customerCard)

**Criteria**

* None

**Predicates**

* None

**Boundaries**

* None

**Combination of Predicates**

* None

| None | Valid / Invalid |              Description              |    Example [ Name -> Name ]    |    JUnit test case     |
| :--: | :-------------: | :-----------------------------------: | :----------------------------: | :--------------------: |
|  *   |    **Valid**    | Sets customer card equal to parameter | T2("1234567890") -> 1234567890 | TestCustomerCardSetter |

### setId(Integer id)

**Criteria**

- Sign of Id

**Predicates**

| Crietrion  | Predicate |
| :--------: | :-------: |
| Sign of Id | [-inf, 0] |
|            | (0, inf]  |



**Boundaries**

| Crietrion  |  Predicate   |
| :--------: | :----------: |
| Sign of Id | -inf, 0, inf |
|            |              |



**Combination of Predicates**

* None



| None | Valid / Invalid |                Description                | Example [ Id -> Id]  | JUnit test case |
| :--: | :-------------: | :---------------------------------------: | :------------------: | :-------------: |
|  *   |    **Valid**    | Sets CustomerID equal to parameter passed |     T3(42) -> 42     |    testSetId    |
|  *   |   **Invalid**   | Sets CustomerID equal to parameter passed |  T4(0) -> old_value  |    testSetId    |
|  *   |   **Invalid**   | Sets CustomerID equal to parameter passed | T5(-42) -> old_value |    testSetId    |



### setPoints(Integer points)

**Criteria**

* None

**Predicates**

* None

**Boundaries**

* None

**Combination of Predicates**

* None

| None | Valid / Invalid |              Description              | Example [ Name -> Name ] | JUnit test case |
| :--: | :-------------: | :-----------------------------------: | :----------------------: | :-------------: |
|  *   |    **Valid**    | Sets customer card equal to parameter |       T2(42) -> 42       |  TestSetPoints  |

### 