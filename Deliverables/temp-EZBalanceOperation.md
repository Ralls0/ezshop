## EZBalanceOperation

### setBalanceID(int balanceId)

**Criteria**
* None

**Predicates**
* None

**Boundaries**
* None

**Combination of Predicates**
* None


| None  | Valid / Invalid |            Description            | Example [ Id -> Id] | JUnit test case  |
| :---: | :-------------: | :-------------------------------: | :-----------------: | :--------------: |
|   *   |    *Valid**     | Sets balanceID equal to parameter |    T1(42) -> 42     | testSetBalanceId |

### setDate(LocalDate date)

**Criteria**
* None

**Predicates**
* None

**Boundaries**
* None

**Combination of Predicates**
* None


| None  | Valid / Invalid |         Description          | Example (Date -> Date) | JUnit test case |
| :---: | :-------------: | :--------------------------: | :--------------------: | :-------------: |
|   *   |    *Valid**     | Sets date equal to parameter |  T1(Date d) -> Date d  |   testSetDate   |


### setMoney(double money)

**Criteria**
* Sign of Money

**Predicates**
|   Crietrion   | Predicate |
| :-----------: | :-------: |
| Sign of Money | [-inf, 0) |
|               | [0, inf]  |


**Boundaries**
|   Crietrion   | Predicate |
| :-----------: | :-------: |
| Sign of Money | [-inf, 0) |
|               |   \[0\]   |
|               | (0, inf]  |

**Combination of Predicates**
* None


| Sign of Money | Valid / Invalid |                      Description                       |   Example [ Money -> (Money, Type)]    | JUnit test case |
| :-----------: | :-------------: | :----------------------------------------------------: | :------------------------------------: | :-------------: |
|   [-inf, 0)   |    *Valid**     |   Sets money equal to parameter and type to "CREDIT"   | T1b(200) -> (200, CREDIT) testSetMoney |
|     \[0\]     |    *Valid**     |   Sets money equal to parameter and type to "CREDIT"   |   T2b(0) -> (0, CREDIT) testSetMoney   |
|   (0, inf]    |    *Valid**     | Sets money equal to parameter * -1 and type to "DEBIT" | T3b(-200) -> (200, DEBIT) testSetMoney |


### setType(String type)


**Criteria**
* None

**Predicates**
* None

**Boundaries**
* None

**Combination of Predicates**
* None


| None  | Valid / Invalid |         Description         | Example (String -> String) | JUnit test case |
| :---: | :-------------: | :-------------------------: | :------------------------: | :-------------: |
|   *   |    *Valid**     | Sets typeequal to parameter |    T1("Test") -> "Test"    |   testSetType   |
