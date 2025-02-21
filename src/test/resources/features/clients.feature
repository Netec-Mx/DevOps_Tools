Feature: Client Management API

    Scenario: Get all clients
        When I request all clients
        Then I should receive a list of clients

    Scenario Outline: Get client by id
        When I request client with "<id>"
        Then I should receive the client details
        Then the client name should be "<name>"

        Examples:
            | id | name    |
            | 1  | Alberto |
            | 3  | Carlos  |
            | 24 | Ximena  |

    Scenario: Create new client
        Given I have a new client with name "Rocio"
        When I create the client
        Then the client should be saved successfully

    Scenario: Update existing client by id
        Given a client exists with id "1"
        When I update the client name to "Adrian"
        Then the client should be updated successfully

#    Scenario: Delete client by id
#        Given a client exists with id "17"
#        When I delete the client
#        Then the client should be removed successfully
