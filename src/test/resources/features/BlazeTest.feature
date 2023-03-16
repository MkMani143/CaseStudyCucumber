Feature: Purchase Item

Scenario: Login in to App
Given User is on Home Page
When User enters login credentials
Then Should display Home Page

Scenario Outline: Add Items to Cart
When Add Items "<categories>" and "<product>" to the cart
Then Item must be added to the cart

Examples: 
|categories|product|
|Phones|Samsung galaxy s7|
|Phones|Iphone 6 32gb|

Scenario: Delete an Item in the Cart
When Delete an Item to cart
Then Item should be deleted in the cart

Scenario: Place Order
When Place Order
Then Items should be Purchased