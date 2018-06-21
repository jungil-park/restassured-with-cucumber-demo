Feature: Test Panopticon API
  Scenario: A client calls web service to get Balance
	Given a client exists with id of 400
	When a client retrieves the balance by id
	Then the status code is 200
#	And response includes the following
#	| totalItems 	 		| 1 					|
    And response includes the following in any order
	| amount 									| 3198.990000000000000000	|
#	| items.volumeInfo.publisher 				| Simon and Schuster		|   
#	| items.volumeInfo.pageCount 				| 630						|

