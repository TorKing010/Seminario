-- Consulta de inventario de productos 

SELECT * FROM Producto;

-- Generar un pedido cuando el stock es bajo

SELECT * FROM Producto WHERE cantidad <= umbral_minimo;

-- Autenticar usuario

SELECT * FROM Usuario WHERE email = 'admin@bateriasmoura.com' AND password = 'PatitoJuan123';

-- Consultar pedido realizado 

SELECT * FROM Pedido;