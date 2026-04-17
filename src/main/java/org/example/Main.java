package org.example;

import java.sql.*;

public class Main {
    public static void main(String[] args) {

        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String usuario = "RIBERA";
        String contraseña = "ribera";

        Connection conn = null;

        try {
            // Conectamos usando las variables correctas
            conn = DriverManager.getConnection(url, usuario, contraseña);

            conn.setAutoCommit(false);

            // Operación 1: Insertar empleado
            String sqlInsert = "INSERT INTO EMPLEADO (ID, NOMBRE, SALARIO) VALUES (?, ?, ?)";
            try (PreparedStatement ps1 = conn.prepareStatement(sqlInsert)) {
                ps1.setInt(1,1);
                ps1.setString(2, "Carlos");
                ps1.setDouble(3, 4200.0);
                ps1.executeUpdate();

                ps1.setInt(1,2);
                ps1.setString(2, "Mariano");
                ps1.setDouble(3, 3500);
                ps1.executeUpdate();

                ps1.setInt(1,2);
                ps1.setString(2, "Pepe");
                ps1.setDouble(3, 1200.0);
                ps1.executeUpdate();
            }


            conn.commit();
            System.out.println("Empleados insertado.");

        } catch (SQLException e) {
            System.out.println("Error en la transacción: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Se ha realizado un rollback de los cambios.");
                } catch (SQLException rollbackEx) {
                    System.out.println("Error al hacer rollback: " + rollbackEx.getMessage());
                }
            }
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
