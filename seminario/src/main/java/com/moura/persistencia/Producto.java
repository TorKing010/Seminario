package com.moura.persistencia;

public class Producto {
    private int id;
    private String nombre;
    private int cantidad;
    private int umbralMinimo;

    public Producto(int id, String nombre, int cantidad, int umbralMinimo) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.umbralMinimo = umbralMinimo;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public int getCantidad() { return cantidad; }
    public int getUmbralMinimo() { return umbralMinimo; }

    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
}