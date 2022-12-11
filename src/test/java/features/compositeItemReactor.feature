Feature: Create, read, update and delete composition item

  Background:
    Given url apiBaseUrl
    Given path '/v2/'
    * def adminScope =  ["ADMIN"]
    * def profileScope = ["PROFILE"]
    * def defaultScope = ["One","Two"]


    * def responseAuthenticationAdminScope = call read('authentication.feature') karate.mapWithKey(adminScope, 'scopeSelected')
    * def responseAuthenticationProfileScope = call read('authentication.feature') karate.mapWithKey(profileScope, 'scopeSelected')
    * def responseAuthenticationDefaultScope = call read('authentication.feature') karate.mapWithKey(defaultScope, 'scopeSelected')


  Scenario: Create composition item

    Given path 'composition-items'
    And header content = 'application/json'
    And header Authorization = 'Bearer ' + responseAuthenticationProfileScope[0].response.value
    And request read('data/composition-item.json')
    When method POST
    Then status 201

    Given path 'v2/composition-items'
    And header content = 'application/json'
    And header Authorization = 'Bearer ' + responseAuthenticationAdminScope[0].response.value
    And request read('data/composition-item.json')
    When method POST
    Then status 403

  Scenario: Create composition item with empty name
    Given path 'composition-items'
    And header content = 'application/json'
    And header Authorization = 'Bearer ' + responseAuthenticationProfileScope[0].response.value
    And request read('data/composition-item-with-name-empty.json')
    When method POST
    Then status 400

    Given path 'v2/composition-items'
    And header content = 'application/json'
    And header Authorization = 'Bearer ' + responseAuthenticationAdminScope[0].response.value
    And request read('data/composition-item-with-name-empty.json')
    When method POST
    Then status 400

  Scenario: Get all composition item
    Given path 'composition-items'
    And header Accept = 'application/json'
    And header Authorization = 'Bearer ' + responseAuthenticationAdminScope[0].response.value
    When method GET
    Then status 200
    And match $ == '##[_ > 0]'

    Given path 'v2/composition-items'
    And header Accept = 'application/json'
    And header Authorization = 'Bearer ' + responseAuthenticationProfileScope[0].response.value
    When method GET
    Then status 403

  Scenario: Get composition item by name
    Given path 'composition-items'
    And header Accept = 'application/json'
    And form field name = 'espresso'
    And header Authorization = 'Bearer ' + responseAuthenticationAdminScope[0].response.value
    When method GET
    Then status 200
    And match $ == '##[_ > 0]'

    Given path 'v2/composition-items'
    And header Accept = 'application/json'
    And form field name = 'espresso'
    And header Authorization = 'Bearer ' + responseAuthenticationProfileScope[0].response.value
    When method GET
    Then status 403

  Scenario: Get composition item by id
    Given path 'composition-items/99999'
    And header Accept = 'application/json'
    And header Authorization = 'Bearer ' + responseAuthenticationProfileScope[0].response.value
    When method GET
    Then status 404

    Given path 'v2/composition-items/99999'
    And header Accept = 'application/json'
    And header Authorization = 'Bearer ' + responseAuthenticationAdminScope[0].response.value
    When method GET
    Then status 403

  Scenario: update composition item
    Given path 'composition-items'
    And header Accept = 'application/json'
    And header Authorization = 'Bearer ' + responseAuthenticationAdminScope[0].response.value
    When method GET
    Then status 200
    And karate.log('Selected composition item :', response[0].id);

    * def firstId = response[0].id

    Given path 'v1/composition-items/' + firstId
    And header Accept = 'application/json'
    And header Authorization = 'Bearer ' + responseAuthenticationAdminScope[0].response.value
    And request read('data/composition-item.json')
    When method PUT
    Then status 204

    Given path 'composition-items/9999'
    And header Accept = 'application/json'
    And header Authorization = 'Bearer ' + responseAuthenticationAdminScope[0].response.value
    And request read('data/composition-item.json')
    When method PUT
    Then status 404

  Scenario: Delete composition item and delete composition does not exist
    Given path 'composition-items'
    And header Accept = 'application/json'
    And header Authorization = 'Bearer ' + responseAuthenticationAdminScope[0].response.value
    When method GET
    Then status 200
    And karate.log('Selected composition item :', response[0].id);

    * def firstId = response[0].id

    Given path 'v2/composition-items/9999'
    And header Authorization = 'Bearer ' + responseAuthenticationProfileScope[0].response.value
    When method DELETE
    Then status 403

  Scenario: Delete all composition item
    Given path 'composition-items'
    And header Accept = 'application/json'
    And header Authorization = 'Bearer ' + responseAuthenticationAdminScope[0].response.value
    When method DELETE
    Then status 204

    Given path 'v2/composition-items'
    And header Accept = 'application/json'
    And header Authorization = 'Bearer ' + responseAuthenticationProfileScope[0].response.value
    When method DELETE
    Then status 403
