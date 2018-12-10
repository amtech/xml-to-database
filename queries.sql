select count(*) from mysql.user;
select count(*) from items where location = "New York";
select count(*) from (select * from categories group by item_id having count(*) = 4 ) C;

select count(*) from sellers where rating > 1000;
select count(*) from sellers inner join bidders on sellers.user_id = bidders.user_id;
select count(*) from (select count(*) from categories inner join bids on categories.item_id = bids.item_id where bids.amount > 100 group by categories.category having count(*) >= 1) C;