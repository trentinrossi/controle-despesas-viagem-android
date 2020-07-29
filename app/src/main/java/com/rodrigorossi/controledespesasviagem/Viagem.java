package com.rodrigorossi.controledespesasviagem;

import androidx.annotation.NonNull;

import java.time.LocalDate;

public class Viagem {
    private String destino;
    private int kmInicial;
    private int kmFinal;
    private int tipoViagem;
    private boolean reembolsar;
    private int tipoVeiculo;

    public Viagem() {

    }

    public Viagem(String destino, int kmInicial, int kmFinal, int tipoViagem, boolean reembolsar, int tipoVeiculo) {
        this.destino = destino;
        this.kmInicial = kmInicial;
        this.kmFinal = kmFinal;
        this.tipoViagem = tipoViagem;
        this.reembolsar = reembolsar;
        this.tipoVeiculo = tipoVeiculo;
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

    public int getTipoViagem() {
        return tipoViagem;
    }

    public void setTipoViagem(int tipoViagem) {
        this.tipoViagem = tipoViagem;
    }

    public boolean isReembolsar() {
        return reembolsar;
    }

    public void setReembolsar(boolean reembolsar) {
        this.reembolsar = reembolsar;
    }

    public int getTipoVeiculo() {
        return tipoVeiculo;
    }

    public void setTipoVeiculo(int tipoVeiculo) {
        this.tipoVeiculo = tipoVeiculo;
    }

    @NonNull
    @Override
    public String toString() {
        return this.getDestino();
    }
}
