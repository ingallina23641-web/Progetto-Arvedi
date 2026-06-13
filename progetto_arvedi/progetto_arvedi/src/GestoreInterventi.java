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

public class GestoreInterventi {

    private ArrayList<Intervento> interventi = new ArrayList<>();

    public void aggiungiIntervento(Scanner scanner,
                                   GestoreCabine gestoreCabine,
                                   GestoreQuadri gestoreQuadri,
                                   GestoreTecnici gestoreTecnici) {

        if (gestoreCabine.getCabine().isEmpty()) {
            System.out.println("Nessuna cabina presente.");
            return;
        }

        if (gestoreQuadri.getQuadri().isEmpty()) {
            System.out.println("Nessun quadro presente.");
            return;
        }

        if (gestoreTecnici.getTecnici().isEmpty()) {
            System.out.println("Nessun tecnico presente.");
            return;
        }

        System.out.print("ID intervento: ");
        String id = scanner.nextLine();

        System.out.print("Data: ");
        String data = scanner.nextLine();

        System.out.println("\n--- TECNICI DISPONIBILI ---");
        gestoreTecnici.visualizzaTecnici();

        System.out.print("Indice tecnico: ");
        int indiceTecnico = scanner.nextInt();

        System.out.println("\n--- CABINE DISPONIBILI ---");
        gestoreCabine.visualizzaCabine();

        System.out.print("Indice cabina: ");
        int indiceCabina = scanner.nextInt();

        System.out.println("\n--- QUADRI DISPONIBILI ---");
        gestoreQuadri.visualizzaQuadri();

        System.out.print("Indice quadro: ");
        int indiceQuadro = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Esito: ");
        String esito = scanner.nextLine();

        System.out.print("Priorità (BASSA/MEDIA/ALTA): ");
        String priorita = scanner.nextLine();

        System.out.print("Note: ");
        String note = scanner.nextLine();

        Tecnico tecnico =
                gestoreTecnici.getTecnici().get(indiceTecnico);

        Cabina cabina =
                gestoreCabine.getCabine().get(indiceCabina);

        QuadroElettrico quadro =
                gestoreQuadri.getQuadri().get(indiceQuadro);

        Intervento intervento = new Intervento(
                id,
                data,
                tecnico,
                cabina,
                quadro,
                esito,
                note,
                priorita
        );

        interventi.add(intervento);

        System.out.println("Intervento registrato correttamente!");
    }

    public void visualizzaInterventi() {

        if (interventi.isEmpty()) {
            System.out.println("Nessun intervento presente.");
            return;
        }

        for (Intervento i : interventi) {
            System.out.println(i);
        }
    }

    public void ricercaPerTecnico(Scanner scanner) {

        if (interventi.isEmpty()) {
            System.out.println("Nessun intervento presente.");
            return;
        }

        System.out.print("Nome tecnico: ");
        String nome = scanner.nextLine();

        boolean trovato = false;

        for (Intervento i : interventi) {

            if (i.getTecnico().getNome().equalsIgnoreCase(nome)) {

                System.out.println(i);
                trovato = true;
            }
        }

        if (!trovato) {
            System.out.println("Nessun intervento trovato.");
        }
    }

    public ArrayList<Intervento> getInterventi() {
        return interventi;
    }
}
