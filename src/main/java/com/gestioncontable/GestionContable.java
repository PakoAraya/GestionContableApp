package com.gestioncontable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GestionContable {
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    double totalIngresos = 0;
    double totalEgresos = 0;

    while(true){
      //Menu Principal
      System.out.println("=================================================");
      System.out.println("================ MENU PRINCIPAL =================");
      System.out.println("||  1. Registrar una transaccion               ||");
      System.out.println("||  2. Mostrar transacciones registradas       ||");
      System.out.println("||  3. Calcular el total de ingresos y egresos ||");
      System.out.println("||  4. Calcular el balance general             ||");
      System.out.println("||  5. Cambiar estado de transaccion           ||");
      System.out.println("||  6. Salir                                   ||");
      System.out.println("=================================================");
      System.out.println("Por favor selecciona una opcion: ");
      int opcion = scan.nextInt();
      scan.nextLine(); //Limpiar el buffer

      switch (opcion){
        case 1 -> registrarTransaccion(scan);
        case 2 -> mostrarTransacciones();
        case 3 -> calcularTotalOperaciones();
        case 4 -> calcularBalanceGeneral();
        case 5 -> cambiarEstadoTransaccion(scan);
        case 6 -> {
          System.out.println("=================================================");
          System.out.println("==================== HASTA LUEGO =================");
          System.exit(0);
          break;
        }
        default -> System.out.println("Opcion no valida");
      }
    }
  }

  private static void registrarTransaccion(Scanner scanner) {
    System.out.print("Ingrese la descripción de la transacción: ");
    String descripcion = scanner.nextLine();
    System.out.print("Ingrese el monto de la transacción: ");
    double monto = scanner.nextDouble();
    scanner.nextLine();
    System.out.print("Ingrese el tipo (Ingreso/Egreso): ");
    String tipo = scanner.nextLine();

    try (Connection connection = DatabaseConnection.getConnection()) {
      String sql = "INSERT INTO transacciones (descripcion, monto, tipo) VALUES (?, ?, ?)";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, descripcion);
      statement.setDouble(2, monto);
      statement.setString(3, tipo);
      int rowsInserted = statement.executeUpdate();

      if (rowsInserted > 0) {
        System.out.println("Transacción registrada con éxito.");
      }
    } catch (SQLException e) {
      System.out.println("Error al registrar la transacción: " + e.getMessage());
    }
  }

  private static void mostrarTransacciones() {
    try (Connection connection = DatabaseConnection.getConnection()) {
      String sql = "SELECT * FROM transacciones";
      PreparedStatement statement = connection.prepareStatement(sql);
      ResultSet resultSet = statement.executeQuery();

      System.out.println("\n--- Transacciones Registradas ---");
      while (resultSet.next()) {
        int id = resultSet.getInt("id");
        String descripcion = resultSet.getString("descripcion");
        double monto = resultSet.getDouble("monto");
        String tipo = resultSet.getString("tipo");
        boolean estado = resultSet.getBoolean("estado");

        System.out.printf("ID: %d | Descripción: %s | Monto: %.2f | Tipo: %s | Estado: %s\n",
                id, descripcion, monto, tipo, estado ? "TRUE" : "FALSE");
      }
    } catch (SQLException e) {
      System.out.println("Error al mostrar las transacciones: " + e.getMessage());
    }
  }

  private static void calcularTotalOperaciones() {
    double totalIngresos = 0;
    double totalEgresos = 0;

    try (Connection connection = DatabaseConnection.getConnection()) {
      // Calcular ingresos
      String sqlIngresos = "SELECT SUM(monto) AS totalIngresos FROM transacciones WHERE UPPER(tipo) = 'INGRESO'";
      PreparedStatement stmtIngresos = connection.prepareStatement(sqlIngresos);
      ResultSet rsIngresos = stmtIngresos.executeQuery();
      if (rsIngresos.next()) {
        totalIngresos = rsIngresos.getDouble("totalIngresos");
        if (rsIngresos.wasNull()) {
          totalIngresos = 0.0;
        }
      }

      // Calcular egresos
      String sqlEgresos = "SELECT SUM(monto) AS totalEgresos FROM transacciones WHERE UPPER(tipo) = 'EGRESO'";
      PreparedStatement stmtEgresos = connection.prepareStatement(sqlEgresos);
      ResultSet rsEgresos = stmtEgresos.executeQuery();
      if (rsEgresos.next()) {
        totalEgresos = rsEgresos.getDouble("totalEgresos");
        if (rsEgresos.wasNull()) {
          totalEgresos = 0.0;
        }
      }

      // Imprimir resultados
      System.out.println("=================================================");
      System.out.println("====== CALCULANDO EL TOTAL DE OPERACIONES =======");
      System.out.println("Total de Ingresos: " + totalIngresos);
      System.out.println("Total de Egresos: " + totalEgresos);
      System.out.println("=================================================");

    } catch (SQLException e) {
      System.out.println("Error al calcular totales: " + e.getMessage());
    }
  }

  private static void calcularBalanceGeneral() {
    double totalIngresos = 0;
    double totalEgresos = 0;

    try (Connection connection = DatabaseConnection.getConnection()) {
      // Calcular ingresos
      String sqlIngresos = "SELECT SUM(monto) AS totalIngresos FROM transacciones WHERE tipo = 'Ingreso'";
      PreparedStatement stmtIngresos = connection.prepareStatement(sqlIngresos);
      ResultSet rsIngresos = stmtIngresos.executeQuery();
      if (rsIngresos.next()) {
        totalIngresos = rsIngresos.getDouble("totalIngresos");
      }

      // Calcular egresos
      String sqlEgresos = "SELECT SUM(monto) AS totalEgresos FROM transacciones WHERE tipo = 'Egreso'";
      PreparedStatement stmtEgresos = connection.prepareStatement(sqlEgresos);
      ResultSet rsEgresos = stmtEgresos.executeQuery();
      if (rsEgresos.next()) {
        totalEgresos = rsEgresos.getDouble("totalEgresos");
      }

      // Calcular y mostrar balance dentro de este método
      double balance = totalIngresos - totalEgresos;
      System.out.println("=================================================");
      System.out.println("============== BALANCE GENERAL ==================");
      System.out.println("Total de Ingresos: " + totalIngresos);
      System.out.println("Total de Egresos: " + totalEgresos);
      System.out.println("Balance General: " + balance);
      System.out.println("=================================================");

    } catch (SQLException e) {
      System.out.println("Error al calcular balance: " + e.getMessage());
    }
  }


  private static void cambiarEstadoTransaccion(Scanner scanner){
    System.out.print("Ingrese el ID de la transacción cuyo estado desea cambiar a TRUE: ");
    int idTransaccion = scanner.nextInt();

    try (Connection connection = DatabaseConnection.getConnection()) {
      String sql = "UPDATE transacciones SET estado = TRUE WHERE id = ?";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setInt(1, idTransaccion);
      int rowsUpdated = statement.executeUpdate();

      if (rowsUpdated > 0) {
        System.out.println("Estado de la transacción con ID " + idTransaccion + " cambiado a TRUE.");
      } else {
        System.out.println("No se encontró una transacción con el ID proporcionado.");
      }
    } catch (SQLException e) {
      System.out.println("Error al cambiar el estado de la transacción: " + e.getMessage());
    }
  }

}
