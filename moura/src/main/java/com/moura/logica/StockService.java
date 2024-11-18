package com.moura.logica;

import com.moura.persistencia.stockDAO;

import java.sql.SQLException;
import java.util.List;

public class StockService {
    private stockDAO stockDAO;

    public Producto obtenerProductoPorNombre(String nombreProducto) {
        try {
            return stockDAO.obtenerProductoPorNombre(nombreProducto);
        } catch (SQLException e) {
            System.err.println("Error al obtener producto por nombre: " + e.getMessage());
            return null;
        }
    }

    public StockService() {
        this.stockDAO = new stockDAO();
    }

    public List<Producto> obtenerStock() {
        return stockDAO.obtenerTodos();
    }

    public boolean realizarPedido(String nombreProducto, int cantidad) throws SQLException {
        Producto producto = stockDAO.obtenerProductoPorNombre(nombreProducto);
        if (producto != null && producto.getCantidad() >= cantidad) {
            producto.setCantidad(producto.getCantidad() - cantidad);
            stockDAO.actualizarProducto(producto);
            return true;
        }
        return false;
    }

    public void solicitarReposicion(String nombreProducto) throws SQLException {
        Producto producto = stockDAO.obtenerProductoPorNombre(nombreProducto);
        if (producto != null) {
            // Generar solicitud de reposición (lógica adicional)
            System.out.println("Solicitud de reposición generada para: " + nombreProducto);
        }
    }

    public boolean actualizarStock(int idProducto, int cantidad, boolean incrementar) {
        try {
            return stockDAO.actualizarStock(idProducto, cantidad, incrementar);
        } catch (SQLException e) {
            System.err.println("Error al actualizar el stock: " + e.getMessage());
            return false;
        }
    }
}
