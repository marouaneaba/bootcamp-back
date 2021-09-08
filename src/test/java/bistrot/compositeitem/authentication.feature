Feature:
  Authentication feature

  Scenario: Client authentication
    # Authentication
    Given url tokenUrl
    And request { claims: { location: 'FR'}, scopes: ['#(scopeSelected)']}
    When method POST
    Then status 200
    And def accessToken = response.value