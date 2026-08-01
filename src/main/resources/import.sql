-- ================================
-- Formas de pagamento
-- ================================
INSERT INTO payment_method_tb (name, fee, created_at, updated_at, deleted) VALUES ('Dinheiro', 0.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false);
INSERT INTO payment_method_tb (name, fee, created_at, updated_at, deleted) VALUES ('Pix', 0.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false);
INSERT INTO payment_method_tb (name, fee, created_at, updated_at, deleted) VALUES ('Cartão de Débito', 1.50, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false);
INSERT INTO payment_method_tb (name, fee, created_at, updated_at, deleted) VALUES ('Cartão de Crédito', 3.50, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false);

-- ================================
-- Bairros
-- ================================
INSERT INTO neighborhood_tb (name, delivery_fee, created_at, updated_at, deleted) VALUES ('Centro', 0.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false);
INSERT INTO neighborhood_tb (name, delivery_fee, created_at, updated_at, deleted) VALUES ('Jardim América', 6.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false);
INSERT INTO neighborhood_tb (name, delivery_fee, created_at, updated_at, deleted) VALUES ('Vila Nova', 8.50, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false);
INSERT INTO neighborhood_tb (name, delivery_fee, created_at, updated_at, deleted) VALUES ('Bela Vista', 5.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false);
INSERT INTO neighborhood_tb (name, delivery_fee, created_at, updated_at, deleted) VALUES ('Santa Rosa', 10.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false);

-- ================================
-- Produtos
-- ================================
INSERT INTO product_tb (name, barcode, cost_price, sale_price, stock_quantity, active, version, created_at, updated_at, deleted) VALUES ('X-Burger', '7891000000011', 8.00, 18.90, 50, true, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false);
INSERT INTO product_tb (name, barcode, cost_price, sale_price, stock_quantity, active, version, created_at, updated_at, deleted) VALUES ('X-Bacon', '7891000000028', 10.50, 22.90, 40, true, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false);
INSERT INTO product_tb (name, barcode, cost_price, sale_price, stock_quantity, active, version, created_at, updated_at, deleted) VALUES ('X-Salada', '7891000000035', 9.00, 19.90, 45, true, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false);
INSERT INTO product_tb (name, barcode, cost_price, sale_price, stock_quantity, active, version, created_at, updated_at, deleted) VALUES ('Batata Frita', '7891000000042', 4.00, 12.90, 60, true, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false);
INSERT INTO product_tb (name, barcode, cost_price, sale_price, stock_quantity, active, version, created_at, updated_at, deleted) VALUES ('Refrigerante Lata', '7891000000059', 2.50, 6.90, 100, true, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false);
INSERT INTO product_tb (name, barcode, cost_price, sale_price, stock_quantity, active, version, created_at, updated_at, deleted) VALUES ('Milkshake de Chocolate', '7891000000066', 6.00, 15.90, 2, true, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false);
INSERT INTO product_tb (name, barcode, cost_price, sale_price, stock_quantity, active, version, created_at, updated_at, deleted) VALUES ('Cheddar Extra', '7891000000073', 3.00, 5.00, 0, false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false);