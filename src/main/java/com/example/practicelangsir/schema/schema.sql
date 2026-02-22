CREATE TABLE products (
    id SERIAL PRIMARY KEY,                 
    name VARCHAR(100) NOT NULL,
    price DOUBLE PRECISION NOT NULL,      
    stock_quantity INTEGER DEFAULT 0      
);
CREATE TABLE orders (
    id SERIAL PRIMARY KEY,                
    customer_email VARCHAR(100) NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DOUBLE PRECISION           
);
CREATE TABLE order_items (
    order_id INTEGER NOT NULL REFERENCES orders(id) ON DELETE CASCADE, 
    product_id INTEGER NOT NULL REFERENCES products(id),                
    quantity INTEGER NOT NULL,                                           
    subtotal DOUBLE PRECISION,                                           
    PRIMARY KEY (order_id, product_id)
);