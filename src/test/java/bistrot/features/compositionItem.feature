Feature: Create, read, update and delete composition item


  Scenario: Create composition item
    Given url 'localhost:8080/api/composition-items'
    And request {name: '', price: '', recipe: '', capacity: ''}
    When method POST
    Then status 201

    Given url 'localhost:8080/api/composition-items'
    And request {name: '', price: '', recipe: '', capacity: ''}
    When method POST
    Then status 400
    # verifier response message.


  Scenario: Get composition item
    Given url 'localhost:8080/api/composition-items'
    When method GET
    Then status 200


  Scenario: update composition item
    # Modification complete de la donnée.
    Given url 'localhost:8080/api/composition-items/1'
    And request todoJson
    When method PUT
    Then status 204

    Given url 'localhost:8080/api/composition-items/999'
    And request todoJson
    When method PUT
    Then status 404


  Scenario: Delete composition item
    Given url 'localhost:8080/api/composition-items/1'
    When method DELETE
    Then status 204
