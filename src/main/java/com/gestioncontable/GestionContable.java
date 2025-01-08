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


    }


  }
}