Feature: Create, read, update and delete menu


  Scenario: Create menu
    # Create a todo
    Given url resourceUrl
    And header Authorization = 'Bearer ' + accessToken
    And request todoJson
    When method POST
    Then status 201



  Scenario: Get menu
    # Create a todo
    Given url resourceUrl
    And header Authorization = 'Bearer ' + accessToken
    And request todoJson
    When method GET
    Then status 201


  Scenario: update menu
    # Create a todo
    Given url resourceUrl
    And header Authorization = 'Bearer ' + accessToken
    And request todoJson
    When method PATH
    Then status 201


  Scenario: Delete menu
    # Create a todo
    Given url resourceUrl
    And header Authorization = 'Bearer ' + accessToken
    And request todoJson
    When method DELETE
    Then status 201
