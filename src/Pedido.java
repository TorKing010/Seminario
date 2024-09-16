package Seminario;

import java.util.Date;

public class Pedido {
    private String idPedido;
    private Producto producto;
    private int cantidadSolicitada;
    private Date fecha;
    private String estado;

    public Pedido(String idPedido, Producto producto, int cantidadSolicitada) {
        this.idPedido = idPedido;
        this.producto = producto;
        this.cantidadSolicitada = cantidadSolicitada;
        this.fecha = new Date();
        this.estado = "Pendiente";
    }

    public String getIdPedido() {
        return idPedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void procesarPedido() {
        if (producto.getCantidad() >= cantidadSolicitada) {
            producto.setCantidad(producto.getCantidad() - cantidadSolicitada);
            estado = "Completado";
        } else {
            estado = "Fallido: Stock insuficiente";
        }
    }

    @Override
    public String toString() {
        return "Pedido [ID: " + idPedido + ", Producto: " + producto.getNombre() + ", Cantidad: " + cantidadSolicitada +
               ", Estado: " + estado + "]";
    }
}
