CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       phone VARCHAR(20) NOT NULL,
                       address VARCHAR(255),
                       avatar_url VARCHAR(255),
                       user_type ENUM('CUSTOMER', 'STAFF', 'ADMIN') DEFAULT 'CUSTOMER',
                       status TINYINT(1) DEFAULT 1
);

CREATE TABLE tables (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        table_number VARCHAR(20) NOT NULL UNIQUE,
                        seats INT NOT NULL,
                        status ENUM('IDLE', 'OCCUPIED') DEFAULT 'IDLE',
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE menu_categories (
                                 id INT AUTO_INCREMENT PRIMARY KEY,
                                 name VARCHAR(50) NOT NULL,
                                 description TEXT,
                                 sort_order INT DEFAULT 0,
                                 category_type ENUM('DESSERT', 'COFFEE', 'OTHER') NOT NULL
);

CREATE TABLE menu (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      category_id INT NOT NULL,
                      name VARCHAR(100) NOT NULL,
                      price DECIMAL(10, 2) NOT NULL,
                      description TEXT,
                      image_url VARCHAR(255),
                      stock INT DEFAULT 0,
                      sales_count INT DEFAULT 0,
                      status TINYINT(1) DEFAULT 1,
                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                      FOREIGN KEY (category_id) REFERENCES menu_categories(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE orders (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        order_no VARCHAR(50) NOT NULL UNIQUE,
                        user_id INT NOT NULL,
                        total_amount DECIMAL(10, 2) NOT NULL,
                        order_type ENUM('DINE_IN', 'TAKEOUT', 'DELIVERY') NOT NULL DEFAULT 'DELIVERY',
                        table_id INT,
                        status ENUM('PENDING', 'PAID', 'PREPARING', 'DELIVERING', 'COMPLETED', 'CANCELLED') DEFAULT 'PENDING',
                        payment_status ENUM('UNPAID', 'PAID', 'REFUNDED') DEFAULT 'UNPAID',
                        payment_method VARCHAR(50),
                        delivery_address TEXT,
                        delivery_phone VARCHAR(20),
                        delivery_fee DECIMAL(10, 2) DEFAULT 0,
                        remark TEXT,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE RESTRICT ON UPDATE CASCADE,
                        FOREIGN KEY (table_id) REFERENCES tables(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE order_items (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             order_id INT NOT NULL,
                             menu_id INT NOT NULL,
                             quantity INT NOT NULL,
                             price DECIMAL(10, 2) NOT NULL,
                             subtotal DECIMAL(10, 2) NOT NULL,
                             FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE ON UPDATE CASCADE,
                             FOREIGN KEY (menu_id) REFERENCES menu(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE materials (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(100) NOT NULL,
                           quantity INT NOT NULL DEFAULT 0,
                           unit VARCHAR(20) NOT NULL,
                           supplier VARCHAR(100),
                           min_stock INT NOT NULL DEFAULT 0,
                           price DECIMAL(10, 2),
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE material_logs (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               material_id INT NOT NULL,
                               type ENUM('IN', 'OUT') NOT NULL,
                               quantity INT NOT NULL,
                               operator_id INT NOT NULL,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               remark TEXT,
                               FOREIGN KEY (material_id) REFERENCES materials(id) ON DELETE RESTRICT ON UPDATE CASCADE,
                               FOREIGN KEY (operator_id) REFERENCES users(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE delivery (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          order_id INT NOT NULL UNIQUE,
                          deliverer_id INT NOT NULL,
                          status ENUM('PENDING', 'PICKING', 'DELIVERING', 'COMPLETED', 'CANCELLED') DEFAULT 'PENDING',
                          start_time TIMESTAMP NULL,
                          end_time TIMESTAMP NULL,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE RESTRICT ON UPDATE CASCADE,
                          FOREIGN KEY (deliverer_id) REFERENCES users(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE sales_statistics (
                                  id INT AUTO_INCREMENT PRIMARY KEY,
                                  date DATE NOT NULL,
                                  total_sales DECIMAL(10, 2) NOT NULL,
                                  order_count INT NOT NULL,
                                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_users_phone ON users(phone);
CREATE INDEX idx_menu_category ON menu(category_id);
CREATE INDEX idx_menu_status ON menu(status);
CREATE INDEX idx_orders_user ON orders(user_id);
CREATE INDEX idx_orders_status ON orders(status);
CREATE INDEX idx_orders_created_at ON orders(created_at);
CREATE INDEX idx_orders_type ON orders(order_type);
CREATE INDEX idx_order_items_order ON order_items(order_id);
CREATE INDEX idx_order_items_menu ON order_items(menu_id);
CREATE INDEX idx_sales_statistics_date ON sales_statistics(date);