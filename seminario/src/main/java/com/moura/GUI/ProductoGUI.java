package com.moura.GUI;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductoGUI extends JFrame {
    
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private JTextField campoNombre;
    private JTextField campoPrecio;
    private JButton botonAgregar;
    private JButton botonEliminar;

    public ProductoGUI() {
        setTitle("Gestion de Productos");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        // Panel superior para agregar productos
        JPanel panelSuperior = new JPanel(new GridLayout(1, 4, 10, 10));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        campoNombre = new JTextField();
        campoPrecio = new JTextField();
        botonAgregar = new JButton("Agregar Producto");
        
        panelSuperior.add(new JLabel("Nombre"));
        panelSuperior.add(campoNombre);
        panelSuperior.add(new JLabel("Precio"));
        panelSuperior.add(campoPrecio);
        panelSuperior.add(botonAgregar);

        // Tabla para mostrar los productos
        String[] columnas = {"ID", "Nombre", "Precio"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaProductos = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaProductos);

        // Botón para eliminar productos seleccionados
        botonEliminar = new JButton("Eliminar Producto");

        // Panel inferior para el botón eliminar
        JPanel panelInferior = new JPanel();
        panelInferior.add(botonEliminar);

        // Agregar componentes al JFrame
        setLayout(new BorderLayout());
        add(panelSuperior, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        // Eventos
        botonAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProducto();
            }
        });

        botonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });
    }

    // Método para agregar un producto a la tabla
    private void agregarProducto() {
        String nombre = campoNombre.getText();
        String precioTexto = campoPrecio.getText();
        
        if (nombre.isEmpty() || precioTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double precio = Double.parseDouble(precioTexto);
            int id = modeloTabla.getRowCount() + 1; // Generar ID simple
            Object[] fila = {id, nombre, precio};
            modeloTabla.addRow(fila);

            campoNombre.setText("");
            campoPrecio.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para eliminar un producto de la tabla
    private void eliminarProducto() {
        int filaSeleccionada = tablaProductos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            modeloTabla.removeRow(filaSeleccionada);
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método principal
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ProductoGUI productoGUI = new ProductoGUI();
                productoGUI.setVisible(true);
            }
        });
    }
}

