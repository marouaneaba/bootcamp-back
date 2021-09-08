Feature: Create, read, update and delete composition item

  Background:
    Given url apiBaseUrl
    Given path '/v1/'
    * def testScope =  ["TEST"]
    * def responseAuthenticationTestScope = call read('authentication.feature') karate.mapWithKey(testScope, 'scopeSelected')

  Scenario: Create composition item
    Given path 'composition-items'
    And header content = 'application/json'
    And header Authorization = 'Bearer ' + responseAuthenticationTestScope[0].response.value
    And request read('data/composition-item.json')
    When method POST
    Then status 201

  Scenario: Create composition item with empty name
    Given path 'composition-items'
    And header content = 'application/json'
    And header Authorization = 'Bearer ' + responseAuthenticationTestScope[0].response.value
    And request read('data/composition-item-with-name-empty.json')
    When method POST
    Then status 400

  Scenario: Get all composition item
    Given path 'composition-items'
    And header Accept = 'application/json'
    And header Authorization = 'Bearer ' + responseAuthenticationTestScope[0].response.value
    When method GET
    Then status 200
    And match $ == '##[_ > 0]'

  Scenario: Get composition item by name
    Given path 'composition-items'
    And header Accept = 'application/json'
    And header Authorization = 'Bearer ' + responseAuthenticationTestScope[0].response.value
    And form field name = 'espresso'
    When method GET
    Then status 200
    And match $ == '##[_ > 0]'

  Scenario: Get composition item by id
    Given path 'composition-items/99999'
    And header Accept = 'application/json'
    And header Authorization = 'Bearer ' + responseAuthenticationTestScope[0].response.value
    When method GET
    Then status 404

  Scenario: update composition item
    Given path 'composition-items'
    And header Accept = 'application/json'
    And header Authorization = 'Bearer ' + responseAuthenticationTestScope[0].response.value
    When method GET
    Then status 200
    And karate.log('Response first element id:', response[0].id);

    * def firstId = response[0].id

    Given path 'v1/composition-items/' + firstId
    And header Accept = 'application/json'
    And header Authorization = 'Bearer ' + responseAuthenticationTestScope[0].response.value
    And request read('data/composition-item.json')
    When method PUT
    Then status 204

    Given path 'v1/composition-items/9999'
    And header Accept = 'application/json'
    And header Authorization = 'Bearer ' + responseAuthenticationTestScope[0].response.value
    And request read('data/composition-item.json')
    When method PUT
    Then status 404

  Scenario: Delete composition item and delete composition does not exist
    Given path 'composition-items'
    And header Accept = 'application/json'
    And header Authorization = 'Bearer ' + responseAuthenticationTestScope[0].response.value
    When method GET
    Then status 200
    And karate.log('First composition item, id :', response[0].id);

    * def firstId = response[0].id

    Given path 'v1/composition-items/9999'
    And header Authorization = 'Bearer ' + responseAuthenticationTestScope[0].response.value
    When method DELETE
    Then status 404

  Scenario: Delete all composition item
    Given path 'composition-items'
    And header Accept = 'application/json'
    And header Authorization = 'Bearer ' + responseAuthenticationTestScope[0].response.value
    When method DELETE
    Then status 204
