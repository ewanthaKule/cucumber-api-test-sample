Feature: Dummy API test Feature

  Scenario: Verify GET posts scenario - get post by number
    Given I call the posts context to get post "1"
    Then the response code is 200
    And the response should return user id "1"
    
  Scenario: Verify GET posts scenario - get all posts
    Given I call the posts context to get all posts
    Then the response code is 200
    And the response should return 100 posts

  Scenario: Verify POST posts scenario
    Given I call the posts context to POST "insert_one_post.json" to posts list
    Then the response code is 201
    And the response body should match with "insert_one_post.json" file
  
  @wiremockApi
  Scenario: Verify wiremock GET API call for testGet context
    Given I call the wiremock GET for "/testGet" context
    Then the response code is 200
    And the response body should match with "wiremock_success.json" file
  
  @wiremockApi
  Scenario: Verify wiremock GET API call for testGetLimitOffset context
    Given I call the wiremock GET for "/testGetLimitOffset" context with "Bearer thisIsBearer" token
    Then the response code is 200
    And the response body should match with "wiremock_success_limit_offset.json" file

  @wiremockApi
  Scenario: Verify wiremock POST API call for testPost context
    Given I call the wiremock POST for "/testPost" context with body "test_post_body.json" with "Bearer thisIsBearer" token
    Then the response code is 200
    And the response body should match with "wiremock_success_post.json" file