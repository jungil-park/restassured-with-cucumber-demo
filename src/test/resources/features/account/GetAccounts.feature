Feature: Test Panopticon API
  Scenario: NX calls web service to add new account
	Given new client does not exist with {"status":"inactive","accountId":"test002","accountType":"vostro"}
	When client is added
	Then the status code is 200
     And response body should be status ok
 	| status  | ok	|
 