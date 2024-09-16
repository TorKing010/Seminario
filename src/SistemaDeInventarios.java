import java.util.ArrayList;
import java.util.List;

public class SistemaDeInventarios {
    private List<Producto> productos;
    private List<Pedido> pedidos;

    public SistemaDeInventarios() {
        this.productos = new ArrayList<>();
        this.pedidos = new ArrayList<>();
    }

    // Agregar productos al inventario
    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    // Crear pedidos
    public void crearPedido(String idPedido, String idProducto, int cantidadSolicitada) {
        Producto producto = buscarProductoPorId(idProducto);
        if (producto != null) {
            Pedido pedido = new Pedido(idPedido, producto, cantidadSolicitada);
            pedido.procesarPedido();
            pedidos.add(pedido);
            verificarReposicionAutomatica(producto);
        } else {
            System.out.println("Producto no encontrado");
        }
    }

    // Buscar producto por ID
    public Producto buscarProductoPorId(String id) {
        for (Producto producto : productos) {
            if (producto.getId().equals(id)) {
                return producto;
            }
        }
        return null;
    }

    // Método para verificar si se necesita reposición automática
    private void verificarReposicionAutomatica(Producto producto) {
        if (producto.necesitaReposicion()) {
            System.out.println("Reposición automática activada para el producto: " + producto.getNombre());
            // Lógica para reposición automática
            producto.actualizarStock(producto.getCantidad() + 100); // Ejemplo: reponer 100 unidades
            System.out.println("Stock actualizado para " + producto.getNombre() + ": " + producto.getCantidad());
        }
    }

    // Mostrar el inventario actual
    public void mostrarInventario() {
        System.out.println("Inventario Actual:");
        for (Producto producto : productos) {
            System.out.println(producto);
        }
    }

    // Mostrar pedidos realizados
    public void mostrarPedidos() {
        System.out.println("Pedidos Realizados:");
        for (Pedido pedido : pedidos) {
            System.out.println(pedido);
        }
    }
}