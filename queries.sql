-- Search Technology for Media & Web -- WiSe 2018/19
-- Assignment 1
-- G M Nazmul Hossain and 
-- MD Shah Newaz Jubayed

USE ad;

-- Find the number of users in the database.
SELECT count(*) FROM 
	(SELECT user_id FROM sellers 
		UNION SELECT user_id FROM bidders
	) T;


-- Find the number of items in "New York", i.e., itmes whose location is exactly the string "New York". Pay special attention to case sensitivity. E.g., you should not match items in "new york".
SELECT count(item_id) FROM items WHERE Location = "New York" AND BINARY(Location) = BINARY("New York");


-- Find the number of auctions belonging to exactly four categories. Be careful to remove duplicates, if you store them.
SELECT count(*) FROM 
	(SELECT item_id FROM categories 
		GROUP BY item_id 
		HAVING count(category) = 4
	) T;


-- Find the ID(s) of current (unsold) auction(s) with the highest bid. 
-- Remember that the data was captured at December 20th, 2001, one second after midnight. 
-- Pay special attention to the current auctions without any bid.
SET @v1 := (SELECT MAX(currently) FROM items WHERE number_of_bids > 0);
SELECT @v1;
SELECT item_id
	FROM items WHERE currently = @v1 AND number_of_bids > 0;


-- Find the number of sellers whose rating is higher than 1000.
SELECT count(*) FROM sellers WHERE rating > 1000;


-- Find the number of users who are both sellers and bidders.
SELECT count(*) FROM sellers INNER JOIN bidders ON sellers.user_id = bidders.user_id;


-- Find the number of categories that include at least one item with a bid of more than $100.
SELECT count(*) FROM 
	(SELECT count(*) FROM categories INNER JOIN bids 
		ON categories.item_id = bids.item_id 
		WHERE bids.amount > 100 
		GROUP BY categories.category 
		HAVING count(*) >= 1
	) T;