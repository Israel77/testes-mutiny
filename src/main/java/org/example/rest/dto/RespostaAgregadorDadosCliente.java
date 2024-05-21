package org.example.rest.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import org.example.integration.dto.resposta.ListaTransacoes;

public class RespostaAgregadorDadosCliente {
    private String textoNomeCliente;
    private String dataNascimentoCliente;
    private String textoGeneroCliente;
    private String textoNomeCidade;
    private String textoSiglaEstado;
    private String codigoEnderecoPostal;
    private BigDecimal saldoAtualContaCorrente;
    private ArrayList<ListaTransacoes> listaTransacoes;

    public RespostaAgregadorDadosCliente() {}

    public ArrayList<ListaTransacoes> getListaTransacoes() {
        return listaTransacoes;
    }

    public void setListaTransacoes(ArrayList<ListaTransacoes> listaTransacoes) {
        this.listaTransacoes = listaTransacoes;
    }

    public String getTextoNomeCliente() {
        return textoNomeCliente;
    }

    public void setTextoNomeCliente(String textoNome) {
        this.textoNomeCliente = textoNome;
    }

    public String getDataNascimentoCliente() {
        return dataNascimentoCliente;
    }

    public void setDataNascimentoCliente(String textoDataNascimento) {
        this.dataNascimentoCliente = textoDataNascimento;
    }

    public String getTextoGeneroCliente() {
        return textoGeneroCliente;
    }

    public void setTextoGeneroCliente(String textoGenero) {
        this.textoGeneroCliente = textoGenero;
    }

    public String getTextoNomeCidade() {
        return textoNomeCidade;
    }

    public void setTextoNomeCidade(String textoNomeCidade) {
        this.textoNomeCidade = textoNomeCidade;
    }

    public String getTextoSiglaEstado() {
        return textoSiglaEstado;
    }

    public void setTextoSiglaEstado(String textoSiglaEstado) {
        this.textoSiglaEstado = textoSiglaEstado;
    }

    public String getCodigoEnderecoPostal() {
        return codigoEnderecoPostal;
    }

    public void setCodigoEnderecoPostal(String numeroCodigoPostal) {
        this.codigoEnderecoPostal = numeroCodigoPostal;
    }

    public BigDecimal getSaldoAtualContaCorrente() {
        return saldoAtualContaCorrente;
    }

    public void setSaldoAtualContaCorrente(BigDecimal saldoAtualContaCorrente) {
        this.saldoAtualContaCorrente = saldoAtualContaCorrente;
    }

}
