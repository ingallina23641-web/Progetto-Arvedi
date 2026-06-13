/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class Cabina {
    private String id;
    private String codice;
    private String ubicazione;
    
    public Cabina(String id, String codice, String ubicazione) {
        this.id = id;
        this.codice = codice;
        this.ubicazione = ubicazione;
    }
    
    public String getId() {
        return id;
    }
    
    public String getCodice() {
        return codice;
    }

    public String getUbicazione() {
        return ubicazione;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                " | Codice: " + codice +
                " | Ubicazione: " + ubicazione;
    }
}
