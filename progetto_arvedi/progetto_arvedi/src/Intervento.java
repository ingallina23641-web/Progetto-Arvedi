/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class Intervento {
    private String id;
    private String data;
    private Tecnico tecnico;
    private Cabina cabina;
    private QuadroElettrico quadro;
    private String esito;
    private String note;
    private String priorita;

    public Intervento(String id, String data, Tecnico tecnico, Cabina cabina, QuadroElettrico quadro, String esito, String note, String priorita) {
        this.id = id;
        this.data = data;
        this.tecnico = tecnico;
        this.cabina = cabina;
        this.quadro = quadro;
        this.esito = esito;
        this.note = note;
        this.priorita = priorita;
    }

    public String getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public Cabina getCabina() {
        return cabina;
    }

    public QuadroElettrico getQuadro() {
        return quadro;
    }

    public String getEsito() {
        return esito;
    }

    public String getNote() {
        return note;
    }

    public String getPriorita() {
        return priorita;
    }

    @Override
    public String toString() {
        return "\nINTERVENTO " + id +
                "\nData: " + data +
                "\nTecnico: " + tecnico.getNome() + " " + tecnico.getCognome() +
                "\nCabina: " + cabina.getCodice() +
                "\nQuadro: " + quadro.getNome() +
                "\nEsito: " + esito +
                "\nPriorita: " + priorita +
                "\nNote: " + note;
    }
}
