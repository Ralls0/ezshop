# Project Estimation 
- [Project Estimation](#project-estimation)
- [Estimate by Product Decomposition](#estimate-by-product-decomposition)
- [Estimate by activity decomposition](#estimate-by-activity-decomposition)
- [Gantt Diagram](#gantt-diagram)


Authors: 
* S281564 Marco Manco
* S290136 Giovanni Pollo
* S292477 Matteo Quarta
* S292602 Davide Fersino
  
Date: 30/04/2021

Version: 1.0

# Estimate by Product Decomposition

|                         | Estimate |    Unit     |
| :---------------------- | :------: | :---------: |
| Number of Classes       |    15    |  #Classes   |
| Avereage Class Size     |   450    |     LOC     |
| Esitimated Project Size |   6800   |     LOC     |
| Estimated Effort        |   680    | PersonHours |
| Estimated Cost          |  20'500  |      €      |
| Estimated Time          |   4-5    |    Weeks    |

# Estimate by activity decomposition
| Activity name                            | Estimated effort (person hours) |
| ---------------------------------------- | ------------------------------- |
| 1 Requirements planning                  |                                 |
| 1.1 Perform work analysis                | 6                               |
| 1.2 Model process                        | 3                               |
| 1.3 Work estimation                      | 4                               |
| 1.4 Identify stakeholders                | 4                               |
| 1.5 Identify context and interfaces      | 4                               |
| 1.6 Analize stories and personas         | 2                               |
| 1.7 Identify user requirements           | 10                              |
| 1.8 Identify non functional requirements | 3                               |
| 1.9 Identify use cases                   | 7                               |
| 1.10 Define important terms              | 6                               |
| 1.11 Define deployment                   | 2                               |
| 1.12 Create requirements document        | 50                              |
| 1.13 Create a GUI prototype              | 30                              |
| 2 Design                                 |                                 |
| 2.1 Define high level design             | 4                               |
| 2.2 Define low level design              | 40                              |
| 2.3 Verification traceability matrix     | 3                               |
| 2.4 Verification sequence diagrams       | 5                               |
| 3 Coding                                 |                                 |
| 3.1 Identify algorithm for methods class | 10                              |
| 3.2 Write the code                       | 250                             |
| 4 Testing                                |                                 |
| 4.1 Identify test cases                  | 10                              |
| 4.1 Write test code                      | 50                              |
| 4.2 Analize test result                  | 10                              |
| 5 Integration test and GUI               |                                 |
| 5.1 Integration GUI with code            | 50                              |
| 5.2 Identify GUI test cases              | 10                              |
| 5.3 Write GUI test code                  | 50                              |
| 5.4 Analize GUI test result              | 10                              |
| 6 Revisioning                            |                                 |
| 6.1 Final revisioning                    | 100                             |

# Gantt Diagram

```plantuml
@startgantt
Project starts the 3th of april 2021

saturday are closed
sunday are closed

2021-04-03 to 2021-04-12 are named [Requirements planning]
2021-04-03 to 2021-04-12 are colored in salmon 

2021-04-12 to 2021-04-15 are named [Design]
2021-04-12 to 2021-04-15 are colored in Lavender

2021-04-15 to 2021-04-27 are named [Coding]
2021-04-15 to 2021-04-27 are colored in #AAF

2021-04-27 to 2021-04-30 are named [Testing]
2021-04-27 to 2021-04-30 are colored in Yellow

2021-04-30 to 2021-05-07 are named [Integration test + GUI]
2021-04-30 to 2021-05-07 are colored in #ADD

2021-05-07 to 2021-05-14 are named [Revisioning]
2021-05-07 to 2021-05-14 are colored in Pink


[Perform work analysis] starts 2021-04-06 and lasts 1 days
[Model process] starts 2021-04-06 and lasts 1 days
[Work estimation] starts 2021-04-06 and lasts 1 days 
[Create requirements document] starts 2021-04-06 and lasts 3 days
[Identify stakeholders] starts 2021-04-06 and lasts 1 days
[Identify context and interfaces] starts 2021-04-06 and lasts 1 days
[Analize stories and personas] starts 2021-04-06 and lasts 1 days
[Identify user requirements] starts 2021-04-06 and lasts 2 days
[Identify non functional requirements] lasts 1 days
[Define deployment] lasts 1 days
[Define important terms] lasts 1 days
[Identify use cases] lasts 1 days
[Create a GUI prototype] lasts 1 days
[Define high level design]  lasts 1 days
[Define low level design] lasts 2 days
[Verification traceability matrix] lasts 1 days
[Verification sequence diagrams] lasts 1 days
[Identify algorithm for methods class] lasts 1 days
[Write the code] lasts 8 days
[Identify test cases] lasts 1 days
[Write test code] lasts 2 days
[Analize test and solve bug] lasts 1 days

[Integration GUI with code] lasts 2 days
[Identify GUI test cases]  lasts 1 days
[Write GUI test code]  lasts 2 days
[Analize GUI test result and solve bug]  lasts 1 days

[Final revisioning] lasts 4 days


[Identify non functional requirements] starts at [Identify user requirements]'s end
[Identify use cases] starts at [Identify user requirements]'s end
[Define important terms] starts at [Identify user requirements]'s end
[Define deployment] starts at [Identify user requirements]'s end
[Create a GUI prototype] starts at [Identify use cases]'s end

[Define high level design] starts at [Create a GUI prototype]'s end
[Define low level design] starts at [Create a GUI prototype]'s end
[Verification traceability matrix] starts at [Define low level design]'s end
[Verification sequence diagrams] starts at [Define low level design]'s end
[Identify algorithm for methods class] starts at [Verification sequence diagrams]'s end
[Write the code] starts at [Verification sequence diagrams]'s end
[Identify test cases] starts at [Write the code]'s end
[Write test code] starts at [Write the code]'s end
[Analize test and solve bug] starts at [Write test code]'s end

[Integration GUI with code] starts at [Analize test and solve bug]'s end
[Identify GUI test cases] starts at [Integration GUI with code]'s end
[Write GUI test code] starts at [Integration GUI with code]'s end
[Analize GUI test result and solve bug] starts at [Write GUI test code]'s end
[Final revisioning] starts at [Analize GUI test result and solve bug]'s end
@endgantt
```