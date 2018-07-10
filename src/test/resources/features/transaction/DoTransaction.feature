Feature: Test Panopticon API
  Scenario: NX calls web service to add new transaction
	Given do transaction with {"creditAccountId":"acct9","debitAccountId":"DepositSuspense","instrumentId":"EUR","amount":"1","instructionReference":"bdd_test1","instructingParty":"nxchange","instructionLocation":"nxchange"}
	When transaction is added
	Then the status code is 200
#	And response includes the following
#	| totalItems 	 		| 1 					|
     And response body should be status ok
 	| status 									| ok	|
#	| items.volumeInfo.publisher 				| Simon and Schuster		|   
#	| items.volumeInfo.pageCount 				| 630						|

 