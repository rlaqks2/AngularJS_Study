/*POSTGRES*/
INSERT INTO "user"(id, email, enabled, password, role, username) VALUES 
(1,'admin@jordanec.com', TRUE, '$2a$10$0sDyWkFXQj7PQVXsjijg6O4O2clhfyjpvSwmouaCgBUypJTqKsipG','ROLE_ADMIN', 'admin'),
(2,'user@jordanec.com',TRUE,'$2a$10$oj9OJwL6ww.ADlG4wefYbern8k0AJSbKplBkzdRQOCRDhDzULdxMK','ROLE_USER','user');

INSERT INTO supplier(id, address, company, name, phone) VALUES 
(1,'San Rafael Arriba','AMSO','Juan Vargas',88997744),
(2,'Curridabat','PPLU','Carlos Mora',65472136),
(3,'San Carlos','ZZMA','Ana Rosales',78456589);

INSERT INTO category(id, description, name) VALUES 
(1,'Artículos deportivos','Deportes'),
(2,'Anteojos, gafas y accesorios','Lentes'),
(3,'Teléfonos celulares y accesorios','Celulares'),
(4,'Equipo de cómputo y accesorios','Cómputo');

INSERT INTO product(id, description, name, unit_price, units_in_stock, category_id) VALUES
(1,'Sony. Procesador 1.5 GHZ, Memoria RAM 2 GB, Memoria Interna 32 GB, Camara 8 Mpx','Celular',180000.00,24,3),
(2,'Adidas Brazuca 2014','Balón de futbol',35000.00,60,1),
(3,'Gafas de sol','Gafas',25000.00,20,2),
(4,'Western Digital Capacidad 2 TB','Disco duro externo',80000.00,10,4);

INSERT INTO "product_suppliers"(products_id,suppliers_id)VALUES
(1,1),
(2,1),
(1,2),
(2,2),
(1,3),
(2,3),
(3,3),
(4,3);
CREATE TABLE "user" (
  id BIGINT NOT NULL,
  username VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  password VARCHAR(255),
  enabled BOOLEAN,
  role VARCHAR(255),
  CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE "user" ADD CONSTRAINT uc_user_email UNIQUE (email);

ALTER TABLE "user" ADD CONSTRAINT uc_user_username UNIQUE (username);
CREATE TABLE category (
  id BIGINT NOT NULL,
  name VARCHAR(255),
  description VARCHAR(255),
  CONSTRAINT pk_category PRIMARY KEY (id)
);
CREATE TABLE product (
  id BIGINT NOT NULL,
  name VARCHAR(255),
  description VARCHAR(255),
  unit_price DOUBLE PRECISION,
  units_in_stock INTEGER,
  category_id BIGINT,
  CONSTRAINT pk_product PRIMARY KEY (id)
);

ALTER TABLE product ADD CONSTRAINT FK_PRODUCT_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);
CREATE TABLE supplier (
  id BIGINT NOT NULL,
  name VARCHAR(255),
  company VARCHAR(255),
  address VARCHAR(255),
  phone INTEGER,
  CONSTRAINT pk_supplier PRIMARY KEY (id)
);