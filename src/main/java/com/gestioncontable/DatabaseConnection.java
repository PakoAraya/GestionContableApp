package com.gestioncontable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
  private static final String URL = "jdbc:postgresql://pg-27b405f-franarayah-postgresql.b.aivencloud.com:13195/pruebajpa?ssl=require";
  private static final String USER = "avnadmin";
  private static final String PASSWORD = "AVNS_zZq2-USoxo5N9Lv32j_";

  //https://jdbc.postgresql.org/download/
  public static Connection getConnection() throws SQLException {
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      throw new RuntimeException("Error al cargar el controlador de PostgreSQL", e);
    }
    return DriverManager.getConnection(URL, USER, PASSWORD);
  }
}

