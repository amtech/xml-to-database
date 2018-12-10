Search Technology for Media & Web -- WiSe 2018/19
Assignment 1
G M Nazmul Hossain
Md Shah Newaz Jubayed

Q: List your relations. Please specify all keys that hold on each relation. You need not specify attribute types at this stage.

A: Relations & Keys
items ( item_id, name, currently, first_bid, number_of_bids, location, country, started, ends, description, seller_user_id )
	Primary Key  item_id
	Foreign Key  seller_user_id, References sellers.user_id

categories ( id, item_id, category )
	Primary Key  id
	Foreign Key  item_id, References items.item_id


locations ( item_id, latitude, longitude )
	Primary Key  item_id
	Foreign Key  item_id, References items.item_id


buy_prices ( item_id, buy_price )
	Primary Key  item_id
	Foreign Key  item_id, References items.item_id


bids (  item_id, user_id, time, amount)
	Primary Key  item_id,user_id,amount
	Foreign Key  item_id, References items.item_id
	Foreign Key  user_id, References bidders.user_id


bidders ( user_id, rating, location, country)
	Primary Key   user_id


sellers ( user_id, rating)
	Primary Key   user_id


Q: List all completely nontrivial functional dependencies that hold on each relation, excluding those that effectively specify keys.
A: 
items:
	item_id -> name
	item_id -> currently
	item_id -> first_bid
	item_id -> number_of_bids
	item_id -> location
	item_id -> country
	item_id -> started
	item_id -> ends
	item_id -> seller_user_id
	item_id -> description
	
categories: 
	item_id -> category

bids:
	bidder_user_id, item_id, amount -> time

bidders:
	bidder_user_id -> rating
	bidder_user_id -> location
	bidder_user_id -> country

sellers:
	user_id -> rating;

locations:
	item_id -> longitude;
	item_id -> latitude;

buy_prices:
	item_id -> buy_prices;


Q: Are all of your relations in Boyce-Codd Normal Form (BCNF)? If not, either redesign them and start over, or, explain why you feel it is advantageous to use non-BCNF relations.
A: No, Because 
-- For BCNF X->Y, X should be the super key of the table, 
-- But our table is not in BCNF as neither item_id nor seller_user_id alone are keys


Q: Are all of your relations in Fourth Normal Form (4NF)? If not, either redesign them and start over, or, explain why you feel it is advantageous to use non-4NF relations.
A: Yes, Because
-- Our tables have multi-valued dependency
-- Like item_id -> latitude and item->category 
-- So this tables in 4NF.