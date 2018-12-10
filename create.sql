use ad;

CREATE TABLE sellers (
user_id VARCHAR(100) NOT NULL,
rating INT NOT NULL,
PRIMARY KEY (user_id)
);

CREATE TABLE items (
item_id INT NOT NULL,
name VARCHAR(100) NOT NULL,
currently DECIMAL(8,2) NOT NULL,
first_bid DECIMAL(8,2) NOT NULL,
number_of_bids INT(5) NOT NULL,
location VARCHAR(100) NOT NULL,
country VARCHAR(100) NOT NULL, 
started TIMESTAMP NOT NULL,
ends TIMESTAMP NOT NULL, 
description VARCHAR(4000) NOT NULL,
seller_user_id VARCHAR(100) NOT NULL,
PRIMARY KEY (item_id),
FOREIGN KEY (seller_user_id) REFERENCES sellers(user_id)
);


CREATE TABLE categories (
id INT NOT NULL,
item_id INT NOT NULL,
category VARCHAR(100) NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (item_id) REFERENCES items(item_id)
);

CREATE TABLE locations (
item_id INT NOT NULL,
latitude DECIMAL(10,8) NOT NULL,
longitude DECIMAL(11,8) NOT NULL,
PRIMARY KEY (item_id),
FOREIGN KEY (item_id) REFERENCES items(item_id)
);

CREATE TABLE buy_prices (
item_id INT NOT NULL,
buy_price DECIMAL(8,2) NOT NULL,
PRIMARY KEY (item_id),
FOREIGN KEY (item_id) REFERENCES items(item_id)
);

CREATE TABLE bidders (
user_id VARCHAR(100) NOT NULL,
rating INT(11) NOT NULL,
location VARCHAR(100) NOT NULL,
country VARCHAR(100) NOT NULL,
PRIMARY KEY (user_id)
);
	
CREATE TABLE bids (
item_id INT NOT NULL,
user_id VARCHAR(100) NOT NULL,
time TIMESTAMP NOT NULL,
amount DECIMAL(8,2) NOT NULL,
PRIMARY KEY (item_id,user_id,amount),
FOREIGN KEY (item_id) REFERENCES items(item_id),
FOREIGN KEY (user_id) REFERENCES bidders(user_id)
);