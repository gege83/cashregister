Checkout Kata
=============

Implement the code for a supermarket checkout that calculates the total price of a number of items.

In a normal supermarket, items are identified by ‘stock keeping units’ or ‘SKUs’. In our store, we will use individual letters of the alphabet, A, B, C etc, as the SKUs. Our goods are priced individually. In addition, some items are multi-priced: buy n of them and which will cost you y. For example, item A might cost 50 pence individually but this week we have a special offer where you can buy 3 As for £1.30.

#### This week’s prices are the following:

|Item |Unit Price|Special Price|
|-----|----------|-------------|
|A|50|3 for 130|
|B|30|2 for 45|
|C| 20||
|D| 15||

Our checkout accepts items in any order so if we scan a B, then an A, then another B, we will recognise the two B’s and price them at 45 (for a total price so far of 95).

Extra points: Because the pricing changes frequently we will need to be able to pass in a set of pricing rules each time we start handling a checkout transaction.

Assumptions
-----------

- as this is a kata i will implement only the business logic
- all data is valid and preprocessed - no null values
- during the recording process i item can occur multiple times with quantities
- quantity can be a float number
- multiple offers will be allowed in the same time

### input objects/interfaces
Sku:
- id - string
- unit price - double

Item:
- sku - Sku
- quantity - double

Offer << interface >>:
- isApplicable(itemSummary) - boolean
- calculateDiscount(itemSummary) - discount

PackagePriceOffer
- sku - Sku
- quantity - double
- price - double

### output objects/interfaces
BlockItem << interface >>:
- getPrice() - double

PricedItemBlockItem - price for individual items
- item - Item
- price - double

SubTotalBlockItem - holds the total value
- total - double - rounded to 3 decimal items

DiscountBlockItem - holds the discounted item count and the discount 
- sku - Sku
- discounted sku quantity - double
- total discount - double (should be negative)

DiscountTotalBlockItem - summary for the discounts
- total - double

TotalBlockItem - holds the final price
- total

Checkout service
----------------

The service get list of items (Sku with quantity) and a list of offer. 
The output will be the elements of the receipt.
The elements will be presented to the user by the view or sent to the client as json.