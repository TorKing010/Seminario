package com.moura.GUI;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.moura.logica.StockService;

public class StockGUI {
    private static StockService stockService = new StockService();
    private static JTable tablaStock;
    private static DefaultTableModel modeloTabla;
    private static JTextField campoNombreProducto;
    private static JTextField campoCantidadPedido;
    private static JButton botonRealizarPedido;
    private static JButton botonSolicitarReposicion;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Crear el marco principal
            JFrame frame = new JFrame("Control de Stock - Baterias Moura");
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 200);  // Tamaño de la ventana
            
            // Panel principal
            JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
            
            // Tabla de stock
            modeloTabla = new DefaultTableModel(new String[] { "ID", "Producto", "Cantidad Disponible", "Umbral Mínimo" },
            0);
            
            Object[][] productosDePrueba = {
                { "1", "Batería Tipo A", 50, 10 },
                { "2", "Batería Tipo B", 30, 5 },
                { "3", "Batería Tipo C", 15, 3 },
                { "4", "Batería Tipo D", 60, 12 },
                { "5", "Batería Tipo E", 20, 8 }
            };

            for (Object[] fila : productosDePrueba) {
                modeloTabla.addRow(fila);
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
            botonRealizarPedido.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    realizarPedido();
                }

                private void realizarPedido() {
                    String nombreProducto = campoNombreProducto.getText();
                    String cantidadTexto = campoCantidadPedido.getText();

                    if (nombreProducto.isEmpty() || cantidadTexto.isEmpty()) {
                            JOptionPane.showMessageDialog(panelPedido, "Complete todos los campos para realizar el pedido.", "ALERTA", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    try {
                        int cantidad = Integer.parseInt(cantidadTexto);
                        boolean pedidoExitoso = stockService.realizarPedido(nombreProducto, cantidad);
                        if (pedidoExitoso) {
                            JOptionPane.showMessageDialog(panelPedido, "Pedido de " + cantidad + " unidades de " + nombreProducto + " realizado.", "COMPLETADO", JOptionPane.OK_OPTION);
                            return;
                        } else {
                            JOptionPane.showMessageDialog(panelPedido, "Stock insuficiente para realizar el pedido.", "SOLICITAR REPOSICIÓN", JOptionPane.WARNING_MESSAGE);
                            return;
                    }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(panelPedido, "La cantidad debe ser un número entero.", "ALERTA", JOptionPane.OK_OPTION);
                        return;
                    }
                    catch (Exception ex) {
                        JOptionPane.showMessageDialog(panelPedido, ex.toString(), "ERROR!!!", JOptionPane.ERROR);
                    }
                }
            });

            botonSolicitarReposicion.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String nombreProducto = campoNombreProducto.getText();
                    stockService.solicitarReposicion(nombreProducto);
                    JOptionPane.showMessageDialog(panelPedido, "Solicitud de reposición enviada para " + nombreProducto + ".", "REPOSICIÓN", JOptionPane.INFORMATION_MESSAGE);
                }
            });
        });
    }
}
