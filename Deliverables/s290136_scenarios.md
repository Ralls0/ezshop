### Use case 1, UC1

| Actors Involved  |                     Worker                     |
| ---------------- | :--------------------------------------------: |
| Precondition     |             A worker is logged out             |
| Post condition   |             A worker is logged in              |
| Nominal Scenario |                     Login                      |
| Variants         | Worker doesn't exist, user is not authenticate |

| Scenario 1.1   |                                                 |
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

