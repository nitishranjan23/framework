#Author: nitishranjan23@gmail.com

Feature: Scenario 2

Scenario: NYSE
Given NYSE page is loaded
Then search for "EPAM"
Then capture share prices between "09/01/21" and "10/05/21"
Then post the JSON file
