package com.example.restaurante;

public class Pedido {
    private String chave;
    private int mesa, item;
    private String prato;
    private boolean atendido;

    public Pedido(String chave, int mesa, int item, String prato) {
        this.chave = chave;
        this.mesa = mesa;
        this.item = item;
        this.prato = prato;
        this.atendido = false;
    }

    public Pedido() {}

    public String getChave() {
        return this.chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public int getMesa() {
        return this.mesa;
    }

    public void setMesa(int mesa) {
        this.mesa = mesa;
    }

    public int getItem() {
        return this.item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public String getPrato() {
        return this.prato;
    }

    public void setPrato(String prato) {
        this.prato = prato;
    }

    public boolean isAtendido() {
        return this.atendido;
    }

    public void setAtendido(boolean atendido) {
        this.atendido = atendido;
    }
}
