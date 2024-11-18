package com.moura.logica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import com.moura.persistencia.*;

public class Pedido {

    private int idPedido;
    private int idProducto;
    private int cantidad;
    private Date fecha;

    // Getters y setters
    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    // Método para insertar un pedido en la base de datos
    public void insertarPedido(int idProducto, int cantidad, Date fecha) throws SQLException {
        String sql = "INSERT INTO Pedidos (id_producto, cantidad, fecha) VALUES (?, ?, ?)";

        try (Connection conn = stockDAO.getConexion();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idProducto);
            pstmt.setInt(2, cantidad);
            pstmt.setDate(3, fecha);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al insertar el pedido: " + e.getMessage());
        }
    }

    // Método para obtener todos los pedidos
    public List<Pedido> obtenerPedidos() throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM Pedidos";

        try (Connection conn = stockDAO.getConexion();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setIdPedido(rs.getInt("id_pedido"));
                pedido.setIdProducto(rs.getInt("id_producto"));
                pedido.setCantidad(rs.getInt("cantidad"));
                pedido.setFecha(rs.getDate("fecha"));
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al obtener los pedidos: " + e.getMessage());
        }
        return pedidos;
    }

    // Método para actualizar un pedido existente
    public void actualizarPedido(int idPedido, int nuevaCantidad) throws SQLException {
        String sql = "UPDATE Pedidos SET cantidad = ? WHERE id_pedido = ?";

        try (Connection conn = stockDAO.getConexion();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, nuevaCantidad);
            pstmt.setInt(2, idPedido);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al actualizar el pedido: " + e.getMessage());
        }
    }

    // Método para eliminar un pedido
    public void eliminarPedido(int idPedido) throws SQLException {
        String sql = "DELETE FROM Pedidos WHERE id_pedido = ?";

        try (Connection conn = stockDAO.getConexion();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idPedido);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al eliminar el pedido: " + e.getMessage());
        }
    }
}
