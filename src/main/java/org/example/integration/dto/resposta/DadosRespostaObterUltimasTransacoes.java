package org.example.integration.dto.resposta;

import java.util.ArrayList;

public class DadosRespostaObterUltimasTransacoes {

    private ArrayList<ListaTransacoes> listaTransacoes;

    public DadosRespostaObterUltimasTransacoes() {}

    public ArrayList<ListaTransacoes> getListaTransacoes() {
        return listaTransacoes;
    }

    public void setListaTransacoes(ArrayList<ListaTransacoes> listaTransacoes) {
        this.listaTransacoes = listaTransacoes;
    }
}
