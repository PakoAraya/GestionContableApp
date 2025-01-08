package com.gestioncontable;

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
      System.out.println("||  4. Calcular el balance                     ||");
      System.out.println("||  5. Salir                                   ||");
      System.out.println("=================================================");
      System.out.println("Por favor selecciona una opcion: ");
      int opcion = scan.nextInt();

      switch (opcion){
        case 1:
          totalIngresos = registrarTransaccion(scan, totalIngresos, totalEgresos);
          break;
        case 2:
          mostrarTransacciones();
          break;
        case 3:
          calcularTotalOperaciones(totalIngresos, totalEgresos);
          break;
        case 4:
          calcularBalance(totalIngresos, totalEgresos);
          break;
        case 5:
          System.out.println("=================================================");
          System.out.println("==================== HASTA LUEGO =================");
          System.exit(0);
          break;
        default:
          System.out.println("Opcion no valida");
      }
    }
  }

  private static double registrarTransaccion(Scanner scanner, double totalIngresos, double totalEgresos){
    System.out.println("=================================================");
    System.out.println("============== REGISTRAR TRANSACCION ============");
    System.out.println("Por favor selecciona el tipo de transaccion: ");
    System.out.println("1. Ingreso");
    System.out.println("2. Egreso");
    int tipoTransaccion = scanner.nextInt();
    System.out.println("Por favor ingresa el monto de la transaccion: ");
    double monto = scanner.nextDouble();
    if(tipoTransaccion == 1){
      totalIngresos += monto;
      System.out.println("Ingreso registrado: " + monto);
    }else if(tipoTransaccion == 2){
      totalEgresos += monto;
      System.out.println("Egreso registrado: " + monto);
    }else{
      System.out.println("Tipo de transaccion no valida");
    }
    return totalIngresos; // Devuelves los totales actualizados
  }

  private static void mostrarTransacciones(){
    System.out.println("=================================================");
    System.out.println("======= TRANSACCIONES REGISTRADAS ===============");
    System.out.println("Ingreso: 1500");
    System.out.println("Egreso: 300");
    System.out.println("Ingreso: 2000");
    System.out.println("Egreso: 500");
  }

  private static void calcularTotalOperaciones(double totalIngresos, double totalEgresos){
    System.out.println("=================================================");
    System.out.println("======= CALCULAR EL TOTAL DE OPERACIONES ========");
    System.out.println("Total de Ingresos: " + totalIngresos);
    System.out.println("Total de Egresos: " + totalEgresos);

  }

  private static void calcularBalance(double totalIngresos, double totalEgresos){
    System.out.println("=================================================");
    System.out.println("================= CALCULAR EL BALANCE ============");
    System.out.println("Balance: " + (totalIngresos - totalEgresos));
  }
}
