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

public class GestoreTecnici {
    private ArrayList<Tecnico> tecnici = new ArrayList<>();

    public void menuTecnici(Scanner scanner) {
        int scelta;
        do {
            System.out.println("\n--- GESTIONE TECNICI ---");
            System.out.println("1. Aggiungi tecnico");
            System.out.println("2. Visualizza tecnici");
            System.out.println("0. Indietro");
            System.out.print("Scelta: ");
            scelta = scanner.nextInt();
            scanner.nextLine();
            switch (scelta) {
                case 1:
                    aggiungiTecnico(scanner);
                    break;

                case 2:
                    visualizzaTecnici();
                    break;

                case 0:
                    System.out.println("Ritorno al menu principale...");
                    break;

                default:
                    System.out.println("Scelta non valida!");
            }

        } while (scelta != 0);
    }

    public void aggiungiTecnico(Scanner scanner) {
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Cognome: ");
        String cognome = scanner.nextLine();
        System.out.print("Reparto: ");
        String reparto = scanner.nextLine();
        Tecnico tecnico = new Tecnico(id, nome, cognome, reparto);
        tecnici.add(tecnico);
        System.out.println("Tecnico aggiunto con successo!");
    }

    public void visualizzaTecnici() {
        if (tecnici.isEmpty()) {
            System.out.println("Nessun tecnico presente.");
            return;
        }
        for (Tecnico t : tecnici) {
            System.out.println(t);
        }
    }
    public ArrayList<Tecnico> getTecnici() {
        return tecnici;
    }
}