LOAD DATA LOCAL INFILE '/home/ad/AD_Assignment_1/itemFile.csv' INTO TABLE items FIELDS TERMINATED BY "�" LINES TERMINATED BY '\n';
LOAD DATA LOCAL INFILE '/home/ad/AD_Assignment_1/categoryItemFile.csv' INTO TABLE categories FIELDS TERMINATED BY "�" LINES TERMINATED BY '\n';
LOAD DATA LOCAL INFILE '/home/ad/AD_Assignment_1/itemLocationFile.csv' INTO TABLE locations FIELDS TERMINATED BY "�" LINES TERMINATED BY '\n';
LOAD DATA LOCAL INFILE '/home/ad/AD_Assignment_1/buyPriceFile.csv' INTO TABLE buy_prices FIELDS TERMINATED BY "�" LINES TERMINATED BY '\n';
LOAD DATA LOCAL INFILE '/home/ad/AD_Assignment_1/bidFile.csv' INTO TABLE bid FIELDS TERMINATED BY "�" LINES TERMINATED BY '\n';
LOAD DATA LOCAL INFILE '/home/ad/AD_Assignment_1/bidderFile.csv' INTO TABLE bidder FIELDS TERMINATED BY "�" LINES TERMINATED BY '\n';
LOAD DATA LOCAL INFILE '/home/ad/AD_Assignment_1/sellerFile.csv' INTO TABLE seller FIELDS TERMINATED BY "�" LINES TERMINATED BY '\n'; 