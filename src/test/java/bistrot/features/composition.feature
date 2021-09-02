Feature: Create, read, update and delete composition


  Scenario: Create composition
    # Create a todo
    Given url 'localhost:8080/api/composition'
    And request todoJson
    When method POST
    Then status 201



  Scenario: Get composition
    # Create a todo
    Given url resourceUrl
    And request todoJson
    When method GET
    Then status 200


  Scenario: update composition
    # Create a todo
    Given url resourceUrl
    And request todoJson
    When method PUT
    Then status 201


  Scenario: Delete composition
    # Create a todo
    Given url resourceUrl
    And request todoJson
    When method DELETE
    Then status 200
