CREATE DATABASE gestion_inventarios;

USE gestion_inventarios;

-- Tabla de productos
CREATE TABLE Producto (
    id VARCHAR(10) PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    cantidad INT NOT NULL,
    umbral_minimo INT NOT NULL
);

-- Tabla de pedidos
CREATE TABLE Pedido (
    id_pedido VARCHAR(10) PRIMARY KEY,
    id_producto VARCHAR(10),
    cantidad_solicitada INT NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(50) NOT NULL,
    FOREIGN KEY (id_producto) REFERENCES Producto(id)
);
