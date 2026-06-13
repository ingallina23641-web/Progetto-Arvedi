/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
import java.util.ArrayList;
import java.util.Scanner;

public class GestoreCabine {
    private ArrayList<Cabina> cabine = new ArrayList<>();
    public void menuCabine(Scanner scanner) {
        int scelta;
        do {
            System.out.println("\n--- GESTIONE CABINE ---");
            System.out.println("1. Aggiungi cabina");
            System.out.println("2. Visualizza cabine");
            System.out.println("0. Indietro");
            scelta = scanner.nextInt();
            scanner.nextLine();
            switch (scelta) {
                case 1:
                    aggiungiCabina(scanner);
                    break;

                case 2:
                    visualizzaCabine();
                    break;
            }
        } while (scelta != 0);
    }

    public void aggiungiCabina(Scanner scanner) {
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Codice: ");
        String codice = scanner.nextLine();
        System.out.print("Ubicazione: ");
        String ubicazione = scanner.nextLine();
        cabine.add(new Cabina(id, codice, ubicazione));
        System.out.println("Cabina aggiunta!");
    }

    public void visualizzaCabine() {
        if (cabine.isEmpty()) {
            System.out.println("Nessuna cabina presente.");
            return;
        }
        for (Cabina c : cabine) {
            System.out.println(c);
        }
    }

    public ArrayList<Cabina> getCabine() {
        return cabine;
    }
}
