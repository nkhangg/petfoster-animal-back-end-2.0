use petfoster

--tong doanh thu theo loai
insert into orders values (getdate(), null, 490000, 10)

insert into order_detail values (2, 200, 140000, 133, 'PD0033')
insert into order_detail values (1, 1000, 170000, 133, 'PD0013')
insert into order_detail values (1, 1000, 250000, 133, 'PD0016')

select * from order_detail
select * from product_repo

create procedure GetRevenueByProductType
as
begin
	select product_type_name, sum(a.total) as 'total'
	from order_detail a
	inner join product b on b.product_id = a.product_id
	inner join product_type c on c.product_type_id = b.[type_id]
	inner join orders o on o.id = a.order_id
	where year(o.create_at) = 2023
	group by product_type_name
end
--chay store nay
-------------------------------------------------------------
CREATE PROCEDURE GetProductTypeRevenueByYear(@year int)
AS
BEGIN
SELECT COALESCE(SUM(od.total), 0)
FROM product_type pt
LEFT JOIN product p ON pt.product_type_id = p.[type_id]
LEFT JOIN order_detail od ON p.product_id = od.product_id
LEFT JOIN (
    SELECT o.id, YEAR(o.create_at) AS order_year
    FROM orders o
) AS order_year ON od.order_id = order_year.id
WHERE order_year.order_year = @year OR order_year.order_year IS NULL
GROUP BY pt.product_type_name
END

select sum(a.total)
	from order_detail a
	inner join product b on b.product_id = a.product_id
	inner join product_type c on c.product_type_id = b.[type_id]
	inner join orders o on o.id = a.order_id
	where year(o.create_at) = 2023
	group by product_type_name

------------------------------
--tong so don hang, doanh thu theo ngay (hien tai)
insert into orders values ('2023-10-11', null, 120000, 2)
insert into orders values ('2023-10-11', null, 240000, 3)

insert into orders values (getdate(), null, 100000, 1)
insert into orders values (getdate(), null, 120000, 2)
insert into orders values (getdate(), null, 240000, 3)

------------------------------
--doang thu theo ngay (truyen ngay vao)
select sum(total) from orders where convert(date, create_at) between '2023-10-01' and '2023-10-10'

select p.product_id as productid, product_name as namep, brand, size, sum(quantity) as quantity, sum(od.total) as total
from product p
inner join order_detail od on od.product_id = p.product_id
inner join orders o on o.id = od.order_id
where convert(date, o.create_at) between '2023-10-01' and '2023-10-10'
group by p.product_id, product_name, brand, size

---------------------

---------------------

select convert(date, min(create_at)) from orders

select count(*) from users where is_active = 'true'

select sum(total) from orders where year(create_at) = 2023

select product_type_name from product_type

select * from orders
select * from order_detail

--WITH AllMonths AS (
--    SELECT 1 AS Month
--    UNION ALL
--    SELECT 2
--    UNION ALL
--    SELECT 3
--    UNION ALL
--    SELECT 4
--    UNION ALL
--    SELECT 5
--    UNION ALL
--    SELECT 6
--    UNION ALL
--    SELECT 7
--    UNION ALL
--    SELECT 8
--    UNION ALL
--    SELECT 9
--    UNION ALL
--    SELECT 10
--    UNION ALL
--    SELECT 11
--    UNION ALL
--    SELECT 12
--)
--SELECT am.Month, COALESCE(SUM(o.total), 0) AS Total
--FROM AllMonths am
--LEFT JOIN orders o ON am.Month = MONTH(o.create_at) AND YEAR(o.create_at) = 2023
--GROUP BY am.Month

--chay store nay
CREATE PROCEDURE GetRevenueByYear(@year INT)
AS
BEGIN
WITH AllMonths AS (
    SELECT 1 AS Month
    UNION ALL
    SELECT 2
    UNION ALL
    SELECT 3
    UNION ALL
    SELECT 4
    UNION ALL
    SELECT 5
    UNION ALL
    SELECT 6
    UNION ALL
    SELECT 7
    UNION ALL
    SELECT 8
    UNION ALL
    SELECT 9
    UNION ALL
    SELECT 10
    UNION ALL
    SELECT 11
    UNION ALL
    SELECT 12
)
SELECT COALESCE(SUM(o.total), 0)
FROM AllMonths am
LEFT JOIN orders o ON am.Month = MONTH(o.create_at) AND YEAR(o.create_at) = @year
GROUP BY am.Month
END

--doanh thu theo thang (truyen thang vao)
select sum(total)
from orders
where year(create_at) = year('2023-10-08 11:00:20.0166667') and month(create_at) = month('2023-10-08 11:00:20.0166667')

--doanh thu theo nam (truyen nam vao)
select sum(total)
from orders
where year(create_at) = year('2023-10-08 11:00:20.0166667')
------------------------------

select *
from product_repo
where product_id = 'PD0002' and size = 200

SELECT a.*, MIN(c.out_price) AS min_out_price
FROM product a
INNER JOIN product_type b ON b.product_type_id = a.[type_id]
INNER JOIN product_repo c ON c.product_id = a.product_id
WHERE product_type_name = 'Cat food'
AND brand = 'Royal Canin'
GROUP BY a.product_id, a.brand, a.create_at, a.product_desc, a.is_active, a.product_name, a.[type_id]
ORDER BY min_out_price ASC;

