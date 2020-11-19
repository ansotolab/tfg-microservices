INSERT INTO db.customer (id,address,cif,city,country,email,name,observations,people,phone,postal_code) VALUES
(1,'Calle Poeta de parque nº 34 Piso 3º F','A123456789','Bacarot','ES','email@email.com','La tiendita S.L.','PAGO POR ADELANTADO.','Pedro Rivera','965600123','01234'),
(2,'Avenida La Travesía nº 11','B123456789','Alicante','ES','email@email.com','El Gran Hotel S.L.','Pago a 3 meses, buen cliente.','Luis Rovira','965600123','03560'),
(3,'C Replaceta de Mendizábal','C123456789','Segovia','ES','email@email.com','Ca Paco S.A.','Nunca paga.','Fernando Di Sousa','965600123','12334');

INSERT INTO db.product_detail (id, text, customer_id) VALUES
(1, 'Texto de prueba para el cliente 1.', 1),
(2, 'Texto de prueba para el cliente 2.', 2),
(3, 'Texto de prueba para el cliente 3.', 3);

UPDATE db.customer
SET detail_id = 1
WHERE id = 1;

UPDATE db.customer
SET detail_id = 2
WHERE id = 2;

UPDATE db.customer
SET detail_id = 3
WHERE id = 3;
