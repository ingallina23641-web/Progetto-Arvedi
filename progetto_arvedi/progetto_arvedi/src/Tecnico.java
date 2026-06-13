/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class Tecnico {
    private String id;
    private String nome;
    private String cognome;
    private String reparto;

    public Tecnico(String id, String nome, String cognome, String reparto) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.reparto = reparto;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getReparto() {
        return reparto;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                " | Nome: " + nome +
                " " + cognome +
                " | Reparto: " + reparto;
    }
}