/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestoreCabine gestoreCabine = new GestoreCabine();
        GestoreQuadri gestoreQuadri = new GestoreQuadri();
        GestoreTecnici gestoreTecnici = new GestoreTecnici();
        GestoreInterventi gestoreInterventi = new GestoreInterventi();
        int scelta;
        do {
            System.out.println("\n===== GESTIONALE CABINE ELETTRICHE =====");
            System.out.println("1. Gestione cabine");
            System.out.println("2. Gestione quadri elettrici");
            System.out.println("3. Gestione tecnici");
            System.out.println("4. Inserisci intervento");
            System.out.println("5. Visualizza interventi");
            System.out.println("6. Ricerca interventi per tecnico");
            System.out.println("0. Esci");
            System.out.print("Scelta: ");
            scelta = scanner.nextInt();
            scanner.nextLine();
            switch (scelta) {
                case 1:
                    gestoreCabine.menuCabine(scanner);
                    break;
                case 2:
                    gestoreQuadri.menuQuadri(scanner, gestoreCabine);
                    break;

                case 3:
                    gestoreTecnici.menuTecnici(scanner);
                    break;

                case 4:
                    gestoreInterventi.aggiungiIntervento(scanner, gestoreCabine, gestoreQuadri, gestoreTecnici);
                    break;

                case 5:
                    gestoreInterventi.visualizzaInterventi();
                    break;

                case 6:
                    gestoreInterventi.ricercaPerTecnico(scanner);
                    break;

                case 0:
                    System.out.println("Uscita dal programma...");
                    break;

                default:
                    System.out.println("Scelta non valida!");
            }
        } while (scelta != 0);
    }
}
