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

public class GestoreQuadri {
    private ArrayList<QuadroElettrico> quadri = new ArrayList<>();
    
    public void menuQuadri(Scanner scanner, GestoreCabine gestoreCabine) {
        int scelta;
        do {
            System.out.println("\n--- GESTIONE QUADRI ---");
            System.out.println("1. Aggiungi quadro");
            System.out.println("2. Visualizza quadri");
            System.out.println("0. Indietro");
            scelta = scanner.nextInt();
            scanner.nextLine();
            switch (scelta) {
                case 1:
                    aggiungiQuadro(scanner, gestoreCabine);
                    break;

                case 2:
                    visualizzaQuadri();
                    break;
            }
        } while (scelta != 0);
    }

    public void aggiungiQuadro(Scanner scanner, GestoreCabine gestoreCabine) {

        if (gestoreCabine.getCabine().isEmpty()) {
            System.out.println("Prima inserisci una cabina.");
            return;
        }
        System.out.print("ID quadro: ");
        String id = scanner.nextLine();
        System.out.print("Nome quadro: ");
        String nome = scanner.nextLine();
        System.out.print("Tipologia: ");
        String tipologia = scanner.nextLine();
        gestoreCabine.visualizzaCabine();
        System.out.print("Scegli indice cabina (partendo da 0): ");
        int indice = scanner.nextInt();
        Cabina cabina = gestoreCabine.getCabine().get(indice);
        quadri.add(new QuadroElettrico(id, nome, tipologia, cabina));
        System.out.println("Quadro aggiunto!");
    }

    public void visualizzaQuadri() {

        if (quadri.isEmpty()) {
            System.out.println("Nessun quadro presente.");
            return;
        }
        for (QuadroElettrico q : quadri) {
            System.out.println(q);
        }
    }
    
    public ArrayList<QuadroElettrico> getQuadri() {
        return quadri;
    }
}
