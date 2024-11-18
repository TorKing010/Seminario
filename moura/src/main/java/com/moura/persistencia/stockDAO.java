package com.moura.persistencia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.moura.logica.Producto;

public class stockDAO {
    private static final String URL = "jdbc:mariadb://Torka:3306/gestion_inventarios";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
//conexion a la base de datos
    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public List<Producto> obtenerTodos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos";

        try (Connection conn = getConexion();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                productos.add(new Producto(
                        rs.getInt("id_producto"),
                        rs.getString("nombre"),
                        rs.getInt("cantidad"),
                        rs.getInt("umbral_minimo")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

    public Producto obtenerProductoPorNombre(String nombreProducto) throws SQLException {
        String sql = "SELECT * FROM productos WHERE nombre = ?";

        try (Connection conn = getConexion();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombreProducto);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Crear y devolver un objeto Producto basado en los datos de la consulta
                    return new Producto(
                            rs.getInt("id_producto"),
                            rs.getString("nombre"),
                            rs.getInt("cantidad"),
                            rs.getInt("umbral_minimo"));
                }
            }
        }
        return null; // Devuelve null si no se encuentra el producto
    }

    public void actualizarProducto(Producto producto) {
        String sql = "UPDATE productos SET cantidad = ? WHERE id_producto = ?";

        try (Connection conn = getConexion();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, producto.getCantidad());
            pstmt.setInt(2, producto.getIdProducto());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean actualizarStock(int idProducto, int cantidad, boolean incrementar) throws SQLException {
        String sql = incrementar ? "UPDATE productos SET cantidad = cantidad + ? WHERE id_producto = ?"
                : "UPDATE productos SET cantidad = cantidad - ? WHERE id_producto = ?";

        try (Connection conn = getConexion();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, cantidad);
            pstmt.setInt(2, idProducto);

            int filasActualizadas = pstmt.executeUpdate();
            return filasActualizadas > 0;
        }
    }
}
