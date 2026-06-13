/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class QuadroElettrico {
    private String id;
    private String nome;
    private String tipologia;
    private Cabina cabina;

    public QuadroElettrico(String id, String nome, String tipologia, Cabina cabina) {
        this.id = id;
        this.nome = nome;
        this.tipologia = tipologia;
        this.cabina = cabina;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTipologia() {
        return tipologia;
    }

    public Cabina getCabina() {
        return cabina;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                " | Nome: " + nome +
                " | Tipologia: " + tipologia +
                " | Cabina: " + cabina.getCodice();
    }
}
