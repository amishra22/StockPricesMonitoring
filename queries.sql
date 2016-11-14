CREATE TABLE real_time_stock_info(symbol varchar(25) NOT NULL PRIMARY KEY, comp_name varchar(100), stock_value double, time_val varchar(100));
CREATE TABLE all_time_stock_info(symbol varchar(25), comp_name varchar(100), stock_value double, time_val varchar(100));
INSERT INTO real_time_stock_info(symbol, comp_name, stock_value, time_val) VALUES ('AAPL', 'Apple Inc.', 91.43, '2016-10-14-08-40-22');
INSERT INTO all_time_stock_info(symbol, comp_name, stock_value, time_val) VALUES ('AAPL', 'Apple Inc.', 91.43, '2016-10-14-08-40-22');
INSERT INTO all_time_stock_info(symbol, comp_name, stock_value, time_val) VALUES ('GOOGL', 'Alphabet Inc.', 720.73, '2016-10-14-08-42-52');
INSERT INTO real_time_stock_info(symbol, comp_name, stock_value, time_val) VALUES ('GOOGL', 'Alphabet Inc.', 720.73, '2016-10-14-08-42-52');
INSERT INTO all_time_stock_info(symbol, comp_name, stock_value, time_val) VALUES ('FB', 'Facebook, Inc.', 72.13, '2016-10-14-06-24-52');
INSERT INTO real_time_stock_info(symbol, comp_name, stock_value, time_val) VALUES ('FB', 'Facebook, Inc.', 72.13, '2016-10-14-06-24-52');
