package Seminario;

public class EjemploDeUso {
    public static void main(String[] args) {
        SistemaDeInventarios sistema = new SistemaDeInventarios();

        // Agregar productos al inventario
        Producto p1 = new Producto("001", "Batería 1000mAh", 50, 20);
        Producto p2 = new Producto("002", "Batería 2000mAh", 30, 10);
        sistema.agregarProducto(p1);
        sistema.agregarProducto(p2);

        // Mostrar inventario
        sistema.mostrarInventario();

        // Crear pedidos
        sistema.crearPedido("P001", "001", 40);
        sistema.crearPedido("P002", "002", 15);

        // Mostrar pedidos y verificar reposición automática
        sistema.mostrarPedidos();

        // Mostrar inventario actualizado
        sistema.mostrarInventario();
    }
}
