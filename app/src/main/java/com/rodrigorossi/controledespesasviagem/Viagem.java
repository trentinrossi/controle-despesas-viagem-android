package com.rodrigorossi.controledespesasviagem;

import java.time.LocalDate;

public class Viagem {
    private String destino;
    private int kmInicial;
    private int kmFinal;
    private String tipoViagem;

    public Viagem(String destino, int kmInicial, int kmFinal, String tipoViagem) {
        this.destino = destino;
        this.kmInicial = kmInicial;
        this.kmFinal = kmFinal;
        this.tipoViagem = tipoViagem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public int getKmInicial() {
        return kmInicial;
    }

    public void setKmInicial(int kmInicial) {
        this.kmInicial = kmInicial;
    }

    public int getKmFinal() {
        return kmFinal;
    }

    public void setKmFinal(int kmFinal) {
        this.kmFinal = kmFinal;
    }

    public String getTipoViagem() {
        return tipoViagem;
    }

    public void setTipoViagem(String tipoViagem) {
        this.tipoViagem = tipoViagem;
    }
}
