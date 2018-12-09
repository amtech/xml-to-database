



select count(*) from sellers where rating > 1000;
select count(*) from sellers inner join bidders on sellers.user_id = bidders.user_id;
select count(*) from items inner join categories on items.item_id = categories.item_id where items.number_of_bids >= 1 and currently > 100;