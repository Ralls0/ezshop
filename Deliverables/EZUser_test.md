## EZUser

## setId(int balanceId)

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



| None | Valid / Invalid |              Description              | Example [ Id -> Id] | JUnit test case |
| :--: | :-------------: | :-----------------------------------: | :-----------------: | :-------------: |
|  *   |    **Valid**    | Sets userID equal to parameter passed |     T1(1) -> 1      |      setId      |
|  *   |     Invalid     | Sets userID equal to parameter passed | T2(0) -> old_value  |      setId      |
|  *   |     Invalid     | Sets userID equal to parameter passed | T3(-1) -> old_value |      setId      |

## setUsername(String username)

**Criteria**

* None

**Predicates**

* None

**Boundaries**

* None

**Combination of Predicates**

* None

| None | Valid / Invalid |           Description            | Example [ Username -> Username ] | JUnit test case |
| :--: | :-------------: | :------------------------------: | :------------------------------: | :-------------: |
|  *   |    **Valid**    | Sets username equal to parameter |    T4("Giovanni") -> Giovanni    |   setUsername   |

### 

## setPassword(String password) 

**Criteria**

* None

**Predicates**

* None

**Boundaries**

* None

**Combination of Predicates**

* None

| None | Valid / Invalid |           Description            | Example [ Password -> Password ] | JUnit test case |
| :--: | :-------------: | :------------------------------: | :------------------------------: | :-------------: |
|  *   |    **Valid**    | Sets password equal to parameter |       T5("12345") -> 12345       |   setPassword   |

### 

## setRole(Stirng role)

**Criteria**

* None

**Predicates**

* None

**Boundaries**

* None

**Combination of Predicates**

* None

| None | Valid / Invalid |         Description          | Example [ Role -> Role]  | JUnit test case |
| :--: | :-------------: | :--------------------------: | :----------------------: | :-------------: |
|  *   |    **Valid**    | Sets role equal to parameter | T6("Cashier") -> Cashier |     setRole     |

