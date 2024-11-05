package com.moura.logica;
import com.moura.persistencia.Producto;
import com.moura.persistencia.stockDAO;
import java.util.List;

public class StockService {
    private stockDAO stockDAO;

    public StockService() {
        this.stockDAO = new stockDAO();
    }

    public List<Producto> obtenerStock() {
        return stockDAO.obtenerTodos();
    }

    public boolean realizarPedido(String nombreProducto, int cantidad) {
        Producto producto = stockDAO.obtenerPorNombre(nombreProducto);
        if (producto != null && producto.getCantidad() >= cantidad) {
            producto.setCantidad(producto.getCantidad() - cantidad);
            stockDAO.actualizarProducto(producto);
            return true;
        }
        return false;
    }

    public void solicitarReposicion(String nombreProducto) {
        Producto producto = stockDAO.obtenerPorNombre(nombreProducto);
        if (producto != null) {
            // Generar solicitud de reposición (lógica adicional)
            System.out.println("Solicitud de reposición generada para: " + nombreProducto);
        }
    }
}
