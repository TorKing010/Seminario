package com.moura;

public class Producto {

    private String id;
    private String nombre;
    private int cantidad;
    private int umbralMinimo; // Nivel mínimo para reposición automática

    public Producto(String id, String nombre, int cantidad, int umbralMinimo) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.umbralMinimo = umbralMinimo;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getUmbralMinimo() {
        return umbralMinimo;
    }

    public boolean necesitaReposicion() {
        return cantidad <= umbralMinimo;
    }

    public void actualizarStock(int cantidadNueva) {
        this.cantidad = cantidadNueva;
    }

    @Override
    public String toString() {
        return "Producto [ID: " + id + ", Nombre: " + nombre + ", Cantidad: " + cantidad + "]";
    }
    
}
