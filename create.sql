use ad;

CREATE TABLE items (
item_id INT(11) NOT NULL,
name VARCHAR(255) NOT NULL,
currently DECIMAL(8,2) NOT NULL,
first_bid DECIMAL(8,2) NOT NULL,
number_of_bids INT(5) NOT NULL,
location VARCHAR(255) NOT NULL,
country VARCHAR(255) NOT NULL, 
started TIMESTAMP NOT NULL,
ends TIMESTAMP NOT NULL, 
description VARCHAR(4000) NOT NULL,
seller_user_id INT(11) NOT NULL,
PRIMARY KEY (item_id)
);


CREATE TABLE categories (
item_id INT(11) NOT NULL,
category VARCHAR(255) NOT NULL,
PRIMARY KEY (item_id, category)
);

CREATE TABLE locations (
item_id INT(11) NOT NULL,
latitude DECIMAL(10,8) NOT NULL,
longitude DECIMAL(11,8) NOT NULL,
PRIMARY KEY (item_id)
);

CREATE TABLE buy_prices (
item_id INT(11) NOT NULL,
buy_price DECIMAL(8,2) NOT NULL,
PRIMARY KEY (item_id)
);
	
CREATE TABLE bids (
item_id INT(11) NOT NULL,
user_id VARCHAR(255) NOT NULL,
bid_t TIMESTAMP NOT NULL,
amount DECIMAL(8,2) NOT NULL,
PRIMARY KEY (item_id, user_id)
);

CREATE TABLE bidders (
user_id VARCHAR(255) NOT NULL,
rating INT(11) NOT NULL,
location VARCHAR(255) NOT NULL,
country VARCHAR(255) NOT NULL,
PRIMARY KEY (user_id)
);

CREATE TABLE sellers (
user_id VARCHAR(255) NOT NULL,
rating INT(11) NOT NULL,
PRIMARY KEY (user_id)
);