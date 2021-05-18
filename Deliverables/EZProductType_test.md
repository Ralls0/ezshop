## EZProductType

### setQuantity(Integer quantity)

**Criteria**

* None

**Predicates**

* None

**Boundaries**

* None

**Combination of Predicates**

* None

| None | Valid / Invalid |        Description         | Example [ Quantity -> Quantity ] | JUnit test case |
| :--: | :-------------: | :------------------------: | :------------------------------: | :-------------: |
|  *   |    **Valid**    | Sets quantity to parameter |           T1(10) -> 10           |   setQuantity   |



### setLocation(String location)

**Criteria**

* None

**Predicates**

* None

**Boundaries**

* None

**Combination of Predicates**

* None

| None | Valid / Invalid |        Description         | Example [ Location -> Location ] | JUnit test case |
| :--: | :-------------: | :------------------------: | :------------------------------: | :-------------: |
|  *   |    **Valid**    | Sets location to parameter |       T1("1-a-1") -> 1-a-1       |   setLocation   |



### setNote(String note)

**Criteria**

* None

**Predicates**

* None

**Boundaries**

* None

**Combination of Predicates**

* None

| None | Valid / Invalid |      Description       | Example [ Note -> Note] | JUnit test case |
| :--: | :-------------: | :--------------------: | :---------------------: | :-------------: |
|  *   |    **Valid**    | Sets note to parameter | T1("Prova") -> "Prova"  |     setNote     |

### setProductDescription(String productDescription)

**Criteria**

* None

**Predicates**

* None

**Boundaries**

* None

**Combination of Predicates**

* None

| None | Valid / Invalid |          Description          |    Example [ Description -> Description ]    |    JUnit test case    |
| :--: | :-------------: | :---------------------------: | :------------------------------------------: | :-------------------: |
|  *   |    **Valid**    | Sets description to parameter | T1("Prova descrizione") -> Prova descrizione | setProductDescription |



### setBarCode(String barCode)

**Criteria**

* None

**Predicates**

* None

**Boundaries**

* None

**Combination of Predicates**

* None

| None | Valid / Invalid |        Description        |    Example [ barCode -> barCode ]    | JUnit test case |
| :--: | :-------------: | :-----------------------: | :----------------------------------: | :-------------: |
|  *   |    **Valid**    | Sets barCode to parameter | T1("9999999999999") -> 9999999999999 |   setBarCode    |



### setPricePerUnit(Double pricePerUnit)

**Criteria**

* None

**Predicates**

* None

**Boundaries**

* None

**Combination of Predicates**

* None

| None | Valid / Invalid |          Description           | Example [ PricePerUnit -> PricePerUnit ] | JUnit test case |
| :--: | :-------------: | :----------------------------: | :--------------------------------------: | :-------------: |
|  *   |    **Valid**    | Sets pricePerUnit to parameter |            T1(10.25) -> 10.25            | setPricePerUnit |

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



| None | Valid / Invalid |               Description                | Example [ Id -> Id]  | JUnit test case |
| :--: | :-------------: | :--------------------------------------: | :------------------: | :-------------: |
|  *   |    **Valid**    | Sets productID equal to parameter passed |     T7(22) -> 22     |      SetId      |
|  *   |   **Invalid**   | Sets productID equal to parameter passed |  T8(0) -> old_value  |      SetId      |
|  *   |   **Invalid**   | Sets productID equal to parameter passed | T9(-22) -> old_value |      SetId      |

