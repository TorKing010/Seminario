package com.moura.GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.moura.logica.*;

import java.util.List;

public class StockGUI {
    private static StockService stockService = new StockService();
    private static JTable tablaStock;
    private static DefaultTableModel modeloTabla;
    private static JTextField campoNombreProducto;
    private static JTextField campoCantidadPedido;
    private static JButton botonRealizarPedido;
    private static JButton botonSolicitarReposicion;

    private static void recargarTabla() {
        modeloTabla.setRowCount(0); // Limpia la tabla actual

        try {
            List<Producto> productos = stockService.obtenerStock();
            for (Producto producto : productos) {
                modeloTabla.addRow(new Object[] {
                        producto.getIdProducto(),
                        producto.getNombre(),
                        producto.getCantidad(),
                        producto.getUmbralMinimo()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Error al recargar los datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Crear el marco principal
            JFrame frame = new JFrame("Control de Stock - Baterias Moura");
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 200); // Tamaño de la ventana

            // Panel principal
            JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));

            modeloTabla = new DefaultTableModel(
                    new String[] { "ID_Producto", "Nombre", "Cantidad", "Umbral_Mínimo" },
                    0);
            // Obtener los datos desde la base de datos
            try {

                List<Producto> productos = stockService.obtenerStock();
                for (Producto producto : productos) {
                    modeloTabla.addRow(new Object[] {
                            producto.getIdProducto(),
                            producto.getNombre(),
                            producto.getCantidad(),
                            producto.getUmbralMinimo()
                    });
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,
                        "Error al cargar los datos desde la base de datos: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

            tablaStock = new JTable(modeloTabla);
            JScrollPane scrollPane = new JScrollPane(tablaStock);
            panelPrincipal.add(scrollPane, BorderLayout.CENTER);

            // Panel de pedidos
            JPanel panelPedido = new JPanel(new GridLayout(3, 2, 5, 5));
            panelPedido.setBorder(BorderFactory.createTitledBorder("Realizar Pedido"));

            campoNombreProducto = new JTextField();
            campoCantidadPedido = new JTextField();
            botonRealizarPedido = new JButton("Realizar Pedido");

            panelPedido.add(new JLabel("Nombre del Producto:"));
            panelPedido.add(campoNombreProducto);
            panelPedido.add(new JLabel("Cantidad a Pedir:"));
            panelPedido.add(campoCantidadPedido);
            panelPedido.add(new JLabel());
            panelPedido.add(botonRealizarPedido);

            // Botón de reposición
            botonSolicitarReposicion = new JButton("Solicitar Reposición de Stock");
            panelPrincipal.add(panelPedido, BorderLayout.NORTH);
            panelPrincipal.add(botonSolicitarReposicion, BorderLayout.SOUTH);

            frame.add(panelPrincipal);
            frame.pack();
            frame.setVisible(true);

            // Eventos de botones
            botonRealizarPedido.addActionListener(e -> {
                try {
                    String nombreProducto = campoNombreProducto.getText();
                    int cantidad = Integer.parseInt(campoCantidadPedido.getText());

                    // Obtener el producto desde el servicio para buscar su ID
                    Producto producto = stockService.obtenerProductoPorNombre(nombreProducto);

                    if (producto == null) {
                        JOptionPane.showMessageDialog(panelPedido,
                                "El producto no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Actualizar el stock
                    boolean exito = stockService.actualizarStock(producto.getIdProducto(), cantidad, false);
                    if (exito) {
                        JOptionPane.showMessageDialog(panelPedido,
                                "Pedido realizado con éxito. Stock actualizado.", "Éxito",
                                JOptionPane.INFORMATION_MESSAGE);
                        recargarTabla(); // Actualiza la tabla para reflejar el cambio
                    } else {
                        JOptionPane.showMessageDialog(panelPedido,
                                "Stock insuficiente para realizar el pedido.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panelPedido,
                            "La cantidad debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panelPedido,
                            "Error al realizar el pedido: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            botonSolicitarReposicion.addActionListener(e -> {
                try {
                    String nombreProducto = campoNombreProducto.getText();
                    int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Cantidad a reponer:"));

                    Producto producto = stockService.obtenerProductoPorNombre(nombreProducto);

                    if (producto == null) {
                        JOptionPane.showMessageDialog(panelPedido,
                                "El producto no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Metodo de reposición de stock
                    boolean exito = stockService.actualizarStock(producto.getIdProducto(), cantidad, true);
                    if (exito) {
                        JOptionPane.showMessageDialog(panelPedido,
                                "Reposición realizada con éxito. Stock actualizado.", "Éxito",
                                JOptionPane.INFORMATION_MESSAGE);
                        recargarTabla(); // Actualiza la tabla para reflejar el cambio
                    } else {
                        JOptionPane.showMessageDialog(panelPedido,
                                "Error al solicitar reposición.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panelPedido,
                            "La cantidad debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panelPedido,
                            "Error al solicitar reposición: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        });
    }
}
